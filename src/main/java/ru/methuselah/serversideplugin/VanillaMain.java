package ru.methuselah.serversideplugin;
import ru.methuselah.serversideplugin.Network.SecurePluginConnection;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import ru.fourgotten.VoxileSecurity.Hacks.BinaryClassLoader;
import ru.fourgotten.VoxileSecurity.Data.MessagesPlugin.AnswerPluginEnabled;
import ru.fourgotten.VoxileSecurity.Data.MessagesPlugin.MessagePluginEnabled;
import ru.fourgotten.VoxileSecurity.Hacks.HacksApplicator;
import ru.fourgotten.VoxileSecurity.SecureConnection;
import ru.fourgotten.VoxileSecurity.SecureConnection.ConnectionEstablishedNotification;
import ru.methuselah.authlib.GlobalReplacementList;

public final class VanillaMain implements Runnable, ConnectionEstablishedNotification
{
	private final SecurePluginConnection connection = new SecurePluginConnection(this, false);
	private BinaryClassLoader serverClassLoader;
	private boolean firstTimeConnection = false;
	@Override
	public void connectionEstablished(SecureConnection connection)
	{
		if(firstTimeConnection)
			return;
		firstTimeConnection = true;
		final MessagePluginEnabled message = new MessagePluginEnabled();
		message.serverVersion = "vanilla";
		final AnswerPluginEnabled answer = VanillaMain.this.connection.onPluginEnable(message);
		HacksApplicator.process(answer.replacementList, serverClassLoader);
		System.out.println("[Methuselah] Applied actual replacements.");
	}
	@Override
	public void connectionBreaked()
	{
	}
	private VanillaMain(String serverJar, String[] serverArgs)
	{
		try
		{
			// Получаю имя главного класса
			final Manifest manifest = new JarFile(serverJar).getManifest();
			final String mainClassFQDN = manifest.getMainAttributes().getValue("Main-Class");
			System.out.println("Server's main-class is: " + mainClassFQDN);
			// Загрузка указанного серверного .jar файла
			final URL urls[] = new URL[]
			{
				VanillaMain.class.getProtectionDomain().getCodeSource().getLocation(),
				new File(serverJar).toURI().toURL(),
			};
			serverClassLoader = new BinaryClassLoader(urls, VanillaMain.class.getClassLoader());
			Thread.currentThread().setContextClassLoader(serverClassLoader);
			HacksApplicator.process(new GlobalReplacementList(), serverClassLoader);
			System.out.println("[Methuselah] Applied default replacements.");
			connection.start();
			// Поиск главного класса и его метода public static void main(String args[])
			final Class mainClass = serverClassLoader.loadClass(mainClassFQDN);
			final Method mainMethod = mainClass.getMethod("main", new Class[] { String[].class });
			// Вызов с передачей нужных аргументов
			System.out.println("\n===== ===== SERVER OUTPUT START ===== =====");
			mainMethod.invoke(null, new Object[] { serverArgs });
			System.out.println("\n===== ===== SERVER OUTPUT END ===== =====");
		} catch(IOException ex) {
			System.err.println(ex);
		} catch(ClassNotFoundException ex) {
			System.err.println(ex);
		} catch(NoSuchMethodException | SecurityException ex) {
			System.err.println(ex);
		} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			System.err.println(ex);
		}
	}
	@Override
	public void run()
	{
	}
	public static void main(String[] args)
	{
		// args = new String[] { "E:\\# mine-temp\\jars\\Spigot_v1.7.10-R0.1_b1552.jar" };
		System.out.println("");
		System.out.println("Methuselah Vanilla Server Wrapper");
		System.out.println("Our website: https://www.methuselah.ru");
		System.out.println("");
		if(args == null || args.length == 0 || args[0] == null || "".equals(args[0]))
		{
			System.out.println("Usage: java [java options] -jar <this.jar> <server.jar> [server options]");
			System.exit(0);
		}
		final String serverJar = args[0];
		System.out.println("Server's jar is: " + serverJar);
		final VanillaMain vanillaWrapper = new VanillaMain(serverJar, Arrays.copyOfRange(args, 1, args.length));
		vanillaWrapper.run();
	}
}
