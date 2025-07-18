package com.portafolio.ecommerce_api.repositories;

import com.portafolio.ecommerce_api.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  
}