package com.github.Soulphur0.dimensionalAlloys;

public interface EntityCometBehaviour {

    // $ Crystallization process
    // _ Crystallization process' properties accessors.
    // * Touching End medium switches.
    default void setInEndMedium(int inFreshEndMedium){}

    default boolean isInFreshEndMedium(){
        return false;
    }

    // * Crystallization ticks accessors.
    default void setCrystallizedTicks(int crystallizedTicks){}

    default int getCrystallizedTicks(){
        return 0;
    }

    // * Crystallization state accessors.
    default void setCrystallized(boolean isCrystallized) {}

    default boolean isCrystallized(){
        return false;
    }

    // * Status effect accessors.
    default void setCrystallizedByStatusEffect(boolean crystallizedByStatusEffect) {}

    default boolean isCrystallizedByStatusEffect() { return false;}

    // * On-crystallization saved attributes accessors.
    default void setOnCrystallizationBodyYaw(float bodyYaw) {}

    default float getOnCrystallizationBodyYaw(){ return 0.0f;}

    // _ Crystallization process' attributes.
    default int getMaxCrystallizedTicks(){
        return 0;
    }

    default float getCrystallizationScale(){
        return 0.0F;
    }

    // _ Crystallization utilities
    default boolean isCrystallizationInterrupted() { return false;}

    // $ Statue rendering.
    default void setStatueMaterial(){}

    default String getStatueMaterial(){ return null;}

    // $ Fire extra behaviour
    default void setSoulFireTicks(int ticks){}

    default int getSoulFireTicks(){ return 0;}

    default void setEndFireTicks(int ticks){}

    default int getEndFireTicks(){ return 0;}
}