package com.zhou.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhou.bean.OrderDetail;
import com.zhou.enums.OrderStatusEnum;
import com.zhou.enums.PayStatusEnum;
import com.zhou.utils.EnumUtil;
import com.zhou.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//如果前段要的数据为nullname我们也不给他传
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

//    订单id
    private String orderId;

//    买家名字
    private String buyerName;

//    买家手机号
    private String buyerPhone;

//     买家地址
    private String buyerAddress;

//    买家微信Openid
    private String buyerOpenid;

//     订单总金额
    private BigDecimal orderAmount;

//    订单状态, 默认为0新下单
    private Integer orderStatus;

//     支付状态, 默认为0未支付
    private Integer payStatus;

//     创建时间

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    //    更新时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;
    //在页面不能显示0/1/2/。。。而是显示新订单，已支付
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }
    //对象转称json格式忽略此方法
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
