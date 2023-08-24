package com.mohammadyousefi.ketabcheh.cartItem;

import com.mohammadyousefi.ketabcheh.auth.Authorization;
import com.mohammadyousefi.ketabcheh.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    @Authorization
    public Response<List<CartItem>> findUserCart(HttpServletRequest request) {
        List<CartItem> cart = cartItemService.findByUserId((Long) request.getAttribute("userId")).orElse(new ArrayList<>());
        return new Response<>(cart);
    }

    @PostMapping("/offline/add/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Authorization
    public Response<String> addToCart(HttpServletRequest request, @PathVariable Long bookId) {
        String res = cartItemService.addToCart((Long) request.getAttribute("userId"), bookId);
        return new Response<>(res, HttpStatus.CREATED.value());
    }

    @PostMapping("/online/add/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Authorization
    public Response<String> addPdfBookToCart(HttpServletRequest request, @PathVariable Long bookId) {
        String res = cartItemService.addPdfBookToCart(getId(request), bookId);
        return new Response<>(res, HttpStatus.CREATED.value());
    }

    @DeleteMapping("/offline/decreaseQuantity/{cartItemId}")
    @Authorization
    public Response<String> decreaseCartQuantity(HttpServletRequest request, @PathVariable Long cartItemId) {
        String res = cartItemService.decreaseQuantity(getId(request), cartItemId);
        return new Response<>(res);
    }

    @DeleteMapping("/offline/remove/{cartItemId}")
    @Authorization
    public Response<String> removeOfflineItem(HttpServletRequest request, @PathVariable Long cartItemId) {
        return new Response<>(cartItemService.removeItem(getId(request), cartItemId, CartItemType.OFFLINE));
    }

    @DeleteMapping("/online/remove/{cartItemId}")
    @Authorization
    public Response<String> removeOnlineItem(HttpServletRequest request, @PathVariable Long cartItemId) {
        return new Response<>(cartItemService.removeItem(getId(request), cartItemId, CartItemType.ONLINE));
    }

    @DeleteMapping("/clear")
    @Authorization
    public Response<String> clear(HttpServletRequest request) {
        return new Response<>(cartItemService.clear(getId(request)));
    }

    private Long getId(HttpServletRequest request) {
        return (Long) request.getAttribute("userId");
    }

}
