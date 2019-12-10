package me.ricky.kbot.core.command

import me.ricky.kbot.core.util.asNullable
import org.javacord.api.entity.channel.ServerTextChannel
import org.javacord.api.entity.channel.ServerVoiceChannel
import org.javacord.api.entity.permission.Role
import org.javacord.api.entity.user.User

/**
 * Shorthand for context.argument(context::requireTextChannel)
 *
 * @see CommandContext.argument
 */
fun ServerCommandArgumentsHandler.requireTextChannel(): ServerTextChannel {
  return context.argument(context::requireTextChannel)
}

/**
 * Shorthand for context.optionalArgument([default], context::requireTextChannel)
 *
 * @see CommandContext.optionalArgument
 */
fun ServerCommandArgumentsHandler.optionalTextChannel(default: ServerTextChannel): ServerTextChannel {
  return context.optionalArgument(default, context::requireTextChannel)
}

/**
 * @param index the index of the argument
 *
 * @return a [ServerTextChannel] of the argument at [index]
 *
 * @throws CommandException if value is not a [ServerTextChannel]
 */
fun ServerCommandContext.requireTextChannel(index: Int): ServerTextChannel {
  val value = args[index]

  var channel = server.getTextChannelById(value.replace(Regex("[^0-9]+"), "")).asNullable

  if (channel == null) {
    val channels = server.getTextChannelsByName(value)

    if (channels.size == 1) {
      channel = channels.first()
    }

    if (channels.size > 1) {
      throw CommandException("Multiple channels with $value. please use id/mention instead.")
    }
  }

  channel ?: throw exceptions.parseException(value, "ServerTextChannel")

  return channel
}

/**
 * Shorthand for context.argument(context::requireVoiceChannel)
 *
 * @see CommandContext.argument
 */
fun ServerCommandArgumentsHandler.requireVoiceChannel(): ServerVoiceChannel {
  return context.argument(context::requireVoiceChannel)
}

/**
 * Shorthand for context.optionalArgument([default], context::requireVoiceChannel)
 *
 * @see CommandContext.optionalArgument
 */
fun ServerCommandArgumentsHandler.optionalVoiceChannel(default: ServerVoiceChannel): ServerVoiceChannel {
  return context.optionalArgument(default, context::requireVoiceChannel)
}

/**
 * @param index the index of the argument
 *
 * @return a [ServerVoiceChannel] of the argument starting at [index]
 *
 * @throws CommandException if value is not a [ServerVoiceChannel]
 *
 */
fun ServerCommandContext.requireVoiceChannel(index: Int): ContinuesArgument<ServerVoiceChannel> {
  var endIndex = index
  val name = args[index]
  val list = args.subList(index, args.size)
  val str = list.joinToString(" ")

  var channel: ServerVoiceChannel? = server
    .getVoiceChannelById(name.replace(Regex("[^0-9]+"), ""))
    .asNullable

  if (channel == null) {
    val channels = server.voiceChannels.filter { str.startsWith(it.name) }

    if (channels.size == 1) {
      channel = channels.first()

      endIndex = args.indexOfLast { channel.name.endsWith(it) }
    }

    if (channels.size > 1) {
      throw CommandException("Found Multiple Voice Channels, please use id/mention of voice channel instead.")
    }
  }

  channel ?: throw exceptions.parseException(str, "ServerVoiceChannel")

  return ContinuesArgument(channel, endIndex)
}

/**
 * Shorthand for context.argument(context::requireUser)
 *
 * @see CommandContext.argument
 */
fun ServerCommandArgumentsHandler.requireUser(): User {
  return context.argument(context::requireUser)
}

/**
 * Shorthand for context.optionalArgument([default], context::requireUser)
 *
 * @see CommandContext.optionalArgument
 */
fun ServerCommandArgumentsHandler.optionalUser(default: User): User {
  return context.optionalArgument(default, context::requireUser)
}

/**
 * @param index the index of the argument
 *
 * @return an [User] of the argument starting at [index]
 *
 * @throws CommandException if value is not a [User]
 */
fun ServerCommandContext.requireUser(index: Int): ContinuesArgument<User> {
  var endIndex = index
  var name = args[index]
  var user: User? = server.getMemberById(name.replace(Regex("[^0-9]+"), "")).asNullable
  val list = args.subList(index, args.size)

  if (user == null && name.contains(Regex("#[0-9]+"))) {
    user = server.getMemberByDiscriminatedName(name).asNullable
  }

  if (user == null) {

    endIndex = list.indexOfLast {
      it.contains(Regex("#[0-9]+"))
    }

    if (endIndex == -1) {
      user = null
    } else {
      name = list.subList(index, endIndex + 1).joinToString(" ")
      user = server.getMemberByDiscriminatedName(name).asNullable
    }
  }

  user ?: throw exceptions.parseException(list.joinToString(" "), "User")

  return ContinuesArgument(user, endIndex)
}

/**
 * Shorthand for context.argument(context::requireRole)
 *
 * @see CommandContext.argument
 */
fun ServerCommandArgumentsHandler.requireRole(): Role {
  return context.argument(context::requireRole)
}

/**
 * Shorthand for context.optionalArgument([default], context::requireRole)
 *
 * @see CommandContext.optionalArgument
 */
fun ServerCommandArgumentsHandler.optionalRole(default: Role): Role {
  return context.optionalArgument(default, context::requireRole)
}

/**
 * @param index The index of the argument
 *
 * @return An user of the argument starting at [index]
 *
 * @throws CommandException if value is not a [Role]
 */
fun ServerCommandContext.requireRole(index: Int): ContinuesArgument<Role> {
  var endIndex = index
  val name = args[index]
  val list = args.subList(index, args.size)
  val str = list.joinToString(" ")

  var role: Role? = server.getRoleById(name.replace(Regex("[^0-9]+"), "")).asNullable

  if (role == null) {
    val roles = server.roles.filter { str.startsWith(it.name) }

    if (roles.size == 1) {
      role = roles.first()

      endIndex = args.indexOfLast { role.name.endsWith(it) }
    }

    if (roles.size > 1) {
      throw CommandException("Found Multiple Roles, please use id/mention of role instead.")
    }
  }

  role ?: throw exceptions.parseException(str, "Role")

  return ContinuesArgument(role, endIndex)
}