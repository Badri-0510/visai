package com.visai.visai.Controller;


import com.visai.visai.Entity.Order;
import com.visai.visai.Service.DailySalesDTO;
import com.visai.visai.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable int orderId) {
        return orderService.getOrderDetails(orderId)
                .map(ResponseEntity::ok) // Map the Optional<Order> to ResponseEntity<Order>
                .orElse(ResponseEntity.notFound().build()); // Return 404 if not found
    }

    @GetMapping("/monthly-sales")
    public List<Object[]> getDailySales(@RequestParam("month") int month) {
        return orderService.getDailySalesByMonth(month);
    }

    @GetMapping("/yearly-sales")
    public List<Object[]> getMonthlySales(@RequestParam("year") int year) {
        return orderService.getMonthlySalesByYear(year);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    // Update an existing order (Partial update using Map)
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable int orderId, @RequestBody Map<String, Object> updates) {
        try {
            Order updatedOrder = orderService.updateOrder(orderId, updates);
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Delete an order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int orderId) {
        if (orderService.deleteOrder(orderId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
