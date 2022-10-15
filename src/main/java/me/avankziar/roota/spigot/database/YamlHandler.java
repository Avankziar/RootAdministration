package main.java.me.avankziar.roota.spigot.database;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import main.java.me.avankziar.roota.general.Language;
import main.java.me.avankziar.roota.general.Language.ISO639_2B;
import main.java.me.avankziar.roota.general.YamlManager;
import main.java.me.avankziar.roota.spigot.RootA;

public class YamlHandler
{
	private RootA plugin;
	private File config = null;
	private YamlConfiguration cfg = new YamlConfiguration();
	
	private String languages;
	
	private File matlanguage = null;
	private YamlConfiguration matlang = new YamlConfiguration();
	private File enchlanguage = null;
	private YamlConfiguration enchlang = new YamlConfiguration();
	private File bannerlanguage = null;
	private YamlConfiguration bannerlang = new YamlConfiguration();
	private File itemflaglanguage = null;
	private YamlConfiguration itemflaglang = new YamlConfiguration();
	private File potiontypelanguage = null;
	private YamlConfiguration potiontypelang = new YamlConfiguration();
	private File potioneffecttypelanguage = null;
	private YamlConfiguration potioneffecttypelang = new YamlConfiguration();
	private File entitytypelanguage = null;
	private YamlConfiguration entitytypelang = new YamlConfiguration();
	private File axolotlvariantlanguage = null;
	private YamlConfiguration axolotlvariantlang = new YamlConfiguration();
	private File bookmetagenerationlanguage = null;
	private YamlConfiguration bookmetagenerationlang = new YamlConfiguration();
	private File colorlanguage = null;
	private YamlConfiguration colorlang = new YamlConfiguration();
	private File dyecolorlanguage = null;
	private YamlConfiguration dyecolorlang = new YamlConfiguration();
	private File tropicalfishbucketlanguage = null;
	private YamlConfiguration tropicalfishbucketlang = new YamlConfiguration();
	private File cattypelanguage = null;
	private YamlConfiguration cattypelang = new YamlConfiguration();
	private File foxtypelanguage = null;
	private YamlConfiguration foxtypelang = new YamlConfiguration();
	private File mapcursortypelanguage = null;
	private YamlConfiguration mapcursortypelang = new YamlConfiguration();
	private File rabbittypelanguage = null;
	private YamlConfiguration rabbittypelang = new YamlConfiguration();
	private File villagertypelanguage = null;
	private YamlConfiguration villagertypelang = new YamlConfiguration();
	private File villagerprofessionlanguage = null;
	private YamlConfiguration villagerprofessionlang = new YamlConfiguration();
	private File treetypelanguage = null;
	private YamlConfiguration treetypelang = new YamlConfiguration();

	public YamlHandler(RootA plugin)
	{
		this.plugin = plugin;
		loadYamlHandler();
	}
	
	public YamlConfiguration getConfig()
	{
		return cfg;
	}
	
	public YamlConfiguration getMaterialLang()
	{
		return matlang;
	}
	
	public YamlConfiguration getEnchLang()
	{
		return enchlang;
	}
	
	public YamlConfiguration getBannerLang()
	{
		return bannerlang;
	}
	
	public YamlConfiguration getItemFlagLang()
	{
		return itemflaglang;
	}
	
	public YamlConfiguration getPotionTypeLang()
	{
		return potiontypelang;
	}
	
	public YamlConfiguration getPotionEffectTypeLang()
	{
		return potioneffecttypelang;
	}
	
	public YamlConfiguration getEntityTypeLang()
	{
		return entitytypelang;
	}
	
	public YamlConfiguration getAxolotlVariantLang()
	{
		return axolotlvariantlang;
	}
	
	public YamlConfiguration getBookMetaGenerationLang()
	{
		return bookmetagenerationlang;
	}
	
	public YamlConfiguration getColorLang()
	{
		return colorlang;
	}
	
	public YamlConfiguration getDyeColorLang()
	{
		return dyecolorlang;
	}
	
