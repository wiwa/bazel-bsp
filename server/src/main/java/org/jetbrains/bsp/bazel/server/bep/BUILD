load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "bep",
    srcs = glob(["*.java"]),
    resources = ["//log4j_config"],
    visibility = ["//server:__subpackages__"],
    deps = [
        "//bazelrunner",
        "//commons",
        "//logger",
        "//server/src/main/java/org/jetbrains/bsp/bazel/server/diagnostics",
        "@com_google_protobuf//:protobuf_java",
        "@googleapis//:google_devtools_build_v1_build_events_java_proto",
        "@googleapis//:google_devtools_build_v1_publish_build_event_java_grpc",
        "@googleapis//:google_devtools_build_v1_publish_build_event_java_proto",
        "@googleapis//:google_devtools_build_v1_publish_build_event_proto",
        "@io_bazel//src/main/java/com/google/devtools/build/lib/buildeventstream/proto:build_event_stream_java_proto",
        "@io_bazel//third_party/grpc:grpc-jar",
        "@io_bazel_rules_scala//src/protobuf/io/bazel/rules_scala:diagnostics_java_proto",
        "@maven//:ch_epfl_scala_bsp4j",
        "@maven//:com_google_guava_guava",
        "@maven//:io_vavr_vavr",
        "@maven//:org_apache_logging_log4j_log4j_api",
        "@maven//:org_apache_logging_log4j_log4j_core",
    ],
)
