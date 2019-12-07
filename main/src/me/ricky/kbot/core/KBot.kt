package me.ricky.kbot.core

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.future.asDeferred
import me.ricky.kbot.core.data.model.BotConfig
import me.ricky.kbot.core.util.jsonx
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

abstract class KBot(
  val configPath: Path = Paths.get("./config.json")
) {
  val config: BotConfig = Files.newBufferedReader(configPath).use {
    jsonx.parse(BotConfig.serializer(), it.readText())
  }

  val builder: DiscordApiBuilder = DiscordApiBuilder().setToken(config.token)

  abstract suspend fun initialize()

  suspend fun loginAsync(): Deferred<DiscordApi> {
    initialize()

    return builder.login().asDeferred()
  }
}