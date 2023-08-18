package com.hackathon.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter2 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        req.setCharacterEncoding("UTF-8");
        // 토큰을 만들었다고 가정. 이름은 cos라는 토큰이 들어오면 인증. 아니면 인증거부할것!
        // id, pw가 정상적으로 들어와 로그인이 완료되면 토큰을 만들어주고 그걸 응답해준다.
        // 그떄 넘어온 토큰이 내가 만든 토큰이 맞는지만 검증하면 됨
        if (req.getMethod().equals("POST")) {
            System.out.println("포스트요청");
            String headerAuth = req.getHeader("Authorization");
            System.out.println(headerAuth);

            if (headerAuth.equals("cos"))
                chain.doFilter(request, response); // 필터를 진행 후 다시 프로그램을 넘겨줌
            else {
                PrintWriter out = res.getWriter();
                out.println("인증안됨");
            }
        }else{
            chain.doFilter(request, response);
        }
    }
}