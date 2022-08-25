package com.sparta.clonecoding_unite_00.security;




import com.sparta.clonecoding_unite_00.jwt.AccessDeniedHandlerException;
import com.sparta.clonecoding_unite_00.jwt.AuthenticationEntryPointException;
import com.sparta.clonecoding_unite_00.jwt.TokenProvider;
import com.sparta.clonecoding_unite_00.jwt.configuration.JwtSecurityConfiguration;
import com.sparta.clonecoding_unite_00.security.userdetail.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class  SecurityConfiguration{

  @Value("${jwt.secret}")
  String SECRET_KEY;
  private final TokenProvider tokenProvider;
  private final UserDetailsServiceImpl userDetailsService;
  private final AuthenticationEntryPointException authenticationEntryPointException;
  private final AccessDeniedHandlerException accessDeniedHandlerException;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
    return (web) -> web.ignoring()
            .antMatchers(
                    "/h2-console/**"
                    ,"/favicon.ico"
                    ,"/error"
            )
            .antMatchers("/v2/api-docs", "/configuration/ui/"
            , "/swagger-resources", "/configuration/security"
            , "swagger-ui.html", "/webjars/**", "/swagger/**");
  }

  @Bean
  @Order(SecurityProperties.BASIC_AUTH_ORDER)
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.httpBasic().disable(); //기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.

    http.cors();
    // jwt token으로 인증할것이므로 세션필요없으므로 생성안함.

    // X-Frame-Options 를 비활성화 하는 설정이다. 그러나 보안적인 이슈가 발생할 수 있다.
    http.headers().frameOptions().disable();

    http.csrf().disable()

        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPointException)
        .accessDeniedHandler(accessDeniedHandlerException)

        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
        .authorizeRequests()
        .antMatchers("/member/**").permitAll()
        .antMatchers("/video/search").permitAll()
        .antMatchers("/images/**").permitAll()
        .antMatchers("/css/**").permitAll()

        // 첫화면이 로그인 화면이 아니기때문에 권한 헤제하기.
//        .antMatchers("/").permitAll()
//        .antMatchers("/**").permitAll()

        .anyRequest().authenticated()

        .and()
        .apply(new JwtSecurityConfiguration(SECRET_KEY, tokenProvider, userDetailsService));

    return http.build();
  }
}
