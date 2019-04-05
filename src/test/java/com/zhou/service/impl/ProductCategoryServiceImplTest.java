package com.zhou.service.impl;

import com.zhou.bean.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {
    @Autowired
    private ProductCategoryServiceImpl PCService;

    @Test
    public void findOne() {
        Assert.assertEquals(new Integer(1),PCService.findOne(1).getCategoryId());
    }

    @Test
    public void findAll() {
        Assert.assertNotEquals(0,PCService.findAll());
    }

    @Test
    public void findByCategoryTypeIn() {
        Assert.assertNotEquals(0,PCService.findByCategoryTypeIn(Arrays.asList(1,2,3)));
    }

    @Test
    @Transactional
    public void save() {
        ProductCategory productCategory = new ProductCategory("男生专享", 10);
        ProductCategory result = PCService.save(productCategory);
        Assert.assertNotNull(result);

    }
}