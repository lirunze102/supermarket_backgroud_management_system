package com.wzu.lrz.dao;

import com.wzu.lrz.pojo.Product;
import com.wzu.lrz.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HiveMapper {

    List<Product> getProductList();
    void addProduct(Product product);
    void deleteById(Integer id);
    Product getProductById(Integer id);
    void updateProduct(Product product);
    List<Product> getProductListByName(String key);

}