package com.example.bob.domain.global.initdata;


import com.example.bob.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class Dev {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner init(MemberService memberService) {
        return args -> {

            // 회원가입 메서드 호출
            memberService.signup("user1", "01012345678", "user1", "1234", "admin@test.com",
                    26, "남자", "대전", "일식");
            memberService.signup("user2", "01012345678", "user2", "1234", "admin@test.com",
                    25, "여자", "대전", "일식");

        };
    }
}

