package com.cys.fmmall.service;

import com.cys.fmmall.vo.ResultVO;

public interface AdminService {
    ResultVO adminLogin(String username,String password);
}
