package com.cys.fmmall.service;

import com.cys.fmmall.entity.ProductVO;
import com.cys.fmmall.vo.ResultVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductService {

    ResultVO listRecommendProducts();
    ResultVO getProductBasicInfo(String productId);
    ResultVO getProductParamsById(String productId);
    //根据三级分类id查询商品并分页
    ResultVO selectProductByCategoryId(int categoryId,int pageNum,int limit);
    //根据三级分类查询品牌
    ResultVO selectBrandByCategoryId(int categoryId);
    //根据关键词查询商品
    ResultVO selectProductByKeyword(String kw,int pageNum,int limit);
    //根据关键词查询品牌
    ResultVO selectBrandByKeyword(String kw);
    //根据关键词查询品牌
    ResultVO findAll(int pageNum);
    //下架商品
    ResultVO delProduct(String productId);
}
