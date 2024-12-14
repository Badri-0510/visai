package com.visai.visai.Controller;

import com.visai.visai.Service.ProductAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
