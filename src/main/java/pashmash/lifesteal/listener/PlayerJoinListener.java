package pashmash.lifesteal.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pashmash.lifesteal.Lifesteal;

public class PlayerJoinListener implements Listener {
    public PlayerJoinListener() {
        Bukkit.getPluginManager().registerEvents(this, Lifesteal.getInstance());
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            Bukkit.getScheduler().runTaskAsynchronously(Lifesteal.getInstance(), () -> {
                if (!(Lifesteal.getInstance().getSqlUtil().get("Lives", "UUID", "UUID='" + player.getUniqueId() + "'").equalsIgnoreCase(player.getUniqueId().toString()))) {
                    Lifesteal.getInstance().getSqlUtil().update("INSERT INTO Lives (UUID, lives) VALUES ('" + player.getUniqueId() + "', 5)");
                }
                Lifesteal.getInstance().getLivesManager().loadLocal(player.getUniqueId());
            });
        }
    }


}

