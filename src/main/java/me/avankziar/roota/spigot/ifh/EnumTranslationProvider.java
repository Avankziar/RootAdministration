package main.java.me.avankziar.roota.spigot.ifh;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.TreeType;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.map.MapCursor;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import main.java.me.avankziar.roota.spigot.RootA;
import me.avankziar.ifh.spigot.interfaces.EnumTranslation;

public class EnumTranslationProvider implements EnumTranslation
{
	private static HashMap<String, String> bannerPatternLocalization = new HashMap<>();//DyeColor_Patterntype, Localization
	private static HashMap<String, String> enchantmentLocalization = new HashMap<>();//Enchanment, Localization
	private static HashMap<String, String> materialLocalization = new HashMap<>();//Material, Localization
	private static HashMap<String, String> itemflagLocalization = new HashMap<>();//ItemFlag, Localization
	private static HashMap<String, String> potiontypeLocalization = new HashMap<>();
	private static HashMap<String, String> potioneffecttypeLocalization = new HashMap<>();
	private static HashMap<String, String> entitytypeLocalization = new HashMap<>();
	private static HashMap<String, String> axolotlvariantLocalization = new HashMap<>();
	private static HashMap<String, String> bookmetagenerationLocalization = new HashMap<>();
	private static HashMap<String, String> colorLocalization = new HashMap<>(); //Color as RGB as String
	private static HashMap<String, String> dyecolorLocalization = new HashMap<>();
	private static HashMap<String, String> tropicalfishbucketLocalization = new HashMap<>();//DyeColor_TropicalFish.Pattern_DyeColor
	private static HashMap<String, String> cattypeLocalization = new HashMap<>();
	private static HashMap<String, String> foxtypeLocalization = new HashMap<>();
	private static HashMap<String, String> mapcursortypeLocalization = new HashMap<>();
	private static HashMap<String, String> rabbittypeLocalization = new HashMap<>();
	private static HashMap<String, String> villagertypeLocalization = new HashMap<>();
	private static HashMap<String, String> villagerprofessionLocalization = new HashMap<>();
	private static HashMap<String, String> treetypeLocalization = new HashMap<>();
	
