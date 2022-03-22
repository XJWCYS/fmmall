package com.cys.fmmall.interceptor;

import com.cys.fmmall.vo.ResStatus;
import com.cys.fmmall.vo.ResultVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CheckTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if("OPTIONS".equalsIgnoreCase(method)){
            return true;
        }
        String token = request.getHeader("token");
        System.out.println("tookenInterceptor"+token);
        if(token == null){
            ResultVO vo = new ResultVO(ResStatus.LOGIN_FAIL_NOT,"请先登录！",null);
            doResponse(response,vo);
        }else{
            try {
                //验证token
                JwtParser parser = Jwts.parser();
                parser.setSigningKey("cysxjw");//解析token的SigningKey必须和⽣成token时设置密码⼀致
                //如果token正确（密码正确，有效期内）则正常执⾏，否则抛出异常
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
                return true;
            }catch (ExpiredJwtException e){
                ResultVO vo =new ResultVO(ResStatus.LOGIN_FAIL_OVERDUE,"登录过期，请重新登录！",null);
                doResponse(response,vo);
            }catch (UnsupportedJwtException e){
                ResultVO vo =  new ResultVO(ResStatus.NO,"Token不合法，请⾃重！",null);
                doResponse(response,vo);
            }catch (Exception e){
                ResultVO vo = new ResultVO(ResStatus.LOGIN_FAIL_NOT,"请先登录！",null);
                doResponse(response,vo);
            }
        }
        return false;
    }
    private void doResponse(HttpServletResponse response,ResultVO vo) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(vo);
        out.print(s);
        out.flush();
        out.close();
    }
}
