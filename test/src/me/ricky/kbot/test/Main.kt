package me.ricky.kbot.test

import me.ricky.kbot.core.KBot
import me.ricky.kbot.core.command.*
import me.ricky.kbot.core.data.MongoHandler
import me.ricky.kbot.core.data.MongoPrefixHandler
import me.ricky.kbot.core.data.PrefixHandler
import me.ricky.kbot.core.util.register
import org.javacord.api.entity.permission.PermissionType

suspend fun main() {
  val bot = TestBot()
  val api = bot.loginAsync().await()

  println(api)
}

class TestBot : KBot() {
  val mongoHandler = MongoHandler(config.mongodb)
  val prefixHandler = MongoPrefixHandler(mongoHandler, ">")
  val commandHandler = MultiCommandHandler()

  override suspend fun initialize() {
    registerCommandHandlers()
    registerCommands()
  }

  fun registerCommandHandlers() {
    commandHandler.register(ServerCommandHandler(prefixHandler))

    builder.register(commandHandler)
  }

  fun registerCommands() {
    commandHandler.register(TestCommand(prefixHandler))
  }
}

class TestCommand(private val prefixHandler: PrefixHandler) : Command<ServerCommandContext> {
  override val info: CommandInfo = CommandInfo(
    botPermissions = setOf(PermissionType.ADMINISTRATOR),
    userPermissions = setOf(PermissionType.ADMINISTRATOR),
    name = "test",
    description = "Testing Command",
    aliases = setOf("t"),
    arguments = listOf(
      required("name"),
      optional("int")
    )
  )

  override suspend fun execute(context: ServerCommandContext) {
    with(context) {
      val name = arguments.requireString()
      val int =  arguments.requireInt()
      val long = arguments.requireLong()

      channel.sendMessage("$name $int $long")
    }
  }
}