package me.ricky.kbot.core.data.modelx

import me.ricky.kbot.core.data.model.ServerDocument
import org.javacord.api.entity.user.User

fun ServerDocument.getMemberOrNull(member: User) = getMemberOrNull(member.id)
fun ServerDocument.getMemberOrNull(id: Long) = members.firstOrNull { it.memberId == id }

fun ServerDocument.getMember(member: User) = getMember(member.id)
fun ServerDocument.getMember(id: Long) = getMemberOrNull(id)!!
