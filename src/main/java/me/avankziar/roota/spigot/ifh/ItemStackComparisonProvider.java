package main.java.me.avankziar.roota.spigot.ifh;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.AxolotlBucketMeta;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.bukkit.potion.PotionEffect;

import main.java.me.avankziar.ifh.spigot.comparison.ItemStackComparison;

public class ItemStackComparisonProvider implements ItemStackComparison
{
	public boolean isSimilar(ItemStack item, ItemStack filter)
	{
		return isSimilar(item, filter, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
				true, true, true, true, true, true);
	}
	
	public boolean isSimilar(ItemStack item, ItemStack filter,
			boolean checkAmount, boolean checkCustomModelData, boolean checkDisplayname, boolean checkItemFlags, boolean checkLore,
			boolean checkEnchantments, boolean checkBookEnchantments, boolean checkChulkerContents, boolean checkArmorMeta,
			boolean checkAxolotlBucketMeta, boolean checkBannerMeta, boolean checkBookMeta, boolean checkBundleMeta,
			boolean checkDamageable, boolean checkFireworkEffectMeta, boolean checkLeatherArmorMeta, boolean checkMapMeta,
			boolean checkPotionMeta, boolean checkRepairable, boolean checkSkullMeta, boolean checkSpawnEggMeta, boolean checkTropicalFishBucketMeta)
	{
		if (item == null || filter == null) 
        {
            return true;
        }
        final ItemStack i = item.clone();
        final ItemStack f = filter.clone();
        if(!checkAmount)
        {
        	i.setAmount(1);
       	 	f.setAmount(1);
        }
        if(i.getType() != f.getType())
        {
        	return false;
        }
        if(i.hasItemMeta() == true && f.hasItemMeta() == true)
        {
        	ItemMeta im = i.getItemMeta();
        	ItemMeta fm = f.getItemMeta();
        	if(checkCustomModelData)
        	{
        		if(!matchCustomModelData(i, f))
        		{
        			return false;
        		}
        	}
        	if(checkDisplayname)
        	{
        		if(!matchDisplayname(i, f))
        		{
        			return false;
        		}
        	}
        	if(checkItemFlags)
        	{
        		if(!matchItemFlags(i, f))
            	{
        			return false;
            	}
        	}
        	if(checkLore)
        	{
        		if(!matchLore(i, f))
            	{
            		return false;
            	}
        	}
        	if(checkEnchantments)
        	{
        		if(im.hasEnchants() && i.getType() != Material.ENCHANTED_BOOK)
            	{
        			if(!fm.hasEnchants() || f.getType() == Material.ENCHANTED_BOOK)
            		{
            			return false;
            		}
            		if(!compareEnchantments(im, fm))
            		{
            			return false;
            		}
            	}
        	}
        	if(im instanceof BlockStateMeta && checkChulkerContents)
			{
        		if(!(fm instanceof BlockStateMeta))
        		{
        			return false;
        		}
				BlockStateMeta ibsm = (BlockStateMeta) im;
				BlockStateMeta fbsm = (BlockStateMeta) fm;
				if(ibsm.getBlockState() instanceof ShulkerBox)
				{
					if(!(fbsm.getBlockState() instanceof ShulkerBox))
					{
						return false;
					}
					ShulkerBox ish = (ShulkerBox) ibsm.getBlockState();
					ShulkerBox fsh = (ShulkerBox) fbsm.getBlockState();
					if(ish.getColor() != fsh.getColor())
					{
						return false;
					}
					for(int j = 0; j < ish.getSnapshotInventory().getStorageContents().length; j++)
					{
						ItemStack its = ish.getSnapshotInventory().getStorageContents()[j];
						ItemStack fts = fsh.getSnapshotInventory().getStorageContents()[j];
						if(!isSimilar(its, fts))
						{
							return false;
						}
					}
				}
				return true; //Short exist only shulker
			}
        	if(checkArmorMeta)
        	{
        		if(!matchArmorMeta(i, f))
        		{
        			return false;
        		}
        	}
        	if(checkAxolotlBucketMeta)
        	{
        		if(!matchAxolotlBucketMeta(i, f))
        		{
        			return false;
        		}
        	}
        	if(checkBannerMeta)
			{
        		if(!matchBannerMeta(i, f))
        		{
        			return false;
        		}
			}
        	if(checkBookMeta)
        	{
        		if(!matchBookMeta(i, f))
        		{
        			return false;
        		}
        	}
        	if(checkBundleMeta)
        	{
        		if(!matchBundleMeta(i, f))
        		{
        			return false;
        		}
        	}
        	if(checkDamageable)
        	{
        		if(!matchDamageable(i, f))
        		{
        			return false;
        		}
        	}
        	if(im instanceof EnchantmentStorageMeta && checkBookEnchantments)
        	{
        		if(!(fm instanceof EnchantmentStorageMeta))
        		{
        			return false;
        		}
        		EnchantmentStorageMeta iesm = (EnchantmentStorageMeta) im;
        		EnchantmentStorageMeta fesm = (EnchantmentStorageMeta) fm;
            	if(!compareStoredEnchantments(iesm, fesm))
            	{
            		return false;
            	}
        	}
        	if(im instanceof FireworkEffectMeta && checkFireworkEffectMeta)
        	{
        		if(!(fm instanceof FireworkEffectMeta))
        		{
        			return false;
        		}
        		FireworkEffectMeta aim = (FireworkEffectMeta) im;
        		FireworkEffectMeta afm = (FireworkEffectMeta) fm;
        		if(aim.hasEffect() && afm.hasEffect())
        		{
        			if(!aim.getEffect().serialize().equals(afm.getEffect().serialize()))
        			{
        				return false;
        			}
        		}
        	}
        	if(checkFireworkEffectMeta)
        	{
        		if(!matchFireworkMeta(i, f))
        		{
        			return false;
        		}
        	}
        	if(checkLeatherArmorMeta)
        	{
        		if(!matchLeatherArmorMeta(i, f))
        		{
        			return false;
        		}
        	}
        	if(im instanceof MapMeta && checkMapMeta)
        	{
        		if(!(fm instanceof MapMeta))
        		{
        			return false;
        		}
        		MapMeta aim = (MapMeta) im;
        		MapMeta afm = (MapMeta) fm;
        		if(aim.getColor().asRGB() != afm.getColor().asRGB())
        		{
        			return false;
        		}
        		//API is extremly poorly
        	}
        	if(checkPotionMeta)
			{
        		if(!matchPotionMeta(i, f))
        		{
        			return false;
        		}
			}
        	if(checkRepairable)
        	{
        		if(!matchRepairable(i, f))
        		{
        			return false;
        		}
        	}
        	if(checkSkullMeta)
        	{
        		if(!matchSkullMeta(i, f))
        		{
        			return false;
        		}
        	}
        	if(checkSpawnEggMeta)
        	{
        		if(!matchSpawnEggMeta(i, f))
        		{
        			return false;
        		}
        	}
        	if(checkTropicalFishBucketMeta)
        	{
        		if(!matchTropicalFishBucketMeta(i, f))
        		{
        			return false;
        		}
        	}
        }
        return true;
	}
	
