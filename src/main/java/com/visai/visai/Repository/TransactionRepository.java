package com.visai.visai.Repository;

import com.visai.visai.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("SELECT p.name AS name, SUM(t.purchaseQuantity) AS totalQuantity FROM Transaction t join Product p on p.pid=t.pid  WHERE SUBSTRING(CAST(t.pid AS string), 1, 3) = :subcategoryId GROUP BY t.pid")
    List<Map<String, Object>> findTotalPurchaseBySubcategory(@Param("subcategoryId") String subcategoryId);

    @Query("SELECT p.name AS name,SUM(p.margin *t.purchaseQuantity) AS totalMargin FROM Transaction t join Product p on p.pid=t.pid  WHERE SUBSTRING(CAST(t.pid AS string), 1, 3) = :subcategoryId GROUP BY t.pid")
    List<Map<String, Object>> findTotalMarginBySubcategory(@Param("subcategoryId") String subcategoryId);


    @Query("SELECT p.pid, p.name, SUM(t.purchaseQuantity) AS totalQuantity " +
            "FROM Transaction t JOIN Product p ON t.pid = p.pid " +
            "GROUP BY p.pid, p.name " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findTopSellers();


    @Query("SELECT p.pid, p.name, SUM(t.purchaseQuantity * p.margin) AS totalProfit " +
            "FROM Transaction t JOIN Product p ON t.pid = p.pid " +
            "GROUP BY p.pid, p.name " +
            "ORDER BY totalProfit DESC")
    List<Object[]> findTopProfitables();

    @Query("SELECT SUBSTRING(CAST(t.pid AS string), 1, 1) AS category, " +
            "SUM(t.purchaseQuantity) AS totalSales " +
            "FROM Transaction t " +
            "GROUP BY category " +
            "ORDER BY category ASC")
    List<Object[]> findSalesByCategory();

    @Query("SELECT t FROM Transaction t WHERE t.tid = :transactionId")
    Optional<Transaction> findTransactionDetailsById(@Param("transactionId") int transactionId);
}
