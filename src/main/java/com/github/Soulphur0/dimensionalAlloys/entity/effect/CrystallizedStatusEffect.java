package com.github.Soulphur0.dimensionalAlloys.entity.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class CrystallizedStatusEffect extends StatusEffect {

    public CrystallizedStatusEffect(){
        super(StatusEffectCategory.NEUTRAL, 0x7330a9);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.setCrystallizedByStatusEffect(true);
        entity.setCrystallizedTicks(140);
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        entity.setCrystallizedByStatusEffect(false);
        if (!entity.isInFreshEndMedium())
            entity.setCrystallizedTicks(0);
    }
}
