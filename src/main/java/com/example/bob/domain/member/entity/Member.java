package com.example.bob.domain.member.entity;

import com.example.bob.domain.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;



@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    @Comment("유저 아이디")
    @Column(unique = true)
    private String username;

    private String password;

    @Comment("유저 닉네임")
    @Column(unique = true)
    private String nickname;

    private String email;

    @Comment("전화번호")
    private String phoneNumber;

    @Comment("성별")
    private String gender;

    @Comment("본인 사진")
    private String selfie; // 사진 파일 경로 또는 URL

    public void addAttribute(String member, Member member1) {
    }
}