	public static void init(RootA plugin)
	{
		for(DyeColor dc : DyeColor.values())
		{
			for(Iterator<PatternType> iter = Registry.BANNER_PATTERN.iterator(); iter.hasNext();)
			{
				PatternType i = iter.next();
				String path = i.getKey().getKey().toUpperCase();
				bannerPatternLocalization.put(dc.toString().toUpperCase()+"_"+path,
						plugin.getYamlHandler().getBannerLang().getString(dc.toString()+"_"+path, dc.toString()+"_"+path));		
			}
		}
		for(Iterator<org.bukkit.enchantments.Enchantment> iter = Registry.ENCHANTMENT.iterator(); iter.hasNext();)
		{
			org.bukkit.enchantments.Enchantment e = iter.next();
			String path = e.getKey().getKey().toUpperCase();
			enchantmentLocalization.put(path, plugin.getYamlHandler().getEnchLang().getString(path, path));		
		}
		for(Material i : Material.values())
		{
			String path = i.toString().toUpperCase();
			materialLocalization.put(path, plugin.getYamlHandler().getMaterialLang().getString(path, path));
		}
		for(ItemFlag i : ItemFlag.values())
		{
			String path = i.toString().toUpperCase();
			itemflagLocalization.put(path, plugin.getYamlHandler().getItemFlagLang().getString(path, path));
		}
		for(PotionType i : PotionType.values())
		{
			String path = i.toString().toUpperCase();
			potiontypeLocalization.put(path, plugin.getYamlHandler().getPotionTypeLang().getString(path, path));
		}
		for(Iterator<org.bukkit.potion.PotionEffectType> iter = Registry.EFFECT.iterator(); iter.hasNext();)
		{
			org.bukkit.potion.PotionEffectType i = iter.next();
			String path = i.getKey().getKey().toUpperCase();
			potioneffecttypeLocalization.put(path, plugin.getYamlHandler().getPotionEffectTypeLang().getString(path, path));
		}
		for(EntityType i : EntityType.values())
		{
			String path = i.toString().toUpperCase();
			entitytypeLocalization.put(path, plugin.getYamlHandler().getEntityTypeLang().getString(path, path));
		}
		for(Axolotl.Variant i : Axolotl.Variant.values())
		{
			String path = i.toString().toUpperCase();
			axolotlvariantLocalization.put(path, plugin.getYamlHandler().getAxolotlVariantLang().getString(path, path));
		}
		for(BookMeta.Generation i : BookMeta.Generation.values())
		{
			String path = i.toString().toUpperCase();
			bookmetagenerationLocalization.put(path, plugin.getYamlHandler().getBookMetaGenerationLang().getString(path, path));
		}
		List<Color> colors = Arrays.asList(new Color[]
				{
						Color.AQUA,Color.BLACK,Color.BLUE,Color.FUCHSIA,Color.GRAY,Color.GREEN,Color.LIME,
						Color.MAROON,Color.NAVY,Color.OLIVE,Color.ORANGE,Color.PURPLE,Color.RED,Color.SILVER,
						Color.TEAL,Color.WHITE,Color.YELLOW
				});
		for(Color i : colors)
		{
			colorLocalization.put(String.valueOf(i.asRGB()), plugin.getYamlHandler().getColorLang().getString(String.valueOf(i.asRGB()), i.toString()));
		}
		for(DyeColor i : DyeColor.values())
		{
			String pi = i.toString().toUpperCase();
			dyecolorLocalization.put(pi, plugin.getYamlHandler().getDyeColorLang().getString(pi, pi));
			for(TropicalFish.Pattern j : TropicalFish.Pattern.values())
			{
				String pj = j.toString().toUpperCase();
				for(DyeColor h : DyeColor.values())
				{
					String ph = h.toString().toUpperCase();
					tropicalfishbucketLocalization.put(pi+"_"+pj+"_"+ph, 
							plugin.getYamlHandler().getTropicalFishBucketLang().getString(pi+"_"+pj+"_"+ph, pi+"_"+pj+"_"+ph));
				}
				
			}
		}
		for(Iterator<org.bukkit.entity.Cat.Type> iter = Registry.CAT_VARIANT.iterator(); iter.hasNext();)
		{
			org.bukkit.entity.Cat.Type i = iter.next();
			String path = i.getKey().getKey().toUpperCase();
			cattypeLocalization.put(path, plugin.getYamlHandler().getCatTypeLang().getString(path, path));
		}
		for(Fox.Type i : Fox.Type.values())
		{
			String path = i.toString().toUpperCase();
			foxtypeLocalization.put(path, plugin.getYamlHandler().getFoxTypeLang().getString(path, path));
		}
		for(Iterator<org.bukkit.map.MapCursor.Type> iter = Registry.MAP_DECORATION_TYPE.iterator(); iter.hasNext();)
		{
			org.bukkit.map.MapCursor.Type i = iter.next();
			String path = i.getKey().getKey().toUpperCase();
			mapcursortypeLocalization.put(path, plugin.getYamlHandler().getMapCursorTypeLang().getString(path, path));
		}
		for(Rabbit.Type i : Rabbit.Type.values())
		{
			String path = i.toString().toUpperCase();
			rabbittypeLocalization.put(path, plugin.getYamlHandler().getRabbitTypeLang().getString(path, path));
		}
		for(Iterator<org.bukkit.entity.Villager.Type> iter = Registry.VILLAGER_TYPE.iterator(); iter.hasNext();)
		{
			org.bukkit.entity.Villager.Type i = iter.next();
			String path = i.getKey().getKey().toUpperCase();
			villagertypeLocalization.put(path, plugin.getYamlHandler().getVillagerTypeLang().getString(path, path));
		}
		for(Iterator<org.bukkit.entity.Villager.Profession> iter = Registry.VILLAGER_PROFESSION.iterator(); iter.hasNext();)
		{
			org.bukkit.entity.Villager.Profession i = iter.next();
			String path = i.getKey().getKey().toUpperCase();
			villagerprofessionLocalization.put(path, plugin.getYamlHandler().getVillagerProfessionLang().getString(path, path));
		}
		for(TreeType i : TreeType.values())
		{
			String path = i.toString().toUpperCase();
			treetypeLocalization.put(path, plugin.getYamlHandler().getTreeTypeLang().getString(path, path));
		}
	}
	
	public String getLocalization(Material i)
	{
		String s = materialLocalization.get(i.toString().toUpperCase());
		return s != null ? s : i.toString().toUpperCase();
	}
	
