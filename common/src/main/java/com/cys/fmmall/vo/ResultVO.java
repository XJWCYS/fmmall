package com.cys.fmmall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ResultVO对象",description = "返回的数据信息")
public class ResultVO {
    @ApiModelProperty(value = "状态码",dataType = "int")
    //响应给前端的状态码
    private int code;
    @ApiModelProperty(value = "提示信息",dataType = "string")
    //响应给前端的提示信息
    private String msg;
    @ApiModelProperty(value = "返回的用户信息",dataType = "Object")
    //相应给前端的数据
    private Object data;
}
