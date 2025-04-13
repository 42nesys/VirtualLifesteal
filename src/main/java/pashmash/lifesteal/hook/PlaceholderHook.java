package pashmash.lifesteal.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pashmash.lifesteal.Lifesteal;
import pashmash.lifesteal.util.ColorUtil;

public class PlaceholderHook extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "lives";
    }

    @Override
    public @NotNull String getAuthor() {
        return "42nesys";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) return null;

        if (identifier.equals("lives")) {
            return Lifesteal.getInstance().getLivesManager().get(player.getUniqueId()) + ColorUtil.HEART;
        }

        return null;
    }
}
