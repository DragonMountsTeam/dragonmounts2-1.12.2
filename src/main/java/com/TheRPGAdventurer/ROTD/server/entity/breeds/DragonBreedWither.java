package com.TheRPGAdventurer.ROTD.server.entity.breeds;

import com.TheRPGAdventurer.ROTD.DragonMountsLootTables;
import com.TheRPGAdventurer.ROTD.client.initialization.ModItems;
import com.TheRPGAdventurer.ROTD.client.items.ItemDragonEssence;
import com.TheRPGAdventurer.ROTD.client.sound.ModSounds;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathAffectedArea;
import com.TheRPGAdventurer.ROTD.server.entity.helper.breath.BreathNode;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;


public class DragonBreedWither extends DragonBreed {
	
	public BreathAffectedArea breathAffectedAreaWither;

    DragonBreedWither() {
        super("wither", 0x50260a);
        
        setImmunity(DamageSource.MAGIC);
        setImmunity(DamageSource.HOT_FLOOR);
        setImmunity(DamageSource.LIGHTNING_BOLT);
        setImmunity(DamageSource.WITHER);
        
    }

    @Override
    public void onEnable(EntityTameableDragon dragon) {}

    @Override
    public void onDisable(EntityTameableDragon dragon) {}

    @Override
    public void onDeath(EntityTameableDragon dragon) {}
    
    @Override
    public SoundEvent getLivingSound() {
        if (rand.nextInt(3) == 0) {
            return SoundEvents.ENTITY_SKELETON_AMBIENT;
        } else {
        	return ModSounds.ENTITY_SKELETON_DRAGON_GROWL;
        }
    }
    
	@Override
	public boolean canChangeBreed() {
		return false;
	}
	
	@Override
    public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {     
        dragon.getBreathHelper().getbreathAffectedAreaWither().continueBreathing(world, origin, endOfLook, power);
        dragon.getBreathHelper().getbreathAffectedAreaWither().updateTick(world);
    }
    
	@Override
    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
        dragon.getBreathHelper().getEmitter().spawnBreathParticlesforWitherDragon(world, power, tickCounter);
    }
	
	@Override
	public ResourceLocation getLootTable(EntityTameableDragon dragon) {
		return DragonMountsLootTables.ENTITIES_DRAGON_SKELETON;
	}
	
	@Override
	public void onLivingUpdate(EntityTameableDragon dragon) {
		World world = dragon.world;
		if(dragon.isUsingBreathWeapon()) {
			if (world instanceof WorldServer && !dragon.isDead && !dragon.isEgg()) {
				((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double) dragon.posX,
						(double) dragon.posY + dragon.getEyeHeight(), (double) dragon.posZ, 1, 0.5D, 0.25D, 0.5D, 0.0D);
			}
		}
	}
	
	@Override
	public boolean isInfertile() {
		return true;
	}
	
	@Override
	public EnumParticleTypes getSneezeParticle() {
		return null;
	}
	
	@Override
	public ItemDragonEssence dragonEssence() {
    return ModItems.EssenceWither;
 }
	
	@Override
	public double getHealth() {
		return 150;
	}

    
}
	
