package nl.sybrenbolandit.genie.grpc.sub;

import picocli.CommandLine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.concurrent.Callable;

@CommandLine.Command(
    name = "insert-introspection-bean",
    description = "Insert an introspection bean")
public class InsertIntrospectionBean implements Callable<Integer> {

  private static final String FILE_NAME = "ServerBuilderListener.java";

  @CommandLine.Option(
      names = {"-l", "--location"},
      description = "The relative path to the target location",
      defaultValue = ".")
  private Path location;

  public static void main(String... args) {
    int exitCode = new CommandLine(new InsertIntrospectionBean()).execute(args);
    System.exit(exitCode);
  }

  @Override
  public Integer call() throws IOException {
    System.out.printf("Insert introspection bean into %s%n", location);

    InputStream sourceFile = fetchSourceFile();
    copyFile(sourceFile);

    return 0;
  }

  private InputStream fetchSourceFile() throws IOException {
    return Optional.ofNullable(
            InsertIntrospectionBean.class.getResourceAsStream("/grpc/" + FILE_NAME))
        .orElseThrow(() -> new IllegalStateException("Source file not found!"));
  }

  private void copyFile(InputStream source) throws IOException {
    Files.copy(source, location.resolve(FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
  }
}
