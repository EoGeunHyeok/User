package com.example.bob.domain.member.entity2;

import com.example.bob.domain.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Verification extends BaseEntity {

    private String email;

    private String verificationCode;

    @Enumerated(EnumType.STRING)
    private VerificationStatus status;

}
