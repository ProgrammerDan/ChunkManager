package com.programmerdan.minecraft.chunkman;

import com.programmerdan.minecraft.chunkman.commands.CommandHandler;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * <p>Chunk Manager is a utility plugin designed to help discover and alleviate chunk-related mismanagement
 *   on the part of Minecraft. 
 * <p>It also exposes a number of investigative tools for server administrators to better grapple
 *   with subtle chunk load issues and player behaviors that might be detrimental to automated chunk management
 *
 * @author ProgrammerDan <programmerdan@gmail.com>
 * @since 1.0.0
 */
public class ChunkManager extends JavaPlugin {
	private static final CommandHandler commandHandler;
	private static final Logger logger;
	private static final JavaPlugin plugin;
	private static boolean enabled = true;
	private static boolean debug = false;

	public static CommandHandler commandHandler() {
		return ChunkManager.commandHandler;
	}

	public static Logger logger() {
		return ChunkManager.logger;
	}

	public static JavaPlugin instance() {
		return ChunkManager.plugin;
	}

	public static boolean isEnabled() {
		return ChunkManager.enabled;
	}

	public static boolean isDebug() {
		return ChunkManager.debug;
	}

	public static void setEnabled(boolean status) {
		ChunkManager.enabled = status;
	}

	public static void setDebug(boolean debug) {
		ChunkManager.debug = debug;
	}

	@Override
	public void onEnable() {
		// setting a couple of static fields so that they are available
		// elsewhere
		logger = getLogger();
		plugin = this;
		commandHandler = new CommandHandler(this);
	}
}
