package cn.crybird.manage.config.security;

import cn.crybird.manage.model.User;
import cn.crybird.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Username " + username + " not found.");
        }
        return createUserDetails(user);
    }

    private UserDetails createUserDetails(User user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRoleName()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),grantedAuthorities);
    }

}
