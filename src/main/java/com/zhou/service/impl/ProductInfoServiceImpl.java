package com.zhou.service.impl;

import com.zhou.bean.ProductInfo;
import com.zhou.dao.ProductInfoDao;
import com.zhou.enums.ProductStatusEnum;
import com.zhou.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoDao PIDao;
    @Override
    public ProductInfo findOne(String productId) {
        return PIDao.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return PIDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return PIDao.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return PIDao.save(productInfo);
    }

    @Override
    public ProductInfo onSale(String productId) {
        return null;
    }

    @Override
    public ProductInfo offSale(String productId) {
        return null;
    }
}
