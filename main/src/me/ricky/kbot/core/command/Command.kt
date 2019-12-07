package me.ricky.kbot.core.command

import kotlinx.serialization.Serializable
import me.ricky.kbot.core.util.JavaCordHandler
import org.javacord.api.entity.permission.PermissionType
import org.javacord.api.event.message.MessageCreateEvent

/**
 * @param prefix Prefix for [CommandArgument]
 * @param suffix Suffix for [CommandArgument]
 */
@Serializable
enum class CommandArgumentType(
  val prefix: String,
  val suffix: String
) {
  OPTIONAL("[", "]"), REQUIRED("<", ">")
}

/**
 * @param type Type of the argument
 * @param name Name of the argument
 */
@Serializable
data class CommandArgument(
  val type: CommandArgumentType,
  val name: String
) {
  inline val value get() = "${type.prefix}$name${type.suffix}"
}

/**
 * @param name Name of the command, used as the command key for [CommandHandler]
 */
@Serializable
data class CommandInfo(
  val name: String,
  val description: String,
  val aliases: Set<String> = setOf(),
  val arguments: List<CommandArgument> = listOf(),
  val botPermissions: Set<PermissionType> = setOf(),
  val userPermissions: Set<PermissionType> = setOf()
)

/**
 * Context for [Command]
 *
 * @param args Arguments for the command
 * @param event [MessageCreateEvent] to delegate from
 */
open class CommandContext(
  val args: List<String>,
  event: MessageCreateEvent
) : MessageCreateEvent by event

/**
 * @property info Gets information about the command
 */
interface Command<C : CommandContext> {
  val info: CommandInfo

  suspend fun execute(context: C)
}

/**
 * @property commands Gets a map of commands, with the name being the key.
 */
interface CommandHandler<C : CommandContext> : JavaCordHandler {
  val commands: Map<String, Command<C>>
}