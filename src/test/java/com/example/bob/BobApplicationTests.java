package com.example.bob;

import com.example.bob.domain.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class BlogApplicationTests {

	@Autowired
	MemberService memberService;


	@Test
	void contextLoads() {
	}
}
