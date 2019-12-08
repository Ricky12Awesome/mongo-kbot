package me.ricky.kbot.core.data.model

import kotlinx.serialization.Serializable
import me.ricky.kbot.core.KBot

/**
 * @param token Token for [KBot]
 * @param mongodb [MongoConfig] for mongodb
 */
@Serializable
data class BotConfig(
  val token: String,
  val mongodb: MongoConfig
)

/**
 * @param connection Connection [String] for mongodb
 * @param database Name of the database to connect too
 */
@Serializable
data class MongoConfig(
  val connection: String,
  val database: String
)