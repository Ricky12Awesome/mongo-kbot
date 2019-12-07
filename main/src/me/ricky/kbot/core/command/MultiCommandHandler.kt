package me.ricky.kbot.core.command

import org.javacord.api.DiscordApiBuilder
import kotlin.reflect.KClass

/**
 * Handles multiple of [CommandHandler] as a single handler.
 * Uses type information to differentiate between handlers.
 *
 * @property handlers Gets all the handlers
 */
class MultiCommandHandler : CommandHandler<CommandContext> {
  private val _handlers = mutableMapOf<KClass<*>, AbstractCommandHandler<CommandContext>>()
  val handlers: Map<KClass<*>, AbstractCommandHandler<CommandContext>> = _handlers

  override val commands: Map<String, Command<CommandContext>> = _handlers.values
    .fold(mapOf()) { map, handler -> map + handler.commands }

  /**
   * Registers an [AbstractCommandHandler] for type [C]
   *
   * @param handler [AbstractCommandHandler] to register
   */
  inline fun <reified C : CommandContext> register(handler: AbstractCommandHandler<C>) {
    registerHandler(C::class, handler)
  }

  /**
   * Registers a [Command] for type [C]
   *
   * @param command [Command] to register
   */
  inline fun <reified C : CommandContext> register(command: Command<C>) {
    val handler = handlerOf<C>()

    handler.register(command)
  }

  /**
   * Unregisters a [Command] for type [C]
   *
   * @param command [Command] to unregister
   */
  inline fun <reified C : CommandContext> unregister(command: Command<C>) {
    val handler = handlerOf<C>()

    handler.unregister(command)
  }

  /**
   * Gets the handler of type [C]
   *
   * @throws IllegalStateException if handler doesn't exist
   *
   * @return [DiscordApiBuilder] for type [C]
   */
  @Suppress("UNCHECKED_CAST")
  inline fun <reified C : CommandContext> handlerOf(): AbstractCommandHandler<C> {
    val handler = handlers[C::class] ?: error("Handler of ${C::class} not found")

    return handler as AbstractCommandHandler<C>
  }

  /**
   * Registers an [AbstractCommandHandler] for type [C]
   *
   * @param kClass [KClass] of type [C]
   * @param handler [AbstractCommandHandler] to register
   */
  @Suppress("UNCHECKED_CAST")
  fun <C : CommandContext> registerHandler(kClass: KClass<C>, handler: AbstractCommandHandler<C>) {
    handler as AbstractCommandHandler<CommandContext>

    if (_handlers.values.contains(handler)) {
      error("$handler is already registered.")
    }

    _handlers[kClass] = handler
  }

  /**
   * Registers all the [handlers] for [api]
   *
   * @param api [DiscordApiBuilder] to register too
   */
  override fun register(api: DiscordApiBuilder) {
    _handlers.values.forEach {
      it.register(api)
    }
  }
}