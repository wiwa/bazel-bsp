package org.jetbrains.bsp.bazel.server.diagnostics

object BazelRootMessageParser : Parser {
  private const val TargetLabel = """(//[\w/.-]*:[\w/.-]+)"""

  override fun tryParse(output: Output): List<Diagnostic> =
      findErrorInBUILD(output) ?: findWarningsInInfoMessage(output) ?: emptyList()

  // .*(pattern)?.* will never match `pattern` as it is optional
  // workaround is to wrap it into non capturing group (?:.*(pattern)).*
  // This approach was used to find optional target label inside the message
  private val ErrorInBUILD = """
      ^               # start of line
      ERROR:\         # error indicator
      ([^:]+/BUILD)   # path to BUILD file (1)
      :(\d+)          # line number (2)
      (?::(\d+))?     # optional column number (3)
      :\              # ": " separator
      (?:.*           # part of actual error message wrapped with label into optional group
      $TargetLabel    # target label (4)
      )?              # make target label optional
      .*              # part of actual error message
      $               # end of line
      """.toRegex(RegexOption.COMMENTS)

  private fun findErrorInBUILD(output: Output): List<Diagnostic>? {
    return output.tryTake(ErrorInBUILD)
        ?.let { match ->
          val targetLabel = match.groupValues[4].ifEmpty { null }
          return collectCompilerDiagnostics(output, targetLabel)
              .ifEmpty { listOf(createError(match, targetLabel)) }
        }
  }

  private fun createError(match: MatchResult, targetLabel: String?): Diagnostic {
    val message = match.value
    val path = match.groupValues[1]
    val line = match.groupValues[2].toInt()
    val column = match.groupValues[3].toIntOrNull() ?: 1
    return Diagnostic(Position(line, column), message, Level.Error, path, targetLabel)
  }

  private val InfoMessage = """
      ^               # start of line
      INFO:\          # info indicator
      .*?             # part of actual message
      $TargetLabel    # target label (1)
      .*              # part of actual message
      $               # end of line
    """.toRegex(RegexOption.COMMENTS)

  private fun findWarningsInInfoMessage(output: Output): List<Diagnostic>? {
    return output.tryTake(InfoMessage)
        ?.let { match ->
          val targetLabel = match.groupValues[1]
          return collectCompilerDiagnostics(output, targetLabel)
        }
  }

  private fun collectCompilerDiagnostics(output: Output, targetLabel: String?) =
      generateSequence { CompilerDiagnosticParser.tryParseOne(output, targetLabel) }.toList()
}
