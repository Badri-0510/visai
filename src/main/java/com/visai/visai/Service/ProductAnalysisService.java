package com.visai.visai.Service;

import com.visai.visai.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductAnalysisService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionRepository.class);

    @Autowired
    private TransactionRepository transactionRepository;

    private final RestTemplate restTemplate;

    public ProductAnalysisService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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


    public ResponseEntity<String> fetchFrequentCombinations(int productId) {
        String url = "http://localhost:5000/frequent_products";

        // Create JSON payload
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("product_id", productId);
        requestBody.put("max_no", 5);

        // Set HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request entity
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make POST request and return raw JSON as response
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    public Map<String, Object> getProductCluster(int productId) {
        String url = "http://localhost:5000/get_product_cluster?product_id=" + productId;

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            return response.getBody();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching product cluster", e);
        }
    }

    public Object getClusterData() {
        String url = "http://localhost:5000/get_cluster_data";

        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
            return response.getBody();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching cluster data", e);
        }
    }

}