	public YamlConfiguration getTropicalFishBucketLang()
	{
		return tropicalfishbucketlang;
	}
	
	public YamlConfiguration getCatTypeLang()
	{
		return cattypelang;
	}
	
	public YamlConfiguration getFoxTypeLang()
	{
		return foxtypelang;
	}
	
	public YamlConfiguration getMapCursorTypeLang()
	{
		return mapcursortypelang;
	}
	
	public YamlConfiguration getRabbitTypeLang()
	{
		return rabbittypelang;
	}
	
	public YamlConfiguration getVillagerTypeLang()
	{
		return villagertypelang;
	}
	
	public YamlConfiguration getVillagerProfessionLang()
	{
		return villagerprofessionlang;
	}
	
	public YamlConfiguration getTreeTypeLang()
	{
		return treetypelang;
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
	
	@SuppressWarnings("deprecation")
	private boolean writeFile(File file, YamlConfiguration yml, LinkedHashMap<String, Language> keyMap)
	{
		yml.options().header("For more explanation see \n https://www.spigotmc.org/resources/rootadministration.104833/");
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
		if(!mkdirDynamicFiles())
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
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, config.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		cfg = loadYamlTask(config, cfg);
		if (cfg == null)
		{
			return false;
		}
		writeFile(config, cfg, plugin.getYamlManager().getConfigKey());
		
		languages = cfg.getString("Language", "ENG").toUpperCase();
		return true;
	}
	
	private boolean mkdirDynamicFiles()
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
		if(!mkdirLanguage())
		{
			return false;
		}
		return true;
	}
	
