package io.pranludi.crossfit.member.grpc.interceptor;

import io.grpc.Context;
import io.grpc.Metadata;

public class InterceptorConstant {

    public static final Metadata.Key<String> MD_MEMBER_ID = Metadata.Key.of("member-id", Metadata.ASCII_STRING_MARSHALLER);
    public static final Context.Key<String> CTX_MEMBER_ID = Context.key("member-id");

}
