package com.cys.fmmall.service;

import com.cys.fmmall.vo.ResultVO;

public interface CategoryService {
    ResultVO listCategories();
    ResultVO listFirstLevelCategories();
    ResultVO findAllById(int categoryLevel);
}
