package com.himedia.spserver.repository;

import com.himedia.spserver.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findTop4ByBestynOrderByIndateDesc(String y);
    List<Product> findTop4ByOrderByIndateDesc();
    List<Product> findByCategory(int category);
    Product findByPseq(int pseq);

    List<Product> findByNameContaining(String key);
    Page<Product> findAllByNameContaining(String key, Pageable pageable);

}
