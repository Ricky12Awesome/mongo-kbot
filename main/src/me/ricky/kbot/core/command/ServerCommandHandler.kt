package me.ricky.kbot.core.command

import me.ricky.kbot.core.data.PrefixHandler
import me.ricky.kbot.core.util.asNullable
import org.javacord.api.entity.channel.ServerTextChannel
import org.javacord.api.entity.server.Server
import org.javacord.api.entity.user.User
import org.javacord.api.event.message.MessageCreateEvent

/**
 * Handles positions for command arguments
 *
 * @param context [ServerCommandContext] to be used for arguments
 */
open class ServerCommandArgumentsHandler(
  override val context: ServerCommandContext
) : CommandArgumentsHandler(context)

/**
 * Command Context for [ServerCommandContext]
 *
 * @param server [Server] where the command was executed from
 * @param author [User] of who executed the command
 * @param channel [ServerTextChannel] where the command was executed from
 * @param args Arguments for the command
 * @param event [MessageCreateEvent] to delegate from
 */
open class ServerCommandContext(
  val server: Server,
  val author: User,
  val channel: ServerTextChannel,
  args: List<String>,
  event: MessageCreateEvent
) : CommandContext(args, event) {
  override val arguments: ServerCommandArgumentsHandler by lazy { ServerCommandArgumentsHandler(this) }
}

/**
 * Handles Commands for servers, Commands must use [ServerCommandContext]
 *
 * @param prefixHandler [PrefixHandler] to get prefixes for commands
 */
class ServerCommandHandler(
  prefixHandler: PrefixHandler
) : AbstractCommandHandler<ServerCommandContext>(prefixHandler) {
  override fun MessageCreateEvent.getContext(
    args: List<String>
  ): ServerCommandContext? {
    return ServerCommandContext(
      server = server.asNullable ?: return null,
      author = messageAuthor.asUser().asNullable ?: return null,
      channel = serverTextChannel.asNullable ?: return null,
      args = args,
      event = this
    )
  }
}

