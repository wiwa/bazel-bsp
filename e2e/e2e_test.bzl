load("//:junit5.bzl", "java_junit5_test")

def e2e_test(name, repo, test_srcs, deps):
    installer = name + "_installer"
    resources = name + "_resources"

    java_junit5_test(
        name = name,
        srcs = test_srcs,
        resources = ["//e2e/src/main/resources:bsp-e2e-resources"],
        test_package = "org.jetbrains.bsp.bazel",
        visibility = ["//e2e:__subpackages__"],
        data = [":" + installer, ":" + resources],
        deps = deps,
    )

    native.filegroup(
        name = resources,
        srcs = native.glob(
            [repo + "/**"],
            exclude_directories = 0,
        ),
        visibility = ["//visibility:public"],
    )

    native.genrule(
        name = installer,
        srcs = [],
        outs = [
            repo + "/.bazelbsp/aspects.bzl",
            repo + "/.bazelbsp/BUILD",
            repo + "/.bazelbsp/default-projectview.bazelproject",
            repo + "/.bsp/bazelbsp.json",
        ],
        cmd = "$(location //server/src/main/java/org/jetbrains/bsp/bazel:bsp-install) -d $(BINDIR)/" + repo,
        tools = ["//server/src/main/java/org/jetbrains/bsp/bazel:bsp-install"],
    )
