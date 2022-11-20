package com.github.Soulphur0.dimensionalAlloys;

public interface StatusEffectInstanceMethods {

    default void setShowParticles(boolean showParticles){}

    default void setHiddenByCrystallization(boolean isHiddenByCrystallization){}

    default boolean isHiddenByCrystallization(){ return false;}
}
