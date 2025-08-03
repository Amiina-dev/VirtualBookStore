//package com.aminatech.VirtualBookStore;
//
//import com.aminatech.VirtualBookStore.model.Order;
//import com.aminatech.VirtualBookStore.model.Cart;
//import com.aminatech.VirtualBookStore.service.CartService;
//import java.time.LocalDate;
//import java.util.List;
//
//public class TestOrderRunner {
//    public static void main(String[] args) {
//        Order order = new Order();
//        order.setId(1L);
//        order.setCustomerName("Amina");
//        order.setCustomerEmail("amina@aminatech.com");
//        order.setBookIds(List.of(101L, 102L));
//        order.setOrderDate(LocalDate.now());
//        order.setFulfilled(false);
//        order.setTotalPrice(49.99);
//
//        System.out.println("✅ Order created:");
//        System.out.println(order);
//        
//        CartService cartService = new CartService();
//        cartService.addBookToCart(1L, 101L);
//        cartService.addBookToCart(1L, 102L);
//
//        Cart cart = cartService.getCart(1L);
//        System.out.println("🛒 Cart contents for customer 1: " + cart.getBookIds());
//
//        cartService.removeBookFromCart(1L, 101L);
//        System.out.println("🧹 After removing book 101: " + cart.getBookIds());
//    }
//}