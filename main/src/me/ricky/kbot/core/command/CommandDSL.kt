package me.ricky.kbot.core.command

import kotlin.properties.Delegates.observable
import kotlin.reflect.KProperty

@DslMarker
annotation class CommandDSLContext

@DslMarker
annotation class CommandInfoDSLContext

@CommandInfoDSLContext
class CommandInfoDSL(name: String) {
  var info: CommandInfo = CommandInfo(name, "undefined description")
    private set

  var description by observable(info.description, changeInfo { copy(description = it) })
  var aliases by observable(info.aliases, changeInfo { copy(aliases = it) })
  var arguments by observable(info.arguments, changeInfo { copy(arguments = it) })
  var botPermissions by observable(info.botPermissions, changeInfo { copy(botPermissions = it) })
  var userPermissions by observable(info.userPermissions, changeInfo { copy(userPermissions = it) })

  @CommandInfoDSLContext
  inline fun arguments(body: CommandInfoArgumentsDSL.() -> Unit) {
    arguments = CommandInfoArgumentsDSL(arguments).apply(body)
  }

  private inline fun <T> changeInfo(
    crossinline newCopy: CommandInfo.(T) -> CommandInfo
  ): (KProperty<*>, T, T) -> Unit {
    return { _, _, new ->
      info = info.newCopy(new)
    }
  }

  @CommandInfoDSLContext
  class CommandInfoArgumentsDSL(
    private val from: List<CommandArgument>
  ) : MutableList<CommandArgument> by from.toMutableList() {

    @CommandInfoDSLContext
    fun optional(vararg names: String) = add(CommandArgument(CommandArgumentType.OPTIONAL, names.toList()))

    @CommandInfoDSLContext
    fun require(vararg names: String) = add(CommandArgument(CommandArgumentType.REQUIRED, names.toList()))
  }
}

fun optional(vararg names: String) = CommandArgument(CommandArgumentType.OPTIONAL, names.toList())
fun require(vararg names: String) = CommandArgument(CommandArgumentType.REQUIRED, names.toList())

@CommandInfoDSLContext
inline fun info(name: String, body: CommandInfoDSL.() -> Unit): CommandInfo {
  return CommandInfoDSL(name).apply(body).info
}
