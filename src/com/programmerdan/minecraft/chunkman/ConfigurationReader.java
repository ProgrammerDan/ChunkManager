package com.programmerdan.minecraft.chunkman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigurationReader {
	public static boolean readConfig() {

		// Note: savedefaultconfig only writes data if config.yml doesn't exist.
		ChunkManager.instance().saveDefaultConfig();
		ChunkManager.instance().reloadConfig();
		FileConfiguration conf = ChunkManager.instance().getConfig();

		// TODO: Read config.

	}
}
