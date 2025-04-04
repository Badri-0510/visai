package com.visai.visai.Controller;

import com.visai.visai.Service.ProductAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductAnalysisController {

    @Autowired
    private ProductAnalysisService productAnalysisService;

    @GetMapping("/subcategory/{subcategoryId}")
    public List<Map<String, Object>> getProductAnalysis(@PathVariable String subcategoryId) {
        return productAnalysisService.getProductAnalysisBySubcategory(subcategoryId);
    }

    @GetMapping("/submargin/{subcategoryId}")
    public List<Map<String,Object>> getProductmargin(@PathVariable String subcategoryId){
        return productAnalysisService.getProductByMargin(subcategoryId);
    }

    @GetMapping("/{productId}/frequent-combinations")
    public ResponseEntity<String> getFrequentCombinations(@PathVariable int productId) {
        return productAnalysisService.fetchFrequentCombinations(productId);
    }

    @GetMapping("/cluster/{productId}")
    public ResponseEntity<Map<String, Object>> getProductCluster(@PathVariable int productId) {
        Map<String, Object> result = productAnalysisService.getProductCluster(productId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/cluster-data")
    public ResponseEntity<Object> getClusterData() {
        Object result = productAnalysisService.getClusterData();
        return ResponseEntity.ok(result);
    }


}
