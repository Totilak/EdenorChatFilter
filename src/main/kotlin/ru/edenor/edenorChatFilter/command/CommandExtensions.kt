package ru.edenor.edenorChatFilter.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack

fun LiteralArgumentBuilder<CommandSourceStack>.simplyRun(
  block: (CommandContext<CommandSourceStack>) -> Unit
): LiteralArgumentBuilder<CommandSourceStack> {
  return this.executes { context ->
    block(context)
    1
  }
}
