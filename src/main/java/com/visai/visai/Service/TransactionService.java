package com.visai.visai.Service;

import com.visai.visai.Entity.Transaction;
import com.visai.visai.Repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Object[]> getTopSellers() {
        // Fetch all results and limit them to the top 10
        return transactionRepository.findTopSellers()
                .stream()
                .limit(10)
                .toList();
    }

    public List<Object[]> getTopProfitable() {
        // Fetch all results and limit them to the top 10
        return transactionRepository.findTopProfitables()
                .stream()
                .limit(10)
                .toList();
    }

    public List<Map<String, Object>> getSalesByCategory() {
        return transactionRepository.findSalesByCategory()
                .stream()
                .map(record -> Map.of(
                        "category", record[0], // First digit of pid
                        "totalSales", record[1] // Sum of purchase quantity
                ))
                .toList();
    }
    public Optional<Transaction> getTransactionDetails(int transactionId) {
        return transactionRepository.findTransactionDetailsById(transactionId);
    }

    // Get all transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Create a new transaction
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Update an existing transaction (Partial update using Map)
    public Transaction updateTransaction(int transactionId, Map<String, Object> updates) {
        java.util.Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isEmpty()) {
            throw new IllegalArgumentException("Transaction with ID " + transactionId + " not found");
        }

        Transaction transaction = optionalTransaction.get();

        // Apply updates based on the provided map
        updates.forEach((key, value) -> {
            switch (key) {
                case "oid":
                    transaction.setOid((Integer) value);
                    break;
                case "pid":
                    transaction.setPid((Integer) value);
                    break;
                case "purchaseQuantity":
                    transaction.setPurchaseQuantity((Integer) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        // Save the updated transaction
        return transactionRepository.save(transaction);
    }

    // Delete a transaction
    public boolean deleteTransaction(int transactionId) {
        if (transactionRepository.existsById(transactionId)) {
            transactionRepository.deleteById(transactionId);
            return true;
        }
        return false;
    }
}

