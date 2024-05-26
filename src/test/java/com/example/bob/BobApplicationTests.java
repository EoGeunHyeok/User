package com.example.bob;

import com.example.bob.domain.member.service.MemberService;
import com.example.bob.domain.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class BlogApplicationTests {

	@Autowired
	MemberService memberService;

	@Autowired
	PostService postService;

	@Test
	void contextLoads() {
		for ( int i = 0; i <= 30; i++ ) {
			String title = String.format("제목 %d", i);
			String content = String.format("내용 %d", i);
			this.postService.create(title, content);
		}

		this.memberService.signup("user1", "123", "현철", "user1@example.com");
		this.memberService.signup("user2", "123", "차은우", "user2@example.com");
		this.memberService.signup("user3", "123", "황예지", "user3@example.com");

	}
}