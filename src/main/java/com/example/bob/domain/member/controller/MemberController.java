package com.example.bob.domain.member.controller;

import com.example.bob.domain.member.entity.Member;
import com.example.bob.domain.member.repository.MemberRepository;
import com.example.bob.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.Base64;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String loginPage() {
        return "member/login";
    }

//    @GetMapping("/signup")
//    public String signupPage() {
//        return "member/signup";
//    }
@GetMapping("/myPage")
public String myPage(Model model) {
    // 현재 로그인된 회원 정보를 가져와 모델에 추가
    Member currentMember = memberService.getCurrentMember();
    byte[] imageBytes = currentMember.getSelfie();
    String base64Image = Base64.getEncoder().encodeToString(imageBytes);

    model.addAttribute("base64Image", base64Image); // 올바른 속성 이름으로 수정
    model.addAttribute("member", currentMember);
    return "member/myPage";
}


    @GetMapping("/signup")
    public String signup(){
        return "member/signup"; // 로그인 페이지로 리다이렉트
    }
}
