package me.ricky.kbot.core.data

import me.ricky.kbot.core.data.model.ServerDocument
import me.ricky.kbot.core.util.JavaCordHandler
import me.ricky.kbot.core.util.asNullable
import me.ricky.kbot.core.util.getOrSet
import me.ricky.kbot.core.util.register
import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.server.Server
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener
import org.litote.kmongo.save
import org.litote.kmongo.updateOneById

/**
 * Handles prefixes for servers
 *
 * @property default Default prefix for server
 */
interface PrefixHandler : MessageCreateListener, JavaCordHandler {
  val default: String

  /**
   * Get the prefix for [Server]
   *
   * @param id Id of [Server]
   */
  fun getPrefix(id: Long): String

  /**
   * @see getPrefix
   */
  fun getPrefix(server: Server): String = getPrefix(server.id)

  /**
   * Set the prefix for [Server]
   *
   * @param id Id of [Server]
   * @param prefix New prefix for [Server]
   */
  fun setPrefix(id: Long, prefix: String)

  /**
   * @see setPrefix
   */
  fun setPrefix(server: Server, prefix: String) = setPrefix(server.id, prefix)

  override fun onMessageCreate(event: MessageCreateEvent) {
    with(event) {
      val server = server.asNullable ?: return

      if (messageContent == api.yourself.nicknameMentionTag) {
        channel.sendMessage("Prefix: `${getPrefix(server)}`")
      }
    }
  }

  override fun register(api: DiscordApiBuilder) {
    api.addMessageCreateListener(this)
  }
}

/**
 * Prefixes are stored at runtime and gets reset when the application restarts.
 *
 * @param default Default Prefix
 */
class RuntimePrefixHandler(override val default: String) : PrefixHandler {
  private val cache = mutableMapOf<Long, String>()

  override fun getPrefix(id: Long): String {
    return cache.getOrSet(id, default)
  }

  override fun setPrefix(id: Long, prefix: String) {
    cache[id] = prefix
  }
}

/**
 * Prefixes are stored with MongoDB
 *
 * @param mongoHandler [MongoHandler] to get prefixes from mongo
 * @param default Default Prefix
 */
class MongoPrefixHandler(
  private val mongoHandler: MongoHandler,
  override val default: String
) : PrefixHandler {
  private val cache = mutableMapOf<Long, String>()

  override fun getPrefix(id: Long): String {
    return cache.getOrPut(id) {
      mongoHandler.getServerOrNull(id)?.prefix ?: default
    }
  }

  override fun setPrefix(id: Long, prefix: String) {
    val server = mongoHandler.getServerOrNull(id) ?: ServerDocument(serverId = id, prefix = prefix)

    mongoHandler.serverCollection.save(server)

    cache[id] = prefix
  }

}