package cn.edu.huel.security;

import cn.edu.huel.util.NativeServletUtils;
import cn.edu.huel.vo.Result;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 张晓华
 * @date 2023-2-16
 */
@Slf4j
@Component
public class UnPassAuthHandler implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		log.error("鉴权未通过{}", authException.getMessage());
		authException.printStackTrace();
		var res = JSON.toJSONString(Result.error().put("tip", "鉴权失败"));
		NativeServletUtils.response(response, res);
	}


}
