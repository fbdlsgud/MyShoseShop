package com.himedia.spserver.service;

import com.himedia.spserver.entity.Product;
import com.himedia.spserver.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository pR;

    public Object getBestProduct() {
        return pR.findTop4ByBestynOrderByIndateDesc("Y");
    }

    public Object getNewProduct() {
        return pR.findTop4ByOrderByIndateDesc();
    }

    public List<Product> getCategoryList(int category) {
        return pR.findByCategory( category );
    }

    public Object getProduct(int pseq) {
        return pR.findByPseq(pseq);
    }
}