	private boolean isNull(ItemStack i, ItemStack f)
	{
		if(i == null || f == null)
		{
			return true;
		}
		return false;
	}
	
	public boolean matchType(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		return i.getType() == f.getType();
	}
	
	public boolean matchAmount(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		return i.getAmount() == f.getAmount();
	}
	
	public boolean matchCustomModelData(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im.hasCustomModelData())
    	{
    		if(!fm.hasCustomModelData())
    		{
    			return false;
    		}
    		if(im.getCustomModelData() != fm.getCustomModelData())
    		{
    			return false;
    		}
    	}
		return true;
	}
	
	public boolean matchDisplayname(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im.hasDisplayName())
    	{
    		if(!fm.hasDisplayName())
    		{
    			return false;
    		}
    		if(!im.getDisplayName().equals(fm.getDisplayName()))
    		{
    			return false;
    		}
    	}
		return true;
	}
	
	public boolean matchItemFlags(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(!im.getItemFlags().isEmpty() && !fm.getItemFlags().isEmpty())
    	{
    		if(fm.getItemFlags().isEmpty()
    				|| im.getItemFlags().size() != fm.getItemFlags().size())
    		{
    			return false;
    		}
    		for(ItemFlag iifs : im.getItemFlags())
    		{
    			if(!fm.hasItemFlag(iifs))
    			{
    				return false;
    			}
    		}
    	}
		return true;
	}
	
	public boolean matchLore(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im.hasLore() && fm.hasLore())
    	{
    		for(int j = 0; j < im.getLore().size(); j++)
    		{
    			if(j >= fm.getLore().size())
    			{
    				return false;
    			}
    			if(!im.getLore().get(j).equals(fm.getLore().get(j)))
    			{
    				return false;
    			}
    		}
    	}
		return true;
	}
	
	public boolean matchEnchantments(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im.hasEnchants() && i.getType() != Material.ENCHANTED_BOOK)
    	{
			if(!fm.hasEnchants() || f.getType() == Material.ENCHANTED_BOOK)
			{
				return false;
			}
    		if(!compareEnchantments(im, fm))
    		{
    			return false;
    		}
    	} else if(i.getType() == Material.ENCHANTED_BOOK && f.getType() == Material.ENCHANTED_BOOK)
    	{
    		if(im instanceof EnchantmentStorageMeta)
        	{
        		if(!(fm instanceof EnchantmentStorageMeta))
        		{
        			return false;
        		}
        		EnchantmentStorageMeta iesm = (EnchantmentStorageMeta) im;
        		EnchantmentStorageMeta fesm = (EnchantmentStorageMeta) fm;
            	if(!compareStoredEnchantments(iesm, fesm))
            	{
            		return false;
            	}
        	}
    	} else
    	{
    		return false;
    	}
		return true;
	}
	
	public boolean matchArmorMeta(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im instanceof ArmorMeta)
    	{
    		if(!(fm instanceof ArmorMeta))
    		{
    			return false;
    		}
    		ArmorMeta aim = (ArmorMeta) im;
    		ArmorMeta afm = (ArmorMeta) fm;
    		if(!compareArmorMeta(aim, afm))
    		{
    			return false;
    		}
    	} else if(fm instanceof ArmorMeta)
    	{
    		return false;
    	}
		return true;
	}
	
	public boolean matchAxolotlBucketMeta(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im instanceof AxolotlBucketMeta)
    	{
    		if(!(fm instanceof AxolotlBucketMeta))
    		{
    			return false;
    		}
    		AxolotlBucketMeta aim = (AxolotlBucketMeta) im;
    		AxolotlBucketMeta afm = (AxolotlBucketMeta) fm;
    		if(!compareAxolotlBucketMeta(aim, afm))
    		{
    			return false;
    		}
    	} else if(fm instanceof AxolotlBucketMeta)
    	{
    		return false;
    	}
		return true;
	}
	
	public boolean matchBannerMeta(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im instanceof BannerMeta)
		{
    		if(!(fm instanceof BannerMeta))
    		{
    			return false;
    		}
			BannerMeta bim = (BannerMeta) im;
			BannerMeta bfm = (BannerMeta) fm;
			if(!compareBannerMeta(bim.getPatterns(), bfm.getPatterns()))
			{
				return false;
			}
		} else if(fm instanceof BannerMeta)
		{
			return false;
		}
		return true;
	}
	
	public boolean matchBookMeta(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im instanceof BookMeta)
    	{
    		if(!(fm instanceof BookMeta))
    		{
    			return false;
    		}
    		BookMeta aim = (BookMeta) im;
    		BookMeta afm = (BookMeta) fm;
    		if(!compareBookMeta(aim, afm))
    		{
    			return false;
    		}
    	} else if(fm instanceof BookMeta)
    	{
    		return false;
    	}
		return true;
	}
	
	public boolean matchBundleMeta(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im instanceof BundleMeta)
    	{
    		if(!(fm instanceof BundleMeta))
    		{
    			return false;
    		}
    		BundleMeta aim = (BundleMeta) im;
    		BundleMeta afm = (BundleMeta) fm;
    		if(!compareBundle(aim, afm))
    		{
    			return false;
    		}
    	} else if(fm instanceof BundleMeta)
    	{
    		return false;
    	}
		return true;
	}
	
	public boolean matchDamageable(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im instanceof Damageable)
    	{
    		if(!(fm instanceof Damageable))
    		{
    			return false;
    		}
    		Damageable aim = (Damageable) im;
    		Damageable afm = (Damageable) fm;
    		if(aim.hasDamage() && afm.hasDamage())
    		{
    			if(aim.getDamage() != afm.getDamage())
    			{
    				return false;
    			}
    		}
    	} else if(fm instanceof Damageable)
    	{
    		return false;
    	}
		return true;
	}
	
	public boolean matchFireworkMeta(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im instanceof FireworkMeta)
    	{
    		if(!(fm instanceof FireworkMeta))
    		{
    			return false;
    		}
    		FireworkMeta aim = (FireworkMeta) im;
    		FireworkMeta afm = (FireworkMeta) fm;
    		if(aim.hasEffects() && afm.hasEffects())
    		{
    			if(aim.getEffects().size() != afm.getEffects().size())
    			{
    				return false;
    			}
    			for(FireworkEffect e : aim.getEffects())
    			{
    				boolean boo = false;
    				for(FireworkEffect ee : afm.getEffects())
    				{
    					if(e.serialize().equals(ee.serialize()))
    					{
    						boo = true;
    						break;
    					}
    				}
    				if(!boo)
    				{
    					return false;
    				}
    			}
    		} else if(fm instanceof FireworkMeta)
    		{
    			return false;
    		}
    	}
		return true;
	}
	
	public boolean matchLeatherArmorMeta(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im instanceof LeatherArmorMeta)
    	{
    		if(!(fm instanceof LeatherArmorMeta))
    		{
    			return false;
    		}
    		LeatherArmorMeta aim = (LeatherArmorMeta) im;
    		LeatherArmorMeta afm = (LeatherArmorMeta) fm;
    		if(aim.getColor().asRGB() != afm.getColor().asRGB())
    		{
    			return false;
    		}
    	} else if(fm instanceof LeatherArmorMeta)
    	{
    		return false;
    	}
		return true;
	}
	
	public boolean matchPotionMeta(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im instanceof PotionMeta)
		{
    		if(!(fm instanceof PotionMeta))
    		{
    			return false;
    		}
			PotionMeta pim = (PotionMeta) im;
			PotionMeta pfm = (PotionMeta) fm;
			if(pim.hasCustomEffects() && pfm.hasCustomEffects())
			{
				if(!comparePotionEffects(pim.getCustomEffects(), pfm.getCustomEffects()))
				{
					return false;
				}
			} else
			{
				if(!comparePotionEffects(pim.getBasePotionType().getPotionEffects(), pfm.getBasePotionType().getPotionEffects()))
				{
					return false;
				}
			}
		} else if(fm instanceof PotionMeta)
		{
			return false;
		}
		return true;
	}
	
	public boolean matchRepairable(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im instanceof Repairable)
    	{
    		if(!(fm instanceof Repairable))
    		{
    			return false;
    		}
    		Repairable aim = (Repairable) im;
    		Repairable afm = (Repairable) fm;
    		if(aim.hasRepairCost() == afm.hasRepairCost())
    		{
    			if(aim.hasRepairCost() && afm.hasRepairCost())
        		{
        			if(aim.getRepairCost() != afm.getRepairCost())
        			{
        				return false;
        			}
        		}
    		}
    	} else if(fm instanceof Repairable)
    	{
    		return false;
    	}
		return true;
	}
	
	public boolean matchSkullMeta(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im instanceof SkullMeta)
    	{
    		if(!(fm instanceof SkullMeta))
    		{
    			return false;
    		}
    		SkullMeta aim = (SkullMeta) im;
    		SkullMeta afm = (SkullMeta) fm;
    		if(aim.hasOwner() && afm.hasOwner())
    		{
    			if(!aim.getOwningPlayer().getName().equals(afm.getOwningPlayer().getName()))
        		{
        			return false;
        		}
    		}
    	} else if(fm instanceof SkullMeta)
    	{
    		return false;
    	}
		return true;
	}
	
	public boolean matchSpawnEggMeta(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im instanceof SpawnEggMeta)
    	{
			if(!(fm instanceof SpawnEggMeta))
			{
				return false;
			}
    		SpawnEggMeta aim = (SpawnEggMeta) im;
    		SpawnEggMeta afm = (SpawnEggMeta) fm;
    		if(aim.getSpawnedEntity().getEntityType() != afm.getSpawnedEntity().getEntityType())
    		{
    			return false;
    		}
    	} else if(fm instanceof SpawnEggMeta)
    	{
    		return false;
    	}
		return true;
	}
	
	public boolean matchTropicalFishBucketMeta(ItemStack i, ItemStack f)
	{
		if(isNull(i, f)) {return false;}
		ItemMeta im = i.getItemMeta();
		ItemMeta fm = f.getItemMeta();
		if(im instanceof TropicalFishBucketMeta)
    	{
			if(!(fm instanceof TropicalFishBucketMeta))
			{
				return false;
			}
    		TropicalFishBucketMeta aim = (TropicalFishBucketMeta) im;
    		TropicalFishBucketMeta afm = (TropicalFishBucketMeta) fm;
    		if(aim.getBodyColor() != afm.getBodyColor())
    		{
    			return false;
    		}
    		if(aim.getPattern() != afm.getPattern())
    		{
    			return false;
    		}
    		if(aim.getPatternColor() != afm.getPatternColor())
    		{
    			return false;
    		}
    	} else if(fm instanceof TropicalFishBucketMeta)
    	{
    		return false;
    	}
		return true;
	}
	
	private boolean compareArmorMeta(ArmorMeta i, ArmorMeta f)
	{
		if(i.hasTrim() && f.hasTrim())
		{
			if(i.getTrim().getMaterial().getKey().getKey().equals(f.getTrim().getMaterial().getKey().getKey())
					&& i.getTrim().getPattern().getKey().getKey().equals(f.getTrim().getPattern().getKey().getKey()))
			{
				return true;
			}
			return false;
		}
		return true;
	}
	
	private boolean compareAxolotlBucketMeta(AxolotlBucketMeta i, AxolotlBucketMeta f)
	{
		if(i.hasVariant() && f.hasVariant())
		{
			if(i.getVariant() != f.getVariant())
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean compareBannerMeta(List<org.bukkit.block.banner.Pattern> i, List<org.bukkit.block.banner.Pattern> f)
	{
		for(org.bukkit.block.banner.Pattern p : i)
		{
			if(!containsBannerMeta(f, p))
			{
				return false;
			}
		}		
		for(org.bukkit.block.banner.Pattern p : f)
		{
			if(!containsBannerMeta(i, p))
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean containsBannerMeta(List<org.bukkit.block.banner.Pattern> i, org.bukkit.block.banner.Pattern f)
	{
		return i.stream()
				.filter(o -> o.getColor() == f.getColor())
				.filter(o -> o.getPattern() == f.getPattern())
				.findFirst().isPresent();
	}
	
	private boolean compareBookMeta(BookMeta i, BookMeta f)
	{
		if(!i.getAuthor().equals(f.getAuthor()))
		{
			return false;
		}
		if(i.getPageCount() != f.getPageCount())
		{
			return false;
		}
		if(i.getGeneration() != f.getGeneration())
		{
			return false;
		}
		if(!i.getTitle().equals(f.getTitle()))
		{
			return false;
		}
		for(String p : i.getPages())
		{
			if(!containsBook(f.getPages(), p))
			{
				return false;
			}
		}
		for(String p : f.getPages())
		{
			if(!containsBook(i.getPages(), p))
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean containsBook(List<String> l, String s)
	{
		return l.stream()
				.filter(o -> o.equals(s))
				.findFirst().isPresent();
	}
	
	private boolean compareBundle(BundleMeta i, BundleMeta f)
	{
		for(ItemStack is : i.getItems())
		{
			boolean b = false;
			for(ItemStack fs : f.getItems())
			{
				if(isSimilar(is, fs))
				{
					b = true;
					break;
				}
			}
			if(!b)
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean compareStoredEnchantments(EnchantmentStorageMeta iesm, EnchantmentStorageMeta fesm)
	{
    	for(Entry<Enchantment, Integer> e : iesm.getStoredEnchants().entrySet())
		{
			if(!containsEnchantment(fesm.getStoredEnchants(), e.getKey(), e.getValue()))
			{
				return false;
			}
		}
    	for(Entry<Enchantment, Integer> e : fesm.getStoredEnchants().entrySet())
		{
			if(!containsEnchantment(iesm.getStoredEnchants(), e.getKey(), e.getValue()))
			{
				return false;
			}
		}
    	return true;
	}
	
	private boolean compareEnchantments(ItemMeta i, ItemMeta f)
	{
    	for(Entry<Enchantment, Integer> e : i.getEnchants().entrySet())
		{
			if(!containsEnchantment(f.getEnchants(), e.getKey(), e.getValue()))
			{
				return false;
			}
		}
    	for(Entry<Enchantment, Integer> e : f.getEnchants().entrySet())
		{
			if(!containsEnchantment(i.getEnchants(), e.getKey(), e.getValue()))
			{
				return false;
			}
		}
    	return true;
	}
	
	private boolean containsEnchantment(Map<Enchantment, Integer> m, Enchantment v, Integer i)
	{
		return m.entrySet().stream()
				.filter(o -> o.getKey().getKey().getKey().equals(v.getKey().getKey()))
				.filter(o -> o.getValue() == i)
				.findFirst().isPresent();
	}
	
	private boolean comparePotionEffects(List<PotionEffect> a, List<PotionEffect> b)
	{
		for(PotionEffect p : a)
		{
			if(!containsPotionEffect(b, p))
			{
				return false;
			}
		}
		for(PotionEffect p : b)
		{
			if(!containsPotionEffect(a, p))
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean containsPotionEffect(List<PotionEffect> l, PotionEffect p)
	{
		return l.stream()
				.filter(o -> o.getType() == p.getType())
				.filter(o -> o.getAmplifier() == p.getAmplifier())
				.filter(o -> o.getDuration() == p.getDuration())
				.findFirst().isPresent();
	}
}