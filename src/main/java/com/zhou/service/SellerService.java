package com.zhou.service;



//  卖家端

import com.zhou.bean.SellerInfo;

public interface SellerService {


//     通过openid查询卖家端信息
    SellerInfo findSellerInfoByOpenid(String openid);
}
