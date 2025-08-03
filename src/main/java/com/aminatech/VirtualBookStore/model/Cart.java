package com.aminatech.VirtualBookStore.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Long customerId;
    private Map<String, CartItem> items = new HashMap<>();

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Map<String, CartItem> getItems() {
        return items;
    }

    public void addBook(Book book) {
        items.put(book.getId(), new CartItem(book.getId(), book.getTitle(), book.getAuthor()));
    }

    public void removeBook(String bookId) {
        items.remove(bookId);
    }
}