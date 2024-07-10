package main.java.me.avankziar.roota.general.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import main.java.me.avankziar.roota.general.database.Language.ISO639_2B;
import main.java.me.avankziar.roota.general.database.YamlManager.Type;

public class YamlHandler
{	
	private String pluginname;
	private Path dataDirectory;
	private YamlManager yamlManager;
	private Logger logger;
	private String administrationLanguage = null;
	
	private GeneralSettings gsd = GeneralSettings.DEFAULT;
	private LoaderSettings lsd = LoaderSettings.builder().setAutoUpdate(true).build();
	private DumperSettings dsd = DumperSettings.DEFAULT;
	private UpdaterSettings usd = UpdaterSettings.builder().setVersioning(new BasicVersioning("file-version"))
			.setKeepAll(true)
			.setOptionSorting(UpdaterSettings.OptionSorting.SORT_BY_DEFAULTS).build();
	
	private String languages;
	
	private YamlDocument config;
	public YamlDocument getConfig()
	{
		return config;
	}
	
	private YamlDocument matlang;
	public YamlDocument getMaterialLang()
	{
		return matlang;
	}
	
	private YamlDocument enchlang;
	public YamlDocument getEnchLang()
	{
		return enchlang;
	}
	
	private YamlDocument bannerlang;
	public YamlDocument getBannerLang()
	{
		return bannerlang;
	}
	
	private YamlDocument itemflaglang;
	public YamlDocument getItemFlagLang()
	{
		return itemflaglang;
	}
	
	private YamlDocument potiontypelang;
	public YamlDocument getPotionTypeLang()
	{
		return potiontypelang;
	}
	
	private YamlDocument potioneffecttypelang;
	public YamlDocument getPotionEffectTypeLang()
	{
		return potioneffecttypelang;
	}
	
	private YamlDocument entitytypelang;
	public YamlDocument getEntityTypeLang()
	{
		return entitytypelang;
	}
	
	private YamlDocument axolotlvariantlang;
	public YamlDocument getAxolotlVariantLang()
	{
		return axolotlvariantlang;
	}
	
	private YamlDocument bookmetagenerationlang;
	public YamlDocument getBookMetaGenerationLang()
	{
		return bookmetagenerationlang;
	}
	
	private YamlDocument colorlang;
	public YamlDocument getColorLang()
	{
		return colorlang;
	}
	
	private YamlDocument dyecolorlang;
	public YamlDocument getDyeColorLang()
	{
		return dyecolorlang;
	}
	
	private YamlDocument tropicalfishbucketlang;
	public YamlDocument getTropicalFishBucketLang()
	{
		return tropicalfishbucketlang;
	}
	
	private YamlDocument cattypelang;
	public YamlDocument getCatTypeLang()
	{
		return cattypelang;
	}
	
	private YamlDocument foxtypelang;
	public YamlDocument getFoxTypeLang()
	{
		return foxtypelang;
	}
	
	private YamlDocument mapcursortypelang;
	public YamlDocument getMapCursorTypeLang()
	{
		return mapcursortypelang;
	}
	
	private YamlDocument rabbittypelang;
	public YamlDocument getRabbitTypeLang()
	{
		return rabbittypelang;
	}
	
	private YamlDocument villagertypelang;
	public YamlDocument getVillagerTypeLang()
	{
		return villagertypelang;
	}
	
	private YamlDocument villagerprofessionlang;
	public YamlDocument getVillagerProfessionLang()
	{
		return villagerprofessionlang;
	}
	
	private YamlDocument treetypelang;
	public YamlDocument getTreeTypeLang()
	{
		return treetypelang;
	}
	
	public YamlHandler(YamlManager.Type type, String pluginname, Logger logger, Path directory, String administrationLanguage)
	{
		this.pluginname = pluginname;
		this.logger = logger;
		this.dataDirectory = directory;
		this.administrationLanguage = administrationLanguage;
		loadYamlHandler(type);
	}
	
	public YamlManager getYamlManager()
	{
		return yamlManager;
	}
	
	public boolean loadYamlHandler(YamlManager.Type type)
	{
		yamlManager = new YamlManager(type);
		if(!mkdirStaticFiles(type))
		{
			return false;
		}
		if(!mkdirDynamicFiles(type))
		{
			return false;
		}
		return true;
	}
	
