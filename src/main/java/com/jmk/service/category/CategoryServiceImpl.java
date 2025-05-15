package com.jmk.service.category;

import com.jmk.mapper.CategoryMapper;
import com.jmk.vo.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> getAllCategories() throws Exception{
        return categoryMapper.getAllCategories();
    }
}
