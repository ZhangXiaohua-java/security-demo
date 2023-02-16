package cn.edu.huel.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 张晓华
 * @date 2023-2-16
 */
@RestController
public class TestController {


	@PreAuthorize("hasAnyAuthority('admin','employee')")
	@GetMapping(value = "/test", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Date test() {
		System.out.println("abc");
		return new Date();
	}

	@GetMapping(value = "/local", produces = {MediaType.APPLICATION_JSON_VALUE})
	public LocalDateTime localDateTime() {
		return LocalDateTime.now();
	}

}
