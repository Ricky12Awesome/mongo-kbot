package me.ricky.kbot.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId

/**
 * @param serverId Id of server
 * @param prefix Prefix for server
 * @param members Set of all members that have data
 */
@Serializable
data class ServerDocument(
  @BsonId
  @SerialName("_id")
  val serverId: Long,
  val prefix: String = ">",
  val members: Set<MemberDocument> = setOf(),
  val channels: Set<ServerChannel> = setOf()
)

/**
 * @param memberId Id of member
 * @param xp Amount of xp for member
 */
@Serializable
data class MemberDocument(
  @BsonId
  @SerialName("_id")
  val memberId: Long,
  val xp: Double = 0.0
)

/**
 * @param isBlacklisted If the channel is blacklisted, commands can't execute in this channel.
 */
@Serializable
data class ServerChannel(
  val isBlacklisted: Boolean = false
)