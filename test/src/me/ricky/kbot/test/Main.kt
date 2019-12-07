package me.ricky.kbot.test

import me.ricky.kbot.core.KBot
import me.ricky.kbot.core.command.*
import me.ricky.kbot.core.data.MongoDBHandler
import me.ricky.kbot.core.data.RuntimePrefixHandler
import me.ricky.kbot.core.data.model.ServerDocument
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
  val mongoHandler = MongoDBHandler(config.mongodb)

  override suspend fun initialize() {
    registerCommands()

    val col = mongoHandler.database.getCollectionOfName<ServerDocument>("server")

    col.insertOne(ServerDocument(0L))

    val server = col.findOneById(0L) ?: error("Unable to find ServerDocument by id 0L")

    col.replaceOneById(0L, server.copy(prefix = "!"))

    val newServer = col.findOneById(0L)

    println(newServer)
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
    context.channel.sendMessage("OOF")
  }
}