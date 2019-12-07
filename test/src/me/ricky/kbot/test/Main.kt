package me.ricky.kbot.test

import me.ricky.kbot.core.KBot
import me.ricky.kbot.core.command.*
import me.ricky.kbot.core.data.RuntimePrefixHandler
import me.ricky.kbot.core.util.register

suspend fun main() {
  val bot = TestBot()
  val api = bot.loginAsync().await()

  println(api)
}

class TestBot : KBot() {
  val prefixHandler = RuntimePrefixHandler(">")
  val commandHandler = MultiCommandHandler()

  override suspend fun initialize() {
    registerCommands()
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