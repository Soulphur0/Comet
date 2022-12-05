package com.github.Soulphur0.dimensionalAlloys;

public interface CrystallizedEntityMethods {

    // _ Crystallization process' accessors.
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

    // _ Crystallization process' attributes.
    default int getCrystallizationFinishedTicks(){
        return 0;
    }

    /** <p>Used to get a value between <b>0.0</b> and <b>1.0</b> which determines how much crystallization
     * advanced even if the required ticks for complete crystallization are modified.<p/>
     * <p>Mainly used to calculate overlay opacity.<p/>
     * @see com.github.Soulphur0.mixin.gui.InGameHudMixin
     */
    default float getCrystallizationScale(){
        return 0.0F;
    }

    // _ Statue material accessors.

    default void setStatueMaterial(){}

    default String getStatueMaterial(){ return null;}

    // ? Utilities
    default boolean isCrystallizationInterrupted() { return false;}
}