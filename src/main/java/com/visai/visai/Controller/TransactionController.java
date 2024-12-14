package com.visai.visai.Controller;

import com.visai.visai.Entity.Transaction;
import com.visai.visai.Service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/top-sellers")
    public List<Object[]> getTopSellers() {
        return transactionService.getTopSellers();
    }

    @GetMapping("/top-profitables")
    public List<Object[]> getTopProfitables() {
        return transactionService.getTopProfitable();
    }

    @GetMapping("/sales-by-category")
    public List<Map<String, Object>> getSalesByCategory() {
        return transactionService.getSalesByCategory();
    }
    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionDetails(@PathVariable int transactionId) {
        return transactionService.getTransactionDetails(transactionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all transactions
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    // Create a new transaction
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(createdTransaction);
    }

    // Update an existing transaction (Partial update using Map)
    @PutMapping("/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable int transactionId, @RequestBody Map<String, Object> updates) {
        try {
            Transaction updatedTransaction = transactionService.updateTransaction(transactionId, updates);
            return ResponseEntity.ok(updatedTransaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Delete a transaction
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<String> deleteTransaction(@PathVariable int transactionId) {
        boolean isDeleted = transactionService.deleteTransaction(transactionId);
        if (isDeleted) {
            return ResponseEntity.ok("The row is deleted successfully.");
        }
        return ResponseEntity.status(404).body("Transaction not found.");
    }
}
