package me.ricky.kbot.core.util

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.util.*

val jsonx: Json = Json(
  configuration = JsonConfiguration.Stable.copy(
    prettyPrint = true,
    indent = "  "
  )
)

val <T> Optional<T>.asNullable get(): T? = if (isPresent) get() else null