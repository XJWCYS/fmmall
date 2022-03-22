package com.cys.fmmall.service;

import com.cys.fmmall.vo.ResultVO;

public interface UserService {
    //注册
    ResultVO userResgit(String username,String password);
    //登录
    ResultVO checkLogin(String username,String password);
    //修改密码
    ResultVO updatePassword(Integer userID,String newPassword,String oldPassword);
}
