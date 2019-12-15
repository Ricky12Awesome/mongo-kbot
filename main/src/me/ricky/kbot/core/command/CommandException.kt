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

open class ServerCommandExceptionHandler(
  private val context: ServerCommandContext
) : CommandExceptionHandler(context) {
  open fun checkBotPermissions(info: CommandInfo) {
    with(context) {
      val hasPermissions = server
        .getAllowedPermissions(api.yourself)
        .containsAll(info.botPermissions)

      if (!hasPermissions) {
        throw CommandException("${api.yourself.mentionTag} doesn't have permissions.")
      }
    }
  }

  open fun checkUserPermissions(info: CommandInfo) {
    with(context) {
      val hasPermissions = server
        .getAllowedPermissions(author)
        .containsAll(info.userPermissions)

      if (!hasPermissions) {
        throw CommandException("${author.mentionTag} doesn't have permissions.")
      }
    }
  }
}