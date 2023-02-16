package cn.edu.huel.security;

import cn.edu.huel.util.NativeServletUtils;
import cn.edu.huel.vo.Result;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 张晓华
 * @date 2023-2-16
 */
@Component
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		var res = JSON.toJSONString(Result.error().put("tip", "无权限访问"));
		NativeServletUtils.response(response, res);
	}

}
