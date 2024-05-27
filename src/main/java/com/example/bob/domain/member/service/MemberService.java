package com.example.bob.domain.member.service;

import com.example.bob.domain.member.entity.Member;
import com.example.bob.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return memberRepository.findByusername(username)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
    }

    public void signup(String username, String phoneNumber, String nickname, String password,
                       String email, int age, String gender, String region, String favoriteFood,
                       MultipartFile selfie) throws IOException { // IOException 추가
        // 회원 객체 생성
        Member member = Member.builder()
                .phoneNumber(phoneNumber)
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .email(email)
                .age(age)
                .gender(gender)
                .region(region)
                .favoriteFood(favoriteFood)
                .selfie(selfie.getBytes()) // MultipartFile을 byte 배열로 변환하여 저장
                .build();

        // 생성된 회원 객체를 저장소에 저장
        memberRepository.save(member);
    }




    private Optional<Member> findByUsername(String username) {
        return memberRepository.findByusername(username);
    }



}
