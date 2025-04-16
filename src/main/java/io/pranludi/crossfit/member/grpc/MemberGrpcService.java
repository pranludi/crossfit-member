package io.pranludi.crossfit.member.grpc;

import io.grpc.stub.StreamObserver;
import io.pranludi.crossfit.member.domain.EnvironmentData;
import io.pranludi.crossfit.member.domain.MemberEntity;
import io.pranludi.crossfit.member.domain.MemberGrade;
import io.pranludi.crossfit.member.grpc.interceptor.GrpcRequestInterceptor;
import io.pranludi.crossfit.member.grpc.interceptor.GrpcResponseInterceptor;
import io.pranludi.crossfit.member.grpc.interceptor.GrpcTokenInterceptor;
import io.pranludi.crossfit.member.grpc.mapper.GrpcMapper;
import io.pranludi.crossfit.member.protobuf.GetMemberRequest;
import io.pranludi.crossfit.member.protobuf.GetMemberResponse;
import io.pranludi.crossfit.member.protobuf.MemberServiceGrpc.MemberServiceImplBase;
import io.pranludi.crossfit.member.protobuf.SignUpRequest;
import io.pranludi.crossfit.member.protobuf.SignUpResponse;
import io.pranludi.crossfit.member.service.MemberService;
import java.time.LocalDateTime;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService(interceptors = {GrpcTokenInterceptor.class, GrpcRequestInterceptor.class, GrpcResponseInterceptor.class})
public class MemberGrpcService extends MemberServiceImplBase {

    final MemberService memberService;
    final MakeEnvironment makeEnvironment;

    public MemberGrpcService(MemberService memberService, MakeEnvironment makeEnvironment) {
        this.memberService = memberService;
        this.makeEnvironment = makeEnvironment;
    }

    @Override
    public void signUp(SignUpRequest req, StreamObserver<SignUpResponse> resObserver) {
        EnvironmentData env = makeEnvironment.make();
        MemberEntity memberEntity = new MemberEntity(
            env.id(),
            req.getPassword(),
            req.getName(),
            req.getEmail(),
            req.getPhoneNumber(),
            MemberGrade.valueOf(req.getGrade().name()),
            LocalDateTime.of(1970, 1, 1, 0, 0, 0)
        );

        MemberEntity member = memberService.signUp(memberEntity).apply(env);
        SignUpResponse res = SignUpResponse.newBuilder()
            .setMember(GrpcMapper.INSTANCE.memberEntityToProto(member))
            .build();
        resObserver.onNext(res);
        resObserver.onCompleted();
    }

    @Override
    public void getMember(GetMemberRequest req, StreamObserver<GetMemberResponse> resObserver) {
        MemberEntity member = memberService.findById().apply(makeEnvironment.make());
        GetMemberResponse res = GetMemberResponse.newBuilder()
            .setMember(GrpcMapper.INSTANCE.memberEntityToProto(member))
            .build();
        resObserver.onNext(res);
        resObserver.onCompleted();
    }
}
