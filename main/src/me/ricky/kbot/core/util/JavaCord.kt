package me.ricky.kbot.core.util

import me.ricky.kbot.core.KBot
import org.javacord.api.DiscordApiBuilder

/**
 * Simple Handler Registerer
 */
interface JavaCordHandler {
  /**
   * @param api [DiscordApiBuilder] to register too
   */
  fun register(api: DiscordApiBuilder)
}

/**
 * Registers a [JavaCordHandler] for [DiscordApiBuilder]
 *
 * @param handler [JavaCordHandler] to register
 */
fun DiscordApiBuilder.register(handler: JavaCordHandler) = handler.register(this)

/**
 * Registers a [JavaCordHandler] for [DiscordApiBuilder]
 *
 * @param handler [JavaCordHandler] to register
 */
fun DiscordApiBuilder.register(handler: () -> JavaCordHandler) = handler().register(this)

/**
 * Registers a [JavaCordHandler] for [KBot.builder]
 *
 * @param handler [JavaCordHandler] to register
 */
fun KBot.register(handler: JavaCordHandler) = builder.register(handler)
