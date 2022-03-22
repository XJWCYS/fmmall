package com.cys.fmmall.service;

import com.cys.fmmall.vo.ResultVO;

public interface UserAddrService {
    ResultVO listAddrsByUid(int userId);
    //根据addrId删除地址
    ResultVO delAddrsByAddrId(int userAddrId);
}
