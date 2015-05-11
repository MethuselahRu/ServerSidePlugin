package ru.methuselah.serversideplugin.Engine;

import java.util.LinkedList;
import ru.fourgotten.VoxileSecurity.Hacks.HacksApplicator;
import ru.methuselah.authlib.GlobalReplacementList;

public final class Utilities
{
	public static void processReplacements(GlobalReplacementList replacements)
	{
		final LinkedList<ClassLoader> loaders = new LinkedList<>();
		for(ClassLoader classLoader = Utilities.class.getClassLoader(); classLoader != null; classLoader = classLoader.getParent())
			loaders.add(0, classLoader);
		for(ClassLoader classLoader : loaders)
			HacksApplicator.process(replacements, classLoader);
	}
}
