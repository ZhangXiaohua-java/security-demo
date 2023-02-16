package cn.edu.huel.service.impl;

import cn.edu.huel.domain.User;
import cn.edu.huel.security.SecurityUser;
import cn.edu.huel.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author 张晓华
 * @date 2023-2-16
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Resource
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (!StringUtils.hasText(username)) {
			throw new UsernameNotFoundException(username + "不存在");
		}
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getUsername, username);
		User user = userService.getOne(queryWrapper);
		if (Objects.isNull(user)) {
			throw new UsernameNotFoundException(username + "不存在");
		}
		SecurityUser securityUser = new SecurityUser();
		List<String> list = List.of("employee");
		user.setPermissions(list);
		securityUser.setCurrentUser(user);
		securityUser.setPermissionValueList(list);
		return securityUser;
	}


}
