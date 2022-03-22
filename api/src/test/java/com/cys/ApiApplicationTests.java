package com.cys;

import com.cys.fmmall.dao.CategoryMapper;
import com.cys.fmmall.dao.ProductMapper;
import com.cys.fmmall.entity.CategoryVO;
import com.cys.fmmall.entity.ProductImg;
import com.cys.fmmall.entity.ProductVO;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
class ApiApplicationTests {
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ProductMapper productMapper;

    @Test
    void contextLoads() {
        List<CategoryVO> categoryVOS = categoryMapper.selectAllCategories1(0);
        for (CategoryVO categoryVO:categoryVOS) {
            System.out.println(categoryVO);
            for (CategoryVO categoryVO1:categoryVO.getCategories()) {
                System.out.println(categoryVO1);
                for (CategoryVO categoryVO2:categoryVO1.getCategories()) {
                    System.out.println(categoryVO2);
                }
            }
        }
    }
    @Test
    void test1(){
        List<ProductVO> productVOS = productMapper.selectRecommendProducts();
        for (ProductVO vo:productVOS) {
            System.out.println(vo);
        }
    }
    @Test
    void test2(){
        List<CategoryVO> categoryVOS = categoryMapper.selectFirstLevelCategories();
        for (CategoryVO categoryVO:categoryVOS) {
            System.out.println(categoryVO);
            for (ProductVO vo:categoryVO.getProducts()) {
                System.out.println(vo);
                for (ProductImg imgs:vo.getImgs()) {
                    System.out.println(imgs);
                }
            }
        }
    }

}
