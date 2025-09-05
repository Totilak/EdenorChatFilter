package ru.edenor.edenorChatFilter

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import ru.edenor.edenorChatFilter.EdenorChatFilter.Companion.BYPASS_PERMISSION
import ru.edenor.edenorWarnings.api.WarningAPI

class ChatWarningListener(private val plugin: EdenorChatFilter) : Listener {

  private var enabled: Boolean = true
  private var blockedWords: Set<String> = emptySet()
  private var cancelMessage: String = "<red>Ваше сообщение было заблокировано!"

  init {
    reloadConfig()
  }

  fun reloadConfig() {
    val section = plugin.config.getConfigurationSection("chat-filter")!!

    enabled = section.getBoolean("enabled", true)
    blockedWords = section.getStringList("blocked-words").map { normalize(it) }.toSet()
    cancelMessage = section.getString("cancel-message", "<red>Ваше сообщение было заблокировано!")!!
  }

  @EventHandler
  fun onPlayerChat(event: AsyncPlayerChatEvent) {
    if (!enabled) return

    if (event.player.hasPermission(BYPASS_PERMISSION)) return
    val message = normalize(event.message)

    for (word in blockedWords) {
      if (message.contains(word)) {
        WarningAPI.issueViolation(event.player, "Запрещённое слово: $word")

        event.isCancelled = true
        event.player.sendRichMessage(cancelMessage)
        return
      }
    }
  }

  private fun normalize(input: String): String {
    return input.lowercase().replace(Regex("[^a-zа-я0-9]"), "")
  }
}
