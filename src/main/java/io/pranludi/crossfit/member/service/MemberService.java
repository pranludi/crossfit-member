package io.pranludi.crossfit.member.service;

import io.pranludi.crossfit.member.domain.EnvironmentData;
import io.pranludi.crossfit.member.repository.dto.MemberDTO;
import io.pranludi.crossfit.member.repository.MemberRepository;
import io.pranludi.crossfit.member.domain.MemberEntity;
import java.util.function.Function;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 등록
    @Transactional
    public Function<EnvironmentData, MemberEntity> signUp(MemberEntity memberEntity) {
        return (EnvironmentData env) -> {
            MemberDTO memberDTO = new MemberDTO(
                env.id(),
                memberEntity.password(),
                memberEntity.name(),
                memberEntity.email(),
                memberEntity.phoneNumber(),
                memberEntity.grade(),
                memberEntity.lastPaidAt()
            );
            memberRepository.save(memberDTO);
            return memberEntity;
        };
    }

    // 회원 조회
    public Function<EnvironmentData, MemberEntity> findById() {
        return (EnvironmentData env) -> {
            MemberDTO memberDTO = memberRepository.findById(env.id()).orElseThrow();
            return new MemberEntity(
                memberDTO.getId(),
                memberDTO.getPassword(),
                memberDTO.getName(),
                memberDTO.getEmail(),
                memberDTO.getPhoneNumber(),
                memberDTO.getGrade(),
                memberDTO.getLastPaidAt()
            );
        };
    }
}
