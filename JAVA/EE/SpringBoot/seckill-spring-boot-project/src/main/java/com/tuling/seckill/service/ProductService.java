package com.tuling.seckill.service;

import com.tuling.seckill.dao.ProductDao;
import com.tuling.seckill.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public List<Product> listProducts() {
        return productDao.listProducts();
    }

    public Product getProductById(Long id) {
        return productDao.getProductById(id);
    }

    @Transactional
    public void saveProduct(Product product) {
        productDao.saveProduct(product);
    }

    @Transactional
    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    @Transactional
    public int deductProductStock(Long id) {
        return productDao.deductProductStock(id);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productDao.deleteProduct(id);
    }
}
