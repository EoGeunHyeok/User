package com.example.bob.domain.member.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class VerificationCodeService {

    private static final int VERIFICATION_CODE_LENGTH = 6;
    private Map<String, String> verificationCodes = new HashMap<>();

    public String generateVerificationCode(String email) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < VERIFICATION_CODE_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        String code = sb.toString();
        verificationCodes.put(email, code); // 이메일과 인증 코드를 매핑하여 저장
        return code;
    }

    // 인증 코드 확인
    public boolean verifyVerificationCode(String email, String verificationCode) {
        String savedCode = verificationCodes.get(email); // 저장된 인증 코드 가져오기
        return savedCode != null && savedCode.equals(verificationCode);
    }
}
