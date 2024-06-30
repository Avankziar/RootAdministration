package main.java.me.avankziar.roota.velocity.database;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import main.java.me.avankziar.roota.general.database.Language;
import main.java.me.avankziar.roota.general.database.Language.ISO639_2B;
import main.java.me.avankziar.roota.general.database.YamlManager;
import main.java.me.avankziar.roota.general.database.YamlManager.Type;
import main.java.me.avankziar.roota.velocity.RootA;

public class YamlHandler
{	
	private RootA plugin;
	private Path dataDirectory;
	private YamlManager yamlManager;
	private String languages;
	
	private YamlDocument config;
	public YamlDocument getConfig()
	{
		return config;
	}
	
	public YamlHandler(RootA plugin)
	{
		this.plugin = plugin;
		this.dataDirectory = plugin.getDataDirectory();
		loadYamlHandler();
	}
	
	public boolean loadYamlHandler()
	{
		yamlManager = new YamlManager(Type.VELO);
		plugin.setYamlManager(yamlManager);
		if(!mkdirStaticFiles())
		{
			return false;
		}
		return true;
	}
	
	public boolean mkdirStaticFiles()
	{
		File directory = new File(dataDirectory.getParent().toFile(), "/"+plugin.pluginName+"/");
		if(!directory.exists())
		{
			directory.mkdirs();
		}
		try
	    {
	    	config = YamlDocument.create(new File(directory, "config.yml"),
	    			getClass().getResourceAsStream("/default.yml"),
	    			GeneralSettings.DEFAULT,
	    			LoaderSettings.builder().setAutoUpdate(true).build(),
	    			DumperSettings.DEFAULT,
	    			UpdaterSettings.builder().setVersioning(new BasicVersioning("file-version"))
	    			.setOptionSorting(UpdaterSettings.OptionSorting.SORT_BY_DEFAULTS).build());
	    	config.update();
	    	languages = config.getString("Language", "ENG").toUpperCase();
			setLanguage();
	    	writeFile(config, yamlManager.getConfigKey());
	    } catch (Exception e)
	    {
	    	e.printStackTrace();
	    	plugin.getLogger().severe("Could not create/load config.yml file! Plugin will shut down!");
	    	return false;
	    	//plugin.onDisable();
	    }
		return true;
	}
	
	private void setLanguage()
	{
		List<Language.ISO639_2B> types = new ArrayList<Language.ISO639_2B>(EnumSet.allOf(Language.ISO639_2B.class));
		ISO639_2B languageType = ISO639_2B.ENG;
		for(ISO639_2B type : types)
		{
			if(type.toString().equals(languages))
			{
				languageType = type;
				break;
			}
		}
		plugin.getYamlManager().setLanguageType(languageType);
	}
	
	private boolean writeFile(YamlDocument yml, LinkedHashMap<String, Language> keyMap)
	{
		try
		{
			for(String key : keyMap.keySet())
			{
				Language languageObject = keyMap.get(key);
				if(languageObject.languageValues.containsKey(plugin.getYamlManager().getLanguageType()) == true)
				{
					plugin.getYamlManager().setFileInputVelocity(yml, keyMap, key, plugin.getYamlManager().getLanguageType());
				} else if(languageObject.languageValues.containsKey(plugin.getYamlManager().getDefaultLanguageType()) == true)
				{
					plugin.getYamlManager().setFileInputVelocity(yml, keyMap, key, plugin.getYamlManager().getDefaultLanguageType());
				}
			}
			yml.save();
		} catch(Exception e)
		{
			plugin.getLogger().warning("Could not write file %file%".replace("%file%", yml.getNameAsString()));
			e.printStackTrace();
			return false;
		}
		return true;
	}
}