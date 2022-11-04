package com.github.SoulArts.configPackage;

import me.lortseam.completeconfig.api.ConfigContainer;
import me.lortseam.completeconfig.data.Config;

public class ElytraAerConfig extends Config implements ConfigContainer {

    @Transitive
    private final ModConfigGroup myFirstGroup = new ModConfigGroup();


    public ElytraAerConfig(){
        super("comet");
    }
}
