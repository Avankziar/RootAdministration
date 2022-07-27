package main.java.me.avankziar.roota.spigot.database;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.LinkedHashMap;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import main.java.me.avankziar.roota.general.Language;
import main.java.me.avankziar.roota.general.YamlManager;
import main.java.me.avankziar.roota.spigot.RootA;

public class YamlHandler
{
	private RootA plugin;
	private File config = null;
	private YamlConfiguration cfg = new YamlConfiguration();

	public YamlHandler(RootA plugin)
	{
		this.plugin = plugin;
		loadYamlHandler();
	}
	
	public YamlConfiguration getConfig()
	{
		return cfg;
	}
	
	private YamlConfiguration loadYamlTask(File file, YamlConfiguration yaml)
	{
		try 
		{
			yaml.load(file);
		} catch (IOException | InvalidConfigurationException e) 
		{
			RootA.log.severe(
					"Could not load the %file% file! You need to regenerate the %file%! Error: ".replace("%file%", file.getName())
					+ e.getMessage());
			e.printStackTrace();
		}
		return yaml;
	}
	
	private boolean writeFile(File file, YamlConfiguration yml, LinkedHashMap<String, Language> keyMap)
	{
		yml.options().header("For more explanation see \n Your pluginsite");
		for(String key : keyMap.keySet())
		{
			Language languageObject = keyMap.get(key);
			if(languageObject.languageValues.containsKey(plugin.getYamlManager().getLanguageType()) == true)
			{
				plugin.getYamlManager().setFileInputBukkit(yml, keyMap, key, plugin.getYamlManager().getLanguageType());
			} else if(languageObject.languageValues.containsKey(plugin.getYamlManager().getDefaultLanguageType()) == true)
			{
				plugin.getYamlManager().setFileInputBukkit(yml, keyMap, key, plugin.getYamlManager().getDefaultLanguageType());
			}
		}
		try
		{
			yml.save(file);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean loadYamlHandler()
	{
		plugin.setYamlManager(new YamlManager(true));
		
		if(!mkdirStaticFiles())
		{
			return false;
		}
		return true;
	}
	
	public boolean mkdirStaticFiles()
	{
		/*
		 * Create the plugin general directory
		 */
		File directory = new File(plugin.getDataFolder()+"");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		/*
		 * Init config.yml
		 */
		config = new File(plugin.getDataFolder(), "config.yml");
		if(!config.exists()) 
		{
			RootA.log.info("Create config.yml...");
			try(InputStream in = plugin.getResource("default.yml"))
			{
				/*
				 * If config.yml dont exist in the main directory, than create config.yml as empty file
				 */
				Files.copy(in, config.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		/*
		 * Load the config.yml
		 */
		cfg = loadYamlTask(config, cfg);
		if (cfg == null)
		{
			return false;
		}
		/*
		 * Write all path for the configfile
		 * Make sure, you use the right linkedHashmap from the YamlManager
		 */
		writeFile(config, cfg, plugin.getYamlManager().getConfigKey());
		return true;
	}
}