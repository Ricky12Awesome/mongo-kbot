package me.ricky.kbot.core.data

import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import me.ricky.kbot.core.data.model.MongoConfig
import org.litote.kmongo.KMongo

class MongoDBHandler(val config: MongoConfig) {
  val client: MongoClient = KMongo.createClient(config.connection)
  val database: MongoDatabase = client.getDatabase(config.database)
}