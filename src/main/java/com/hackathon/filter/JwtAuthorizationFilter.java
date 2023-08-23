package com.hackathon.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hackathon.Config.auth.PrincipalDetails;
import com.hackathon.Diary.Repository.UserRepository;
import com.hackathon.Diary.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 시큐리티가 필터를 가지고 있는데 그 중 베이직어센티케이션 필터라는게 있음
// 권한이나 인증이 필요한 특정 주소를 요청 했을 때 무조건 타게 되어 있음
// 만약 권한이 인증이 필요한 주소가 아니라면 이 필터를 타지 않음
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    UserRepository userRepository;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }


    // 인증이나 권한이 필요한 주소요청시 해당 필터를 타게 됨
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("요청이 들어옴. jwt 토큰 검사");

        //super.doFilterInternal(request, response, chain);
        String jwtHeader = request.getHeader("Authorization");
        System.out.println("jwtHeader " + jwtHeader);

        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")){
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        System.out.println(jwtToken);
        String username = JWT.require(Algorithm.HMAC512("cos")).build().verify(jwtToken).getClaim("username").asString();
        if(username != null){ // null이 아니면 서명된것
            System.out.println("username " + username);
            User userEntity = userRepository.findByUsername(username);
            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
            System.out.println("================");
            System.out.println(principalDetails.getUser().getRoles());
            // jwt 토큰 서명을 통해서 서명이 정상이 면 Authentication 객체를 만들어준다.
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            System.out.println("authentication " + authentication.getName());
                    // 강제로 Authentication 객체 만들기 임의로 객체만 만들기 때문에 pw는 필요없음
                    // 지금은 username이 null이 아니기 때문에 만들 수 있는 것
            SecurityContextHolder.getContext().setAuthentication(authentication); // 강제로 시큐리티 세션에 접근하여 접근
            System.out.println("세션에 들어감? " + SecurityContextHolder.getContext().getAuthentication().getName());
            chain.doFilter(request,response);
        }
    }
}
