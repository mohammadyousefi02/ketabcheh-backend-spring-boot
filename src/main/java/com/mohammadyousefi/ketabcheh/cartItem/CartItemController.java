package com.mohammadyousefi.ketabcheh.cartItem;

import com.mohammadyousefi.ketabcheh.auth.Authorization;
import com.mohammadyousefi.ketabcheh.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cartItem")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
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

    @DeleteMapping("/offline/decreaseQuantity/{bookId}")
    @Authorization
    public Response<String> decreaseCartQuantity(HttpServletRequest request, @PathVariable Long bookId) {
        String res = cartItemService.decreaseQuantity(getId(request), bookId);
        return new Response<>(res);
    }

    @DeleteMapping("/offline/remove/{bookId}")
    @Authorization
    public Response<String> removeOfflineItem(HttpServletRequest request, @PathVariable Long bookId) {
        return new Response<>(cartItemService.removeItem(getId(request), bookId, CartItemType.OFFLINE));
    }

    @DeleteMapping("/online/remove/{bookId}")
    @Authorization
    public Response<String> removeOnlineItem(HttpServletRequest request, @PathVariable Long bookId) {
        return new Response<>(cartItemService.removeItem(getId(request), bookId, CartItemType.ONLINE));
    }

    @DeleteMapping("/clear")
    @Authorization
    public Response<String> clear(HttpServletRequest request) {
        return new Response<>(cartItemService.clear(getId(request)));
    }

    private Long getId(@NotNull HttpServletRequest request) {
        return (Long) request.getAttribute("userId");
    }

}
