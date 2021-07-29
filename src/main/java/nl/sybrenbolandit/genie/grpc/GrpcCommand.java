package nl.sybrenbolandit.genie.grpc;

import picocli.CommandLine;

@CommandLine.Command(
        name = "grpc",
        description = "Says hello for now"
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
