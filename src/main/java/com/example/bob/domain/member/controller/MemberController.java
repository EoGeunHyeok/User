package com.example.bob.domain.member.controller;

import com.example.bob.domain.email.EmailService;
import com.example.bob.domain.member.entity.Member;
import com.example.bob.domain.member.service.MemberService;
import com.example.bob.domain.member.service.VerificationCodeService;
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


            // 파일 업로드 성공 시 회원 가입 처리
            memberService.signup2(username, phoneNumber, nickname, password, email, age, gender, region, favoriteFood, mbti, sns, thumbnail);

            String subject = "차은우보단!";
            String body = "박현철 !! " ;
            emailService.send(email, subject, body);

            return "member/login"; // 회원 가입 후 로그인 페이지로 리다이렉트

        }




}
