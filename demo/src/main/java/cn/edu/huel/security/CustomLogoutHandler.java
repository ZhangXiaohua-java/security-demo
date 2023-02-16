package cn.edu.huel.security;

import cn.edu.huel.vo.Result;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author 张晓华
 * @date 2023-2-16
 */
@Component
public class CustomLogoutHandler implements LogoutHandler {

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		PrintWriter writer = null;
		try {
			response.setStatus(200);
			writer = response.getWriter();
			writer.write(JSON.toJSONString(Result.ok().put("res", "退出成功")));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}


}
