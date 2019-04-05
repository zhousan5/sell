package com.zhou.dao;

import com.zhou.bean.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {
    @Autowired
    private ProductCategoryDao PCDao;
    @Test
    public void findOneTest(){
    ProductCategory pc = PCDao.findOne(1);
        System.out.println(pc.toString());
    }

    @Test
//  类似回滚 不会扰乱数据库
    @Transactional
    public void saveTest(){
        ProductCategory pc = new ProductCategory("明星榜",7);
//        pc.setCategoryName("明星榜");
//        pc.setCategoryType(3);
//       ProductCategory result = PCDao.save(pc);
       Assert.assertNotNull(PCDao.save(pc));

    }
//视频  没有无参构造器  报错  我没有却可以
    @Test
    public void findByCategoryTypeIn(){
        List<Integer> list = Arrays.asList(2, 3, 4);
        List<ProductCategory> result = PCDao.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result);
    }
    @Test
    public void updateTest(){
        ProductCategory pc = new ProductCategory("bang", 99);
        Assert.assertEquals(pc,PCDao.save(pc));
    }
}