package com.visai.visai.Service;


import com.visai.visai.Entity.Order;
import com.visai.visai.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public List<Object[]> getDailySalesByMonth(int month) {
        return orderRepository.findDailySalesByMonth(month);
    }


    public List<Object[]> getMonthlySalesByYear(int year) {
        return orderRepository.findMonthlySalesByYear(year);
    }


    public Optional<Order> getOrderDetails(int orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    // Update an existing order (Partial update using Map)
    public Order updateOrder(int orderId, Map<String, Object> updates) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new IllegalArgumentException("Order with ID " + orderId + " not found");
        }

        Order order = optionalOrder.get();

        // Apply updates based on the provided map
        updates.forEach((key, value) -> {
            switch (key) {
                case "total":
                    order.setTotal((Double) value);
                    break;
                case "datetime":
                    order.setDatetime(LocalDateTime.parse(value.toString()));
                    break;
                case "customer":
                    throw new UnsupportedOperationException("Customer updates not supported via this endpoint.");
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        // Save the updated order
        return orderRepository.save(order);
    }

    // Delete an order
    public boolean deleteOrder(int orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }
}
