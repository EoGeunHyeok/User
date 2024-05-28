package com.example.bob.domain.member.controller;

import com.example.bob.domain.email.EmailService;
import com.example.bob.domain.member.entity.Member;
import com.example.bob.domain.member.repository.MemberRepository;
import com.example.bob.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final EmailService emailService;

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
        Member currentMember = memberService.getCurrentMember();
        model.addAttribute("member", currentMember);
        return "member/myPage";
    }




    @GetMapping("/signup")
    public String signupForm(Model model) {
        return "member/signup"; // signup.html을 반환
    }



    @PostMapping("/signup")
    public String signup(String username, String phoneNumber, String nickname, String password,
                         String email, int age, String gender, String region, String favoriteFood, Model model) {
        try {
            memberService.signup(username, phoneNumber, nickname, password, email, age, gender, region, favoriteFood);

            String subject = " 환영합니다!";
            String body = "현철아 찌발 됬냐?!";
            emailService.send(email, subject, body);


            return "member/login"; // 회원 가입 후 로그인 페이지로 리다이렉트
        } catch (Exception e) {
            model.addAttribute("errorMessage", "회원 가입 중 오류가 발생했습니다.");
            return "member/signup"; // 오류 발생 시 다시 회원 가입 페이지로
        }
    }

}
