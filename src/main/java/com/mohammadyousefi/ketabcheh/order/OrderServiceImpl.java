package com.mohammadyousefi.ketabcheh.order;

import com.mohammadyousefi.ketabcheh.book.Book;
import com.mohammadyousefi.ketabcheh.book.BookService;
import com.mohammadyousefi.ketabcheh.cartItem.CartItem;
import com.mohammadyousefi.ketabcheh.cartItem.CartItemService;
import com.mohammadyousefi.ketabcheh.cartItem.CartItemType;
import com.mohammadyousefi.ketabcheh.exception.BadRequestException;
import com.mohammadyousefi.ketabcheh.profile.Profile;
import com.mohammadyousefi.ketabcheh.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemService cartItemService;
    private final BookService bookService;

    public OrderServiceImpl(OrderRepository orderRepository, CartItemService cartItemService, BookService bookService) {
        this.orderRepository = orderRepository;
        this.cartItemService = cartItemService;
        this.bookService = bookService;
    }

    @Override
    @Transactional
    public Order order(OrderDto orderDto) {
        Optional<List<CartItem>> cartItemList = cartItemService.findByUserId(orderDto.getUserId());
        if (cartItemList.isEmpty()) throw new BadRequestException("you should add something to your cart first");

        Order newOrder = new Order();
        newOrder.setName(orderDto.getName());
        newOrder.setCity(orderDto.getCity());
        newOrder.setAddress(orderDto.getAddress());
        newOrder.setZipcode(orderDto.getZipcode());
        newOrder.setProvince(orderDto.getProvince());
        User user = new User();
        user.setId(orderDto.getUserId());

        Profile newProfile = new Profile();
        newProfile.setUser(user);
        List<Book> books = new ArrayList<>();

        List<OrderItem> orderItems = new ArrayList<>();
        cartItemList.get().forEach(cartItem -> {
            if (cartItem.getType() == CartItemType.OFFLINE && (cartItem.getQuantity() > cartItem.getBook().getStock()))
                throw new BadRequestException("one of your cart items is unavailable");
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTitle(cartItem.getBook().getTitle());
            orderItem.setPrice(cartItem.getBook().getPrice());
            orderItem.setBook(cartItem.getBook());
            orderItem.setOrder(newOrder);

            orderItems.add(orderItem);
            if (cartItem.getType() == CartItemType.OFFLINE)
                bookService.decreaseStock(cartItem.getBook().getId(), cartItem.getQuantity());
            else {
                List<Profile> profiles = cartItem.getBook().getProfiles() != null ? cartItem.getBook().getProfiles() : new ArrayList<>();
                profiles.forEach(profile -> {
                    if (Objects.equals(profile.getUser().getId(), orderDto.getUserId())) {
                        Optional<Book> optionalBook = profile.getBooks().stream().filter(book -> Objects.equals(book.getId(), cartItem.getBook().getId())).findFirst();
                        if (optionalBook.isEmpty()) {
                            books.add(cartItem.getBook());
                            newProfile.setBooks(books);
                            profiles.add(newProfile);
                            cartItem.getBook().setProfiles(profiles);
                        }
                    }
                });
            }

        });

        newOrder.setUser(user);
        cartItemService.clear(orderDto.getUserId());
        newOrder.setOrderItems(orderItems);
        return orderRepository.save(newOrder);
    }
}
