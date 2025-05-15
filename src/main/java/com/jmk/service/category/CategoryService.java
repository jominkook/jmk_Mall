package com.jmk.service.category;

import com.jmk.vo.category.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getAllCategories() throws Exception;
}
