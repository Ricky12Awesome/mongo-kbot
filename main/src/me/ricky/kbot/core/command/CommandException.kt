package me.ricky.kbot.core.command

open class CommandException(message: String) : Throwable(message)

open class CommandExceptionHandler(
  private val context: CommandContext
) {
  open fun checkNotEnoughArguments(index: Int) {
    if (index > context.args.lastIndex) {
      throw CommandException(
        message = "Not Enough Arguments."
      )
    }
  }

  open fun checkTooManyArguments(index: Int) {
    if (context.args.size > index) {
      throw CommandException(
        message = "Too Many Arguments."
      )
    }
  }

  open fun parseException(parsing: String, to: String): CommandException {
    return CommandException("Failed to parse $parsing to $to.")
  }
}