package com.zhou.service.impl;

import com.zhou.bean.ProductInfo;
import com.zhou.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {
    @Autowired
    private ProductInfoServiceImpl productInfoService;
    @Test
    public void findOne() {
        ProductInfo productInfo = productInfoService.findOne("1");
        Assert.assertEquals("1",productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> ProductInfoList = productInfoService.findUpAll();
        Assert.assertNotEquals(0,ProductInfoList.size());

    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(1,2);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(request);
        System.out.println(productInfoPage.getTotalElements()+"!!!!!!!!!!!");
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("3");
        productInfo.setProductName("西湖龙井");
        productInfo.setProductPrice(new BigDecimal(9.9));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好喝的茶");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(1);


        Assert.assertNotNull(productInfoService.save(productInfo));
    }

    @Test
    public void onSale() {
        ProductInfo productInfo = productInfoService.onSale("1");
        Assert.assertEquals(ProductStatusEnum.UP,productInfo.getProductStatusEnum());
    }

    @Test
    public void offSale() {
        ProductInfo productInfo = productInfoService.offSale("1");
        Assert.assertEquals(ProductStatusEnum.DOWN,productInfo.getProductStatusEnum());
    }
}