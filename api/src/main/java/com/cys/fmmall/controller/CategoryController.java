package com.cys.fmmall.controller;

import com.cys.fmmall.dao.CategoryMapper;
import com.cys.fmmall.service.CategoryService;
import com.cys.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/category")

@CrossOrigin
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @GetMapping("/detail/{cLevel}")

    public ResultVO getCategory(@PathVariable("cLevel") String cLevel){
        int level = Integer.parseInt(cLevel);
        return categoryService.findAllById(level);
    }
}
