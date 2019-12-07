package me.ricky.kbot.test

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.future.asDeferred
import me.ricky.kbot.core.command.*
import me.ricky.kbot.core.data.RuntimePrefixHandler
import me.ricky.kbot.core.util.register
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder

suspend fun main() {
  val bot = KBot(System.getenv("token"))
  val api = bot.startAsync().await()

  println(api)
}

class KBot(token: String) {
  val builder: DiscordApiBuilder = DiscordApiBuilder().setToken(token)
  val prefixHandler = RuntimePrefixHandler(">")
  val commandHandler = MultiCommandHandler()

  fun startAsync(): Deferred<DiscordApi> {
    registerCommands()

    return builder.login().asDeferred()
  }

  fun registerCommands() {
    commandHandler.register(ServerCommandHandler(prefixHandler))

    commandHandler.register(TestCommand())

    builder.register(commandHandler)
  }
}

class TestCommand : Command<ServerCommandContext> {
  override val info: CommandInfo = CommandInfo(
    name = "test"
  )

  override suspend fun execute(context: ServerCommandContext) {
    context.channel.sendMessage("OOF")
  }
}