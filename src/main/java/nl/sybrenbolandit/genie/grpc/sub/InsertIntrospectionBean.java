package nl.sybrenbolandit.genie.grpc.sub;

import picocli.CommandLine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

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

  @CommandLine.Option(
          names = {"-p", "--package"},
          description = "The package of the new class")
  private String packageName;

  public static void main(String... args) {
    int exitCode = new CommandLine(new InsertIntrospectionBean()).execute(args);
    System.exit(exitCode);
  }

  @Override
  public Integer call() throws IOException {
    System.out.printf("Insert introspection bean into %s%n", location);

    String sourceFile = fetchSourceFile();
    InputStream completeSource = setPackage(sourceFile);
    copyFile(completeSource);

    return 0;
  }

  private String fetchSourceFile() throws IOException {
    InputStream inputStream = Optional.ofNullable(
            InsertIntrospectionBean.class.getResourceAsStream("/grpc/" + FILE_NAME))
        .orElseThrow(() -> new IllegalStateException("Source file not found!"));

    return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
  }

  private InputStream setPackage(String source) {
    String evaluatedPackageName =
        Optional.ofNullable(packageName)
            .orElse(evaluatePackageFromLocation());

    String newSource = source.replace("@packageName@", evaluatedPackageName);

    return new ByteArrayInputStream(newSource.getBytes(StandardCharsets.UTF_8));
  }

  private String evaluatePackageFromLocation() {
    return IntStream.range(4, location.getNameCount())
            .mapToObj(i -> location.getName(i))
            .map(Path::toString)
            .reduce((p1, p2) -> p1 + "." + p2)
            .orElse("");
  }

  private void copyFile(InputStream source) throws IOException {
    Files.copy(source, location.resolve(FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
  }
}
