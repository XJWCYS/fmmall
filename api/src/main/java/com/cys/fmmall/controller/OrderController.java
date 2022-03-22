package com.cys.fmmall.controller;

import com.cys.fmmall.config.MyPayConfig;
import com.cys.fmmall.entity.Orders;
import com.cys.fmmall.service.OrderService;
import com.cys.fmmall.vo.ResStatus;
import com.cys.fmmall.vo.ResultVO;
import com.github.wxpay.sdk.WXPay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/orders")
@Api(value = "提供订单的操作接口",tags = "订单管理")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/success")
    public void success(){

    }
    @PostMapping("/add")
    public ResultVO addOrder(String cids, @RequestBody Orders orders){
        ResultVO resultVO = null;
        try {
            Map<String, String> map = orderService.addOrder(cids, orders);
            //发送请求，获取响应
            //微信⽀付：申请⽀付连接
            WXPay wxPay = new WXPay(new MyPayConfig());
            Map<String, String> resp = wxPay.unifiedOrder(map);
            map.put("payUrl",resp.get("code_url"));
            resultVO =  new ResultVO(ResStatus.OK,"提交成功",map);
        } catch (SQLException throwables) {
            return new ResultVO(ResStatus.NO,"提交订单失败",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultVO;
    }
    @GetMapping("/list")
    @ApiOperation("订单查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "userId", value = "用户ID",required = true),
            @ApiImplicitParam(dataType = "string",name = "status", value = "订单状态",required = false),
            @ApiImplicitParam(dataType = "int",name = "pageNum", value = "页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit", value = "每页条数",required = true)
    })
    public ResultVO list(@RequestHeader("token")String token,
                         String userId,String status,int pageNum,int limit){
        ResultVO resultVO = orderService.listOrders(userId, status, pageNum, limit);
        return resultVO;
    }
    @GetMapping("/adminlist")
    @ApiOperation("管理员订单查询接口")

    public ResultVO list(@RequestHeader("token")String token,String status,int pageNum){
        ResultVO resultVO = orderService.listAdminOrders(status,pageNum);
        return resultVO;
    }
    @PostMapping("/fh")
    @ApiOperation("管理员订单查询接口")
    public ResultVO fhAdminOrders(@RequestHeader("token")String token,@RequestParam(value = "orderId") String orderId){
        System.out.println(orderId);
        ResultVO resultVO = orderService.fhAdminOrders(orderId);
        return resultVO;
    }
}
