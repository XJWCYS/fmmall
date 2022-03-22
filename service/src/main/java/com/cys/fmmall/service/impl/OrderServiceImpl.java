package com.cys.fmmall.service.impl;

import com.cys.fmmall.dao.OrderItemMapper;
import com.cys.fmmall.dao.OrdersMapper;
import com.cys.fmmall.dao.ProductSkuMapper;
import com.cys.fmmall.dao.ShoppingCartMapper;
import com.cys.fmmall.entity.*;
import com.cys.fmmall.service.OrderService;
import com.cys.fmmall.utils.PageHelper;
import com.cys.fmmall.vo.ResStatus;
import com.cys.fmmall.vo.ResultVO;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Transactional
    public Map<String,String> addOrder(String cids, Orders order) throws SQLException {
        HashMap<String, String> map = new HashMap<>();
        //1.校验库存：根据cids查询当前订单中关联的购物⻋记录详情（包括库存
        String[] arr = cids.split(",");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(Integer.parseInt(arr[i]));
        }
        List<ShoppingCartVO> shopCarts = shoppingCartMapper.selectShopcartByCids(list);
        boolean f = true;
        String untitled = "";

        //检验库存是否充足
        for (int i = 0; i < shopCarts.size(); i++) {
            if(Integer.parseInt(shopCarts.get(i).getCartNum())>shopCarts.get(i).getSkuStock()){
                f = false;
                break;
            }
            //获取所有商品名称，以,分割拼接成字符串
            if(i<shopCarts.size()-1){
                untitled = untitled + shopCarts.get(i).getProductName()+",";
            }else {
                untitled = untitled + shopCarts.get(i).getProductName();
            }
        }

        if(f){ //库存充足
            //添加订单
            order.setUntitled(untitled);
            order.setCreateTime(new Date());
            order.setStatus("1");
            String orderId = UUID.randomUUID().toString().replace("-", "");//⽣成订单编号
            order.setOrderId(orderId);
            ordersMapper.insert(order);

            for (ShoppingCartVO sc: shopCarts) {
                //添加商品快照
                int cnum = Integer.parseInt(sc.getCartNum());
                String itemId = System.currentTimeMillis()+""+ (new Random().nextInt(89999)+10000);//生成10000-99999之间的随机数
                OrderItem orderItem = new OrderItem(itemId,orderId,sc.getProductId(),sc.getProductName(),
                        sc.getProductImg(),sc.getSkuId(),sc.getSkuName(),new BigDecimal(sc.getSellPrice()),cnum,
                        new BigDecimal(sc.getSellPrice()*cnum),new Date(),new Date(),0);
                orderItemMapper.insert(orderItem);
            }
            for (ShoppingCartVO sc:shopCarts) {
                int cnum = Integer.parseInt(sc.getCartNum());
                //扣除库存
                String skuId =sc.getSkuId();
                int newStock = sc.getSkuStock()-cnum;
                ProductSku productSku = new ProductSku();
                productSku.setSkuId(skuId);
                productSku.setStock(newStock);
                productSkuMapper.updateByPrimaryKeySelective(productSku);
            }
            //删除购物车：当购物车记录购买成功之后，删除购物车中的对应数据
            for (int cid:list) {
                shoppingCartMapper.deleteByPrimaryKey(cid);
            }
            if(orderId != null){
                map.put("body",untitled); //商品描述
                map.put("out_trade_no",orderId); //使⽤当前⽤户订单的编号作为当前⽀付交易的交易号
                map.put("fee_type","CNY"); //⽀付币种
                map.put("total_fee",1+""); //⽀付⾦额
                map.put("trade_type","NATIVE"); //交易类型
                map.put("notify_url","http://cys.vaiwan.com/pay/callback");
            }
            return map;
        }else{
            return null;
        }

    }

    @Override
    public int updateOrderStatus(String orderId, String status) {
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setStatus(status);
        int i = ordersMapper.updateByPrimaryKeySelective(orders);
        return i;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)//用户并行使用时让其串行使用service
    public void closeOrder(String orderId) {
        synchronized (this){
            //  1.修改当前订单：status=6 已关闭  close_type=1 超时未支付
            Orders orders = new Orders();
            orders.setOrderId(orderId);
            orders.setStatus("6"); //关闭订单
            orders.setCloseType(1);//订单关闭类型  1：超时关闭
            ordersMapper.updateByPrimaryKeySelective(orders);
            //  2.还原库存：先根据当前订单编号查询商品快照（skuid  buy_count）--->修改product_sku
            Example example = new Example(OrderItem.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("orderId",orderId);
            List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
            for (int i = 0; i < orderItems.size(); i++) {
                OrderItem orderItem = orderItems.get(i);
                ProductSku productSku = productSkuMapper.selectByPrimaryKey(orderItem.getSkuId());
                productSku.setStock(productSku.getStock() + orderItem.getBuyCounts());
                productSkuMapper.updateByPrimaryKey(productSku);
            }
        }
    }

    @Override
    public ResultVO listOrders(String userId, String status, int pageNum, int limit) {
        //1.分页查询
        int start = (pageNum-1)*limit;
        List<OrdersVO> orders = ordersMapper.selectOrders(userId, status, start, limit);
        //2.查询总记录数
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        if(status != null && !"".equals(status)){
            criteria.andLike("status",status);
        }
        int count = ordersMapper.selectCountByExample(example);
        //计算页数
        int pageCount = count % limit==0 ? count/limit:count/limit+1;
        PageHelper<OrdersVO> pageHelper = new PageHelper<>(count,pageCount,orders);
        return new ResultVO(ResStatus.OK,"success",pageHelper);
    }

    @Override
    public ResultVO listAdminOrders(String status,int pageNum) {
        int limit = 6;
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andNotEqualTo("status",1);
        criteria.andNotEqualTo("status",1);
        int start = (pageNum-1)*6;
        List<OrdersVO> orders = ordersMapper.selectAdminOrders(status,start, limit);
        int count = ordersMapper.selectCountByExample(example);
        int pageCount = count % limit==0 ? count/limit:count/limit+1;
        PageHelper<OrdersVO> pageHelper = new PageHelper<>(count,pageCount,orders);

        return new ResultVO(ResStatus.OK,"success",pageHelper);
    }

    @Override
    public ResultVO fhAdminOrders(String orderId) {
        System.out.println(orderId);
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",orderId);
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setStatus("3");
        int i = ordersMapper.updateByPrimaryKeySelective(orders);
        if(i>0){
            return new ResultVO(ResStatus.OK,"success",null);
        }else{
            return new ResultVO(ResStatus.NO,"fail",null);
        }

    }


}
