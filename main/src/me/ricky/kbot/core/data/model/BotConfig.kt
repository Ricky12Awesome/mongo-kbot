package me.ricky.kbot.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BotConfig(
  val token: String,
  val mongodb: MongoConfig
)

@Serializable
data class MongoConfig(
  val connection: String,
  val database: String
)