	public boolean mkdirStaticFiles(YamlManager.Type type)
	{
		File directory = new File(dataDirectory.getParent().toFile(), "/"+pluginname+"/");
		if(!directory.exists())
		{
			directory.mkdirs();
		}
		String f = "config";
		try
	    {
			config = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, config, yamlManager.getConfigKey()))
			{
				return false;
			}
	    } catch (Exception e)
	    {
	    	logger.severe("Could not create/load config.yml file! Plugin will shut down!");
	    	return false;
	    }
		return true;
	}
	
	private boolean mkdirDynamicFiles(YamlManager.Type type)
	{
		if(!mkdirLanguage(type))
		{
			return false;
		}
		return true;
	}
	
	private boolean mkdirLanguage(YamlManager.Type type)
	{
		if(type != Type.SPIGOT)
		{
			return true;
		}
		String languageString = languages.toLowerCase();
		File directory = new File(dataDirectory.getParent().toFile(), "/"+pluginname+"/Languages/");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		String f = languageString+"_material.yml";
		try
		{
			matlang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, matlang, yamlManager.getMaterialLanguageKey()))
			{
				return false;
			}
			f = languageString+"_enchantment";
			enchlang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, enchlang, yamlManager.getEnchantmentLanguageKey()))
			{
				return false;
			}
			f = languageString+"_banner";
			bannerlang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, bannerlang, yamlManager.getBannerLanguageKey()))
			{
				return false;
			}
			f = languageString+"_itemflag";
			itemflaglang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, itemflaglang, yamlManager.getItemFlagLanguageKey()))
			{
				return false;
			}
			f = languageString+"_potiontype.yml";
			potiontypelang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, potiontypelang, yamlManager.getPotionTypeLanguageKey()))
			{
				return false;
			}
			f = languageString+"_potioneffecttype.yml";
			potioneffecttypelang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, potioneffecttypelang, yamlManager.getPotionEffectTypeLanguageKey()))
			{
				return false;
			}
			f = languageString+"_entitytype.yml";
			entitytypelang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, entitytypelang, yamlManager.getEntityTypeLanguageKey()))
			{
				return false;
			}
			f = languageString+"_axolotlvariant.yml";
			axolotlvariantlang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, axolotlvariantlang, yamlManager.getAxolotlVariantLanguageKey()))
			{
				return false;
			}
			f = languageString+"_bookmetageneration.yml";
			bookmetagenerationlang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, bookmetagenerationlang, yamlManager.getBookMetaGenerationLanguageKey()))
			{
				return false;
			}
			f = languageString+"_color.yml";
			colorlang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, colorlang, yamlManager.getColorLanguageKey()))
			{
				return false;
			}
			f = languageString+"_dyecolor.yml";
			dyecolorlang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, dyecolorlang, yamlManager.getDyeColorLanguageKey()))
			{
				return false;
			}
			f = languageString+"_tropicalfishbucket.yml";
			tropicalfishbucketlang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, tropicalfishbucketlang, yamlManager.getTropicalFishBucketLanguageKey()))
			{
				return false;
			}
			f = languageString+"_cattype.yml";
			cattypelang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, cattypelang, yamlManager.getCatTypeLanguageKey()))
			{
				return false;
			}
			f = languageString+"_foxtype.yml";
			foxtypelang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, foxtypelang, yamlManager.getFoxTypeLanguageKey()))
			{
				return false;
			}
			f = languageString+"_mapcursortype.yml";
			mapcursortypelang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, mapcursortypelang, yamlManager.getMapCursorTypeLanguageKey()))
			{
				return false;
			}
			f = languageString+"_rabbittype.yml";
			rabbittypelang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, rabbittypelang, yamlManager.getRabbitTypeLanguageKey()))
			{
				return false;
			}
			f = languageString+"_villagertype.yml";
			villagertypelang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, villagertypelang, yamlManager.getVillagerTypeLanguageKey()))
			{
				return false;
			}
			f = languageString+"_villagerprofession.yml";
			villagerprofessionlang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, villagerprofessionlang, yamlManager.getVillagerProfessionLanguageKey()))
			{
				return false;
			}
			f = languageString+"_treetype.yml";
			treetypelang = YamlDocument.create(new File(directory,"%f%.yml".replace("%f%", f)),
					getClass().getResourceAsStream("/default.yml"),gsd,lsd,dsd,usd);
			if(!setupStaticFile(f, treetypelang, yamlManager.getTreeTypeLanguageKey()))
			{
				return false;
			}
		} catch(Exception e)
		{
			logger.severe("Could not create/load %f%.yml file! Plugin will shut down!".replace("%f%", f));
			return false;
		}
		return true;
	}
	
	private boolean setupStaticFile(String f, YamlDocument yd, LinkedHashMap<String, Language> map) throws IOException
	{
		yd.update();
		if(f.equals("config") && config != null)
		{
			//If Config already exists
			languages = administrationLanguage == null 
					? config.getString("Language", "ENG").toUpperCase() 
					: administrationLanguage;
			setLanguage();
		}
		for(String key : map.keySet())
		{
			Language languageObject = map.get(key);
			if(languageObject.languageValues.containsKey(yamlManager.getLanguageType()) == true)
			{
				yamlManager.setFileInput(yd, map, key, yamlManager.getLanguageType());
			} else if(languageObject.languageValues.containsKey(yamlManager.getDefaultLanguageType()) == true)
			{
				yamlManager.setFileInput(yd, map, key, yamlManager.getDefaultLanguageType());
			}
		}
		yd.save();
		if(f.equals("config") && config != null)
    	{
			//if Config was created the first time
			languages = administrationLanguage == null 
					? config.getString("Language", "ENG").toUpperCase() 
					: administrationLanguage;
			setLanguage();
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
		yamlManager.setLanguageType(languageType);
	}
}