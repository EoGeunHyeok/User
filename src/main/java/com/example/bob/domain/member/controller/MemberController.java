package com.example.bob.domain.member.controller;

import com.example.bob.domain.email.EmailService;
import com.example.bob.domain.member.entity.Member;
import com.example.bob.domain.member.service.MemberService;
import com.example.bob.domain.member.service.VerificationCodeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final VerificationCodeService verificationCodeService;
    private final MemberService memberService;
    private final EmailService emailService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String loginPage() {
        return "member/login";
    }

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
    public String signup(@RequestParam("username") String username,
                         @RequestParam("phoneNumber") String phoneNumber,
                         @RequestParam("nickname") String nickname,
                         @RequestParam("password") String password,
                         @RequestParam("email") String email,
                         @RequestParam("age") int age,
                         @RequestParam("gender") String gender,
                         @RequestParam("region") String region,
                         @RequestParam("mbti") String mbti,
                         @RequestParam("sns") String sns,
                         @RequestParam("favoriteFood") String favoriteFood,
                         @RequestParam("thumbnail") MultipartFile thumbnail) {

        // 이메일 확인용 코드 생성
        String verificationCode = verificationCodeService.generateVerificationCode(email);
        // 회원가입 확인 이메일 보내기

        String subject = "회원가입 인증코드";
        String body = "회원가입인증 코드입니다. : " + verificationCode;
        emailService.send(email, subject, body);

        // 파일 업로드 성공 시 회원 가입 처리
        memberService.signup2(username, phoneNumber, nickname, password, email, age, gender, region, favoriteFood, mbti, sns, thumbnail);

        // 회원 가입 후 로그인 페이지로 리다이렉트
        return "redirect:/member/verifyCode";
    }
    @GetMapping("/verifyCode")
    public String verifyCodeForm(Model model) {
        return "member/verifyCode"; // verifyCode.html을 반환
    }
    @PostMapping("/verifyCode")
    public String verifyCode(@RequestParam("verification") String verificationCode, HttpSession session) {
        String storedVerification = (String) session.getAttribute("verificationCode");
        if (verificationCode != null && verificationCode.equals(storedVerification)) {
            session.removeAttribute("verification");
            return "member/login";
        } else {
            return "member/verifyCode";
        }
    }

}
