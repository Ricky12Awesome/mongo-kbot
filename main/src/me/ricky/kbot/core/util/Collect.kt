package me.ricky.kbot.core.util

fun <K, V> MutableMap<K, V>.getOrSet(id: K, value: V) = getOrPut(id, { value })