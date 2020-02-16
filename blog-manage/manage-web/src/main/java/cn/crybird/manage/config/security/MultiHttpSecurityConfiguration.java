package cn.crybird.manage.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class MultiHttpSecurityConfiguration {

    private final MyUserDetailsService userDetailsService;

    @Autowired
    public MultiHttpSecurityConfiguration(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        auth.authenticationProvider(provider);
    }

    @Configuration
    @Order(1)
    public static class AdminFormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter{

        /*@Autowired
        private final AccessDeniedHandler accessDeniedHandler;*/

        private final MyUserDetailsService userDetailsService;

        private final MyLoginSuccessHandler loginSuccessHandler;

        @Autowired
        public AdminFormLoginWebSecurityConfigurerAdapter(/*AccessDeniedHandler accessDeniedHandler,*/ MyUserDetailsService userDetailsService, MyLoginSuccessHandler loginSuccessHandler) {
            //this.accessDeniedHandler = accessDeniedHandler;
            this.userDetailsService = userDetailsService;
            this.loginSuccessHandler = loginSuccessHandler;
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/swagger-ui.html",
                    "/v2/api-docs",
                    "/swagger-resources/**",
                    "/images/**",
                    "/webjars/**",
                    "/configuration/ui",
                    "/configuration/security");
            web.ignoring().antMatchers("/css/**", "/images/**", "/js/**", "/lib/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/admin/**")
                    .authorizeRequests()
                    .antMatchers("/admin/login", "/admin/doLogin", "/admin/logout").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    /*.rememberMe()
                    .rememberMeParameter("remember-me")
                    .key("uniqueSecretUsedForGenerateToken")
                    .tokenValiditySeconds(86400)*/
                    .userDetailsService(userDetailsService)
                    /*.and()*/
                    .formLogin()
                    .loginPage("/admin/login")
                    .loginProcessingUrl("/admin/doLogin")
                    .defaultSuccessUrl("/admin")
                    //.failureUrl("/admin/login?error=1")
                    .permitAll()

                    .and()
                    .logout()
                    .logoutUrl("/admin/logout")
                    .logoutSuccessUrl("/admin/login")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)

                    /*.and()
                    .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler)*/
//                    .accessDeniedPage("/403")
                    .and()
                    .csrf().disable();

        }

    }

    /*@Configuration
    @Order(2)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        *//*@Autowired
        private final AccessDeniedHandler accessDeniedHandler;*//*

        private final MyUserDetailsService userDetailsService;

        @Autowired
        public FormLoginWebSecurityConfigurerAdapter(*//*AccessDeniedHandler accessDeniedHandler, *//*MyUserDetailsService userDetailsService) {
            //this.accessDeniedHandler = accessDeniedHandler;
            this.userDetailsService = userDetailsService;
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/personal/**").authenticated()
                    *//*.antMatchers("/t/**").hasRole("USER")*//*
                    .antMatchers("/login**").permitAll()
                    .anyRequest().permitAll()

                    .and()
                    *//*.rememberMe()
                    .rememberMeParameter("remember-me")
                    .key("uniqueSecretUsedForGenerateToken")
                    .tokenValiditySeconds(86400)*//*
                    .userDetailsService(userDetailsService)

                    *//*.and()*//*
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/doLogin")
                    //.successForwardUrl("/login-success")
                    //.failureUrl("/login?error=1")
                    .permitAll()

                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    //.logoutSuccessUrl("/") //退出登录后的默认网址是”/”
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)

                    *//*.and()
                    .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler)*//*

                    .and()
                    .csrf().disable();
        }

    }*/

}
