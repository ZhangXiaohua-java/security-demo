package cn.edu.huel.security.filter;

import cn.edu.huel.magic.SecurityConst;
import cn.edu.huel.util.JWTUtils;
import cn.edu.huel.util.NativeServletUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author 张晓华
 * @date 2023-2-16
 */
public class AuthFilter extends BasicAuthenticationFilter {

	public RedisTemplate<String, Object> redisTemplate;

	public ValueOperations<String, Object> ops;


	public AuthFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}


	/**
	 * 用户认证之后每次请求都会经过当前过滤器的处理,当前过滤器只需要从请求头中获取token信息,
	 * 然后从Redis中查询用户的权限列表信息并将本次请求的认证信息放入到Security的上下文中,由Security进行后序的鉴权操作
	 *
	 * @param request  原生请求
	 * @param response 原生响应
	 * @param chain    过滤器链
	 * @throws IOException      异常
	 * @throws ServletException 异常
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String token = request.getHeader("token");
		if (StringUtils.hasText(token)) {
			String username = JWTUtils.getInfo(token);
			Object o = ops.get(SecurityConst.TOKEN_PREFIX + username);
			if (!ObjectUtils.isEmpty(o) && o instanceof List list) {
				String permissions = String.join(",", list);
				List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(permissions);
				SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, token, authorities));
				chain.doFilter(request, response);
			}
		}
	}


}
