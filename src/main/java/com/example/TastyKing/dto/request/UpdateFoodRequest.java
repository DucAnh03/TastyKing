package com.example.TastyKing.dto.request;

import com.example.TastyKing.entity.Category;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateFoodRequest {
    private Long categoryID;


    private String foodName;

    @DecimalMin(value = "0.0", inclusive = false, message = "PRICE_INVALID")
    private Double foodPrice;

    @DecimalMin(value = "0.0", inclusive = false, message = "PRICE_INVALID")
    private Double foodCost;


    private String description;

    private String unit;


    private MultipartFile foodImage;

}
