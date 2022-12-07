package com.vskkkkkshablon.config;


import com.vskkkkkshablon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserService userService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService);
  }

  @Bean
  protected BCryptPasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .exceptionHandling().accessDeniedPage("/403")
        .and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/details").hasAnyRole("ADMIN","USER")
        .antMatchers(HttpMethod.POST,"/saveitem", "/deleteitem","/additem").hasRole("ADMIN")
        .antMatchers(HttpMethod.GET,"/additem").hasRole("ADMIN")
        .anyRequest()
        .authenticated()
        .and()

        .formLogin()
//        .loginPage("/login").permitAll()
        .usernameParameter("user_email")
        .passwordParameter("user_password")
        .loginProcessingUrl("/auth")
//        .failureUrl("login?error")
        .defaultSuccessUrl("/profile")
        .and()

        .logout()
        .logoutUrl("/logout").permitAll()
        .logoutSuccessUrl("/login");

//                .loginPage("login")

  }

//  @Bean
//  @Override
//  protected UserDetailsService userDetailsService() {
//    return new InMemoryUserDetailsManager(
//        User.builder()
//            .username("admin")
//            .password(passwordEncoder().encode("admin"))
//            .roles("ADMIN")
//            .build(),
//
//        User.builder()
//            .username("user")
//            .password(passwordEncoder().encode("user"))
//            .roles("USER")
//            .build()
//    );
//  }




}
