package com.example.bob.domain.member.service;

import com.example.bob.domain.email.EmailService;
import com.example.bob.domain.member.entity.Member;
import com.example.bob.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;

    public Member getCurrentMember() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.findByusername(username)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
    }
    @Value("${custom.fileDirPath}")
    private String fileDirPath;

    public void signup2(String username, String phoneNumber, String nickname, String password,
                        String email, int age, String gender, String region, String favoriteFood, String mbti, String sns, MultipartFile thumbnail) {

        // 이메일 주소에 대한 인증 코드 생성 및 전송
        String verificationCode = verificationCodeService.generateVerificationCode(email);

//        String subject = "회원가입을 위한 인증코드입니다.";
//        String body = "회원가입을 완료하려면 아래의 인증코드를 입력해주세요.\n인증코드: " + verificationCode;
//        emailService.send(email, subject, body);

        String thumbnailRelPath = "post/" + UUID.randomUUID().toString() + ".jpg";
        File thumbnailFile = new File(fileDirPath + "/" + thumbnailRelPath);

        try {
            thumbnail.transferTo(thumbnailFile);
        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }

        Member member = Member.builder()
                .username(username)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .email(email)
                .age(age)
                .gender(gender)
                .region(region)
                .favoriteFood(favoriteFood)
                .thumbnailImg(thumbnailRelPath)
                .sns(sns)
                .mbti(mbti)
                .build();

        memberRepository.save(member);


    }

    // 이메일 인증 확인
    public boolean verifyVerificationCode(String email, String verificationCode) {
        return verificationCodeService.verifyVerificationCode(email, verificationCode);
    }

    public void signup(String username, String phoneNumber, String nickname, String password,
                       String email, int age, String gender, String region, String favoriteFood, String mbti, String sns) {

        Member member = Member.builder()
                .username(username)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .email(email)
                .age(age)
                .gender(gender)
                .region(region)
                .favoriteFood(favoriteFood)
                .sns(sns)
                .mbti(mbti)
                .build();

        memberRepository.save(member);
    }




    private Optional<Member> findByUsername(String username) {
        return memberRepository.findByusername(username);
    }

    @Transactional
    public Member whenSocialLogin(String username, String nickname) throws IOException {
        Optional<Member> opMember = findByUsername(username);

        if (opMember.isPresent()) {
            return opMember.get();
        }

        // 소셜 로그인을 통한 가입 시 비밀번호와 기타 정보는 빈 문자열로 초기화
        signup(username, "", nickname, "", "", 0, "", "", "", "", ""); // signup 메서드는 void를 반환하므로 반환값 없음
        return getMemberByUsername(username); // 저장된 회원을 다시 조회하여 반환
    }
//    public Member signupWithBasicInfo(String username, String nickname, MultipartFile thumbnail) {
//        String thumbnailRelPath = "post/" + UUID.randomUUID().toString() + ".jpg";
//        File thumbnailFile = new File(fileDirPath + "/" + thumbnailRelPath);
//
//        Member member = Member.builder()
//                .username(username)
//                .nickname(nickname)
//                .thumbnailImg(thumbnailRelPath)
//                .password("") // 비밀번호는 빈 문자열로 설정
//                .phoneNumber("")
//                .email("")
//                .age(0)
//                .gender("")
//                .region("")
//                .favoriteFood("")
//                .build();
//
//        return memberRepository.save(member);
//    }

    public Member getMemberByUsername(String username) {
        return memberRepository.findByusername(username)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
    }
}