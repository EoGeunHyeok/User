package com.example.bob;

import com.example.bob.domain.member.entity.Member;
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
		for (int i = 0; i <= 30; i++) {
			String title = String.format("제목 %d", i);
			String content = String.format("내용 %d", i);
			this.postService.create(title, content);
		}
		Member member1 = memberService.signup("user1", "1234", "현철쿤", "user1@example.com", "123456789", "https://i.ibb.co/j5tSMPV/image.jpg", "남자");
		Member member2 = memberService.signup("user2", "1234", "예지", "user2@example.com", "987654321", "https://i.ibb.co/j5tSMPV/image.jpg", "여자");
		Member member3 = memberService.signup("user3", "1234", "유나", "user3@example.com", "456789123", "https://i.ibb.co/j5tSMPV/image.jpg", "여자");

	}
}