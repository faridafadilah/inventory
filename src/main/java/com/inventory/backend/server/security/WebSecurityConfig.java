package com.inventory.backend.server.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.inventory.backend.server.interceptor.LoggerInterceptor;
import com.inventory.backend.server.security.jwt.AuthEntryPointJwt;
import com.inventory.backend.server.security.jwt.AuthTokenFilter;
import com.inventory.backend.server.security.services.UserDetailsServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig implements WebMvcConfigurer {
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/uploads/**")
        .addResourceLocations("file:uploads\\");
    registry.addResourceHandler("/imageUser/**")
        .addResourceLocations("file:imageUser\\");
    registry.addResourceHandler("/imageSampul/**")
        .addResourceLocations("file:imageSampul\\");
  }

  @Autowired
  private LoggerInterceptor loggingInterceptor;

  // @Value("${cors.allowed-origins}")
  // private String allowedOrigins;

  @Bean
  public AuthTokenFilter authenticationJwTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests().antMatchers("/addAdmin").permitAll().and()
        .authorizeRequests().antMatchers("/imageSampul/**").permitAll().and()
        .authorizeRequests().antMatchers("/uploads/**").permitAll().and()
        .authorizeRequests().antMatchers("/addList").permitAll().and()
        .authorizeRequests().antMatchers("/imageUser/**").permitAll().and()
        .authorizeRequests().antMatchers("/deleteList/**").permitAll().and()
        .authorizeRequests().antMatchers("/getLength").permitAll().and()
        .authorizeRequests().antMatchers("/getList").permitAll().and()
        .authorizeRequests().antMatchers("/getList/**").permitAll().and()
        .authorizeRequests().antMatchers("/updateList/**").permitAll().and()
        .authorizeRequests().antMatchers("/addCategory").permitAll().and()
        .authorizeRequests().antMatchers("/deleteCategory/**").permitAll().and()
        .authorizeRequests().antMatchers("/getCategory/**").permitAll().and()
        .authorizeRequests().antMatchers("/getCategorys").permitAll().and()
        .authorizeRequests().antMatchers("/updateCategory/**").permitAll().and()
        .authorizeRequests().antMatchers("/addFlow").permitAll().and()
        .authorizeRequests().antMatchers("/deleteFlow/**").permitAll().and()
        .authorizeRequests().antMatchers("/getFlow/**").permitAll().and()
        .authorizeRequests().antMatchers("/getFlows").permitAll().and()
        .authorizeRequests().antMatchers("/getFlows/**").permitAll().and()
        .authorizeRequests().antMatchers("/updateFlow/**").permitAll().and()
        .authorizeRequests().antMatchers("/getUser/**").permitAll().and()
        .authorizeRequests().antMatchers("/updateProfile/**").permitAll().and()
        .authorizeRequests().antMatchers("/userRole/").permitAll().and()
        .authorizeRequests().antMatchers("/userRole/**").permitAll().and()
        .authorizeRequests().antMatchers("/userRole/role/**").permitAll().and()
        .authorizeRequests().antMatchers("/addSuplier").permitAll().and()
        .authorizeRequests().antMatchers("/deleteSuplier").permitAll().and()
        .authorizeRequests().antMatchers("/getSuplier/**").permitAll().and()
        .authorizeRequests().antMatchers("/getSuliers").permitAll().and()
        .authorizeRequests().antMatchers("/updateSuplier/**").permitAll().and()
        .authorizeRequests().antMatchers("/signin").permitAll().and()
        .authorizeRequests().antMatchers("/log").permitAll().and()
        .authorizeRequests().antMatchers("/reset-password").permitAll().and()
        .authorizeRequests().antMatchers("/forgot-password").permitAll().and()
        .authorizeRequests().antMatchers("/api/profile/**").permitAll()
        .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs", "/webjars/**").permitAll()
        .anyRequest().authenticated();

    http.authenticationProvider(authenticationProvider());
    http.addFilterBefore(authenticationJwTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loggingInterceptor);
  }
}