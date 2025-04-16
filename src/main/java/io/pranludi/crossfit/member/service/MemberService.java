package io.pranludi.crossfit.member.service;

import io.pranludi.crossfit.member.domain.EnvironmentData;
import io.pranludi.crossfit.member.domain.MemberEntity;
import io.pranludi.crossfit.member.exception.ServerError;
import io.pranludi.crossfit.member.repository.MemberRepository;
import io.pranludi.crossfit.member.repository.dto.MemberDTO;
import io.pranludi.crossfit.member.service.mapper.MemberMapper;
import java.util.function.Function;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    final MemberRepository memberRepository;
    final MemberMapper memberMapper;

    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    // 회원 등록
    @Transactional
    public Function<EnvironmentData, MemberEntity> signUp(MemberEntity memberEntity) {
        return (EnvironmentData env) -> {
            MemberDTO memberDTO = memberMapper.toDto(memberEntity);
            MemberDTO savedMember = memberRepository.save(memberDTO);
            return memberMapper.toEntity(savedMember);
        };
    }

    // 회원 조회
    public Function<EnvironmentData, MemberEntity> findById() {
        return (EnvironmentData env) -> {
            MemberDTO memberDTO = memberRepository.findById(env.id())
                .orElseThrow(() -> ServerError.MEMBER_NOT_FOUND(env.id()));
            return memberMapper.toEntity(memberDTO);
        };
    }
}
