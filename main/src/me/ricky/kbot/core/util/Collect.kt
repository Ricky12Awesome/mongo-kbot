package me.ricky.kbot.core.util

fun <K, V> MutableMap<K, V>.getOrSet(key: K, value: V) = getOrPut(key, { value })
fun <K, V> Map<K, V>.notContainsKey(key: K) = !containsKey(key)