package com.cys.fmmall.service.impl;

import com.cys.fmmall.dao.UsersMapper;
import com.cys.fmmall.entity.Users;
import com.cys.fmmall.service.UserService;
import com.cys.fmmall.utils.MD5Utils;
import com.cys.fmmall.vo.ResStatus;
import com.cys.fmmall.vo.ResultVO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;
    @Transactional
    @Override
    public ResultVO userResgit(String username, String password) {
        //锁
        synchronized (this){
        //查询用户是否被注册
            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username",username);
            List<Users> users = usersMapper.selectByExample(example);

            //2.如果没有被注册则进⾏保存操作
            if(users.size() == 0){
                String md5Pwd = MD5Utils.md5(password);
                Users user = new Users();

                user.setUsername(username);
                user.setPassword(md5Pwd);
                user.setUserImg("img/default.png");
                user.setUserRegtime(new Date());
                user.setUserModtime(new Date());
                int i = usersMapper.insertUseGeneratedKeys(user);
                if(i>0){
                    return new ResultVO(ResStatus.OK,"注册成功",user);
                }else{
                    return new ResultVO(ResStatus.NO,"注册失败",null);
                }
            }else{
                return new ResultVO(ResStatus.NO,"用户名已存在",null);
            }
            }

    }

    @Override
    public ResultVO checkLogin(String username, String password) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        List<Users> users = usersMapper.selectByExample(example);
        if(users.size() == 0){
            return new ResultVO(ResStatus.NO,"用户名错误",null);
        }else{
            String md5Pwd = MD5Utils.md5(password);
            if(md5Pwd.equals(users.get(0).getPassword())){
                //如果登录验证成功，则需要生成令牌token（token就是按照特定规则生成的字符串）
                //使用jwt规则生成token字符串
                JwtBuilder builder = Jwts.builder();
                HashMap<String,Object> map = new HashMap<>();
                map.put("username",username);
                map.put("password",password);
                String token = builder.setSubject(username)  //主题，就是token中携带的数据
                        .setIssuedAt(new Date())     //设置token的生成时间
                        .setId(users.get(0).getUserId() + "")//设置用户id为token  id
                        .setClaims(map)             //map中可以存放用户的角色权限信息
                        .setExpiration(new Date(System.currentTimeMillis()+24*60*60*1000))
                        .signWith(SignatureAlgorithm.HS256,"cysxjw")
                        .compact();
                return new ResultVO(ResStatus.OK,token,users.get(0));
            }else{
                return new ResultVO(ResStatus.NO,"密码错误",null);
            }
        }
    }

    @Override
    public ResultVO updatePassword(Integer userId,String newPassword, String oldPassword) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        List<Users> users = usersMapper.selectByExample(example);
        String password = users.get(0).getPassword();
        String md5Pwd = MD5Utils.md5(oldPassword);
        if(md5Pwd.equals(password)){
            Users user = new Users();
            String md5Pwd1 = MD5Utils.md5(newPassword);
            user.setUserId(userId);
            user.setPassword(md5Pwd1);
            int i = usersMapper.updateByPrimaryKeySelective(user);
            if(i>0){
                return new ResultVO(ResStatus.OK,"success",null);
            }else{
                return new ResultVO(ResStatus.NO,"未知错误",null);
            }
        }else{
            return new ResultVO(ResStatus.NO,"密码错误",null);
        }

    }
}
