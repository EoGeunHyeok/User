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

    public Member signup(MultipartFile selfie, String phoneNumber, String name, String username, String password, String email, int age, String gender, String region, String favoriteFood) throws IOException {
        // 회원 객체 생성
        Member member = Member.builder()
                .phoneNumber(phoneNumber)
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(name)
                .email(email)
                .age(age)
                .gender(gender)
                .region(region)
                .favoriteFood(favoriteFood)
                .selfie(selfie.getBytes()) // MultipartFile을 byte 배열로 변환하여 저장
                .build();

        member.setSelfie(selfie.getBytes());

        // 생성된 회원 객체를 저장소에 저장
        return memberRepository.save(member);
    }


}