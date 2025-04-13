package pashmash.lifesteal.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pashmash.lifesteal.Lifesteal;
import pashmash.lifesteal.util.ColorUtil;

import java.util.Objects;

public class PlayerDeathListener implements Listener {
    public PlayerDeathListener() {
        Lifesteal.getInstance().getServer().getPluginManager().registerEvents(this, Lifesteal.getInstance());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Player killer = player.getKiller();

        if (killer != null) {
            int killerLives = Lifesteal.getInstance().getLivesManager().get(killer.getUniqueId());
            Lifesteal.getInstance().getLivesManager().set(killer.getUniqueId(), killerLives + 1);
            killer.sendMessage(ColorUtil.translate(ColorUtil.PREFIX + "You have received 1" + ColorUtil.HEART + " for killing " + ColorUtil.ERROR + player.getName()));
        }

        int playerLives = Lifesteal.getInstance().getLivesManager().get(player.getUniqueId());

        if (playerLives - 1 <= 0) {
            Lifesteal.getInstance().getLivesManager().set(player.getUniqueId(), 5);

            String command = Objects.requireNonNull(Lifesteal.getInstance().getConfig().getString("Settings.BanCommand"))
                    .replace("%player%", player.getName());
            Lifesteal.getInstance().getServer().dispatchCommand(Lifesteal.getInstance().getServer().getConsoleSender(), command);
        } else {
            Lifesteal.getInstance().getLivesManager().set(player.getUniqueId(), playerLives - 1);
            player.sendMessage(ColorUtil.translate(ColorUtil.PREFIX + ColorUtil.ERROR + "-1&l❤"));
        }
    }
}
