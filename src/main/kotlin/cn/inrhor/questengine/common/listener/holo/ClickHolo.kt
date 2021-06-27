package cn.inrhor.questengine.common.listener.holo

import cn.inrhor.questengine.common.database.data.DataStorage
import cn.inrhor.questengine.common.kether.KetherHandler
import io.izzel.taboolib.module.inject.TListener
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

@TListener
class ClickHolo: Listener {

    @EventHandler
    fun clickAction(ev: PlayerInteractEvent) {
        val p = ev.player
        if (ev.action != Action.LEFT_CLICK_AIR) return
        val pData = DataStorage().getPlayerData(p)
        for (holoBox in pData.dialogData.holoBoxList) {
            if (holoBox.isBox()) {
                val replyModule = holoBox.replyModule
                for (script in replyModule.script) {
                    KetherHandler.eval(p, script)
                }
                for (holoDialog in pData.dialogData.holoDialogList) {
                    val dialogModule = holoDialog.dialogModule
                    if (dialogModule.dialogID == holoBox.replyModule.dialogID) {
                        holoDialog.end()
                    }
                }
                return
            }
        }
    }

}