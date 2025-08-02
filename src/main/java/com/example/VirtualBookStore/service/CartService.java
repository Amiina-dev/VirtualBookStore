package com.example.VirtualBookStore.service;

import com.example.VirtualBookStore.model.Cart;
import com.example.VirtualBookStore.model.Book;
import com.example.VirtualBookStore.service.BookService;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CartService {
 
    private final Map<Long, Cart> cartStore = new HashMap<>();
    private final BookService bookService;

    public CartService(BookService bookService) {
        this.bookService = bookService;
    }

    public Cart getCart(Long customerId) {
        return cartStore.computeIfAbsent(customerId, id -> {
            Cart cart = new Cart();
            cart.setCustomerId(id);
            return cart;
        });
    }

    public void addBookToCart(Long customerId, String bookId) {
        // Validate book existence via BookService
        Book book = bookService.getBookById(bookId);

        if (book == null) {
            throw new IllegalArgumentException("Cannot add to cart: Book with ID " + bookId + " does not exist.");
        }

        // Enrich cart item with book details
        Cart cart = getCart(customerId);
        cart.addBook(book); // Stores bookId, title, author via CartItem
    }

    public void removeBookFromCart(Long customerId, String bookId) {
        Cart cart = getCart(customerId);
        cart.removeBook(bookId);
    }

    public void clearCart(Long customerId) {
        cartStore.remove(customerId);
    }
}