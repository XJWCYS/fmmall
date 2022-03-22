package com.cys.fmmall.dao;

import com.cys.fmmall.entity.Product;
import com.cys.fmmall.entity.ProductVO;
import com.cys.fmmall.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductMapper extends GeneralDao<Product> {
    //查询推荐商品   按照最新上线来查询
    List<ProductVO> selectRecommendProducts();
    //查询销量前六的商品
    List<ProductVO> selectTop6ByCategroy(int cid);
    //根据三级分类分页查询商品数据

    List<ProductVO> selectProductByCategoryId(@Param("cid") int cid,
                                              @Param("start") int start,
                                              @Param("limit") int limit);

    //根据三级分类id查询品牌
    List<String> selectBrandByCategoryId(int cid);

    //根据搜索关键字分页查询商品数据
    List<ProductVO> selectProductByKeyword(@Param("kw") String kw,
                                              @Param("start") int start,
                                              @Param("limit") int limit);
    //根据三级分类id查询品牌
    List<String> selectBrandByKeyword(String kw);

}