	private boolean mkdirLanguage()
	{
		String languageString = plugin.getYamlManager().getLanguageType().toString().toLowerCase();
		File directory = new File(plugin.getDataFolder()+"/Languages/");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		/*language = new File(directory.getPath(), languageString+".yml");
		if(!language.exists()) 
		{
			SaLE.log.info("Create %lang%.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, language.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		lang = loadYamlTask(language, lang);
		if (lang == null)
		{
			return false;
		}
		writeFile(language, lang, plugin.getYamlManager().getLanguageKey());*/
		
		matlanguage = new File(directory.getPath(), languageString+"_material.yml");
		if(!matlanguage.exists()) 
		{
			RootA.log.info("Create %lang%_material.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, matlanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		matlang = loadYamlTask(matlanguage, matlang);
		if(matlang == null)
		{
			return false;
		}
		writeFile(matlanguage, matlang, plugin.getYamlManager().getMaterialLanguageKey());
		
		enchlanguage = new File(directory.getPath(), languageString+"_enchantment.yml");
		if(!enchlanguage.exists()) 
		{
			RootA.log.info("Create %lang%_enchantment.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, enchlanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		enchlang = loadYamlTask(enchlanguage, enchlang);
		if(enchlang == null)
		{
			return false;
		}
		writeFile(enchlanguage, enchlang, plugin.getYamlManager().getEnchantmentLanguageKey());
		
		bannerlanguage = new File(directory.getPath(), languageString+"_banner.yml");
		if(!bannerlanguage.exists()) 
		{
			RootA.log.info("Create %lang%_banner.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, bannerlanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		bannerlang = loadYamlTask(bannerlanguage, bannerlang);
		if(bannerlang == null)
		{
			return false;
		}
		writeFile(bannerlanguage, bannerlang, plugin.getYamlManager().getBannerLanguageKey());
		
		itemflaglanguage = new File(directory.getPath(), languageString+"_itemflag.yml");
		if(!itemflaglanguage.exists()) 
		{
			RootA.log.info("Create %lang%_itemflag.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, itemflaglanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		itemflaglang = loadYamlTask(itemflaglanguage, itemflaglang);
		if(itemflaglang == null)
		{
			return false;
		}
		writeFile(itemflaglanguage, itemflaglang, plugin.getYamlManager().getItemFlagLanguageKey());
		
		potiontypelanguage = new File(directory.getPath(), languageString+"_potiontype.yml");
		if(!potiontypelanguage.exists()) 
		{
			RootA.log.info("Create %lang%_potiontype.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, potiontypelanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		potiontypelang = loadYamlTask(potiontypelanguage, potiontypelang);
		if(potiontypelang == null)
		{
			return false;
		}
		writeFile(potiontypelanguage, potiontypelang, plugin.getYamlManager().getPotionTypeLanguageKey());
		
		potioneffecttypelanguage = new File(directory.getPath(), languageString+"_potioneffecttype.yml");
		if(!potioneffecttypelanguage.exists()) 
		{
			RootA.log.info("Create %lang%_potioneffecttype.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, potioneffecttypelanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		potioneffecttypelang = loadYamlTask(potioneffecttypelanguage, potioneffecttypelang);
		if(potioneffecttypelang == null)
		{
			return false;
		}
		writeFile(potioneffecttypelanguage, potioneffecttypelang, plugin.getYamlManager().getPotionEffectTypeLanguageKey());
		
		entitytypelanguage = new File(directory.getPath(), languageString+"_entitytype.yml");
		if(!entitytypelanguage.exists()) 
		{
			RootA.log.info("Create %lang%_entitytype.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, entitytypelanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		entitytypelang = loadYamlTask(entitytypelanguage, entitytypelang);
		if(entitytypelang == null)
		{
			return false;
		}
		writeFile(entitytypelanguage, entitytypelang, plugin.getYamlManager().getEntityTypeLanguageKey());
		
		axolotlvariantlanguage = new File(directory.getPath(), languageString+"_axolotlvariant.yml");
		if(!axolotlvariantlanguage.exists()) 
		{
			RootA.log.info("Create %lang%_axolotlvariant.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, axolotlvariantlanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		axolotlvariantlang = loadYamlTask(axolotlvariantlanguage, axolotlvariantlang);
		if(axolotlvariantlang == null)
		{
			return false;
		}
		writeFile(axolotlvariantlanguage, axolotlvariantlang, plugin.getYamlManager().getAxolotlVariantLanguageKey());
		
		bookmetagenerationlanguage = new File(directory.getPath(), languageString+"_bookmetageneration.yml");
		if(!bookmetagenerationlanguage.exists()) 
		{
			RootA.log.info("Create %lang%_bookmetageneration.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, bookmetagenerationlanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		bookmetagenerationlang = loadYamlTask(bookmetagenerationlanguage, bookmetagenerationlang);
		if(bookmetagenerationlang == null)
		{
			return false;
		}
		writeFile(bookmetagenerationlanguage, bookmetagenerationlang, plugin.getYamlManager().getBookMetaGenerationLanguageKey());
		
		colorlanguage = new File(directory.getPath(), languageString+"_color.yml");
		if(!colorlanguage.exists()) 
		{
			RootA.log.info("Create %lang%_color.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, colorlanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		colorlang = loadYamlTask(colorlanguage, colorlang);
		if(colorlang == null)
		{
			return false;
		}
		writeFile(colorlanguage, colorlang, plugin.getYamlManager().getColorLanguageKey());
		
		dyecolorlanguage = new File(directory.getPath(), languageString+"_dyecolor.yml");
		if(!dyecolorlanguage.exists()) 
		{
			RootA.log.info("Create %lang%_dyecolor.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, dyecolorlanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		dyecolorlang = loadYamlTask(dyecolorlanguage, dyecolorlang);
		if(dyecolorlang == null)
		{
			return false;
		}
		writeFile(dyecolorlanguage, dyecolorlang, plugin.getYamlManager().getDyeColorLanguageKey());
		
		tropicalfishbucketlanguage = new File(directory.getPath(), languageString+"_tropicalfishbucket.yml");
		if(!tropicalfishbucketlanguage.exists()) 
		{
			RootA.log.info("Create %lang%_tropicalfishbucket.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, tropicalfishbucketlanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		tropicalfishbucketlang = loadYamlTask(tropicalfishbucketlanguage, tropicalfishbucketlang);
		if(tropicalfishbucketlang == null)
		{
			return false;
		}
		writeFile(tropicalfishbucketlanguage, tropicalfishbucketlang, plugin.getYamlManager().getTropicalFishBucketLanguageKey());
		
		cattypelanguage = new File(directory.getPath(), languageString+"_cattype.yml");
		if(!cattypelanguage.exists()) 
		{
			RootA.log.info("Create %lang%_cattype.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, cattypelanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		cattypelang = loadYamlTask(cattypelanguage, cattypelang);
		if(cattypelang == null)
		{
			return false;
		}
		writeFile(cattypelanguage, cattypelang, plugin.getYamlManager().getCatTypeLanguageKey());
		
		foxtypelanguage = new File(directory.getPath(), languageString+"_foxtype.yml");
		if(!foxtypelanguage.exists()) 
		{
			RootA.log.info("Create %lang%_foxtype.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, foxtypelanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		foxtypelang = loadYamlTask(foxtypelanguage, foxtypelang);
		if(foxtypelang == null)
		{
			return false;
		}
		writeFile(foxtypelanguage, foxtypelang, plugin.getYamlManager().getFoxTypeLanguageKey());
		
		mapcursortypelanguage = new File(directory.getPath(), languageString+"_mapcursortype.yml");
		if(!mapcursortypelanguage.exists()) 
		{
			RootA.log.info("Create %lang%_mapcursortype.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, mapcursortypelanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		mapcursortypelang = loadYamlTask(mapcursortypelanguage, mapcursortypelang);
		if(mapcursortypelang == null)
		{
			return false;
		}
		writeFile(mapcursortypelanguage, mapcursortypelang, plugin.getYamlManager().getMapCursorTypeLanguageKey());
		
		rabbittypelanguage = new File(directory.getPath(), languageString+"_rabbittype.yml");
		if(!rabbittypelanguage.exists()) 
		{
			RootA.log.info("Create %lang%_rabbittype.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, rabbittypelanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		rabbittypelang = loadYamlTask(rabbittypelanguage, rabbittypelang);
		if(rabbittypelang == null)
		{
			return false;
		}
		writeFile(rabbittypelanguage, rabbittypelang, plugin.getYamlManager().getRabbitTypeLanguageKey());
		
		villagertypelanguage = new File(directory.getPath(), languageString+"_villagertype.yml");
		if(!villagertypelanguage.exists()) 
		{
			RootA.log.info("Create %lang%_villagertype.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, villagertypelanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		villagertypelang = loadYamlTask(villagertypelanguage, villagertypelang);
		if(villagertypelang == null)
		{
			return false;
		}
		writeFile(villagertypelanguage, villagertypelang, plugin.getYamlManager().getVillagerTypeLanguageKey());
		
		villagerprofessionlanguage = new File(directory.getPath(), languageString+"_villagerprofession.yml");
		if(!villagerprofessionlanguage.exists()) 
		{
			RootA.log.info("Create %lang%_villagerprofession.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, villagerprofessionlanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		villagerprofessionlang = loadYamlTask(villagerprofessionlanguage, villagerprofessionlang);
		if(villagerprofessionlang == null)
		{
			return false;
		}
		writeFile(villagerprofessionlanguage, villagerprofessionlang, plugin.getYamlManager().getVillagerProfessionLanguageKey());
		
		treetypelanguage = new File(directory.getPath(), languageString+"_treetype.yml");
		if(!treetypelanguage.exists()) 
		{
			RootA.log.info("Create %lang%_treetype.yml...".replace("%lang%", languageString));
			try(InputStream in = plugin.getResource("default.yml"))
			{
				Files.copy(in, treetypelanguage.toPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		treetypelang = loadYamlTask(treetypelanguage, treetypelang);
		if(treetypelang == null)
		{
			return false;
		}
		writeFile(treetypelanguage, treetypelang, plugin.getYamlManager().getTreeTypeLanguageKey());
		return true;
	}
}