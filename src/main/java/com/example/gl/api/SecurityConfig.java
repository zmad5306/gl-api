package com.example.gl.api;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("bob")
				.password(passwordEncoder().encode("password"))
				.roles("USER")
				.and()
			.withUser("sue")
				.password(passwordEncoder().encode("password"))
				.roles("USER")
			.and()
				.passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/healthz", "/actuator/health").permitAll()
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
				.disable();
//				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//				.ignoringAntMatchers("/logout")
//				.ignoringAntMatchers("/login");
	}

}
