package cn.crybird.manage.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*@Configuration
@EnableWebSecurity*/
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private MyLoginSuccessHandler loginSuccessHandler;

    @Autowired
    private MyLoginFailureHandler loginFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        auth.authenticationProvider(provider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/admin/login").permitAll()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .and().logout().permitAll()
                .and().csrf().disable();
//        http.authorizeRequests().antMatchers("/admin/**").hasRole("USER").anyRequest().authenticated();
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

}
