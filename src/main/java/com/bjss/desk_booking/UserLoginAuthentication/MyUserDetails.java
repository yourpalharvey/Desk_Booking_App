package com.bjss.desk_booking.UserLoginAuthentication;

import com.bjss.desk_booking.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private int userId;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private int rating;
    private String userEmail;



    public MyUserDetails(User user){
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = setUserAuthorities(user.isAdmin());
        this.rating = user.getRating();
        this.userEmail = user.getUserEmail();

    }

    public List<GrantedAuthority> setUserAuthorities(boolean isAdmin) {
        //set USER/ADMIN authorities
        if(isAdmin){
            return new ArrayList(Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        } else {
            return new ArrayList(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
