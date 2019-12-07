package me.ricky.kbot.core.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId

@Serializable
data class ServerDocument(
  @BsonId val serverId: Long,
  val prefix: String = ">",
  val members: Map<Long, MemberDocument> = mapOf()
)

@Serializable
data class MemberDocument(
  val xp: Double = 0.0
)