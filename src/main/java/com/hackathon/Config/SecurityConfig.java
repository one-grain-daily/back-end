package com.hackathon.Config;

import com.hackathon.Config.jwt.JwtAuthenticationFilter;
import com.hackathon.Repository.UserRepository;
import com.hackathon.filter.JwtAuthorizationFilter;
import com.hackathon.filter.MyFilter2;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 이것이 필터. 이것이 기본 필터 체인에 등록이 됨.
    @Bean // 해당 메서드의 리턴되는 오브젝트를 IoC에 등록
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }
    private final UserRepository userRepository;
    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.addFilterBefore(new MyFilter2(), BasicAuthenticationFilter.class); // 이대로하면 오류남. 시큐리티 필터만 등록가능함 그러므로 addFilterbefor, after 등으로 걸어야함
        //단 굳이 시큐리티에 걸 필욘없음
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않겠다. 웹은 원래 스테이트리스인데 스테이트풀처름 쓰는게 세션 쿠기었음
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // 로그인을 진행할 필터이기 때문에 매니져가 필요함 Cross : 필터 인증 X 이렇게하면 필터인증 O
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
                .formLogin().disable() // 로그인 폼을 쓰지 않기 때문에 .login을 실행시 킬 필터가 필요하다!
                .httpBasic().disable() // 기본적인 http 방식을 사용하지 않겠다.
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();
    }
}

