package io.pranludi.crossfit.member.domain;

import java.time.LocalDateTime;

public record MemberEntity(
    String id,
    String password,
    String name,
    String email,
    String phoneNumber,
    MemberGrade grade,
    LocalDateTime lastPaidAt
) {

}
