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
    registerHandlers()
    registerCommands()
  }

  fun registerHandlers() {
    registerCommandHandlers()

    register(prefixHandler)
  }

  fun registerCommandHandlers() {
    commandHandler.register(ServerCommandHandler(prefixHandler))

    register(commandHandler)
  }

  fun registerCommands() {
    commandHandler.register(TestCommand(prefixHandler))
  }
}

class TestCommand(private val prefixHandler: PrefixHandler) : Command<ServerCommandContext> {
  override val info: CommandInfo = info("test") {
    description = "123"
    aliases = setOf("a", "b", "c")
    botPermissions = setOf(PermissionType.ADMINISTRATOR)
    userPermissions = setOf(PermissionType.ADMINISTRATOR)

    arguments {
      require("prefix")
    }
  }

  override suspend fun execute(context: ServerCommandContext) {
    with(context) {
      val prefix = arguments.optionalPrefix()

      if (prefix == null) {
        channel.sendMessage("Current Prefix: `${prefixHandler.getPrefix(server)}`")
      } else {
        prefixHandler.setPrefix(context.server, prefix)

        channel.sendMessage("Prefix changed to `$prefix`")
      }
    }
  }

  fun CommandArgumentsHandler.optionalPrefix(): String? {
    val prefix = context.optionalArgument(null, context::requireString) ?: return null

    if (prefix.contains("`")) {
      throw CommandException("Prefix must not contain `")
    }

    if (prefix.length > 4) {
      throw CommandException("Prefix is too long, must be less than 4.")
    }

    return prefix
  }
}