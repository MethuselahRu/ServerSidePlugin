package ru.methuselah.serversideplugin.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import ru.methuselah.authlib.links.GlobalReplacementList;
import ru.methuselah.authlib.links.Links;
import ru.methuselah.authlib.links.LinksElyBy;
import ru.methuselah.authlib.links.LinksMethuselah;
import ru.methuselah.authlib.links.LinksMojang;
import ru.methuselah.serversideplugin.API.CustomLinks;
import ru.methuselah.serversideplugin.API.CustomLinksBuilder;
import ru.methuselah.serversideplugin.BukkitMain;
import ru.methuselah.serversideplugin.Engine.Utilities;
import ru.simsonic.rscMinecraftLibrary.Bukkit.CommandAnswerException;

public class FeatureSetupLinks extends BukkitFeature
{
	private final String customLinksFilename = "custom-links.json";
	private final File   customLinks;
	public FeatureSetupLinks(BukkitMain plugin)
	{
		super(plugin);
		customLinks = new File(plugin.getDataFolder(), customLinksFilename);
	}
	@Override
	public void onLoad()
	{
		integrate();
	}
	@Override
	public void onEnable()
	{
		integrate();
	}
	@Override
	public void onDisable()
	{
	}
	@Override
	public void onCommand(CommandSender sender, String command, String[] args) throws CommandAnswerException
	{
	}
	private void integrate()
	{
		final FileConfiguration config = plugin.getConfig();
		final String provider = config.getString("general.links-provider", "methuselah");
		config.set("general.links-provider", provider);
		plugin.saveConfig();
		final Links links = analyze(provider.toLowerCase());
		final GlobalReplacementList grl = links.buildReplacements();
		Utilities.hackServerLinks(grl);
		BukkitMain.consoleLog.info("[Methuselah] Authentication scheme has been applied.");
	}
	private Links analyze(String provider)
	{
		switch(provider.toLowerCase())
		{
			case "mojang":
			case "mojang.com":
				BukkitMain.consoleLog.info("[Methuselah] Using Mojang links for authentication.");
				return new LinksMojang();
			case "methuselah":
			case "methuselah.ru":
			case "auth.methuselah.ru":
				BukkitMain.consoleLog.info("[Methuselah] Using methuselah.ru links for authentication.");
				return new LinksMethuselah();
			case "ely":
			case "ely.by":
			case "minecraft.ely.by":
				BukkitMain.consoleLog.info("[Methuselah] Using ely.by links for authentication.");
				return new LinksElyBy();
			case "custom":
			default:
				BukkitMain.consoleLog.info("[Methuselah] Using links from custom-links.json for authentication.");
				break;
		}
		try
		{
			CustomLinksBuilder.LinksCustomContainer container = CustomLinksBuilder.load(customLinks);
			return new CustomLinks(container);
		} catch(IOException ex) {
			BukkitMain.consoleLog.log(Level.WARNING, "[Methuselah] Integration exception:\n{0}", ex);
			return new LinksMethuselah();
		}
	}
}
