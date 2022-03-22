package com.cys.fmmall.service.impl;

import com.cys.fmmall.dao.CategoryMapper;
import com.cys.fmmall.entity.Category;
import com.cys.fmmall.entity.CategoryVO;
import com.cys.fmmall.service.CategoryService;
import com.cys.fmmall.vo.ResStatus;
import com.cys.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public ResultVO listCategories() {
        List<CategoryVO> categoryVO = categoryMapper.selectAllCategories1(0);
        return new ResultVO(ResStatus.OK,"success",categoryVO);
    }

    @Override
    public ResultVO listFirstLevelCategories() {
        List<CategoryVO> categoryVO = categoryMapper.selectFirstLevelCategories();
        return new ResultVO(ResStatus.OK,"success",categoryVO);
    }

    @Override
    public ResultVO findAllById(int categoryLevel) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryLevel",categoryLevel);
        List<Category> categories = categoryMapper.selectByExample(example);
        return new ResultVO(ResStatus.OK,"success",categories);
    }


}
