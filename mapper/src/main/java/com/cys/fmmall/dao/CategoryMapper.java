package com.cys.fmmall.dao;

import com.cys.fmmall.entity.Category;
import com.cys.fmmall.entity.CategoryVO;
import com.cys.fmmall.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryMapper extends GeneralDao<Category> {
    //连接查询
    List<CategoryVO> selectAllCategories();
    //子查询
    List<CategoryVO> selectAllCategories1(int parentId);
    //查询一级类别
    List<CategoryVO> selectFirstLevelCategories();
    //查询二级类别
    List<Category> selectAllTwoCategories(int parentId);
}