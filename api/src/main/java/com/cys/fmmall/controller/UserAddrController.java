package com.cys.fmmall.controller;

import com.cys.fmmall.service.UserAddrService;
import com.cys.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/useraddr")
@CrossOrigin
@Api(value = "用户地址查询",tags = "地址管理")
public class UserAddrController {
    @Autowired
    private UserAddrService userAddrService;
    @GetMapping("/list")
    @ApiOperation("地址列表信息接口")
    @ApiImplicitParam(dataType = "int",name = "userId", value = "用户ID",required = true)
    public ResultVO listAddrsByUid(Integer userId, @RequestHeader String token){
        return userAddrService.listAddrsByUid(userId);
    }
    @DeleteMapping("/del/{userAddrId}")
    @ApiOperation("删除地址")
    @ApiImplicitParam(dataType = "int",name = "userAddrId", value = "地址ID",required = true)
    public ResultVO delAddrsByAddrId(@PathVariable("userAddrId") Integer userAddrId, @RequestHeader String token){
        return userAddrService.delAddrsByAddrId(userAddrId);
    }
}
