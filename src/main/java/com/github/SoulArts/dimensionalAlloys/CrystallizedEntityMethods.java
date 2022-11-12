package com.github.SoulArts.dimensionalAlloys;

public interface CrystallizedEntityMethods {

    default void setInFreshEndMedium(int inFreshEndMedium){

    }

    default void setCrystallizedTicks(int crystallizedTicks){

    }

    default int getCrystallizedTicks(){
        return 0;
    }

    default int getCrystallizationFinishedTicks(){
        return 0;
    }

    default boolean isCrystallized(){
        return false;
    }

    /** <p>Used to get a value between 1.0 and 0.0; which determines how much crystallization
     * advanced even if the parameters for it are modified.<p/>
     * <p>Mainly used to calculate overlay opacity.<p/>
     */
    default float getCrystallizationScale(){
        return 0.0F;
    }
}
