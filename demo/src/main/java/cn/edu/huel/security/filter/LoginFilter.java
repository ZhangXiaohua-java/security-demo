package cn.edu.huel.security.filter;

import cn.edu.huel.magic.SecurityConst;
import cn.edu.huel.security.SecurityUser;
import cn.edu.huel.util.JWTUtils;
import cn.edu.huel.util.NativeServletUtils;
import cn.edu.huel.vo.Result;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 张晓华
 * @date 2023-2-16
 */
@Component
public class LoginFilter extends UsernamePasswordAuthenticationFilter {


	AuthenticationManager authenticationManager;

	RedisTemplate<String, Object> redisTemplate;

	public LoginFilter(AuthenticationManager authenticationManager, RedisTemplate<String, Object> redisTemplate) {
		// TODO 记录一下此处的要点,如果不调用父类的有参构造就会报authenticationManager must be specified错误
		super(authenticationManager);
		// 只支持post请求
		this.setPostOnly(true);
		// 设置登陆接口地址
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/user/login"));
		this.authenticationManager = authenticationManager;
		this.redisTemplate = redisTemplate;
	}


	/**
	 * @param request  原生请求
	 * @param response 原生响应
	 * @return 认证信息(只是从用户的登陆请求中获取到的信息, 并不是真正的认证信息)
	 * @throws AuthenticationException 认证失败
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		return getAuth(request);
	}

	/**
	 * 认证成功后的回调函数,可以给用户响应token信息
	 *
	 * @param request    原生请求
	 * @param response   原生响应
	 * @param chain      过滤器链
	 * @param authResult 认证结果
	 * @throws IOException      异常
	 * @throws ServletException 异常
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		Object principal = authResult.getPrincipal();
		if (principal instanceof SecurityUser user) {
			String username = user.getUsername();
			String token = JWTUtils.createToken(username);
			redisTemplate.opsForValue().set(SecurityConst.TOKEN_PREFIX + username, user.getCurrentUser().getPermissions());
			String res = JSON.toJSONString(Result.ok().put("token", token));
			NativeServletUtils.response(response, res);
		} else {
			throw new RuntimeException("测试异常,类型判断有误");
		}
	}

	/**
	 * 认证失败之后执行的回调函数,可以给用户响应错误信息
	 *
	 * @param request  原生请求
	 * @param response 原生响应
	 * @param failed   失败的认证信息
	 * @throws IOException      异常
	 * @throws ServletException 异常
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		NativeServletUtils.response(response, JSON.toJSONString(Result.error().put("tip", "认证失败")));
	}


	private Authentication getAuth(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
			return null;
		}
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, authorities));
	}


}
