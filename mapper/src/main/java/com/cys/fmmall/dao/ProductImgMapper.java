package com.cys.fmmall.dao;

import com.cys.fmmall.entity.ProductImg;
import com.cys.fmmall.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductImgMapper extends GeneralDao<ProductImg> {
    List<ProductImg> selectProductImgByProductId(int productId);
}