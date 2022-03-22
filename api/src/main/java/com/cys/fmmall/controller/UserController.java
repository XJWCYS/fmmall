package com.cys.fmmall.controller;

import com.cys.fmmall.entity.Users;
import com.cys.fmmall.service.UserService;
import com.cys.fmmall.vo.ResStatus;
import com.cys.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Api(value = "进行个人信息的增删改查",tags = "用户管理")
@CrossOrigin
public class UserController {
    @Resource
    private UserService userService;
    @GetMapping("/login")
    @ApiOperation("⽤户登录接⼝")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "username", value = "⽤户登录账号",required = true),
            @ApiImplicitParam(dataType = "string",name = "password", value = "⽤户登录密码",required = true)
    })
    public ResultVO login(@RequestParam(value = "username") String username,
                          @RequestParam(value = "password") String password){
        return userService.checkLogin(username,password);
    }
    @PostMapping("/regist")
    @ApiOperation("⽤户注册接⼝")
    public ResultVO regist(@RequestBody Users users){
        return userService.userResgit(users.getUsername(),users.getPassword());
    }
    @GetMapping("/check")
    @ApiOperation("⽤户注册接⼝")
    public ResultVO check(@RequestHeader String token){
        return new ResultVO(ResStatus.OK,"success",null);
    }
    @PostMapping("/update")
    @ApiOperation("⽤户修改密码接⼝")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "integer",name = "userId", value = "⽤户id",required = true),
            @ApiImplicitParam(dataType = "string",name = "newPassword", value = "⽤户登录密码",required = true),
            @ApiImplicitParam(dataType = "string",name = "oldPassword", value = "⽤户新登录密码",required = true)
    })
    public ResultVO update(@RequestHeader String token,
                            @RequestParam(value = "userId") Integer userId,
                            @RequestParam(value = "newPassword") String newPassword,
                            @RequestParam(value = "oldPassword") String oldPassword){
        return userService.updatePassword(userId,newPassword,oldPassword);
    }
}
