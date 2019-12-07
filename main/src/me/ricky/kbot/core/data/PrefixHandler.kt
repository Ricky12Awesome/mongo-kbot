package me.ricky.kbot.core.data

import me.ricky.kbot.core.util.getOrSet
import org.javacord.api.entity.server.Server

/**
 * Handles prefixes for servers
 */
interface PrefixHandler {
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
}

/**
 * Prefixes are stored at runtime and gets reset when the application restarts.
 *
 * @param default Default prefix
 */
class RuntimePrefixHandler(private val default: String) : PrefixHandler {
  private val cache = mutableMapOf<Long, String>()

  override fun getPrefix(id: Long): String {
    return cache.getOrSet(id, default)
  }

  override fun setPrefix(id: Long, prefix: String) {
    cache[id] = prefix
  }
}