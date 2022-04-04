package org.jetbrains.bsp.bazel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.epfl.scala.bsp4j.BuildClientCapabilities;
import ch.epfl.scala.bsp4j.InitializeBuildParams;
import com.google.devtools.build.runfiles.Runfiles;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.bsp.bazel.base.BazelBspTestBaseScenario;
import org.jetbrains.bsp.testkit.client.TestClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestableTest {

    private static final Logger LOGGER = LogManager.getLogger(BazelBspTestBaseScenario.class);

    @BeforeAll
    public static void setUp() throws IOException {
        Runfiles runfiles = Runfiles.create();
        String path = runfiles.rlocation(".bazelbsp/aspects.bzl");
        LOGGER.info("Running setUp");
        LOGGER.info("path: " + path);
    }

    @Test
    public void testResolve() throws IOException {
        LOGGER.info("Running testResolve");
        var BSP_VERSION = "0.0.1";
        var workspacePath  = Paths.get("").toAbsolutePath();
        var capabilities = new BuildClientCapabilities(List.of("java", "scala", "kotlin", "cpp"));
        var initializeBuildParams =
                new InitializeBuildParams(
                        "BspTestClient", "1.0.0", BSP_VERSION, workspacePath.toString(), capabilities);
        var client = new TestClient(workspacePath, initializeBuildParams);
        // list contents of the current folder
        Path currentRelativePath = Paths.get("e2e/test-resources/sample-repo");
        Files.list(currentRelativePath).forEach(System.out::println);


        client.testResolveProject(Duration.ofSeconds(100));

    }

}
