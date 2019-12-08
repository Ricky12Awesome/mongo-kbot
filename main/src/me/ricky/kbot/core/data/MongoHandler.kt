package me.ricky.kbot.core.data

import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import me.ricky.kbot.core.data.model.MongoConfig
import me.ricky.kbot.core.data.model.ServerDocument
import org.javacord.api.entity.server.Server
import org.litote.kmongo.KMongo
import org.litote.kmongo.findOneById
import org.litote.kmongo.getCollectionOfName

/**
 * @param config Config for connection and database
 *
 * @property client [MongoClient] of [MongoConfig.connection]
 * @property database [MongoDatabase] of [MongoConfig.database]
 * @property serverCollection [MongoCollection] of [ServerDocument]
 */
class MongoHandler(val config: MongoConfig) {
  val client: MongoClient = KMongo.createClient(config.connection)
  val database: MongoDatabase = client.getDatabase(config.database)

  inline val serverCollection: MongoCollection<ServerDocument>
    get() = database.getCollectionOfName("server")

  /**
   * @param server [Server] to get id from
   *
   * @return [ServerDocument] of [server] or null if it doesn't exist
   */
  fun getServerOrNull(server: Server) = getServerOrNull(server.id)

  /**
   * @param id Id of [ServerDocument]
   *
   * @return [ServerDocument] of [id] or null if it doesn't exist
   */
  fun getServerOrNull(id: Long) = serverCollection.findOneById(id)

  /**
   * @param server [Server] to get id from
   *
   * @return [ServerDocument] of [server] or throws [KotlinNullPointerException]
   *
   * @throws KotlinNullPointerException If value is null
   */
  fun getServer(server: Server) = getServerOrNull(server)!!

  /**
   * @param id Id of [ServerDocument]
   *
   * @return [ServerDocument] of [id] or throws [KotlinNullPointerException]
   *
   * @throws KotlinNullPointerException If value is null
   */
  fun getServer(id: Long) = getServerOrNull(id)!!
}
