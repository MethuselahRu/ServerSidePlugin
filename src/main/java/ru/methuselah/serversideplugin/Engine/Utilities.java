package ru.methuselah.serversideplugin.Engine;

import java.util.LinkedList;
import ru.methuselah.authlib.links.GlobalReplacementList;
import ru.methuselah.securitylibrary.Hacks.HacksApplicator;
import ru.methuselah.serversideplugin.API.Settings;
import ru.methuselah.serversideplugin.VanillaMain;
import ru.simsonic.rscMinecraftLibrary.Bukkit.GenericChatCodes;

public final class Utilities
{
	public static void hackServerLinks(GlobalReplacementList replacements)
	{
		final LinkedList<ClassLoader> loaders = new LinkedList<>();
		for(ClassLoader classLoader = Utilities.class.getClassLoader(); classLoader != null; classLoader = classLoader.getParent())
			loaders.add(0, classLoader);
		for(ClassLoader classLoader : loaders)
		{
			System.out.println("[Methuselah] Processing ClassLoader: " + classLoader.getClass().getCanonicalName());
			HacksApplicator.process(replacements, classLoader);
		}
	}
	public String getDebugStackTrace()
	{
		final StringBuilder sb = new StringBuilder(Settings.CHAT_PREFIX);
		sb.append("[DEBUG] An API method was invoked from the path:").append(System.lineSeparator());
		for(StackTraceElement ste : Thread.currentThread().getStackTrace())
		{
			final String className = ste.getClassName();
			if(!className.equals(this.getClass().getName())
				&& !className.equals(Thread.class.getName())
				)
				sb.append(Settings.CHAT_PREFIX).append("[DEBUG] ")
					.append(className.startsWith(VanillaMain.class.getPackage().getName()) ? "{_LG}" : "{_LS}")
					.append(ste.toString())
					.append(System.lineSeparator());
		}
		return GenericChatCodes.processStringStatic(sb.toString());
	}
}
