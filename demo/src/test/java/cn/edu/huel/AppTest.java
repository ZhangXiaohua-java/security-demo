package cn.edu.huel;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Unit test for simple App.
 */
public class AppTest {


	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("111111"));
	}

}
