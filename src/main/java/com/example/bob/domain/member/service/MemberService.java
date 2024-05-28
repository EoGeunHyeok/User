package com.example.bob.domain.member.service;

import com.example.bob.domain.member.entity.Member;
import com.example.bob.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member getCurrentMember() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.findByusername(username)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
    }

    public void signup(String username, String phoneNumber, String nickname, String password,
                       String email, int age, String gender, String region, String favoriteFood) {
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
        signup(username, "", nickname, "", "", 0, "", "", ""); // signup 메서드는 void를 반환하므로 반환값 없음
        return getMemberByUsername(username); // 저장된 회원을 다시 조회하여 반환
    }

    public Member signupWithBasicInfo(String username, String nickname) {
        Member member = Member.builder()
                .username(username)
                .nickname(nickname)
                .password("") // 비밀번호는 빈 문자열로 설정
                .phoneNumber("")
                .email("")
                .age(0)
                .gender("")
                .region("")
                .favoriteFood("")
                .build();

        return memberRepository.save(member);
    }

    public Member getMemberByUsername(String username) {
        return memberRepository.findByusername(username)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
    }
}
