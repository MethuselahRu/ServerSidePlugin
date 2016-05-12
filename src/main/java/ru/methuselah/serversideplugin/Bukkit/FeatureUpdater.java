package ru.methuselah.serversideplugin.Bukkit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.methuselah.serversideplugin.API.Settings;
import ru.methuselah.serversideplugin.BukkitMain;
import ru.simsonic.rscMinecraftLibrary.AutoUpdater.BukkitUpdater;
import ru.simsonic.rscMinecraftLibrary.Bukkit.CommandAnswerException;

public class FeatureUpdater extends BukkitFeature
{
	private final BukkitUpdater updater;
	public FeatureUpdater(BukkitMain plugin)
	{
		super(plugin);
		updater = new BukkitUpdater(plugin, Settings.UPDATER_URL, Settings.CHAT_PREFIX);
	}
	@Override
	public void onLoad()
	{
	}
	@Override
	public void onEnable()
	{
		updater.onEnable();
	}
	@Override
	public void onDisable()
	{
	}
	@Override
	public void onCommand(CommandSender sender, String command, String[] args) throws CommandAnswerException
	{
		if(command.equals("methuselah") && args.length > 0 && args[0] != null && !"".equals(args[0]))
		{
			switch(args[0].toLowerCase())
			{
				case "update":
					if(sender.hasPermission("methuselah.admin"))
					{
						final Player player = sender instanceof Player ? (Player)sender : null;
						if(args.length > 1 && "do".equals(args[1]))
							updater.doUpdate(player);
						else
							updater.checkUpdate(player);
					}
					break;
				default:
					break;
			}
		}
	}
}
