package ru.edenor.edenorChatFilter

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin
import ru.edenor.edenorChatFilter.command.EdenorChatFilterCommand

class EdenorChatFilter : JavaPlugin() {

  lateinit var listener: ChatWarningListener
    private set

  override fun onEnable() {
    saveDefaultConfig()
    instance = this
    slF4JLogger.info("EdenorChatFilter is active!")

    listener = ChatWarningListener(this)
    server.pluginManager.registerEvents(listener, this)

    lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { commands ->
      EdenorChatFilterCommand(this).commands().forEach { node ->
        commands.registrar().register(node)
      }
    }
  }

  override fun onDisable() {
    slF4JLogger.info("EdenorChatFilter disable!")
  }

  companion object {
    const val BYPASS_PERMISSION = "ecf.bypass"
    lateinit var instance: EdenorChatFilter
      private set
  }
}
