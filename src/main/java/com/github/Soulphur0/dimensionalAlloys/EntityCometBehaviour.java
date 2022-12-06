package com.github.Soulphur0.dimensionalAlloys;

public interface EntityCometBehaviour {

    // _ Crystallization process
    // ? Crystallization process' accessors.
    // * End medium switches.
    default void setInFreshEndMedium(int inFreshEndMedium){

    }

    default boolean isInFreshEndMedium(){
        return false;
    }

    // * Crystallization ticks accessors.
    default void setCrystallizedTicks(int crystallizedTicks){}

    default int getCrystallizedTicks(){
        return 0;
    }

    // * Crystallization accessors.
    default void setCrystallized(boolean isCrystallized) {}

    default boolean isCrystallized(){
        return false;
    }

    // * Status effect accessors.
    default void setCrystallizedByStatusEffect(boolean crystallizedByStatusEffect) {}

    default boolean isCrystallizedByStatusEffect() { return false;}

    // * On-crystallization attributes accessors.

    default void setOnCrystallizationBodyYaw(float bodyYaw) {}

    default float getOnCrystallizationBodyYaw(){ return 0.0f;}

    // ? Crystallization process' attributes.
    default int getCrystallizationFinishedTicks(){
        return 0;
    }

    default float getCrystallizationScale(){
        return 0.0F;
    }

    // ? Statue material accessors.
    default void setStatueMaterial(){}

    default String getStatueMaterial(){ return null;}

    // ? Utilities
    default boolean isCrystallizationInterrupted() { return false;}

    // _ Fire extra behaviour
    default void setSoulFireTicks(int ticks){}

    default int getSoulFireTicks(){ return 0;}

    default void setEndFireTicks(int ticks){}

    default int getEndFireTicks(){ return 0;}
}