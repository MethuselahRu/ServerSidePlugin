package ru.methuselah.serversideplugin.API;

public abstract class PluginComponent
{
	public abstract void onLoad();
	public abstract void onEnable();
	public abstract void onDisable();
	public abstract void onConnected();
	public abstract void onDisconnected();
}
