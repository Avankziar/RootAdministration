package main.java.me.avankziar.roota.spigot.ifh;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
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

import main.java.me.avankziar.ifh.spigot.interfaces.EnumTranslation;
import main.java.me.avankziar.roota.spigot.RootA;

public class EnumTranslationProvider implements EnumTranslation
{
	private static HashMap<String, String> bannerPatternLocalization = new HashMap<>();//DyeColor_Patterntype, Localization
	private static HashMap<String, String> enchantmentLocalization = new HashMap<>();//Enchanment, Localization
	private static HashMap<Material, String> materialLocalization = new HashMap<>();//Material, Localization
	private static HashMap<ItemFlag, String> itemflagLocalization = new HashMap<>();//ItemFlag, Localization
	private static HashMap<PotionType, String> potiontypeLocalization = new HashMap<>();
	private static HashMap<String, String> potioneffecttypeLocalization = new HashMap<>();
	private static HashMap<EntityType, String> entitytypeLocalization = new HashMap<>();
	private static HashMap<Axolotl.Variant, String> axolotlvariantLocalization = new HashMap<>();
	private static HashMap<BookMeta.Generation, String> bookmetagenerationLocalization = new HashMap<>();
	private static HashMap<Color, String> colorLocalization = new HashMap<>();
	private static HashMap<DyeColor, String> dyecolorLocalization = new HashMap<>();
	private static HashMap<String, String> tropicalfishbucketLocalization = new HashMap<>();//DyeColor_TropicalFish.Pattern_DyeColor
	private static HashMap<Cat.Type, String> cattypeLocalization = new HashMap<>();
	private static HashMap<Fox.Type, String> foxtypeLocalization = new HashMap<>();
	private static HashMap<MapCursor.Type, String> mapcursortypeLocalization = new HashMap<>();
	private static HashMap<Rabbit.Type, String> rabbittypeLocalization = new HashMap<>();
	private static HashMap<Villager.Type, String> villagertypeLocalization = new HashMap<>();
	private static HashMap<Villager.Profession, String> villagerprofessionLocalization = new HashMap<>();
	private static HashMap<TreeType, String> treetypeLocalization = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	public static void init(RootA plugin)
	{
		for(DyeColor dc : DyeColor.values())
		{
			for(PatternType p : PatternType.values())
			{
				bannerPatternLocalization.put(dc.toString()+"_"+p.toString(),
						plugin.getYamlHandler().getBannerLang().getString(dc.toString()+"_"+p.toString(), dc.toString()+"_"+p.toString()));		
			}
		}
		for(Enchantment e : Enchantment.values())
		{
			enchantmentLocalization.put(e.getName(), plugin.getYamlHandler().getEnchLang().getString(e.getName(), e.getName()));		
		}
		for(Material m : Material.values())
		{
			materialLocalization.put(m, plugin.getYamlHandler().getMaterialLang().getString(m.toString(), m.toString()));
		}
		for(ItemFlag i : ItemFlag.values())
		{
			itemflagLocalization.put(i, plugin.getYamlHandler().getItemFlagLang().getString(i.toString(), i.toString()));
		}
		for(PotionType pt : PotionType.values())
		{
			potiontypeLocalization.put(pt,
					plugin.getYamlHandler().getPotionTypeLang().getString(pt.toString(), pt.toString()));
		}
		for(PotionEffectType pet : PotionEffectType.values())
		{
			potioneffecttypeLocalization.put(pet.getName(),
					plugin.getYamlHandler().getPotionEffectTypeLang().getString(pet.getName(), pet.getName()));
		}
		for(EntityType i : EntityType.values())
		{
			entitytypeLocalization.put(i, plugin.getYamlHandler().getEntityTypeLang().getString(i.toString(), i.toString()));
		}
		for(Axolotl.Variant i : Axolotl.Variant.values())
		{
			axolotlvariantLocalization.put(i, plugin.getYamlHandler().getAxolotlVariantLang().getString(i.toString(), i.toString()));
		}
		for(BookMeta.Generation i : BookMeta.Generation.values())
		{
			bookmetagenerationLocalization.put(i, plugin.getYamlHandler().getBookMetaGenerationLang().getString(i.toString(), i.toString()));
		}
		List<Color> colors = Arrays.asList(new Color[]
				{
						Color.AQUA,Color.BLACK,Color.BLUE,Color.FUCHSIA,Color.GRAY,Color.GREEN,Color.LIME,
						Color.MAROON,Color.NAVY,Color.OLIVE,Color.ORANGE,Color.PURPLE,Color.RED,Color.SILVER,
						Color.TEAL,Color.WHITE,Color.YELLOW
				});
		for(Color i : colors)
		{
			colorLocalization.put(i, plugin.getYamlHandler().getColorLang().getString(i.toString(), i.toString()));
		}
		for(DyeColor i : DyeColor.values())
		{
			dyecolorLocalization.put(i, plugin.getYamlHandler().getDyeColorLang().getString(i.toString(), i.toString()));
			for(TropicalFish.Pattern j : TropicalFish.Pattern.values())
			{
				for(DyeColor h : DyeColor.values())
				{
					tropicalfishbucketLocalization.put(i.toString()+"_"+j.toString()+"_"+h.toString(), 
							plugin.getYamlHandler().getTropicalFishBucketLang().getString(
									i.toString()+"_"+j.toString()+"_"+h.toString(), i.toString()+"_"+j.toString()+"_"+h.toString()));
				}
				
			}
		}
		for(Cat.Type i : Cat.Type.values())
		{
			cattypeLocalization.put(i, plugin.getYamlHandler().getCatTypeLang().getString(i.toString(), i.toString()));
		}
		for(Fox.Type i : Fox.Type.values())
		{
			foxtypeLocalization.put(i, plugin.getYamlHandler().getFoxTypeLang().getString(i.toString(), i.toString()));
		}
		for(MapCursor.Type i : MapCursor.Type.values())
		{
			mapcursortypeLocalization.put(i, plugin.getYamlHandler().getMapCursorTypeLang().getString(i.toString(), i.toString()));
		}
		for(Rabbit.Type i : Rabbit.Type.values())
		{
			rabbittypeLocalization.put(i, plugin.getYamlHandler().getRabbitTypeLang().getString(i.toString(), i.toString()));
		}
		for(Villager.Type i : Villager.Type.values())
		{
			villagertypeLocalization.put(i, plugin.getYamlHandler().getVillagerTypeLang().getString(i.toString(), i.toString()));
		}
		for(Villager.Profession i : Villager.Profession.values())
		{
			villagerprofessionLocalization.put(i, plugin.getYamlHandler().getVillagerProfessionLang().getString(i.toString(), i.toString()));
		}
		for(TreeType i : TreeType.values())
		{
			treetypeLocalization.put(i, plugin.getYamlHandler().getTreeTypeLang().getString(i.toString(), i.toString()));
		}
	}
	
