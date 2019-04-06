package com.zhou.service.impl;

import com.zhou.bean.OrderDetail;
import com.zhou.bean.OrderMaster;
import com.zhou.bean.ProductInfo;
import com.zhou.dao.OrderDetailDao;
import com.zhou.dao.OrderMasterDao;
import com.zhou.dto.CartDTO;
import com.zhou.dto.OrderDTO;
import com.zhou.enums.OrderStatusEnum;
import com.zhou.enums.PayStatusEnum;
import com.zhou.enums.ResultEnum;
import com.zhou.exception.SellException;
import com.zhou.service.OrderService;
import com.zhou.service.ProductInfoService;
import com.zhou.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productService;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private OrderMasterDao orderMasterDao;

//    @Autowired
//    private PayService payService;
//
//    @Autowired
//    private PushMessageService pushMessageService;
//
//    @Autowired
//    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        //生成主键 订单生成时就生成了
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

//        List<CartDTO> cartDTOList = new ArrayList<>();

        //1. 查询商品数量, 价格
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2. 计算订单总价  乘法有专门的的写法
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库
            //主见
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            //订单ID
            orderDetail.setOrderId(orderId);
            //属性拷贝
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailDao.save(orderDetail);

//            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
//            cartDTOList.add(cartDTO);
        }


        //3. 写入订单数据库（orderMaster和orderDetail）
        OrderMaster orderMaster = new OrderMaster();
        //订单号
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        //总价
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDao.save(orderMaster);

        //4. 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        //发送websocket消息
//        webSocket.sendMessage(orderDTO.getOrderId());

            return orderDTO;
        }

    @Override
    public OrderDTO findOne(String orderId) {
//
//        OrderMaster orderMaster = orderMasterDao.findOne(orderId);
//        if (orderMaster == null) {
//            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
//        }
//
//        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
//        if (CollectionUtils.isEmpty(orderDetailList)) {
//            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
//        }
//
        OrderDTO orderDTO = new OrderDTO();
//        BeanUtils.copyProperties(orderMaster, orderDTO);
//        orderDTO.setOrderDetailList(orderDetailList);
//
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
//        Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
//
//        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
//
//        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
//    }
//
//    @Override
//    @Transactional
//    public OrderDTO cancel(OrderDTO orderDTO) {
//        OrderMaster orderMaster = new OrderMaster();
//
//        //判断订单状态
//        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
//            log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
//            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
//        }
//
//        //修改订单状态
//        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
//        BeanUtils.copyProperties(orderDTO, orderMaster);
//        OrderMaster updateResult = orderMasterDao.save(orderMaster);
//        if (updateResult == null) {
//            log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
//            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
//        }
//
//        //返回库存
//        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
//            log.error("【取消订单】订单中无商品详情, orderDTO={}", orderDTO);
//            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
//        }
//        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
//                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
//                .collect(Collectors.toList());
//        productService.increaseStock(cartDTOList);
//
//        //如果已支付, 需要退款
//        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
//            payService.refund(orderDTO);
//        }
//
//        return orderDTO;
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
//        //判断订单状态
//        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
//            log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
//            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
//        }
//
//        //修改订单状态
//        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
//        OrderMaster orderMaster = new OrderMaster();
//        BeanUtils.copyProperties(orderDTO, orderMaster);
//        OrderMaster updateResult = orderMasterDao.save(orderMaster);
//        if (updateResult == null) {
//            log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
//            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
//        }
//
//        //推送微信模版消息
//        pushMessageService.orderStatus(orderDTO);
//
//        return orderDTO;
        return null;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
//        //判断订单状态
//        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
//            log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
//            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
//        }
//
//        //判断支付状态
//        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
//            log.error("【订单支付完成】订单支付状态不正确, orderDTO={}", orderDTO);
//            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
//        }
//
//        //修改支付状态
//        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
//        OrderMaster orderMaster = new OrderMaster();
//        BeanUtils.copyProperties(orderDTO, orderMaster);
//        OrderMaster updateResult = orderMasterDao.save(orderMaster);
//        if (updateResult == null) {
//            log.error("【订单支付完成】更新失败, orderMaster={}", orderMaster);
//            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
//        }
//
//        return orderDTO;
        return null;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
//        Page<OrderMaster> orderMasterPage = orderMasterDao.findAll(pageable);
//
//        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
//
//        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
        return null;
    }
    }
