package com.vipjoker.ecomerce.repository;

import com.vipjoker.ecomerce.model.Category;
import com.vipjoker.ecomerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository  extends JpaRepository<Category,Long> {
    Category findByName(String name);
}
