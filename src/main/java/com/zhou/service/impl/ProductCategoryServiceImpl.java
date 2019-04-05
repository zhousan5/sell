package com.zhou.service.impl;

import com.zhou.bean.ProductCategory;
import com.zhou.dao.ProductCategoryDao;
import com.zhou.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao PCDao;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return PCDao.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return PCDao.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return PCDao.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return PCDao.save(productCategory);
    }
}
