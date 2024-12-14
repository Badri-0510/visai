package com.visai.visai.Service;

import com.visai.visai.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Service
public class ProductAnalysisService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionRepository.class);

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Map<String, Object>> getProductAnalysisBySubcategory(String subcategoryId) {

        List<Map<String, Object>> result = transactionRepository.findTotalPurchaseBySubcategory(subcategoryId.trim());
        logger.info("Query Result for Subcategory ID {}: {}", subcategoryId, result); // Log the result
        return result;
    }

    public List<Map<String, Object>> getProductByMargin(String subcategoryId) {

        List<Map<String, Object>> result = transactionRepository.findTotalMarginBySubcategory(subcategoryId.trim());
        logger.info("Query Result for Subcategory ID {}: {}", subcategoryId, result); // Log the result
        return result;

    }
}
