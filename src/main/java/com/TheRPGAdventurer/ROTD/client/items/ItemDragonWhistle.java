package com.TheRPGAdventurer.ROTD.client.items;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.util.DMUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.reflect.internal.Trees.Modifiers;

public class ItemDragonWhistle extends Item {

	// private final MessageDragonWhistle dcw = new MessageDragonWhistle();
	// private List<EntityTameableDragon> dragons = new ArrayList();
	// EntityTameableDragon dragon;
	private byte oldIndex;

	public ItemDragonWhistle() {
		this.setUnlocalizedName("dragon_whistle");
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, "dragon_whistle"));
		this.setMaxStackSize(1);
		this.setCreativeTab(DragonMounts.TAB);
	}

	@Override 
	@SideOnly(Side.CLIENT) 
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//		tooltip.add(StatCollector.translateToLocal(dragon.toString()));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
        
      //  if(worldIn instanceof WorldServer) {
      //     WorldServer worldServer = (WorldServer) worldIn;
		//   EntityTameableDragon dragon = (EntityTameableDragon) worldServer.getEntityFromUuid(stack.getTagCompound().getUniqueId("dragons"));		   
		//   if (player.isSneaking() && stack.hasTagCompound()) {
		//	   NBTTagCompound nbt = stack.getTagCompound();
		//	   NBTTagList nbtList = nbt !=null ? nbt.getTagList("dragon", nbt.getByte("byte")) : null; 
		//	   nbtList = nbt.getTagList("dragon", Constants.NBT.TAG_BYTE);
		//	   byte oldIndex = nbt.getByte("byte");
		//	   byte newIndex = (byte) ((oldIndex + 1) % Math.min(3, stack.getTagCompound().getSize())); 
		//	   nbt.getTagList("byte", Constants.NBT.TAG_BYTE).get(newIndex);
			   
			   
	//		   if(dragon.isDead && dragon == null && !player.isSneaking()) {
			  //   nbtList.removeTag(dragon.getUniqueID());
		//	   }
			   
		//	   DMUtils.getLogger().info("Dragon is " + dragon.getDisplayName().toString());
		//	   player.sendStatusMessage(new TextComponentString("whistle.controlling:" + dragon.getDisplayName().toString() + "" +
		//			   dragon.getUniqueID().toString()), true); 
		//	}
     //   }
		
          if (!player.isSneaking() && stack.hasTagCompound() && stack.getTagCompound().hasKey("dragon")) {
			  if (worldIn.isRemote) {
				  DragonMounts.proxy.openDragonWhistleGui(stack.getTagCompound().getUniqueId("dragon"),
						new ItemStack(this), worldIn);
				  return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
			  }
		  } else if(!stack.hasTagCompound() && !stack.hasTagCompound() && !stack.getTagCompound().hasKey("dragon")) {
			    player.sendStatusMessage(new TextComponentString("whistle.empty"), true);
		        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		  //}
       }
        
	   return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);		
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity target) {
		if (target instanceof EntityTameableDragon) {
			EntityTameableDragon dragon = (EntityTameableDragon) target;
			if (dragon.isTamedFor(player)) {
				NBTTagCompound nbt = new NBTTagCompound();

				if (stack.hasTagCompound()) {
					nbt = stack.getTagCompound();
			//		nbtList = nbt.getTagList("dragon", Constants.NBT.TAG_BYTE);
				} else {
					stack.setTagCompound(nbt);
				}
				
				if (!nbt.hasKey("dragon")) { 
					  UUID uuid = dragon.getUniqueID();
					  nbt.setTag("dragon", NBTUtil.createUUIDTag(uuid)); 
				   } else {
					  player.sendStatusMessage(new TextComponentString("whistle.limit"), true);
				}

				if(nbt.hasKey("dragon")) {
					player.sendStatusMessage(new TextComponentString("whistle.hasDragon" + dragon.getBreed().getSkin()), true);
				} else {
					player.sendStatusMessage(new TextComponentString("whistle.noNBT"), true);
				}
			} else {
                player.sendStatusMessage(new TextComponentString("whistle.notOwned"), true);
			}
			return true;
		} else {
			return false;
		}
	}
}
