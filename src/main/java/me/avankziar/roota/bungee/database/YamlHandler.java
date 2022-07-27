package main.java.me.avankziar.roota.bungee.database;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.LinkedHashMap;

import main.java.me.avankziar.roota.bungee.RootA;
import main.java.me.avankziar.roota.general.Language;
import main.java.me.avankziar.roota.general.YamlManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class YamlHandler
{

	private RootA plugin;
	private File config = null;
	private Configuration cfg = new Configuration();

	public YamlHandler(RootA plugin)
	{
		this.plugin = plugin;
		loadYamlHandler();
	}
	
	public Configuration getConfig()
	{
		return cfg;
	}
	
	private Configuration loadYamlTask(File file, Configuration yaml)
	{
		Configuration y = null;
		try 
		{
			yaml = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) 
		{
			RootA.log.severe(
					"Could not load the %file% file! You need to regenerate the %file%! Error: ".replace("%file%", file.getName())
					+ e.getMessage());
			e.printStackTrace();
		}
		y = yaml;
		return y;
	}
	
	private boolean writeFile(File file, Configuration yml, LinkedHashMap<String, Language> keyMap) 
	{
		for(String key : keyMap.keySet())
		{
			Language languageObject = keyMap.get(key);
			if(languageObject.languageValues.containsKey(plugin.getYamlManager().getLanguageType()) == true)
			{
				plugin.getYamlManager().setFileInputBungee(yml, keyMap, key, plugin.getYamlManager().getLanguageType());
			} else if(languageObject.languageValues.containsKey(plugin.getYamlManager().getDefaultLanguageType()) == true)
			{
				plugin.getYamlManager().setFileInputBungee(yml, keyMap, key, plugin.getYamlManager().getDefaultLanguageType());
			}
		}
		try
		{
			 ConfigurationProvider.getProvider(YamlConfiguration.class).save(yml, file);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean loadYamlHandler()
	{
		plugin.setYamlManager(new YamlManager(false));
		if(!mkdirStaticFiles())
		{
			return false;
		}
		return true;
	}
	
	public boolean mkdirStaticFiles()
	{
		File directory = new File(plugin.getDataFolder()+"");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		config = new File(plugin.getDataFolder(), "config.yml");
		if(!config.exists()) 
		{
			RootA.log.info("Create config.yml...");
			 try (InputStream in = plugin.getResourceAsStream("default.yml")) 
	    	 {       
	    		 Files.copy(in, config.toPath());
	         } catch (IOException e) 
	    	 {
	        	 e.printStackTrace();
	        	 return false;
	         }
		}
		cfg = loadYamlTask(config, cfg);
		writeFile(config, cfg, plugin.getYamlManager().getConfigKey());		
		return true;
	}
}