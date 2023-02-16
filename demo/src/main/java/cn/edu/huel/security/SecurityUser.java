package cn.edu.huel.security;

import cn.edu.huel.domain.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 张晓华
 * @date 2023-2-16
 */

@Data
public class SecurityUser implements UserDetails {


	private User currentUser;

	private List<String> permissionValueList;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<String> permissions = currentUser.getPermissions();
		if (!ObjectUtils.isEmpty(permissions)) {
			ArrayList<GrantedAuthority> authorities = new ArrayList<>();
			for (String permission : permissions) {
				authorities.add(new SimpleGrantedAuthority(permission));
			}
			return authorities;
		}
		return null;
	}


	@Override
	public String getPassword() {
		return currentUser.getPassword();
	}


	@Override
	public String getUsername() {
		return currentUser.getUsername();
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	public boolean isEnabled() {
		return true;
	}


}
