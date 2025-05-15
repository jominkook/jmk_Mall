package com.jmk.service.product;

import com.jmk.vo.product.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductService {
    public void insertProduct(Product product) throws Exception;

    public List<Product> getProductList() throws Exception;

    public Product getProductById(int productId) throws Exception;

    public void updateProduct(Product product) throws Exception;

    public void deleteProduct(int productId) throws Exception;

    //페이징
    public int countProduct() throws Exception;

    public List<Product> selectProductPage(@Param("limit") int limit, @Param("offset") int offset) throws Exception;

    //키워드
    public List<Product> searchProductPage(
            @Param("keyword") String keyword,
            @Param("limit") int limit,
            @Param("offset") int offset
    ) throws Exception;

    public int countProductByKeyword(@Param("keyword") String keyword) throws Exception;
}
