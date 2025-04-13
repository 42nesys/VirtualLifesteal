package pashmash.lifesteal.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pashmash.lifesteal.Lifesteal;
import pashmash.lifesteal.util.ColorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LifestealCommand implements CommandExecutor, TabCompleter {

    public LifestealCommand() {
        Objects.requireNonNull(Lifesteal.getInstance().getCommand("lifesteal")).setExecutor(this);
        Objects.requireNonNull(Lifesteal.getInstance().getCommand("lifesteal")).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ColorUtil.translate("&cOnly players can use this command!"));
            return true;
        }

        if (!player.hasPermission("lifesteal.admin")) {
            player.sendMessage(ColorUtil.translate(ColorUtil.PREFIX + ColorUtil.PERMISSION));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ColorUtil.translate(ColorUtil.PREFIX + "&aUsage: /lifesteal <set|remove> <player> <lives>"));
            return true;
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(ColorUtil.translate(ColorUtil.PREFIX + "&cPlayer not found!"));
                return true;
            }

            try {
                int lives = Integer.parseInt(args[2]);
                if (lives < 0) {
                    player.sendMessage(ColorUtil.translate(ColorUtil.PREFIX + "&cLives must be a non-negative number!"));
                    return true;
                }

                Lifesteal.getInstance().getLivesManager().set(target.getUniqueId(), lives);
                player.sendMessage(ColorUtil.translate(ColorUtil.PREFIX + "&aSet " + target.getName() + "'s lives to " + lives));
                target.sendMessage(ColorUtil.translate(ColorUtil.PREFIX + "&aYour lives have been set to " + lives + " by " + player.getName()));

            } catch (NumberFormatException e) {
                player.sendMessage(ColorUtil.translate(ColorUtil.PREFIX + "&cInvalid number of lives!"));
            }
            return true;
        }

        player.sendMessage(ColorUtil.translate(ColorUtil.PREFIX + "&cUnknown command. Use /lifesteal for help."));
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            return List.of("set");
        }

        if (args.length == 2) {
            List<String> playerNames = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerNames.add(player.getName());
            }
            return playerNames;
        }

        return new ArrayList<>();
    }
}