package com.healthcare.integrator1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		

	    http.authorizeRequests()
	    .antMatchers("/admin/**")
		.access("hasRole('ROLE_ADMIN')")
		.antMatchers("/doctor/**")
		.access("hasAnyRole('ROLE_DOCTOR','ROLE_ADMIN')")
		.antMatchers("/reception/**")
		.access("hasAnyRole('ROLE_RECEPTION','ROLE_ADMIN')")
		.antMatchers("/nurse/**")
		.access("hasAnyRole('ROLE_NURSE','ROLE_ADMIN')")
		.antMatchers("/pharmacy/**")
		.access("hasAnyRole('ROLE_PHARMACY','ROLE_ADMIN')")
		.antMatchers("/lab/**")
		.access("hasAnyRole('ROLE_LAB','ROLE_ADMIN')")
		.and()
		.formLogin().loginPage("/login").failureUrl("/login?error")
		.usernameParameter("username")
		.passwordParameter("password")
		.and()
		.logout()
		.deleteCookies("remove")
        .invalidateHttpSession(true)
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?logout")
		//.and().csrf()
		.and().exceptionHandling().accessDeniedPage("/403");

	    http.csrf().disable();
	    http.sessionManagement().maximumSessions(2);
	    http.sessionManagement()
	    .invalidSessionUrl("/login");
	    
		
	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder(){
//		PasswordEncoder encoder = new BCryptPasswordEncoder();
//		return encoder;
//	}
	
}
