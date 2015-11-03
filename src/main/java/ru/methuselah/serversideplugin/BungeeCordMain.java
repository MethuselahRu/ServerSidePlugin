package ru.methuselah.serversideplugin;

import net.md_5.bungee.api.plugin.Plugin;
import ru.methuselah.authlib.links.GlobalReplacementList;
import ru.methuselah.authlib.links.Links;
import ru.methuselah.authlib.links.LinksMethuselah;
import ru.methuselah.serversideplugin.BungeeCord.BungeeListener;
import ru.methuselah.serversideplugin.Engine.Utilities;

public final class BungeeCordMain extends Plugin
{
	private final BungeeListener listener = new BungeeListener(this);
	@Override
	public void onEnable()
	{
		getProxy().getPluginManager().registerListener(this, listener);
		final Links links = new LinksMethuselah();
		final GlobalReplacementList grl = links.buildReplacements();
		Utilities.hackServerLinks(grl);
		System.out.println("Authentication scheme has been applied.");
	}
	@Override
	public void onDisable()
	{
		getProxy().getPluginManager().unregisterListeners(this);
	}
}
