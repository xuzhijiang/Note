package com.itheima.security.distributed.order.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itheima.security.distributed.order.common.EncryptUtil;
import com.itheima.security.distributed.order.model.UserDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 微服务可以拿到 网关解析后的 明文token(明文token中包含登录用户的身份和权限信息)后也需要做两件事：
 *
 * （1）用户授权拦截（看当前用户访问的controller 是否有权访问该资源）
 * （2）将用户信息存储进当前线程上下文（有利于后续业务逻辑随时获取当前用户信息）
 *      就是在filter中把明文token中的用户信息 封装到 UsernamePasswordAuthenticationToken
 *      ,方便其他地方获取
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 解析出头中的token
        String token = httpServletRequest.getHeader("json-token");
        if(token!=null){
            // 解码base64
            String json = EncryptUtil.decodeUTF8StringBase64(token);
            //将token转成json对象
            JSONObject jsonObject = JSON.parseObject(json);
            //用户身份信息
//            UserDTO userDTO = new UserDTO();
//            String principal = jsonObject.getString("principal");
//            userDTO.setUsername(principal);
            UserDTO userDTO = JSON.parseObject(jsonObject.getString("principal"), UserDTO.class); // key的名字要和网关中的一致
            //用户权限
            JSONArray authoritiesArray = jsonObject.getJSONArray("authorities"); // key的名字要和网关中的一致
            String[] authorities = authoritiesArray.toArray(new String[authoritiesArray.size()]);

            //将用户信息和权限填充 到用户身份token对象中(这个对象spring security认识)
            // 这个很重要: 封装到UsernamePasswordAuthenticationToken之后,设置到安全上下文中,其他地方就可以获取用户信息了.
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userDTO,null, AuthorityUtils.createAuthorityList(authorities));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            //将authenticationToken填充到安全上下文
            // 这样security就可以按照他自己的方式解析权限等信息.(解析访问controller是否有权限,如果有权限就进入controller的某个方法,如果没有权限就直接拒绝)

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
