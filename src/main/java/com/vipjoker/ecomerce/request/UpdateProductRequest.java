package com.vipjoker.ecomerce.request;

import com.vipjoker.ecomerce.model.Category;
import com.vipjoker.ecomerce.model.Image;

import java.math.BigDecimal;
import java.util.List;

public class UpdateProductRequest {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory ;
    private String description;
    private Category category;
    private List<Image> images;
}
