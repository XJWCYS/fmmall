package com.cys.fmmall.controller;

import com.cys.fmmall.service.OrderService;
import com.cys.fmmall.websocket.WebSocketServer;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    /**
     * 回调接口:当用户支付成功之后，微信支付平台就会请求这个接口，将支付状态的数据传递过来
     */
    @RequestMapping("/callback")
    public String addOrder(HttpServletRequest request) throws Exception {
        System.out.println("success");
        //1.接收微信支付平台传递的数据（使用request的输入流接收）
        ServletInputStream is = request.getInputStream();
        byte[] bytes = new byte[1024];
        int len = -1;
        StringBuilder sb = new StringBuilder();
        while((len=is.read(bytes))!=-1){
            sb.append(new String(bytes,0,len));
        }
        String s = sb.toString();
        //使用帮助类将xml接口的字符串装换成map
        Map<String, String> map = WXPayUtil.xmlToMap(s);
        if(map!=null && "success".equalsIgnoreCase(map.get("result_code"))){
            //支付成功
            //2.修改订单状态为“待发货/已支付”
            String orderId = map.get("out_trade_no");
            int i = orderService.updateOrderStatus(orderId, "2");
            System.out.println(i);
            System.out.println("--orderId:"+orderId);
            //3.通过websocket连接，向前端推送消息
            WebSocketServer.sendMsg(orderId,"1");

            //4.响应微信支付平台
            if(i>0){
                HashMap<String,String> resp = new HashMap<>();
                resp.put("return_code","success");
                resp.put("return_msg","OK");
                resp.put("appid",map.get("appid"));
                resp.put("result_code","success");
                return WXPayUtil.mapToXml(resp);
            }
        }

        return null;
    }
}
