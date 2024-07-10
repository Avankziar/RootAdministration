package main.java.me.avankziar.roota.general.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import main.java.me.avankziar.roota.general.database.Language.ISO639_2B;

public class YamlManager
{
	public enum Type
	{
		BUNGEE, SPIGOT, VELO;
	}
	
	private ISO639_2B languageType = ISO639_2B.GER;
	//The default language of your plugin. Mine is german.
	private ISO639_2B defaultLanguageType = ISO639_2B.GER;
	private Type type;
	
	//Per Flatfile a linkedhashmap.
	private static LinkedHashMap<String, Language> configKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> matlanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> enchlanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> bannerlanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> itemflaglanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> potiontypelanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> potioneffecttypelanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> entitytypelanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> axolotlvariantlanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> bookmetagenerationlanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> colorlanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> dyecolorlanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> tropicalfishbucketlanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> cattypelanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> foxtypelanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> mapcursortypelanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> rabbittypelanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> villagertypelanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> villagerprofessionlanguageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, Language> treetypelanguageKeys = new LinkedHashMap<>();
	
	public YamlManager(Type type)
	{
		if(type == Type.SPIGOT)
		{
			initConfig(type);
			initMaterialLanguage();
			initEnchantmentLanguage();
			initBannerLanguage();
			initItemFlagLanguage();
			initPotionTypeLanguage();
			initPotionEffectTypeLanguage();
			initEntityType();
			initAxolotlVariant();
			initBookMetaGeneration();
			initColor();
			initDyeColor();
			initTropicalFish();
			initCatType();
			initFoxType();
			initMapCursorType();
			initRabbitType();
			initVillagerType();
			initVillagerProfession();
			initTreeType();
		}
		if(type == Type.BUNGEE || type == Type.VELO)
		{
			initConfig(type);
		}		
	}
	
	public ISO639_2B getLanguageType()
	{
		return languageType;
	}

	public void setLanguageType(ISO639_2B languageType)
	{
		this.languageType = languageType;
	}
	
	public ISO639_2B getDefaultLanguageType()
	{
		return defaultLanguageType;
	}
	
	public LinkedHashMap<String, Language> getConfigKey()
	{
		return configKeys;
	}
	
	public LinkedHashMap<String, Language> getMaterialLanguageKey()
	{
		return matlanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getEnchantmentLanguageKey()
	{
		return enchlanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getBannerLanguageKey()
	{
		return bannerlanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getItemFlagLanguageKey()
	{
		return itemflaglanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getPotionTypeLanguageKey()
	{
		return potiontypelanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getPotionEffectTypeLanguageKey()
	{
		return potioneffecttypelanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getEntityTypeLanguageKey()
	{
		return entitytypelanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getAxolotlVariantLanguageKey()
	{
		return axolotlvariantlanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getBookMetaGenerationLanguageKey()
	{
		return bookmetagenerationlanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getColorLanguageKey()
	{
		return colorlanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getDyeColorLanguageKey()
	{
		return dyecolorlanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getTropicalFishBucketLanguageKey()
	{
		return tropicalfishbucketlanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getCatTypeLanguageKey()
	{
		return cattypelanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getFoxTypeLanguageKey()
	{
		return foxtypelanguageKeys;
	}

	public LinkedHashMap<String, Language> getMapCursorTypeLanguageKey()
	{
		return mapcursortypelanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getRabbitTypeLanguageKey()
	{
		return rabbittypelanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getVillagerTypeLanguageKey()
	{
		return villagertypelanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getVillagerProfessionLanguageKey()
	{
		return villagerprofessionlanguageKeys;
	}
	
	public LinkedHashMap<String, Language> getTreeTypeLanguageKey()
	{
		return treetypelanguageKeys;
	}
	
	public void setFileInputBukkit(org.bukkit.configuration.file.YamlConfiguration yml,
			LinkedHashMap<String, Language> keyMap, String key, ISO639_2B languageType)
	{
		if(!keyMap.containsKey(key))
		{
			return;
		}
		if(key.startsWith("#"))
		{
			//Comments
			String k = key.replace("#", "");
			if(yml.get(k) == null)
			{
				//return because no aktual key are present
				return;
			}
			if(yml.getComments(k) != null && !yml.getComments(k).isEmpty())
			{
				//Return, because the comments are already present, and there could be modified. F.e. could be comments from a admin.
				return;
			}
			if(keyMap.get(key).languageValues.get(languageType).length == 1)
			{
				if(keyMap.get(key).languageValues.get(languageType)[0] instanceof String)
				{
					String s = ((String) keyMap.get(key).languageValues.get(languageType)[0]).replace("\r\n", "");
					yml.setComments(k, Arrays.asList(s));
				}
			} else
			{
				List<Object> list = Arrays.asList(keyMap.get(key).languageValues.get(languageType));
				ArrayList<String> stringList = new ArrayList<>();
				if(list instanceof List<?>)
				{
					for(Object o : list)
					{
						if(o instanceof String)
						{
							stringList.add(((String) o).replace("\r\n", ""));
						}
					}
				}
				yml.setComments(k, (List<String>) stringList);
			}
			return;
		}
		if(yml.get(key) != null)
		{
			return;
		}
		if(keyMap.get(key).languageValues.get(languageType).length == 1)
		{
			if(keyMap.get(key).languageValues.get(languageType)[0] instanceof String)
			{
				yml.set(key, ((String) keyMap.get(key).languageValues.get(languageType)[0]).replace("\r\n", ""));
			} else
			{
				yml.set(key, keyMap.get(key).languageValues.get(languageType)[0]);
			}
		} else
		{
			List<Object> list = Arrays.asList(keyMap.get(key).languageValues.get(languageType));
			ArrayList<String> stringList = new ArrayList<>();
			if(list instanceof List<?>)
			{
				for(Object o : list)
				{
					if(o instanceof String)
					{
						stringList.add(((String) o).replace("\r\n", ""));
					} else
					{
						stringList.add(o.toString().replace("\r\n", ""));
					}
				}
			}
			yml.set(key, (List<String>) stringList);
		}
	}
	
	public void setFileInputBungee(net.md_5.bungee.config.Configuration yml,
			LinkedHashMap<String, Language> keyMap, String key, ISO639_2B languageType)
	{
		if(!keyMap.containsKey(key))
		{
			return;
		}
		if(key.startsWith("#"))
		{
			//Comments cannot funktion on bungee
			return;
		}
		if(yml.get(key) != null)
		{
			return;
		}
		if(keyMap.get(key).languageValues.get(languageType).length == 1)
		{
			if(keyMap.get(key).languageValues.get(languageType)[0] instanceof String)
			{
				yml.set(key, ((String) keyMap.get(key).languageValues.get(languageType)[0]).replace("\r\n", ""));
			} else
			{
				yml.set(key, keyMap.get(key).languageValues.get(languageType)[0]);
			}
		} else
		{
			List<Object> list = Arrays.asList(keyMap.get(key).languageValues.get(languageType));
			ArrayList<String> stringList = new ArrayList<>();
			if(list instanceof List<?>)
			{
				for(Object o : list)
				{
					if(o instanceof String)
					{
						stringList.add(((String) o).replace("\r\n", ""));
					} else
					{
						stringList.add(o.toString().replace("\r\n", ""));
					}
				}
			}
			yml.set(key, (List<String>) stringList);
		}
	}
	
	public void setFileInput(dev.dejvokep.boostedyaml.YamlDocument yml,
			LinkedHashMap<String, Language> keyMap, String key, ISO639_2B languageType) throws org.spongepowered.configurate.serialize.SerializationException
	{
		if(!keyMap.containsKey(key))
		{
			return;
		}
		if(yml.get(key) != null)
		{
			return;
		}
		if(key.startsWith("#"))
		{
			//Comments
			String k = key.replace("#", "");
			if(yml.get(k) == null)
			{
				//return because no actual key are present
				return;
			}
			if(yml.getBlock(k) == null)
			{
				return;
			}
			if(yml.getBlock(k).getComments() != null && !yml.getBlock(k).getComments().isEmpty())
			{
				//Return, because the comments are already present, and there could be modified. F.e. could be comments from a admin.
				return;
			}
			if(keyMap.get(key).languageValues.get(languageType).length == 1)
			{
				if(keyMap.get(key).languageValues.get(languageType)[0] instanceof String)
				{
					String s = ((String) keyMap.get(key).languageValues.get(languageType)[0]).replace("\r\n", "");
					yml.getBlock(k).setComments(Arrays.asList(s));
				}
			} else
			{
				List<Object> list = Arrays.asList(keyMap.get(key).languageValues.get(languageType));
				ArrayList<String> stringList = new ArrayList<>();
				if(list instanceof List<?>)
				{
					for(Object o : list)
					{
						if(o instanceof String)
						{
							stringList.add(((String) o).replace("\r\n", ""));
						}
					}
				}
				yml.getBlock(k).setComments((List<String>) stringList);
			}
			return;
		}
		if(keyMap.get(key).languageValues.get(languageType).length == 1)
		{
			if(keyMap.get(key).languageValues.get(languageType)[0] instanceof String)
			{
				yml.set(key, convertMiniMessageToBungee(((String) keyMap.get(key).languageValues.get(languageType)[0]).replace("\r\n", "")));
			} else
			{
				yml.set(key, keyMap.get(key).languageValues.get(languageType)[0]);
			}
		} else
		{
			List<Object> list = Arrays.asList(keyMap.get(key).languageValues.get(languageType));
			ArrayList<String> stringList = new ArrayList<>();
			if(list instanceof List<?>)
			{
				for(Object o : list)
				{
					if(o instanceof String)
					{
						stringList.add(convertMiniMessageToBungee(((String) o).replace("\r\n", "")));
					} else
					{
						stringList.add(o.toString().replace("\r\n", ""));
					}
				}
			}
			yml.set(key, (List<String>) stringList);
		}
	}
	
	private String convertMiniMessageToBungee(String s)
	{
		if(type != Type.BUNGEE)
		{
			//If Server is not Bungee, there is no need to convert.
			return s;
		}
		StringBuilder b = new StringBuilder();
		for(int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if(c == '<' && i+1 < s.length())
			{
				char cc = s.charAt(i+1);
				if(cc == '#' && i+8 < s.length())
				{
					//Hexcolors
					//     i12345678
					//f.e. <#00FF00>
					String rc = s.substring(i, i+8);
					b.append(rc.replace("<#", "&#").replace(">", ""));
					i += 8;
				} else
				{
					//Normal Colors
					String r = null;
					StringBuilder sub = new StringBuilder();
					sub.append(c).append(cc);
					i++;
					for(int j = i+1; j < s.length(); j++)
					{
						i++;
						char jc = s.charAt(j);
						if(jc == '>')
						{
							sub.append(jc);
							switch(sub.toString())
							{
							case "</color>":
							case "</black>":
							case "</dark_blue>":
							case "</dark_green>":
							case "</dark_aqua>":
							case "</dark_red>":
							case "</dark_purple>":
							case "</gold>":
							case "</gray>":
							case "</dark_gray>":
							case "</blue>":
							case "</green>":
							case "</aqua>":
							case "</red>":
							case "</light_purple>":
							case "</yellow>":
							case "</white>":
							case "</obf>":
							case "</obfuscated>":
							case "</b>":
							case "</bold>":
							case "</st>":
							case "</strikethrough>":
							case "</u>":
							case "</underlined>":
							case "</i>":
							case "</em>":
							case "</italic>":
								r = "";
								break;
							case "<black>":
								r = "&0";
								break;
							case "<dark_blue>":
								r = "&1";
								break;
							case "<dark_green>":
								r = "&2";
								break;
							case "<dark_aqua>":
								r = "&3";
								break;
							case "<dark_red>":
								r = "&4";
								break;
							case "<dark_purple>":
								r = "&5";
								break;
							case "<gold>":
								r = "&6";
								break;
							case "<gray>":
								r = "&7";
								break;
							case "<dark_gray>":
								r = "&8";
								break;
							case "<blue>":
								r = "&9";
								break;
							case "<green>":
								r = "&a";
								break;
							case "<aqua>":
								r = "&b";
								break;
							case "<red>":
								r = "&c";
								break;
							case "<light_purple>":
								r = "&d";
								break;
							case "<yellow>":
								r = "&e";
								break;
							case "<white>":
								r = "&f";
								break;
							case "<obf>":
							case "<obfuscated>":
								r = "&k";
								break;
							case "<b>":
							case "<bold>":
								r = "&l";
								break;
							case "<st>":
							case "<strikethrough>":
								r = "&m";
								break;
							case "<u>":
							case "<underlined>":
								r = "&n";
								break;
							case "<i>":
							case "<em>":
							case "<italic>":
								r = "&o";
								break;
							case "<reset>":
								r = "&r";
								break;
							case "<newline>":
								r = "~!~";
								break;
							}
							b.append(r);
							break;
						} else
						{
							//Search for the color.
							sub.append(jc);
						}
					}
				}
			} else
			{
				b.append(c);
			}
		}
		return b.toString();
	}
	
	private void addComments(LinkedHashMap<String, Language> mapKeys, String path, Object[] o)
	{
		mapKeys.put(path, new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, o));
	}
	
	private void addConfig(String path, Object[] c, Object[] o)
	{
		configKeys.put(path, new Language(new ISO639_2B[] {ISO639_2B.GER}, c));
		addComments(configKeys, "#"+path, o);
	}
	
	public void initConfig(Type type) //INFO:Config
	{
		addConfig("IFHAdministrationPath", 
				new Object[] {
				"roota"},
				new Object[] {
				"Diese Funktion sorgt dafür, dass Roota selbstständig auf seine eigene Funktion des PluginForwarding, siehe unten zugreifen kann.",
				"This function ensures that Roota can independently access its own PluginForwarding function, see below."});
		addConfig("Language",
				new Object[] {
				"ENG"},
				new Object[] {
				"",
				"Die eingestellte Sprache. Von Haus aus sind 'ENG=Englisch' und 'GER=Deutsch' mit dabei.",
				"Falls andere Sprachen gewünsch sind, kann man unter den folgenden Links nachschauen, welchs Kürzel für welche Sprache gedacht ist.",
				"Siehe hier nach, sowie den Link, welche dort auch für Wikipedia steht.",
				"https://github.com/Avankziar/RootAdministration/blob/main/src/main/java/me/avankziar/roota/general/Language.java",
				"",
				"The set language. By default, ENG=English and GER=German are included.",
				"If other languages are required, you can check the following links to see which abbreviation is intended for which language.",
				"See here, as well as the link, which is also there for Wikipedia.",
				"https://github.com/Avankziar/RootAdministration/blob/main/src/main/java/me/avankziar/roota/general/Language.java"});
		if(type == Type.SPIGOT)
		{
			addConfig("Server",
					new Object[] {
					"hub"},
					new Object[] {
					"",
					"Der Server steht für den Namen des Spigotservers, wie er in BungeeCord/Waterfall config.yml unter dem Pfad 'servers' angegeben ist.",
					"",
					"The server stands for the name of the spigot server as specified in BungeeCord/Waterfall config.yml under the path 'servers'."});
		}
		addConfig("PluginForwarding",
				new Object[] {
				"aep>>>default", 
				"ash>>>one",
				"afkr>>>default",
				"btm>>>default",
				"mavec>>>default",
				"mhr>>>default",
				"roota>>>default",
				"sale>>>default",
				"scc>>>default",
				"tt>>>default"},
				new Object[] {
				"",
				"Die PluginForwarding Mechanik ist die Hauptmethodik in der IFH Administration. Sie sorgt dafür das über IFH externe Plugins,",
				"an Roota mit einem Schlüsselbegriff herantreten können, damit Roota dann den exteren Plugins den Pfad zu den Mysqldaten übermitteln kann.",
				"Bspw. wenn Roota selber über IFH an seine eigene Methodik heran geht, übermittelt Roota den Schlüsselbegriff 'roota' an IFH, welcher",
				"wiederum an Roota zurückgeht. Als Standart ab dem Build 1-5-0 kommt dann 'default' zurück.",
				"Deshalb wird in der List es auch als 'roota>>>default' deklariert. Die drei > sind nur eine Trenner bzw. eine Verbildlichung.",
				"",
				"The plugin forwarding mechanism is the main method in IFH administration. It ensures that external plugins can approach Roota with a key term via IFH,",
				"so that Roota can then transmit the path to the mysql data to the external plugins.",
				"For example, if Roota itself approaches its own methodology via IFH, Roota transmits the key term 'roota' to IFH, which in turn returns to Roota.",
				"As standard from build 1-5-0 'default' is then returned.",
				"This is why it is also declared as 'roota>>>default' in the list. The three > are only a separator and/or a visualization."});
		addConfig("Mysql.default.IsActive",
				new Object[] {
				false},
				new Object[] {
				"",
				"isActive ist eine simple Sicherheitsfunktion für das Plugin. Sie stellt sicher, dass man aktiv bestätigen muss, dass alle Werte eingetragen worden sind.",
				"Diesen Wert somit auf true stellen, wenn alle Daten korrekt eingegeben wurden. Sollte etwas falsch oder unvollständig eingetragen worden sein,",
				"so wird man eine Fehlermeldung in der Konsole nachlesen könne.",
				"",
				"isActive is a simple security function for the plugin. It ensures that you must actively confirm that all values have been entered.",
				"Set this value to true if all data has been entered correctly.",
				"If something has been entered incorrectly or incompletely, you will see an error message in the console."});
		addComments(configKeys, "#Mysql", 
				new Object[] {
				"","Hier im 'default' Pfad ist einer der Möglichen Mysqldaten hinterlegt. Es können unbegrenzt weitere Pfade angegeben werden,",
				"um verschiedenste Verbindungsdaten zuhinterlegen.",
				"",
				"One of the possible mysql data is stored here in the 'default' path.",
				"An unlimited number of other paths can be specified in order to store a wide variety of connection data."});
		addConfig("Mysql.default.Host",
				new Object[] {
				"127.0.0.1"},
				new Object[] {
				"",
				"Der Host, oder auch die IP. Sie kann aus einer Zahlenkombination oder aus einer Adresse bestehen.",
				"Für den Lokalhost, ist es möglich entweder 127.0.0.1 oder 'localhost' einzugeben. Bedenke, manchmal kann es vorkommen,",
				"das bei gehosteten Server die ServerIp oder Lokalhost möglich ist.",
				"",
				"The host, or IP. It can consist of a number combination or an address.",
				"For the local host, it is possible to enter either 127.0.0.1 or 'localhost'.",
				"Please note that sometimes the serverIp or localhost is possible for hosted servers."});
		addConfig("Mysql.default.Port",
				new Object[] {
				3306},
				new Object[] {
				"",
				"Ein Port oder eine Portnummer ist in Rechnernetzen eine Netzwerkadresse,",
				"mit der das Betriebssystem die Datenpakete eines Transportprotokolls zu einem Prozess zuordnet.",
				"Ein Port für Mysql ist standart gemäß 3306.",
				"",
				"In computer networks, a port or port number ",
				"is a network address with which the operating system assigns the data packets of a transport protocol to a process.",
				"A port for Mysql is standard according to 3306."});
		addConfig("Mysql.default.DatabaseName",
				new Object[] {
				"mydatabase"},
				new Object[] {
				"",
				"Name der Datenbank in Mysql.",
				"",
				"Name of the database in Mysql."});
		addConfig("Mysql.default.SSLEnabled",
				new Object[] {
				false},
				new Object[] {
				"",
				"SSL ist einer der drei Möglichkeiten, welcher, solang man nicht weiß, was es ist, es so lassen sollte wie es ist.",
				"",
				"SSL is one of the three options which, as long as you don't know what it is, you should leave it as it is."});
		addConfig("Mysql.default.AutoReconnect",
				new Object[] {
				true},
				new Object[] {
				"",
				"AutoReconnect ist einer der drei Möglichkeiten, welcher, solang man nicht weiß, was es ist, es so lassen sollte wie es ist.",
				"",
				"AutoReconnect is one of the three options which, as long as you don't know what it is, you should leave it as it is."});
		addConfig("Mysql.default.VerifyServerCertificate",
				new Object[] {
				false},
				new Object[] {
				"",
				"VerifyServerCertificate ist einer der drei Möglichkeiten, welcher, solang man nicht weiß, was es ist, es so lassen sollte wie es ist.",
				"",
				"VerifyServerCertificate is one of the three options which, as long as you don't know what it is, you should leave it as it is."});
		addConfig("Mysql.default.User",
				new Object[] {
				"admin"},
				new Object[] {
				"",
				"Der User, welcher auf die Mysql zugreifen soll.",
				"",
				"The user who should access the Mysql."});
		addConfig("Mysql.default.Password",
				new Object[] {
				"not_0123456789"},
				new Object[] {
				"",
				"Das Passwort des Users, womit er Zugang zu Mysql bekommt.",
				"",
				"The user's password, with which he gets access to Mysql."});
		configKeys.put("Mysql.one.IsActive"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				false}));
		configKeys.put("Mysql.one.Host"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"127.0.0.1"}));
		configKeys.put("Mysql.one.Port"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				3306}));
		configKeys.put("Mysql.one.DatabaseName"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"mydatabase"}));
		configKeys.put("Mysql.one.SSLEnabled"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				false}));
		configKeys.put("Mysql.one.AutoReconnect"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				true}));
		configKeys.put("Mysql.one.VerifyServerCertificate"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				false}));
		configKeys.put("Mysql.one.User"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"admin"}));
		configKeys.put("Mysql.one.Password"
				, new Language(new ISO639_2B[] {ISO639_2B.GER}, new Object[] {
				"not_0123456789"}));
	}
	
	public void initMaterialLanguage()
	{
		for(org.bukkit.Material m : org.bukkit.Material.values())
		{
			String ger = "";
			String eng = m.toString();
			switch(m)
			{
			default:
				//ger = "ger = \""+m.toString()+"\"; eng = \""+m.toString()+"\"; break;"; eng = m.toString(); break;
				ger = eng = m.toString(); break;
			case AIR: ger = "Luft"; eng = "AIR"; break;
			case STONE: ger = "Stein"; eng = "STONE"; break;
			case GRANITE: ger = "Granit"; eng = "GRANITE"; break;
			case POLISHED_GRANITE: ger = "polierter Granit"; eng = "POLISHED_GRANITE"; break;
			case DIORITE: ger = "Diorit"; eng = "DIORITE"; break;
			case POLISHED_DIORITE: ger = "polierter Diorit"; eng = "POLISHED_DIORITE"; break;
			case ANDESITE: ger = "Andesit"; eng = "ANDESITE"; break;
			case POLISHED_ANDESITE: ger = "polierter Andesit"; eng = "POLISHED_ANDESITE"; break;
			case DEEPSLATE: ger = "Tiefenschiefer"; eng = "DEEPSLATE"; break;
			case COBBLED_DEEPSLATE: ger = "Bruchtiefenschiefer"; eng = "COBBLED_DEEPSLATE"; break;
			case POLISHED_DEEPSLATE: ger = "polierter Tiefenschiefer"; eng = "POLISHED_DEEPSLATE"; break;
			case CALCITE: ger = "Kalzit"; eng = "CALCITE"; break;
			case TUFF: ger = "Tuffstein"; eng = "TUFF"; break;
			case DRIPSTONE_BLOCK: ger = "Tropfsteinblock"; eng = "DRIPSTONE_BLOCK"; break;
			case GRASS_BLOCK: ger = "Grasblock"; eng = "GRASS_BLOCK"; break;
			case DIRT: ger = "Erde"; eng = "DIRT"; break;
			case COARSE_DIRT: ger = "grobe Erde"; eng = "COARSE_DIRT"; break;
			case PODZOL: ger = "Podsol"; eng = "PODZOL"; break;
			case ROOTED_DIRT: ger = "Wurzelerde"; eng = "ROOTED_DIRT"; break;
			case MUD: ger = "Schlamm"; eng = "MUD"; break;
			case CRIMSON_NYLIUM: ger = "Nezel"; eng = "CRIMSON_NYLIUM"; break;
			case WARPED_NYLIUM: ger = "Wirr-Nezel"; eng = "WARPED_NYLIUM"; break;
			case COBBLESTONE: ger = "Bruchstein"; eng = "COBBLESTONE"; break;
			case OAK_PLANKS: ger = "Eichenholzbretter"; eng = "OAK_PLANKS"; break;
			case SPRUCE_PLANKS: ger = "Fichtenholzbretter"; eng = "SPRUCE_PLANKS"; break;
			case BIRCH_PLANKS: ger = "Birkenholzbretter"; eng = "BIRCH_PLANKS"; break;
			case JUNGLE_PLANKS: ger = "Tropenholzbretter"; eng = "JUNGLE_PLANKS"; break;
			case ACACIA_PLANKS: ger = "Akazienholzbretter"; eng = "ACACIA_PLANKS"; break;
			case DARK_OAK_PLANKS: ger = "Schwarzeichenholzbretter"; eng = "DARK_OAK_PLANKS"; break;
			case MANGROVE_PLANKS: ger = "Mangrovenbretter"; eng = "MANGROVE_PLANKS"; break;
			case CRIMSON_PLANKS: ger = "Karmesinbretter"; eng = "CRIMSON_PLANKS"; break;
			case WARPED_PLANKS: ger = "Wirrbretter"; eng = "WARPED_PLANKS"; break;
			case OAK_SAPLING: ger = "Eichensetzling"; eng = "OAK_SAPLING"; break;
			case SPRUCE_SAPLING: ger = "Fichtensetzling"; eng = "SPRUCE_SAPLING"; break;
			case BIRCH_SAPLING: ger = "Birkensetzling"; eng = "BIRCH_SAPLING"; break;
			case JUNGLE_SAPLING: ger = "Tropenbaumsetzling"; eng = "JUNGLE_SAPLING"; break;
			case ACACIA_SAPLING: ger = "Akaziensetzling"; eng = "ACACIA_SAPLING"; break;
			case DARK_OAK_SAPLING: ger = "Schwarzeichensetzling"; eng = "DARK_OAK_SAPLING"; break;
			case MANGROVE_PROPAGULE: ger = "Mangroven-Keimling"; eng = "MANGROVE_PROPAGULE"; break;
			case BEDROCK: ger = "Grundgestein"; eng = "BEDROCK"; break;
			case SAND: ger = "Sand"; eng = "SAND"; break;
			case RED_SAND: ger = "roter Sand"; eng = "RED_SAND"; break;
			case GRAVEL: ger = "Kies"; eng = "GRAVEL"; break;
			case COAL_ORE: ger = "Steinkohle"; eng = "COAL_ORE"; break;
			case DEEPSLATE_COAL_ORE: ger = "Tiefenschiefer-Steinkohle"; eng = "DEEPSLATE_COAL_ORE"; break;
			case IRON_ORE: ger = "Eisenerz"; eng = "IRON_ORE"; break;
			case DEEPSLATE_IRON_ORE: ger = "Tiefenschiefer-Eisenerz"; eng = "DEEPSLATE_IRON_ORE"; break;
			case COPPER_ORE: ger = "Kupfererz"; eng = "COPPER_ORE"; break;
			case DEEPSLATE_COPPER_ORE: ger = "Tiefenschiefer-Kupfererz"; eng = "DEEPSLATE_COPPER_ORE"; break;
			case GOLD_ORE: ger = "Golderz"; eng = "Goldore"; break;
			case DEEPSLATE_GOLD_ORE: ger = "Tiefenschiefer-Golderz"; eng = "DEEPSLATE_GOLD_ORE"; break;
			case REDSTONE_ORE: ger = "Redstone-Erz"; eng = "REDSTONE_ORE"; break;
			case DEEPSLATE_REDSTONE_ORE: ger = "Tiefenschiefer-Redstone-Erz"; eng = "DEEPSLATE_REDSTONE_ORE"; break;
			case EMERALD_ORE: ger = "Smaragderz"; eng = "EMERALD_ORE"; break;
			case DEEPSLATE_EMERALD_ORE: ger = "Tiefenschiefer-Smaragderz"; eng = "DEEPSLATE_EMERALD_ORE"; break;
			case LAPIS_ORE: ger = "Lapislazulierz"; eng = "LAPIS_ORE"; break;
			case DEEPSLATE_LAPIS_ORE: ger = "Tiefenschiefer-Lapislazulierz"; eng = "DEEPSLATE_LAPIS_ORE"; break;
			case DIAMOND_ORE: ger = "Diamanterz"; eng = "DIAMOND_ORE"; break;
			case DEEPSLATE_DIAMOND_ORE: ger = "Tiefenschiefer-Diamanterz"; eng = "DEEPSLATE_DIAMOND_ORE"; break;
			case NETHER_GOLD_ORE: ger = "Nethergolderz"; eng = "NETHER_GOLD_ORE"; break;
			case NETHER_QUARTZ_ORE: ger = "Netherquarzerz"; eng = "NETHER_QUARTZ_ORE"; break;
			case ANCIENT_DEBRIS: ger = "Antiker Schrott"; eng = "ANCIENT_DEBRIS"; break;
			case COAL_BLOCK: ger = "Kohleblock"; eng = "COAL_BLOCK"; break;
			case RAW_IRON_BLOCK: ger = "Roheisenblock"; eng = "RAW_IRON_BLOCK"; break;
			case RAW_COPPER_BLOCK: ger = "Rohkupferblock"; eng = "RAW_COPPER_BLOCK"; break;
			case RAW_GOLD_BLOCK: ger = "Rohgoldblock"; eng = "RAW_GOLD_BLOCK"; break;
			case AMETHYST_BLOCK: ger = "Amethystblock"; eng = "AMETHYST_BLOCK"; break;
			case BUDDING_AMETHYST: ger = "Amethystknospenblock"; eng = "BUDDING_AMETHYST"; break;
			case IRON_BLOCK: ger = "Eisenblock"; eng = "IRON_BLOCK"; break;
			case COPPER_BLOCK: ger = "Kupferblock"; eng = "COPPER_BLOCK"; break;
			case GOLD_BLOCK: ger = "Goldblock"; eng = "GOLD_BLOCK"; break;
			case DIAMOND_BLOCK: ger = "Diamantblock"; eng = "DIAMOND_BLOCK"; break;
			case NETHERITE_BLOCK: ger = "Netheritblock"; eng = "NETHERITE_BLOCK"; break;
			case EXPOSED_COPPER: ger = "Angelaufener Kupferblock"; eng = "EXPOSED_COPPER"; break;
			case WEATHERED_COPPER: ger = "Verwitterter Kupferblock"; eng = "WEATHERED_COPPER"; break;
			case OXIDIZED_COPPER: ger = "Oxidierter Kupferblock"; eng = "OXIDIZED_COPPER"; break;
			case CUT_COPPER: ger = "Geschnittener Kupferblock"; eng = "CUT_COPPER"; break;
			case EXPOSED_CUT_COPPER: ger = "Angelaufener geschnittener Kupferblock"; eng = "EXPOSED_CUT_COPPER"; break;
			case WEATHERED_CUT_COPPER: ger = "Verwitterter geschnittener Kupferblock"; eng = "WEATHERED_CUT_COPPER"; break;
			case OXIDIZED_CUT_COPPER: ger = "Oxidierter geschnittener Kupferblock"; eng = "OXIDIZED_CUT_COPPER"; break;
			case CUT_COPPER_STAIRS: ger = "Geschnittene Kupfertreppe"; eng = "CUT_COPPER_STAIRS"; break;
			case EXPOSED_CUT_COPPER_STAIRS: ger = "Angelaufene geschnittene Kupfertreppe"; eng = "EXPOSED_CUT_COPPER_STAIRS"; break;
			case WEATHERED_CUT_COPPER_STAIRS: ger = "Verwitterte geschnittene Kupfertreppe"; eng = "WEATHERED_CUT_COPPER_STAIRS"; break;
			case OXIDIZED_CUT_COPPER_STAIRS: ger = "Oxidierte geschnittene Kupfertreppe"; eng = "OXIDIZED_CUT_COPPER_STAIRS"; break;
			case CUT_COPPER_SLAB: ger = "Geschnittene Kupferstufe"; eng = "CUT_COPPER_SLAB"; break;
			case EXPOSED_CUT_COPPER_SLAB: ger = "Angelaufene geschnittene Kupferstufe"; eng = "EXPOSED_CUT_COPPER_SLAB"; break;
			case WEATHERED_CUT_COPPER_SLAB: ger = "Verwitterte geschnittene Kupferstufe"; eng = "WEATHERED_CUT_COPPER_SLAB"; break;
			case OXIDIZED_CUT_COPPER_SLAB: ger = "Oxidierte geschnittene Kupferstufe"; eng = "OXIDIZED_CUT_COPPER_SLAB"; break;
			case WAXED_COPPER_BLOCK: ger = "Gewachster Kupferblock"; eng = "WAXED_COPPER_BLOCK"; break;
			case WAXED_EXPOSED_COPPER: ger = "Gewachster angelaufener Kupferblock"; eng = "WAXED_EXPOSED_COPPER"; break;
			case WAXED_WEATHERED_COPPER: ger = "Gewachster verwitterter Kupferblock"; eng = "WAXED_WEATHERED_COPPER"; break;
			case WAXED_OXIDIZED_COPPER: ger = "Gewachster oxidierter Kupferblock"; eng = "WAXED_OXIDIZED_COPPER"; break;
			case WAXED_CUT_COPPER: ger = "Gewachster geschnittener Kupferblock"; eng = "WAXED_CUT_COPPER"; break;
			case WAXED_EXPOSED_CUT_COPPER: ger = "Gewachster angelaufener geschnittener Kupferblock"; eng = "WAXED_EXPOSED_CUT_COPPER"; break;
			case WAXED_WEATHERED_CUT_COPPER: ger = "Gewachster verwitterter geschnittener Kupferblock"; eng = "WAXED_WEATHERED_CUT_COPPER"; break;
			case WAXED_OXIDIZED_CUT_COPPER: ger = "Gewachster oxidierter geschnittener Kupferblock"; eng = "WAXED_OXIDIZED_CUT_COPPER"; break;
			case WAXED_CUT_COPPER_STAIRS: ger = "Gewachste geschnittene Kupfertreppe"; eng = "WAXED_CUT_COPPER_STAIRS"; break;
			case WAXED_EXPOSED_CUT_COPPER_STAIRS: ger = "Gewachste angelaufene geschnittene Kupfertreppe"; eng = "WAXED_EXPOSED_CUT_COPPER_STAIRS"; break;
			case WAXED_WEATHERED_CUT_COPPER_STAIRS: ger = "Gewachste verwitterte geschnittene Kupfertreppe"; eng = "WAXED_WEATHERED_CUT_COPPER_STAIRS"; break;
			case WAXED_OXIDIZED_CUT_COPPER_STAIRS: ger = "Gewachste oxidierte geschnittene Kupfertreppe"; eng = "WAXED_OXIDIZED_CUT_COPPER_STAIRS"; break;
			case WAXED_CUT_COPPER_SLAB: ger = "Gewachste geschnittene Kupferstufe"; eng = "WAXED_CUT_COPPER_SLAB"; break;
			case WAXED_EXPOSED_CUT_COPPER_SLAB: ger = "Gewachste angelaufene geschnittene Kupferstufe"; eng = "WAXED_EXPOSED_CUT_COPPER_SLAB"; break;
			case WAXED_WEATHERED_CUT_COPPER_SLAB: ger = "Gewachste verwitterte geschnittene Kupferstufe"; eng = "WAXED_WEATHERED_CUT_COPPER_SLAB"; break;
			case WAXED_OXIDIZED_CUT_COPPER_SLAB: ger = "Gewachste oxidierte geschnittene Kupferstufe"; eng = "WAXED_OXIDIZED_CUT_COPPER_SLAB"; break;
			case OAK_LOG: ger = "Eichenstamm"; eng = "OAK_LOG"; break;
			case SPRUCE_LOG: ger = "Fichtenstamm"; eng = "SPRUCE_LOG"; break;
			case BIRCH_LOG: ger = "Birkenstamm"; eng = "BIRCH_LOG"; break;
			case JUNGLE_LOG: ger = "Tropenbaumstamm"; eng = "JUNGLE_LOG"; break;
			case ACACIA_LOG: ger = "Akazienstamm"; eng = "ACACIA_LOG"; break;
			case DARK_OAK_LOG: ger = "Schwarzeichenstamm"; eng = "DARK_OAK_LOG"; break;
			case MANGROVE_LOG: ger = "Mangrovenstamm"; eng = "MANGROVE_LOG"; break;
			case MANGROVE_ROOTS: ger = "Mangrovenwurzeln"; eng = "MANGROVE_ROOTS"; break;
			case MUDDY_MANGROVE_ROOTS: ger = "Schlammige Mangrovenwurzeln"; eng = "MUDDY_MANGROVE_ROOTS"; break;
			case CRIMSON_STEM: ger = "Karmesinstiel"; eng = "CRIMSON_STEM"; break;
			case WARPED_STEM: ger = "Wirrstiel"; eng = "WARPED_STEM"; break;
			case STRIPPED_OAK_LOG: ger = "Entrindeter Eichenstamm"; eng = "STRIPPED_OAK_LOG"; break;
			case STRIPPED_SPRUCE_LOG: ger = "Entrindeter Fichtenstamm"; eng = "STRIPPED_SPRUCE_LOG"; break;
			case STRIPPED_BIRCH_LOG: ger = "Entrindeter Birkenstamm"; eng = "STRIPPED_BIRCH_LOG"; break;
			case STRIPPED_JUNGLE_LOG: ger = "Entrindeter Tropenbaumstamm"; eng = "STRIPPED_JUNGLE_LOG"; break;
			case STRIPPED_ACACIA_LOG: ger = "Entrindeter Akazienstamm"; eng = "STRIPPED_ACACIA_LOG"; break;
			case STRIPPED_DARK_OAK_LOG: ger = "Entrindeter Schwarzeichenstamm"; eng = "STRIPPED_DARK_OAK_LOG"; break;
			case STRIPPED_MANGROVE_LOG: ger = "Entrindeter Mangrovenstamm"; eng = "STRIPPED_MANGROVE_LOG"; break;
			case STRIPPED_CRIMSON_STEM: ger = "Geschälter Karmesinstiel"; eng = "STRIPPED_CRIMSON_STEM"; break;
			case STRIPPED_WARPED_STEM: ger = "Geschälter Wirrstiel"; eng = "STRIPPED_WARPED_STEM"; break;
			case STRIPPED_OAK_WOOD: ger = "Entrindetes Eichenholz"; eng = "STRIPPED_OAK_WOOD"; break;
			case STRIPPED_SPRUCE_WOOD: ger = "Entrindetes Fichtenholz"; eng = "STRIPPED_SPRUCE_WOOD"; break;
			case STRIPPED_BIRCH_WOOD: ger = "Entrindetes Birkenholz"; eng = "STRIPPED_BIRCH_WOOD"; break;
			case STRIPPED_JUNGLE_WOOD: ger = "Entrindetes Tropenholz"; eng = "STRIPPED_JUNGLE_WOOD"; break;
			case STRIPPED_ACACIA_WOOD: ger = "Entrindetes Akazienholz"; eng = "STRIPPED_ACACIA_WOOD"; break;
			case STRIPPED_DARK_OAK_WOOD: ger = "Entrindetes Schwarzeichenholz"; eng = "STRIPPED_DARK_OAK_WOOD"; break;
			case STRIPPED_MANGROVE_WOOD: ger = "Entrindetes Mangrovenholz"; eng = "STRIPPED_MANGROVE_WOOD"; break;
			case STRIPPED_CRIMSON_HYPHAE: ger = "Geschälte Karmesinhyphen"; eng = "STRIPPED_CRIMSON_HYPHAE"; break;
			case STRIPPED_WARPED_HYPHAE: ger = "Geschälte Wirrhyphen"; eng = "STRIPPED_WARPED_HYPHAE"; break;
			case OAK_WOOD: ger = "Eichenholz"; eng = "OAK_WOOD"; break;
			case SPRUCE_WOOD: ger = "Fichtenholz"; eng = "SPRUCE_WOOD"; break;
			case BIRCH_WOOD: ger = "Birkenholz"; eng = "BIRCH_WOOD"; break;
			case JUNGLE_WOOD: ger = "Tropenholz"; eng = "JUNGLE_WOOD"; break;
			case ACACIA_WOOD: ger = "Akazienholz"; eng = "ACACIA_WOOD"; break;
			case DARK_OAK_WOOD: ger = "Schwarzeichenholz"; eng = "DARK_OAK_WOOD"; break;
			case MANGROVE_WOOD: ger = "Mangrovenholz"; eng = "MANGROVE_WOOD"; break;
			case CRIMSON_HYPHAE: ger = "Karmesinhyphen"; eng = "CRIMSON_HYPHAE"; break;
			case WARPED_HYPHAE: ger = "Wirrhyphen"; eng = "WARPED_HYPHAE"; break;
			case OAK_LEAVES: ger = "Eichenlaub"; eng = "OAK_LEAVES"; break;
			case SPRUCE_LEAVES: ger = "Fichtennadeln"; eng = "SPRUCE_LEAVES"; break;
			case BIRCH_LEAVES: ger = "Birkenlaub"; eng = "BIRCH_LEAVES"; break;
			case JUNGLE_LEAVES: ger = "Tropenbaumlaub"; eng = "JUNGLE_LEAVES"; break;
			case ACACIA_LEAVES: ger = "Akazienlaub"; eng = "ACACIA_LEAVES"; break;
			case DARK_OAK_LEAVES: ger = "Schwarzeichenlaub"; eng = "DARK_OAK_LEAVES"; break;
			case MANGROVE_LEAVES: ger = "Mangrovenlaub"; eng = "MANGROVE_LEAVES"; break;
			case AZALEA_LEAVES: ger = "Azaleenlaub"; eng = "AZALEA_LEAVES"; break;
			case FLOWERING_AZALEA_LEAVES: ger = "Blühendes Azaleenlaub"; eng = "FLOWERING_AZALEA_LEAVES"; break;
			case SPONGE: ger = "Schwamm"; eng = "SPONGE"; break;
			case WET_SPONGE: ger = "Nasser Schwamm"; eng = "WET_SPONGE"; break;
			case GLASS: ger = "Glas"; eng = "GLASS"; break;
			case TINTED_GLASS: ger = "Getöntes Glas"; eng = "TINTED_GLASS"; break;
			case LAPIS_BLOCK: ger = "Lapislazuliblock"; eng = "LAPIS_BLOCK"; break;
			case SANDSTONE: ger = "Sandstein"; eng = "SANDSTONE"; break;
			case CHISELED_SANDSTONE: ger = "Gemeißelter Sandstein"; eng = "CHISELED_SANDSTONE"; break;
			case CUT_SANDSTONE: ger = "Geschnittener Sandstein"; eng = "CUT_SANDSTONE"; break;
			case COBWEB: ger = "Spinnennetz"; eng = "COBWEB"; break;
			case SHORT_GRASS: ger = "Gras"; eng = "GRASS"; break;
			case FERN: ger = "Farn"; eng = "FERN"; break;
			case AZALEA: ger = "Azalee"; eng = "AZALEA"; break;
			case FLOWERING_AZALEA: ger = "Blühende Azalee"; eng = "FLOWERING_AZALEA"; break;
			case DEAD_BUSH: ger = "Toter Busch"; eng = "DEAD_BUSH"; break;
			case SEAGRASS: ger = "Seegras"; eng = "SEAGRASS"; break;
			case SEA_PICKLE: ger = "Seegurke"; eng = "SEA_PICKLE"; break;
			case WHITE_WOOL: ger = "Weiße Wolle"; eng = "WHITE_WOOL"; break;
			case ORANGE_WOOL: ger = "Orange Wolle"; eng = "ORANGE_WOOL"; break;
			case MAGENTA_WOOL: ger = "Magenta Wolle"; eng = "MAGENTA_WOOL"; break;
			case LIGHT_BLUE_WOOL: ger = "Hellblaue Wolle"; eng = "LIGHT_BLUE_WOOL"; break;
			case YELLOW_WOOL: ger = "Gelbe Wolle"; eng = "YELLOW_WOOL"; break;
			case LIME_WOOL: ger = "Hellgrüne Wolle"; eng = "LIME_WOOL"; break;
			case PINK_WOOL: ger = "Rosa Wolle"; eng = "PINK_WOOL"; break;
			case GRAY_WOOL: ger = "Graue Wolle"; eng = "GRAY_WOOL"; break;
			case LIGHT_GRAY_WOOL: ger = "Hellgraue Wolle"; eng = "LIGHT_GRAY_WOOL"; break;
			case CYAN_WOOL: ger = "Türkise Wolle"; eng = "CYAN_WOOL"; break;
			case PURPLE_WOOL: ger = "Violette Wolle"; eng = "PURPLE_WOOL"; break;
			case BLUE_WOOL: ger = "Blaue Wolle"; eng = "BLUE_WOOL"; break;
			case BROWN_WOOL: ger = "Braune Wolle"; eng = "BROWN_WOOL"; break;
			case GREEN_WOOL: ger = "Grüne Wolle"; eng = "GREEN_WOOL"; break;
			case RED_WOOL: ger = "Rote Wolle"; eng = "RED_WOOL"; break;
			case BLACK_WOOL: ger = "Schwarze Wolle"; eng = "BLACK_WOOL"; break;
			case DANDELION: ger = "Löwenzahn"; eng = "DANDELION"; break;
			case POPPY: ger = "Mohn"; eng = "POPPY"; break;
			case BLUE_ORCHID: ger = "Baue Orchidee"; eng = "BLUE_ORCHID"; break;
			case ALLIUM: ger = "Zierlauch"; eng = "ALLIUM"; break;
			case AZURE_BLUET: ger = "Porzellansternchen"; eng = "AZURE_BLUET"; break;
			case RED_TULIP: ger = "Rote Tulpe"; eng = "RED_TULIP"; break;
			case ORANGE_TULIP: ger = "Orange Tulpe"; eng = "ORANGE_TULIP"; break;
			case WHITE_TULIP: ger = "Weiße Tulpe"; eng = "WHITE_TULIP"; break;
			case PINK_TULIP: ger = "Rosa Tulpe"; eng = "PINK_TULIP"; break;
			case OXEYE_DAISY: ger = "Margerite"; eng = "OXEYE_DAISY"; break;
			case CORNFLOWER: ger = "Kornblume"; eng = "CORNFLOWER"; break;
			case LILY_OF_THE_VALLEY: ger = "Maiglöckchen"; eng = "LILY_OF_THE_VALLEY"; break;
			case WITHER_ROSE: ger = "Wither-Rose"; eng = "WITHER_ROSE"; break;
			case SPORE_BLOSSOM: ger = "Sporenblüte"; eng = "SPORE_BLOSSOM"; break;
			case BROWN_MUSHROOM: ger = "Brauner Pilz"; eng = "BROWN_MUSHROOM"; break;
			case RED_MUSHROOM: ger = "Roter Pilz"; eng = "RED_MUSHROOM"; break;
			case CRIMSON_FUNGUS: ger = "Karmesinpilz"; eng = "CRIMSON_FUNGUS"; break;
			case WARPED_FUNGUS: ger = "Wirrpilz"; eng = "WARPED_FUNGUS"; break;
			case CRIMSON_ROOTS: ger = "Karmesinwurzeln"; eng = "CRIMSON_ROOTS"; break;
			case WARPED_ROOTS: ger = "Wirrwurzeln"; eng = "WARPED_ROOTS"; break;
			case NETHER_SPROUTS: ger = "Nethersprossen"; eng = "NETHER_SPROUTS"; break;
			case WEEPING_VINES: ger = "Trauerranken"; eng = "WEEPING_VINES"; break;
			case TWISTING_VINES: ger = "Zwirbelranken"; eng = "TWISTING_VINES"; break;
			case SUGAR_CANE: ger = "Zuckerrohr"; eng = "SUGAR_CANE"; break;
			case KELP: ger = "Seetang"; eng = "KELP"; break;
			case MOSS_CARPET: ger = "Moosteppich"; eng = "MOSS_CARPET"; break;
			case MOSS_BLOCK: ger = "Moosblock"; eng = "MOSS_BLOCK"; break;
			case HANGING_ROOTS: ger = "Hängende Wurzeln"; eng = "HANGING_ROOTS"; break;
			case BIG_DRIPLEAF: ger = "Großes Tropfblatt"; eng = "BIG_DRIPLEAF"; break;
			case SMALL_DRIPLEAF: ger = "Kleines Tropfblatt"; eng = "SMALL_DRIPLEAF"; break;
			case BAMBOO: ger = "Bambus"; eng = "BAMBOO"; break;
			case OAK_SLAB: ger = "Eichenholzstufe"; eng = "OAK_SLAB"; break;
			case SPRUCE_SLAB: ger = "Fichtenholzstufe"; eng = "SPRUCE_SLAB"; break;
			case BIRCH_SLAB: ger = "Birkenholzstufe"; eng = "BIRCH_SLAB"; break;
			case JUNGLE_SLAB: ger = "Tropenholzstufe"; eng = "JUNGLE_SLAB"; break;
			case ACACIA_SLAB: ger = "Akazienholzstufe"; eng = "ACACIA_SLAB"; break;
			case DARK_OAK_SLAB: ger = "Schwarzeichenholzstufe"; eng = "DARK_OAK_SLAB"; break;
			case MANGROVE_SLAB: ger = "Mangrovenholzstufe"; eng = "MANGROVE_SLAB"; break;
			case CRIMSON_SLAB: ger = "Karmesinstufe"; eng = "CRIMSON_SLAB"; break;
			case WARPED_SLAB: ger = "Wirrstufe"; eng = "WARPED_SLAB"; break;
			case STONE_SLAB: ger = "Steinstufe"; eng = "STONE_SLAB"; break;
			case SMOOTH_STONE_SLAB: ger = "Glatte Steinstufe"; eng = "SMOOTH_STONE_SLAB"; break;
			case SANDSTONE_SLAB: ger = "Sandsteinstufe"; eng = "SANDSTONE_SLAB"; break;
			case CUT_SANDSTONE_SLAB: ger = "Geschnittene Sandsteinstufe"; eng = "CUT_SANDSTONE_SLAB"; break;
			case PETRIFIED_OAK_SLAB: ger = "Versteinerte Eichenholzstufe"; eng = "PETRIFIED_OAK_SLAB"; break;
			case COBBLESTONE_SLAB: ger = "Bruchsteinstufe"; eng = "COBBLESTONE_SLAB"; break;
			case BRICK_SLAB: ger = "Ziegelstufe"; eng = "BRICK_SLAB"; break;
			case STONE_BRICK_SLAB: ger = "Steinziegelstufe"; eng = "STONE_BRICK_SLAB"; break;
			case MUD_BRICK_SLAB: ger = "Schlammziegelstufe"; eng = "MUD_BRICK_SLAB"; break;
			case NETHER_BRICK_SLAB: ger = "Netherziegelstufe"; eng = "NETHER_BRICK_SLAB"; break;
			case QUARTZ_SLAB: ger = "Quarzstufe"; eng = "QUARTZ_SLAB"; break;
			case RED_SANDSTONE_SLAB: ger = "Rote Sandsteinstufe"; eng = "RED_SANDSTONE_SLAB"; break;
			case CUT_RED_SANDSTONE_SLAB: ger = "Geschnittene rote Sandsteinstufe"; eng = "CUT_RED_SANDSTONE_SLAB"; break;
			case PURPUR_SLAB: ger = "Purpurstufe"; eng = "PURPUR_SLAB"; break;
			case PRISMARINE_SLAB: ger = "Prismarinstufe"; eng = "PRISMARINE_SLAB"; break;
			case PRISMARINE_BRICK_SLAB: ger = "Prismarinziegelstufe"; eng = "PRISMARINE_BRICK_SLAB"; break;
			case DARK_PRISMARINE_SLAB: ger = "Dunkle Prismarinstufe"; eng = "DARK_PRISMARINE_SLAB"; break;
			case SMOOTH_QUARTZ: ger = "Glatter Quarzblock"; eng = "SMOOTH_QUARTZ"; break;
			case SMOOTH_RED_SANDSTONE: ger = "Glatter roter Sandstein"; eng = "SMOOTH_RED_SANDSTONE"; break;
			case SMOOTH_SANDSTONE: ger = "Glatter Sandstein"; eng = "SMOOTH_SANDSTONE"; break;
			case SMOOTH_STONE: ger = "Glatter Stein"; eng = "SMOOTH_STONE"; break;
			case BRICKS: ger = "Ziegelsteine"; eng = "BRICKS"; break;
			case BOOKSHELF: ger = "Bücherregal"; eng = "BOOKSHELF"; break;
			case MOSSY_COBBLESTONE: ger = "Bemooster Bruchstein"; eng = "MOSSY_COBBLESTONE"; break;
			case OBSIDIAN: ger = "Obsidian"; eng = "OBSIDIAN"; break;
			case TORCH: ger = "Fackel"; eng = "TORCH"; break;
			case END_ROD: ger = "Endstab"; eng = "END_ROD"; break;
			case CHORUS_PLANT: ger = "Choruspflanze"; eng = "CHORUS_PLANT"; break;
			case CHORUS_FLOWER: ger = "Chorusblüte"; eng = "CHORUS_FLOWER"; break;
			case PURPUR_BLOCK: ger = "Purpurblock"; eng = "PURPUR_BLOCK"; break;
			case PURPUR_PILLAR: ger = "Purpursäule"; eng = "PURPUR_PILLAR"; break;
			case PURPUR_STAIRS: ger = "Purpurtreppe"; eng = "PURPUR_STAIRS"; break;
			case SPAWNER: ger = "Spawner"; eng = "SPAWNER"; break;
			case CHEST: ger = "Truhe"; eng = "CHEST"; break;
			case CRAFTING_TABLE: ger = "Werkbank"; eng = "CRAFTING_TABLE"; break;
			case FARMLAND: ger = "Ackerboden"; eng = "FARMLAND"; break;
			case FURNACE: ger = "Ofen"; eng = "FURNACE"; break;
			case LADDER: ger = "Leiter"; eng = "LADDER"; break;
			case COBBLESTONE_STAIRS: ger = "Bruchsteinstufe"; eng = "COBBLESTONE_STAIRS"; break;
			case SNOW: ger = "Schnee"; eng = "SNOW"; break;
			case ICE: ger = "Eis"; eng = "ICE"; break;
			case SNOW_BLOCK: ger = "Schneeblock"; eng = "SNOW_BLOCK"; break;
			case CACTUS: ger = "Kaktus"; eng = "CACTUS"; break;
			case CLAY: ger = "Ton"; eng = "CLAY"; break;
			case JUKEBOX: ger = "Plattenspieler"; eng = "JUKEBOX"; break;
			case OAK_FENCE: ger = "Eichenholzzaun"; eng = "OAK_FENCE"; break;
			case SPRUCE_FENCE: ger = "Fichtenholzzaun"; eng = "SPRUCE_FENCE"; break;
			case BIRCH_FENCE: ger = "Birkenholzzaun"; eng = "BIRCH_FENCE"; break;
			case JUNGLE_FENCE: ger = "Tropenholzzaun"; eng = "JUNGLE_FENCE"; break;
			case ACACIA_FENCE: ger = "Akazienholzzaun"; eng = "ACACIA_FENCE"; break;
			case DARK_OAK_FENCE: ger = "Schwarzeichenholzzaun"; eng = "DARK_OAK_FENCE"; break;
			case MANGROVE_FENCE: ger = "Mangrovenholzzaun"; eng = "MANGROVE_FENCE"; break;
			case CRIMSON_FENCE: ger = "Karmesinzaun"; eng = "CRIMSON_FENCE"; break;
			case WARPED_FENCE: ger = "Wirrzaun"; eng = "WARPED_FENCE"; break;
			case PUMPKIN: ger = "Kürbis"; eng = "PUMPKIN"; break;
			case CARVED_PUMPKIN: ger = "Geschnitzter Kürbis"; eng = "CARVED_PUMPKIN"; break;
			case JACK_O_LANTERN: ger = "Kürbislaterne"; eng = "JACK_O_LANTERN"; break;
			case NETHERRACK: ger = "Netherrack"; eng = "NETHERRACK"; break;
			case SOUL_SAND: ger = "Seelensand"; eng = "SOUL_SAND"; break;
			case SOUL_SOIL: ger = "Seelenerde"; eng = "SOUL_SOIL"; break;
			case BASALT: ger = "Basalt"; eng = "BASALT"; break;
			case POLISHED_BASALT: ger = "Polierter Basalt"; eng = "POLISHED_BASALT"; break;
			case SMOOTH_BASALT: ger = "Glatter Basalt"; eng = "SMOOTH_BASALT"; break;
			case SOUL_TORCH: ger = "Seelenfackel"; eng = "SOUL_TORCH"; break;
			case GLOWSTONE: ger = "Leuchtstein"; eng = "GLOWSTONE"; break;
			case INFESTED_STONE: ger = "Befallener Stein"; eng = "INFESTED_STONE"; break;
			case INFESTED_COBBLESTONE: ger = "Befallener Bruchstein"; eng = "INFESTED_COBBLESTONE"; break;
			case INFESTED_STONE_BRICKS: ger = "Befallene Steinziegel"; eng = "INFESTED_STONE_BRICKS"; break;
			case INFESTED_MOSSY_STONE_BRICKS: ger = "Befallene bemooste Steinziegel"; eng = "INFESTED_MOSSY_STONE_BRICKS"; break;
			case INFESTED_CRACKED_STONE_BRICKS: ger = "Befallene rissige Steinziegel"; eng = "INFESTED_CRACKED_STONE_BRICKS"; break;
			case INFESTED_CHISELED_STONE_BRICKS: ger = "Befallene gemeißelte Steinziegel"; eng = "INFESTED_CHISELED_STONE_BRICKS"; break;
			case INFESTED_DEEPSLATE: ger = "Befallener Tiefenschiefer"; eng = "INFESTED_DEEPSLATE"; break;
			case STONE_BRICKS: ger = "Steinziegel"; eng = "STONE_BRICKS"; break;
			case MOSSY_STONE_BRICKS: ger = "Bemooste Steinziegel"; eng = "MOSSY_STONE_BRICKS"; break;
			case CRACKED_STONE_BRICKS: ger = "Rissige Steinziegel"; eng = "CRACKED_STONE_BRICKS"; break;
			case CHISELED_STONE_BRICKS: ger = "Gemeißelte Steinziegel"; eng = "CHISELED_STONE_BRICKS"; break;
			case PACKED_MUD: ger = "Fester Schlamm"; eng = "PACKED_MUD"; break;
			case MUD_BRICKS: ger = "Schlammziegel"; eng = "MUD_BRICKS"; break;
			case DEEPSLATE_BRICKS: ger = "Tiefenschieferziegel"; eng = "DEEPSLATE_BRICKS"; break;
			case CRACKED_DEEPSLATE_BRICKS: ger = "Rissige Tiefenschieferziegel"; eng = "CRACKED_DEEPSLATE_BRICKS"; break;
			case DEEPSLATE_TILES: ger = "Tiefenschieferfliesen"; eng = "DEEPSLATE_TILES"; break;
			case CRACKED_DEEPSLATE_TILES: ger = "Rissige Tiefenschieferfliesen"; eng = "CRACKED_DEEPSLATE_TILES"; break;
			case CHISELED_DEEPSLATE: ger = "Gemeißelter Tiefenschiefer"; eng = "CHISELED_DEEPSLATE"; break;
			case REINFORCED_DEEPSLATE: ger = "Verstärkter Tiefenschiefer"; eng = "REINFORCED_DEEPSLATE"; break;
			case BROWN_MUSHROOM_BLOCK: ger = "Brauner Pilzblock"; eng = "BROWN_MUSHROOM_BLOCK"; break;
			case RED_MUSHROOM_BLOCK: ger = "Roter Pilzblock"; eng = "RED_MUSHROOM_BLOCK"; break;
			case MUSHROOM_STEM: ger = "Pilzstiel"; eng = "MUSHROOM_STEM"; break;
			case IRON_BARS: ger = "Eisengitter"; eng = "IRON_BARS"; break;
			case CHAIN: ger = "Kette"; eng = "CHAIN"; break;
			case GLASS_PANE: ger = "Glasscheibe"; eng = "GLASS_PANE"; break;
			case MELON: ger = "Melone"; eng = "MELON"; break;
			case VINE: ger = "Ranken"; eng = "VINE"; break;
			case GLOW_LICHEN: ger = "Leuchtflechte"; eng = "GLOW_LICHEN"; break;
			case BRICK_STAIRS: ger = "Ziegeltreppe"; eng = "BRICK_STAIRS"; break;
			case STONE_BRICK_STAIRS: ger = "Steinziegeltreppe"; eng = "STONE_BRICK_STAIRS"; break;
			case MUD_BRICK_STAIRS: ger = "Schlammziegeltreppe"; eng = "MUD_BRICK_STAIRS"; break;
			case MYCELIUM: ger = "Myzel"; eng = "MYCELIUM"; break;
			case LILY_PAD: ger = "Seerosenblatt"; eng = "LILY_PAD"; break;
			case NETHER_BRICKS: ger = "Netherziegel"; eng = "NETHER_BRICKS"; break;
			case CRACKED_NETHER_BRICKS: ger = "Rissige Netherziegel"; eng = "CRACKED_NETHER_BRICKS"; break;
			case CHISELED_NETHER_BRICKS: ger = "Gemeißelte Netherziegel"; eng = "CHISELED_NETHER_BRICKS"; break;
			case NETHER_BRICK_FENCE: ger = "Netherziegelzaun"; eng = "NETHER_BRICK_FENCE"; break;
			case NETHER_BRICK_STAIRS: ger = "Netherziegeltreppe"; eng = "NETHER_BRICK_STAIRS"; break;
			case SCULK: ger = "Sculk"; eng = "SCULK"; break;
			case SCULK_VEIN: ger = "Sculk-Ader"; eng = "SCULK_VEIN"; break;
			case SCULK_CATALYST: ger = "Sculk-Katalysator"; eng = "SCULK_CATALYST"; break;
			case SCULK_SHRIEKER: ger = "Sculk-Kreischer"; eng = "SCULK_SHRIEKER"; break;
			case ENCHANTING_TABLE: ger = "Zaubertisch"; eng = "ENCHANTING_TABLE"; break;
			case END_PORTAL_FRAME: ger = "Endportalrahmen"; eng = "END_PORTAL_FRAME"; break;
			case END_STONE: ger = "Endstein"; eng = "END_STONE"; break;
			case END_STONE_BRICKS: ger = "Endsteinziegel"; eng = "END_STONE_BRICKS"; break;
			case DRAGON_EGG: ger = "Drachenei"; eng = "DRAGON_EGG"; break;
			case SANDSTONE_STAIRS: ger = "Sandsteintreppe"; eng = "SANDSTONE_STAIRS"; break;
			case ENDER_CHEST: ger = "Endertruhe"; eng = "ENDER_CHEST"; break;
			case EMERALD_BLOCK: ger = "Smaragdblock"; eng = "EMERALD_BLOCK"; break;
			case OAK_STAIRS: ger = "Eichenholztreppe"; eng = "OAK_STAIRS"; break;
			case SPRUCE_STAIRS: ger = "Fichtenholztreppe"; eng = "SPRUCE_STAIRS"; break;
			case BIRCH_STAIRS: ger = "Birkenholztreppe"; eng = "BIRCH_STAIRS"; break;
			case JUNGLE_STAIRS: ger = "Tropenholztreppe"; eng = "JUNGLE_STAIRS"; break;
			case ACACIA_STAIRS: ger = "Akazienholztreppe"; eng = "ACACIA_STAIRS"; break;
			case DARK_OAK_STAIRS: ger = "Schwarzeichenholztreppe"; eng = "DARK_OAK_STAIRS"; break;
			case MANGROVE_STAIRS: ger = "Mangrovenholztreppe"; eng = "MANGROVE_STAIRS"; break;
			case CRIMSON_STAIRS: ger = "Karmesintreppe"; eng = "CRIMSON_STAIRS"; break;
			case WARPED_STAIRS: ger = "Wirrtreppe"; eng = "WARPED_STAIRS"; break;
			case COMMAND_BLOCK: ger = "Befehlsblock"; eng = "COMMAND_BLOCK"; break;
			case BEACON: ger = "Leuchtfeuer"; eng = "BEACON"; break;
			case COBBLESTONE_WALL: ger = "Bruchsteinmauer"; eng = "COBBLESTONE_WALL"; break;
			case MOSSY_COBBLESTONE_WALL: ger = "Bemooste Bruchsteinmauer"; eng = "MOSSY_COBBLESTONE_WALL"; break;
			case BRICK_WALL: ger = "Ziegelsteinmauer"; eng = "BRICK_WALL"; break;
			case PRISMARINE_WALL: ger = "Prismarinmauer"; eng = "PRISMARINE_WALL"; break;
			case RED_SANDSTONE_WALL: ger = "Rote Sandsteimauer"; eng = "RED_SANDSTONE_WALL"; break;
			case MOSSY_STONE_BRICK_WALL: ger = "Bemooste Steinziegelmauer"; eng = "MOSSY_STONE_BRICK_WALL"; break;
			case GRANITE_WALL: ger = "Granitmauer"; eng = "GRANITE_WALL"; break;
			case STONE_BRICK_WALL: ger = "Steinziegelmauer"; eng = "STONE_BRICK_WALL"; break;
			case MUD_BRICK_WALL: ger = "Schlammziegelmauer"; eng = "MUD_BRICK_WALL"; break;
			case NETHER_BRICK_WALL: ger = "Netherziegelmauer"; eng = "NETHER_BRICK_WALL"; break;
			case ANDESITE_WALL: ger = "Andesitmauer"; eng = "ANDESITE_WALL"; break;
			case RED_NETHER_BRICK_WALL: ger = "Rote Netherziegelmauer"; eng = "RED_NETHER_BRICK_WALL"; break;
			case SANDSTONE_WALL: ger = "Sandsteinmauer"; eng = "SANDSTONE_WALL"; break;
			case END_STONE_BRICK_WALL: ger = "Endsteinziegelmauer"; eng = "END_STONE_BRICK_WALL"; break;
			case DIORITE_WALL: ger = "Dioritmauer"; eng = "DIORITE_WALL"; break;
			case BLACKSTONE_WALL: ger = "Schwarzsteinmauer"; eng = "BLACKSTONE_WALL"; break;
			case POLISHED_BLACKSTONE_WALL: ger = "Polierte Schwarzsteinmauer"; eng = "POLISHED_BLACKSTONE_WALL"; break;
			case POLISHED_BLACKSTONE_BRICK_WALL: ger = "Polierte Schwarzsteinziegelmauer"; eng = "POLISHED_BLACKSTONE_BRICK_WALL"; break;
			case COBBLED_DEEPSLATE_WALL: ger = "Bruchtiefenschiefermauer"; eng = "COBBLED_DEEPSLATE_WALL"; break;
			case POLISHED_DEEPSLATE_WALL: ger = "Polierte Tiefenschiefermauer"; eng = "POLISHED_DEEPSLATE_WALL"; break;
			case DEEPSLATE_BRICK_WALL: ger = "Tiefenschieferziegelmauer"; eng = "DEEPSLATE_BRICK_WALL"; break;
			case DEEPSLATE_TILE_WALL: ger = "Tiefenschieferfliesenmauer"; eng = "DEEPSLATE_TILE_WALL"; break;
			case ANVIL: ger = "Amboss"; eng = "ANVIL"; break;
			case CHIPPED_ANVIL: ger = "Angeschlagener Amboss"; eng = "CHIPPED_ANVIL"; break;
			case DAMAGED_ANVIL: ger = "Beschädigter Amboss"; eng = "DAMAGED_ANVIL"; break;
			case CHISELED_QUARTZ_BLOCK: ger = "Gemeißelter Quarzblock"; eng = "CHISELED_QUARTZ_BLOCK"; break;
			case QUARTZ_BLOCK: ger = "Quarzblock"; eng = "QUARTZ_BLOCK"; break;
			case QUARTZ_BRICKS: ger = "Quarzziegel"; eng = "QUARTZ_BRICKS"; break;
			case QUARTZ_PILLAR: ger = "Quarzsäule"; eng = "QUARTZ_PILLAR"; break;
			case QUARTZ_STAIRS: ger = "Quarztreppe"; eng = "QUARTZ_STAIRS"; break;
			case WHITE_TERRACOTTA: ger = "Weiße Keramik"; eng = "WHITE_TERRACOTTA"; break;
			case ORANGE_TERRACOTTA: ger = "Orange Keramik"; eng = "ORANGE_TERRACOTTA"; break;
			case MAGENTA_TERRACOTTA: ger = "Magenta Keramik"; eng = "MAGENTA_TERRACOTTA"; break;
			case LIGHT_BLUE_TERRACOTTA: ger = "Hellblaue Keramik"; eng = "LIGHT_BLUE_TERRACOTTA"; break;
			case YELLOW_TERRACOTTA: ger = "Gelbe Keramik"; eng = "YELLOW_TERRACOTTA"; break;
			case LIME_TERRACOTTA: ger = "Hellgrüne Keramik"; eng = "LIME_TERRACOTTA"; break;
			case PINK_TERRACOTTA: ger = "Rosa Keramik"; eng = "PINK_TERRACOTTA"; break;
			case GRAY_TERRACOTTA: ger = "Graue Keramik"; eng = "GRAY_TERRACOTTA"; break;
			case LIGHT_GRAY_TERRACOTTA: ger = "Hellgraue Keramik"; eng = "LIGHT_GRAY_TERRACOTTA"; break;
			case CYAN_TERRACOTTA: ger = "Türkise Keramik"; eng = "CYAN_TERRACOTTA"; break;
			case PURPLE_TERRACOTTA: ger = "Violette Keramik"; eng = "PURPLE_TERRACOTTA"; break;
			case BLUE_TERRACOTTA: ger = "Blaue Keramik"; eng = "BLUE_TERRACOTTA"; break;
			case BROWN_TERRACOTTA: ger = "Braune Keramik"; eng = "BROWN_TERRACOTTA"; break;
			case GREEN_TERRACOTTA: ger = "Grüne Keramik"; eng = "GREEN_TERRACOTTA"; break;
			case RED_TERRACOTTA: ger = "Rote Keramik"; eng = "RED_TERRACOTTA"; break;
			case BLACK_TERRACOTTA: ger = "Schwarze Keramik"; eng = "case TERRACOTTA"; break;
			case BARRIER: ger = "Barriere"; eng = "BARRIER"; break;
			case LIGHT: ger = "Lichtblock"; eng = "LIGHT"; break;
			case HAY_BLOCK: ger = "Strohballen"; eng = "HAY_BLOCK"; break;
			case WHITE_CARPET: ger = "Weißer Teppich"; eng = "WHITE_CARPET"; break;
			case ORANGE_CARPET: ger = "Oranger Teppich"; eng = "ORANGE_CARPET"; break;
			case MAGENTA_CARPET: ger = "Magenta Teppich"; eng = "MAGENTA_CARPET"; break;
			case LIGHT_BLUE_CARPET: ger = "Hellblauer Teppich"; eng = "LIGHT_BLUE_CARPET"; break;
			case YELLOW_CARPET: ger = "Gelber Teppich"; eng = "YELLOW_CARPET"; break;
			case LIME_CARPET: ger = "Hellgrüner Teppich"; eng = "LIME_CARPET"; break;
			case PINK_CARPET: ger = "Rosa Teppich"; eng = "PINK_CARPET"; break;
			case GRAY_CARPET: ger = "Grauer Teppich"; eng = "GRAY_CARPET"; break;
			case LIGHT_GRAY_CARPET: ger = "Hellgrauer Teppich"; eng = "LIGHT_GRAY_CARPET"; break;
			case CYAN_CARPET: ger = "Türkiser Teppich"; eng = "CYAN_CARPET"; break;
			case PURPLE_CARPET: ger = "Violetter Teppich"; eng = "PURPLE_CARPET"; break;
			case BLUE_CARPET: ger = "Blauer Teppich"; eng = "BLUE_CARPET"; break;
			case BROWN_CARPET: ger = "Brauner Teppich"; eng = "BROWN_CARPET"; break;
			case GREEN_CARPET: ger = "Grüner Teppich"; eng = "GREEN_CARPET"; break;
			case RED_CARPET: ger = "Rpter Teppich"; eng = "RED_CARPET"; break;
			case BLACK_CARPET: ger = "Schwarzer Teppich"; eng = "BLACK_CARPET"; break;
			case TERRACOTTA: ger = "Keramik"; eng = "TERRACOTTA"; break;
			case PACKED_ICE: ger = "Packeis"; eng = "PACKED_ICE"; break;
			case DIRT_PATH: ger = "Trampelpfad"; eng = "DIRT_PATH"; break;
			case SUNFLOWER: ger = "Sonnenblume"; eng = "SUNFLOWER"; break;
			case LILAC: ger = "Flieder"; eng = "LILAC"; break;
			case ROSE_BUSH: ger = "Rosenstrauch"; eng = "ROSE_BUSH"; break;
			case PEONY: ger = "Pfingstrose"; eng = "PEONY"; break;
			case TALL_GRASS: ger = "Hohes Gras"; eng = "TALL_GRASS"; break;
			case LARGE_FERN: ger = "Großer Farn"; eng = "LARGE_FERN"; break;
			case WHITE_STAINED_GLASS: ger = "Weißes Glas"; eng = "WHITE_STAINED_GLASS"; break;
			case ORANGE_STAINED_GLASS: ger = "Oranges Glas"; eng = "ORANGE_STAINED_GLASS"; break;
			case MAGENTA_STAINED_GLASS: ger = "Magenta Glas"; eng = "MAGENTA_STAINED_GLASS"; break;
			case LIGHT_BLUE_STAINED_GLASS: ger = "Hellblaues Glas"; eng = "LIGHT_BLUE_STAINED_GLASS"; break;
			case YELLOW_STAINED_GLASS: ger = "Gelbes Glas"; eng = "YELLOW_STAINED_GLASS"; break;
			case LIME_STAINED_GLASS: ger = "Hellgrünes Glas"; eng = "LIME_STAINED_GLASS"; break;
			case PINK_STAINED_GLASS: ger = "Rosa Glas"; eng = "PINK_STAINED_GLASS"; break;
			case GRAY_STAINED_GLASS: ger = "Graues Glas"; eng = "GRAY_STAINED_GLASS"; break;
			case LIGHT_GRAY_STAINED_GLASS: ger = "Hellgraues Glas"; eng = "LIGHT_GRAY_STAINED_GLASS"; break;
			case CYAN_STAINED_GLASS: ger = "Türkises Glas"; eng = "CYAN_STAINED_GLASS"; break;
			case PURPLE_STAINED_GLASS: ger = "Violettes Glas"; eng = "PURPLE_STAINED_GLASS"; break;
			case BLUE_STAINED_GLASS: ger = "Blaues Glas"; eng = "BLUE_STAINED_GLASS"; break;
			case BROWN_STAINED_GLASS: ger = "Braunes Glas"; eng = "BROWN_STAINED_GLASS"; break;
			case GREEN_STAINED_GLASS: ger = "Grünes Glas"; eng = "GREEN_STAINED_GLASS"; break;
			case RED_STAINED_GLASS: ger = "Rotes Glas"; eng = "RED_STAINED_GLASS"; break;
			case BLACK_STAINED_GLASS: ger = "Schwarzes Glas"; eng = "BLACK_STAINED_GLASS"; break;
			case WHITE_STAINED_GLASS_PANE: ger = "Weiße Glasscheibe"; eng = "WHITE_STAINED_GLASS_PANE"; break;
			case ORANGE_STAINED_GLASS_PANE: ger = "Orange Glasscheibe"; eng = "ORANGE_STAINED_GLASS_PANE"; break;
			case MAGENTA_STAINED_GLASS_PANE: ger = "Magenta Glasscheibe"; eng = "MAGENTA_STAINED_GLASS_PANE"; break;
			case LIGHT_BLUE_STAINED_GLASS_PANE: ger = "Hellblaue Glasscheibe"; eng = "LIGHT_BLUE_STAINED_GLASS_PANE"; break;
			case YELLOW_STAINED_GLASS_PANE: ger = "Gelbe Glasscheibe"; eng = "YELLOW_STAINED_GLASS_PANE"; break;
			case LIME_STAINED_GLASS_PANE: ger = "Hellgrüne Glasscheibe"; eng = "LIME_STAINED_GLASS_PANE"; break;
			case PINK_STAINED_GLASS_PANE: ger = "Rosa Glasscheibe"; eng = "PINK_STAINED_GLASS_PANE"; break;
			case GRAY_STAINED_GLASS_PANE: ger = "Graue Glasscheibe"; eng = "GRAY_STAINED_GLASS_PANE"; break;
			case LIGHT_GRAY_STAINED_GLASS_PANE: ger = "Hellgraue Glasscheibe"; eng = "LIGHT_GRAY_STAINED_GLASS_PANE"; break;
			case CYAN_STAINED_GLASS_PANE: ger = "Türkise Glasscheibe"; eng = "CYAN_STAINED_GLASS_PANE"; break;
			case PURPLE_STAINED_GLASS_PANE: ger = "Violette Glasscheibe"; eng = "PURPLE_STAINED_GLASS_PANE"; break;
			case BLUE_STAINED_GLASS_PANE: ger = "Blaue Glasscheibe"; eng = "BLUE_STAINED_GLASS_PANE"; break;
			case BROWN_STAINED_GLASS_PANE: ger = "Braune Glasscheibe"; eng = "BROWN_STAINED_GLASS_PANE"; break;
			case GREEN_STAINED_GLASS_PANE: ger = "Grüne Glasscheibe"; eng = "GREEN_STAINED_GLASS_PANE"; break;
			case RED_STAINED_GLASS_PANE: ger = "Rote Glasscheibe"; eng = "RED_STAINED_GLASS_PANE"; break;
			case BLACK_STAINED_GLASS_PANE: ger = "Schwarze Glasscheibe"; eng = "BLACK_STAINED_GLASS_PANE"; break;
			case PRISMARINE: ger = "Prismarin"; eng = "PRISMARINE"; break;
			case PRISMARINE_BRICKS: ger = "Prismarinziegel"; eng = "PRISMARINE_BRICKS"; break;
			case DARK_PRISMARINE: ger = "Dunkler Prismarin"; eng = "DARK_PRISMARINE"; break;
			case PRISMARINE_STAIRS: ger = "Prismarintreppe"; eng = "PRISMARINE_STAIRS"; break;
			case PRISMARINE_BRICK_STAIRS: ger = "Prismarinziegeltreppe"; eng = "PRISMARINE_BRICK_STAIRS"; break;
			case DARK_PRISMARINE_STAIRS: ger = "Dunkle Prismarinziegeltreppe"; eng = "DARK_PRISMARINE_STAIRS"; break;
			case SEA_LANTERN: ger = "Seelaterne"; eng = "SEA_LANTERN"; break;
			case RED_SANDSTONE: ger = "Roter Sandstein"; eng = "RED_SANDSTONE"; break;
			case CHISELED_RED_SANDSTONE: ger = "Gemeißelter roter Sandstein"; eng = "CHISELED_RED_SANDSTONE"; break;
			case CUT_RED_SANDSTONE: ger = "Geschnittener roter Sandstein"; eng = "CUT_RED_SANDSTONE"; break;
			case RED_SANDSTONE_STAIRS: ger = "Rote Sandsteintreppe"; eng = "RED_SANDSTONE_STAIRS"; break;
			case REPEATING_COMMAND_BLOCK: ger = "Wiederhol-Befehlsblock"; eng = "REPEATING_COMMAND_BLOCK"; break;
			case CHAIN_COMMAND_BLOCK: ger = "Ketten-Befehlsblock"; eng = "CHAIN_COMMAND_BLOCK"; break;
			case MAGMA_BLOCK: ger = "Magmsablock"; eng = "MAGMA_BLOCK"; break;
			case NETHER_WART_BLOCK: ger = "Netherwarzenblock"; eng = "NETHER_WART_BLOCK"; break;
			case WARPED_WART_BLOCK: ger = "Wirrwarzenblock"; eng = "WARPED_WART_BLOCK"; break;
			case RED_NETHER_BRICKS: ger = "Rote Netherziegel"; eng = "RED_NETHER_BRICKS"; break;
			case BONE_BLOCK: ger = "Knochenblock"; eng = "BONE_BLOCK"; break;
			case STRUCTURE_VOID: ger = "Konstruktionsleere"; eng = "STRUCTURE_VOID"; break;
			case SHULKER_BOX: ger = "Shulker-Kiste"; eng = "SHULKER_BOX"; break;
			case WHITE_SHULKER_BOX: ger = "Weiße Shulker-Kiste"; eng = "WHITE_SHULKER_BOX"; break;
			case ORANGE_SHULKER_BOX: ger = "Orange Shulker-Kiste"; eng = "ORANGE_SHULKER_BOX"; break;
			case MAGENTA_SHULKER_BOX: ger = "Magenta Shulker-Kiste"; eng = "MAGENTA_SHULKER_BOX"; break;
			case LIGHT_BLUE_SHULKER_BOX: ger = "Hellblaue Shulker-Kiste"; eng = "LIGHT_BLUE_SHULKER_BOX"; break;
			case YELLOW_SHULKER_BOX: ger = "Gelbe Shulker-Kiste"; eng = "YELLOW_SHULKER_BOX"; break;
			case LIME_SHULKER_BOX: ger = "Hellgrüne Shulker-Kiste"; eng = "LIME_SHULKER_BOX"; break;
			case PINK_SHULKER_BOX: ger = "Rosa Shulker-Kiste"; eng = "PINK_SHULKER_BOX"; break;
			case GRAY_SHULKER_BOX: ger = "Graue Shulker-Kiste"; eng = "GRAY_SHULKER_BOX"; break;
			case LIGHT_GRAY_SHULKER_BOX: ger = "Hellgraue Shulker-Kiste"; eng = "LIGHT_GRAY_SHULKER_BOX"; break;
			case CYAN_SHULKER_BOX: ger = "Türkise Shulker-Kiste"; eng = "CYAN_SHULKER_BOX"; break;
			case PURPLE_SHULKER_BOX: ger = "Violette Shulker-Kiste"; eng = "PURPLE_SHULKER_BOX"; break;
			case BLUE_SHULKER_BOX: ger = "Blaue Shulker-Kiste"; eng = "BLUE_SHULKER_BOX"; break;
			case BROWN_SHULKER_BOX: ger = "Braune Shulker-Kiste"; eng = "BROWN_SHULKER_BOX"; break;
			case GREEN_SHULKER_BOX: ger = "Grüne Shulker-Kiste"; eng = "GREEN_SHULKER_BOX"; break;
			case RED_SHULKER_BOX: ger = "Rote Shulker-Kiste"; eng = "RED_SHULKER_BOX"; break;
			case BLACK_SHULKER_BOX: ger = "Schwarze Shulker-Kiste"; eng = "BLACK_SHULKER_BOX"; break;
			case WHITE_GLAZED_TERRACOTTA: ger = "Weiße glasierte Keramik"; eng = "WHITE_GLAZED_TERRACOTTA"; break;
			case ORANGE_GLAZED_TERRACOTTA: ger = "Orange glasierte Keramik"; eng = "ORANGE_GLAZED_TERRACOTTA"; break;
			case MAGENTA_GLAZED_TERRACOTTA: ger = "Magenta glasierte Keramik"; eng = "MAGENTA_GLAZED_TERRACOTTA"; break;
			case LIGHT_BLUE_GLAZED_TERRACOTTA: ger = "Hellblaue glasierte Keramik"; eng = "LIGHT_BLUE_GLAZED_TERRACOTTA"; break;
			case YELLOW_GLAZED_TERRACOTTA: ger = "Gelbe glasierte Keramik"; eng = "YELLOW_GLAZED_TERRACOTTA"; break;
			case LIME_GLAZED_TERRACOTTA: ger = "Hellgrüne glasierte Keramik"; eng = "LIME_GLAZED_TERRACOTTA"; break;
			case PINK_GLAZED_TERRACOTTA: ger = "Rosa glasierte Keramik"; eng = "PINK_GLAZED_TERRACOTTA"; break;
			case GRAY_GLAZED_TERRACOTTA: ger = "Graue glasierte Keramik"; eng = "GRAY_GLAZED_TERRACOTTA"; break;
			case LIGHT_GRAY_GLAZED_TERRACOTTA: ger = "Hellgraue glasierte Keramik"; eng = "LIGHT_GRAY_GLAZED_TERRACOTTA"; break;
			case CYAN_GLAZED_TERRACOTTA: ger = "Türkise glasierte Keramik"; eng = "CYAN_GLAZED_TERRACOTTA"; break;
			case PURPLE_GLAZED_TERRACOTTA: ger = "Violette glasierte Keramik"; eng = "PURPLE_GLAZED_TERRACOTTA"; break;
			case BLUE_GLAZED_TERRACOTTA: ger = "Blaue glasierte Keramik"; eng = "BLUE_GLAZED_TERRACOTTA"; break;
			case BROWN_GLAZED_TERRACOTTA: ger = "Braune glasierte Keramik"; eng = "BROWN_GLAZED_TERRACOTTA"; break;
			case GREEN_GLAZED_TERRACOTTA: ger = "Grüne glasierte Keramik"; eng = "GREEN_GLAZED_TERRACOTTA"; break;
			case RED_GLAZED_TERRACOTTA: ger = "Rote glasierte Keramik"; eng = "RED_GLAZED_TERRACOTTA"; break;
			case BLACK_GLAZED_TERRACOTTA: ger = "Schwarze glasierte Keramik"; eng = "BLACK_GLAZED_TERRACOTTA"; break;
			case WHITE_CONCRETE: ger = "Weißer Beton"; eng = "WHITE_CONCRETE"; break;
			case ORANGE_CONCRETE: ger = "Oranger Beton"; eng = "ORANGE_CONCRETE"; break;
			case MAGENTA_CONCRETE: ger = "Magenta Beton"; eng = "MAGENTA_CONCRETE"; break;
			case LIGHT_BLUE_CONCRETE: ger = "Hellblauer Beton"; eng = "LIGHT_BLUE_CONCRETE"; break;
			case YELLOW_CONCRETE: ger = "Gelber Beton"; eng = "YELLOW_CONCRETE"; break;
			case LIME_CONCRETE: ger = "Hellgrüner Beton"; eng = "LIME_CONCRETE"; break;
			case PINK_CONCRETE: ger = "Rosa Beton"; eng = "PINK_CONCRETE"; break;
			case GRAY_CONCRETE: ger = "Grauer Beton"; eng = "GRAY_CONCRETE"; break;
			case LIGHT_GRAY_CONCRETE: ger = "Hellgrauer Beton"; eng = "LIGHT_GRAY_CONCRETE"; break;
			case CYAN_CONCRETE: ger = "Türkiser Beton"; eng = "CYAN_CONCRETE"; break;
			case PURPLE_CONCRETE: ger = "Violetter Beton"; eng = "PURPLE_CONCRETE"; break;
			case BLUE_CONCRETE: ger = "Blauer Beton"; eng = "BLUE_CONCRETE"; break;
			case BROWN_CONCRETE: ger = "Brauner Beton"; eng = "BROWN_CONCRETE"; break;
			case GREEN_CONCRETE: ger = "Grüner Beton"; eng = "GREEN_CONCRETE"; break;
			case RED_CONCRETE: ger = "Roter Beton"; eng = "RED_CONCRETE"; break;
			case BLACK_CONCRETE: ger = "Schwarzer Beton"; eng = "BLACK_CONCRETE"; break;
			case WHITE_CONCRETE_POWDER: ger = "Weißer Trockenbeton"; eng = "WHITE_CONCRETE_POWDER"; break;
			case ORANGE_CONCRETE_POWDER: ger = "Oranger Trockenbeton"; eng = "ORANGE_CONCRETE_POWDER"; break;
			case MAGENTA_CONCRETE_POWDER: ger = "Magenta Trockenbeton"; eng = "MAGENTA_CONCRETE_POWDER"; break;
			case LIGHT_BLUE_CONCRETE_POWDER: ger = "Hellblauer Trockenbeton"; eng = "LIGHT_BLUE_CONCRETE_POWDER"; break;
			case YELLOW_CONCRETE_POWDER: ger = "Gelber Trockenbeton"; eng = "YELLOW_CONCRETE_POWDER"; break;
			case LIME_CONCRETE_POWDER: ger = "Hellgrüner Trockenbeton"; eng = "LIME_CONCRETE_POWDER"; break;
			case PINK_CONCRETE_POWDER: ger = "Rosa Trockenbeton"; eng = "PINK_CONCRETE_POWDER"; break;
			case GRAY_CONCRETE_POWDER: ger = "Grauer Trockenbeton"; eng = "GRAY_CONCRETE_POWDER"; break;
			case LIGHT_GRAY_CONCRETE_POWDER: ger = "Hellgruer Trockenbeton"; eng = "LIGHT_GRAY_CONCRETE_POWDER"; break;
			case CYAN_CONCRETE_POWDER: ger = "Türkiser Trockenbeton"; eng = "CYAN_CONCRETE_POWDER"; break;
			case PURPLE_CONCRETE_POWDER: ger = "Violetter Trockenbeton"; eng = "PURPLE_CONCRETE_POWDER"; break;
			case BLUE_CONCRETE_POWDER: ger = "Blauer Trockenbeton"; eng = "BLUE_CONCRETE_POWDER"; break;
			case BROWN_CONCRETE_POWDER: ger = "Brauner Trockenbeton"; eng = "BROWN_CONCRETE_POWDER"; break;
			case GREEN_CONCRETE_POWDER: ger = "Grüner Trockenbeton"; eng = "GREEN_CONCRETE_POWDER"; break;
			case RED_CONCRETE_POWDER: ger = "Roter Trockenbeton"; eng = "RED_CONCRETE_POWDER"; break;
			case BLACK_CONCRETE_POWDER: ger = "Schwarzer Trockenbeton"; eng = "BLACK_CONCRETE_POWDER"; break;
			case TURTLE_EGG: ger = "Schildkrötenei"; eng = "TURTLE_EGG"; break;
			case DEAD_TUBE_CORAL_BLOCK: ger = "Abgestorbener Orgelkorallenblock"; eng = "DEAD_TUBE_CORAL_BLOCK"; break;
			case DEAD_BRAIN_CORAL_BLOCK: ger = "Abgestorbener Hirnkorallenblock"; eng = "DEAD_BRAIN_CORAL_BLOCK"; break;
			case DEAD_BUBBLE_CORAL_BLOCK: ger = "Abgestorbener Blasenkorallenblock"; eng = "DEAD_BUBBLE_CORAL_BLOCK"; break;
			case DEAD_FIRE_CORAL_BLOCK: ger = "Abgestorbener Feuerkorallenblock"; eng = "DEAD_FIRE_CORAL_BLOCK"; break;
			case DEAD_HORN_CORAL_BLOCK: ger = "Abgestorbener Geweihkorallenblock"; eng = "DEAD_HORN_CORAL_BLOCK"; break;
			case TUBE_CORAL_BLOCK: ger = "Orgelkorallenblock"; eng = "TUBE_CORAL_BLOCK"; break;
			case BRAIN_CORAL_BLOCK: ger = "Hirnkorallenblock"; eng = "BRAIN_CORAL_BLOCK"; break;
			case BUBBLE_CORAL_BLOCK: ger = "Blasenkorallenblock"; eng = "BUBBLE_CORAL_BLOCK"; break;
			case FIRE_CORAL_BLOCK: ger = "Feuerkorallenblock"; eng = "FIRE_CORAL_BLOCK"; break;
			case HORN_CORAL_BLOCK: ger = "Geweihkorallenblock"; eng = "HORN_CORAL_BLOCK"; break;
			case TUBE_CORAL: ger = "Orgelkoralle"; eng = "TUBE_CORAL"; break;
			case BRAIN_CORAL: ger = "Hirnkoralle"; eng = "BRAIN_CORAL"; break;
			case BUBBLE_CORAL: ger = "Blasenkoralle"; eng = "BUBBLE_CORAL"; break;
			case FIRE_CORAL: ger = "Feuerkoralle"; eng = "FIRE_CORAL"; break;
			case HORN_CORAL: ger = "Geweihkoralle"; eng = "HORN_CORAL"; break;
			case DEAD_BRAIN_CORAL: ger = "Abgestorbene Hirnkoralle"; eng = "DEAD_BRAIN_CORAL"; break;
			case DEAD_BUBBLE_CORAL: ger = "Abgestorbene Blasenkoralle"; eng = "DEAD_BUBBLE_CORAL"; break;
			case DEAD_FIRE_CORAL: ger = "Abgestorbene Feuerkoralle"; eng = "DEAD_FIRE_CORAL"; break;
			case DEAD_HORN_CORAL: ger = "Abgestorbene Geweihkoralle"; eng = "DEAD_HORN_CORAL"; break;
			case DEAD_TUBE_CORAL: ger = "Abgestorbene Orgelkoralle"; eng = "DEAD_TUBE_CORAL"; break;
			case TUBE_CORAL_FAN: ger = "Orgelkorallenfächer"; eng = "TUBE_CORAL_FAN"; break;
			case BRAIN_CORAL_FAN: ger = "Hirnkorallenfächer"; eng = "BRAIN_CORAL_FAN"; break;
			case BUBBLE_CORAL_FAN: ger = "Blasenkorallenfächer"; eng = "BUBBLE_CORAL_FAN"; break;
			case FIRE_CORAL_FAN: ger = "Feuerkorallenfächer"; eng = "FIRE_CORAL_FAN"; break;
			case HORN_CORAL_FAN: ger = "Geweihkorallenfächer"; eng = "HORN_CORAL_FAN"; break;
			case DEAD_TUBE_CORAL_FAN: ger = "Abgestorbener Orgelkorallenfächer"; eng = "DEAD_TUBE_CORAL_FAN"; break;
			case DEAD_BRAIN_CORAL_FAN: ger = "Abgestorbener Hirnkorallenfächer"; eng = "DEAD_BRAIN_CORAL_FAN"; break;
			case DEAD_BUBBLE_CORAL_FAN: ger = "Abgestorbener Blasenkorallenfächer"; eng = "DEAD_BUBBLE_CORAL_FAN"; break;
			case DEAD_FIRE_CORAL_FAN: ger = "Abgestorbener Feuerkorallenfächer"; eng = "DEAD_FIRE_CORAL_FAN"; break;
			case DEAD_HORN_CORAL_FAN: ger = "Abgestorbener Geweihkorallenfächer"; eng = "DEAD_HORN_CORAL_FAN"; break;
			case BLUE_ICE: ger = "Blaues Eis"; eng = "BLUE_ICE"; break;
			case CONDUIT: ger = "Aquisator"; eng = "CONDUIT"; break;
			case POLISHED_GRANITE_STAIRS: ger = "Polierte Granittreppe"; eng = "POLISHED_GRANITE_STAIRS"; break;
			case SMOOTH_RED_SANDSTONE_STAIRS: ger = "Glatte rote Sandsteintreppe"; eng = "SMOOTH_RED_SANDSTONE_STAIRS"; break;
			case MOSSY_STONE_BRICK_STAIRS: ger = "Bemooste Steinziegeltreppe"; eng = "MOSSY_STONE_BRICK_STAIRS"; break;
			case POLISHED_DIORITE_STAIRS: ger = "Polierte Diorittreppe"; eng = "POLISHED_DIORITE_STAIRS"; break;
			case MOSSY_COBBLESTONE_STAIRS: ger = "Bemooste Bruchsteintreppe"; eng = "MOSSY_COBBLESTONE_STAIRS"; break;
			case END_STONE_BRICK_STAIRS: ger = "Endsteinziegeltreppe"; eng = "END_STONE_BRICK_STAIRS"; break;
			case STONE_STAIRS: ger = "Steintreppe"; eng = "STONE_STAIRS"; break;
			case SMOOTH_SANDSTONE_STAIRS: ger = "Glatte Sandsteintreppe"; eng = "SMOOTH_SANDSTONE_STAIRS"; break;
			case SMOOTH_QUARTZ_STAIRS: ger = "Glatte Quarztreppe"; eng = "SMOOTH_QUARTZ_STAIRS"; break;
			case GRANITE_STAIRS: ger = "Granittreppe"; eng = "GRANITE_STAIRS"; break;
			case ANDESITE_STAIRS: ger = "Andesittreppe"; eng = "ANDESITE_STAIRS"; break;
			case RED_NETHER_BRICK_STAIRS: ger = "Rote Netherziegeltreppe"; eng = "RED_NETHER_BRICK_STAIRS"; break;
			case POLISHED_ANDESITE_STAIRS: ger = "Polierte Andesittreppe"; eng = "POLISHED_ANDESITE_STAIRS"; break;
			case DIORITE_STAIRS: ger = "Diorittreppe"; eng = "DIORITE_STAIRS"; break;
			case COBBLED_DEEPSLATE_STAIRS: ger = "Bruchtiefenschiefertreppe"; eng = "COBBLED_DEEPSLATE_STAIRS"; break;
			case POLISHED_DEEPSLATE_STAIRS: ger = "Polierte Tiefenschiefertreppe"; eng = "POLISHED_DEEPSLATE_STAIRS"; break;
			case DEEPSLATE_BRICK_STAIRS: ger = "Tiefenschieferziegeltreppe"; eng = "DEEPSLATE_BRICK_STAIRS"; break;
			case DEEPSLATE_TILE_STAIRS: ger = "Tiefenschieferfliesentreppe"; eng = "DEEPSLATE_TILE_STAIRS"; break;
			case POLISHED_GRANITE_SLAB: ger = "Polierte Granitstufe"; eng = "POLISHED_GRANITE_SLAB"; break;
			case SMOOTH_RED_SANDSTONE_SLAB: ger = "Glatte rote Sandsteinstufe"; eng = "SMOOTH_RED_SANDSTONE_SLAB"; break;
			case MOSSY_STONE_BRICK_SLAB: ger = "Bemooste Steinziegelstufe"; eng = "MOSSY_STONE_BRICK_SLAB"; break;
			case POLISHED_DIORITE_SLAB: ger = "Polierte Dioritstufe"; eng = "POLISHED_DIORITE_SLAB"; break;
			case MOSSY_COBBLESTONE_SLAB: ger = "Bemooste Bruchsteinstufe"; eng = "MOSSY_COBBLESTONE_SLAB"; break;
			case END_STONE_BRICK_SLAB: ger = "Endsteinziegelstufe"; eng = "END_STONE_BRICK_SLAB"; break;
			case SMOOTH_SANDSTONE_SLAB: ger = "Glatte Sandsteinstufe"; eng = "SMOOTH_SANDSTONE_SLAB"; break;
			case SMOOTH_QUARTZ_SLAB: ger = "Glatte Quarzstufe"; eng = "SMOOTH_QUARTZ_SLAB"; break;
			case GRANITE_SLAB: ger = "Granitstufe"; eng = "GRANITE_SLAB"; break;
			case ANDESITE_SLAB: ger = "Andesitstufe"; eng = "ANDESITE_SLAB"; break;
			case RED_NETHER_BRICK_SLAB: ger = "Rote Netherziegelstufe"; eng = "RED_NETHER_BRICK_SLAB"; break;
			case POLISHED_ANDESITE_SLAB: ger = "Polierte Andesitstufe"; eng = "POLISHED_ANDESITE_SLAB"; break;
			case DIORITE_SLAB: ger = "Dioritstufe"; eng = "DIORITE_SLAB"; break;
			case COBBLED_DEEPSLATE_SLAB: ger = "Bruchtiefenschieferstufe"; eng = "COBBLED_DEEPSLATE_SLAB"; break;
			case POLISHED_DEEPSLATE_SLAB: ger = "Polierte Tiefenschieferstufe"; eng = "POLISHED_DEEPSLATE_SLAB"; break;
			case DEEPSLATE_BRICK_SLAB: ger = "Tiefenschieferziegelstufe"; eng = "DEEPSLATE_BRICK_SLAB"; break;
			case DEEPSLATE_TILE_SLAB: ger = "Tiefenschieferfliesenstufe"; eng = "DEEPSLATE_TILE_SLAB"; break;
			case SCAFFOLDING: ger = "Gerüst"; eng = "SCAFFOLDING"; break;
			case REDSTONE: ger = "Redstone-Staub"; eng = "REDSTONE"; break;
			case REDSTONE_TORCH: ger = "Redstonefackel"; eng = "REDSTONE_TORCH"; break;
			case REDSTONE_BLOCK: ger = "Redstone-Block"; eng = "REDSTONE_BLOCK"; break;
			case REPEATER: ger = "Redstone-Verstärker"; eng = "REPEATER"; break;
			case COMPARATOR: ger = "Redstone-Komparator"; eng = "COMPARATOR"; break;
			case PISTON: ger = "Kolben"; eng = "PISTON"; break;
			case STICKY_PISTON: ger = "Klebriger Kolben"; eng = "STICKY_PISTON"; break;
			case SLIME_BLOCK: ger = "Schleimblock"; eng = "SLIME_BLOCK"; break;
			case HONEY_BLOCK: ger = "Honigblock"; eng = "HONEY_BLOCK"; break;
			case OBSERVER: ger = "Beobachter"; eng = "OBSERVER"; break;
			case HOPPER: ger = "Trichter"; eng = "HOPPER"; break;
			case DISPENSER: ger = "Werfer"; eng = "DISPENSER"; break;
			case DROPPER: ger = "Spender"; eng = "DROPPER"; break;
			case LECTERN: ger = "Lesepult"; eng = "LECTERN"; break;
			case TARGET: ger = "Zielblock"; eng = "TARGET"; break;
			case LEVER: ger = "Hebel"; eng = "LEVER"; break;
			case LIGHTNING_ROD: ger = "Blitzableiter"; eng = "LIGHTNING_ROD"; break;
			case DAYLIGHT_DETECTOR: ger = "Tageslichtsensor"; eng = "DAYLIGHT_DETECTOR"; break;
			case SCULK_SENSOR: ger = "Sculk-Sensor"; eng = "SCULK_SENSOR"; break;
			case TRIPWIRE_HOOK: ger = "Haken"; eng = "TRIPWIRE_HOOK"; break;
			case TRAPPED_CHEST: ger = "Redstone-Truhe"; eng = "TRAPPED_CHEST"; break;
			case TNT: ger = "TNT"; eng = "TNT"; break;
			case REDSTONE_LAMP: ger = "Redstone-Lampe"; eng = "REDSTONE_LAMP"; break;
			case NOTE_BLOCK: ger = "Notenblock"; eng = "NOTE_BLOCK"; break;
			case STONE_BUTTON: ger = "Steinknopf"; eng = "STONE_BUTTON"; break;
			case POLISHED_BLACKSTONE_BUTTON: ger = "Polierter Schwarzsteinknopf"; eng = "POLISHED_BLACKSTONE_BUTTON"; break;
			case OAK_BUTTON: ger = "Eichenholzknopf"; eng = "OAK_BUTTON"; break;
			case SPRUCE_BUTTON: ger = "Fichtenholzknopf"; eng = "SPRUCE_BUTTON"; break;
			case BIRCH_BUTTON: ger = "Birkenholzknopf"; eng = "BIRCH_BUTTON"; break;
			case JUNGLE_BUTTON: ger = "Tropenholzknopf"; eng = "JUNGLE_BUTTON"; break;
			case ACACIA_BUTTON: ger = "Akazienholzknopf"; eng = "ACACIA_BUTTON"; break;
			case DARK_OAK_BUTTON: ger = "Schwarzeichenholzknopf"; eng = "DARK_OAK_BUTTON"; break;
			case MANGROVE_BUTTON: ger = "Mngrovenholzknopf"; eng = "MANGROVE_BUTTON"; break;
			case CRIMSON_BUTTON: ger = "Karmesinknopf"; eng = "CRIMSON_BUTTON"; break;
			case WARPED_BUTTON: ger = "Wirrknopf"; eng = "WARPED_BUTTON"; break;
			case STONE_PRESSURE_PLATE: ger = "Steindruckplatte"; eng = "STONE_PRESSURE_PLATE"; break;
			case POLISHED_BLACKSTONE_PRESSURE_PLATE: ger = "Polierte Schwarzsteindruckplatte"; eng = "POLISHED_BLACKSTONE_PRESSURE_PLATE"; break;
			case LIGHT_WEIGHTED_PRESSURE_PLATE: ger = "Feinwägeplatte"; eng = "LIGHT_WEIGHTED_PRESSURE_PLATE"; break;
			case HEAVY_WEIGHTED_PRESSURE_PLATE: ger = "Grobwägeplatte"; eng = "HEAVY_WEIGHTED_PRESSURE_PLATE"; break;
			case OAK_PRESSURE_PLATE: ger = "EichenholzdruckplatteE"; eng = "OAK_PRESSURE_PLATE"; break;
			case SPRUCE_PRESSURE_PLATE: ger = "Fichtenholzdruckplatte"; eng = "SPRUCE_PRESSURE_PLATE"; break;
			case BIRCH_PRESSURE_PLATE: ger = "Birkenholzdruckplatte"; eng = "BIRCH_PRESSURE_PLATE"; break;
			case JUNGLE_PRESSURE_PLATE: ger = "Tropenholzdruckplatte"; eng = "JUNGLE_PRESSURE_PLATE"; break;
			case ACACIA_PRESSURE_PLATE: ger = "Akazienholzdruckplatte"; eng = "ACACIA_PRESSURE_PLATE"; break;
			case DARK_OAK_PRESSURE_PLATE: ger = "Schwarzeichenholzdruckplatte"; eng = "DARK_OAK_PRESSURE_PLATE"; break;
			case MANGROVE_PRESSURE_PLATE: ger = "Mangrovenholzdruckplatte"; eng = "MANGROVE_PRESSURE_PLATE"; break;
			case CRIMSON_PRESSURE_PLATE: ger = "Karmesindruckplatte"; eng = "CRIMSON_PRESSURE_PLATE"; break;
			case WARPED_PRESSURE_PLATE: ger = "Wirrdruckplatte"; eng = "WARPED_PRESSURE_PLATE"; break;
			case IRON_DOOR: ger = "Eisentür"; eng = "IRON_DOOR"; break;
			case OAK_DOOR: ger = "Eichenholztür"; eng = "OAK_DOOR"; break;
			case SPRUCE_DOOR: ger = "Fichtenholztür"; eng = "SPRUCE_DOOR"; break;
			case BIRCH_DOOR: ger = "Birkenholztür"; eng = "BIRCH_DOOR"; break;
			case JUNGLE_DOOR: ger = "Tropenholztür"; eng = "JUNGLE_DOOR"; break;
			case ACACIA_DOOR: ger = "Akazienholztür"; eng = "ACACIA_DOOR"; break;
			case DARK_OAK_DOOR: ger = "Schwarzeichenholztür"; eng = "DARK_OAK_DOOR"; break;
			case MANGROVE_DOOR: ger = "Mangrovenholztür"; eng = "MANGROVE_DOOR"; break;
			case CRIMSON_DOOR: ger = "Wirrtür"; eng = "CRIMSON_DOOR"; break;
			case WARPED_DOOR: ger = "Karmesintür"; eng = "WARPED_DOOR"; break;
			case IRON_TRAPDOOR: ger = "Eisenfalltür"; eng = "IRON_TRAPDOOR"; break;
			case OAK_TRAPDOOR: ger = "Eichenholzfalltür"; eng = "OAK_TRAPDOOR"; break;
			case SPRUCE_TRAPDOOR: ger = "Fichtenholzfalltür"; eng = "SPRUCE_TRAPDOOR"; break;
			case BIRCH_TRAPDOOR: ger = "Birkenholzfalltür"; eng = "BIRCH_TRAPDOOR"; break;
			case JUNGLE_TRAPDOOR: ger = "Tropenholzfalltür"; eng = "JUNGLE_TRAPDOOR"; break;
			case ACACIA_TRAPDOOR: ger = "Akazienholzfalltür"; eng = "ACACIA_TRAPDOOR"; break;
			case DARK_OAK_TRAPDOOR: ger = "Schwarzeichenholzfalltür"; eng = "DARK_OAK_TRAPDOOR"; break;
			case MANGROVE_TRAPDOOR: ger = "Mangrovenholzfalltür"; eng = "MANGROVE_TRAPDOOR"; break;
			case CRIMSON_TRAPDOOR: ger = "Karmesinfalltür"; eng = "CRIMSON_TRAPDOOR"; break;
			case WARPED_TRAPDOOR: ger = "Wirrfalltür"; eng = "WARPED_TRAPDOOR"; break;
			case OAK_FENCE_GATE: ger = "Eichenholzzauntor"; eng = "OAK_FENCE_GATE"; break;
			case SPRUCE_FENCE_GATE: ger = "Fichtenholzzauntor"; eng = "SPRUCE_FENCE_GATE"; break;
			case BIRCH_FENCE_GATE: ger = "Birkenholzzauntor"; eng = "BIRCH_FENCE_GATE"; break;
			case JUNGLE_FENCE_GATE: ger = "Tropenholzzauntor"; eng = "JUNGLE_FENCE_GATE"; break;
			case ACACIA_FENCE_GATE: ger = "Akazienholzzauntor"; eng = "ACACIA_FENCE_GATE"; break;
			case DARK_OAK_FENCE_GATE: ger = "Schwarzeichenholzzauntor"; eng = "DARK_OAK_FENCE_GATE"; break;
			case MANGROVE_FENCE_GATE: ger = "Mangrovenholzzauntor"; eng = "MANGROVE_FENCE_GATE"; break;
			case CRIMSON_FENCE_GATE: ger = "Karmesinzauntor"; eng = "CRIMSON_FENCE_GATE"; break;
			case WARPED_FENCE_GATE: ger = "Wirrzauntor"; eng = "WARPED_FENCE_GATE"; break;
			case POWERED_RAIL: ger = "Antriebsschiene"; eng = "POWERED_RAIL"; break;
			case DETECTOR_RAIL: ger = "Sensorschiene"; eng = "DETECTOR_RAIL"; break;
			case RAIL: ger = "Schiene"; eng = "RAIL"; break;
			case ACTIVATOR_RAIL: ger = "Aktivierungsschiene"; eng = "ACTIVATOR_RAIL"; break;
			case SADDLE: ger = "Sattel"; eng = "SADDLE"; break;
			case MINECART: ger = "Lore"; eng = "MINECART"; break;
			case CHEST_MINECART: ger = "Güterlore"; eng = "CHEST_MINECART"; break;
			case FURNACE_MINECART: ger = "Ofenlore"; eng = "FURNACE_MINECART"; break;
			case TNT_MINECART: ger = "TNT-Lore"; eng = "TNT_MINECART"; break;
			case HOPPER_MINECART: ger = "Trichterlore"; eng = "HOPPER_MINECART"; break;
			case CARROT_ON_A_STICK: ger = "Karottenrute"; eng = "CARROT_ON_A_STICK"; break;
			case WARPED_FUNGUS_ON_A_STICK: ger = "Wirrpilzrute"; eng = "WARPED_FUNGUS_ON_A_STICK"; break;
			case ELYTRA: ger = "Elytren"; eng = "ELYTRA"; break;
			case OAK_BOAT: ger = "Eichenholzboit"; eng = "OAK_BOAT"; break;
			case OAK_CHEST_BOAT: ger = "Eichenholztruhenboot"; eng = "OAK_CHEST_BOAT"; break;
			case SPRUCE_BOAT: ger = "Fichtenholzboot"; eng = "SPRUCE_BOAT"; break;
			case SPRUCE_CHEST_BOAT: ger = "Fichtenholztruhenboot"; eng = "SPRUCE_CHEST_BOAT"; break;
			case BIRCH_BOAT: ger = "Birkenholzboot"; eng = "BIRCH_BOAT"; break;
			case BIRCH_CHEST_BOAT: ger = "Birkenholztruhenboot"; eng = "BIRCH_CHEST_BOAT"; break;
			case JUNGLE_BOAT: ger = "Tropenholzboot"; eng = "JUNGLE_BOAT"; break;
			case JUNGLE_CHEST_BOAT: ger = "Tropenholztruehnboot"; eng = "JUNGLE_CHEST_BOAT"; break;
			case ACACIA_BOAT: ger = "Akazienholzboot"; eng = "ACACIA_BOAT"; break;
			case ACACIA_CHEST_BOAT: ger = "Akazienholztruhenboot"; eng = "ACACIA_CHEST_BOAT"; break;
			case DARK_OAK_BOAT: ger = "Schwarzeichenholzboot"; eng = "DARK_OAK_BOAT"; break;
			case DARK_OAK_CHEST_BOAT: ger = "Schwarzeichenholztruehenboot"; eng = "DARK_OAK_CHEST_BOAT"; break;
			case MANGROVE_BOAT: ger = "Mangrovenholzboot"; eng = "MANGROVE_BOAT"; break;
			case MANGROVE_CHEST_BOAT: ger = "Mangrovenholztruhenboot"; eng = "MANGROVE_CHEST_BOAT"; break;
			case STRUCTURE_BLOCK: ger = "Konstruktionsblock"; eng = "STRUCTURE_BLOCK"; break;
			case JIGSAW: ger = "Verbundblock"; eng = "JIGSAW"; break;
			case TURTLE_HELMET: ger = "Schildkrötenpanzer"; eng = "TURTLE_HELMET"; break;
			case TURTLE_SCUTE: ger = "Hornschild"; eng = "SCUTE"; break;
			case FLINT_AND_STEEL: ger = "Feuerzeug"; eng = "FLINT_AND_STEEL"; break;
			case APPLE: ger = "Apfel"; eng = "APPLE"; break;
			case BOW: ger = "Bogen"; eng = "BOW"; break;
			case ARROW: ger = "Pfeil"; eng = "ARROW"; break;
			case COAL: ger = "Kohle"; eng = "COAL"; break;
			case CHARCOAL: ger = "Holzkohle"; eng = "CHARCOAL"; break;
			case DIAMOND: ger = "Diamant"; eng = "DIAMOND"; break;
			case EMERALD: ger = "Smaragd"; eng = "EMERALD"; break;
			case LAPIS_LAZULI: ger = "Lapislazuli"; eng = "LAPIS_LAZULI"; break;
			case QUARTZ: ger = "Netherquarz"; eng = "QUARTZ"; break;
			case AMETHYST_SHARD: ger = "Amethystscherbe"; eng = "AMETHYST_SHARD"; break;
			case RAW_IRON: ger = "Roheisen"; eng = "RAW_IRON"; break;
			case IRON_INGOT: ger = "Eisenbarren"; eng = "IRON_INGOT"; break;
			case RAW_COPPER: ger = "Rohkupfer"; eng = "RAW_COPPER"; break;
			case COPPER_INGOT: ger = "Kupferbarren"; eng = "COPPER_INGOT"; break;
			case RAW_GOLD: ger = "Rohgold"; eng = "RAW_GOLD"; break;
			case GOLD_INGOT: ger = "Goldbarren"; eng = "GOLD_INGOT"; break;
			case NETHERITE_INGOT: ger = "Netheritbarren"; eng = "NETHERITE_INGOT"; break;
			case NETHERITE_SCRAP: ger = "Netheritplatten"; eng = "NETHERITE_SCRAP"; break;
			case WOODEN_SWORD: ger = "Holzschwert"; eng = "WOODEN_SWORD"; break;
			case WOODEN_SHOVEL: ger = "Holzschaufel"; eng = "WOODEN_SHOVEL"; break;
			case WOODEN_PICKAXE: ger = "Holzspitzhacke"; eng = "WOODEN_PICKAXE"; break;
			case WOODEN_AXE: ger = "Holzaxt"; eng = "WOODEN_AXE"; break;
			case WOODEN_HOE: ger = "Holzhacke"; eng = "WOODEN_HOE"; break;
			case STONE_SWORD: ger = "Steinschwert"; eng = "STONE_SWORD"; break;
			case STONE_SHOVEL: ger = "Steinschaufel"; eng = "STONE_SHOVEL"; break;
			case STONE_PICKAXE: ger = "Steinspitzhacke"; eng = "STONE_PICKAXE"; break;
			case STONE_AXE: ger = "Steinaxt"; eng = "STONE_AXE"; break;
			case STONE_HOE: ger = "Steinhacke"; eng = "STONE_HOE"; break;
			case GOLDEN_SWORD: ger = "Goldschwert"; eng = "GOLDEN_SWORD"; break;
			case GOLDEN_SHOVEL: ger = "Goldschaufel"; eng = "GOLDEN_SHOVEL"; break;
			case GOLDEN_PICKAXE: ger = "Goldspitzhacke"; eng = "GOLDEN_PICKAXE"; break;
			case GOLDEN_AXE: ger = "Goldaxt"; eng = "GOLDEN_AXE"; break;
			case GOLDEN_HOE: ger = "Goldhacke"; eng = "GOLDEN_HOE"; break;
			case IRON_SWORD: ger = "Eisenschwert"; eng = "IRON_SWORD"; break;
			case IRON_SHOVEL: ger = "Eisenschaufel"; eng = "IRON_SHOVEL"; break;
			case IRON_PICKAXE: ger = "Eisenspitzhacke"; eng = "IRON_PICKAXE"; break;
			case IRON_AXE: ger = "Eisenaxt"; eng = "IRON_AXE"; break;
			case IRON_HOE: ger = "Eisenhacke"; eng = "IRON_HOE"; break;
			case DIAMOND_SWORD: ger = "Diamantschwert"; eng = "DIAMOND_SWORD"; break;
			case DIAMOND_SHOVEL: ger = "Diamantschaufel"; eng = "DIAMOND_SHOVEL"; break;
			case DIAMOND_PICKAXE: ger = "Diamantspitzhacke"; eng = "DIAMOND_PICKAXE"; break;
			case DIAMOND_AXE: ger = "Diamantaxt"; eng = "DIAMOND_AXE"; break;
			case DIAMOND_HOE: ger = "Diamanthacke"; eng = "DIAMOND_HOE"; break;
			case NETHERITE_SWORD: ger = "Netheritschwert"; eng = "NETHERITE_SWORD"; break;
			case NETHERITE_SHOVEL: ger = "Netheritschaufel"; eng = "NETHERITE_SHOVEL"; break;
			case NETHERITE_PICKAXE: ger = "Netheritspitzhacke"; eng = "NETHERITE_PICKAXE"; break;
			case NETHERITE_AXE: ger = "Netheritaxt"; eng = "NETHERITE_AXE"; break;
			case NETHERITE_HOE: ger = "Netherithacke"; eng = "NETHERITE_HOE"; break;
			case STICK: ger = "Stock"; eng = "STICK"; break;
			case BOWL: ger = "Schüssel"; eng = "BOWL"; break;
			case MUSHROOM_STEW: ger = "Pilzsuppe"; eng = "MUSHROOM_STEW"; break;
			case STRING: ger = "Faden"; eng = "STRING"; break;
			case FEATHER: ger = "Feder"; eng = "FEATHER"; break;
			case GUNPOWDER: ger = "Schwarzpulver"; eng = "GUNPOWDER"; break;
			case WHEAT_SEEDS: ger = "Weizenkörner"; eng = "WHEAT_SEEDS"; break;
			case WHEAT: ger = "Weizen"; eng = "WHEAT"; break;
			case BREAD: ger = "Brot"; eng = "BREAD"; break;
			case LEATHER_HELMET: ger = "Lederkappe"; eng = "LEATHER_HELMET"; break;
			case LEATHER_CHESTPLATE: ger = "Lederjacke"; eng = "LEATHER_CHESTPLATE"; break;
			case LEATHER_LEGGINGS: ger = "Lederhose"; eng = "LEATHER_LEGGINGS"; break;
			case LEATHER_BOOTS: ger = "Lederstiefel"; eng = "LEATHER_BOOTS"; break;
			case CHAINMAIL_HELMET: ger = "Kettenhaube"; eng = "CHAINMAIL_HELMET"; break;
			case CHAINMAIL_CHESTPLATE: ger = "Kettenhemd"; eng = "CHAINMAIL_CHESTPLATE"; break;
			case CHAINMAIL_LEGGINGS: ger = "Kettenhose"; eng = "CHAINMAIL_LEGGINGS"; break;
			case CHAINMAIL_BOOTS: ger = "Kettenstiefel"; eng = "CHAINMAIL_BOOTS"; break;
			case IRON_HELMET: ger = "Eisenhelm"; eng = "IRON_HELMET"; break;
			case IRON_CHESTPLATE: ger = "Eisenharnisch"; eng = "IRON_CHESTPLATE"; break;
			case IRON_LEGGINGS: ger = "Eisenbeinschutz"; eng = "IRON_LEGGINGS"; break;
			case IRON_BOOTS: ger = "Eisenstiefel"; eng = "IRON_BOOTS"; break;
			case DIAMOND_HELMET: ger = "Diamanthelm"; eng = "DIAMOND_HELMET"; break;
			case DIAMOND_CHESTPLATE: ger = "Diamantharnisch"; eng = "DIAMOND_CHESTPLATE"; break;
			case DIAMOND_LEGGINGS: ger = "Diamantbeinschutz"; eng = "DIAMOND_LEGGINGS"; break;
			case DIAMOND_BOOTS: ger = "Diamantstiefel"; eng = "DIAMOND_BOOTS"; break;
			case GOLDEN_HELMET: ger = "Goldhelm"; eng = "GOLDEN_HELMET"; break;
			case GOLDEN_CHESTPLATE: ger = "Goldharnisch"; eng = "GOLDEN_CHESTPLATE"; break;
			case GOLDEN_LEGGINGS: ger = "Goldbeinschutz"; eng = "GOLDEN_LEGGINGS"; break;
			case GOLDEN_BOOTS: ger = "Goldstiefel"; eng = "GOLDEN_BOOTS"; break;
			case NETHERITE_HELMET: ger = "Netherithelm"; eng = "NETHERITE_HELMET"; break;
			case NETHERITE_CHESTPLATE: ger = "Netheritharnisch"; eng = "NETHERITE_CHESTPLATE"; break;
			case NETHERITE_LEGGINGS: ger = "Netheritbeinschutz"; eng = "NETHERITE_LEGGINGS"; break;
			case NETHERITE_BOOTS: ger = "Netheritstiefel"; eng = "NETHERITE_BOOTS"; break;
			case FLINT: ger = "Feuerstein"; eng = "FLINT"; break;
			case PORKCHOP: ger = "Schweinefleisch"; eng = "PORKCHOP"; break;
			case COOKED_PORKCHOP: ger = "Gebratenes Schweinefleisch"; eng = "COOKED_PORKCHOP"; break;
			case PAINTING: ger = "Gemälde"; eng = "PAINTING"; break;
			case GOLDEN_APPLE: ger = "Goldener Apfel"; eng = "GOLDEN_APPLE"; break;
			case ENCHANTED_GOLDEN_APPLE: ger = "Verzauberter goldener Apfel"; eng = "ENCHANTED_GOLDEN_APPLE"; break;
			case OAK_SIGN: ger = "Eichenholzschild"; eng = "OAK_SIGN"; break;
			case SPRUCE_SIGN: ger = "Fichtenholzschild"; eng = "SPRUCE_SIGN"; break;
			case BIRCH_SIGN: ger = "Birkenholzschild"; eng = "BIRCH_SIGN"; break;
			case JUNGLE_SIGN: ger = "Tropenholzschild"; eng = "JUNGLE_SIGN"; break;
			case ACACIA_SIGN: ger = "Akazienholzschild"; eng = "ACACIA_SIGN"; break;
			case DARK_OAK_SIGN: ger = "Schwarzeichenholzschild"; eng = "DARK_OAK_SIGN"; break;
			case MANGROVE_SIGN: ger = "Mangrovenschild"; eng = "MANGROVE_SIGN"; break;
			case CRIMSON_SIGN: ger = "Karmesinschild"; eng = "CRIMSON_SIGN"; break;
			case WARPED_SIGN: ger = "Wirrschild"; eng = "WARPED_SIGN"; break;
			case BUCKET: ger = "Eimer"; eng = "BUCKET"; break;
			case WATER_BUCKET: ger = "Wassereimer"; eng = "WATER_BUCKET"; break;
			case LAVA_BUCKET: ger = "Lavaeimer"; eng = "LAVA_BUCKET"; break;
			case POWDER_SNOW_BUCKET: ger = "Pulverschneeeimer"; eng = "POWDER_SNOW_BUCKET"; break;
			case SNOWBALL: ger = "Schneeball"; eng = "SNOWBALL"; break;
			case LEATHER: ger = "Leder"; eng = "LEATHER"; break;
			case MILK_BUCKET: ger = "Milcheimer"; eng = "MILK_BUCKET"; break;
			case PUFFERFISH_BUCKET: ger = "Kugelfischeimer"; eng = "PUFFERFISH_BUCKET"; break;
			case SALMON_BUCKET: ger = "Lachseimer"; eng = "SALMON_BUCKET"; break;
			case COD_BUCKET: ger = "Kabeljaueimer"; eng = "COD_BUCKET"; break;
			case TROPICAL_FISH_BUCKET: ger = "Tropenfischeimer"; eng = "TROPICAL_FISH_BUCKET"; break;
			case AXOLOTL_BUCKET: ger = "Axolotleimer"; eng = "AXOLOTL_BUCKET"; break;
			case TADPOLE_BUCKET: ger = "Kaulquappeneimer"; eng = "TADPOLE_BUCKET"; break;
			case BRICK: ger = "Ziegel"; eng = "BRICK"; break;
			case CLAY_BALL: ger = "Tonklumpen"; eng = "CLAY_BALL"; break;
			case DRIED_KELP_BLOCK: ger = "Getrockneter Seetangblock"; eng = "DRIED_KELP_BLOCK"; break;
			case PAPER: ger = "Papier"; eng = "PAPER"; break;
			case BOOK: ger = "Buch"; eng = "BOOK"; break;
			case SLIME_BALL: ger = "Schleimball"; eng = "SLIME_BALL"; break;
			case EGG: ger = "Ei"; eng = "EGG"; break;
			case COMPASS: ger = "Kompass"; eng = "COMPASS"; break;
			case RECOVERY_COMPASS: ger = "Bergungskompass"; eng = "RECOVERY_COMPASS"; break;
			case BUNDLE: ger = "Sack"; eng = "BUNDLE"; break;
			case FISHING_ROD: ger = "Angel"; eng = "FISHING_ROD"; break;
			case CLOCK: ger = "Uhr"; eng = "CLOCK"; break;
			case SPYGLASS: ger = "Fernrohr"; eng = "SPYGLASS"; break;
			case GLOWSTONE_DUST: ger = "Leuchtsteinstaub"; eng = "GLOWSTONE_DUST"; break;
			case COD: ger = "Kabeljau"; eng = "COD"; break;
			case SALMON: ger = "Lachs"; eng = "SALMON"; break;
			case TROPICAL_FISH: ger = "Tropenfisch"; eng = "TROPICAL_FISH"; break;
			case PUFFERFISH: ger = "Kugelfisch"; eng = "PUFFERFISH"; break;
			case COOKED_COD: ger = "Gebratener Kabeljau"; eng = "COOKED_COD"; break;
			case COOKED_SALMON: ger = "Gebratener Lachs"; eng = "COOKED_SALMON"; break;
			case INK_SAC: ger = "Tintensack"; eng = "INK_SAC"; break;
			case GLOW_INK_SAC: ger = "Leuchttintenbeutel"; eng = "GLOW_INK_SAC"; break;
			case COCOA_BEANS: ger = "Kakaobohnen"; eng = "COCOA_BEANS"; break;
			case WHITE_DYE: ger = "Weißer Farbstoff"; eng = "WHITE_DYE"; break;
			case ORANGE_DYE: ger = "Oranger Farbstoff"; eng = "ORANGE_DYE"; break;
			case MAGENTA_DYE: ger = "Magenta Farbstoff"; eng = "MAGENTA_DYE"; break;
			case LIGHT_BLUE_DYE: ger = "Hellblauer Farbstoff"; eng = "LIGHT_BLUE_DYE"; break;
			case YELLOW_DYE: ger = "Gelber Farbstoff"; eng = "YELLOW_DYE"; break;
			case LIME_DYE: ger = "Hellgrüner Farbstoff"; eng = "LIME_DYE"; break;
			case PINK_DYE: ger = "Rosa Farbstoff"; eng = "PINK_DYE"; break;
			case GRAY_DYE: ger = "Grauer Farbstoff"; eng = "GRAY_DYE"; break;
			case LIGHT_GRAY_DYE: ger = "Hellgrauer Farbstoff"; eng = "LIGHT_GRAY_DYE"; break;
			case CYAN_DYE: ger = "Türkiser Farbstoff"; eng = "CYAN_DYE"; break;
			case PURPLE_DYE: ger = "Violetter Farbstoff"; eng = "PURPLE_DYE"; break;
			case BLUE_DYE: ger = "Blauer Farbstoff"; eng = "BLUE_DYE"; break;
			case BROWN_DYE: ger = "Brauner Farbstoff"; eng = "BROWN_DYE"; break;
			case GREEN_DYE: ger = "Grüner Farbstoff"; eng = "GREEN_DYE"; break;
			case RED_DYE: ger = "Roter Farbstoff"; eng = "RED_DYE"; break;
			case BLACK_DYE: ger = "Schwarzer Farbstoff"; eng = "case DYE"; break;
			case BONE_MEAL: ger = "Knochenmehl"; eng = "BONE_MEAL"; break;
			case BONE: ger = "Knochen"; eng = "BONE"; break;
			case SUGAR: ger = "Zucker"; eng = "SUGAR"; break;
			case CAKE: ger = "Kuchen"; eng = "CAKE"; break;
			case WHITE_BED: ger = "Weisses Bett"; eng = "WHITE_BED"; break;
			case ORANGE_BED: ger = "Oranges Bett"; eng = "ORANGE_BED"; break;
			case MAGENTA_BED: ger = "Magenta Bett"; eng = "MAGENTA_BED"; break;
			case LIGHT_BLUE_BED: ger = "Hellblaues Bett"; eng = "LIGHT_BLUE_BED"; break;
			case YELLOW_BED: ger = "Gelbes Bett"; eng = "YELLOW_BED"; break;
			case LIME_BED: ger = "Hellgrünes Bett"; eng = "LIME_BED"; break;
			case PINK_BED: ger = "Rosa Bett"; eng = "PINK_BED"; break;
			case GRAY_BED: ger = "Graues Bett"; eng = "GRAY_BED"; break;
			case LIGHT_GRAY_BED: ger = "hellgraues Bett"; eng = "LIGHT_GRAY_BED"; break;
			case CYAN_BED: ger = "Türkises Bett"; eng = "CYAN_BED"; break;
			case PURPLE_BED: ger = "Violettes Bett"; eng = "PURPLE_BED"; break;
			case BLUE_BED: ger = "Blaues Bett"; eng = "BLUE_BED"; break;
			case BROWN_BED: ger = "Braunes Bett"; eng = "BROWN_BED"; break;
			case GREEN_BED: ger = "Grünes Bett"; eng = "GREEN_BED"; break;
			case RED_BED: ger = "Rotes Bett"; eng = "RED_BED"; break;
			case BLACK_BED: ger = "Schwarzes Bett"; eng = "BLACK_BED"; break;
			case COOKIE: ger = "Keks"; eng = "COOKIE"; break;
			case FILLED_MAP: ger = "Karte"; eng = "FILLED_MAP"; break;
			case SHEARS: ger = "Schere"; eng = "SHEARS"; break;
			case MELON_SLICE: ger = "Melonenscheibe"; eng = "MELON_SLICE"; break;
			case DRIED_KELP: ger = "Getrockneter Seetang"; eng = "DRIED_KELP"; break;
			case PUMPKIN_SEEDS: ger = "Kürbiskerne"; eng = "PUMPKIN_SEEDS"; break;
			case MELON_SEEDS: ger = "Melonenkerne"; eng = "MELON_SEEDS"; break;
			case BEEF: ger = "Rohes Rindfleisch"; eng = "BEEF"; break;
			case COOKED_BEEF: ger = "Steak"; eng = "COOKED_BEEF"; break;
			case CHICKEN: ger = "Rohes Hühnchen"; eng = "CHICKEN"; break;
			case COOKED_CHICKEN: ger = "Gebratenes Hühnchen"; eng = "COOKED_CHICKEN"; break;
			case ROTTEN_FLESH: ger = "Verrottetes Fleisch"; eng = "ROTTEN_FLESH"; break;
			case ENDER_PEARL: ger = "Enderperle"; eng = "ENDER_PEARL"; break;
			case BLAZE_ROD: ger = "Lohenrute"; eng = "BLAZE_ROD"; break;
			case GHAST_TEAR: ger = "Ghast-Träne"; eng = "GHAST_TEAR"; break;
			case GOLD_NUGGET: ger = "Goldklumpen"; eng = "GOLD_NUGGET"; break;
			case NETHER_WART: ger = "Netherwarze"; eng = "NETHER_WART"; break;
			case POTION: ger = "Trank"; eng = "POTION"; break;
			case GLASS_BOTTLE: ger = "Glasflasche"; eng = "GLASS_BOTTLE"; break;
			case SPIDER_EYE: ger = "Spinnenauge"; eng = "SPIDER_EYE"; break;
			case FERMENTED_SPIDER_EYE: ger = "Fermentiertes Spinnenauge"; eng = "FERMENTED_SPIDER_EYE"; break;
			case BLAZE_POWDER: ger = "Lohenstaub"; eng = "BLAZE_POWDER"; break;
			case MAGMA_CREAM: ger = "Magmacreme"; eng = "MAGMA_CREAM"; break;
			case BREWING_STAND: ger = "Braustand"; eng = "BREWING_STAND"; break;
			case CAULDRON: ger = "Kessel"; eng = "CAULDRON"; break;
			case ENDER_EYE: ger = "Enderauge"; eng = "ENDER_EYE"; break;
			case GLISTERING_MELON_SLICE: ger = "Glizernde Melonenscheibe"; eng = "GLISTERING_MELON_SLICE"; break;
			case ALLAY_SPAWN_EGG: ger = "Hilfsgeist-Spawn-Ei"; eng = "ALLAY_SPAWN_EGG"; break;
			case AXOLOTL_SPAWN_EGG: ger = "Axolottl-Spawn-Ei"; eng = "AXOLOTL_SPAWN_EGG"; break;
			case BAT_SPAWN_EGG: ger = "Fledermaus-Spawn-Ei"; eng = "BAT_SPAWN_EGG"; break;
			case BEE_SPAWN_EGG: ger = "Bienen-Spawn-Ei"; eng = "BEE_SPAWN_EGG"; break;
			case BLAZE_SPAWN_EGG: ger = "Lohen-Spawn-Ei"; eng = "BLAZE_SPAWN_EGG"; break;
			case CAT_SPAWN_EGG: ger = "Katzen-Spawn-Ei"; eng = "CAT_SPAWN_EGG"; break;
			case CAVE_SPIDER_SPAWN_EGG: ger = "Hohlenspinnen-Spawn-Ei"; eng = "CAVE_SPIDER_SPAWN_EGG"; break;
			case CHICKEN_SPAWN_EGG: ger = "Huhn-Spawn-Ei"; eng = "CHICKEN_SPAWN_EGG"; break;
			case COD_SPAWN_EGG: ger = "Kabeljau-Spawn-Ei"; eng = "COD_SPAWN_EGG"; break;
			case COW_SPAWN_EGG: ger = "Kuh-Spawn-Ei"; eng = "COW_SPAWN_EGG"; break;
			case CREEPER_SPAWN_EGG: ger = "Creeper-Spawn-Ei"; eng = "CREEPER_SPAWN_EGG"; break;
			case DOLPHIN_SPAWN_EGG: ger = "Delphin-Spawn-Ei"; eng = "DOLPHIN_SPAWN_EGG"; break;
			case DONKEY_SPAWN_EGG: ger = "Esel-Spawn-Ei"; eng = "DONKEY_SPAWN_EGG"; break;
			case DROWNED_SPAWN_EGG: ger = "Ertrunkenen-Spawn-Ei"; eng = "DROWNED_SPAWN_EGG"; break;
			case ELDER_GUARDIAN_SPAWN_EGG: ger = "Großer-Wächter-Spawn-Ei"; eng = "ELDER_GUARDIAN_SPAWN_EGG"; break;
			case ENDERMAN_SPAWN_EGG: ger = "Endermen-Spawn-Ei"; eng = "ENDERMAN_SPAWN_EGG"; break;
			case ENDERMITE_SPAWN_EGG: ger = "Endermiten-Spawn-Ei"; eng = "ENDERMITE_SPAWN_EGG"; break;
			case EVOKER_SPAWN_EGG: ger = "Magier-Spawn-Ei"; eng = "EVOKER_SPAWN_EGG"; break;
			case FOX_SPAWN_EGG: ger = "Fuchs-Spawn-Ei"; eng = "FOX_SPAWN_EGG"; break;
			case FROG_SPAWN_EGG: ger = "Frosch-Spawn-Ei"; eng = "FROG_SPAWN_EGG"; break;
			case GHAST_SPAWN_EGG: ger = "Ghast-Spawn-Ei"; eng = "GHAST_SPAWN_EGG"; break;
			case GLOW_SQUID_SPAWN_EGG: ger = "Leuchttintenfisch-Spawn-Ei"; eng = "GLOW_SQUID_SPAWN_EGG"; break;
			case GOAT_SPAWN_EGG: ger = "Ziegen-Spawn-Ei"; eng = "GOAT_SPAWN_EGG"; break;
			case GUARDIAN_SPAWN_EGG: ger = "Wächter-Spawn-Ei"; eng = "GUARDIAN_SPAWN_EGG"; break;
			case HOGLIN_SPAWN_EGG: ger = "Hoglin-Spawn-Ei"; eng = "HOGLIN_SPAWN_EGG"; break;
			case HORSE_SPAWN_EGG: ger = "Pferde-Spawn-Ei"; eng = "HORSE_SPAWN_EGG"; break;
			case HUSK_SPAWN_EGG: ger = "Wüstenzombie-Spawn-Ei"; eng = "HUSK_SPAWN_EGG"; break;
			case LLAMA_SPAWN_EGG: ger = "Lama-Spawn-Ei"; eng = "LLAMA_SPAWN_EGG"; break;
			case MAGMA_CUBE_SPAWN_EGG: ger = "Magmawürfel-Spawn-Ei"; eng = "MAGMA_CUBE_SPAWN_EGG"; break;
			case MOOSHROOM_SPAWN_EGG: ger = "Mooshroom-Spawn-Ei"; eng = "MOOSHROOM_SPAWN_EGG"; break;
			case MULE_SPAWN_EGG: ger = "Maultier-Spawn-Ei"; eng = "MULE_SPAWN_EGG"; break;
			case OCELOT_SPAWN_EGG: ger = "Ozelot-Spawn-Ei"; eng = "OCELOT_SPAWN_EGG"; break;
			case PANDA_SPAWN_EGG: ger = "Panda-Spawn-Ei"; eng = "PANDA_SPAWN_EGG"; break;
			case PARROT_SPAWN_EGG: ger = "Papageien-Spawn-Ei"; eng = "PARROT_SPAWN_EGG"; break;
			case PHANTOM_SPAWN_EGG: ger = "Phantom-Spawn-Ei"; eng = "PHANTOM_SPAWN_EGG"; break;
			case PIG_SPAWN_EGG: ger = "Schweine-Spawn-Ei"; eng = "PIG_SPAWN_EGG"; break;
			case PIGLIN_SPAWN_EGG: ger = "Piglin-Spawn-Ei"; eng = "PIGLIN_SPAWN_EGG"; break;
			case PIGLIN_BRUTE_SPAWN_EGG: ger = "Piglin-Babaren-Spawn-Ei"; eng = "PIGLIN_BRUTE_SPAWN_EGG"; break;
			case PILLAGER_SPAWN_EGG: ger = "Plünderer-Spawn-Ei"; eng = "PILLAGER_SPAWN_EGG"; break;
			case POLAR_BEAR_SPAWN_EGG: ger = "Eisbären-Spawn-Ei"; eng = "POLAR_BEAR_SPAWN_EGG"; break;
			case PUFFERFISH_SPAWN_EGG: ger = "Kugelfisch-Spawn-Ei"; eng = "PUFFERFISH_SPAWN_EGG"; break;
			case RABBIT_SPAWN_EGG: ger = "Kaninchen-Spawn-Ei"; eng = "RABBIT_SPAWN_EGG"; break;
			case RAVAGER_SPAWN_EGG: ger = "Verwüster-Spawn-Ei"; eng = "RAVAGER_SPAWN_EGG"; break;
			case SALMON_SPAWN_EGG: ger = "Lachs-Spawn-Ei"; eng = "SALMON_SPAWN_EGG"; break;
			case SHEEP_SPAWN_EGG: ger = "Schafs-Spawn-Ei"; eng = "SHEEP_SPAWN_EGG"; break;
			case SHULKER_SPAWN_EGG: ger = "Shulker-Spawn-Ei"; eng = "SHULKER_SPAWN_EGG"; break;
			case SILVERFISH_SPAWN_EGG: ger = "Silberfischchen-Spawn-Ei"; eng = "SILVERFISH_SPAWN_EGG"; break;
			case SKELETON_SPAWN_EGG: ger = "Skelett-Spawn-Ei"; eng = "SKELETON_SPAWN_EGG"; break;
			case SKELETON_HORSE_SPAWN_EGG: ger = "Skelett-Pferde-Spawn-Ei"; eng = "SKELETON_HORSE_SPAWN_EGG"; break;
			case SLIME_SPAWN_EGG: ger = "Schleim-Spawn-Ei"; eng = "SLIME_SPAWN_EGG"; break;
			case SPIDER_SPAWN_EGG: ger = "Spinnen-Spawn-Ei"; eng = "SPIDER_SPAWN_EGG"; break;
			case SQUID_SPAWN_EGG: ger = "Tintenfisch-Spawn-Ei"; eng = "SQUID_SPAWN_EGG"; break;
			case STRAY_SPAWN_EGG: ger = "Eiswanderer-Spawn-Ei"; eng = "STRAY_SPAWN_EGG"; break;
			case STRIDER_SPAWN_EGG: ger = "Schreiter-Spawn-Ei"; eng = "STRIDER_SPAWN_EGG"; break;
			case TADPOLE_SPAWN_EGG: ger = "Kaulquappen-Spawn-Ei"; eng = "TADPOLE_SPAWN_EGG"; break;
			case TRADER_LLAMA_SPAWN_EGG: ger = "Händlerlama-Spawn-Ei"; eng = "TRADER_LLAMA_SPAWN_EGG"; break;
			case TROPICAL_FISH_SPAWN_EGG: ger = "Tropenfisch-Spawn-Ei"; eng = "TROPICAL_FISH_SPAWN_EGG"; break;
			case TURTLE_SPAWN_EGG: ger = "Schildkröten-Spawn-Ei"; eng = "TURTLE_SPAWN_EGG"; break;
			case VEX_SPAWN_EGG: ger = "Plagegeister-Spawn-Ei"; eng = "VEX_SPAWN_EGG"; break;
			case VILLAGER_SPAWN_EGG: ger = "Dorfbewohner-Spawn-Ei"; eng = "VILLAGER_SPAWN_EGG"; break;
			case VINDICATOR_SPAWN_EGG: ger = "Diener-Spawn-Ei"; eng = "VINDICATOR_SPAWN_EGG"; break;
			case WANDERING_TRADER_SPAWN_EGG: ger = "Fahrender-Händler-Spawn-Ei"; eng = "WANDERING_TRADER_SPAWN_EGG"; break;
			case WARDEN_SPAWN_EGG: ger = "Wärter-Spawn-Ei"; eng = "WARDEN_SPAWN_EGG"; break;
			case WITCH_SPAWN_EGG: ger = "Hexen-Spawn-Ei"; eng = "WITCH_SPAWN_EGG"; break;
			case WITHER_SKELETON_SPAWN_EGG: ger = "Witherskelett-Spawn-Ei"; eng = "WITHER_SKELETON_SPAWN_EGG"; break;
			case WOLF_SPAWN_EGG: ger = "Wolfs-Spawn-Ei"; eng = "WOLF_SPAWN_EGG"; break;
			case ZOGLIN_SPAWN_EGG: ger = "Zoglin-Spawn-Ei"; eng = "ZOGLIN_SPAWN_EGG"; break;
			case ZOMBIE_SPAWN_EGG: ger = "Zobie-Spawn-Ei"; eng = "ZOMBIE_SPAWN_EGG"; break;
			case ZOMBIE_HORSE_SPAWN_EGG: ger = "Zombiepferde-Spawn-Ei"; eng = "ZOMBIE_HORSE_SPAWN_EGG"; break;
			case ZOMBIE_VILLAGER_SPAWN_EGG: ger = "Zombiedorfbewohner-Spawn-Ei"; eng = "ZOMBIE_VILLAGER_SPAWN_EGG"; break;
			case ZOMBIFIED_PIGLIN_SPAWN_EGG: ger = "Zobifizierter-Piglin-Spawn-Ein"; eng = "ZOMBIFIED_PIGLIN_SPAWN_EGG"; break;
			case EXPERIENCE_BOTTLE: ger = "Erfahrungsfläschchen"; eng = "EXPERIENCE_BOTTLE"; break;
			case FIRE_CHARGE: ger = "Feuerkugel"; eng = "FIRE_CHARGE"; break;
			case WRITABLE_BOOK: ger = "Buch und Feder"; eng = "WRITABLE_BOOK"; break;
			case WRITTEN_BOOK: ger = "Beschriebenes Buch"; eng = "WRITTEN_BOOK"; break;
			case ITEM_FRAME: ger = "Rahmen"; eng = "ITEM_FRAME"; break;
			case GLOW_ITEM_FRAME: ger = "Leuchtrahmen"; eng = "GLOW_ITEM_FRAME"; break;
			case FLOWER_POT: ger = "Blumentopf"; eng = "FLOWER_POT"; break;
			case CARROT: ger = "Karotte"; eng = "CARROT"; break;
			case POTATO: ger = "Kartoffel"; eng = "POTATO"; break;
			case BAKED_POTATO: ger = "Ofenkartoffel"; eng = "BAKED_POTATO"; break;
			case POISONOUS_POTATO: ger = "Giftige Kartoffel"; eng = "POISONOUS_POTATO"; break;
			case MAP: ger = "Leere Karte"; eng = "MAP"; break;
			case GOLDEN_CARROT: ger = "Goldene Karotte"; eng = "GOLDEN_CARROT"; break;
			case SKELETON_SKULL: ger = "Skelettschädel"; eng = "SKELETON_SKULL"; break;
			case WITHER_SKELETON_SKULL: ger = "Whitherskelettschädel"; eng = "WITHER_SKELETON_SKULL"; break;
			case PLAYER_HEAD: ger = "Spielerkopf"; eng = "PLAYER_HEAD"; break;
			case ZOMBIE_HEAD: ger = "Zombiekopf"; eng = "ZOMBIE_HEAD"; break;
			case CREEPER_HEAD: ger = "Creeperkopf"; eng = "CREEPER_HEAD"; break;
			case DRAGON_HEAD: ger = "Drachenkopf"; eng = "DRAGON_HEAD"; break;
			case NETHER_STAR: ger = "Netherstern"; eng = "NETHER_STAR"; break;
			case PUMPKIN_PIE: ger = "Kürbiskuchen"; eng = "PUMPKIN_PIE"; break;
			case FIREWORK_ROCKET: ger = "Feuerwerksrakete"; eng = "FIREWORK_ROCKET"; break;
			case FIREWORK_STAR: ger = "Feuerwerksstern"; eng = "FIREWORK_STAR"; break;
			case ENCHANTED_BOOK: ger = "Verzaubertes Buch"; eng = "ENCHANTED_BOOK"; break;
			case NETHER_BRICK: ger = "Netherziegel"; eng = "NETHER_BRICK"; break;
			case PRISMARINE_SHARD: ger = "Prismarinscherbe"; eng = "PRISMARINE_SHARD"; break;
			case PRISMARINE_CRYSTALS: ger = "Prismarinkristalle"; eng = "PRISMARINE_CRYSTALS"; break;
			case RABBIT: ger = "Rohes Kaninchen"; eng = "RABBIT"; break;
			case COOKED_RABBIT: ger = "Gebratenes Kaninchen"; eng = "COOKED_RABBIT"; break;
			case RABBIT_STEW: ger = "Kaninchenragout"; eng = "RABBIT_STEW"; break;
			case RABBIT_FOOT: ger = "Hasenpfote"; eng = "RABBIT_FOOT"; break;
			case RABBIT_HIDE: ger = "Kaninchenfell"; eng = "RABBIT_HIDE"; break;
			case ARMOR_STAND: ger = "Rüstungsständer"; eng = "ARMOR_STAND"; break;
			case IRON_HORSE_ARMOR: ger = "Eisner Rossharnisch"; eng = "IRON_HORSE_ARMOR"; break;
			case GOLDEN_HORSE_ARMOR: ger = "Goldener Rossharnisch"; eng = "GOLDEN_HORSE_ARMOR"; break;
			case DIAMOND_HORSE_ARMOR: ger = "Diamantener Rossharnisch"; eng = "DIAMOND_HORSE_ARMOR"; break;
			case LEATHER_HORSE_ARMOR: ger = "Ledener Rossharisch"; eng = "LEATHER_HORSE_ARMOR"; break;
			case LEAD: ger = "Leine"; eng = "LEAD"; break;
			case NAME_TAG: ger = "Namensschild"; eng = "NAME_TAG"; break;
			case COMMAND_BLOCK_MINECART: ger = "Befehlsblocklore"; eng = "COMMAND_BLOCK_MINECART"; break;
			case MUTTON: ger = "Rohes Hammelfleisch"; eng = "MUTTON"; break;
			case COOKED_MUTTON: ger = "Gebratenes Hammelfleisch"; eng = "COOKED_MUTTON"; break;
			case WHITE_BANNER: ger = "Weißes Banner"; eng = "WHITE_BANNER"; break;
			case ORANGE_BANNER: ger = "Oranges Banner"; eng = "ORANGE_BANNER"; break;
			case MAGENTA_BANNER: ger = "Magenta Banner"; eng = "MAGENTA_BANNER"; break;
			case LIGHT_BLUE_BANNER: ger = "Hellblaues Banner"; eng = "LIGHT_BLUE_BANNER"; break;
			case YELLOW_BANNER: ger = "Gelbes Banner"; eng = "YELLOW_BANNER"; break;
			case LIME_BANNER: ger = "Hellgrünes Banner"; eng = "LIME_BANNER"; break;
			case PINK_BANNER: ger = "Rosa Banner"; eng = "PINK_BANNER"; break;
			case GRAY_BANNER: ger = "Graues Banner"; eng = "GRAY_BANNER"; break;
			case LIGHT_GRAY_BANNER: ger = "hellgraues Banner"; eng = "LIGHT_GRAY_BANNER"; break;
			case CYAN_BANNER: ger = "Türkises Banner"; eng = "CYAN_BANNER"; break;
			case PURPLE_BANNER: ger = "Violettes Banner"; eng = "PURPLE_BANNER"; break;
			case BLUE_BANNER: ger = "Blaues Banner"; eng = "BLUE_BANNER"; break;
			case BROWN_BANNER: ger = "Braunes Banner"; eng = "BROWN_BANNER"; break;
			case GREEN_BANNER: ger = "Grünes Banner"; eng = "GREEN_BANNER"; break;
			case RED_BANNER: ger = "Rotes Banner"; eng = "RED_BANNER"; break;
			case BLACK_BANNER: ger = "Schwarzes Banner"; eng = "BLACK_BANNER"; break;
			case END_CRYSTAL: ger = "Enderkristall"; eng = "END_CRYSTAL"; break;
			case CHORUS_FRUIT: ger = "Chorusfrucht"; eng = "CHORUS_FRUIT"; break;
			case POPPED_CHORUS_FRUIT: ger = "Geplatzte Chorusfrucht"; eng = "POPPED_CHORUS_FRUIT"; break;
			case BEETROOT: ger = "Rote Bete"; eng = "BEETROOT"; break;
			case BEETROOT_SEEDS: ger = "Rote-Bete-Samen"; eng = "BEETROOT_SEEDS"; break;
			case BEETROOT_SOUP: ger = "Borschtsch"; eng = "BEETROOT_SOUP"; break;
			case DRAGON_BREATH: ger = "Drachenatem"; eng = "DRAGON_BREATH"; break;
			case SPLASH_POTION: ger = "Werfbare Trank"; eng = "SPLASH_POTION"; break;
			case SPECTRAL_ARROW: ger = "Spektralpfeil"; eng = "SPECTRAL_ARROW"; break;
			case TIPPED_ARROW: ger = "Getränkter Pfeil"; eng = "TIPPED_ARROW"; break;
			case LINGERING_POTION: ger = "Verweilende Trank"; eng = "LINGERING_POTION"; break;
			case SHIELD: ger = "Schild"; eng = "SHIELD"; break;
			case TOTEM_OF_UNDYING: ger = "Totem der Unsterblichkeit"; eng = "TOTEM_OF_UNDYING"; break;
			case SHULKER_SHELL: ger = "Shulker-Schale"; eng = "SHULKER_SHELL"; break;
			case IRON_NUGGET: ger = "Eisenklumpen"; eng = "IRON_NUGGET"; break;
			case KNOWLEDGE_BOOK: ger = "Buch des Wissens"; eng = "KNOWLEDGE_BOOK"; break;
			case DEBUG_STICK: ger = "Debug-Stick"; eng = "DEBUG_STICK"; break;
			case MUSIC_DISC_13: ger = "Schallplatte C418-13"; eng = "MUSIC_DISC_13"; break;
			case MUSIC_DISC_CAT: ger = "Schallplatte C418-Cat"; eng = "MUSIC_DISC_CAT"; break;
			case MUSIC_DISC_BLOCKS: ger = "Schallplatte C418-Blocks"; eng = "MUSIC_DISC_BLOCKS"; break;
			case MUSIC_DISC_CHIRP: ger = "Schallplatte C418-Chirp"; eng = "MUSIC_DISC_CHIRP"; break;
			case MUSIC_DISC_FAR: ger = "Schallplatte C418-Far"; eng = "MUSIC_DISC_FAR"; break;
			case MUSIC_DISC_MALL: ger = "Schallplatte C418-Mall"; eng = "MUSIC_DISC_MALL"; break;
			case MUSIC_DISC_MELLOHI: ger = "Schallplatte C418-Mellohi"; eng = "MUSIC_DISC_MELLOHI"; break;
			case MUSIC_DISC_STAL: ger = "Schallplatte C418-Stal"; eng = "MUSIC_DISC_STAL"; break;
			case MUSIC_DISC_STRAD: ger = "Schallplatte C418-Strad"; eng = "MUSIC_DISC_STRAD"; break;
			case MUSIC_DISC_WARD: ger = "Schallplatte C418-Ward"; eng = "MUSIC_DISC_WARD"; break;
			case MUSIC_DISC_11: ger = "Schallplatte C418-11"; eng = "MUSIC_DISC_11"; break;
			case MUSIC_DISC_WAIT: ger = "Schallplatte C418-Wait"; eng = "MUSIC_DISC_WAIT"; break;
			case MUSIC_DISC_OTHERSIDE: ger = "Schallplatte Lena Raine Otherside"; eng = "MUSIC_DISC_OTHERSIDE"; break;
			case MUSIC_DISC_5: ger = "Schallplatte Samuel Aberg-5"; eng = "MUSIC_DISC_5"; break;
			case MUSIC_DISC_PIGSTEP: ger = "Schallplatte Lena Raine Pigstep"; eng = "MUSIC_DISC_PIGSTEP"; break;
			case DISC_FRAGMENT_5: ger = "Plattenbruchstück"; eng = "DISC_FRAGMENT_5"; break;
			case TRIDENT: ger = "Dreizack"; eng = "TRIDENT"; break;
			case PHANTOM_MEMBRANE: ger = "Phantomhaut"; eng = "PHANTOM_MEMBRANE"; break;
			case NAUTILUS_SHELL: ger = "Nautilisschale"; eng = "NAUTILUS_SHELL"; break;
			case HEART_OF_THE_SEA: ger = "Herz des Meeres"; eng = "HEART_OF_THE_SEA"; break;
			case CROSSBOW: ger = "Armbrust"; eng = "CROSSBOW"; break;
			case SUSPICIOUS_STEW: ger = "Seltsame Suppe"; eng = "SUSPICIOUS_STEW"; break;
			case LOOM: ger = "Webstuhl"; eng = "LOOM"; break;
			case FLOWER_BANNER_PATTERN: ger = "Bannervorlage Blume"; eng = "FLOWER_BANNER_PATTERN"; break;
			case CREEPER_BANNER_PATTERN: ger = "Bannervorlage Creeper"; eng = "CREEPER_BANNER_PATTERN"; break;
			case SKULL_BANNER_PATTERN: ger = "Bannervorlage Schädel"; eng = "SKULL_BANNER_PATTERN"; break;
			case MOJANG_BANNER_PATTERN: ger = "Bannervorlage Mojang-Logo"; eng = "MOJANG_BANNER_PATTERN"; break;
			case GLOBE_BANNER_PATTERN: ger = "Bannervorlage Globus"; eng = "GLOBE_BANNER_PATTERN"; break;
			case PIGLIN_BANNER_PATTERN: ger = "Bannervorlage Schnauze"; eng = "PIGLIN_BANNER_PATTERN"; break;
			case GOAT_HORN: ger = "Bockshorn"; eng = "GOAT_HORN"; break;
			case COMPOSTER: ger = "Komposter"; eng = "COMPOSTER"; break;
			case BARREL: ger = "Fass"; eng = "BARREL"; break;
			case SMOKER: ger = "Räucherofen"; eng = "SMOKER"; break;
			case BLAST_FURNACE: ger = "Schnelzofen"; eng = "BLAST_FURNACE"; break;
			case CARTOGRAPHY_TABLE: ger = "Kartentisch"; eng = "CARTOGRAPHY_TABLE"; break;
			case FLETCHING_TABLE: ger = "Bognertisch"; eng = "FLETCHING_TABLE"; break;
			case GRINDSTONE: ger = "Schleifstein"; eng = "GRINDSTONE"; break;
			case SMITHING_TABLE: ger = "Schmiedetisch"; eng = "SMITHING_TABLE"; break;
			case STONECUTTER: ger = "Steinsäge"; eng = "STONECUTTER"; break;
			case BELL: ger = "Glocke"; eng = "BELL"; break;
			case LANTERN: ger = "Laterne"; eng = "LANTERN"; break;
			case SOUL_LANTERN: ger = "Seelenlaterne"; eng = "SOUL_LANTERN"; break;
			case SWEET_BERRIES: ger = "Süßbeeren"; eng = "SWEET_BERRIES"; break;
			case GLOW_BERRIES: ger = "Leuchtbeeren"; eng = "GLOW_BERRIES"; break;
			case CAMPFIRE: ger = "Lagerfeuer"; eng = "CAMPFIRE"; break;
			case SOUL_CAMPFIRE: ger = "Seelenlagerfeuer"; eng = "SOUL_CAMPFIRE"; break;
			case SHROOMLIGHT: ger = "Pilzlicht"; eng = "SHROOMLIGHT"; break;
			case HONEYCOMB: ger = "Honigwabe"; eng = "HONEYCOMB"; break;
			case BEE_NEST: ger = "Bienennest"; eng = "BEE_NEST"; break;
			case BEEHIVE: ger = "Bienestock"; eng = "BEEHIVE"; break;
			case HONEY_BOTTLE: ger = "Honigflasche"; eng = "HONEY_BOTTLE"; break;
			case HONEYCOMB_BLOCK: ger = "Honigwabenblock"; eng = "HONEYCOMB_BLOCK"; break;
			case LODESTONE: ger = "Leitstein"; eng = "LODESTONE"; break;
			case CRYING_OBSIDIAN: ger = "Weinender Obsidian"; eng = "CRYING_OBSIDIAN"; break;
			case BLACKSTONE: ger = "Schwarzstein"; eng = "BLACKSTONE"; break;
			case BLACKSTONE_SLAB: ger = "Schwarzsteinstufe"; eng = "BLACKSTONE_SLAB"; break;
			case BLACKSTONE_STAIRS: ger = "Schwarzsteintreppe"; eng = "BLACKSTONE_STAIRS"; break;
			case GILDED_BLACKSTONE: ger = "Golddurchzogener Schwarzstein"; eng = "GILDED_BLACKSTONE"; break;
			case POLISHED_BLACKSTONE: ger = "Polierter Schwarzstein"; eng = "POLISHED_BLACKSTONE"; break;
			case POLISHED_BLACKSTONE_SLAB: ger = "Polierte Schwarzsteinstufe"; eng = "POLISHED_BLACKSTONE_SLAB"; break;
			case POLISHED_BLACKSTONE_STAIRS: ger = "Polierte Schwarzsteintreppe"; eng = "POLISHED_BLACKSTONE_STAIRS"; break;
			case CHISELED_POLISHED_BLACKSTONE: ger = "Gemeißelter polierter Schwarzstein"; eng = "CHISELED_POLISHED_BLACKSTONE"; break;
			case POLISHED_BLACKSTONE_BRICKS: ger = "Polierte Schwarzsteinziegel"; eng = "POLISHED_BLACKSTONE_BRICKS"; break;
			case POLISHED_BLACKSTONE_BRICK_SLAB: ger = "Polierte Schwarsteinziegelstufe"; eng = "POLISHED_BLACKSTONE_BRICK_SLAB"; break;
			case POLISHED_BLACKSTONE_BRICK_STAIRS: ger = "Polierte Schwarzsteinziegeltreppe"; eng = "POLISHED_BLACKSTONE_BRICK_STAIRS"; break;
			case CRACKED_POLISHED_BLACKSTONE_BRICKS: ger = "Rissige polierte Schwarzsteinziegel"; eng = "CRACKED_POLISHED_BLACKSTONE_BRICKS"; break;
			case RESPAWN_ANCHOR: ger = "Seelenanker"; eng = "RESPAWN_ANCHOR"; break;
			case CANDLE: ger = "Kerze"; eng = "CANDLE"; break;
			case WHITE_CANDLE: ger = "Weiße Kerze"; eng = "WHITE_CANDLE"; break;
			case ORANGE_CANDLE: ger = "Orange Kerze"; eng = "ORANGE_CANDLE"; break;
			case MAGENTA_CANDLE: ger = "Magenta Kerze"; eng = "MAGENTA_CANDLE"; break;
			case LIGHT_BLUE_CANDLE: ger = "Hellblaue Kerze"; eng = "LIGHT_BLUE_CANDLE"; break;
			case YELLOW_CANDLE: ger = "Gelbe Kerze"; eng = "YELLOW_CANDLE"; break;
			case LIME_CANDLE: ger = "Hellgrüne Kerze"; eng = "LIME_CANDLE"; break;
			case PINK_CANDLE: ger = "Rosa Kerze"; eng = "PINK_CANDLE"; break;
			case GRAY_CANDLE: ger = "Graue Kerze"; eng = "GRAY_CANDLE"; break;
			case LIGHT_GRAY_CANDLE: ger = "Hellgraue Kerze"; eng = "LIGHT_GRAY_CANDLE"; break;
			case CYAN_CANDLE: ger = "Türkise Kerze"; eng = "CYAN_CANDLE"; break;
			case PURPLE_CANDLE: ger = "Violette Kerze"; eng = "PURPLE_CANDLE"; break;
			case BLUE_CANDLE: ger = "Blaue Kerze"; eng = "BLUE_CANDLE"; break;
			case BROWN_CANDLE: ger = "Braune Kerze"; eng = "BROWN_CANDLE"; break;
			case GREEN_CANDLE: ger = "Grüne Kerze"; eng = "GREEN_CANDLE"; break;
			case RED_CANDLE: ger = "Rote Kerze"; eng = "RED_CANDLE"; break;
			case BLACK_CANDLE: ger = "Schwarze Kerze"; eng = "BLACK_CANDLE"; break;
			case SMALL_AMETHYST_BUD: ger = "Kleine Amethystknospe"; eng = "SMALL_AMETHYST_BUD"; break;
			case MEDIUM_AMETHYST_BUD: ger = "Mittlere Amethystknospe"; eng = "MEDIUM_AMETHYST_BUD"; break;
			case LARGE_AMETHYST_BUD: ger = "Große Amethystknospe"; eng = "LARGE_AMETHYST_BUD"; break;
			case AMETHYST_CLUSTER: ger = "Amethysthaufen"; eng = "AMETHYST_CLUSTER"; break;
			case POINTED_DRIPSTONE: ger = "Spitzer Tropfstein"; eng = "POINTED_DRIPSTONE"; break;
			case OCHRE_FROGLIGHT: ger = "Ockernes Froschlicht"; eng = "OCHRE_FROGLIGHT"; break;
			case VERDANT_FROGLIGHT: ger = "Junggrünes Froschlicht"; eng = "VERDANT_FROGLIGHT"; break;
			case PEARLESCENT_FROGLIGHT: ger = "Perlmutternes Froschlicht"; eng = "PEARLESCENT_FROGLIGHT"; break;
			case FROGSPAWN: ger = "Froschlaich"; eng = "FROGSPAWN"; break;
			case ECHO_SHARD: ger = "Echoscherbe"; eng = "ECHO_SHARD"; break;
			case WATER: ger = "Wasser"; eng = "WATER"; break;
			case LAVA: ger = "Lava"; eng = "LAVA"; break;
			case TALL_SEAGRASS: ger = "Hohes Seegras"; eng = "TALL_SEAGRASS"; break;
			case PISTON_HEAD: ger = "Kolben Kopf"; eng = "PISTON_HEAD"; break;
			case MOVING_PISTON: ger = "Verschobener Kolben"; eng = "MOVING_PISTON"; break;
			case WALL_TORCH: ger = "Wand-Fackel"; eng = "WALL_TORCH"; break;
			case FIRE: ger = "Feuer"; eng = "FIRE"; break;
			case SOUL_FIRE: ger = "Seelenfeuer"; eng = "SOUL_FIRE"; break;
			case REDSTONE_WIRE: ger = "Redstone Leitung"; eng = "REDSTONE_WIRE"; break;
			case OAK_WALL_SIGN: ger = "Eichenholzschild Wand"; eng = "OAK_WALL_SIGN"; break;
			case SPRUCE_WALL_SIGN: ger = "Fichtenholzschild Wand"; eng = "SPRUCE_WALL_SIGN"; break;
			case BIRCH_WALL_SIGN: ger = "Birkenholzschild Wand"; eng = "BIRCH_WALL_SIGN"; break;
			case ACACIA_WALL_SIGN: ger = "Akazienholzschild Wand"; eng = "ACACIA_WALL_SIGN"; break;
			case JUNGLE_WALL_SIGN: ger = "Tropenholschildd Wand"; eng = "JUNGLE_WALL_SIGN"; break;
			case DARK_OAK_WALL_SIGN: ger = "Schwarzeichenholzschild Wand"; eng = "DARK_OAK_WALL_SIGN"; break;
			case MANGROVE_WALL_SIGN: ger = "Mangrovenholzschild Wand"; eng = "MANGROVE_WALL_SIGN"; break;
			case REDSTONE_WALL_TORCH: ger = "Wand-Redstonefackel"; eng = "REDSTONE_WALL_TORCH"; break;
			case SOUL_WALL_TORCH: ger = "Wand-Seelen-Fackel"; eng = "SOUL_WALL_TORCH"; break;
			case NETHER_PORTAL: ger = "Netherportal"; eng = "NETHER_PORTAL"; break;
			case ATTACHED_PUMPKIN_STEM: ger = "Vereinter Kürbis"; eng = "ATTACHED_PUMPKIN_STEM"; break;
			case ATTACHED_MELON_STEM: ger = "Vereinte Melone"; eng = "ATTACHED_MELON_STEM"; break;
			case PUMPKIN_STEM: ger = "Kürbisstamm"; eng = "PUMPKIN_STEM"; break;
			case MELON_STEM: ger = "Melonenstamm"; eng = "MELON_STEM"; break;
			case WATER_CAULDRON: ger = "Wasserkessel"; eng = "WATER_CAULDRON"; break;
			case LAVA_CAULDRON: ger = "Lavakessel"; eng = "LAVA_CAULDRON"; break;
			case POWDER_SNOW_CAULDRON: ger = "Puderschneekessel"; eng = "POWDER_SNOW_CAULDRON"; break;
			case END_PORTAL: ger = "Endportal"; eng = "END_PORTAL"; break;
			case COCOA: ger = "Kakao"; eng = "COCOA"; break;
			case TRIPWIRE: ger = "Stolperdraht"; eng = "TRIPWIRE"; break;
			case POTTED_OAK_SAPLING: ger = "Eingetopfter Eichensetzling"; eng = "POTTED_OAK_SAPLING"; break;
			case POTTED_SPRUCE_SAPLING: ger = "Eingetopfter Fichtesetzlin"; eng = "POTTED_SPRUCE_SAPLING"; break;
			case POTTED_BIRCH_SAPLING: ger = "Eingetopfter Birkensetzling"; eng = "POTTED_BIRCH_SAPLING"; break;
			case POTTED_JUNGLE_SAPLING: ger = "Eingetopfter Tropensetzling"; eng = "POTTED_JUNGLE_SAPLING"; break;
			case POTTED_ACACIA_SAPLING: ger = "Eingetopfter Akaziensetzling"; eng = "POTTED_ACACIA_SAPLING"; break;
			case POTTED_DARK_OAK_SAPLING: ger = "Eingetopfter Schwarzeichensetzling"; eng = "POTTED_DARK_OAK_SAPLING"; break;
			case POTTED_MANGROVE_PROPAGULE: ger = "Eingetopfter Mangrovensetzling"; eng = "POTTED_MANGROVE_PROPAGULE"; break;
			case POTTED_FERN: ger = "Eingetopfter Farn"; eng = "POTTED_FERN"; break;
			case POTTED_DANDELION: ger = "Eingetopfter Löwenzahn"; eng = "POTTED_DANDELION"; break;
			case POTTED_POPPY: ger = "Eingetopfter Mohn"; eng = "POTTED_POPPY"; break;
			case POTTED_BLUE_ORCHID: ger = "Eingetopfte Orichide"; eng = "POTTED_BLUE_ORCHID"; break;
			case POTTED_ALLIUM: ger = "Eingetopfter Zierlauch"; eng = "POTTED_ALLIUM"; break;
			case POTTED_AZURE_BLUET: ger = "Eingetopfter Porzelansternchen"; eng = "POTTED_AZURE_BLUET"; break;
			case POTTED_RED_TULIP: ger = "Eingetopfte rote Tulpe"; eng = "POTTED_RED_TULIP"; break;
			case POTTED_ORANGE_TULIP: ger = "Eingetopfte orange Tulpe"; eng = "POTTED_ORANGE_TULIP"; break;
			case POTTED_WHITE_TULIP: ger = "Eingetopfte weiße Tulpe"; eng = "POTTED_WHITE_TULIP"; break;
			case POTTED_PINK_TULIP: ger = "Eingetopfte rosa Tulpe"; eng = "POTTED_PINK_TULIP"; break;
			case POTTED_OXEYE_DAISY: ger = "Eingetopfte Margerite"; eng = "POTTED_OXEYE_DAISY"; break;
			case POTTED_CORNFLOWER: ger = "Eingetopfte Kornblume"; eng = "POTTED_CORNFLOWER"; break;
			case POTTED_LILY_OF_THE_VALLEY: ger = "Eingetopftes Maiglöckchen"; eng = "POTTED_LILY_OF_THE_VALLEY"; break;
			case POTTED_WITHER_ROSE: ger = "Eingetopfte Witherrose"; eng = "POTTED_WITHER_ROSE"; break;
			case POTTED_RED_MUSHROOM: ger = "Eingetopfter roter Pilz"; eng = "POTTED_RED_MUSHROOM"; break;
			case POTTED_BROWN_MUSHROOM: ger = "Eingetopfter brauner Pilz"; eng = "POTTED_BROWN_MUSHROOM"; break;
			case POTTED_DEAD_BUSH: ger = "Eingetopfter toter Busch"; eng = "POTTED_DEAD_BUSH"; break;
			case POTTED_CACTUS: ger = "Eingetopfter Kaktus"; eng = "POTTED_CACTUS"; break;
			case CARROTS: ger = "Karottem"; eng = "CARROTS"; break;
			case POTATOES: ger = "Kartoffeln"; eng = "POTATOES"; break;
			case SKELETON_WALL_SKULL: ger = "Skelettkopf Wand"; eng = "SKELETON_WALL_SKULL"; break;
			case WITHER_SKELETON_WALL_SKULL: ger = "Whiterskelettkopf Wand"; eng = "WITHER_SKELETON_WALL_SKULL"; break;
			case ZOMBIE_WALL_HEAD: ger = "Zombiekopf Wand"; eng = "ZOMBIE_WALL_HEAD"; break;
			case PLAYER_WALL_HEAD: ger = "Spielerkof Wand"; eng = "PLAYER_WALL_HEAD"; break;
			case CREEPER_WALL_HEAD: ger = "Creeperkopf Wand"; eng = "CREEPER_WALL_HEAD"; break;
			case DRAGON_WALL_HEAD: ger = "Drachenkopf Wand"; eng = "DRAGON_WALL_HEAD"; break;
			case WHITE_WALL_BANNER: ger = "Weißes Banner Wand"; eng = "WHITE_WALL_BANNER"; break;
			case ORANGE_WALL_BANNER: ger = "Oranges banner Wand"; eng = "ORANGE_WALL_BANNER"; break;
			case MAGENTA_WALL_BANNER: ger = "Magenta Banner Wand"; eng = "MAGENTA_WALL_BANNER"; break;
			case LIGHT_BLUE_WALL_BANNER: ger = "Hellblaues Banner Wand"; eng = "LIGHT_BLUE_WALL_BANNER"; break;
			case YELLOW_WALL_BANNER: ger = "Gelbes Banner Wand"; eng = "YELLOW_WALL_BANNER"; break;
			case LIME_WALL_BANNER: ger = "Hellgrünes banner Wand"; eng = "LIME_WALL_BANNER"; break;
			case PINK_WALL_BANNER: ger = "Rosa Banner Wand"; eng = "PINK_WALL_BANNER"; break;
			case GRAY_WALL_BANNER: ger = "Graues banner Wand"; eng = "GRAY_WALL_BANNER"; break;
			case LIGHT_GRAY_WALL_BANNER: ger = "Hellgraues Banner Wand"; eng = "LIGHT_GRAY_WALL_BANNER"; break;
			case CYAN_WALL_BANNER: ger = "Türkises banner Wand"; eng = "CYAN_WALL_BANNER"; break;
			case PURPLE_WALL_BANNER: ger = "Violettes Banner Wand"; eng = "PURPLE_WALL_BANNER"; break;
			case BLUE_WALL_BANNER: ger = "Blaues Banner Wand"; eng = "BLUE_WALL_BANNER"; break;
			case BROWN_WALL_BANNER: ger = "Braunes Banner Wand"; eng = "BROWN_WALL_BANNER"; break;
			case GREEN_WALL_BANNER: ger = "Grünes banner Wand"; eng = "GREEN_WALL_BANNER"; break;
			case RED_WALL_BANNER: ger = "Rotes banner Wand"; eng = "RED_WALL_BANNER"; break;
			case BLACK_WALL_BANNER: ger = "Schwarzes Banner Wand"; eng = "BLACK_WALL_BANNER"; break;
			case BEETROOTS: ger = "Rote Beete Samen"; eng = "BEETROOTS"; break;
			case END_GATEWAY: ger = "End Gateway"; eng = "END_GATEWAY"; break;
			case FROSTED_ICE: ger = "Gefrorenes Eis"; eng = "FROSTED_ICE"; break;
			case KELP_PLANT: ger = "Seetang Pflanze"; eng = "KELP_PLANT"; break;
			case DEAD_TUBE_CORAL_WALL_FAN: ger = "Totes Orgelkorallenfächer Wand"; eng = "DEAD_TUBE_CORAL_WALL_FAN"; break;
			case DEAD_BRAIN_CORAL_WALL_FAN: ger = "Totes Hirnkorallenfächer Wand"; eng = "DEAD_BRAIN_CORAL_WALL_FAN"; break;
			case DEAD_BUBBLE_CORAL_WALL_FAN: ger = "Totes Blasenkorallenfächer Wand"; eng = "DEAD_BUBBLE_CORAL_WALL_FAN"; break;
			case DEAD_FIRE_CORAL_WALL_FAN: ger = "Totes Feuerkorallenfächer Wand"; eng = "DEAD_FIRE_CORAL_WALL_FAN"; break;
			case DEAD_HORN_CORAL_WALL_FAN: ger = "Totes Geweihkorallenfächer Wand"; eng = "DEAD_HORN_CORAL_WALL_FAN"; break;
			case TUBE_CORAL_WALL_FAN: ger = "Tote Orgelkorallen Wand"; eng = "TUBE_CORAL_WALL_FAN"; break;
			case BRAIN_CORAL_WALL_FAN: ger = "Tote Hirnkoralle Wand"; eng = "BRAIN_CORAL_WALL_FAN"; break;
			case BUBBLE_CORAL_WALL_FAN: ger = "Tote Blasenkoralle Wand"; eng = "BUBBLE_CORAL_WALL_FAN"; break;
			case FIRE_CORAL_WALL_FAN: ger = "Tote Feuerkoralle Wand"; eng = "FIRE_CORAL_WALL_FAN"; break;
			case HORN_CORAL_WALL_FAN: ger = "Tote Geweihkoralle Wand"; eng = "HORN_CORAL_WALL_FAN"; break;
			case BAMBOO_SAPLING: ger = "Bambussetzling"; eng = "BAMBOO_SAPLING"; break;
			case POTTED_BAMBOO: ger = "Eingetopfter Bambussetzling"; eng = "POTTED_BAMBOO"; break;
			case VOID_AIR: ger = "Leerenluft"; eng = "VOID_AIR"; break;
			case CAVE_AIR: ger = "Höhlenluft"; eng = "CAVE_AIR"; break;
			case BUBBLE_COLUMN: ger = "Blasensäule"; eng = "BUBBLE_COLUMN"; break;
			case SWEET_BERRY_BUSH: ger = "Süßbeerenbusch"; eng = "SWEET_BERRY_BUSH"; break;
			case WEEPING_VINES_PLANT: ger = "Gepflanzte Trauerranken"; eng = "WEEPING_VINES_PLANT"; break;
			case TWISTING_VINES_PLANT: ger = "Gepflanzte Trauerranken"; eng = "TWISTING_VINES_PLANT"; break;
			case CRIMSON_WALL_SIGN: ger = "Karmesinschild Wand"; eng = "CRIMSON_WALL_SIGN"; break;
			case WARPED_WALL_SIGN: ger = "Wirrschild Wand"; eng = "WARPED_WALL_SIGN"; break;
			case POTTED_CRIMSON_FUNGUS: ger = "Eingetopfter Karmesinsetzling"; eng = "POTTED_CRIMSON_FUNGUS"; break;
			case POTTED_WARPED_FUNGUS: ger = "Eingetopfter Wirrsetzling"; eng = "POTTED_WARPED_FUNGUS"; break;
			case POTTED_CRIMSON_ROOTS: ger = "Eingetopfte Karmesinwurzeln"; eng = "POTTED_CRIMSON_ROOTS"; break;
			case POTTED_WARPED_ROOTS: ger = "Eingetopfte Wirrwurzeln"; eng = "POTTED_WARPED_ROOTS"; break;
			case CANDLE_CAKE: ger = "Kuchenkerze"; eng = "CANDLE_CAKE"; break;
			case WHITE_CANDLE_CAKE: ger = "Weiße Kuchenkerze"; eng = "WHITE_CANDLE_CAKE"; break;
			case ORANGE_CANDLE_CAKE: ger = "Orange Kuchenkerze"; eng = "ORANGE_CANDLE_CAKE"; break;
			case MAGENTA_CANDLE_CAKE: ger = "Magenta Kuchenkerze"; eng = "MAGENTA_CANDLE_CAKE"; break;
			case LIGHT_BLUE_CANDLE_CAKE: ger = "Hellbalue Kuchenkerze"; eng = "LIGHT_BLUE_CANDLE_CAKE"; break;
			case YELLOW_CANDLE_CAKE: ger = "Gelbe Kuchenkerze"; eng = "YELLOW_CANDLE_CAKE"; break;
			case LIME_CANDLE_CAKE: ger = "Hellgrüne Kuchenkerze"; eng = "LIME_CANDLE_CAKE"; break;
			case PINK_CANDLE_CAKE: ger = "Rosa Kuchenkerze"; eng = "PINK_CANDLE_CAKE"; break;
			case GRAY_CANDLE_CAKE: ger = "Graue Kuchenkerze"; eng = "GRAY_CANDLE_CAKE"; break;
			case LIGHT_GRAY_CANDLE_CAKE: ger = "Hellgraue Kuchenkerze"; eng = "LIGHT_GRAY_CANDLE_CAKE"; break;
			case CYAN_CANDLE_CAKE: ger = "Türkise Kuchenkerze"; eng = "CYAN_CANDLE_CAKE"; break;
			case PURPLE_CANDLE_CAKE: ger = "Violette Kuchenkerze"; eng = "PURPLE_CANDLE_CAKE"; break;
			case BLUE_CANDLE_CAKE: ger = "Blaue Kuchenkerze"; eng = "BLUE_CANDLE_CAKE"; break;
			case BROWN_CANDLE_CAKE: ger = "Braune Kuchenkerze"; eng = "BROWN_CANDLE_CAKE"; break;
			case GREEN_CANDLE_CAKE: ger = "Grüne Kuchenkerze"; eng = "GREEN_CANDLE_CAKE"; break;
			case RED_CANDLE_CAKE: ger = "Rote Kuchenkerze"; eng = "RED_CANDLE_CAKE"; break;
			case BLACK_CANDLE_CAKE: ger = "Schwarze Kuchenkerze"; eng = "BLACK_CANDLE_CAKE"; break;
			case POWDER_SNOW: ger = "Pulverschnee"; eng = "POWDER_SNOW"; break;
			case CAVE_VINES: ger = "Höhlenranken"; eng = "CAVE_VINES"; break;
			case CAVE_VINES_PLANT: ger = "Höhlenranken gepflanzt"; eng = "CAVE_VINES_PLANT"; break;
			case BIG_DRIPLEAF_STEM: ger = "Großes Tropfsteinblatt-Stamm"; eng = "BIG_DRIPLEAF_STEM"; break;
			case POTTED_AZALEA_BUSH: ger = "Eingetopftes Azalenlaub"; eng = "POTTED_AZALEA_BUSH"; break;
			case POTTED_FLOWERING_AZALEA_BUSH: ger = "Eingetopftes blühendes Azalenlaub"; eng = "POTTED_FLOWERING_AZALEA_BUSH"; break;
			case CHERRY_PLANKS: ger = "Kirschholzbretter"; break;
			case BAMBOO_PLANKS: ger = "Bambusbretter"; break;
			case BAMBOO_MOSAIC: ger = "Bambusmosaik"; break;
			case CHERRY_SAPLING: ger = "Kirschsetzling"; break;
			case SUSPICIOUS_SAND: ger = "Seltsamer Sand"; break;
			case SUSPICIOUS_GRAVEL: ger = "Seltsamer Kies"; break;
			case CHERRY_LOG: ger = "Kirschstamm"; break;
			case BAMBOO_BLOCK: ger = "Bambusblock"; break;
			case STRIPPED_CHERRY_LOG: ger = "Entrindeter Kirschstamm"; break;
			case STRIPPED_CHERRY_WOOD: ger = "Entrindetes Kirschholz"; break;
			case STRIPPED_BAMBOO_BLOCK: ger = "Geschälter Bambusblock"; break;
			case CHERRY_WOOD: ger = "Kirschholz"; break;
			case CHERRY_LEAVES: ger = "Kirschlaub"; break;
			case TORCHFLOWER: ger = "Fackellilie"; break;
			case PITCHER_PLANT: ger = "Kannenpflanze"; break;
			case PINK_PETALS: ger = "Rosa Blütenblätter"; break;
			case CHERRY_SLAB: ger = "Kirschholzstufe"; break;
			case BAMBOO_SLAB: ger = "Bambusstufe"; break;
			case BAMBOO_MOSAIC_SLAB: ger = "Bambusmosaikstufe"; break;
			case CHISELED_BOOKSHELF: ger = "Gearbeitetes Bücherregal"; break;
			case DECORATED_POT: ger = "Verzierter Krug"; break;
			case CHERRY_FENCE: ger = "Kirschholzzaun"; break;
			case BAMBOO_FENCE: ger = "Bambuszaun"; break;
			case CHERRY_STAIRS: ger = "Kirschholztreppe"; break;
			case BAMBOO_STAIRS: ger = "Bambustreppe"; break;
			case BAMBOO_MOSAIC_STAIRS: ger = "Bambusmosaiktreppe"; break;
			case SNIFFER_EGG: ger = "Schnüffler-Ei"; break;
			case CALIBRATED_SCULK_SENSOR: ger = "Kalibrierter Sculk-Sensor"; break;
			case CHERRY_BUTTON: ger = "Kirschholzknopf"; break;
			case BAMBOO_BUTTON: ger = "Bambusknopf"; break;
			case CHERRY_PRESSURE_PLATE: ger = "Kirschholzdruckplatte"; break;
			case BAMBOO_PRESSURE_PLATE: ger = "Bambusdruckplatte"; break;
			case CHERRY_DOOR: ger = "Kirschholztür"; break;
			case BAMBOO_DOOR: ger = "Bambustür"; break;
			case CHERRY_TRAPDOOR: ger = "Kirschholzfalltür"; break;
			case BAMBOO_TRAPDOOR: ger = "Bambusfalltür"; break;
			case CHERRY_FENCE_GATE: ger = "Kirschholzzauntor"; break;
			case BAMBOO_FENCE_GATE: ger = "Bambuszauntor"; break;
			case CHERRY_BOAT: ger = "Kirschholzboot"; break;
			case CHERRY_CHEST_BOAT: ger = "Kirschholztruhenboot"; break;
			case BAMBOO_RAFT: ger = "Bambusfloß"; break;
			case BAMBOO_CHEST_RAFT: ger = "Bambus-Truhenfloß"; break;
			case CHERRY_SIGN: ger = "Kirschholzschild"; break;
			case BAMBOO_SIGN: ger = "Bambusschild"; break;
			case OAK_HANGING_SIGN: ger = "Eichenholzhängeschild"; break;
			case SPRUCE_HANGING_SIGN: ger = "Fichtenholzhängeschild"; break;
			case BIRCH_HANGING_SIGN: ger = "Birkenholzhängeschild"; break;
			case JUNGLE_HANGING_SIGN: ger = "Tropenholzhängeschild"; break;
			case ACACIA_HANGING_SIGN: ger = "Akazienholzhängeschild"; break;
			case CHERRY_HANGING_SIGN: ger = "Kirschholzhängeschild"; break;
			case DARK_OAK_HANGING_SIGN: ger = "Schwarzeichenholzhängeschild"; break;
			case MANGROVE_HANGING_SIGN: ger = "Mangrovenholzhängeschild"; break;
			case BAMBOO_HANGING_SIGN: ger = "Bambushängeschild"; break;
			case CRIMSON_HANGING_SIGN: ger = "Karmesinhängeschild"; break;
			case WARPED_HANGING_SIGN: ger = "Wirrhängeschild"; break;
			case CAMEL_SPAWN_EGG: ger = "Dromedar-Spawn-Ei"; break;
			case ENDER_DRAGON_SPAWN_EGG: ger = "Drachen-Spawn-Ei"; break;
			case IRON_GOLEM_SPAWN_EGG: ger = "Eisengolem-Spawn-Ei"; break;
			case SNIFFER_SPAWN_EGG: ger = "Schnüffler-Spawn-Ei"; break;
			case SNOW_GOLEM_SPAWN_EGG: ger = "Schneegolem-Spawn-Ei"; break;
			case WITHER_SPAWN_EGG: ger = "Whiter-Spawn-Ei"; break;
			case PIGLIN_HEAD: ger = "Piglingkopf"; break;
			case TORCHFLOWER_SEEDS: ger = "Fackelliliensamen"; break;
			case PITCHER_POD: ger = "Kannenpflanzenkapsel"; break;
			case MUSIC_DISC_RELIC: ger = "Schallplatte Aaron Cherof - Relic"; break;
			case BRUSH: ger = "Pinsel"; break;
			case NETHERITE_UPGRADE_SMITHING_TEMPLATE: ger = "Schmiedevorlage Netheritaufwertung"; break;
			case SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Wachen-Rüstungsbesatz"; break;
			case DUNE_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Dünen-Rüstungsbesatz"; break;
			case COAST_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Küsten-Rüstungsbesatz"; break;
			case WILD_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Wildnis-Rüstungsbesatz"; break;
			case WARD_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Warthof-Rüstungsbesatz"; break;
			case EYE_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Augen-Rüstungsbesatz"; break;
			case VEX_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Plagegeister-Rüstungsbesatz"; break;
			case TIDE_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Gezeiten-Rüstungsbesatz"; break;
			case SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Schnauzen-Rüstungsbesatz"; break;
			case RIB_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Rippen-Rüstungsbesatz"; break;
			case SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Turmspitzen-Rüstungsbesatz"; break;
			case WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Wegfinder-Rüstungsbesatz"; break;
			case SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Gestalter-Rüstungsbesatz"; break;
			case SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Stille-Rüstungsbesatz"; break;
			case RAISER_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Aufzieher-Rüstungsbesatz"; break;
			case HOST_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Gastwirts-Rüstungsbesatz"; break;
			case ANGLER_POTTERY_SHERD: ger = "Angler-Töpferscherbe"; break;
			case ARCHER_POTTERY_SHERD: ger = "Schützen-Töpferscherbe"; break;
			case ARMS_UP_POTTERY_SHERD: ger = "Gebärden-Töpferscherbe"; break;
			case BLADE_POTTERY_SHERD: ger = "Klingen-Töpferscherbe"; break;
			case BREWER_POTTERY_SHERD: ger = "Brauer-Töpferscherbe"; break;
			case BURN_POTTERY_SHERD: ger = "Flammen-Töpferscherbe"; break;
			case DANGER_POTTERY_SHERD: ger = "Gefahren-Töpferscherbe"; break;
			case EXPLORER_POTTERY_SHERD: ger = "Entdecker-Töpferscherbe"; break;
			case FRIEND_POTTERY_SHERD: ger = "Freundes-Töpferscherbe"; break;
			case HEART_POTTERY_SHERD: ger = "Herz-Töpferscherbe"; break;
			case HEARTBREAK_POTTERY_SHERD: ger = "Herzschmerz-Töpferscherbe"; break;
			case HOWL_POTTERY_SHERD: ger = "Geheul-Töpferscherbe"; break;
			case MINER_POTTERY_SHERD: ger = "Bergarbeiter-Töpferscherbe"; break;
			case MOURNER_POTTERY_SHERD: ger = "Wimmerer-Töpferscherbe"; break;
			case PLENTY_POTTERY_SHERD: ger = "Reichtums-Töpferscherbe"; break;
			case PRIZE_POTTERY_SHERD: ger = "Juwelen-Töpferscherbe"; break;
			case SHEAF_POTTERY_SHERD: ger = "Garben-Töpferscherbe"; break;
			case SHELTER_POTTERY_SHERD: ger = "Zufluchts-Töpferscherbe"; break;
			case SKULL_POTTERY_SHERD: ger = "Totenkopf-Töpferscherbe"; break;
			case SNORT_POTTERY_SHERD: ger = "Schnaub-Töpferscherbe"; break;
			case CHERRY_WALL_SIGN: ger = "Kirschholz Wandschild"; break;
			case BAMBOO_WALL_SIGN: ger = "Bambus Wandschild"; break;
			case OAK_WALL_HANGING_SIGN: ger = "Eichenholz Wandhängeschild"; break;
			case SPRUCE_WALL_HANGING_SIGN: ger = "Fichtenholz Wandhängeschild"; break;
			case BIRCH_WALL_HANGING_SIGN: ger = "Birkenholz Wandhängeschild"; break;
			case ACACIA_WALL_HANGING_SIGN: ger = "Akazienholz Wandhängeschild"; break;
			case CHERRY_WALL_HANGING_SIGN: ger = "Kirschholz Wandhängeschild"; break;
			case JUNGLE_WALL_HANGING_SIGN: ger = "Tropenholz Wandhängeschild"; break;
			case DARK_OAK_WALL_HANGING_SIGN: ger = "Schwarzeichen Wandhängeschild"; break;
			case MANGROVE_WALL_HANGING_SIGN: ger = "Mangrovenholz Wandhängeschild"; break;
			case CRIMSON_WALL_HANGING_SIGN: ger = "Karmesin Wandhängeschild"; break;
			case WARPED_WALL_HANGING_SIGN: ger = "Wirr Wandhängeschild"; break;
			case BAMBOO_WALL_HANGING_SIGN: ger = "Bambus Wandhängeschild"; break;
			case POTTED_TORCHFLOWER: ger = "Fackelblume im Blumentopf"; break;
			case POTTED_CHERRY_SAPLING: ger = "Kirschsetling im Blumentopf"; break;
			case PIGLIN_WALL_HEAD: ger = "Piglinkopf"; break;
			case TORCHFLOWER_CROP: ger = "Fackelblumenernte"; break;
			case PITCHER_CROP: ger = "Kannenpflanzenernte"; break;
			//Below 1.20.4
			case BREEZE_SPAWN_EGG: ger = "Breezespawnei"; break;
			case CHISELED_COPPER: ger = "Bearbeiteter Kupferblock"; break;
			case CHISELED_TUFF: ger = "Bearbeiteter Tuffstein"; break;
			case CHISELED_TUFF_BRICKS: ger = "Bearbeiteter Tuffziegel"; break;
			case COPPER_BULB: ger = "Kupferbirne"; break;
			case COPPER_DOOR: ger = "Kupfertür"; break;
			case COPPER_GRATE: ger = "Kupferrost"; break;
			case COPPER_TRAPDOOR: ger = "Kupferfalltür"; break;
			case CRAFTER: ger = "Crafter"; break;
			case EXPOSED_CHISELED_COPPER: ger = "Angelaufener bearbeiteter Kupferblock"; break;
			case EXPOSED_COPPER_BULB: ger = "Angelaufene Kupferbirne"; break;
			case EXPOSED_COPPER_DOOR: ger = "Angelaufener bearbeitete Kupfertüre"; break;
			case EXPOSED_COPPER_GRATE: ger = "Angelaufener bearbeiteter Kupferrost"; break;
			case EXPOSED_COPPER_TRAPDOOR: ger = "Angelaufener bearbeiteter "; break;
			case OXIDIZED_CHISELED_COPPER: ger = "Oxidierter bearbeiteter Kupferblock"; break;
			case OXIDIZED_COPPER_BULB: ger = "Oxidierte Kupferbirne"; break;
			case OXIDIZED_COPPER_DOOR: ger = "Oxidierte Kupfertüre"; break;
			case OXIDIZED_COPPER_GRATE: ger = "Oxidierte Kupferrost"; break;
			case OXIDIZED_COPPER_TRAPDOOR: ger = "Oxidierte Kupferfalltüre"; break;
			case POLISHED_TUFF: ger = "Polierter Tuffstein"; break;
			case POLISHED_TUFF_SLAB: ger = "Polierte Tuffsteinstufe"; break;
			case POLISHED_TUFF_STAIRS: ger = "Polierte Tuffsteintreppe"; break;
			case POLISHED_TUFF_WALL: ger = "Polierte Tuffsteinmauer"; break;
			case TRIAL_KEY: ger = "Trialschlüssel"; break;
			case TRIAL_SPAWNER: ger = "Trialspawner"; break;
			case TUFF_BRICK_SLAB: ger = "Tuffziegelstufe"; break;
			case TUFF_BRICK_STAIRS: ger = "Tuffziegeltreppe"; break;
			case TUFF_BRICK_WALL: ger = "Tuffziegelmauer"; break;
			case TUFF_BRICKS: ger = "Tuffziegel"; break;
			case TUFF_SLAB: ger = "Tuffsteinstufe"; break;
			case TUFF_STAIRS: ger = "Tuffsteintreppe"; break;
			case TUFF_WALL: ger = "Tuffsteinmauer"; break;
			case WAXED_CHISELED_COPPER: ger = "Gewachster bearbeiteter Kupferblock"; break;
			case WAXED_COPPER_BULB: ger = "Gewachste Kupferbirne"; break;
			case WAXED_COPPER_DOOR: ger = "Gewachste Kupfertür"; break;
			case WAXED_COPPER_GRATE: ger = "Gewachster Kupferrost"; break;
			case WAXED_COPPER_TRAPDOOR: ger = "Gewachste Kupferfalltüre"; break;
			case WAXED_EXPOSED_CHISELED_COPPER: ger = "Gewachster angelaufener bearbeiteter Kupferblock"; break;
			case WAXED_EXPOSED_COPPER_BULB: ger = "Gewachste angelaufene Kupferbirne"; break;
			case WAXED_EXPOSED_COPPER_DOOR: ger = "Gewachste angelaufene Kupfertür"; break;
			case WAXED_EXPOSED_COPPER_GRATE: ger = "Gewachste angelaufene Kupferrost"; break;
			case WAXED_EXPOSED_COPPER_TRAPDOOR: ger = "Gewachste angelaufene Kupferfalltüre"; break;
			case WAXED_OXIDIZED_CHISELED_COPPER: ger = "Gewachster oxidierter bearbeiteter Kupferblock"; break;
			case WAXED_OXIDIZED_COPPER_BULB: ger = "Gewachste oxidierte Kupferbirne"; break;
			case WAXED_OXIDIZED_COPPER_DOOR: ger = "Gewachste oxidierte Kupfertüre"; break;
			case WAXED_OXIDIZED_COPPER_GRATE: ger = "Gewachste oxidierte Kupferrost"; break;
			case WAXED_OXIDIZED_COPPER_TRAPDOOR: ger = "Gewachste oxidierte Kupferfalltüre"; break;
			case WAXED_WEATHERED_CHISELED_COPPER: ger = "Gewachster verwitterter bearbeiteter Kupferblock"; break;
			case WAXED_WEATHERED_COPPER_BULB: ger = "Gewachste verwitterte Kupferbirne"; break;
			case WAXED_WEATHERED_COPPER_DOOR: ger = "Gewachste verwitterte Kupfertüre"; break;
			case WAXED_WEATHERED_COPPER_GRATE: ger = "Gewachste verwitterter Kupferrost"; break;
			case WAXED_WEATHERED_COPPER_TRAPDOOR: ger = "Gewachste verwitterte Kupferfalltüre"; break;
			case WEATHERED_CHISELED_COPPER: ger = "Verwitterter bearbeiteter Kupferblock"; break;
			case WEATHERED_COPPER_BULB: ger = "Verwitterte Kupferbirne"; break;
			case WEATHERED_COPPER_DOOR: ger = "Verwitterte Kupfertüre"; break;
			case WEATHERED_COPPER_GRATE: ger = "Verwitterter Kupferrost"; break;
			case WEATHERED_COPPER_TRAPDOOR: ger = "Verwitterte Kupferfalltüre"; break;
			//Below 1.21
			case ARMADILLO_SCUTE: ger = "Armadillohornschild"; break;
			case ARMADILLO_SPAWN_EGG: ger = "Armadillospawnei"; break;
			case BOGGED_SPAWN_EGG: ger = "Sumpfskelettspawnei"; break;
			case BOLT_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Bolzen"; break;
			case BREEZE_ROD: ger = "Böerute"; break;
			case FLOW_ARMOR_TRIM_SMITHING_TEMPLATE: ger = "Schmiedevorlage Spirale"; break;
			case FLOW_BANNER_PATTERN: ger = "Bannervorlage Spirale"; break;
			case FLOW_POTTERY_SHERD: ger = "Spiral-Töpferscherbe"; break;
			case GUSTER_BANNER_PATTERN: ger = "Bannervorlage Wither"; break;
			case GUSTER_POTTERY_SHERD: ger = "Wither-Töpferscherb"; break;
			case HEAVY_CORE: ger = "Schwerer Kern"; break;
			case MACE: ger = "Streitkolben"; break;
			case MUSIC_DISC_CREATOR: ger = "Schallplatte C418-Erschaffer"; break;
			case MUSIC_DISC_CREATOR_MUSIC_BOX: ger = "Schallplatte C418-ErschafferMusikbox"; break;
			case MUSIC_DISC_PRECIPICE: ger = "Schallplatte C418-Abgrund"; break;
			case OMINOUS_BOTTLE: ger = "Ominöse Flasche"; break;
			case OMINOUS_TRIAL_KEY: ger = "Ominöser Trialschlüssel"; break;
			case SCRAPE_POTTERY_SHERD: ger = "Kratzen-Töpferscherb"; break;
			case VAULT: ger = "Tresor"; break;
			case WIND_CHARGE: ger = "Windkugel"; break;
			case WOLF_ARMOR: ger = "Wolfrüstung"; break;
			}
			matlanguageKeys.put(m.toString(),
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger,
							eng}));
		}
	}
	
	public void initEnchantmentLanguage() //INFO:EnchantmentLanguages
	{		
		for(org.bukkit.enchantments.Enchantment e : org.bukkit.Registry.ENCHANTMENT.stream().collect(Collectors.toList()))
		{
			String ger = e.getKey().getKey();
			String eng = e.getKey().getKey();
			switch(e.getKey().getKey())
			{
			case "respiration": ger = "Atmung"; break;
			case "smite": ger = "Bann"; break;
			case "silk_touch": ger = "Behutsamkeit"; break;
			case "piercing": ger = "Durchschuss"; break;
			case "thorns": ger = "Dornen"; break;
			case "efficiency": ger = "Effizienz"; break;
			case "frost_walker": ger = "Eisläufer"; break;
			case "channeling": ger = "Entladung"; break;
			case "blast_protection": ger = "Explosionsschutz"; break;
			case "feather_falling": ger = "Federfall"; break;
			case "fire_protection": ger = "Feuerschutz"; break;
			case "flame": ger = "Flame"; break;
			case "binding_curse": ger = "Fluch der Bindung"; break;
			case "vanishing_curse": ger = "Fluch des Verschwindens"; break;
			case "fortune": ger = "Glück"; break;
			case "luck_of_the_sea": ger = "Glück des Meeres"; break;
			case "unbreaking": ger = "Haltbarkeit"; break;
			case "impaling": ger = "Harpune"; break;
			case "swift_sneak": ger = "Huschen"; break;
			case "lure": ger = "Köder"; break;
			case "multishot": ger = "Mehrfachschuss"; break;
			case "bane_of_arthropods": ger = "Nemesis der Gliederfüßer"; break;
			case "looting": ger = "Plünderung"; break;
			case "mending": ger = "Reparatur"; break;
			case "knockback": ger = "Rückstoß"; break;
			case "sharpness": ger = "Schärfe"; break;
			case "punch": ger = "Schlag"; break;
			case "quick_charge": ger = "Schnellladen"; break;
			case "projectile_protection": ger = "Schusssicher"; break;
			case "protection": ger = "Schutz"; break;
			case "sweeping": ger = "Schwungkraft"; break;
			case "soul_speed": ger = "Seelenläufer"; break;
			case "riptide": ger = "Sog"; break;
			case "cleaving": ger = "Spaltung"; break;
			case "power": ger = "Stärke"; break;
			case "loyalty": ger = "Treue"; break;
			case "infinity": ger = "Unendlichkeit"; break;
			case "fire_aspect": ger = "Verbrennung"; break;
			case "aqua_affinity": ger = "Wasseraffinität"; break;
			case "depth_strider": ger = "Wafferläufer"; break;
			}
			enchlanguageKeys.put(e.getKey().getKey(),
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger, eng}));
		}
	}
	
	public void initBannerLanguage() //INFO:BannerLanguages
	{
		for(org.bukkit.DyeColor dc : org.bukkit.DyeColor.values())
		{
			for(org.bukkit.block.banner.PatternType p : org.bukkit.block.banner.PatternType.values())
			{
				String eng = "";
				String ger = "";
				switch(dc)
				{
				case BLACK:
					switch(p)
					{
					case BASE: ger = "Schwarzes Banner"; eng = "BLACK_BASE"; break;
					case SQUARE_BOTTOM_LEFT: ger = "Schwarzes rechtes Untereck"; eng = "BLACK_SQUARE_BOTTOM_LEFT"; break;
					case SQUARE_BOTTOM_RIGHT: ger = "Schwarzes linkes Untereck"; eng = "BLACK_SQUARE_BOTTOM_RIGHT"; break;
					case SQUARE_TOP_LEFT: ger = "Schwarzes rechtes Obereck"; eng = "BLACK_SQUARE_TOP_LEFT"; break;
					case SQUARE_TOP_RIGHT: ger = "Schwarzes linkes Obereck"; eng = "BLACK_SQUARE_TOP_RIGHT"; break;
					case STRIPE_BOTTOM: ger = "Schwazer Bannerfuß"; eng = "BLACK_STRIPE_BOTTOM"; break;
					case STRIPE_TOP: ger = "Schwarzes Bannerhaupt"; eng = "BLACK_STRIPE_TOP"; break;
					case STRIPE_LEFT: ger = "Schwarze rechte Flanke"; eng = "BLACK_STRIPE_LEFT"; break;
					case STRIPE_RIGHT: ger = "Schwarze linke Flanke"; eng = "BLACK_STRIPE_RIGHT"; break;
					case STRIPE_CENTER: ger = "Schwazer Pfahl"; eng = "BLACK_STRIPE_CENTER"; break;
					case STRIPE_MIDDLE: ger = "Schwazer Balken"; eng = "BLACK_STRIPE_MIDDLE"; break;
					case STRIPE_DOWNRIGHT: ger = "Schwazer Schrägbalken"; eng = "BLACK_STRIPE_DOWNRIGHT"; break;
					case STRIPE_DOWNLEFT: ger = "Schwazer Schräglinksbalken"; eng = "BLACK_STRIPE_DOWNLEFT"; break;
					case SMALL_STRIPES: ger = "Vier schwarze Pfähle"; eng = "BLACK_STRIPE_SMALL"; break;
					case CROSS: ger = "Schwarzes Andreaskreuz"; eng = "BLACK_CROSS"; break;
					case STRAIGHT_CROSS: ger = "Schwarzes Kreuz"; eng = "BLACK_STRAIGHT_CROSS"; break;
					case TRIANGLE_BOTTOM: ger = "Schwarze halbe Spitze"; eng = "case TRIANGLE_BOTTOM"; break;
					case TRIANGLE_TOP: ger = "Schwarze gestürzte halbe Spitze"; eng = "case TRIANGLE_TOP"; break;
					case TRIANGLES_BOTTOM: ger = "Schwazer gespickelter Bannerfuß"; eng = "case TRIANGLES_BOTTOM"; break;
					case TRIANGLES_TOP: ger = "Schwarzes gespickeltes Bannerhaupt"; eng = "case TRIANGLES_TOP"; break;
					case DIAGONAL_LEFT: ger = "Schwarz schräglinks geteilt"; eng = "case DIAGONAL_LEFT"; break;
					case DIAGONAL_RIGHT: ger = "Schwarz schräglinks geteilt (Invertiert"; eng = "case DIAGONAL_RIGHT"; break;
					case DIAGONAL_UP_LEFT: ger = "Schwarz schrägrechts geteilt (Invertiert)"; eng = "case DIAGONAL_UP_LEFT"; break;
					case DIAGONAL_UP_RIGHT: ger = "Schwarz schrägrechts geteilt"; eng = "case DIAGONAL_UP_RIGHT"; break;
					case CIRCLE: ger = "Schwarze Kugel"; eng = "BLACK_CIRCLE_MIDDLE"; break;
					case RHOMBUS: ger = "Schwarze Raute"; eng = "BLACK_RHOMBUS_MIDDLE"; break;
					case HALF_VERTICAL: ger = "Rechts schwarz gespalten"; eng = "case HALF_VERTICAL"; break;
					case HALF_HORIZONTAL: ger = "Oben schwarz geteilt"; eng = "case HALF_HORIZONTAL"; break;
					case HALF_VERTICAL_RIGHT: ger = "Links schwarz gespalten"; eng = "case HALF_VERTICAL_RIGHT"; break;
					case HALF_HORIZONTAL_BOTTOM: ger = "Unten schwarz geteilt"; eng = "case HALF_HORIZONTAL_BOTTOM"; break;
					case BORDER: ger = "Schwarzer Bord"; eng = "BLACK_BORDER"; break;
					case CURLY_BORDER: ger = "Schwarzer Spickelbord"; eng = "BLACK_CURLY_BORDER"; break;
					case CREEPER: ger = "Schwarzer Creeper"; eng = "BLACK_CREEPER"; break;
					case GRADIENT: ger = "Schwarzer Farbverlauf"; eng = "BLACK_GRADIENT"; break;
					case GRADIENT_UP: ger = "Schwarzer Farbverlauf (Invertiert)"; eng = "BLACK_GRADIENT_UP"; break;
					case BRICKS: ger = "Feld schwarz gemauert"; eng = "BLACK_BRICKS"; break;
					case SKULL: ger = "Schwarzer Schädel"; eng = "BLACK_SKULL"; break;
					case FLOWER: ger = "Schwarze Blume"; eng = "BLACK_FLOWER"; break;
					case MOJANG: ger = "Schwarzes Mojang-Logo"; eng = "BLACK_MOJANG"; break;
					case GLOBE: ger = "Schwarzer Globus"; eng = "BLACK_GLOBE"; break;
					case PIGLIN: ger = "Schwarzer Schnauze"; eng = "BLACK_PIGLIN"; break;
					case FLOW: ger = "Schwarze Spirale"; eng = dc.toString()+"_"+p.toString(); break;
					case GUSTER: ger = "Schwarzer Wither"; eng = dc.toString()+"_"+p.toString(); break;
					}
					break;
				case BLUE:
					switch(p)
					{
					case BASE: ger = "Blaues Banner"; eng = "BLUE_BASE"; break;
					case SQUARE_BOTTOM_LEFT: ger = "Blaues rechtes Untereck"; eng = "BLUE_SQUARE_BOTTOM_LEFT"; break;
					case SQUARE_BOTTOM_RIGHT: ger = "Blaues linkes Untereck"; eng = "BLUE_SQUARE_BOTTOM_RIGHT"; break;
					case SQUARE_TOP_LEFT: ger = "Blaues rechtes Obereck"; eng = "BLUE_SQUARE_TOP_LEFT"; break;
					case SQUARE_TOP_RIGHT: ger = "Blaues linkes Obereck"; eng = "BLUE_SQUARE_TOP_RIGHT"; break;
					case STRIPE_BOTTOM: ger = "Blauer Bannerfuß"; eng = "BLUE_STRIPE_BOTTOM"; break;
					case STRIPE_TOP: ger = "Blaues Bannerhaupt"; eng = "BLUE_STRIPE_TOP"; break;
					case STRIPE_LEFT: ger = "Blaue rechte Flanke"; eng = "BLUE_STRIPE_LEFT"; break;
					case STRIPE_RIGHT: ger = "Blaue linke Flanke"; eng = "BLUE_STRIPE_RIGHT"; break;
					case STRIPE_CENTER: ger = "Blauer Pfahl"; eng = "BLUE_STRIPE_CENTER"; break;
					case STRIPE_MIDDLE: ger = "Blauer Balken"; eng = "BLUE_STRIPE_MIDDLE"; break;
					case STRIPE_DOWNRIGHT: ger = "Blauer Schrägbalken"; eng = "BLUE_STRIPE_DOWNRIGHT"; break;
					case STRIPE_DOWNLEFT: ger = "Blauer Schräglinksbalken"; eng = "BLUE_STRIPE_DOWNLEFT"; break;
					case SMALL_STRIPES: ger = "Vier blaue Pfähle"; eng = "BLUE_STRIPE_SMALL"; break;
					case CROSS: ger = "Blaues Andreaskreuz"; eng = "BLUE_CROSS"; break;
					case STRAIGHT_CROSS: ger = "Blaues Kreuz"; eng = "BLUE_STRAIGHT_CROSS"; break;
					case TRIANGLE_BOTTOM: ger = "Blaue halbe Spitze"; eng = "BLUE_TRIANGLE_BOTTOM"; break;
					case TRIANGLE_TOP: ger = "Blaue gestürzte halbe Spitze"; eng = "BLUE_TRIANGLE_TOP"; break;
					case TRIANGLES_BOTTOM: ger = "Blauer gespickelter Bannerfuß"; eng = "BLUE_TRIANGLES_BOTTOM"; break;
					case TRIANGLES_TOP: ger = "Blaues gespickeltes Bannerhaupt"; eng = "BLUE_TRIANGLES_TOP"; break;
					case DIAGONAL_LEFT: ger = "Blau schräglinks geteilt"; eng = "BLUE_DIAGONAL_LEFT"; break;
					case DIAGONAL_RIGHT: ger = "Blau schräglinks geteilt (Invertiert"; eng = "BLUE_DIAGONAL_RIGHT"; break;
					case DIAGONAL_UP_LEFT: ger = "Blau schrägrechts geteilt (Invertiert)"; eng = "BLUE_DIAGONAL_LEFT_MIRROR"; break;
					case DIAGONAL_UP_RIGHT: ger = "Blau schrägrechts geteilt"; eng = "BLUE_DIAGONAL_RIGHT_MIRROR"; break;
					case CIRCLE: ger = "Blaue Kugel"; eng = "BLUE_CIRCLE_MIDDLE"; break;
					case RHOMBUS: ger = "Blaue Raute"; eng = "BLUE_RHOMBUS_MIDDLE"; break;
					case HALF_VERTICAL: ger = "Rechts blau gespalten"; eng = "BLUE_HALF_VERTICAL"; break;
					case HALF_HORIZONTAL: ger = "Oben blau geteilt"; eng = "BLUE_HALF_HORIZONTAL"; break;
					case HALF_VERTICAL_RIGHT: ger = "Links blau gespalten"; eng = "BLUE_HALF_VERTICAL_MIRROR"; break;
					case HALF_HORIZONTAL_BOTTOM: ger = "Unten blau geteilt"; eng = "BLUE_HALF_HORIZONTAL_MIRROR"; break;
					case BORDER: ger = "Blauer Bord"; eng = "BLUE_BORDER"; break;
					case CURLY_BORDER: ger = "Blauer Spickelbord"; eng = "BLUE_CURLY_BORDER"; break;
					case CREEPER: ger = "Blauer Creeper"; eng = "BLUE_CREEPER"; break;
					case GRADIENT: ger = "Blauer Farbverlauf"; eng = "BLUE_GRADIENT"; break;
					case GRADIENT_UP: ger = "Blauer Farbverlauf (Invertiert)"; eng = "BLUE_GRADIENT_UP"; break;
					case BRICKS: ger = "Feld blau gemauert"; eng = "BLUE_BRICKS"; break;
					case SKULL: ger = "Blauer Schädel"; eng = "BLUE_SKULL"; break;
					case FLOWER: ger = "Blaue Blume"; eng = "BLUE_FLOWER"; break;
					case MOJANG: ger = "Blaues Mojang-Logo"; eng = "BLUE_MOJANG"; break;
					case GLOBE: ger = "Blauer Globus"; eng = "BLUE_GLOBE"; break;
					case PIGLIN: ger = "Blaue Schnauze"; eng = "BLUE_PIGLIN"; break;
					case FLOW: ger = "Blaue Spirale"; eng = dc.toString()+"_"+p.toString(); break;
					case GUSTER: ger = "Blauer Wither"; eng = dc.toString()+"_"+p.toString(); break;
					}
					break;
				case BROWN:
					switch(p)
					{
					case BASE: ger = "Braunes Banner"; eng = "BROWN_BASE"; break;
					case SQUARE_BOTTOM_LEFT: ger = "Braunes rechtes Untereck"; eng = "BROWN_SQUARE_BOTTOM_LEFT"; break;
					case SQUARE_BOTTOM_RIGHT: ger = "Braunes linkes Untereck"; eng = "BROWN_SQUARE_BOTTOM_RIGHT"; break;
					case SQUARE_TOP_LEFT: ger = "Braunes rechtes Obereck"; eng = "BROWN_SQUARE_TOP_LEFT"; break;
					case SQUARE_TOP_RIGHT: ger = "Braunes linkes Obereck"; eng = "BROWN_SQUARE_TOP_RIGHT"; break;
					case STRIPE_BOTTOM: ger = "Brauner Bannerfuß"; eng = "BROWN_STRIPE_BOTTOM"; break;
					case STRIPE_TOP: ger = "Braunes Bannerhaupt"; eng = "BROWN_STRIPE_TOP"; break;
					case STRIPE_LEFT: ger = "Braune rechte Flanke"; eng = "BROWN_STRIPE_LEFT"; break;
					case STRIPE_RIGHT: ger = "Braune linke Flanke"; eng = "BROWN_STRIPE_RIGHT"; break;
					case STRIPE_CENTER: ger = "Brauner Pfahl"; eng = "BROWN_STRIPE_CENTER"; break;
					case STRIPE_MIDDLE: ger = "Brauner Balken"; eng = "BROWN_STRIPE_MIDDLE"; break;
					case STRIPE_DOWNRIGHT: ger = "Brauner Schrägbalken"; eng = "BROWN_STRIPE_DOWNRIGHT"; break;
					case STRIPE_DOWNLEFT: ger = "Brauner Schräglinksbalken"; eng = "BROWN_STRIPE_DOWNLEFT"; break;
					case SMALL_STRIPES: ger = "Vier braune Pfähle"; eng = "BROWN_STRIPE_SMALL"; break;
					case CROSS: ger = "Braunes Andreaskreuz"; eng = "BROWN_CROSS"; break;
					case STRAIGHT_CROSS: ger = "Braunes Kreuz"; eng = "BROWN_STRAIGHT_CROSS"; break;
					case TRIANGLE_BOTTOM: ger = "Braune halbe Spitze"; eng = "BROWN_TRIANGLE_BOTTOM"; break;
					case TRIANGLE_TOP: ger = "Braune gestürzte halbe Spitze"; eng = "BROWN_TRIANGLE_TOP"; break;
					case TRIANGLES_BOTTOM: ger = "Brauner gespickelter Bannerfuß"; eng = "BROWN_TRIANGLES_BOTTOM"; break;
					case TRIANGLES_TOP: ger = "Braunes gespickeltes Bannerhaupt"; eng = "BROWN_TRIANGLES_TOP"; break;
					case DIAGONAL_LEFT: ger = "Braun schräglinks geteilt"; eng = "BROWN_DIAGONAL_LEFT"; break;
					case DIAGONAL_RIGHT: ger = "Braun schräglinks geteilt (Invertiert"; eng = "BROWN_DIAGONAL_RIGHT"; break;
					case DIAGONAL_UP_LEFT: ger = "Braun schrägrechts geteilt (Invertiert)"; eng = "BROWN_DIAGONAL_LEFT_MIRROR"; break;
					case DIAGONAL_UP_RIGHT: ger = "Braun schrägrechts geteilt"; eng = "BROWN_DIAGONAL_RIGHT_MIRROR"; break;
					case CIRCLE: ger = "Braune Kugel"; eng = "BROWN_CIRCLE_MIDDLE"; break;
					case RHOMBUS: ger = "Braune Raute"; eng = "BROWN_RHOMBUS_MIDDLE"; break;
					case HALF_VERTICAL: ger = "Rechts braun gespalten"; eng = "BROWN_HALF_VERTICAL"; break;
					case HALF_HORIZONTAL: ger = "Oben braun geteilt"; eng = "BROWN_HALF_HORIZONTAL"; break;
					case HALF_VERTICAL_RIGHT: ger = "Links braun gespalten"; eng = "BROWN_HALF_VERTICAL_MIRROR"; break;
					case HALF_HORIZONTAL_BOTTOM: ger = "Unten braun geteilt"; eng = "BROWN_HALF_HORIZONTAL_MIRROR"; break;
					case BORDER: ger = "Brauner Bord"; eng = "BROWN_BORDER"; break;
					case CURLY_BORDER: ger = "Brauner Spickelbord"; eng = "BROWN_CURLY_BORDER"; break;
					case CREEPER: ger = "Brauner Creeper"; eng = "BROWN_CREEPER"; break;
					case GRADIENT: ger = "Brauner Farbverlauf"; eng = "BROWN_GRADIENT"; break;
					case GRADIENT_UP: ger = "Brauner Farbverlauf (Invertiert)"; eng = "BROWN_GRADIENT_UP"; break;
					case BRICKS: ger = "Feld braun gemauert"; eng = "BROWN_BRICKS"; break;
					case SKULL: ger = "Brauner Schädel"; eng = "BROWN_SKULL"; break;
					case FLOWER: ger = "Braune Blume"; eng = "BROWN_FLOWER"; break;
					case MOJANG: ger = "Braunes Mojang-Logo"; eng = "BROWN_MOJANG"; break;
					case GLOBE: ger = "Brauner Globus"; eng = "BROWN_GLOBE"; break;
					case PIGLIN: ger = "Braune Schnauze"; eng = "BROWN_PIGLIN"; break;
					case FLOW: ger = "Braune Spirale"; eng = dc.toString()+"_"+p.toString(); break;
					case GUSTER: ger = "Brauner Wither"; eng = dc.toString()+"_"+p.toString(); break;
					}
					break;
				case CYAN:
					switch(p)
					{
					case BASE: ger = "Türkises Banner"; eng = "CYAN_BASE"; break;
					case SQUARE_BOTTOM_LEFT: ger = "Türkises rechtes Untereck"; eng = "CYAN_SQUARE_BOTTOM_LEFT"; break;
					case SQUARE_BOTTOM_RIGHT: ger = "Türkises linkes Untereck"; eng = "CYAN_SQUARE_BOTTOM_RIGHT"; break;
					case SQUARE_TOP_LEFT: ger = "Türkises rechtes Obereck"; eng = "CYAN_SQUARE_TOP_LEFT"; break;
					case SQUARE_TOP_RIGHT: ger = "Türkises linkes Obereck"; eng = "CYAN_SQUARE_TOP_RIGHT"; break;
					case STRIPE_BOTTOM: ger = "Türkiser Bannerfuß"; eng = "CYAN_STRIPE_BOTTOM"; break;
					case STRIPE_TOP: ger = "Türkises Bannerhaupt"; eng = "CYAN_STRIPE_TOP"; break;
					case STRIPE_LEFT: ger = "Türkise rechte Flanke"; eng = "CYAN_STRIPE_LEFT"; break;
					case STRIPE_RIGHT: ger = "Türkise linke Flanke"; eng = "CYAN_STRIPE_RIGHT"; break;
					case STRIPE_CENTER: ger = "Türkiser Pfahl"; eng = "CYAN_STRIPE_CENTER"; break;
					case STRIPE_MIDDLE: ger = "Türkiser Balken"; eng = "CYAN_STRIPE_MIDDLE"; break;
					case STRIPE_DOWNRIGHT: ger = "Türkiser Schrägbalken"; eng = "CYAN_STRIPE_DOWNRIGHT"; break;
					case STRIPE_DOWNLEFT: ger = "Türkiser Schräglinksbalken"; eng = "CYAN_STRIPE_DOWNLEFT"; break;
					case SMALL_STRIPES: ger = "Vier türkise Pfähle"; eng = "CYAN_STRIPE_SMALL"; break;
					case CROSS: ger = "Türkises Andreaskreuz"; eng = "CYAN_CROSS"; break;
					case STRAIGHT_CROSS: ger = "Türkises Kreuz"; eng = "CYAN_STRAIGHT_CROSS"; break;
					case TRIANGLE_BOTTOM: ger = "Türkise halbe Spitze"; eng = "CYAN_TRIANGLE_BOTTOM"; break;
					case TRIANGLE_TOP: ger = "Türkise gestürzte halbe Spitze"; eng = "CYAN_TRIANGLE_TOP"; break;
					case TRIANGLES_BOTTOM: ger = "Türkiser gespickelter Bannerfuß"; eng = "CYAN_TRIANGLES_BOTTOM"; break;
					case TRIANGLES_TOP: ger = "Türkises gespickeltes Bannerhaupt"; eng = "CYAN_TRIANGLES_TOP"; break;
					case DIAGONAL_LEFT: ger = "Türkis schräglinks geteilt"; eng = "CYAN_DIAGONAL_LEFT"; break;
					case DIAGONAL_RIGHT: ger = "Türkis schräglinks geteilt (Invertiert"; eng = "CYAN_DIAGONAL_RIGHT"; break;
					case DIAGONAL_UP_LEFT: ger = "Türkis schrägrechts geteilt (Invertiert)"; eng = "CYAN_DIAGONAL_LEFT_MIRROR"; break;
					case DIAGONAL_UP_RIGHT: ger = "Türkis schrägrechts geteilt"; eng = "CYAN_DIAGONAL_RIGHT_MIRROR"; break;
					case CIRCLE: ger = "Türkise Kugel"; eng = "CYAN_CIRCLE_MIDDLE"; break;
					case RHOMBUS: ger = "Türkise Raute"; eng = "CYAN_RHOMBUS_MIDDLE"; break;
					case HALF_VERTICAL: ger = "Rechts türkis gespalten"; eng = "CYAN_HALF_VERTICAL"; break;
					case HALF_HORIZONTAL: ger = "Oben türkis geteilt"; eng = "CYAN_HALF_HORIZONTAL"; break;
					case HALF_VERTICAL_RIGHT: ger = "Links türkis gespalten"; eng = "CYAN_HALF_VERTICAL_MIRROR"; break;
					case HALF_HORIZONTAL_BOTTOM: ger = "Unten türkis geteilt"; eng = "CYAN_HALF_HORIZONTAL_MIRROR"; break;
					case BORDER: ger = "Türkiser Bord"; eng = "CYAN_BORDER"; break;
					case CURLY_BORDER: ger = "Türkiser Spickelbord"; eng = "CYAN_CURLY_BORDER"; break;
					case CREEPER: ger = "Türkiser Creeper"; eng = "CYAN_CREEPER"; break;
					case GRADIENT: ger = "Türkiser Farbverlauf"; eng = "CYAN_GRADIENT"; break;
					case GRADIENT_UP: ger = "Türkiser Farbverlauf (Invertiert)"; eng = "CYAN_GRADIENT_UP"; break;
					case BRICKS: ger = "Feld türkis gemauert"; eng = "CYAN_BRICKS"; break;
					case SKULL: ger = "Türkiser Schädel"; eng = "CYAN_SKULL"; break;
					case FLOWER: ger = "Türkise Blume"; eng = "CYAN_FLOWER"; break;
					case MOJANG: ger = "Türkises Mojang-Logo"; eng = "CYAN_MOJANG"; break;
					case GLOBE: ger = "Türkiser Globus"; eng = "CYAN_GLOBE"; break;
					case PIGLIN: ger = "Türkise Schnauze"; eng = "CYAN_PIGLIN"; break;
					case FLOW: ger = "Türkise Spirale"; eng = dc.toString()+"_"+p.toString(); break;
					case GUSTER: ger = "Türkiser Wither"; eng = dc.toString()+"_"+p.toString(); break;
					}
					break;
				case GRAY:
					switch(p)
					{
					case BASE: ger = "Graues Banner"; eng = "GRAY_BASE"; break;
					case SQUARE_BOTTOM_LEFT: ger = "Graues rechtes Untereck"; eng = "GRAY_SQUARE_BOTTOM_LEFT"; break;
					case SQUARE_BOTTOM_RIGHT: ger = "Graues linkes Untereck"; eng = "GRAY_SQUARE_BOTTOM_RIGHT"; break;
					case SQUARE_TOP_LEFT: ger = "Graues rechtes Obereck"; eng = "GRAY_SQUARE_TOP_LEFT"; break;
					case SQUARE_TOP_RIGHT: ger = "Graues linkes Obereck"; eng = "GRAY_SQUARE_TOP_RIGHT"; break;
					case STRIPE_BOTTOM: ger = "Grauer Bannerfuß"; eng = "GRAY_STRIPE_BOTTOM"; break;
					case STRIPE_TOP: ger = "Graues Bannerhaupt"; eng = "GRAY_STRIPE_TOP"; break;
					case STRIPE_LEFT: ger = "Graue rechte Flanke"; eng = "GRAY_STRIPE_LEFT"; break;
					case STRIPE_RIGHT: ger = "Graue linke Flanke"; eng = "GRAY_STRIPE_RIGHT"; break;
					case STRIPE_CENTER: ger = "Grauer Pfahl"; eng = "GRAY_STRIPE_CENTER"; break;
					case STRIPE_MIDDLE: ger = "Grauer Balken"; eng = "GRAY_STRIPE_MIDDLE"; break;
					case STRIPE_DOWNRIGHT: ger = "Grauer Schrägbalken"; eng = "GRAY_STRIPE_DOWNRIGHT"; break;
					case STRIPE_DOWNLEFT: ger = "Grauer Schräglinksbalken"; eng = "GRAY_STRIPE_DOWNLEFT"; break;
					case SMALL_STRIPES: ger = "Vier graue Pfähle"; eng = "GRAY_STRIPE_SMALL"; break;
					case CROSS: ger = "Graues Andreaskreuz"; eng = "GRAY_CROSS"; break;
					case STRAIGHT_CROSS: ger = "Graues Kreuz"; eng = "GRAY_STRAIGHT_CROSS"; break;
					case TRIANGLE_BOTTOM: ger = "Graue halbe Spitze"; eng = "GRAY_TRIANGLE_BOTTOM"; break;
					case TRIANGLE_TOP: ger = "Graue gestürzte halbe Spitze"; eng = "GRAY_TRIANGLE_TOP"; break;
					case TRIANGLES_BOTTOM: ger = "Grauer gespickelter Bannerfuß"; eng = "GRAY_TRIANGLES_BOTTOM"; break;
					case TRIANGLES_TOP: ger = "Graues gespickeltes Bannerhaupt"; eng = "GRAY_TRIANGLES_TOP"; break;
					case DIAGONAL_LEFT: ger = "Grau schräglinks geteilt"; eng = "GRAY_DIAGONAL_LEFT"; break;
					case DIAGONAL_RIGHT: ger = "Grau schräglinks geteilt (Invertiert"; eng = "GRAY_DIAGONAL_RIGHT"; break;
					case DIAGONAL_UP_LEFT: ger = "Grau schrägrechts geteilt (Invertiert)"; eng = "GRAY_DIAGONAL_LEFT_MIRROR"; break;
					case DIAGONAL_UP_RIGHT: ger = "Grau schrägrechts geteilt"; eng = "GRAY_DIAGONAL_RIGHT_MIRROR"; break;
					case CIRCLE: ger = "Graue Kugel"; eng = "GRAY_CIRCLE_MIDDLE"; break;
					case RHOMBUS: ger = "Graue Raute"; eng = "GRAY_RHOMBUS_MIDDLE"; break;
					case HALF_VERTICAL: ger = "Rechts grau gespalten"; eng = "GRAY_HALF_VERTICAL"; break;
					case HALF_HORIZONTAL: ger = "Oben grau geteilt"; eng = "GRAY_HALF_HORIZONTAL"; break;
					case HALF_VERTICAL_RIGHT: ger = "Links grau gespalten"; eng = "GRAY_HALF_VERTICAL_MIRROR"; break;
					case HALF_HORIZONTAL_BOTTOM: ger = "Unten grau geteilt"; eng = "GRAY_HALF_HORIZONTAL_MIRROR"; break;
					case BORDER: ger = "Grauer Bord"; eng = "GRAY_BORDER"; break;
					case CURLY_BORDER: ger = "Grauer Spickelbord"; eng = "GRAY_CURLY_BORDER"; break;
					case CREEPER: ger = "Grauer Creeper"; eng = "GRAY_CREEPER"; break;
					case GRADIENT: ger = "Grauer Farbverlauf"; eng = "GRAY_GRADIENT"; break;
					case GRADIENT_UP: ger = "Grauer Farbverlauf (Invertiert)"; eng = "GRAY_GRADIENT_UP"; break;
					case BRICKS: ger = "Feld grau gemauert"; eng = "GRAY_BRICKS"; break;
					case SKULL: ger = "Grauer Schädel"; eng = "GRAY_SKULL"; break;
					case FLOWER: ger = "Graue Blume"; eng = "GRAY_FLOWER"; break;
					case MOJANG: ger = "Graues Mojang-Logo"; eng = "GRAY_MOJANG"; break;
					case GLOBE: ger = "Grauer Globus"; eng = "GRAY_GLOBE"; break;
					case PIGLIN: ger = "Graue Schnauze"; eng = "GRAY_PIGLIN"; break;
					case FLOW: ger = "Graue Spirale"; eng = dc.toString()+"_"+p.toString(); break;
					case GUSTER: ger = "Grauer Wither"; eng = dc.toString()+"_"+p.toString(); break;
					}
					break;
				case GREEN:
					switch(p)
					{
					case BASE: ger = "Grünes Banner"; eng = "GREEN_BASE"; break;
					case SQUARE_BOTTOM_LEFT: ger = "Grünes rechtes Untereck"; eng = "GREEN_SQUARE_BOTTOM_LEFT"; break;
					case SQUARE_BOTTOM_RIGHT: ger = "Grünes linkes Untereck"; eng = "GREEN_SQUARE_BOTTOM_RIGHT"; break;
					case SQUARE_TOP_LEFT: ger = "Grünes rechtes Obereck"; eng = "GREEN_SQUARE_TOP_LEFT"; break;
					case SQUARE_TOP_RIGHT: ger = "Grünes linkes Obereck"; eng = "GREEN_SQUARE_TOP_RIGHT"; break;
					case STRIPE_BOTTOM: ger = "Grüner Bannerfuß"; eng = "GREEN_STRIPE_BOTTOM"; break;
					case STRIPE_TOP: ger = "Grünes Bannerhaupt"; eng = "GREEN_STRIPE_TOP"; break;
					case STRIPE_LEFT: ger = "Grüne rechte Flanke"; eng = "GREEN_STRIPE_LEFT"; break;
					case STRIPE_RIGHT: ger = "Grüne linke Flanke"; eng = "GREEN_STRIPE_RIGHT"; break;
					case STRIPE_CENTER: ger = "Grüner Pfahl"; eng = "GREEN_STRIPE_CENTER"; break;
					case STRIPE_MIDDLE: ger = "Grüner Balken"; eng = "GREEN_STRIPE_MIDDLE"; break;
					case STRIPE_DOWNRIGHT: ger = "Grüner Schrägbalken"; eng = "GREEN_STRIPE_DOWNRIGHT"; break;
					case STRIPE_DOWNLEFT: ger = "Grüner Schräglinksbalken"; eng = "GREEN_STRIPE_DOWNLEFT"; break;
					case SMALL_STRIPES: ger = "Vier grüne Pfähle"; eng = "GREEN_STRIPE_SMALL"; break;
					case CROSS: ger = "Grünes Andreaskreuz"; eng = "GREEN_CROSS"; break;
					case STRAIGHT_CROSS: ger = "Grünes Kreuz"; eng = "GREEN_STRAIGHT_CROSS"; break;
					case TRIANGLE_BOTTOM: ger = "Grüne halbe Spitze"; eng = "GREEN_TRIANGLE_BOTTOM"; break;
					case TRIANGLE_TOP: ger = "Grüne gestürzte halbe Spitze"; eng = "GREEN_TRIANGLE_TOP"; break;
					case TRIANGLES_BOTTOM: ger = "Grüner gespickelter Bannerfuß"; eng = "GREEN_TRIANGLES_BOTTOM"; break;
					case TRIANGLES_TOP: ger = "Grünes gespickeltes Bannerhaupt"; eng = "GREEN_TRIANGLES_TOP"; break;
					case DIAGONAL_LEFT: ger = "Grün schräglinks geteilt"; eng = "GREEN_DIAGONAL_LEFT"; break;
					case DIAGONAL_RIGHT: ger = "Grün schräglinks geteilt (Invertiert"; eng = "GREEN_DIAGONAL_RIGHT"; break;
					case DIAGONAL_UP_LEFT: ger = "Grün schrägrechts geteilt (Invertiert)"; eng = "GREEN_DIAGONAL_LEFT_MIRROR"; break;
					case DIAGONAL_UP_RIGHT: ger = "Grün schrägrechts geteilt"; eng = "GREEN_DIAGONAL_RIGHT_MIRROR"; break;
					case CIRCLE: ger = "Grüne Kugel"; eng = "case CIRCLE"; break;
					case RHOMBUS: ger = "Grüne Raute"; eng = "GREEN_RHOMBUS_MIDDLE"; break;
					case HALF_VERTICAL: ger = "Rechts grün gespalten"; eng = "GREEN_HALF_VERTICAL"; break;
					case HALF_HORIZONTAL: ger = "Oben grün geteilt"; eng = "GREEN_HALF_HORIZONTAL"; break;
					case HALF_VERTICAL_RIGHT: ger = "Links grün gespalten"; eng = "GREEN_HALF_VERTICAL_MIRROR"; break;
					case HALF_HORIZONTAL_BOTTOM: ger = "Unten grün geteilt"; eng = "GREEN_HALF_HORIZONTAL_MIRROR"; break;
					case BORDER: ger = "Grüner Bord"; eng = "GREEN_BORDER"; break;
					case CURLY_BORDER: ger = "Grüner Spickelbord"; eng = "GREEN_CURLY_BORDER"; break;
					case CREEPER: ger = "Grüner Creeper"; eng = "GREEN_CREEPER"; break;
					case GRADIENT: ger = "Grüner Farbverlauf"; eng = "GREEN_GRADIENT"; break;
					case GRADIENT_UP: ger = "Grüner Farbverlauf (Invertiert)"; eng = "GREEN_GRADIENT_UP"; break;
					case BRICKS: ger = "Feld grün gemauert"; eng = "GREEN_BRICKS"; break;
					case SKULL: ger = "Grüner Schädel"; eng = "GREEN_SKULL"; break;
					case FLOWER: ger = "Grüne Blume"; eng = "GREEN_FLOWER"; break;
					case MOJANG: ger = "Grünes Mojang-Logo"; eng = "GREEN_MOJANG"; break;
					case GLOBE: ger = "Grüner Globus"; eng = "GREEN_GLOBE"; break;
					case PIGLIN: ger = "Grüne Schnauze"; eng = "GREEN_PIGLIN"; break;
					case FLOW: ger = "Grüne Spirale"; eng = dc.toString()+"_"+p.toString(); break;
					case GUSTER: ger = "Grüner Wither"; eng = dc.toString()+"_"+p.toString(); break;
					}
					break;
				case LIGHT_BLUE:
					switch(p)
					{
					case BASE: ger = "Hellblaues Banner"; eng = "LIGHT_BLUE_BASE"; break;
					case SQUARE_BOTTOM_LEFT: ger = "Hellblaues rechtes Untereck"; eng = "LIGHT_BLUE_SQUARE_BOTTOM_LEFT"; break;
					case SQUARE_BOTTOM_RIGHT: ger = "Hellblaues linkes Untereck"; eng = "LIGHT_BLUE_SQUARE_BOTTOM_RIGHT"; break;
					case SQUARE_TOP_LEFT: ger = "Hellblaues rechtes Obereck "; eng = "LIGHT_BLUE_SQUARE_TOP_LEFT"; break;
					case SQUARE_TOP_RIGHT: ger = "Hellblaues linkes Obereck"; eng = "LIGHT_BLUE_SQUARE_TOP_RIGHT"; break;
					case STRIPE_BOTTOM: ger = "Hellblauer Bannerfuß"; eng = "LIGHT_BLUE_STRIPE_BOTTOM"; break;
					case STRIPE_TOP: ger = "Hellblaues Bannerhaupt"; eng = "LIGHT_BLUE_STRIPE_TOP"; break;
					case STRIPE_LEFT: ger = "Hellblaue rechte Flanke"; eng = "LIGHT_BLUE_STRIPE_LEFT"; break;
					case STRIPE_RIGHT: ger = "Hellblaue linke Flanke"; eng = "LIGHT_BLUE_STRIPE_RIGHT"; break;
					case STRIPE_CENTER: ger = "Hellblauer Pfahl"; eng = "LIGHT_BLUE_STRIPE_CENTER"; break;
					case STRIPE_MIDDLE: ger = "Hellblauer Balken"; eng = "LIGHT_BLUE_STRIPE_MIDDLE"; break;
					case STRIPE_DOWNRIGHT: ger = "Hellblauer Schrägbalken"; eng = "LIGHT_BLUE_STRIPE_DOWNRIGHT"; break;
					case STRIPE_DOWNLEFT: ger = "Hellblauer Schräglinksbalken"; eng = "LIGHT_BLUE_STRIPE_DOWNLEFT"; break;
					case SMALL_STRIPES: ger = "Vier hellblaue Pfähle"; eng = "LIGHT_BLUE_STRIPE_SMALL"; break;
					case CROSS: ger = "Hellblaues Andreaskreuz"; eng = "LIGHT_BLUE_CROSS"; break;
					case STRAIGHT_CROSS: ger = "Hellblaues Kreuz"; eng = "LIGHT_BLUE_STRAIGHT_CROSS"; break;
					case TRIANGLE_BOTTOM: ger = "Hellblaue halbe Spitze"; eng = "LIGHT_BLUE_TRIANGLE_BOTTOM"; break;
					case TRIANGLE_TOP: ger = "Hellblaue gestürzte halbe Spitze"; eng = "LIGHT_BLUE_TRIANGLE_TOP"; break;
					case TRIANGLES_BOTTOM: ger = "Hellblauer gespickelter Bannerfuß"; eng = "LIGHT_BLUE_TRIANGLES_BOTTOM"; break;
					case TRIANGLES_TOP: ger = "Hellblaues gespickeltes Bannerhaupt"; eng = "LIGHT_BLUE_TRIANGLES_TOP"; break;
					case DIAGONAL_LEFT: ger = "Hellblau schräglinks geteilt"; eng = "LIGHT_BLUE_DIAGONAL_LEFT"; break;
					case DIAGONAL_RIGHT: ger = "Hellblau schräglinks geteilt (Invertiert"; eng = "LIGHT_BLUE_DIAGONAL_RIGHT"; break;
					case DIAGONAL_UP_LEFT: ger = "Hellblau schrägrechts geteilt (Invertiert)"; eng = "LIGHT_BLUE_DIAGONAL_LEFT_MIRROR"; break;
					case DIAGONAL_UP_RIGHT: ger = "Hellblau schrägrechts geteilt"; eng = "LIGHT_BLUE_DIAGONAL_RIGHT_MIRROR"; break;
					case CIRCLE: ger = "Hellblaue Kugel"; eng = "LIGHT_BLUE_CIRCLE_MIDDLE"; break;
					case RHOMBUS: ger = "Hellblaue Raute"; eng = "LIGHT_BLUE_RHOMBUS_MIDDLE"; break;
					case HALF_VERTICAL: ger = "Rechts hellblau gespalten"; eng = "LIGHT_BLUE_HALF_VERTICAL"; break;
					case HALF_HORIZONTAL: ger = "Oben hellblau geteilt"; eng = "LIGHT_BLUE_HALF_HORIZONTAL"; break;
					case HALF_VERTICAL_RIGHT: ger = "Links hellblau gespalten"; eng = "LIGHT_BLUE_HALF_VERTICAL_MIRROR"; break;
					case HALF_HORIZONTAL_BOTTOM: ger = "Unten hellblau geteilt"; eng = "LIGHT_BLUE_HALF_HORIZONTAL_MIRROR"; break;
					case BORDER: ger = "Hellblauer Bord"; eng = "LIGHT_BLUE_BORDER"; break;
					case CURLY_BORDER: ger = "Hellblauer Spickelbord"; eng = "LIGHT_BLUE_CURLY_BORDER"; break;
					case CREEPER: ger = "Hellblauer Creeper"; eng = "LIGHT_BLUE_CREEPER"; break;
					case GRADIENT: ger = "Hellblauer Farbverlauf"; eng = "LIGHT_BLUE_GRADIENT"; break;
					case GRADIENT_UP: ger = "Hellblauer Farbverlauf (Invertiert)"; eng = "LIGHT_BLUE_GRADIENT_UP"; break;
					case BRICKS: ger = "Feld hellblau gemauert"; eng = "LIGHT_BLUE_BRICKS"; break;
					case SKULL: ger = "Hellblauer Schädel"; eng = "LIGHT_BLUE_SKULL"; break;
					case FLOWER: ger = "Hellblaue Blume"; eng = "LIGHT_BLUE_FLOWER"; break;
					case MOJANG: ger = "Hellblaues Mojang-Logo"; eng = "LIGHT_BLUE_MOJANG"; break;
					case GLOBE: ger = "Hellblauer Globus"; eng = "LIGHT_BLUE_GLOBE"; break;
					case PIGLIN: ger = "Hellblaue Schnauze"; eng = "LIGHT_BLUE_PIGLIN"; break;
					case FLOW: ger = "Hellblaue Spirale"; eng = dc.toString()+"_"+p.toString(); break;
					case GUSTER: ger = "Hellblauer Wither"; eng = dc.toString()+"_"+p.toString(); break;
					}
					break;
				case LIGHT_GRAY:
					switch(p)
					{
					case BASE: ger = "Hellgraues Banner"; eng = "LIGHT_GRAY_BASE"; break;
					case SQUARE_BOTTOM_LEFT: ger = "Hellgraues rechtes Untereck"; eng = "LIGHT_GRAY_SQUARE_BOTTOM_LEFT"; break;
					case SQUARE_BOTTOM_RIGHT: ger = "Hellgraues linkes Untereck"; eng = "LIGHT_GRAY_SQUARE_BOTTOM_RIGHT"; break;
					case SQUARE_TOP_LEFT: ger = "Hellgraues rechtes Obereck"; eng = "LIGHT_GRAY_SQUARE_TOP_LEFT"; break;
					case SQUARE_TOP_RIGHT: ger = "Hellgraues linkes Obereck"; eng = "LIGHT_GRAY_SQUARE_TOP_RIGHT"; break;
					case STRIPE_BOTTOM: ger = "Hellgrauer Bannerfuß"; eng = "LIGHT_GRAY_STRIPE_BOTTOM"; break;
					case STRIPE_TOP: ger = "Hellgraues Bannerhaupt"; eng = "LIGHT_GRAY_STRIPE_TOP"; break;
					case STRIPE_LEFT: ger = "Hellgraue rechte Flanke"; eng = "LIGHT_GRAY_STRIPE_LEFT"; break;
					case STRIPE_RIGHT: ger = "Hellgraue linke Flanke"; eng = "LIGHT_GRAY_STRIPE_RIGHT"; break;
					case STRIPE_CENTER: ger = "Hellgrauer Pfahl"; eng = "LIGHT_GRAY_STRIPE_CENTER"; break;
					case STRIPE_MIDDLE: ger = "Hellgrauer Balken"; eng = "LIGHT_GRAY_STRIPE_MIDDLE"; break;
					case STRIPE_DOWNRIGHT: ger = "Hellgrauer Schrägbalken"; eng = "LIGHT_GRAY_STRIPE_DOWNRIGHT"; break;
					case STRIPE_DOWNLEFT: ger = "Hellgrauer Schräglinksbalken"; eng = "LIGHT_GRAY_STRIPE_DOWNLEFT"; break;
					case SMALL_STRIPES: ger = "Vier hellgraue Pfähle"; eng = "LIGHT_GRAY_STRIPE_SMALL"; break;
					case CROSS: ger = "Hellgraues Andreaskreuz"; eng = "LIGHT_GRAY_CROSS"; break;
					case STRAIGHT_CROSS: ger = "Hellgraues Kreuz"; eng = "LIGHT_GRAY_STRAIGHT_CROSS"; break;
					case TRIANGLE_BOTTOM: ger = "Hellgraue halbe Spitze"; eng = "LIGHT_GRAY_TRIANGLE_BOTTOM"; break;
					case TRIANGLE_TOP: ger = "Hellgraue gestürzte halbe Spitze"; eng = "LIGHT_GRAY_TRIANGLE_TOP"; break;
					case TRIANGLES_BOTTOM: ger = "Hellgrauer gespickelter Bannerfuß"; eng = "LIGHT_GRAY_TRIANGLES_BOTTOM"; break;
					case TRIANGLES_TOP: ger = "Hellgraues gespickeltes Bannerhaupt"; eng = "LIGHT_GRAY_TRIANGLES_TOP"; break;
					case DIAGONAL_LEFT: ger = "Hellgrau schräglinks geteilt"; eng = "LIGHT_GRAY_DIAGONAL_LEFT"; break;
					case DIAGONAL_RIGHT: ger = "Hellgrau schräglinks geteilt (Invertiert"; eng = "LIGHT_GRAY_DIAGONAL_RIGHT"; break;
					case DIAGONAL_UP_LEFT: ger = "Hellgrau schrägrechts geteilt (Invertiert)"; eng = "LIGHT_GRAY_DIAGONAL_LEFT_MIRROR"; break;
					case DIAGONAL_UP_RIGHT: ger = "Hellgrau schrägrechts geteilt"; eng = "LIGHT_GRAY_DIAGONAL_RIGHT_MIRROR"; break;
					case CIRCLE: ger = "Hellgraue Kugel"; eng = "LIGHT_GRAY_CIRCLE_MIDDLE"; break;
					case RHOMBUS: ger = "Hellgraue Raute"; eng = "LIGHT_GRAY_RHOMBUS_MIDDLE"; break;
					case HALF_VERTICAL: ger = "Rechts hellgrau gespalten"; eng = "LIGHT_GRAY_HALF_VERTICAL"; break;
					case HALF_HORIZONTAL: ger = "Oben hellgrau geteilt "; eng = "LIGHT_GRAY_HALF_HORIZONTAL"; break;
					case HALF_VERTICAL_RIGHT: ger = "Links hellgrau gespalten"; eng = "LIGHT_GRAY_HALF_VERTICAL_MIRROR"; break;
					case HALF_HORIZONTAL_BOTTOM: ger = "Unten hellgrau geteilt"; eng = "LIGHT_GRAY_HALF_HORIZONTAL_MIRROR"; break;
					case BORDER: ger = "Hellgrauer Bord"; eng = "LIGHT_GRAY_BORDER"; break;
					case CURLY_BORDER: ger = "Hellgrauer Spickelbord"; eng = "LIGHT_GRAY_CURLY_BORDER"; break;
					case CREEPER: ger = "Hellgrauer Creeper"; eng = "LIGHT_GRAY_CREEPER"; break;
					case GRADIENT: ger = "Hellgrauer Farbverlauf"; eng = "LIGHT_GRAY_GRADIENT"; break;
					case GRADIENT_UP: ger = "Hellgrauer Farbverlauf (Invertiert)"; eng = "LIGHT_GRAY_GRADIENT_UP"; break;
					case BRICKS: ger = "Feld helgrau gemauert"; eng = "LIGHT_GRAY_BRICKS"; break;
					case SKULL: ger = "Hellgrauer Schädel"; eng = "LIGHT_GRAY_SKULL"; break;
					case FLOWER: ger = "Hellgraue Blume"; eng = "LIGHT_GRAY_FLOWER"; break;
					case MOJANG: ger = "Hellgraues Mojang-Logo"; eng = "LIGHT_GRAY_MOJANG"; break;
					case GLOBE: ger = "Hellgrauer Globus"; eng = "LIGHT_GRAY_GLOBE"; break;
					case PIGLIN: ger = "Hellgraue Schnauze"; eng = "LIGHT_GRAY_PIGLIN"; break;
					case FLOW: ger = "Hellgraue Spirale"; eng = dc.toString()+"_"+p.toString(); break;
					case GUSTER: ger = "Hellgrauer Wither"; eng = dc.toString()+"_"+p.toString(); break;
					}
					break;
				case LIME:
					switch(p)
					{
					case BASE: ger = "Hellgrünes Banner"; eng = "LIME_BASE"; break;
					case SQUARE_BOTTOM_LEFT: ger = "Hellgrünes rechtes Untereck"; eng = "LIME_SQUARE_BOTTOM_LEFT"; break;
					case SQUARE_BOTTOM_RIGHT: ger = "Hellgrünes linkes Untereck"; eng = "LIME_SQUARE_BOTTOM_RIGHT"; break;
					case SQUARE_TOP_LEFT: ger = "Hellgrünes rechtes Obereck"; eng = "LIME_SQUARE_TOP_LEFT"; break;
					case SQUARE_TOP_RIGHT: ger = "Hellgrünes linkes Obereck"; eng = "LIME_SQUARE_TOP_RIGHT"; break;
					case STRIPE_BOTTOM: ger = "Hellgrüner Bannerfuß"; eng = "LIME_STRIPE_BOTTOM"; break;
					case STRIPE_TOP: ger = "Hellgrünes Bannerhaupt"; eng = "LIME_STRIPE_TOP"; break;
					case STRIPE_LEFT: ger = "Hellgrüne rechte Flanke"; eng = "LIME_STRIPE_LEFT"; break;
					case STRIPE_RIGHT: ger = "Hellgrüne linke Flanke"; eng = "LIME_STRIPE_RIGHT"; break;
					case STRIPE_CENTER: ger = "Hellgrüner Pfahl"; eng = "LIME_STRIPE_CENTER"; break;
					case STRIPE_MIDDLE: ger = "Hellgrüner Balken"; eng = "LIME_STRIPE_MIDDLE"; break;
					case STRIPE_DOWNRIGHT: ger = "Hellgrüner Schrägbalken"; eng = "LIME_STRIPE_DOWNRIGHT"; break;
					case STRIPE_DOWNLEFT: ger = "Hellgrüner Schräglinksbalken"; eng = "LIME_STRIPE_DOWNLEFT"; break;
					case SMALL_STRIPES: ger = "Vier hellgrüne Pfähle"; eng = "LIME_STRIPE_SMALL"; break;
					case CROSS: ger = "Hellgrünes Andreaskreuz"; eng = "LIME_CROSS"; break;
					case STRAIGHT_CROSS: ger = "Hellgrünes Kreuz"; eng = "LIME_STRAIGHT_CROSS"; break;
					case TRIANGLE_BOTTOM: ger = "Hellgrüne halbe Spitze"; eng = "LIME_TRIANGLE_BOTTOM"; break;
					case TRIANGLE_TOP: ger = "Hellgrüne gestürzte halbe Spitze"; eng = "LIME_TRIANGLE_TOP"; break;
					case TRIANGLES_BOTTOM: ger = "Hellgrüner gespickelter Bannerfuß"; eng = "LIME_TRIANGLES_BOTTOM"; break;
					case TRIANGLES_TOP: ger = "Hellgrünes gespickeltes Bannerhaupt"; eng = "LIME_TRIANGLES_TOP"; break;
					case DIAGONAL_LEFT: ger = "Hellgrün schräglinks geteilt"; eng = "LIME_DIAGONAL_LEFT"; break;
					case DIAGONAL_RIGHT: ger = "Hellgrün schräglinks geteilt (Invertiert"; eng = "LIME_DIAGONAL_RIGHT"; break;
					case DIAGONAL_UP_LEFT: ger = "Hellgrün schrägrechts geteilt (Invertiert)"; eng = "LIME_DIAGONAL_LEFT_MIRROR"; break;
					case DIAGONAL_UP_RIGHT: ger = "Hellgrün schrägrechts geteilt"; eng = "LIME_DIAGONAL_RIGHT_MIRROR"; break;
					case CIRCLE: ger = "Hellgrüne Kugel"; eng = "LIME_CIRCLE_MIDDLE"; break;
					case RHOMBUS: ger = "Hellgrüne Raute"; eng = "LIME_RHOMBUS_MIDDLE"; break;
					case HALF_VERTICAL: ger = "Rechts hellgrün gespalten"; eng = "LIME_HALF_VERTICAL"; break;
					case HALF_HORIZONTAL: ger = "Oben hellgrün geteilt"; eng = "LIME_HALF_HORIZONTAL"; break;
					case HALF_VERTICAL_RIGHT: ger = "Links hellgrün gespalten"; eng = "LIME_HALF_VERTICAL_MIRROR"; break;
					case HALF_HORIZONTAL_BOTTOM: ger = "Unten hellgrün geteilt"; eng = "LIME_HALF_HORIZONTAL_MIRROR"; break;
					case BORDER: ger = "Hellgrüner Bord"; eng = "LIME_BORDER"; break;
					case CURLY_BORDER: ger = "Hellgrüner Spickelbord"; eng = "LIME_CURLY_BORDER"; break;
					case CREEPER: ger = "Hellgrüner Creeper"; eng = "LIME_CREEPER"; break;
					case GRADIENT: ger = "Hellgrüner Farbverlauf"; eng = "LIME_GRADIENT"; break;
					case GRADIENT_UP: ger = "Hellgrüner Farbverlauf (Invertiert)"; eng = "LIME_GRADIENT_UP"; break;
					case BRICKS: ger = "Feld hellgrün gemauert"; eng = "LIME_BRICKS"; break;
					case SKULL: ger = "Hellgrüner Schädel"; eng = "LIME_SKULL"; break;
					case FLOWER: ger = "Hellgrüne Blume"; eng = "LIME_FLOWER"; break;
					case MOJANG: ger = "Hellgrünes Mojang-Logo"; eng = "LIME_MOJANG"; break;
					case GLOBE: ger = "Hellgrüner Globus"; eng = "LIME_GLOBE"; break;
					case PIGLIN: ger = "Hellgrüne Schnauze"; eng = "LIME_PIGLIN"; break;
					case FLOW: ger = "Hellgrüne Spirale"; eng = dc.toString()+"_"+p.toString(); break;
					case GUSTER: ger = "Hellgrüner Wither"; eng = dc.toString()+"_"+p.toString(); break;
					}
					break;
				case MAGENTA:
					switch(p)
					{
					case BASE: ger = "Magenta Banner"; eng = "MAGENTA_BASE"; break;
					case SQUARE_BOTTOM_LEFT: ger = "Magenta rechtes Untereck"; eng = "MAGENTA_SQUARE_BOTTOM_LEFT"; break;
					case SQUARE_BOTTOM_RIGHT: ger = "Magenta linkes Untereck"; eng = "MAGENTA_SQUARE_BOTTOM_RIGHT"; break;
					case SQUARE_TOP_LEFT: ger = "Magenta rechtes Obereck"; eng = "MAGENTA_SQUARE_TOP_LEFT"; break;
					case SQUARE_TOP_RIGHT: ger = "Magenta linkes Obereck"; eng = "MAGENTA_SQUARE_TOP_RIGHT"; break;
					case STRIPE_BOTTOM: ger = "Magenta Bannerfuß"; eng = "MAGENTA_STRIPE_BOTTOM"; break;
					case STRIPE_TOP: ger = "Magenta Bannerhaupt"; eng = "MAGENTA_STRIPE_TOP"; break;
					case STRIPE_LEFT: ger = "Magenta rechte Flanke"; eng = "MAGENTA_STRIPE_LEFT"; break;
					case STRIPE_RIGHT: ger = "Magenta linke Flanke"; eng = "MAGENTA_STRIPE_RIGHT"; break;
					case STRIPE_CENTER: ger = "Magenta Pfahl"; eng = "MAGENTA_STRIPE_CENTER"; break;
					case STRIPE_MIDDLE: ger = "Magenta Balken"; eng = "MAGENTA_STRIPE_MIDDLE"; break;
					case STRIPE_DOWNRIGHT: ger = "Magenta Schrägbalken"; eng = "MAGENTA_STRIPE_DOWNRIGHT"; break;
					case STRIPE_DOWNLEFT: ger = "Magenta Schräglinksbalken"; eng = "MAGENTA_STRIPE_DOWNLEFT"; break;
					case SMALL_STRIPES: ger = "Vier Magenta Pfähle"; eng = "MAGENTA_STRIPE_SMALL"; break;
					case CROSS: ger = "Magenta Andreaskreuz"; eng = "MAGENTA_CROSS"; break;
					case STRAIGHT_CROSS: ger = "Magenta Kreuz"; eng = "MAGENTA_STRAIGHT_CROSS"; break;
					case TRIANGLE_BOTTOM: ger = "Magenta halbe Spitze"; eng = "MAGENTA_TRIANGLE_BOTTOM"; break;
					case TRIANGLE_TOP: ger = "Magenta gestürzte halbe Spitze"; eng = "MAGENTA_TRIANGLE_TOP"; break;
					case TRIANGLES_BOTTOM: ger = "Magenta gespickelter Bannerfuß"; eng = "MAGENTA_TRIANGLES_BOTTOM"; break;
					case TRIANGLES_TOP: ger = "Magenta gespickeltes Bannerhaupt"; eng = "MAGENTA_TRIANGLES_TOP"; break;
					case DIAGONAL_LEFT: ger = "Magenta schräglinks geteilt"; eng = "MAGENTA_DIAGONAL_LEFT"; break;
					case DIAGONAL_RIGHT: ger = "Magenta schräglinks geteilt (Invertiert"; eng = "MAGENTA_DIAGONAL_RIGHT"; break;
					case DIAGONAL_UP_LEFT: ger = "Magenta schrägrechts geteilt (Invertiert)"; eng = "MAGENTA_DIAGONAL_LEFT_MIRROR"; break;
					case DIAGONAL_UP_RIGHT: ger = "Magenta schrägrechts geteilt"; eng = "MAGENTA_DIAGONAL_RIGHT_MIRROR"; break;
					case CIRCLE: ger = "Magenta Kugel"; eng = "MAGENTA_CIRCLE_MIDDLE"; break;
					case RHOMBUS: ger = "Magenta Raute"; eng = "MAGENTA_RHOMBUS_MIDDLE"; break;
					case HALF_VERTICAL: ger = "Rechts Magenta gespalten"; eng = "MAGENTA_HALF_VERTICAL"; break;
					case HALF_HORIZONTAL: ger = "Oben Magenta geteilt"; eng = "MAGENTA_HALF_HORIZONTAL"; break;
					case HALF_VERTICAL_RIGHT: ger = "Links Magenta gespalten"; eng = "MAGENTA_HALF_VERTICAL_MIRROR"; break;
					case HALF_HORIZONTAL_BOTTOM: ger = "Unten Magenta geteilt"; eng = "MAGENTA_HALF_HORIZONTAL_MIRROR"; break;
					case BORDER: ger = "Magenta Bord"; eng = "MAGENTA_BORDER"; break;
					case CURLY_BORDER: ger = "Magenta Spickelbord"; eng = "MAGENTA_CURLY_BORDER"; break;
					case CREEPER: ger = "Magenta Creeper"; eng = "MAGENTA_CREEPER"; break;
					case GRADIENT: ger = "Magenta Farbverlauf"; eng = "MAGENTA_GRADIENT"; break;
					case GRADIENT_UP: ger = "Magenta Farbverlauf (Invertiert)"; eng = "MAGENTA_GRADIENT_UP"; break;
					case BRICKS: ger = "Feld Magenta gemauert"; eng = "MAGENTA_BRICKS"; break;
					case SKULL: ger = "Magenta Schädel"; eng = "MAGENTA_SKULL"; break;
					case FLOWER: ger = "Magenta Blume"; eng = "MAGENTA_FLOWER"; break;
					case MOJANG: ger = "Magenta Mojang-Logo"; eng = "MAGENTA_MOJANG"; break;
					case GLOBE: ger = "Magenta Globus"; eng = "MAGENTA_GLOBE"; break;
					case PIGLIN: ger = "Magenta Schnauze"; eng = "MAGENTA_PIGLIN"; break;
					case FLOW: ger = "Magenta Spirale"; eng = dc.toString()+"_"+p.toString(); break;
					case GUSTER: ger = "Magenta Wither"; eng = dc.toString()+"_"+p.toString(); break;
					}
					break;
				case ORANGE:
					switch(p)
					{
					case BASE: ger = "Oranges Banner"; eng = "ORANGE_BASE"; break;
					case SQUARE_BOTTOM_LEFT: ger = "Oranges rechtes Untereck"; eng = "ORANGE_SQUARE_BOTTOM_LEFT"; break;
					case SQUARE_BOTTOM_RIGHT: ger = "Oranges linkes Untereck"; eng = "ORANGE_SQUARE_BOTTOM_RIGHT"; break;
					case SQUARE_TOP_LEFT: ger = "Oranges rechtes Obereck"; eng = "ORANGE_SQUARE_TOP_LEFT"; break;
					case SQUARE_TOP_RIGHT: ger = "Oranges linkes Obereck"; eng = "ORANGE_SQUARE_TOP_RIGHT"; break;
					case STRIPE_BOTTOM: ger = "Oranger Bannerfuß"; eng = "ORANGE_STRIPE_BOTTOM"; break;
					case STRIPE_TOP: ger = "Oranges Bannerhaupt"; eng = "ORANGE_STRIPE_TOP"; break;
					case STRIPE_LEFT: ger = "Orange rechte Flanke"; eng = "ORANGE_STRIPE_LEFT"; break;
					case STRIPE_RIGHT: ger = "Orange linke Flanke"; eng = "ORANGE_STRIPE_RIGHT"; break;
					case STRIPE_CENTER: ger = "Oranger Pfahl"; eng = "ORANGE_STRIPE_CENTER"; break;
					case STRIPE_MIDDLE: ger = "Oranger Balken"; eng = "ORANGE_STRIPE_MIDDLE"; break;
					case STRIPE_DOWNRIGHT: ger = "Oranger Schrägbalken"; eng = "ORANGE_STRIPE_DOWNRIGHT";  break;
					case STRIPE_DOWNLEFT: ger = "Oranger Schräglinksbalken"; eng = "ORANGE_STRIPE_DOWNLEFT";  break;
					case SMALL_STRIPES: ger = "Vier orange Pfähle"; eng = "ORANGE_STRIPE_SMALL"; break;
					case CROSS: ger = "Oranges Andreaskreuz"; eng = "ORANGE_CROSS"; break;
					case STRAIGHT_CROSS: ger = "Oranges Kreuz"; eng = "ORANGE_STRAIGHT_CROSS"; break;
					case TRIANGLE_BOTTOM: ger = "Orange halbe Spitze"; eng = "ORANGE_TRIANGLE_BOTTOM";  break;
					case TRIANGLE_TOP: ger = "Orange gestürzte halbe Spitze"; eng = "ORANGE_TRIANGLE_TOP";  break;
					case TRIANGLES_BOTTOM: ger = "Oranger gespickelter Bannerfuß"; eng = "ORANGE_TRIANGLES_BOTTOM"; break;
					case TRIANGLES_TOP: ger = "Oranges gespickeltes Bannerhaupt"; eng = "ORANGE_TRIANGLES_TOP"; break;
					case DIAGONAL_LEFT: ger = "Orange schräglinks geteilt"; eng = "ORANGE_DIAGONAL_LEFT"; break;
					case DIAGONAL_RIGHT: ger = "Orange schräglinks geteilt (Invertiert"; eng = "ORANGE_DIAGONAL_RIGHT";  break;
					case DIAGONAL_UP_LEFT: ger = "Orange schrägrechts geteilt (Invertiert)"; eng = "ORANGE_DIAGONAL_LEFT_MIRROR"; break;
					case DIAGONAL_UP_RIGHT: ger = "Orange schrägrechts geteilt"; eng = "ORANGE_DIAGONAL_RIGHT_MIRROR"; break;
					case CIRCLE: ger = "Orange Kugel"; eng = "ORANGE_CIRCLE_MIDDLE"; break;
					case RHOMBUS: ger = "Orange Raute"; eng = "ORANGE_RHOMBUS_MIDDLE"; break;
					case HALF_VERTICAL: ger = "Rechts orange gespalten"; eng = "ORANGE_HALF_VERTICAL"; break;
					case HALF_HORIZONTAL: ger = "Oben orange geteilt"; eng = "ORANGE_HALF_HORIZONTAL"; break;
					case HALF_VERTICAL_RIGHT: ger = "Links orange gespalten"; eng = "ORANGE_HALF_VERTICAL_MIRROR"; break;
					case HALF_HORIZONTAL_BOTTOM: ger = "Unten orange geteilt"; eng = "ORANGE_HALF_HORIZONTAL_MIRROR"; break;
					case BORDER: ger = "Oranger Bord"; eng = "ORANGE_BORDER"; break;
					case CURLY_BORDER: ger = "Oranger Spickelbord"; eng = "ORANGE_CURLY_BORDER"; break;
					case CREEPER: ger = "Oranger Creeper"; eng = "ORANGE_CREEPER"; break;
					case GRADIENT: ger = "Oranger Farbverlauf"; eng = "ORANGE_GRADIENT"; break;
					case GRADIENT_UP: ger = "Oranger Farbverlauf (Invertiert)"; eng = "ORANGE_GRADIENT_UP"; break;
					case BRICKS: ger = "Feld Orange gemauert"; eng = "ORANGE_BRICKS"; break;
					case SKULL: ger = "Oranger Schädel"; eng = "ORANGE_SKULL"; break;
					case FLOWER: ger = "Orange Blume"; eng = "ORANGE_FLOWER"; break;
					case MOJANG: ger = "Oranges Mojang-Logo"; eng = "ORANGE_MOJANG"; break;
					case GLOBE: ger = "Oranger Globus"; eng = "ORANGE_GLOBE"; break;
					case PIGLIN: ger = "Orange Schnauze"; eng = "ORANGE_PIGLIN"; break;
					case FLOW: ger = "Orange Spirale"; eng = dc.toString()+"_"+p.toString(); break;
					case GUSTER: ger = "Oranger Wither"; eng = dc.toString()+"_"+p.toString(); break;
					}
					break;
				case PINK:
					switch(p)
					{
					case BASE: ger = "Rosa Banner"; eng = "PINK_BASE"; break;
					case SQUARE_BOTTOM_LEFT: ger = "Rosa rechtes Untereck"; eng = "PINK_SQUARE_BOTTOM_LEFT"; break;
					case SQUARE_BOTTOM_RIGHT: ger = "Rosa linkes Untereck"; eng = "PINK_SQUARE_BOTTOM_RIGHT"; break;
					case SQUARE_TOP_LEFT: ger = "Rosa rechtes Obereck"; eng = "PINK_SQUARE_TOP_LEFT"; break;
					case SQUARE_TOP_RIGHT: ger = "Rosa linkes Obereck"; eng = "PINK_SQUARE_TOP_RIGHT"; break;
					case STRIPE_BOTTOM: ger = "Rosa Bannerfuß"; eng = "PINK_STRIPE_BOTTOM"; break;
					case STRIPE_TOP: ger = "Rosa Bannerhaupt"; eng = "PINK_STRIPE_TOP"; break;
					case STRIPE_LEFT: ger = "Rosa rechte Flanke"; eng = "PINK_STRIPE_LEFT"; break;
					case STRIPE_RIGHT: ger = "Rosa linke Flanke"; eng = "PINK_STRIPE_RIGHT"; break;
					case STRIPE_CENTER: ger = "Rosa Pfahl"; eng = "PINK_STRIPE_CENTER"; break;
					case STRIPE_MIDDLE: ger = "Rosa Balken"; eng = "PINK_STRIPE_MIDDLE"; break;
					case STRIPE_DOWNRIGHT: ger = "Rosa Schrägbalken"; eng = "PINK_STRIPE_DOWNRIGHT"; break;
					case STRIPE_DOWNLEFT: ger = "Rosa Schräglinksbalken"; eng = "PINK_STRIPE_DOWNLEFT"; break;
					case SMALL_STRIPES: ger = "Vier rosa Pfähle"; eng = "PINK_STRIPE_SMALL"; break;
					case CROSS: ger = "Rosa Andreaskreuz"; eng = "PINK_CROSS"; break;
					case STRAIGHT_CROSS: ger = "Rosa Kreuz"; eng = "PINK_STRAIGHT_CROSS"; break;
					case TRIANGLE_BOTTOM: ger = "Rosa halbe Spitze"; eng = "PINK_TRIANGLE_BOTTOM"; break;
					case TRIANGLE_TOP: ger = "Rosa gestürzte halbe Spitze"; eng = "PINK_TRIANGLE_TOP"; break;
					case TRIANGLES_BOTTOM: ger = "Rosa gespickelter Bannerfuß"; eng = "PINK_TRIANGLES_BOTTOM"; break;
					case TRIANGLES_TOP: ger = "Rosa gespickeltes Bannerhaupt"; eng = "PINK_TRIANGLES_TOP"; break;
					case DIAGONAL_LEFT: ger = "Rosa schräglinks geteilt"; eng = "PINK_DIAGONAL_LEFT"; break;
					case DIAGONAL_RIGHT: ger = "Rosa schräglinks geteilt (Invertiert"; eng = "PINK_DIAGONAL_RIGHT"; break;
					case DIAGONAL_UP_LEFT: ger = "Rosa schrägrechts geteilt (Invertiert)"; eng = "PINK_DIAGONAL_LEFT_MIRROR"; break;
					case DIAGONAL_UP_RIGHT: ger = "Rosa schrägrechts geteilt"; eng = "PINK_DIAGONAL_RIGHT_MIRROR"; break;
					case CIRCLE: ger = "Rosa Kugel"; eng = "PINK_CIRCLE_MIDDLE"; break;
					case RHOMBUS: ger = "Rosa Raute"; eng = "PINK_RHOMBUS_MIDDLE"; break;
					case HALF_VERTICAL: ger = "Rechts rosa gespalten"; eng = "PINK_HALF_VERTICAL"; break;
					case HALF_HORIZONTAL: ger = "Oben rosa geteilt"; eng = "PINK_HALF_HORIZONTAL"; break;
					case HALF_VERTICAL_RIGHT: ger = "Links rosa gespalten"; eng = "PINK_HALF_VERTICAL_MIRROR"; break;
					case HALF_HORIZONTAL_BOTTOM: ger = "Unten rosa geteilt"; eng = "PINK_HALF_HORIZONTAL_MIRROR"; break;
					case BORDER: ger = "Rosa Bord"; eng = "PINK_BORDER"; break;
					case CURLY_BORDER: ger = "Rosa Spickelbord"; eng = "PINK_CURLY_BORDER"; break;
					case CREEPER: ger = "Rosa Creeper"; eng = "PINK_CREEPER"; break;
					case GRADIENT: ger = "Rosa Farbverlauf"; eng = "PINK_GRADIENT"; break;
					case GRADIENT_UP: ger = "Rosa Farbverlauf (Invertiert)"; eng = "PINK_GRADIENT_UP"; break;
					case BRICKS: ger = "Feld rosa gemauert"; eng = "PINK_BRICKS"; break;
					case SKULL: ger = "Rosa Schädel"; eng = "PINK_SKULL"; break;
					case FLOWER: ger = "Rosa Blume"; eng = "PINK_FLOWER"; break;
					case MOJANG: ger = "Rosanes Mojang-Logo"; eng = "PINK_MOJANG"; break;
					case GLOBE: ger = "Rosa Globus"; eng = "PINK_GLOBE"; break;
					case PIGLIN: ger = "Rosa Schnauze"; eng = "PINK_PIGLIN"; break;
					case FLOW: ger = "Rosa Spirale"; eng = dc.toString()+"_"+p.toString(); break;
					case GUSTER: ger = "Rosa Wither"; eng = dc.toString()+"_"+p.toString(); break;
					}
					break;
				case PURPLE:
					switch(p)
					{
					case BASE: ger = "Violettes Banner"; eng = "PURPLE_BASE"; break;
					case SQUARE_BOTTOM_LEFT: ger = "Violettes rechtes Untereck"; eng = "PURPLE_SQUARE_BOTTOM_LEFT"; break;
					case SQUARE_BOTTOM_RIGHT: ger = "Violettes linkes Untereck"; eng = "PURPLE_SQUARE_BOTTOM_RIGHT"; break;
					case SQUARE_TOP_LEFT: ger = "Violettes rechtes Obereck"; eng = "PURPLE_SQUARE_TOP_LEFT"; break;
					case SQUARE_TOP_RIGHT: ger = "Violettes linkes Obereck"; eng = "PURPLE_SQUARE_TOP_RIGHT"; break;
					case STRIPE_BOTTOM: ger = "Violetter Bannerfuß"; eng = "PURPLE_STRIPE_BOTTOM"; break;
					case STRIPE_TOP: ger = "Violettes Bannerhaupt"; eng = "PURPLE_STRIPE_TOP"; break;
					case STRIPE_LEFT: ger = "Violette rechte Flanke"; eng = "PURPLE_STRIPE_LEFT"; break;
					case STRIPE_RIGHT: ger = "Violette linke Flanke"; eng = "PURPLE_STRIPE_RIGHT"; break;
					case STRIPE_CENTER: ger = "Violetter Pfahl"; eng = "PURPLE_STRIPE_CENTER"; break;
					case STRIPE_MIDDLE: ger = "Violetter Balken"; eng = "PURPLE_STRIPE_MIDDLE"; break;
					case STRIPE_DOWNRIGHT: ger = "Violetter Schrägbalken"; eng = "PURPLE_STRIPE_DOWNRIGHT"; break;
					case STRIPE_DOWNLEFT: ger = "Violetter Schräglinksbalken"; eng = "PURPLE_STRIPE_DOWNLEFT"; break;
					case SMALL_STRIPES: ger = "Vier violette Pfähle"; eng = "PURPLE_STRIPE_SMALL"; break;
					case CROSS: ger = "Violettes Andreaskreuz"; eng = "PURPLE_CROSS"; break;
					case STRAIGHT_CROSS: ger = "Violettes Kreuz"; eng = "PURPLE_STRAIGHT_CROSS"; break;
					case TRIANGLE_BOTTOM: ger = "Violette halbe Spitze"; eng = "PURPLE_TRIANGLE_BOTTOM"; break;
					case TRIANGLE_TOP: ger = "Violette gestürzte halbe Spitze"; eng = "PURPLE_TRIANGLE_TOP"; break;
					case TRIANGLES_BOTTOM: ger = "Violetter gespickelter Bannerfuß"; eng = "PURPLE_TRIANGLES_BOTTOM"; break;
					case TRIANGLES_TOP: ger = "Violettes gespickeltes Bannerhaupt"; eng = "PURPLE_TRIANGLES_TOP"; break;
					case DIAGONAL_LEFT: ger = "Violett schräglinks geteilt"; eng = "PURPLE_DIAGONAL_LEFT"; break;
					case DIAGONAL_RIGHT: ger = "Violett schräglinks geteilt (Invertiert"; eng = "PURPLE_DIAGONAL_RIGHT"; break;
					case DIAGONAL_UP_LEFT: ger = "Violett schrägrechts geteilt (Invertiert)"; eng = "PURPLE_DIAGONAL_LEFT_MIRROR"; break;
					case DIAGONAL_UP_RIGHT: ger = "Violett schrägrechts geteilt"; eng = "PURPLE_DIAGONAL_RIGHT_MIRROR"; break;
					case CIRCLE: ger = "Violette Kugel"; eng = "PURPLE_CIRCLE_MIDDLE"; break;
					case RHOMBUS: ger = "Violette Raute"; eng = "PURPLE_RHOMBUS_MIDDLE"; break;
					case HALF_VERTICAL: ger = "Rechts violett gespalten"; eng = "PURPLE_HALF_VERTICAL"; break;
					case HALF_HORIZONTAL: ger = "Oben violett geteilt"; eng = "PURPLE_HALF_HORIZONTAL"; break;
					case HALF_VERTICAL_RIGHT: ger = "Links violett gespalten"; eng = "PURPLE_HALF_VERTICAL_MIRROR"; break;
					case HALF_HORIZONTAL_BOTTOM: ger = "Unten violett geteilt"; eng = "PURPLE_HALF_HORIZONTAL_MIRROR"; break;
					case BORDER: ger = "Violetter Bord"; eng = "PURPLE_BORDER"; break;
					case CURLY_BORDER: ger = "Violetter Spickelbord"; eng = "PURPLE_CURLY_BORDER"; break;
					case CREEPER: ger = "Violetter Creeper"; eng = "PURPLE_CREEPER"; break;
					case GRADIENT: ger = "Violetter Farbverlauf"; eng = "PURPLE_GRADIENT"; break;
					case GRADIENT_UP: ger = "Violetter Farbverlauf (Invertiert)"; eng = "PURPLE_GRADIENT_UP"; break;
					case BRICKS: ger = "Feld violett gemauert"; eng = "PURPLE_BRICKS"; break;
					case SKULL: ger = "Violetter Schädel"; eng = "PURPLE_SKULL"; break;
					case FLOWER: ger = "Violette Blume"; eng = "PURPLE_FLOWER"; break;
					case MOJANG: ger = "Violettes Mojang-Logo"; eng = "PURPLE_MOJANG"; break;
					case GLOBE: ger = "Violetter Globus"; eng = "PURPLE_GLOBE"; break;
					case PIGLIN: ger = "Violette Schnauze"; eng = "PURPLE_PIGLIN"; break;
					case FLOW: ger = "Violette Spirale"; eng = dc.toString()+"_"+p.toString(); break;
					case GUSTER: ger = "Violetter Wither"; eng = dc.toString()+"_"+p.toString(); break;
					}
					break;
				case RED:
					switch(p)
					{
					case BASE: ger = "Rotes Banner"; eng = "RED_BASE"; break;
					case SQUARE_BOTTOM_LEFT: ger = "Rotes rechtes Untereck"; eng = "RED_SQUARE_BOTTOM_LEFT"; break;
					case SQUARE_BOTTOM_RIGHT: ger = "Rotes linkes Untereck"; eng = "RED_SQUARE_BOTTOM_RIGHT"; break;
					case SQUARE_TOP_LEFT: ger = "Rotes rechtes Obereck"; eng = "RED_SQUARE_TOP_LEFT"; break;
					case SQUARE_TOP_RIGHT: ger = "Rotes linkes Obereck"; eng = "RED_SQUARE_TOP_RIGHT"; break;
					case STRIPE_BOTTOM: ger = "Roter Bannerfuß"; eng = "RED_STRIPE_BOTTOM"; break;
					case STRIPE_TOP: ger = "Rotes Bannerhaupt"; eng = "RED_STRIPE_TOP"; break;
					case STRIPE_LEFT: ger = "Rote rechte Flanke"; eng = "RED_STRIPE_LEFT"; break;
					case STRIPE_RIGHT: ger = "Rote linke Flanke"; eng = "RED_STRIPE_RIGHT"; break;
					case STRIPE_CENTER: ger = "Roter Pfahl"; eng = "RED_STRIPE_CENTER"; break;
					case STRIPE_MIDDLE: ger = "Roter Balken"; eng = "RED_STRIPE_MIDDLE"; break;
					case STRIPE_DOWNRIGHT: ger = "Roter Schrägbalken"; eng = "RED_STRIPE_DOWNRIGHT"; break;
					case STRIPE_DOWNLEFT: ger = "Roter Schräglinksbalken"; eng = "RED_STRIPE_DOWNLEFT"; break;
					case SMALL_STRIPES: ger = "Vier rote Pfähle"; eng = "RED_STRIPE_SMALL"; break;
					case CROSS: ger = "Rotes Andreaskreuz"; eng = "RED_CROSS"; break;
					case STRAIGHT_CROSS: ger = "Rotes Kreuz"; eng = "RED_STRAIGHT_CROSS"; break;
					case TRIANGLE_BOTTOM: ger = "Rote halbe Spitze"; eng = "RED_TRIANGLE_BOTTOM"; break;
					case TRIANGLE_TOP: ger = "Rote gestürzte halbe Spitze"; eng = "RED_TRIANGLE_TOP"; break;
					case TRIANGLES_BOTTOM: ger = "Roter gespickelter Bannerfuß"; eng = "RED_TRIANGLES_BOTTOM"; break;
					case TRIANGLES_TOP: ger = "Rotes gespickeltes Bannerhaupt"; eng = "RED_TRIANGLES_TOP"; break;
					case DIAGONAL_LEFT: ger = "Rot schräglinks geteilt"; eng = "RED_DIAGONAL_LEFT"; break;
					case DIAGONAL_RIGHT: ger = "Rot schräglinks geteilt (Invertiert"; eng = "RED_DIAGONAL_RIGHT"; break;
					case DIAGONAL_UP_LEFT: ger = "Rot schrägrechts geteilt (Invertiert)"; eng = "RED_DIAGONAL_LEFT_MIRROR"; break;
					case DIAGONAL_UP_RIGHT: ger = "Rot schrägrechts geteilt"; eng = "RED_DIAGONAL_RIGHT_MIRROR"; break;
					case CIRCLE: ger = "Rote Kugel"; eng = "RED_CIRCLE_MIDDLE"; break;
					case RHOMBUS: ger = "Rote Raute"; eng = "RED_RHOMBUS_MIDDLE"; break;
					case HALF_VERTICAL: ger = "Rechts rot gespalten"; eng = "RED_HALF_VERTICAL"; break;
					case HALF_HORIZONTAL: ger = "Oben rot geteilt"; eng = "RED_HALF_HORIZONTAL"; break;
					case HALF_VERTICAL_RIGHT: ger = "Links rot gespalten"; eng = "RED_HALF_VERTICAL_MIRROR"; break;
					case HALF_HORIZONTAL_BOTTOM: ger = "Unten rot geteilt"; eng = "RED_HALF_HORIZONTAL_MIRROR"; break;
					case BORDER: ger = "Roter Bord"; eng = "RED_BORDER"; break;
					case CURLY_BORDER: ger = "Roter Spickelbord"; eng = "RED_CURLY_BORDER"; break;
					case CREEPER: ger = "Roter Creeper"; eng = "RED_CREEPER"; break;
					case GRADIENT: ger = "Roter Farbverlauf"; eng = "RED_GRADIENT"; break;
					case GRADIENT_UP: ger = "Roter Farbverlauf (Invertiert)"; eng = "case GRADIENT_UP"; break;
					case BRICKS: ger = "Feld rot gemauert"; eng = "RED_BRICKS"; break;
					case SKULL: ger = "Roter Schädel"; eng = "RED_SKULL"; break;
					case FLOWER: ger = "Rote Blume"; eng = "RED_FLOWER"; break;
					case MOJANG: ger = "Rotes Mojang-Logo"; eng = "RED_MOJANG"; break;
					case GLOBE: ger = "Roter Globus"; eng = "RED_GLOBE"; break;
					case PIGLIN: ger = "Rote Schnauze"; eng = "RED_PIGLIN"; break;
					case FLOW: ger = "Rote Spirale"; eng = dc.toString()+"_"+p.toString(); break;
					case GUSTER: ger = "Roter Wither"; eng = dc.toString()+"_"+p.toString(); break;
					}
					break;
				case WHITE:
					switch(p)
					{
					case BASE: ger = "Weißes Banner"; eng = "WHITE_BASE"; break;
					case SQUARE_BOTTOM_LEFT: ger = "Weißes rechtes Untereck"; eng = "WHITE_SQUARE_BOTTOM_LEFT"; break;
					case SQUARE_BOTTOM_RIGHT: ger = "Weißes linkes Untereck"; eng = "WHITE_SQUARE_BOTTOM_RIGHT"; break;
					case SQUARE_TOP_LEFT: ger = "Weißes rechtes Obereck"; eng = "WHITE_SQUARE_TOP_LEFT"; break;
					case SQUARE_TOP_RIGHT: ger = "Weißes linkes Obereck"; eng = "WHITE_SQUARE_TOP_RIGHT"; break;
					case STRIPE_BOTTOM: ger = "Weißer Bannerfuß"; eng = "WHITE_STRIPE_BOTTOM"; break;
					case STRIPE_TOP: ger = "Weißes Bannerhaupt"; eng = "WHITE_STRIPE_TOP"; break;
					case STRIPE_LEFT: ger = "Weiße rechte Flanke"; eng = "WHITE_STRIPE_LEFT"; break;
					case STRIPE_RIGHT: ger = "Weiße linke Flanke"; eng = "WHITE_STRIPE_RIGHT"; break;
					case STRIPE_CENTER: ger = "Weißer Pfahl"; eng = "WHITE_STRIPE_CENTER"; break;
					case STRIPE_MIDDLE: ger = "Weißer Balken"; eng = "WHITE_STRIPE_MIDDLE"; break;
					case STRIPE_DOWNRIGHT: ger = "Weißer Schrägbalken"; eng = "WHITE_STRIPE_DOWNRIGHT"; break;
					case STRIPE_DOWNLEFT: ger = "Weißer Schräglinksbalken"; eng = "WHITE_STRIPE_DOWNLEFT"; break;
					case SMALL_STRIPES: ger = "Vier weiße Pfähle"; eng = "WHITE_STRIPE_SMALL"; break;
					case CROSS: ger = "Weißes Andreaskreuz"; eng = "WHITE_CROSS"; break;
					case STRAIGHT_CROSS: ger = "Weißes Kreuz"; eng = "WHITE_STRAIGHT_CROSS"; break;
					case TRIANGLE_BOTTOM: ger = "Weiße halbe Spitze"; eng = "WHITE_TRIANGLE_BOTTOM"; break;
					case TRIANGLE_TOP: ger = "Weiße gestürzte halbe Spitze"; eng = "WHITE_TRIANGLE_TOP";  break;
					case TRIANGLES_BOTTOM: ger = "Weißer gespickelter Bannerfuß"; eng = "WHITE_TRIANGLES_BOTTOM"; break;
					case TRIANGLES_TOP: ger = "Weißes gespickeltes Bannerhaupt"; eng = "WHITE_TRIANGLES_TOP"; break;
					case DIAGONAL_LEFT: ger = "Weiß schräglinks geteilt"; eng = "WHITE_DIAGONAL_LEFT";  break;
					case DIAGONAL_RIGHT: ger = "Weiß schräglinks geteilt (Invertiert)"; eng = "WHITE_DIAGONAL_RIGHT"; break;
					case DIAGONAL_UP_LEFT: ger = "Weiß schrägrechts geteilt (Invertiert)"; eng = "WHITE_DIAGONAL_LEFT_MIRROR"; break;
					case DIAGONAL_UP_RIGHT: ger = "Weiß schrägrechts geteilt"; eng = "WHITE_DIAGONAL_RIGHT_MIRROR"; break;
					case CIRCLE: ger = "Weiße Kugel"; eng = "WHITE_CIRCLE_MIDDLE"; break;
					case RHOMBUS: ger = "Weiße Raute"; eng = "WHITE_RHOMBUS_MIDDLE"; break;
					case HALF_VERTICAL: ger = "Rechts weiß gespalten"; eng = "WHITE_HALF_VERTICAL"; break;
					case HALF_HORIZONTAL: ger = "Oben weiß geteilt"; eng = "WHITE_HALF_HORIZONTAL"; break;
					case HALF_VERTICAL_RIGHT: ger = "Links weiß gespalten"; eng = "WHITE_HALF_VERTICAL_MIRROR"; break;
					case HALF_HORIZONTAL_BOTTOM: ger = "Unten weiß geteilt"; eng = "WHITE_HALF_HORIZONTAL_MIRROR"; break;
					case BORDER: ger = "Weißer Bord"; eng = "WHITE_BORDER"; break;
					case CURLY_BORDER: ger = "Weißer Spickelbord"; eng = "WHITE_CURLY_BORDER"; break;
					case CREEPER: ger = "Weißer Creeper"; eng = "WHITE_CREEPER"; break;
					case GRADIENT: ger = "Weißer Farbverlauf"; eng = "WHITE_GRADIENT"; break;
					case GRADIENT_UP: ger = "Weißer Farbverlauf (Invertiert)"; eng = "WHITE_GRADIENT_UP"; break;
					case BRICKS: ger = "Feld weiß gemauert"; eng = "WHITE_BRICKS"; break;
					case SKULL: ger = "Weißer Schädel"; eng = "WHITE_SKULL"; break;
					case FLOWER: ger = "Weiße Blume"; eng = "WHITE_FLOWER"; break;
					case MOJANG: ger = "Weißes Mojang-Logo"; eng = "WHITE_MOJANG"; break;
					case GLOBE: ger = "Weißer Globus"; eng = "WHITE_GLOBE"; break;
					case PIGLIN: ger = "Weiße Schnauze"; eng = "WHITE_PIGLIN"; break;
					case FLOW: ger = "Weiße Spirale"; eng = dc.toString()+"_"+p.toString(); break;
					case GUSTER: ger = "Weißer Wither"; eng = dc.toString()+"_"+p.toString(); break;
					}
					break;
				case YELLOW:
					switch(p)
					{
					case BASE: ger = "Gelbes Banner"; eng = "YELLOW_BASE"; break;
					case SQUARE_BOTTOM_LEFT: ger = "Gelbes rechtes Untereck"; eng = "YELLOW_SQUARE_BOTTOM_LEFT"; break;
					case SQUARE_BOTTOM_RIGHT: ger = "Gelbes linkes Untereck"; eng = "YELLOW_SQUARE_BOTTOM_RIGHT"; break;
					case SQUARE_TOP_LEFT: ger = "Gelbes rechtes Obereck"; eng = "YELLOW_SQUARE_TOP_LEFT"; break;
					case SQUARE_TOP_RIGHT: ger = "Gelbes linkes Obereck"; eng = "YELLOW_SQUARE_TOP_RIGHT"; break;
					case STRIPE_BOTTOM: ger = "Gelber Bannerfuß"; eng = "YELLOW_STRIPE_BOTTOM"; break;
					case STRIPE_TOP: ger = "Gelbes Bannerhaupt"; eng = "YELLOW_STRIPE_TOP"; break;
					case STRIPE_LEFT: ger = "Gelbe rechte Flanke"; eng = "YELLOW_STRIPE_LEFT"; break;
					case STRIPE_RIGHT: ger = "Gelbe linke Flanke"; eng = "YELLOW_STRIPE_RIGHT"; break;
					case STRIPE_CENTER: ger = "Gelber Pfahl"; eng = "YELLOW_STRIPE_CENTER"; break;
					case STRIPE_MIDDLE: ger = "Gelber Balken"; eng = "YELLOW_STRIPE_MIDDLE"; break;
					case STRIPE_DOWNRIGHT: ger = "Gelber Schrägbalken"; eng = "YELLOW_STRIPE_DOWNRIGHT"; break;
					case STRIPE_DOWNLEFT: ger = "Gelber Schräglinksbalken"; eng = "YELLOW_STRIPE_DOWNLEFT"; break;
					case SMALL_STRIPES: ger = "Vier gelbe Pfähle"; eng = "YELLOW_STRIPE_SMALL"; break;
					case CROSS: ger = "Gelbes Andreaskreuz"; eng = "YELLOW_CROSS"; break;
					case STRAIGHT_CROSS: ger = "Gelbes Kreuz"; eng = "YELLOW_STRAIGHT_CROSS"; break;
					case TRIANGLE_BOTTOM: ger = "Gelbe halbe Spitze"; eng = "YELLOW_TRIANGLE_BOTTOM"; break;
					case TRIANGLE_TOP: ger = "Gelbe gestürzte halbe Spitze"; eng = "YELLOW_TRIANGLE_TOP"; break;
					case TRIANGLES_BOTTOM: ger = "Gelber gespickelter Bannerfuß"; eng = "YELLOW_TRIANGLES_BOTTOM"; break;
					case TRIANGLES_TOP: ger = "Gelbes gespickeltes Bannerhaupt"; eng = "YELLOW_TRIANGLES_TOP"; break;
					case DIAGONAL_LEFT: ger = "Gelb schräglinks geteilt"; eng = "YELLOW_DIAGONAL_LEFT"; break;
					case DIAGONAL_RIGHT: ger = "Gelb schräglinks geteilt (Invertiert"; eng = "YELLOW_DIAGONAL_RIGHT"; break;
					case DIAGONAL_UP_LEFT: ger = "Gelb schrägrechts geteilt (Invertiert)"; eng = "YELLOW_DIAGONAL_LEFT_MIRROR"; break;
					case DIAGONAL_UP_RIGHT: ger = "Gelb schrägrechts geteilt"; eng = "YELLOW_DIAGONAL_RIGHT_MIRROR"; break;
					case CIRCLE: ger = "Gelbe Kugel"; eng = "YELLOW_CIRCLE_MIDDLE"; break;
					case RHOMBUS: ger = "Gelbe Raute"; eng = "YELLOW_RHOMBUS_MIDDLE"; break;
					case HALF_VERTICAL: ger = "Rechts gelb gespalten"; eng = "YELLOW_HALF_VERTICAL"; break;
					case HALF_HORIZONTAL: ger = "Oben gelb geteilt"; eng = "YELLOW_HALF_HORIZONTAL"; break;
					case HALF_VERTICAL_RIGHT: ger = "Links gelb gespalten"; eng = "YELLOW_HALF_VERTICAL_MIRROR"; break;
					case HALF_HORIZONTAL_BOTTOM: ger = "Unten gelb geteilt"; eng = "YELLOW_HALF_HORIZONTAL_MIRROR"; break;
					case BORDER: ger = "Gelber Bord"; eng = "YELLOW_BORDER"; break;
					case CURLY_BORDER: ger = "Gelber Spickelbord"; eng = "YELLOW_CURLY_BORDER"; break;
					case CREEPER: ger = "Gelber Creeper"; eng = "YELLOW_CREEPER"; break;
					case GRADIENT: ger = "Gelber Farbverlauf"; eng = "YELLOW_GRADIENT"; break;
					case GRADIENT_UP: ger = "Gelber Farbverlauf (Invertiert)"; eng = "YELLOW_GRADIENT_UP"; break;
					case BRICKS: ger = "Feld gelb gemauert"; eng = "YELLOW_BRICKS"; break;
					case SKULL: ger = "Gelber Schädel"; eng = "YELLOW_SKULL"; break;
					case FLOWER: ger = "Gelbe Blume"; eng = "YELLOW_FLOWER"; break;
					case MOJANG: ger = "Gelbes Mojang-Logo"; eng = "YELLOW_MOJANG"; break;
					case GLOBE: ger = "Gelber Globus"; eng = "YELLOW_GLOBE"; break;
					case PIGLIN: ger = "Gelbe Schnauze"; eng = "YELLOW_PIGLIN"; break;
					case FLOW: ger = "Gelbe Spirale"; eng = dc.toString()+"_"+p.toString(); break;
					case GUSTER: ger = "Gelber Wither"; eng = dc.toString()+"_"+p.toString(); break;
					}
					break;
				}
								
				bannerlanguageKeys.put(dc.toString()+"_"+p.toString(), 
						new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
								ger, eng}));
			}
		}
	}
	
	public void initItemFlagLanguage() //INFO:ItemFlagLanguages
	{
		for(org.bukkit.inventory.ItemFlag i : org.bukkit.inventory.ItemFlag.values())
		{
			String ger = "";
			String eng = "";
			switch(i)
			{
			case HIDE_ARMOR_TRIM: ger = "Versteckte Rüstungsdekoration"; eng = "Hide Armor Trim"; break;
			case HIDE_ATTRIBUTES: ger = "Verstecke Attribute"; eng = "Hide Attributes"; break;
			case HIDE_DESTROYS: ger = "Verstecke Abbausmöglichkeit"; eng = "Hide Destroys"; break;
			case HIDE_DYE: ger = "Verstecke Einfärbung"; eng = "Hide Dye"; break;
			case HIDE_ENCHANTS: ger = "Verstecke Verzauberung"; eng = "Hide Enchants"; break;
			case HIDE_PLACED_ON: ger = "Verstecke Baumöglichkeit"; eng = "Hide Place on"; break;
			//case HIDE_POTION_EFFECTS: ger = "Verstecke Trankeffekte"; eng = "Hide Potion Effects"; break;
			case HIDE_UNBREAKABLE: ger = "Verstecke Unzerstörbarkeit"; eng = "Hide Unbreakable"; break;
			case HIDE_ADDITIONAL_TOOLTIP: ger = "Verstecke additionale Hilfetipps"; eng = "Hide additional tooltip"; break;
			}
			itemflaglanguageKeys.put(i.toString(), 
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger, eng}));
		}
	}
	
	public void initPotionTypeLanguage() //INFO:PotionTypeLanguages
	{
		
		for(org.bukkit.potion.PotionType pt : org.bukkit.potion.PotionType.values())
		{
			String ger = "";
			String eng = "";
			switch(pt)
			{
			case AWKWARD: ger = "Seltsamer Trank"; eng = "Awkward Potion"; break;
			case FIRE_RESISTANCE: ger = "Feuerresistenz"; eng = "Fire Resistance"; break;
			case LONG_FIRE_RESISTANCE: ger = "Feuerresistenz (verlängert)"; eng = "Fire Resistance (extended)"; break;
			case STRONG_HARMING: ger = "Schaden (verstärkt)"; eng = "Harm (amplified)"; break;
			case HARMING: ger = "Schaden"; eng = "Harming"; break;
			case HEALING: ger = "Heilung"; eng = "Healing"; break;
			case STRONG_HEALING: ger = "Heilung (verstärkt)"; eng = "Heal (amplified)"; break;
			case INVISIBILITY: ger = "Unsichtbarkeit"; eng = "Invisbility"; break;
			case LONG_INVISIBILITY: ger = "Unsichtbarkeit (verlängert)"; eng = "Invisbility (extended)"; break;
			case LEAPING: ger = "Sprungkraft"; eng = "Jump"; break;
			case LUCK: ger = "Glück"; eng = "Luck"; break;
			case LONG_LEAPING: ger = "Sprungkraft (verlängert)"; eng = "Jump (extended)"; break;
			case STRONG_LEAPING: ger = "Sprungkraft (verstärkt)"; eng = "Jump (amplified)"; break;
			case MUNDANE: ger = "Gewöhnlicher Trank"; eng = "Mundane Potion"; break;
			case NIGHT_VISION: ger = "Nachtsicht"; eng = "Night Vison"; break;
			case LONG_NIGHT_VISION: ger = "Nachtsicht (verlängert)"; eng = "Night Vison (extended)"; break;
			case POISON: ger = "Vergiftung"; eng = "Poison"; break;
			case LONG_POISON: ger = "Vergiftung (verlängert)"; eng = "Poison (extended)"; break;
			case STRONG_POISON: ger = "Vergiftung (verstärkt)"; eng = "Poison (amplified)"; break;
			case REGENERATION: ger = "Regeneration"; eng = "Regeneration"; break;
			case LONG_REGENERATION: ger = "Regeneration (verlängert)"; eng = "Regeneration (extended)"; break;
			case STRONG_REGENERATION: ger = "Regeneration (verstärkt)"; eng = "Regeneration (amplified)"; break;
			case SLOW_FALLING: ger = "sanfter Fall"; eng = "Slow Falling"; break;
			case LONG_SLOW_FALLING: ger = "sanfter Fall (verlängert)"; eng = "Slow Falling (extended)"; break;
			case SLOWNESS: ger = "Langsamkeit"; eng = "Slowness"; break;
			case LONG_SLOWNESS: ger = "Langsamkeit (verlängert)"; eng = "Slowness (extended)"; break;
			case STRONG_SLOWNESS: ger = "Langsamkeit (verstärkt)"; eng = "Slowness (amplified)"; break;
			case STRENGTH: ger = "Stärke"; eng = "Strength"; break;
			case LONG_STRENGTH: ger = "Stärke (verlängert)"; eng = "Strength (extended)"; break;
			case STRONG_STRENGTH: ger = "Stärke (verstärkt)"; eng = "Strength (amplified)"; break;
			case SWIFTNESS: ger = "Schnelligkeit"; eng = "Swiftness"; break;
			case LONG_SWIFTNESS: ger = "Schnelligkeit (verlängert)"; eng = "Swiftness (extended)"; break;
			case STRONG_SWIFTNESS: ger = "Schnelligkeit (verstärkt)"; eng = "Swiftness (amplified)"; break;
			case THICK: ger = "Dickflüssiger Trank"; eng = "Thick Potion"; break;
			case TURTLE_MASTER: ger = "Schildkrötenmeister"; eng = "Turtle Master"; break;
			case LONG_TURTLE_MASTER: ger = "Schildkrötenmeister (verlängert)"; eng = "Turtle Master (extended)"; break;
			case STRONG_TURTLE_MASTER: ger = "Schildkrötenmeister (verstärkt)"; eng = "Turtle Master (amplified)"; break;
			case WATER: ger = "Wasserflasche"; eng = "Waterbottle"; break;
			case WATER_BREATHING: ger = "Unterwasseratmung"; eng = "Water Breathing"; break;
			case LONG_WATER_BREATHING: ger = "Unterwasseratmung (verlängert)"; eng = "Water Breathing (extended)"; break;
			case WEAKNESS: ger = "Schwäche"; eng = "Weakness"; break;
			case LONG_WEAKNESS: ger = "Schwäche (verlängert)"; eng = "Weakness (extended)"; break;
			case INFESTED: ger = "Befallen"; eng = "Infested"; break;
			case OOZING: ger = "Schleimen"; eng = "Oozing"; break;
			case WEAVING: ger = "Weben"; eng = "Weaving"; break;
			case WIND_CHARGED: ger = "Windgeladen"; eng = "Wind Charged"; break;
			}
			potiontypelanguageKeys.put(pt.toString(), 
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger, eng}));
		}
	}
	
	@SuppressWarnings("deprecation")
	public void initPotionEffectTypeLanguage() //INFO:PotionEffectTypeLanguages
	{
		for(org.bukkit.potion.PotionEffectType pet : org.bukkit.potion.PotionEffectType.values())
		{
			String ger = "";
			String eng = "";
			if(pet.getName().equals(org.bukkit.potion.PotionEffectType.ABSORPTION.getName())){ger = "Absorption"; eng = "Absorption";} 
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.BAD_OMEN.getName())){ger = "Böses Omen"; eng = "Bad Omen";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.BLINDNESS.getName())){ger = "Blindheit"; eng = "Blindness";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.CONDUIT_POWER.getName())){ger = "Meereskraft"; eng = "Conduit Power";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.DARKNESS.getName())){ger = "Dunkelheit"; eng = "Darkness";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.DOLPHINS_GRACE.getName())){ger = "Gunst des Delphins"; eng = "Dolphins Grace";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.FIRE_RESISTANCE.getName())){ger = "Feuerresistenz"; eng = "Fireresistance";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.GLOWING.getName())){ger = "Leuchten"; eng = "Leuchten";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.HASTE.getName())){ger = "Eile"; eng = "Haste";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.HEALTH_BOOST.getName())){ger = "Extra Energie"; eng = "Health Boost";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.HERO_OF_THE_VILLAGE.getName())){ger = "Held des Dorfes"; eng = "Hero of the Village";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.HUNGER.getName())){ger = "Hunger"; eng = "Hunger";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.INFESTED.getName())){ger = "Befallen"; eng = "infested";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.INSTANT_DAMAGE.getName())){ger = "Schaden"; eng = "Harm";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.INSTANT_HEALTH.getName())){ger = "Heilung"; eng = "Heal";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.INVISIBILITY.getName())){ger = "Unsichtbarkeit"; eng = "Invisibility";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.JUMP_BOOST.getName())){ger = "Sprungkraft"; eng = "Jump";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.LEVITATION.getName())){ger = "Schwebekraft"; eng = "Levitation";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.LUCK.getName())){ger = "Glück"; eng = "Luck";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.MINING_FATIGUE.getName())){ger = "Abbaulähmung"; eng = "Slow Digging";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.NAUSEA.getName())){ger = "Verwirrung"; eng = "Confusion";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.NIGHT_VISION.getName())){ger = "Nachtsicht"; eng = "Night Vision";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.OOZING.getName())){ger = "Schleimen"; eng = "Oozing";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.POISON.getName())){ger = "Vergiftung"; eng = "Poison";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.RAID_OMEN.getName())){ger = "Omen des Überfall"; eng = "Raid Omen";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.REGENERATION.getName())){ger = "Regeneration"; eng = "Regeneration";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.RESISTANCE.getName())){ger = "Resistenz"; eng = "Resistance";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.SATURATION.getName())){ger = "Sättigung"; eng = "Saturation";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.SLOWNESS.getName())){ger = "Langsamkeit"; eng = "Slow";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.STRENGTH.getName())){ger = "Stärke"; eng = "Strenght";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.SLOW_FALLING.getName())){ger = "Sanfter Fall"; eng = "Slow Falling";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.SPEED.getName())){ger = "Schnelligkeit"; eng = "Speed";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.TRIAL_OMEN.getName())){ger = "Omen des Trial"; eng = "Trial Omen";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.UNLUCK.getName())){ger = "Unglück"; eng = "Unluck";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.WATER_BREATHING.getName())){ger = "Wasseratmung"; eng = "Water Breathing";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.WEAKNESS.getName())){ger = "Schwäche"; eng = "Weakness";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.WEAVING.getName())){ger = "Weben"; eng = "Weaving";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.WIND_CHARGED.getName())){ger = "Windgeladen"; eng = "Wind Charged";}
			else if(pet.getName().equals(org.bukkit.potion.PotionEffectType.WITHER.getName())){ger = "Wither"; eng = "Wither";}			
			potioneffecttypelanguageKeys.put(pet.getName(), 
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger, eng}));
		}
	}
	
	public void initEntityType() //INFO:EntityType
	{
		for(org.bukkit.entity.EntityType i : org.bukkit.entity.EntityType.values())
		{
			String eng = "";
			String ger = "";
			switch(i)
			{
			case ALLAY: ger = "Hilfgeist"; eng = "Allay"; break;
			case AREA_EFFECT_CLOUD: ger = "Areal Effekt Wolke"; eng = "Area Effect Cloud"; break;
			case ARMOR_STAND: ger = "Rüstungsständer"; eng = "Armor Stand"; break;
			case ARROW: ger = "Pfeil"; eng = "Arrow"; break;
			case AXOLOTL: ger = "Axolotl"; eng = "Axolotl"; break;
			case BAT: ger = "Fledermaus"; eng = "Bat"; break;
			case BEE: ger = "Biene"; eng = "Bee"; break;
			case BLAZE: ger = "Lohe"; eng = "Blaze"; break;
			case BOAT: ger = "Boot"; eng = "Boat"; break;
			case CAT: ger = "Katze"; eng = "Cat"; break;
			case CAVE_SPIDER: ger = "Höhlenspinne"; eng = "Cave Spider"; break;
			case CHEST_BOAT: ger = "Kistenboot"; eng = "Chest Boat"; break;
			case CHICKEN: ger = "Huhn"; eng = "Chicken"; break;
			case COD: ger = "Kabeljau"; eng = "Cod"; break;
			case COW: ger = "Kuh"; eng = "Cow"; break;
			case CREEPER: ger = "Creeper"; eng = "Creeper"; break;
			case DOLPHIN: ger = "Delphin"; eng = "Dolphin"; break;
			case DONKEY: ger = "Esel"; eng = "Donkey"; break;
			case DRAGON_FIREBALL: ger = "Drachenfeuerball"; eng = "Dragon Fireball"; break;
			case ITEM: ger = "Fallengelassenes Item"; eng = "Dropped Item"; break;
			case DROWNED: ger = "Ertrunkener"; eng = "Drowned"; break;
			case EGG: ger = "Ei"; eng = "Egg"; break;
			case ELDER_GUARDIAN: ger = "Großer Wächter"; eng = "Elder Guardian"; break;
			case END_CRYSTAL: ger = "Ender Kristall"; eng = "Ender Crystal"; break;
			case ENDER_DRAGON: ger = "Enderdrache"; eng = "Ender Dragon"; break;
			case ENDER_PEARL: ger = "Enderperle"; eng = "Ender Pearl"; break;
			case ENDERMAN: ger = "Endermaln"; eng = "Enderman"; break;
			case ENDERMITE: ger = "Endermite"; eng = "Endermite"; break;
			case EVOKER: ger = "Magier"; eng = "Evoker"; break;
			case EVOKER_FANGS: ger = "Magierzähne"; eng = "Evoker Fangs"; break;
			case EXPERIENCE_ORB: ger = "Erfahrungsorb"; eng = "Experience Orb"; break;
			case FALLING_BLOCK: ger = "Fallender Block"; eng = "Falling Block"; break;
			case FIREBALL: ger = "Feuerball"; eng = "Fireball"; break;
			case FIREWORK_ROCKET: ger = "Feuerwerk"; eng = "Firework"; break;
			case FISHING_BOBBER: ger = "Angelhacken"; eng = "Fishing Hook"; break;
			case FOX: ger = "Fuchs"; eng = "Fox"; break;
			case FROG: ger = "Frosch"; eng = "Frog"; break;
			case GHAST: ger = "Ghast"; eng = "Ghast"; break;
			case GIANT: ger = "Riese"; eng = "Giant"; break;
			case GLOW_ITEM_FRAME: ger = "Leuchtrahmen"; eng = "Glow Item Frame"; break;
			case GLOW_SQUID: ger = "Leuchttintenfisch"; eng = "Glow Squid"; break;
			case GOAT: ger = "Ziege"; eng = "Goat"; break;
			case GUARDIAN: ger = "Wächter"; eng = "Guardian"; break;
			case HOGLIN: ger = "Hoglin"; eng = "Hoglin"; break;
			case HORSE: ger = "Pferd"; eng = "Horse"; break;
			case HUSK: ger = "Wüstenzombie"; eng = "Husk"; break;
			case ILLUSIONER: ger = "Illusionist"; eng = "Illusioner"; break;
			case IRON_GOLEM: ger = "Eisengolem"; eng = "Irom Golem"; break;
			case ITEM_FRAME: ger = "Rahmen"; eng = "Item Frame"; break;
			case LEASH_KNOT: ger = "Leinenpfosten"; eng = "Leash Hitch"; break;
			case LIGHTNING_BOLT: ger = "Blitz"; eng = "Lightning"; break;
			case LLAMA: ger = "Lama"; eng = "Llama"; break;
			case LLAMA_SPIT: ger = "Lamaspucke"; eng = "Llama Spit"; break;
			case MAGMA_CUBE: ger = "Magmawürfel"; eng = "Magma Cube"; break;
			case MARKER: ger = "Marker"; eng = "Marker"; break;
			case MINECART: ger = "Lore"; eng = "Minecart"; break;
			case CHEST_MINECART: ger = "Kistenlore"; eng = "Minecart Chest"; break;
			case COMMAND_BLOCK_MINECART: ger = "Befehlslore"; eng = "Minecart Command"; break;
			case FURNACE_MINECART: ger = "Ofenlore"; eng = "Minecart Furnace"; break;
			case HOPPER_MINECART: ger = "Trichterlore"; eng = "Minecart Hopper"; break;
			case SPAWNER_MINECART: ger = "Spawnerlore"; eng = "Minecraft Mob Spawner"; break;
			case TNT_MINECART: ger = "TNT Lore"; eng = "Minecart Lore"; break;
			case MULE: ger = "Maultier"; eng = "Mule"; break;
			case MOOSHROOM: ger = "Pilzkuh"; eng = "Mushroom Cow"; break;
			case OCELOT: ger = "Ozelot"; eng = "Ocelot"; break;
			case PAINTING: ger = "Gemälde"; eng = "Painting"; break;
			case PANDA: ger = "Panda"; eng = "Panda"; break;
			case PARROT: ger = "Papagei"; eng = "Parrot"; break;
			case PHANTOM: ger = "Phantom"; eng = "Phantom"; break;
			case PIG: ger = "Schwein"; eng = "Pig"; break;
			case PIGLIN: ger = "Piglin"; eng = "Piglin"; break;
			case PIGLIN_BRUTE: ger = "Piglin Barbar"; eng = "Piglin Brute"; break;
			case PILLAGER: ger = "Plünderer"; eng = "Pillager"; break;
			case PLAYER: ger = "Spieler"; eng = "Player"; break;
			case POLAR_BEAR: ger = "Polarbär"; eng = "Polar Bear"; break;
			case TNT: ger = "Gezündetes TNT"; eng = "Primed TNT"; break;
			case PUFFERFISH: ger = "Kugelfisch"; eng = "Pufferfish"; break;
			case RABBIT: ger = "Kaninschen"; eng = "Rabbit"; break;
			case RAVAGER: ger = "Verwüster"; eng = "Ravager"; break;
			case SALMON: ger = "Lachs"; eng = "Salmon"; break;
			case SHEEP: ger = "Schaf"; eng = "Sheep"; break;
			case SHULKER: ger = "Shulker"; eng = "Shulker"; break;
			case SHULKER_BULLET: ger = "Shulkerkugel"; eng = "Shulker Bullet"; break;
			case SILVERFISH: ger = "Silberfisch"; eng = "Silverfish"; break;
			case SKELETON: ger = "Skelett"; eng = "Skeleton"; break;
			case SKELETON_HORSE: ger = "Skelettpferd"; eng = "Skeleton Horse"; break;
			case SLIME: ger = "Schleim"; eng = "Slime"; break;
			case SMALL_FIREBALL: ger = "Kleiner Feuerball"; eng = "Small Fireball"; break;
			case SNOWBALL: ger = "Schneeball"; eng = "Snowball"; break;
			case SNOW_GOLEM: ger = "Schneeman"; eng = "Snowman"; break;
			case SPECTRAL_ARROW: ger = "Spektralpfeil"; eng = "Spectral Arrow"; break;
			case SPIDER:  ger = "Spinne"; eng = "Spider"; break;
			case SQUID: ger = "Tintenfisch"; eng = "Squid"; break;
			case STRAY: ger = "Eiswanderer"; eng = "Stray"; break;
			case STRIDER: ger = "Schreiter"; eng = "Strider"; break;
			case TADPOLE: ger = "Kaulquappe"; eng = "Tadpole"; break;
			case EXPERIENCE_BOTTLE: ger = "Geworfene Expflasche"; eng = "Thrown Exp Bottle"; break;
			case TRADER_LLAMA: ger = "Händlerlama"; eng = "Trader Llama"; break;
			case TRIDENT: ger = "Dreizack"; eng = "Trident"; break;
			case TROPICAL_FISH: ger = "Tropenfisch"; eng = "Tropical Fish"; break;
			case TURTLE: ger = "Schildkröte"; eng = "Turtle"; break;
			case UNKNOWN: ger = "Unbekannt"; eng = "Unknown"; break;
			case VEX: ger = "Plagegeist"; eng = "Vex"; break;
			case VILLAGER: ger = "Dorfbewohner"; eng = "Villager"; break;
			case VINDICATOR: ger = "Diener"; eng = "Vindicator"; break;
			case WANDERING_TRADER: ger = "Wandernder Händler"; eng = "Wandering Trader"; break;
			case WARDEN: ger = "Wärter"; eng = "Warden"; break;
			case WITCH: ger = "Hexe"; eng = "Witch"; break;
			case WITHER: ger = "Wither"; eng = "Wither"; break;
			case WITHER_SKELETON: ger = "Witherskelett"; eng = "Wither Skeleton"; break;
			case WITHER_SKULL: ger = "Witherschädel"; eng = "Wither Skull"; break;
			case WOLF: ger = "Wolf"; eng = "Wolf"; break;
			case ZOGLIN: ger = "Zoglin"; eng = "Zoglin"; break;
			case ZOMBIE: ger = "Zombie"; eng = "Zombie"; break;
			case ZOMBIE_HORSE: ger = "Zombiepferd"; eng = "Zombie Horse"; break;
			case ZOMBIE_VILLAGER: ger = "Zombiedorfbewohner"; eng = "Zombie Villager"; break;
			case ZOMBIFIED_PIGLIN: ger = "Zombiefizierter Piglin"; eng = "Zombified Piglin"; break;
			//1.20.4
			case BLOCK_DISPLAY: ger = "Blockdisplay"; eng = "Blockdisplay"; break;
			case BREEZE: ger = "Breeze"; eng = "Breeze"; break;
			case CAMEL: ger = "Kamel"; eng = "Camel"; break;
			case INTERACTION: ger = "Interaktionsobjekt"; eng = "Interaction"; break;
			case ITEM_DISPLAY: ger = "Itemdisplay"; eng = "Itemdisplay"; break;
			case SNIFFER: ger = "Sniffer"; eng = "Sniffer"; break;
			case TEXT_DISPLAY: ger = "Textdisplay"; eng = "Textdisplay"; break;
			case WIND_CHARGE: ger = "Windladung"; eng = "Windcharge"; break;
			//1.21
			case ARMADILLO: ger = "Armadillo"; eng = "Armadillo"; break;
			case BOGGED: ger = "Sumpfskelett"; eng = "Bogged"; break;
			case BREEZE_WIND_CHARGE: ger = "Böen Windladung"; eng = "Breeze Wind Charge"; break;
			case EYE_OF_ENDER: ger = "Auge des Enders"; eng = "Eye of Ender"; break;
			case OMINOUS_ITEM_SPAWNER: ger = "Ominöser Itemspawner"; eng = "Ominous Itemspawner"; break;
			case POTION: ger = "Trank"; eng = "Trank"; break;
			}
			entitytypelanguageKeys.put(i.toString(), 
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger, eng}));
		}
	}
	
	public void initAxolotlVariant() //INFO:AxolotlVariant
	{
		for(org.bukkit.entity.Axolotl.Variant i : org.bukkit.entity.Axolotl.Variant.values())
		{
			String eng = "";
			String ger = "";
			switch(i)
			{
			case BLUE: ger = "Blau"; eng = "Blue"; break;
			case CYAN: ger = "Cyan"; eng = "Cyan"; break;
			case GOLD: ger = "Gold"; eng = "Gold"; break;
			case LUCY: ger = "Lucy"; eng = "Lucy"; break;
			case WILD: ger = "Wild"; eng = "Wild"; break;
			}
			axolotlvariantlanguageKeys.put(i.toString(), 
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger, eng}));
		}
	}
	
	public void initBookMetaGeneration() //INFO:BookMetaGeneration
	{
		for(org.bukkit.inventory.meta.BookMeta.Generation i : org.bukkit.inventory.meta.BookMeta.Generation.values())
		{
			String eng = "";
			String ger = "";
			switch(i)
			{
			case COPY_OF_COPY: ger = "Kopie einer Kopie"; eng = "Copy of Copy"; break;
			case COPY_OF_ORIGINAL: ger = "Kopie des Originals"; eng = "Copy of Original"; break;
			case ORIGINAL: ger = "Original"; eng = "Original"; break;
			case TATTERED: ger = "Zerfetzt"; eng = "Tatterd"; break;
			}
			bookmetagenerationlanguageKeys.put(i.toString(), 
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger, eng}));
		}
	}
	
	public void initColor() //INFO:Color
	{
		List<org.bukkit.Color> colors = Arrays.asList(new org.bukkit.Color[]
				{
					org.bukkit.Color.AQUA, org.bukkit.Color.BLACK, org.bukkit.Color.BLUE, org.bukkit.Color.FUCHSIA, 
					org.bukkit.Color.GRAY, org.bukkit.Color.GREEN, org.bukkit.Color.LIME,
					org.bukkit.Color.MAROON, org.bukkit.Color.NAVY, org.bukkit.Color.OLIVE, org.bukkit.Color.ORANGE, 
					org.bukkit.Color.PURPLE, org.bukkit.Color.RED, org.bukkit.Color.SILVER,
					org.bukkit.Color.TEAL, org.bukkit.Color.WHITE, org.bukkit.Color.YELLOW
				});
		for(org.bukkit.Color i : colors)
		{
			String eng = "";
			String ger = "";
			if(i == org.bukkit.Color.AQUA){ger = "Aqua"; eng = "Aqua";}
			if(i == org.bukkit.Color.BLACK){ger = "Schwarz"; eng = "Black";}
			if(i == org.bukkit.Color.BLUE){ger = "Blau"; eng = "Blue";}
			if(i == org.bukkit.Color.FUCHSIA){ger = "Fuchsie"; eng = "Fuchsia";}
			if(i == org.bukkit.Color.GRAY){ger = "Grau"; eng = "Gray";}
			if(i == org.bukkit.Color.GREEN){ger = "Grün"; eng = "Green";}
			if(i == org.bukkit.Color.LIME){ger = "Limettegrün"; eng = "Lime";}
			if(i == org.bukkit.Color.MAROON){ger = "Kastanienbraun"; eng = "Maroon";}
			if(i == org.bukkit.Color.NAVY){ger = "Navyblau"; eng = "Navy";}
			if(i == org.bukkit.Color.OLIVE){ger = "Olivengrün"; eng = "Olive";}
			if(i == org.bukkit.Color.ORANGE){ger = "Orange"; eng = "Orange";}
			if(i == org.bukkit.Color.PURPLE){ger = "Violett"; eng = "Purple";}
			if(i == org.bukkit.Color.RED){ger = "Rot"; eng = "Red";}
			if(i == org.bukkit.Color.SILVER){ger = "Silber"; eng = "Silver";}
			if(i == org.bukkit.Color.TEAL){ger = "Blaugrün"; eng = "Teal";}
			if(i == org.bukkit.Color.WHITE){ger = "Weiß"; eng = "White";}
			if(i == org.bukkit.Color.YELLOW){ger = "Gelb"; eng = "Yellow";}
			
			colorlanguageKeys.put(String.valueOf(i.asRGB()), 
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger, eng}));
		}
	}
	
	public void initDyeColor() //INFO:DyeColor
	{
		for(org.bukkit.DyeColor i : org.bukkit.DyeColor.values())
		{
			String eng = "";
			String ger = "";
			switch(i)
			{
			case BLACK: ger = "Schwarz"; eng = "Black"; break;
			case BLUE: ger = "Blau"; eng = "Blue"; break;
			case BROWN: ger = "Braun"; eng = "Brown"; break;
			case CYAN: ger = "Cyan"; eng = "Cyan"; break;
			case GRAY: ger = "Grau"; eng = "Gray"; break;
			case GREEN: ger = "Grün"; eng = "Green"; break;
			case LIGHT_BLUE: ger = "Hellblau"; eng = "Light Blue"; break;
			case LIGHT_GRAY: ger = "Hellgrau"; eng = "Light Gray"; break;
			case LIME: ger = "Limette"; eng = "Lime"; break;
			case MAGENTA: ger = "Magenta"; eng = "Magenta"; break;
			case ORANGE: ger = "Orange"; eng = "Orange"; break;
			case PINK: ger = "Pink"; eng = "Pink"; break;
			case PURPLE: ger = "Violett"; eng = "Purple"; break;
			case RED: ger = "Rot"; eng = "Red"; break;
			case WHITE: ger = "Weiß"; eng = "White"; break;
			case YELLOW: ger = "Gelb"; eng = "Yellow"; break;
			}
			dyecolorlanguageKeys.put(i.toString(), 
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger, eng}));
		}
	}
	
	public void initTropicalFish() //INFO:TropicalFish
	{
		for(org.bukkit.DyeColor i : org.bukkit.DyeColor.values())
		{
			String ger = "";
			String eng = "";
			switch(i)
			{
			case BLACK: ger += "Schwarzer "; break;
			case BLUE: ger += "Blauer "; break;
			case BROWN: ger += "Brauner "; break;
			case CYAN: ger += "Cyaner "; break;
			case GRAY: ger += "Grauer "; break;
			case GREEN: ger += "Grüner "; break;
			case LIGHT_BLUE: ger += "Hellblauer "; break;
			case LIGHT_GRAY: ger += "Hellgrauer "; break;
			case LIME: ger += "Limoner "; break;
			case MAGENTA: ger += "Magenta "; break;
			case ORANGE: ger += "Oranger "; break;
			case PINK: ger += "Pinker "; break;
			case PURPLE: ger += "Lilaner "; break;
			case RED: ger += "Roter "; break;
			case WHITE: ger += "Weißer "; break;
			case YELLOW: ger += "Gelber "; break;
			}
			for(org.bukkit.DyeColor h : org.bukkit.DyeColor.values())
			{
				switch(i)
				{
				case BLACK: ger += "Schwarz"; break;
				case BLUE: ger += "Blau"; break;
				case BROWN: ger += "Braun"; break;
				case CYAN: ger += "Cyan"; break;
				case GRAY: ger += "Grau"; break;
				case GREEN: ger += "Grün"; break;
				case LIGHT_BLUE: ger += "Hellblau"; break;
				case LIGHT_GRAY: ger += "Hellgrau"; break;
				case LIME: ger += "Limon"; break;
				case MAGENTA: ger += "Magenta"; break;
				case ORANGE: ger += "Orange"; break;
				case PINK: ger += "Pink"; break;
				case PURPLE: ger += "Lila"; break;
				case RED: ger += "Rot"; break;
				case WHITE: ger += "Weiß"; break;
				case YELLOW: ger += "Gelb"; break;
				}
				for(org.bukkit.entity.TropicalFish.Pattern j : org.bukkit.entity.TropicalFish.Pattern.values())
				{
					switch(j)
					{
					case BETTY: ger += "trommlerfisch"; break;
					case BLOCKFISH: ger += "blockfisch"; break;
					case BRINELY: ger += "salzfisch"; break;
					case CLAYFISH: ger += "lehmfisch"; break;
					case DASHER: ger += "flitzer"; break;
					case FLOPPER: ger += "zappler"; break;
					case GLITTER: ger += "glitzerfisch"; break;
					case KOB: ger += "peitschenfisch"; break;
					case SNOOPER: ger += "schnüffler"; break;
					case SPOTTY: ger += "fleckenlippfisch"; break;
					case STRIPEY: ger += "streifler"; break;
					case SUNSTREAK: ger += "sonnenstreifenfisch"; break;
					}
					eng = i.toString()+"_"+j.toString()+"_"+h.toString();
					tropicalfishbucketlanguageKeys.put(i.toString()+"_"+j.toString()+"_"+h.toString(), 
							new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
									ger, eng}));
				}
				
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void initCatType() //INFO:CatType
	{
		for(org.bukkit.entity.Cat.Type i : org.bukkit.entity.Cat.Type.values())
		{
			String eng = "";
			String ger = "";
			switch(i.getKey().getKey())
			{
			default: break;
			case "ALL_BLACK": ger = "Ganz Schwarz"; eng = "All Black"; break;
			case "BLACK": ger = "Schwarz"; eng = "Black"; break;
			case "BRITISH_SHORTHAIR": ger = "Britisch Kurzhaar"; eng = "British Shorthair"; break;
			case "CALICO": ger = "Calico"; eng = "Calico"; break;
			case "JELLIE": ger = "Jellie"; eng = "Jellie"; break;
			case "PERSIAN": ger = "Perser"; eng = "Persian"; break;
			case "RAGDOLL": ger = "Ragdoll"; eng = "Ragdoll"; break;
			case "RED": ger = "Rot"; eng = "Red"; break;
			case "SIAMESE": ger = "Siamese"; eng = "Siamese"; break;
			case "TABBY": ger = "Getigert"; eng = "Tabby"; break;
			case "WHITE": ger = "Weiß"; eng = "White"; break;
			}
			cattypelanguageKeys.put(i.toString(), 
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger, eng}));
		}
	}
	
	public void initFoxType() //INFO:FoxType
	{
		for(org.bukkit.entity.Fox.Type i : org.bukkit.entity.Fox.Type.values())
		{
			String eng = "";
			String ger = "";
			switch(i)
			{
			case RED: ger = "Rot"; eng = "Red"; break;
			case SNOW: ger = "Schnee"; eng = "Snow"; break;
			}
			foxtypelanguageKeys.put(i.toString(), 
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger, eng}));
		}
	}
	
	@SuppressWarnings("deprecation")
	public void initMapCursorType() //INFO:MapCursorType
	{
		for(org.bukkit.map.MapCursor.Type i : org.bukkit.map.MapCursor.Type.values())
		{
			String eng = "";
			String ger = "";
			switch(i.getKey().getKey())
			{
			default:
			case "RED_MARKER": ger = "Rote Markierung"; eng = "RED_MARKER"; break;
			case "MANSION": ger = "Waldanwesen"; eng = "MANSION"; break;
			case "BANNER_WHITE": ger = "Weißes Banner"; eng = "BANNER_WHITE"; break;
			case "BANNER_ORANGE": ger = "Oranges Banner"; eng = "BANNER_ORANGE"; break;
			case "BANNER_MAGENTA": ger = "Magenta Banner"; eng = "BANNER_MAGENTA"; break;
			case "BANNER_LIGHT_BLUE": ger = "Hellblaues Banner"; eng = "BANNER_LIGHT_BLUE"; break;
			case "BANNER_YELLOW": ger = "Gelbes Banner"; eng = "BANNER_YELLOW"; break;
			case "BANNER_LIME": ger = "Hellgrünes Banner"; eng = "BANNER_LIME"; break;
			case "BANNER_PINK": ger = "Rosa Banner"; eng = "BANNER_PINK"; break;
			case "BANNER_GRAY": ger = "Graues Banner"; eng = "BANNER_GRAY"; break;
			case "BANNER_LIGHT_GRAY": ger = "Hellgraues Banner"; eng = "BANNER_LIGHT_GRAY"; break;
			case "BANNER_CYAN": ger = "Türkises Banner"; eng = "BANNER_CYAN"; break;
			case "BANNER_PURPLE": ger = "Violettes Banner"; eng = "BANNER_PURPLE"; break;
			case "BANNER_BLUE": ger = "Blaues Banner"; eng = "BANNER_BLUE"; break;
			case "BANNER_BROWN": ger = "Braunes Banner"; eng = "BANNER_BROWN"; break;
			case "BANNER_GREEN": ger = "Grünes Banner"; eng = "BANNER_GREEN"; break;
			case "BANNER_RED": ger = "Rotes Banner"; eng = "BANNER_RED"; break;
			case "BANNER_BLACK": ger = "Schwarzes Banner"; eng = "BANNER_BLACK"; break;
			case "RED_X": ger = "Rotes X"; eng = "RED_X"; break;
			case "JUNGLE_TEMPLE": ger = "Dschungeltempel"; eng = "JUNGLE_TEMPLE"; break;
			case "SWAMP_HUT": ger = "Moorhütte"; eng = "SWAMP_HUT"; break;
			case "BLUE_MARKER": ger = "Blauer Zeiger"; eng = "Blue Marker"; break;
			case "FRAME": ger = "Rahmen"; eng = "Frame"; break;
			case "MONUMENT": ger = "Monument"; eng = "Monument"; break;
			case "PLAYER": ger = "Spieler"; eng = "Player"; break;
			case "PLAYER_OFF_LIMITS": ger = "Spieler außer Reichweite"; eng = "Player off Limits"; break;
			case "PLAYER_OFF_MAP": ger = "Spieler außerhalb der Karte"; eng = "Player off Map"; break;
			case "TARGET_POINT": ger = "Zielpunkt"; eng = "Targetpoint"; break;
			case "TARGET_X": ger = "Ziel X"; eng = "Target X"; break;
			case "TRIAL_CHAMBERS": ger = "Trialkammer"; eng = "Trial Chamber"; break;
			case "VILLAGE_DESERT": ger = "Wüstendorf"; eng = "DESERT_VILLAGE"; break;
			case "VILLAGE_PLAINS": ger = "Ebenendorf"; eng = "PLAINS_VILLAGE"; break;
			case "VILLAGE_SAVANNA": ger = "Savannadorf"; eng = "SAVANNA_VILLAGE"; break;
			case "VILLAGE_SNOWY": ger = "Verscheites Dorf"; eng = "SNOWY_VILLAGE"; break;
			case "VILLAGE_TAIGA": ger = "Taigadorf"; eng = "TAIGA_VILLAGE"; break;
			}
			mapcursortypelanguageKeys.put(i.toString(), 
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger, eng}));
		}
	}
	
	public void initRabbitType() //INFO:RabbitType
	{
		for(org.bukkit.entity.Rabbit.Type i : org.bukkit.entity.Rabbit.Type.values())
		{
			String eng = "";
			String ger = "";
			switch(i)
			{
			case BLACK: ger = "Schwarz"; eng = "Black"; break;
			case BLACK_AND_WHITE: ger = "Schwarzweiß"; eng = "Black and White"; break;
			case BROWN: ger = "Braun"; eng = "Brown"; break;
			case GOLD: ger = "Gold"; eng = "Gold"; break;
			case SALT_AND_PEPPER: ger = "Salz und Pfeffer"; eng = "Salt and Pepper"; break;
			case THE_KILLER_BUNNY: ger = "Killer Kaninchen"; eng = "The Killer Bunny"; break;
			case WHITE: ger = "Weiß"; eng = "White"; break;
			}
			rabbittypelanguageKeys.put(i.toString(), 
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger, eng}));
		}
	}
	
	@SuppressWarnings("deprecation")
	public void initVillagerType() //INFO:VillagerType
	{
		for(org.bukkit.entity.Villager.Type i : org.bukkit.entity.Villager.Type.values())
		{
			String eng = "";
			String ger = "";
			switch(i.getKey().getKey())
			{
			default:
			case "DESERT": ger = "Wüste"; eng = "Desert"; break;
			case "JUNGLE": ger = "Dschungel"; eng = "Jungle"; break;
			case "PLAINS": ger = "Ebenen"; eng = "Plains"; break;
			case "SAVANNA": ger = "Savanne"; eng = "Savanna"; break;
			case "SNOW": ger = "Schnee"; eng = "Snow"; break;
			case "SWAMP": ger = "Sumpf"; eng = "Swamp"; break;
			case "TAIGA": ger = "Taiga"; eng = "Taiga"; break;
			}
			villagertypelanguageKeys.put(i.toString(), 
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger, eng}));
		}
	}
	
	@SuppressWarnings("deprecation")
	public void initVillagerProfession() //INFO:VillagerProfession
	{
		for(org.bukkit.entity.Villager.Profession i : org.bukkit.entity.Villager.Profession.values())
		{
			String eng = "";
			String ger = "";
			switch(i.getKey().getKey())
			{
			case "ARMORER": ger = "Rüstungsschmied"; eng = "Armorer"; break;
			case "BUTCHER": ger = "Metzger"; eng = "Butcher"; break;
			case "CARTOGRAPHER": ger = "Kartenzeichner"; eng = "Cartographer"; break;
			case "CLERIC": ger = "Geistliche"; eng = "Cleric"; break;
			case "FARMER": ger = "Bauer"; eng = "Farmer"; break;
			case "FISHERMAN": ger = "Fischer"; eng = "Fisherman"; break;
			case "FLETCHER": ger = "Pfeilmacher"; eng = "Fletcher"; break;
			case "LEATHERWORKER": ger = "Gerber"; eng = "Leatherworker"; break;
			case "LIBRARIAN": ger = "Bibliothekar"; eng = "Librarian"; break;
			case "MASON": ger = "Steinmetz"; eng = "Mason"; break;
			case "NITWIT": ger = "Nichtnutz"; eng = "Niwit"; break;
			case "NONE": ger = "Arbeitsloser"; eng = "None"; break;
			case "SHEPHERD": ger = "Schäfer"; eng = "Shepherd"; break;
			case "TOOLSMITH": ger = "Werkzeugschmied"; eng = "Toolsmith"; break;
			case "WEAPONSMITH": ger = "Waffenschmied"; eng = "Weaponsmith"; break;
			}
			villagerprofessionlanguageKeys.put(i.toString(), 
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger, eng}));
		}
	}
	
	public void initTreeType() //INFO:TreeType
	{
		for(org.bukkit.TreeType i : org.bukkit.TreeType.values())
		{
			String eng = "";
			String ger = "";
			switch(i)
			{
			case TREE: ger = "Baum"; eng = "TREE"; break;
			case BIG_TREE: ger = "BIG_TREE"; eng = "BIG_TREE"; break;
			case REDWOOD: ger = "Küsten-Sequoie"; eng = "REDWOOD"; break;
			case TALL_REDWOOD: ger = "Große Küsten-Sequoie"; eng = "TALL_REDWOOD"; break;
			case BIRCH: ger = "Birke"; eng = "BIRCH"; break;
			case JUNGLE: ger = "Trope"; eng = "JUNGLE"; break;
			case SMALL_JUNGLE: ger = "Kleiner Dschungel"; eng = "SMALL_JUNGLE"; break;
			case COCOA_TREE: ger = "Kakaobaum"; eng = "COCOA_TREE"; break;
			case JUNGLE_BUSH: ger = "Dschungel Busch"; eng = "JUNGLE_BUSH"; break;
			case RED_MUSHROOM: ger = "Roter Pilz"; eng = "RED_MUSHROOM"; break;
			case BROWN_MUSHROOM: ger = "Brauner Pilz"; eng = "BROWN_MUSHROOM"; break;
			case SWAMP: ger = "Sumpf"; eng = "SWAMP"; break;
			case ACACIA: ger = "Akazie"; eng = "ACACIA"; break;
			case DARK_OAK: ger = "Schwarz Eiche"; eng = "DARK_OAK"; break;
			case MEGA_REDWOOD: ger = "Mega Küsten-Sequoie"; eng = "MEGA_REDWOOD"; break;
			case TALL_BIRCH: ger = "Große Birke"; eng = "TALL_BIRCH"; break;
			case CHORUS_PLANT: ger = "Chorus Pflanze "; eng = "CHORUS_PLANT"; break;
			case CRIMSON_FUNGUS: ger = "Karmesinpilz"; eng = "CRIMSON_FUNGUS"; break;
			case WARPED_FUNGUS: ger = "Wirrpilz"; eng = "WARPED_FUNGUS"; break;
			case AZALEA: ger = "Azalee"; eng = "AZALEA"; break;
			case MANGROVE: ger = "Mangrove"; eng = "MANGROVE"; break;
			case TALL_MANGROVE: ger = "Hohe Mangrove"; eng = "TALL_MANGROVE"; break;
			case CHERRY: ger = "Kirschbaum"; eng = "CHERRY"; break;
			case MEGA_PINE: ger = "Mega Kiefer"; eng = "Mega Pine"; break;
			}
			treetypelanguageKeys.put(i.toString(), 
					new Language(new ISO639_2B[] {ISO639_2B.GER, ISO639_2B.ENG}, new Object[] {
							ger, eng}));
		}
	}
}