package com.example.bob;

import com.example.bob.domain.member.entity.Member;
import com.example.bob.domain.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;


@SpringBootTest
class BlogApplicationTests {

	@Autowired
	MemberService memberService;


	@Test
	void contextLoads() {
			try {
				MockMultipartFile selfie = new MockMultipartFile("dd", "https://i.ibb.co/j5tSMPV/image.jpg", "image/jpeg", "https://i.ibb.co/j5tSMPV/image.jpg".getBytes());
				String phoneNumber = "01043884075";
				String name = "황예지";
				String username = "qwer";
				String password = "123";
				String email = "123@naver.com";
				String gender = "여자";
				String region = "대전";
				String favoriteFood = "중식";
				int age = 25; // 나이 추가

				// 회원 가입 메서드 호출
				Member member = memberService.signup(selfie, phoneNumber, name, username, password, email, age, gender, region, favoriteFood);

				// 테스트 결과 확인
				ModelAndView modelAndView = new ModelAndView("profile"); // 프로필 템플릿의 이름으로 ModelAndView 객체 생성
				modelAndView.addObject("member", member); // 회원 정보를 모델에 추가

				// 실제로는 렌더링되는 템플릿에 따라 반환된 ModelAndView를 처리하는 코드가 있어야 합니다.
			} catch (IOException e) {
				// 예외 처리 코드 작성
				e.printStackTrace(); // 예외 메시지 출력 또는 로깅 등
			}
		}
}
