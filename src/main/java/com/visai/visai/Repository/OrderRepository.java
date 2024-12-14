package com.visai.visai.Repository;

import com.visai.visai.Entity.Order;
import com.visai.visai.Service.DailySalesDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer>{

    @Query(value = "SELECT o FROM Order o " +
            "JOIN FETCH o.customer c " +
            "JOIN FETCH Transaction t ON t.oid = o.oid " +
            "JOIN FETCH Product p ON p.pid = t.pid " +
            "WHERE o.oid = :orderId",nativeQuery = true)
    Optional<Order> findOrderDetailsById(@Param("orderId") int orderId);

    @Query("SELECT FUNCTION('DAY', o.datetime), SUM(o.total) " +
            "FROM Order o " +
            "WHERE FUNCTION('MONTH', o.datetime) = :month " +
            "GROUP BY FUNCTION('DAY', o.datetime) " +
            "ORDER BY FUNCTION('DAY', o.datetime)")
    List<Object[]> findDailySalesByMonth(@Param("month") int month);

    @Query("SELECT FUNCTION('MONTH', o.datetime), SUM(o.total) " +
            "FROM Order o " +
            "WHERE FUNCTION('YEAR', o.datetime) = :year " +
            "GROUP BY FUNCTION('MONTH', o.datetime) " +
            "ORDER BY FUNCTION('MONTH', o.datetime)")
    List<Object[]> findMonthlySalesByYear(@Param("year") int year);


}