	public String getLocalization(DyeColor dc, PatternType pt)
	{
		String s = bannerPatternLocalization.get(dc.toString().toUpperCase()+"_"+pt.getKey().getKey().toUpperCase());
		return s != null ? s : dc.toString().toUpperCase()+"_"+pt.getKey().getKey().toUpperCase();
	}
	
	public String getLocalization(Enchantment i)
	{
		String s = enchantmentLocalization.get(i.getKey().getKey().toUpperCase());
		return s != null ? s : i.getKey().getKey().toUpperCase();
	}
	
	public String getLocalization(ItemFlag i)
	{
		String s = itemflagLocalization.get(i.toString().toUpperCase());
		return s != null ? s : i.toString().toUpperCase();
	}
	
	public String getLocalization(PotionType i)
	{
		String s = potiontypeLocalization.get(i.toString().toUpperCase());
		return s != null ? s : i.toString().toUpperCase();
	}
	
	public String getLocalization(PotionType pt, PotionMeta pm)
	{
		if(pm.hasCustomEffects())
		{
			String s = potiontypeLocalization.get(PotionType.AWKWARD.toString().toUpperCase());
			return s != null ? s : pt.toString().toUpperCase();
		}
		String s = potiontypeLocalization.get(pt.toString().toUpperCase());
		return s != null ? s : pt.toString().toUpperCase();
	}
	
	public String getLocalization(PotionEffectType i)
	{
		String s = potioneffecttypeLocalization.get(i.getKey().getKey().toUpperCase());
		return s != null ? s : i.getKey().getKey().toUpperCase();
	}
	
	public String getLocalization(Axolotl.Variant i)
	{
		String s = axolotlvariantLocalization.get(i.toString().toUpperCase());
		return s != null ? s : i.toString().toUpperCase();
	}
	
	public String getLocalization(EntityType i)
	{
		String s = entitytypeLocalization.get(i.toString().toUpperCase());
		return s != null ? s : i.toString().toUpperCase();
	}
	
	public String getLocalization(BookMeta.Generation i)
	{
		String s = bookmetagenerationLocalization.get(i.toString().toUpperCase());
		return s != null ? s : i.toString().toUpperCase();
	}
	
	public String getLocalization(Color i)
	{
		String s = colorLocalization.get(String.valueOf(i.asRGB()));
		return s != null ? s : i.toString().toUpperCase();
	}
	
	public String getLocalization(DyeColor i)
	{
		String s = dyecolorLocalization.get(i.toString().toUpperCase());
		return s != null ? s : i.toString().toUpperCase();
	}
	
	public String getLocalization(DyeColor bc, TropicalFish.Pattern p, DyeColor pc)
	{
		String s = tropicalfishbucketLocalization.get(bc.toString().toUpperCase()+"_"+p.toString().toUpperCase()+"_"+pc.toString().toUpperCase());
		return s != null ? s : bc.toString().toUpperCase()+"_"+p.toString().toUpperCase()+"_"+pc.toString().toUpperCase();
	}
	
	public String getLocalization(Cat.Type i)
	{
		String s = cattypeLocalization.get(i.getKey().getKey().toUpperCase());
		return s != null ? s : i.getKey().getKey().toUpperCase();
	}
	
	public String getLocalization(Fox.Type i)
	{
		String s = foxtypeLocalization.get(i.toString().toUpperCase());
		return s != null ? s : i.toString().toUpperCase();
	}
	
	public String getLocalization(MapCursor.Type i)
	{
		String s = mapcursortypeLocalization.get(i.getKey().getKey().toUpperCase());
		return s != null ? s : i.getKey().getKey().toUpperCase();
	}
	
	public String getLocalization(Rabbit.Type i)
	{
		String s = rabbittypeLocalization.get(i.toString().toUpperCase());
		return s != null ? s : i.toString().toUpperCase();
	}
	
	public String getLocalization(Villager.Type i)
	{
		String s = villagertypeLocalization.get(i.getKey().getKey().toUpperCase());
		return s != null ? s : i.getKey().getKey().toUpperCase();
	}
	
	public String getLocalization(Villager.Profession i)
	{
		String s = villagerprofessionLocalization.get(i.getKey().getKey().toUpperCase());
		return s != null ? s : i.getKey().getKey().toUpperCase();
	}
	
	public String getLocalization(TreeType i)
	{
		String s = treetypeLocalization.get(i.toString().toUpperCase());
		return s != null ? s : i.toString().toUpperCase();
	}
}