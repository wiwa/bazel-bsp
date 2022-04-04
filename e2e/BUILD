# we cannot use `bazel test ...` because testrunner blocks bazel deamon,
# but testing server needs it for queries and etc

load(":e2e_test.bzl", "e2e_test")

#e2e_test(
#    name = "sample-repo",
#    repo = "test-resources/sample-repo",
#    test_srcs = ["//e2e/src/main/java/org/jetbrains/bsp/bazel:TestableTest.java"],
#    deps = [
#        "//commons",
#        "//e2e/src/main/java/org/jetbrains/bsp/bazel/base",
#        "@maven//:ch_epfl_scala_bsp4j",
#        "@maven//:com_google_guava_guava",
#        "@maven//:org_apache_logging_log4j_log4j_api",
#        "@maven//:org_apache_logging_log4j_log4j_core",
#        "@testkit//:testkit-client",
#    ],
#)

genrule(
    name = "installer",
    srcs = [],
    outs = [
        ".bazelbsp/aspects.bzl",
        ".bazelbsp/BUILD",
        ".bazelbsp/default-projectview.bazelproject",
        ".bsp/bazelbsp.json",
    ],
    cmd = "$(location //server/src/main/java/org/jetbrains/bsp/bazel:bsp-install) -d $(RULEDIR)",
    tools = ["//server/src/main/java/org/jetbrains/bsp/bazel:bsp-install"],
    visibility = ["//visibility:public"],
)

filegroup(
    name = "resources",
    srcs = glob(
        ["test-resources/sample-repo/**"],
        exclude_directories = 0,
    ),
    visibility = ["//visibility:public"],
)
#
#genrule(
#    name = "install_server",
#    srcs = [],
#    outs = [
#        ".bazelbsp/aspects.bzl",
#        ".bazelbsp/BUILD",
#        ".bazelbsp/default-projectview.bazelproject",
#        ".bsp/bazelbsp.json",
#    ],
#    cmd = "$(location //server/src/main/java/org/jetbrains/bsp/bazel:bsp-install) -d $(RULEDIR)",
#    output_to_bindir = 1,
#    tools = ["//server/src/main/java/org/jetbrains/bsp/bazel:bsp-install"],
#    visibility = ["//visibility:public"],
#)
#
#genrule(
#  name = 'copy_the_files',
#  srcs = [":sample-repo"],
#  outs = [f.replace('the_directory', 'another_place/folder') for f in files],
#  cmd = '\n'.join(['mkdir -p $$(dirname $(location %s)) && cp $(location %s) $(location :%s)' % (f, f, f.replace('the_directory', 'another_place/folder')) for f in files]),
#)

#files = glob(
#    ["test-resources/sample-repo/**"],
#    exclude_directories = 0,
#)
#
#genrule(
#    name = "copy_the_files",
#    srcs = files,
#    outs = [f.replace("test-resources/sample-repo", "e2e") for f in files],
#    cmd = "\n".join(["mkdir -p $$(dirname $(location %s)) && cp $(location %s) $(location :%s)" % (
#        f,
#        f,
#        f.replace("test-resources/sample-repo", "e2e"),
#    ) for f in files]),
#    visibility = ["//visibility:public"],
#)

#filegroup(
#    name = "sample-repo",
#    srcs = glob(
#        ["test-resources/sample-repo/**"],
#        exclude_directories = 0,
#    ),
#    path = "",
#    visibility = ["//visibility:public"],
#)

sh_test(
    name = "BazelBspSampleRepoTest",
    srcs = ["runTest.sh"],
    args = [
        "//e2e/src/main/java/org/jetbrains/bsp/bazel:BazelBspSampleRepoTest",
        "e2e/test-resources/sample-repo",
    ],
    data = [":install_server"],
)

sh_binary(
    name = "BazelBspActionGraphV1Test",
    srcs = ["runTest.sh"],
    args = [
        "//e2e/src/main/java/org/jetbrains/bsp/bazel:BazelBspActionGraphV1Test",
        "e2e/test-resources/sample-repo",
    ],
)

sh_binary(
    name = "BazelBspActionGraphV2Test",
    srcs = ["runTest.sh"],
    args = [
        "//e2e/src/main/java/org/jetbrains/bsp/bazel:BazelBspActionGraphV2Test",
        "e2e/test-resources/action-graph-v2",
    ],
)

sh_binary(
    name = "BazelBspJava8ProjectTest",
    srcs = ["runTest.sh"],
    args = [
        "//e2e/src/main/java/org/jetbrains/bsp/bazel:BazelBspJava8ProjectTest",
        "e2e/test-resources/java-8-project",
    ],
)

sh_binary(
    name = "BazelBspJava11ProjectTest",
    srcs = ["runTest.sh"],
    args = [
        "//e2e/src/main/java/org/jetbrains/bsp/bazel:BazelBspJava11ProjectTest",
        "e2e/test-resources/java-11-project",
    ],
)

sh_binary(
    name = "BazelBspCppProjectTest",
    srcs = ["runTest.sh"],
    args = [
        "//e2e/src/main/java/org/jetbrains/bsp/bazel:BazelBspCppProjectTest",
        "e2e/test-resources/cpp-project",
    ],
)

sh_binary(
    name = "BazelBspEntireRepositoryImportTest",
    srcs = ["runTest.sh"],
    args = [
        "//e2e/src/main/java/org/jetbrains/bsp/bazel:BazelBspEntireRepositoryImportTest",
    ],
)

# simply a wrapper for the bash script to keep the convention
sh_binary(
    name = "all",
    srcs = ["runAll.sh"],
)
