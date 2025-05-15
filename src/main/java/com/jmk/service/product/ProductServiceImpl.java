package com.jmk.service.product;

import com.jmk.mapper.ProductMapper;
import com.jmk.vo.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Transactional
    @Override
    public void insertProduct(Product product) throws Exception {
       productMapper.insertProduct(product);
    }

    @Transactional
    @Override
    public List<Product> getProductList() throws Exception {
        return productMapper.selectProductList();
    }

    @Transactional
    @Override
    public Product getProductById(int productId) throws Exception {
        return productMapper.selectProductById(productId);
    }

    @Transactional
    @Override
    public void updateProduct(Product product) throws Exception {
        productMapper.updateProduct(product);
    }

    @Transactional
    @Override
    public void deleteProduct(int productId) throws Exception {
        productMapper.deleteProduct(productId);
    }

    //페이징
    @Override
    public int countProduct() throws Exception {
        return productMapper.countProduct();
    }

    @Override
    public List<Product> selectProductPage(int limit, int offset) throws Exception {
        return productMapper.selectProductPage(limit,offset);
    }

    @Override
    public List<Product> searchProductPage(String keyword, int limit, int offset) throws Exception {
        return productMapper.searchProductPage(keyword, limit, offset);

    }

    @Override
    public int countProductByKeyword(String keyword) throws Exception {
        return productMapper.countProductByKeyword(keyword);
    }


}
