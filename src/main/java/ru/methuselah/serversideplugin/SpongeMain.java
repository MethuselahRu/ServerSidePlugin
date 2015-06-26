package ru.methuselah.serversideplugin;

import org.spongepowered.api.event.state.ServerAboutToStartEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.event.state.ServerStartingEvent;
import org.spongepowered.api.event.state.ServerStoppedEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import ru.methuselah.authlib.links.GlobalReplacementList;
import ru.methuselah.authlib.links.Links;
import ru.methuselah.authlib.links.LinksMethuselah;
import ru.methuselah.serversideplugin.Engine.Utilities;

@Plugin(id = "Methuselah", name = "Methuselah", version="0.14a")
public final class SpongeMain
{
	public void onServerStart(ServerAboutToStartEvent event)
	{
		final Links links = new LinksMethuselah();
		final GlobalReplacementList grl = links.buildReplacements();
		Utilities.processReplacements(grl);
	}
	public void onServerStarting(ServerStartingEvent event)
	{
	}
	public void onServerStarted(ServerStartedEvent event)
	{
	}
	public void onServerStopping(ServerStoppingEvent event)
	{
	}
	public void onServerStopped(ServerStoppedEvent event)
	{
	}
}
