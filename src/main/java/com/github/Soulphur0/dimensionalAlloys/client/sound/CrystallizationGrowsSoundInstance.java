package com.github.Soulphur0.dimensionalAlloys.client.sound;

import com.github.Soulphur0.Comet;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundCategory;

public class CrystallizationGrowsSoundInstance extends MovingSoundInstance {

    private final LivingEntity source;

    public CrystallizationGrowsSoundInstance(LivingEntity source, SoundCategory soundCategory) {
        super(Comet.CRYSTALLIZATION_GROWS, soundCategory, SoundInstance.createRandom());
        this.source = source;
        this.repeat = true;
        this.repeatDelay = 0;
    }

    @Override
    public boolean canPlay() {
        return !(source instanceof MobEntity && source.isSilent());
    }

    @Override
    public void tick() {
        if (this.source.isRemoved() || this.source.getCrystallizedTicks() == 0 || this.source.isCrystallized()){
            this.setDone();
            return;
        }

        // - Update sound position.
        this.x = (float)this.source.getX();
        this.y = (float)this.source.getY();
        this.z = (float)this.source.getZ();

        // - Update sound volume and pitch.
        this.volume = source.getCrystallizationScale();
        this.pitch = source.getCrystallizationScale() + 0.5f;
    }
}
