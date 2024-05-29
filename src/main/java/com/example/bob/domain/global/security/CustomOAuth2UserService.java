package com.example.bob.domain.global.security;

import com.example.bob.domain.member.entity.Member;
import com.example.bob.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberService memberService;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String oauthId = oAuth2User.getName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Map<String, Object> attributesProperties = (Map<String, Object>) attributes.get("properties");
        String nickname = (String) attributesProperties.get("nickname");
        String thumbnailUrl = (String) attributesProperties.get("thumbnail_image");

        String providerTypeCode = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
        String username = providerTypeCode + "__%s".formatted(oauthId);



        Member member;
        List<GrantedAuthority> authorityList = new ArrayList<>();

        try {
            member = memberService.whenSocialLogin(username, nickname);
        } catch (IOException e) {
            throw new RuntimeException("Social login error", e);
        }

        return new CustomOAuth2User(member.getUsername(), member.getPassword(), authorityList);
    }
}

class CustomOAuth2User extends User implements OAuth2User {

    public CustomOAuth2User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null; // Attributes can be returned here if needed
    }

    @Override
    public String getName() {
        return getUsername();
    }
}
