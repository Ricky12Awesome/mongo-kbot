package me.ricky.kbot.test

import me.ricky.kbot.core.KBot
import me.ricky.kbot.core.command.*
import me.ricky.kbot.core.data.MongoDBHandler
import me.ricky.kbot.core.data.RuntimePrefixHandler
import me.ricky.kbot.core.data.model.ServerDocument
import me.ricky.kbot.core.util.jsonx
import me.ricky.kbot.core.util.register
import org.javacord.api.entity.permission.PermissionType
import org.litote.kmongo.findOneById
import org.litote.kmongo.getCollectionOfName
import org.litote.kmongo.replaceOneById

suspend fun main() {
  val bot = TestBot()
  val api = bot.loginAsync().await()

  println(api)
}

class TestBot : KBot() {
  val prefixHandler = RuntimePrefixHandler(">")
  val commandHandler = MultiCommandHandler()
  // val mongoHandler = MongoDBHandler(config.mongodb)

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
    botPermissions = setOf(PermissionType.ADMINISTRATOR),
    userPermissions = setOf(PermissionType.ADMINISTRATOR),
    name = "test",
    description = "Testing Command",
    aliases = setOf("t"),
    arguments = listOf(
      required("name"),
      optional("user")
    )
  )

  override suspend fun execute(context: ServerCommandContext) {
    context.channel.sendMessage(
      buildString {
        appendln("```json")
        appendln(jsonx.stringify(CommandInfo.serializer(), info))
        appendln("```")
      })
  }
}