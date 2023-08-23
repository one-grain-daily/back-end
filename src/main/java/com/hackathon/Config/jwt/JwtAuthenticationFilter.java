package com.hackathon.Config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.Config.auth.PrincipalDetails;
import com.hackathon.Diary.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

// 이 필터는 /login 요청을 해서 username, password를 포스트로 전송하면 이 필터가 동작을 한다.
// 그러나 formlogin을 디스에이블하여 작동하지 않음. 작동시켜야함
// 이 필터를 security config에 적용시킨다.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // 로그인 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("로그인 시도중");
//        try {
//            BufferedReader br = request.getReader();
//            String input = null;
//            while((input = br.readLine()) != null){
//                System.out.println(input);
//            }
//
//            System.out.println(request.getInputStream());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        ObjectMapper om = new ObjectMapper(); // JSON 데이터를 파싱해줌
        try {
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            // 위가 실행되면 principaldetail service에 loadUSerByusername 함수 실행
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println(principalDetails.getUser().getUsername()); // 이게 나오면 어쏀틱케이션 객체가 세션영역에 저장되었다는 뜻
            return authentication;
            // 리턴 될 때 세션에 저장됨. authentication 객체가 세션 영역에 저장되어야 하고 그 방법이 return 해줌.
            // 리턴의 이유는 권한 관리를 위해서 시큐리티가 해주기 때문임. jwt를 사용하면 굳이 세션을 만들 필요가 없음 사실은.


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 1. username, password 받아서
        // 2. 로그인 시도. authenticationmanager로 로인 시도를 하면! PrincipalDetail 실행
        // 3. principalDetails를 세션에 담고 세션에 안담으면 권한관리가 안됨..
        // 4. jwt 토큰을 만들어서 응답해줌
        // 여기서 아이디 비밀번호 확인

    }
    // attempAuthentication 실행 후 인증이 정상이면 아래가 실행된다. 여기서 JWT를 만들어서 request 요청한 사용자에게 jwt 토큰을 리스폰스 해주면 됨 !
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("인증완료!");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
    }
}
