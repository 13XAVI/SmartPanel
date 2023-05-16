package com.SmartPannel.userData.JWT;

import com.SmartPannel.userData.Model.Roles;
import com.SmartPannel.userData.Model.Users;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class jwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private jwtTokenApi tokenApi;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/auth/Register")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (requestURI.equals("/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (requestURI.equals("/HomePage")) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
                System.out.println("Auth Header: "+header);

               if (!hasAuthorizationHeader(request)){
                   filterChain.doFilter(request,response);
                   return;
               }
                String accessToken = getAccessToken(request);

               if(!tokenApi.ValidateAccToken(accessToken)){
                   filterChain.doFilter(request,response);
                   return;
               }
               setAuthenticationContext(accessToken,request);
                filterChain.doFilter(request,response);
               

    }

    private void setAuthenticationContext(String accessToken, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(accessToken);
        UsernamePasswordAuthenticationToken authenticationToken  =new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private UserDetails getUserDetails(String accessToken) {
        Users userDetails = new Users();
        Claims claims = tokenApi.parseClaims(accessToken);

        List<String> roleNames = (List<String>) claims.get("roles");
        for (String roleName : roleNames) {
            userDetails.addRole(new Roles(roleName));
        }

        String subject = (String)claims.get(Claims.SUBJECT);
        String[] subjectArr = subject.split(",");
        if (subjectArr.length >= 2) {
            userDetails.setId(Long.parseLong(subjectArr[0]));
            userDetails.setEmail(subjectArr[1]);
        } else {
            System.out.println(userDetails.getId()+ userDetails.getEmail()) ;
        }
        return userDetails;
    }


    private boolean hasAuthorizationHeader(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        System.out.println("Auth Header: "+ header);
        if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")){
            return false;
        }

            return true;
    }

    private String getAccessToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        System.out.println("Access Token:"+token);
        return token;

    }

}
