package cn.edu.huel.util;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author 张晓华
 * @date 2023-2-16
 */
public final class NativeServletUtils {


	public static void response(HttpServletResponse response, String content) {
		try {
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			PrintWriter writer = response.getWriter();
			writer.write(content);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
