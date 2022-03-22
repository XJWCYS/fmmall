package com.cys.fmmall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductVO {
    private String productId;
    private String productName;
    private Integer categoryId;
    private Integer rootCategoryId;
    private Integer soldNum;
    private Integer productStatus;
    private Date createTime;
    private Date updateTime;
    private String content;
    private List<ProductImg> imgs;
    private List<ProductSku> skus;

}