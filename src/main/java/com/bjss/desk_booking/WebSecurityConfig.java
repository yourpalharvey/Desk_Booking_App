package com.bjss.desk_booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/css/**").permitAll() // all files in the Css directory can be seen by all
                .mvcMatchers("/js/MyBookingScript.js").permitAll()
                .mvcMatchers("/js/BookingPageScript.js").permitAll()
                .mvcMatchers("/js/QuickBookingPageScript.js").permitAll()
                .mvcMatchers("/js/hideUserNavLinks.js").hasRole("ADMIN")
                .mvcMatchers("/js/hideAdminNavLinks.js").hasRole("USER")
                .mvcMatchers("/js/showLogout.js").hasAnyRole("USER", "ADMIN")
                .mvcMatchers("/images/**").permitAll()
                .mvcMatchers("/").permitAll()
                .mvcMatchers("/admindeskstatus/**").hasRole("ADMIN")
                .mvcMatchers("/previousbooking/**").hasRole("ADMIN")
                .mvcMatchers("/admin/desk/**").hasRole("ADMIN")
                .mvcMatchers("/desk/**").permitAll()
                .mvcMatchers("/public/**").permitAll()
                .mvcMatchers("/user/**").hasRole("USER") // Pages with /user need users to be signed in with the role USER
                .mvcMatchers("/admin/**").hasRole("ADMIN") // Pages with /admin need users to be signed in with the role of ADMIN
                .anyRequest().denyAll()
                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/public/home").permitAll(); // Logout redirects to home page

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        /* User details are stored here. Can be done via the users on the database later*/
        auth
                .inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER");
        auth
                .inMemoryAuthentication()
                .withUser("beynonh").password("{noop}password").roles("USER");
        auth
                .inMemoryAuthentication()
                .withUser("shortmant").password("{noop}password").roles("USER");
        auth
                .inMemoryAuthentication()
                .withUser("alahif").password("{noop}password").roles("USER");
        auth
                .inMemoryAuthentication()
                .withUser("admin").password("{noop}admin").roles("ADMIN");
    }
}
