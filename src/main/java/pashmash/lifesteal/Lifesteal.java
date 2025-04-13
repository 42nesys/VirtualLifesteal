package pashmash.lifesteal;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pashmash.lifesteal.command.LifestealCommand;
import pashmash.lifesteal.hook.PlaceholderHook;
import pashmash.lifesteal.listener.PlayerDeathListener;
import pashmash.lifesteal.listener.PlayerJoinListener;
import pashmash.lifesteal.manager.LivesManager;
import pashmash.lifesteal.util.SqlUtil;

@Getter
public final class Lifesteal extends JavaPlugin {

    @Getter
    private static Lifesteal instance;
    private SqlUtil sqlUtil;
    private LivesManager livesManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        sqlUtil = new SqlUtil(getConfig().getString("Sql.Host"), getConfig().getString("Sql.Port"), getConfig().getString("Sql.Database"), getConfig().getString("Sql.Username"), getConfig().getString("Sql.Password"));
        sqlUtil.update("CREATE TABLE IF NOT EXISTS Lives (" + "UUID VARCHAR(36) PRIMARY KEY," + "lives INT" + ");");

        livesManager = new LivesManager();




        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderHook().register();
        } else {
            getLogger().warning("PlaceholderAPI not found, PlaceholderHook will not be registered.");
        }


        new PlayerJoinListener();
        new LifestealCommand();
        new PlayerDeathListener();



    }

    @Override
    public void onDisable() {

    }
}
