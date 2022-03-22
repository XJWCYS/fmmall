package com.cys.fmmall.service.impl;

import com.cys.fmmall.dao.UserAddrMapper;
import com.cys.fmmall.entity.UserAddr;
import com.cys.fmmall.service.UserAddrService;
import com.cys.fmmall.vo.ResStatus;
import com.cys.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserAddrServiceImpl implements UserAddrService {
    @Autowired
    private UserAddrMapper userAddrMapper;
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVO listAddrsByUid(int userId) {
        Example example = new Example(UserAddr.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("status",1);
        List<UserAddr> userAddrs = userAddrMapper.selectByExample(example);
        ResultVO resultVO = new ResultVO(ResStatus.OK, "success", userAddrs);
        return resultVO;
    }

    @Override
    public ResultVO delAddrsByAddrId(int userAddrId) {
        int i = userAddrMapper.deleteByPrimaryKey(userAddrId);
        if(i!=0){
            return new ResultVO(ResStatus.OK,"success",null);
        }else{
            return new ResultVO(ResStatus.NO,"fail",null);
        }

    }
}
