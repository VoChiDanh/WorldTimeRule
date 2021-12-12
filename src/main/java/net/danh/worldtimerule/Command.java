package net.danh.worldtimerule;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Command  implements CommandExecutor {


    private WorldTimeRule main;


    public Command(WorldTimeRule main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("wtrreload")) {
            if (args.length == 0) {
                if (!sender.hasPermission("wtr.reload")) {
                    return true;
                }
                if (sender.hasPermission("wtf.reload")) {
                    main.reloadConfigs();
                    sender.sendMessage(main.convert(main.getConfig().getString("reload")) + " (v" + main.getDescription().getVersion() + ")");
                }
            }
        }
        return true;
    }
}
