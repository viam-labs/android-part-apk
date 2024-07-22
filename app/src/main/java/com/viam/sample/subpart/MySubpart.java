package com.viam.sample.subpart;
import android.util.Log;

import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import com.viam.sdk.core.component.generic.Generic;
import com.viam.sdk.core.rpc.Server;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.ServerBuilder;
import io.grpc.ServerCredentials;
import io.grpc.TlsServerCredentials;

public class MySubpart implements Runnable {
    final static String tag = "MySubpart";
    public static class MyGeneric extends Generic {
        public MyGeneric(final String name) {
            super(name);
        }

        @Override
        public Struct doCommand(Map<String, Value> command) {
            final Struct.Builder builder = Struct.newBuilder();
            return builder.putFields(
                    "hello",
                    Value.newBuilder().setBoolValue(true).build()
            ).build();
        }
    }

    public void run() {
        final Generic generic = new MyGeneric("example");
        final int port = 50051;
        ServerBuilder builder = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create());
        try (Server server = new Server(List.of(generic), builder)) {
            server.start();
        }
    }
}
