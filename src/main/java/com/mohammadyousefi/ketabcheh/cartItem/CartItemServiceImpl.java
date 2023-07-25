package com.mohammadyousefi.ketabcheh.cartItem;

import com.mohammadyousefi.ketabcheh.book.Book;
import com.mohammadyousefi.ketabcheh.book.BookService;
import com.mohammadyousefi.ketabcheh.exception.BadRequestException;
import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
import com.mohammadyousefi.ketabcheh.user.User;
import com.mohammadyousefi.ketabcheh.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final BookService bookService;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, UserService userService, BookService bookService) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public Optional<List<CartItem>> findByUserId(Long userId) {
        return cartItemRepository.findByUser_Id(userId);
    }

    @Override
    public String addToCart(Long userId, Long bookId) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findByUser_IdAndBook_IdAndType(userId, bookId, CartItemType.OFFLINE);
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            throwBookStockError(checkStock(cartItem));
            cartItem.increaseQuantity();
            cartItemRepository.save(cartItem);
        } else {
            User user = findUserById(userId);
            Book book = findBookById(bookId);
            throwBookStockError(checkStock(book));
            addToCart(book, user, CartItemType.OFFLINE);
        }
        return "successfully added";
    }

    @Override
    public String addPdfBookToCart(Long userId, Long bookId) {
        User user = findUserById(userId);
        Book book = findBookById(bookId);
        if (cartItemRepository.findByUser_IdAndBook_IdAndType(userId, bookId, CartItemType.ONLINE).isEmpty()) {
            addToCart(book, user, CartItemType.ONLINE);
        }
        return "successfully added";
    }

    @Override
    public String decreaseQuantity(Long userId, Long bookId) {
        CartItem cartItem = findOneByUserIdAndBookId(userId, bookId, CartItemType.OFFLINE);
        if (cartItem.getQuantity() == 1) cartItemRepository.delete(cartItem);
        cartItem.decreaseQuantity();
        cartItemRepository.save(cartItem);
        return "successfully done";
    }

    @Override
    public String removeItem(Long userId, Long bookId, CartItemType cartItemType) {
        CartItem cartItem = findOneByUserIdAndBookId(userId, bookId, cartItemType);
        cartItemRepository.delete(cartItem);
        return "successfully deleted";
    }

    @Override
    @Transactional
    public String clear(Long userId) {
        findUserById(userId);
        cartItemRepository.deleteByUser_Id(userId);
        return "successfully deleted";
    }

    private CartItem findOneByUserIdAndBookId(Long userId, Long bookId, CartItemType cartItemType) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findByUser_IdAndBook_IdAndType(userId, bookId, cartItemType);
        if (cartItemOptional.isEmpty()) throw new NotFoundException("there is no cart item with this id");
        return cartItemOptional.get();
    }

    private User findUserById(Long userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) throw new NotFoundException("there is no user with this id");
        return userOptional.get();
    }

    private Book findBookById(Long bookId) {
        Optional<Book> bookOptional = bookService.findById(bookId);
        if (bookOptional.isEmpty()) throw new NotFoundException("there is no book with this id");
        return bookOptional.get();
    }

    private Boolean checkStock(CartItem cartItem) {
        return cartItem.getQuantity() < cartItem.getBook().getStock();
    }

    private Boolean checkStock(Book book) {
        return book.getStock() > 0;
    }

    private void throwBookStockError(Boolean ableToAdd) {
        if (Boolean.FALSE.equals(ableToAdd)) throw new BadRequestException("you can't add more of this book");
    }

    private void addToCart(Book book, User user, CartItemType cartItemType) {
        CartItem newCartItem = new CartItem();
        newCartItem.setBook(book);
        newCartItem.setUser(user);
        newCartItem.setType(cartItemType);
        cartItemRepository.save(newCartItem);
    }
}
