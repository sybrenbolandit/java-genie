package nl.sybrenbolandit.genie.grpc;

import nl.sybrenbolandit.genie.grpc.sub.InsertIntrospectionBean;
import picocli.CommandLine;

@CommandLine.Command(
        name = "grpc",
        description = "Says hello for now",
        subcommands = {
                InsertIntrospectionBean.class
        }
)
public class GrpcCommand implements Runnable {

    @Override
    public void run() {
        System.out.println("Hello from gRPC command");
    }

    public static void main(String[] args) {
        CommandLine.run(new GrpcCommand(), args);
    }

}
