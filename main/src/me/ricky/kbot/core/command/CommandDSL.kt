package me.ricky.kbot.core.command

fun optional(name: String) = CommandArgument(CommandArgumentType.OPTIONAL, name)
fun required(name: String) = CommandArgument(CommandArgumentType.REQUIRED, name)