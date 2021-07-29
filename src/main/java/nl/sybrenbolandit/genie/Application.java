package nl.sybrenbolandit.genie;

import nl.sybrenbolandit.genie.grpc.GrpcCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name = "main",
        description = "Container command",
        subcommands = {
                GrpcCommand.class
        }
)
public class Application implements Runnable {

    @Override
    public void run() {
        System.out.println("Hello from the java genie");
    }

    public static void main(String[] args) {
        CommandLine.run(new Application(), args);
    }

}
