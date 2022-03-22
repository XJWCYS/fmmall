package com.cys.fmmall.service.impl;

import com.cys.fmmall.dao.AdminMapper;
import com.cys.fmmall.entity.Admin;
import com.cys.fmmall.service.AdminService;
import com.cys.fmmall.vo.ResStatus;
import com.cys.fmmall.vo.ResultVO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public ResultVO adminLogin(String username, String password) {
        Example example = new Example(Admin.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        criteria.andEqualTo("password",password);
        List<Admin> admins = adminMapper.selectByExample(example);
        if(admins.size()>0){
            JwtBuilder builder = Jwts.builder();
            HashMap<String,Object> map = new HashMap<>();
            map.put("username",username);
            map.put("password",password);
            String token = builder.setSubject(username)  //主题，就是token中携带的数据
                    .setIssuedAt(new Date())     //设置token的生成时间
                    .setId(admins.get(0).getUserid() + "")//设置用户id为token  id
                    .setClaims(map)             //map中可以存放用户的角色权限信息
                    .setExpiration(new Date(System.currentTimeMillis()+24*60*60*1000))
                    .signWith(SignatureAlgorithm.HS256,"cysxjw")
                    .compact();
            return new ResultVO(ResStatus.OK,token,admins.get(0));
        }else {
            return new ResultVO(ResStatus.NO,"fail",null);
        }
    }
}
