package com.cys.fmmall.controller;

import com.cys.fmmall.service.AdminService;
import com.cys.fmmall.service.UserService;
import com.cys.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    @Resource
    private AdminService adminService;
    @GetMapping("/login")

    public ResultVO login(@RequestParam(value = "username") String username,
                          @RequestParam(value = "password") String password){
        return adminService.adminLogin(username,password);
    }
}
