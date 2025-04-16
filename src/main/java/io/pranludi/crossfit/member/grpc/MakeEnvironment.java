package io.pranludi.crossfit.member.grpc;

import io.pranludi.crossfit.member.domain.EnvironmentData;
import io.pranludi.crossfit.member.grpc.interceptor.InterceptorConstant;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class MakeEnvironment {

    public EnvironmentData make() {
        String memberId = InterceptorConstant.CTX_MEMBER_ID.get();

        EnvironmentData environmentData = new EnvironmentData(memberId, LocalDateTime.now());

        return environmentData;
    }
}
