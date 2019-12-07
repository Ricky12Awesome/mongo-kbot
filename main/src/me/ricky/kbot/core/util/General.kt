package me.ricky.kbot.core.util

import java.util.*

val <T> Optional<T>.asNullable get(): T? = if (isPresent) get() else null