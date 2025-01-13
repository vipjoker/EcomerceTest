package com.vipjoker.ecomerce.request;

import com.vipjoker.ecomerce.model.Category;
import com.vipjoker.ecomerce.model.Image;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class AddProductRequest {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory ;
    private String description;
    private Category category;
    private List<Image> images;
}
