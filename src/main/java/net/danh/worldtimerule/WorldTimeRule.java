package net.danh.worldtimerule;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

public final class WorldTimeRule extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("wtrreload").setExecutor(new Command(this));
        createConfigs();
        (new BukkitRunnable() {
            public void run() {
                if (getConfig().getString("world") != null) {
                    if (getServer().getWorld(getConfig().getString("world")).getTime() >= 0L && getServer().getWorld(getConfig().getString("world")).getTime() < 13700L) {
                        getServer().getWorld(getConfig().getString("world")).setGameRule(GameRule.KEEP_INVENTORY, true);
                        Iterator var2 = Bukkit.getOnlinePlayers().iterator();
                        while (var2.hasNext()) {
                            Player p = (Player) var2.next();
                            p.sendMessage(convert(getConfig().getString("messageday")));

                        }
                    }


                    } else if (getServer().getWorld(getConfig().getString("world")).getTime() >= 13700L) {
                        getServer().getWorld(getConfig().getString("world")).setGameRule(GameRule.KEEP_INVENTORY, false);
                        Iterator var2 = Bukkit.getOnlinePlayers().iterator();
                        while (var2.hasNext()) {
                            Player p = (Player) var2.next();
                            p.sendMessage(convert(getConfig().getString("messagenight")));
                            p.sendTitle(convert(getConfig().getString("title")), convert(getConfig().getString("subtitle")), getConfig().getInt("fadein"), getConfig().getInt("stay"), getConfig().getInt("fadeout"));
                        }
                    }
                    if (getServer().getWorld(getConfig().getString("world")).getTime() >= 13500L){
                        int giaycon = (int)((13700L - getServer().getWorld(getConfig().getString("world")).getTime()) / 20L) + 1;
                        if (giaycon == 10) {

                            Iterator var2 = Bukkit.getOnlinePlayers().iterator();
                            while (var2.hasNext()) {
                                Player p = (Player) var2.next();
                                p.sendMessage(convert(getConfig().getString("messagecanhbao")).replaceAll("{giay}", String.valueOf(giaycon)));
                        }
                    }
                }
            }
        }).runTaskTimer(this, 20L, 20L);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private File configFile;
    private FileConfiguration config;


    public void createConfigs() {
        configFile = new File(getDataFolder(), "config.yml");

        if (!configFile.exists()) saveResource("config.yml", false);

        config = new YamlConfiguration();

        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void reloadConfigs() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public String convert(String s) {
        return s.replaceAll("&", "§");
    }

}
