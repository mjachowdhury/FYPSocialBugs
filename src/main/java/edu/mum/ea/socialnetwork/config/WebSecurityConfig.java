package edu.mum.ea.socialnetwork.config;

import edu.mum.ea.socialnetwork.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
        //with no encoder
//        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());

        //using im memmory
//        auth.inMemoryAuthentication()
//                .withUser("user").password("{noop}123456").roles("user").and().withUser("admin").password("123").roles("Admin").passwordEncoder(passwordEncoder());
        //using in
//        auth.jdbcAuthentication()
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                //.ignoring().antMatchers("/resources/**");
                .antMatchers("/css/**", "/fonts/**", "/js/**", "/lib/**", "/vendor/**", "/media/**", "/images/**", "/login/**", "/register/**", "/forgetPassword/**", "/errorMessages/**", "/messages/**", "/authenticate").permitAll()
                .antMatchers().hasRole("USER")
                .antMatchers("/logout", "/profile/**").hasAnyRole("ADMIN", "USER", "MARKETING_MANAGER", "CONTENT_MANAGER")
                .antMatchers("/unhealthyWords/**", "/postCategories/**").hasAnyRole("ADMIN", "CONTENT_MANAGER")

                .antMatchers("/admin/unhealthyPosts").hasAnyRole("ADMIN", "CONTENT_MANAGER")
                .antMatchers("/admin/deactivatedUsers", "/admin/manageUserRoles").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                // NOTE: To hide the report page from the user, just uncomment it.
                //  .antMatchers("/report/**").hasAnyRole("ADMIN", "CONTENT_MANAGER")
                .antMatchers("/report/**").permitAll()
                //.defaultSuccessUrl("/")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .accessDeniedPage("/denied")
                .and()
                .sessionManagement();
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
