package com.zhou.controller;

import com.zhou.VO.ProductInfoVO;
import com.zhou.VO.ProductVO;
import com.zhou.VO.ResultVO;

import com.zhou.bean.ProductCategory;
import com.zhou.bean.ProductInfo;
import com.zhou.service.ProductCategoryService;
import com.zhou.service.ProductInfoService;
import com.zhou.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

//买家商品
//返回的jsong格式所以
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @GetMapping("/list")
    public ResultVO list(){
        //要展示 先查询商品信息是否是上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        //查询类目 一次查询  查需要的
//        List<Integer> productCategoryTypeList = new ArrayList<>();
        //传统方法
//        for (ProductInfo productInfo: productInfoList) {
//            productCategoryTypeList.add(productInfo.getCategoryType());
//        }
        //精简 java8 lambda表达式
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());

        
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        //数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory: productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo: productInfoList) {
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

//        ResultVO resultVO = new ResultVO();
//        resultVO.setData(productVOList);
//        resultVO.setCode(0);
//        resultVO.setMsg("成功");

        return ResultVOUtil.success(productVOList);

    }



}
