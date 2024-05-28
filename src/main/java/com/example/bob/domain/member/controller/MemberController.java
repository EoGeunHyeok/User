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
                         String email, int age, String gender, String region, String favoriteFood, String verificationCode, Model model) {
        try {
            // 회원가입 처리
            memberService.signup(username, phoneNumber, nickname, password, email, age, gender, region, favoriteFood);

            // 회원 가입 성공 메시지 전달
            model.addAttribute("successMessage", "회원가입이 성공적으로 완료되었습니다. 이메일을 확인하여 인증 코드를 입력하세요.");

            // 회원 가입 페이지로 리다이렉트
            return "member/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "회원 가입 중 오류가 발생했습니다.");
            return "member/signup"; // 오류 발생 시 다시 회원 가입 페이지로
        }
    }

    public String sendVerificationCode(String email, Model model) {
        try {
            // 이메일 인증을 위한 인증 코드 생성
            int verificationCode = emailService.generateVerificationCode();

            // 회원가입한 이메일로 인증 코드를 보내는 메일 발송
            String subject = "이메일 인증 코드";
            String body = "회원가입을 완료하려면 아래 인증 코드를 입력하세요: " + verificationCode;
            emailService.send(email, subject, body);

            // 성공 메시지 전달
            model.addAttribute("successMessage", "이메일로 인증 코드를 전송했습니다. 이메일을 확인하세요.");

            // 회원 가입 페이지로 리다이렉트
            return "redirect:/member/signup";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "이메일 전송 중 오류가 발생했습니다.");
            return "member/signup"; // 오류 발생 시 다시 회원 가입 페이지로
        }
    }
}