	public String getLocalization(Material mat)
	{
		String s = materialLocalization.get(mat);
		return s != null ? s : mat.toString();
	}
	
	public String getLocalization(DyeColor dyeColor, PatternType patternType)
	{
		String s = bannerPatternLocalization.get(dyeColor.toString()+"_"+patternType.toString());
		return s != null ? s : dyeColor.toString()+"_"+patternType.toString();
	}
	
	@SuppressWarnings("deprecation")
	public String getLocalization(Enchantment ench)
	{
		String s = enchantmentLocalization.get(ench.getName());
		return s != null ? s : ench.getName();
	}
	
	public String getLocalization(ItemFlag i)
	{
		String s = itemflagLocalization.get(i);
		return s != null ? s : i.toString();
	}
	
	public String getLocalization(PotionType pt)
	{
		String s = potiontypeLocalization.get(pt);
		return s != null ? s : pt.toString();
	}
	
	public String getLocalization(PotionType pt, PotionMeta pm)
	{
		if(pm.hasCustomEffects())
		{
			String s = potiontypeLocalization.get(PotionType.UNCRAFTABLE);
			return s != null ? s : pt.toString();
		}
		String s = potiontypeLocalization.get(pt);
		return s != null ? s : pt.toString();
	}
	
	public String getLocalization(PotionEffectType pet)
	{
		String s = potioneffecttypeLocalization.get(pet.getName());
		return s != null ? s : pet.toString();
	}
	
	public String getLocalization(Axolotl.Variant axv)
	{
		String s = axolotlvariantLocalization.get(axv);
		return s != null ? s : axv.toString();
	}
	
	public String getLocalization(EntityType et)
	{
		String s = entitytypeLocalization.get(et);
		return s != null ? s : et.toString();
	}
	
	public String getLocalization(BookMeta.Generation bmg)
	{
		String s = bookmetagenerationLocalization.get(bmg);
		return s != null ? s : bmg.toString();
	}
	
	public String getLocalization(Color c)
	{
		String s = colorLocalization.get(c);
		return s != null ? s : c.toString();
	}
	
	public String getLocalization(DyeColor dc)
	{
		String s = dyecolorLocalization.get(dc);
		return s != null ? s : dc.toString();
	}
	
	public String getLocalization(DyeColor bodyColor, TropicalFish.Pattern pattern, DyeColor patternColor)
	{
		String s = tropicalfishbucketLocalization.get(bodyColor.toString()+"_"+pattern.toString()+"_"+patternColor.toString());
		return s != null ? s : bodyColor.toString()+"_"+pattern.toString()+"_"+patternColor.toString();
	}
	
	public String getLocalization(Cat.Type ct)
	{
		String s = cattypeLocalization.get(ct);
		return s != null ? s : ct.toString();
	}
	
	public String getLocalization(Fox.Type ft)
	{
		String s = foxtypeLocalization.get(ft);
		return s != null ? s : ft.toString();
	}
	
	public String getLocalization(MapCursor.Type mct)
	{
		String s = mapcursortypeLocalization.get(mct);
		return s != null ? s : mct.toString();
	}
	
	public String getLocalization(Rabbit.Type rt)
	{
		String s = rabbittypeLocalization.get(rt);
		return s != null ? s : rt.toString();
	}
	
	public String getLocalization(Villager.Type vt)
	{
		String s = villagertypeLocalization.get(vt);
		return s != null ? s : vt.toString();
	}
	
	public String getLocalization(Villager.Profession vp)
	{
		String s = villagerprofessionLocalization.get(vp);
		return s != null ? s : vp.toString();
	}
	
	public String getLocalization(TreeType tt)
	{
		String s = treetypeLocalization.get(tt);
		return s != null ? s : tt.toString();
	}
}