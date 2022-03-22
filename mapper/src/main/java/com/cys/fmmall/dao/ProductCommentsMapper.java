package com.cys.fmmall.dao;

import com.cys.fmmall.entity.ProductComments;
import com.cys.fmmall.entity.ProductCommentsVO;
import com.cys.fmmall.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductCommentsMapper extends GeneralDao<ProductComments> {
    List<ProductCommentsVO> selectCommentsByProductId(@Param("productId") String productId,
                                                      @Param("start") int start,
                                                      @Param("limit")int limit);
}