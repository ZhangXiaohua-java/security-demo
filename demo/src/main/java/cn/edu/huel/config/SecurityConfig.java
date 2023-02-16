package cn.edu.huel.config;

import cn.edu.huel.security.CustomLogoutHandler;
import cn.edu.huel.security.DefaultAccessDeniedHandler;
import cn.edu.huel.security.UnPassAuthHandler;
import cn.edu.huel.security.filter.AuthFilter;
import cn.edu.huel.security.filter.LoginFilter;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author 张晓华
 * @date 2023-2-16
 */
@Configuration
public class SecurityConfig {

	@Resource
	private HttpSecurity httpSecurity;

	@Resource
	private AuthenticationManagerBuilder auth;
	@Resource
	private UnPassAuthHandler unPassAuthHandler;

	@Resource
	private DefaultAccessDeniedHandler accessDeniedHandler;

	@Resource
	private UserDetailsService userDetailsService;


	@Resource
	private CustomLogoutHandler logoutHandler;


	@Bean("authenticationManager")
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public AuthFilter authFilter(AuthenticationManager authenticationManager, RedisTemplate<String, Object> redisTemplate) {
		AuthFilter authFilter = new AuthFilter(authenticationManager);
		authFilter.redisTemplate = redisTemplate;
		authFilter.ops = redisTemplate.opsForValue();
		return authFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(LoginFilter loginFilter, AuthFilter authFilter) throws Exception {
		httpSecurity.csrf().disable();
		httpSecurity.authorizeHttpRequests()
				.anyRequest().authenticated();
		httpSecurity.exceptionHandling()
				.authenticationEntryPoint(unPassAuthHandler)
				.accessDeniedHandler(accessDeniedHandler);
		httpSecurity.userDetailsService(userDetailsService);
		httpSecurity.addFilter(loginFilter)
				.addFilter(authFilter)
				.logout()
				.logoutUrl("/user/logout")
				.addLogoutHandler(logoutHandler)
				.logoutSuccessUrl("/user/login")
				.and()
				.httpBasic();
		return httpSecurity.build();
	}


}
