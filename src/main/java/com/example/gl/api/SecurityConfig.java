package com.example.gl.api;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("bob")
				.password("password")
				.roles("USER")
				.and()
			.withUser("sue")
				.password("password")
				.roles("USER");
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
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.ignoringAntMatchers("/logout");
	}

}
