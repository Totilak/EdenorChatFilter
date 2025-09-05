package ru.edenor.edenorChatFilter.command

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands.literal
import org.bukkit.command.CommandSender
import ru.edenor.edenorChatFilter.EdenorChatFilter

class EdenorChatFilterCommand(private val plugin: EdenorChatFilter) {

  fun commands(): Array<LiteralCommandNode<CommandSourceStack>> {
    return arrayOf(chatfilter)
  }

  private val reloadSection = literal("reload").simplyRun(::reload)

  private val chatfilter = literal("ecf").simplyRun(::sendHelp).then(reloadSection).build()

  private fun reload(context: CommandContext<CommandSourceStack>) {

    plugin.reloadConfig()
    plugin.listener.reloadConfig()
    val sender: CommandSender = context.source.sender
    sender.sendRichMessage("<green>EdenorChatFilter конфиг перезагружен!")
    plugin.slF4JLogger.info("{} reload config ChatFilter", sender.name)
  }

  private fun sendHelp(context: CommandContext<CommandSourceStack>) {
    val sender: CommandSender = context.source.sender
    sender.sendRichMessage(
        "<dark_red><b>EdenorChatFilter</b> <gray>(${plugin.pluginMeta.version})</gray> <dark_aqua>- Фильтрует чат сервера")
    sender.sendRichMessage("<green>/ecf reload <yellow>- Перезагружает настройки.")
  }
}
