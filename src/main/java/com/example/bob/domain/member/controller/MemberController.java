package com.example.bob.domain.member.controller;

import com.example.bob.domain.member.entity.Member;
import com.example.bob.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "member/login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "member/signup";
    }
    @GetMapping("/myPage")
    public String myPage(Model model) {
        // 현재 로그인한 사용자의 정보를 가져옵니다. (예: testuser)
        Optional<Member> optionalMember = memberRepository.findByusername("testuser");

        // Optional에서 Member 객체를 추출하여 변수에 할당합니다.
        Member member = optionalMember.orElse(null); // 혹은 원하는 대체값을 지정할 수 있습니다.

        // 모델에 회원 정보를 추가합니다.
        model.addAttribute("member", member);

        // member/myPage.html로 이동합니다.
        return "member/myPage";
    }
}