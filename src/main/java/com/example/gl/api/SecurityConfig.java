package com.example.gl.api;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	@Override
	protected UserDetailsService userDetailsService() {
		UserDetails user1 = 
				 User.withDefaultPasswordEncoder()
	                .username("bob")
	                .password("password")
	                .roles("USER")
	                .build();
		
		UserDetails user2 = 
				 User.withDefaultPasswordEncoder()
	                .username("sue")
	                .password("password")
	                .roles("USER")
	                .build();
		
		return new InMemoryUserDetailsManager(user1, user2);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginProcessingUrl("/login")
				.successHandler((request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK))
				.failureHandler((request, response, authException) -> response.sendError(HttpServletResponse.SC_FORBIDDEN))
				.passwordParameter("password")
				.usernameParameter("username")
				.permitAll()
				.and()
			.exceptionHandling().authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
				.and()
			.exceptionHandling().accessDeniedHandler((request, response, authException) -> response.sendError(HttpServletResponse.SC_FORBIDDEN))
				.and()
			.logout()
				.logoutUrl("/logout")
				.permitAll()
				.and()
			.csrf()
//				.disable();
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.ignoringAntMatchers("/logout")
				.ignoringAntMatchers("/login");
	}

}
