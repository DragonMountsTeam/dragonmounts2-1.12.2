package com.TheRPGAdventurer.ROTD.entity.breath.sound;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

/**
 * User: The Grey Ghost
 * Date: 17/04/2014
 * Contains (some of the) sound effect names used for the dragon
 */
public enum SoundEffectNames {


  SILENCE("mob.enderdragon.silence", 0),

  ADULT_BREATHE_FIRE_START("mob.dragon.breathweapon.fire.adultbreathefirestart", 2.0),
  ADULT_BREATHE_FIRE_LOOP("mob.dragon.breathweapon.fire.adultbreathefireloop", 5.336),
  ADULT_BREATHE_FIRE_STOP("mob.dragon.breathweapon.fire.adultbreathefirestop", 1.0),
  JUVENILE_BREATHE_FIRE_START("mob.dragon.breathweapon.fire.juvenilebreathefirestart", 2.0),
  JUVENILE_BREATHE_FIRE_LOOP("mob.dragon.breathweapon.fire.juvenilebreathefireloop", 5.336),
  JUVENILE_BREATHE_FIRE_STOP("mob.dragon.breathweapon.fire.juvenilebreathefirestop", 1.0),
  HATCHLING_BREATHE_FIRE_START("mob.dragon.breathweapon.fire.hatchlingbreathefirestart", 2.0),
  HATCHLING_BREATHE_FIRE_LOOP("mob.dragon.breathweapon.fire.hatchlingbreathefireloop", 5.336),
  HATCHLING_BREATHE_FIRE_STOP("mob.dragon.breathweapon.fire.hatchlingbreathefirestop", 1.0),

  BREATHE_ICE_START("mob.dragon.breathweapon.ice.adultbreatheicestart", 1.7),
  BREATHE_ICE_LOOP("mob.dragon.breathweapon.ice.adultbreatheiceloop", 7.663),
  BREATHE_ICE_STOP("mob.dragon.breathweapon.ice.adultsbreatheicestop", 1.2),

  BREATHE_FOREST_START("mob.dragon.breathweapon.forest.breatheforeststart", 0.929),
  BREATHE_FOREST_LOOP("mob.dragon.breathweapon.forest.breatheforestloop", 1.936),
  BREATHE_FOREST_STOP("mob.dragon.breathweapon.forest.breatheforeststop", 0.708),

  BREATHE_AIR_START("mob.dragon.breathweapon.air.breatheairstart", 1.5),
  BREATHE_AIR_LOOP("mob.dragon.breathweapon.air.breatheairloop", 7.407),
  BREATHE_AIR_STOP("mob.dragon.breathweapon.air.breatheairstop", 1.317),

  BREATHE_WATER_START("mob.dragon.breathweapon.water.breathewaterstart", 0.733),
  BREATHE_WATER_LOOP("mob.dragon.breathweapon.water.breathewaterloop", 4.949),
  BREATHE_WATER_STOP("mob.dragon.breathweapon.water.breathewaterstop", 3.536);


  public final String getJsonName() {return DragonMounts.MODID + ":" + jsonName;}
  public final SoundEvent getSoundEvent() {return soundEvent;}
  public final double getDurationInSeconds() {return durationInSeconds;}
  public final int getDurationInTicks() {return (int)(durationInSeconds * 20.0);}

  /**
   * Information about the sound effect
   * @param i_jsonName
   * @param i_durationInSeconds the duration of the sound effect (0 = unused) - in practice, this is the duration
   *                            before the cross-fade to the next sound starts.  For looping sounds no effect
   */
  private SoundEffectNames(String i_jsonName, double i_durationInSeconds) {
    jsonName = i_jsonName;
    durationInSeconds = i_durationInSeconds;
    soundEvent = registerSound(jsonName);
  }

  private final String jsonName;
  private final double durationInSeconds;
  private final SoundEvent soundEvent;

  private static SoundEvent registerSound(String soundName) {
    ResourceLocation soundID = new ResourceLocation(DragonMounts.MODID, soundName);
    return new SoundEvent(soundID).setRegistryName(soundID);
  }
}
