package ru.methuselah.serversideplugin;

import net.md_5.bungee.api.plugin.Plugin;
import ru.methuselah.authlib.GlobalReplacementList;
import ru.methuselah.serversideplugin.BungeeCord.BungeeListener;
import ru.methuselah.serversideplugin.Engine.Utilities;

public final class BungeeCordMain extends Plugin
{
	private final BungeeListener listener = new BungeeListener(this);
	@Override
	public void onEnable()
	{
		Utilities.processReplacements(new GlobalReplacementList());
		getProxy().getPluginManager().registerListener(this, listener);
	}
	@Override
	public void onDisable()
	{
		getProxy().getPluginManager().unregisterListeners(this);
	}
}
