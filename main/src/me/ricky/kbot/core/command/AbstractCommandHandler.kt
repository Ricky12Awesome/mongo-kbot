package me.ricky.kbot.core.command

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.ricky.kbot.core.data.PrefixHandler
import me.ricky.kbot.core.util.asNullable
import org.javacord.api.DiscordApiBuilder
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener

/**
 * @param prefixHandler [PrefixHandler] to get prefixes for commands
 */
abstract class AbstractCommandHandler<C : CommandContext>(
  private val prefixHandler: PrefixHandler
) : CommandHandler<C>, MessageCreateListener {
  private val _commands = mutableMapOf<String, Command<C>>()
  override val commands: Map<String, Command<C>> = _commands

  /**
   * Registers a [Command]
   *
   * @param command [Command] to register
   */
  fun register(command: Command<C>) {
    _commands[command.info.name] = command
  }

  /**
   * Unregisters a [Command]
   *
   * @param command [Command] to unregister
   */
  fun unregister(command: Command<C>) {
    _commands.remove(command.info.name)
  }

  /**
   * Gets the [C] that is needed to execute the [Command]
   *
   * @param args Arguments for the command
   */
  abstract fun MessageCreateEvent.getContext(args: List<String>): C?

  /**
   * Executes a [command]
   *
   * @param command Command to be executed
   * @receiver CommandContext to execute the command with
   */
  abstract suspend fun C.executeCommand(command: Command<C>)

  /**
   * Handles the execution for commands
   *
   * @param event [MessageCreateEvent] to trigger the event
   */
  final override fun onMessageCreate(event: MessageCreateEvent) {
    val content = event.messageContent
    val prefix = prefixHandler.getPrefix(event.server.asNullable?.id ?: -1)

    if (!content.startsWith(prefix)) return

    val args = content.removePrefix(prefix).split(" ")
    val command = getCommandByNameOrAliases(args[0]) ?: return
    val context = event.getContext(args.drop(1)) ?: return

    GlobalScope.launch {
      try {
        context.executeCommand(command)
      } catch (throwable: Throwable) {
        when (throwable) {
          is CommandException -> context.channel.sendMessage(throwable.message)
          else -> {
            throwable.printStackTrace()
            context.channel.sendMessage("An unknown error has occurred. ${throwable.message}")
          }
        }
      }
    }
  }

  /**
   * @param api [DiscordApiBuilder] the register too
   */
  final override fun register(api: DiscordApiBuilder) {
    api.addMessageCreateListener(this)
  }
}