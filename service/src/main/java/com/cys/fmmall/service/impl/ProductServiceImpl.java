package com.cys.fmmall.service.impl;

import com.cys.fmmall.dao.*;
import com.cys.fmmall.entity.*;
import com.cys.fmmall.service.ProductService;
import com.cys.fmmall.utils.PageHelper;
import com.cys.fmmall.vo.ResStatus;
import com.cys.fmmall.vo.ResultVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImgMapper productImgMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductParamsMapper productParamsMapper;

    @Override
    public ResultVO listRecommendProducts() {
        List<ProductVO> productVOs = productMapper.selectRecommendProducts();
        ResultVO resultVO = new ResultVO(ResStatus.OK, "success", productVOs);
        return resultVO;
    }

    @Override
    public ResultVO getProductBasicInfo(String productId) {
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        criteria.andEqualTo("productStatus",1);
        //criteria.andEqualTo("productStatus",1);
        List<Product> products = productMapper.selectByExample(example);
        if(products.size()>0){
            Example example1 = new Example(ProductImg.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("itemId",productId);
            List<ProductImg> productImgs = productImgMapper.selectByExample(example1);
            Example example2 = new Example(ProductSku.class);
            Example.Criteria criteria2 = example2.createCriteria();
            criteria2.andEqualTo("productId",productId);
            criteria2.andEqualTo("status",1);
            List<ProductSku> productSkus = productSkuMapper.selectByExample(example2);
            HashMap<String,Object> basicInfo = new HashMap<>();
            basicInfo.put("product",products.get(0));
            basicInfo.put("productImgs",productImgs);
            basicInfo.put("productSkus",productSkus);
            return new ResultVO(ResStatus.OK, "success",basicInfo);
        }else{
            return new ResultVO(ResStatus.NO, "不存在该商品",null);
        }
    }

    @Override
    public ResultVO getProductParamsById(String productId) {
        Example example = new Example(ProductParams.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        List<ProductParams> productParams = productParamsMapper.selectByExample(example);
        if(productParams.size()>0){
            return new ResultVO(ResStatus.OK, "success",productParams.get(0));
        }else{
            return new ResultVO(ResStatus.NO, "三无产品",null);
        }

    }

    @Override
    public ResultVO selectProductByCategoryId(int categoryId, int pageNum, int limit) {
        //1.查询分页数据
        int start = (pageNum-1)*limit;
        List<ProductVO> productVOs = productMapper.selectProductByCategoryId(categoryId, start, limit);
        //2.查询当前类别下的商品的总记录数
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryId",categoryId);
        int count = productMapper.selectCountByExample(example);
        //3.计算总页数
        int pageCount = count%limit==0? count/limit : count/limit+1;
        //4.封装返回数据
        PageHelper<ProductVO> productVOPageHelper = new PageHelper<>(count, pageCount, productVOs);
        return new ResultVO(ResStatus.OK,"success",productVOPageHelper);
    }

    @Override
    public ResultVO selectBrandByCategoryId(int categoryId) {
        List<String> brands = productMapper.selectBrandByCategoryId(categoryId);
        return new ResultVO(ResStatus.OK,"success",brands);
    }

    @Override
    public ResultVO selectProductByKeyword(String kw, int pageNum, int limit) {
        //1.查询分页数据
        kw = "%"+kw+"%";
        int start= (pageNum-1)*limit;
        List<ProductVO> productVOS = productMapper.selectProductByKeyword(kw, start, limit);
        //2.查询当前类别下的商品的总记录数
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("productName",kw);
        int count = productMapper.selectCountByExample(example);
        //3.计算总页数
        int pageCount = count%limit==0 ? count/limit:count/limit+1;
        //4.封装返回数据
        PageHelper<ProductVO> productVOPageHelper = new PageHelper<>(count, pageCount, productVOS);
        return new ResultVO(ResStatus.OK,"success",productVOPageHelper);
    }

    @Override
    public ResultVO selectBrandByKeyword(String kw) {
        kw = "%"+kw+"%";
        List<String> brands = productMapper.selectBrandByKeyword(kw);
        return new ResultVO(ResStatus.OK,"success",brands);
    }

    @Override
    public ResultVO findAll(int pageNum) {
        //分⻚查询
        int pageSize = 6;
        int start = (pageNum-1)*pageSize;
        RowBounds rowBounds = new RowBounds(start,pageSize);
        List<Product> products = productMapper.selectByRowBounds(new Product(),rowBounds);
        List<Product> products1 = productMapper.selectAll();
        int count = products1.size();
        int pageCount = count%6==0 ? count/6:count/6+1;
        PageHelper<Product> productPageHelper = new PageHelper<>(count,pageCount,products);
        return new ResultVO(ResStatus.OK,"success",productPageHelper);
    }

    @Override
    public ResultVO delProduct(String productId) {
        Product product = new Product();
        product.setProductId(productId);
        product.setProductStatus(0);
        int i = productMapper.updateByPrimaryKeySelective(product);
        if(i>0){
            return new ResultVO(ResStatus.OK,"success",null);
        }else{
            return new ResultVO(ResStatus.NO,"fail",null);
        }

    }


}
