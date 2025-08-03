package com.aminatech.VirtualBookStore.controller;

import com.aminatech.VirtualBookStore.model.Cart;
import com.aminatech.VirtualBookStore.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
public class CartController {

	private final CartService cartService;

	public CartController(CartService cartService) {
	    this.cartService = cartService;
	}

    private Long getCustomerId(HttpSession session) {
        Object id = session.getAttribute("customerId");
        if (id == null) throw new RuntimeException("❌ Not logged in");
        return (Long) id;
    }

    @GetMapping
    public Cart viewCart(HttpSession session) {
        return cartService.getCart(getCustomerId(session));
    }

    @PostMapping("/add/{bookId}")
    public ResponseEntity<String> addBook(@PathVariable String bookId, HttpSession session) {
        cartService.addBookToCart(getCustomerId(session), bookId);
        return ResponseEntity.ok("✅ Book added to cart");
    } 
    
    @DeleteMapping("/remove/{bookId}")
    public ResponseEntity<String> removeBook(@PathVariable String bookId, HttpSession session) {
        cartService.removeBookFromCart(getCustomerId(session), bookId);
        return ResponseEntity.ok("🧹 Book removed from cart");
    }


    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(HttpSession session) {
        cartService.clearCart(getCustomerId(session));
        return ResponseEntity.ok("🗑️ Cart cleared");
    } 
    
    @PostMapping("/login")
    public ResponseEntity<String> login(HttpSession session) {
        session.setAttribute("customerId", 1L); // or any mock ID
        return ResponseEntity.ok("✅ Logged in for testing");
    }

}