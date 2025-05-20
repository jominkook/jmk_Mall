package com.jmk.mapper;

import com.jmk.vo.product.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    public void insertProduct(Product product);

    public List<Product> selectProductList();

    public Product selectProductById(int productId);

    public void updateProduct(Product product);

    public void deleteProduct(int productId);

    //상품재고 차감
    public void updateProductStock(@Param("productId") int productId, @Param("quantity") int quantity);

    //페이징 처리

    public int countProduct();

    public List<Product> selectProductPage(@Param("limit") int limit, @Param("offset") int offset);

    //키워드
    public List<Product> searchProductPage(
            @Param("keyword") String keyword,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    public int countProductByKeyword(@Param("keyword") String keyword);
}
