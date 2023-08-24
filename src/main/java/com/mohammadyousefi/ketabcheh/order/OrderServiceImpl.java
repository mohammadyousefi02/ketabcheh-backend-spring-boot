package com.mohammadyousefi.ketabcheh.order;

import com.mohammadyousefi.ketabcheh.book.Book;
import com.mohammadyousefi.ketabcheh.book.BookService;
import com.mohammadyousefi.ketabcheh.cartItem.CartItem;
import com.mohammadyousefi.ketabcheh.cartItem.CartItemService;
import com.mohammadyousefi.ketabcheh.cartItem.CartItemType;
import com.mohammadyousefi.ketabcheh.exception.BadRequestException;
import com.mohammadyousefi.ketabcheh.exception.ErrorMessages;
import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
import com.mohammadyousefi.ketabcheh.profile.Profile;
import com.mohammadyousefi.ketabcheh.profile.ProfileService;
import com.mohammadyousefi.ketabcheh.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemService cartItemService;
    private final ProfileService profileService;
    private final BookService bookService;

    public OrderServiceImpl(OrderRepository orderRepository, CartItemService cartItemService, ProfileService profileService, BookService bookService) {
        this.orderRepository = orderRepository;
        this.cartItemService = cartItemService;
        this.profileService = profileService;
        this.bookService = bookService;
    }

    private Order findById(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty())
            throw new NotFoundException(ErrorMessages.notFound("order"));
        return optionalOrder.get();
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUser_Id(userId).orElse(new ArrayList<>());
    }

    @Override
    @Transactional
    public Order order(OrderDto orderDto, Boolean useWallet) {
        Optional<List<CartItem>> cartItemList = cartItemService.findByUserId(orderDto.getUserId());
        if (cartItemList.isEmpty() || cartItemList.get().isEmpty())
            throw new BadRequestException("you should add something to your cart first");

        Order newOrder = createOrderObj(orderDto);

        Profile userProfile = profileService.findByUserId(orderDto.getUserId());
        List<Book> books = userProfile.getBooks() != null ? userProfile.getBooks() : new ArrayList<>();
        User user = new User();
        user.setId(orderDto.getUserId());

        if (Boolean.TRUE.equals(useWallet)) payByWallet(cartItemList.get(), userProfile);

        List<OrderItem> orderItems = new ArrayList<>();
        cartItemList.get().forEach(cartItem -> {
            orderItems.add(createOrderItem(cartItem, newOrder));
            if (cartItem.getType() == CartItemType.OFFLINE)
                bookService.decreaseStock(cartItem.getBook().getId(), cartItem.getQuantity());
            else {
                Optional<Book> optionalBook = userProfile.getBooks().stream().filter(book -> Objects.equals(book.getId(), cartItem.getBook().getId())).findFirst();
                if (optionalBook.isEmpty()) {
                    books.add(cartItem.getBook());
                    userProfile.setBooks(books);
                }
            }
        });

        newOrder.setUser(user);
        cartItemService.clear(orderDto.getUserId());
        newOrder.setOrderItems(orderItems);
        return orderRepository.save(newOrder);
    }

    @Override
    public String toggleStatus(Long orderId) {
        Order order = findById(orderId);
        order.setDelivered(!order.getDelivered());
        orderRepository.save(order);
        return "toggled delivered status successfully";
    }

    private Order createOrderObj(OrderDto orderDto) {
        Order newOrder = new Order();
        newOrder.setName(orderDto.getName());
        newOrder.setCity(orderDto.getCity());
        newOrder.setAddress(orderDto.getAddress());
        newOrder.setZipcode(orderDto.getZipcode());
        newOrder.setProvince(orderDto.getProvince());
        return newOrder;
    }

    private OrderItem createOrderItem(CartItem cartItem, Order newOrder) {
        if (cartItem.getType() == CartItemType.OFFLINE && (cartItem.getQuantity() > cartItem.getBook().getStock()))
            throw new BadRequestException("one of your cart items is unavailable");
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setTitle(cartItem.getBook().getTitle());
        orderItem.setPrice(cartItem.getBook().getPrice());
        orderItem.setBook(cartItem.getBook());
        orderItem.setOrder(newOrder);
        return orderItem;
    }

    private int calculateTotalPrice(List<CartItem> cartItems) {
        AtomicInteger totalPrice = new AtomicInteger();
        cartItems.forEach(cartItem -> totalPrice.addAndGet((cartItem.getBook().getPrice() * cartItem.getQuantity())));
        return totalPrice.get();
    }

    private void canBuy(int totalPrice, Long wallet) {
        if (Boolean.FALSE.equals(totalPrice < wallet))
            throw new BadRequestException("the wallet credit is not enough to pay");
    }

    private void payByWallet(List<CartItem> cartItems, Profile profile) {
        int totalPrice = calculateTotalPrice(cartItems);
        canBuy(totalPrice, profile.getWallet());
        profile.setWallet(profile.getWallet() - totalPrice);
    }
}
