![imagen](https://user-images.githubusercontent.com/75187144/214413931-eb104440-8357-4170-8c5d-40b5f6084eb4.png)
# Comet 1.0.0

Comet is a mod that aims to give more life to those parts of the game that feel undeveloped, in a vanilla-friendly way, following logical design choices and trying to respect Vanilla Minecraft's design tendencies.

In this first release, Comet introduces new content to The End, in a way that tries to give a definitive answer to the controversy regarding this dimension's identity, and how to approach it when adding content.

If you have a little bit of time, I highly recommend you to watch the video embedded below to understand the design choices that were made in the making of this mod.

**Please notice:**<br>
This first release contains a lot of concepts that can, and will, be developed upon in future versions. This mod is being released as an ALPHA, and while some features are almost completely fleshed out, it is likely for some others to not work as intended.

If any issues, or compatibility problems arise during the gameplay with this mod, please, let me know through any of the communication channels I will provide you with below.

As a final note to this introduction notice, do not put any valuable world at risk with this mod, since some features are likely to be tweaked, specially The End's world generation. 

While in most cases the worst thing that could happen is losing this mod's items, The End dimension is influenced by a world generation datapack and some world generation custom code that will 100% worked upon a lot more in the future, and it's unpredictable if newer versions will adapt well enough to older world generation, which can lead to splitted terrain generation at the borders of old chunks.

___

# TLDR; What does this mod do?

1. If you haven't, please read the previous notice.

2. Added new blocks to The End's terrain generation with interesting new mechanics!
When you think you've already found everything try using night vision…

3. Crystallization: transport mobs in a more convenient and vanilla-friendly way or turn them into decorative statues! Or crystallize yourself to be invincible!!
Although you won't be able to move much…

4. Crystallization effect: Temporarily crystallize your enemies, this type of crystallization comes with its own separate characteristics from the aforementioned one.
Crunchy on the outside, but soft on the inside…

5. Added a new mineral to The End, a tube-like formation with organic origins and its own unique way to look for! Not simply diggin' at low level altitudes! 
With it, you can craft a new armor set and a very special shield…

6. New armor set! A netherite level armor with its own special attributes, focused on mobility and with its own minimalistic custom model! Antennas, rudders? And yes, you can wear elytras with it, of course you can! 
Meeting certain conditions that is…

7. The portal shield: deflect projectiles and teleport them back into your enemies! 
Looking at it is mesmerizing…

8. End fire! Catch on fire and teleport around like a flaming enderman would!
It still hurts though, you better be careful…

9. And some other blocks and mechanics that let you obtain or farm material to craft some  of the stuff already mentioned.

10. If you want to explore all the features this mod brings in more detail, please feel free to keep reading the mod's page where they are completely explained.

___

## The mod in-depth
I've made a video showcasing the mod's design philosohpy and some of its main features, if you have some time to spare, you can check it out!

[](INSERT VIDEO)

The full list of features, compleately detailed and with comments on design choices can be found below.
I will later remove the list below and replace it with the GitHub wiki page when I finish making it, so you don't need to sail through an endless text file.

<details>
  <summary>Game Mechanics</summary>
  
  ## Crystallization
Crystallization is the shining star of this first Comet update. This game mechanic allows you to do many things, but before I start explaining it, you must know that there are two types of crystallization: crystallization by a block, and crystallization by status effect. Let's begin by explaining the former.

### Crystallization by a block.

![imagen](https://user-images.githubusercontent.com/75187144/214414041-1ddc3a11-bd06-4142-841f-6b0113b36002.png)

**Design choices**

The End dimension, like any other part of a well thought out game, works around its own specific theme in the gameplay's lifecycle, in this particular case that theme is transport.

And we shouldn't be much surprised, the characteristic ability of endermen is to teleport around! Back in the 1.9 update we saw this thematic developed upon more, with the introduction of elytras which made player transport a lot more convenient, and shulker boxes which made item transport a lot more efficient too.

With crystallization I aimed to make mob transport a lot less tricky, a more vanilla way of making the classical 'golden lassos'. While staying within the thematic of The End and the unifying force (or block) that I introduced with this mod: End Medium, which we will explore later.

**Game Mechanics**

Crystallization by a block occurs when a mob gets in contact with an End Medium block, or an End Medium Layer block; said mob will begin to turn purple and players will also get a screen overlay effect similar to the one powder snow gives.

<ins>**_Crystallizing mobs_**</ins>

When the entity finishes turning purple it will successfully crystallize, turning into a block known as a Crystallized Creature block. This block can be gathered with any tool or your bare hand, and can be placed anywhere in the ground as long as the block has a hitbox in its center (i.e: fences, walls; but not a cauldron or a composter).

![imagen](https://user-images.githubusercontent.com/75187144/214415298-888ddc35-2433-4c4b-83b3-197fe1618199.png)

You can de-crystallize an entity by placing water inside of the crystallized creature block which can be waterlogged. Name, status effects, villager trades, collar color, and any data that is stored in an entity's NBT tag is preserved. The entity that got crystallized and then released back is in fact the same one, so much so that their UUID is kept and duping the block and releasing two copies of the same entity makes the new copy disappear because Minecraft itself doesn't allow two entities of the same UUID.

Bosses (Enderdragon and Wither), semi-bosses (Elder Guardian, Ravager, Warden) and Iron Golems cannot be crystallized, although I have plans to make them crystallizable in the future under certain circumstances, like last-hitting them with a special weapon.

Crystallized creature blocks have a crystal base that can be removed by right-clicking them with a pickaxe, this will give you a 'cleaner' version of your crystallized mob, but if what you want is a decorative statue, you can go a step further.

![imagen](https://user-images.githubusercontent.com/75187144/214413522-82e03d2e-cc02-4022-bcdf-d145fe701367.png)

With a crystallized creature block and four amethyst shards you can craft a Creature Statue, a block that will permanently seal the crystallized mob, and remove the crystallized overlay off it, leaving what looks like the regular version of the mob. You can then, if you want, right click it with quartz to turn it into a quartz statue, or remove the quartz by right-clicking it with a pickaxe if you weren't convinced by the finishing touch. I'll make more materials for statues in the future.

End mobs are naturally immune to crystallization by regular means, but they can still get crystallized.

<ins>**_Crystallizing players_**</ins>

![imagen](https://user-images.githubusercontent.com/75187144/214416828-1c0d497c-fa31-49c7-8729-973522a1398c.png)

When players crystallize they don't turn into blocks, instead, they become unmovable, un-pushable and invulnerable, almost like a block. In this state the player is theoretically invincible, they cannot be hit, their health doesn't lower, their food bar won't deplete and their status effects' timers will freeze in place; these three things are all respectively indicated in a graphic manner by: 

- Displaying their body with a crystallized texture → unhittable. 
- Turning their hearts into heart-shaped crystals → health modification and status effects won't apply.
- Crystallizing their food bar → indicates that natural regeneration won't apply.

These indications will make more sense once we review the other type of crystallization.

If the player moves, tries to use an item, or tries to attack they will break out of the crystallized state, so this is more of a 'preservation' mechanic that could be useful for AFK-ing, but has potential for other uses.

Bear in mind that crystallization is not instant and won't begin on an entity that has recently received damage, so it can't be used to cheat death, at least not with this method…

### Crystallization by status effect.

![imagen](https://user-images.githubusercontent.com/75187144/214418471-39f8951c-9d52-4fe9-8a4d-4ad0801bcab4.png)

**Design choices**

Crystallization potions are designed with PvP in mind, although they can be useful in PvE too; they act as a 'stun' or a 'timeout', but not just that, since they can be very lethal or save your life if used properly, because the type of crystallization they apply is slightly different from the one we just saw.

**Mechanics**

Crystallization potions can be brewed with a Bottle of Concentrated End Medium, a material that is showcased ahead in the items section.

These potions will crystallize a player or an entity (without turning it into a block) for a brief period of time: 

- 8 seconds for regular and splash potions and 16 seconds for long duration regular and splash potions.
- 2 seconds for regular lingering potions and 4 seconds for long duration lingering potions.
- 1 second for regular tipped arrows and 2 seconds for long duration tipped arrows.

Unlike with regular crystallization, moving or using an item won't release you from this state, and actions like attacking, placing a block or using an item will just not work, the only thing you can do is drop items, pick up nearby items or move items inside your inventory.

When crystallized by this status effect, your health bar won't appear as crystallized, this means that, while your body and food bar are crystallized; indicating that you won't be affected by external sources of damage nor you can regenerate your health naturally, you will be affected by status effects, that will tick while you are crystallized by this effect.

This can be used both in an offensive and a defensive way:

Offensively, you could poison a player and then crystallize them, they will have to tank that damage since they won't be able to do anything for as long as the crystallized effect lasts.

Defensively, you can use a regeneration potion and then crystallize yourself, you can't regenerate health naturally, but that doesn't account for status effects, so no one will be able to harm you while you are healing inside your crystal shell.

Take into account that once you are crystallized you won't be able to receive any more status effects until you get de-crystallized, so think very well in which order you'll  use your potions.

<ins>Security measures</ins>

To avoid the spam of crystallization potions that could be very annoying for players and unbalanced for both PvP and PvE, any entity that was crystallized recently by a status effect won't be able to be crystallized again for a period of time equal to twice the maximum duration of the last source of crystallization; in the future a gamerule will probably be includen to further secure the spam of this effect.

The duration of the cooldown is regardless of the time that the actual effect lasted, which means that if you got hit by a 16 seconds crystallization potion but only got the effect for 5 seconds because you were not hit point-blank, you can't be crystallized by other players for 32 seconds. 
**Notice:** this cooldown mechanic doesn't apply if you are the one crystallizing yourself, but in case this leads to unbalance, the cooldown may apply to self-crystallization as well in the future.

![imagen](https://user-images.githubusercontent.com/75187144/214418825-6739572b-fdf6-424c-b69f-7147af6dae51.png)

For more balance purposes, mini bosses (Elder Guardians, Ravagers, Wardens) and Iron Golems, although they can be crystallized by a potion, its effect will last for only ⅓ of what is supposed to last and the full cooldown if the effect will still apply, so you can use a potion of crystallization to freeze a Warden for 4 seconds at maximum, but you better run fast the next 32…

The Ender Dragon and Wither bosses are immune to all status effects by default, and, by following these design principles they should be immune to the crystallization effect too, so no changes were made to these mobs.

Finally, for anti-exploit purposes, if a player is surrounded by lava or buried in blocks while it was crystallized, it will teleport to safety the moment it touches the lava (they still catch on fire), or the moment they begin to suffocate; it is not guaranteed if there is not enough room to find a teleport location though.

#### Known issues and what to expect in future versions

- Effect duration for potions can be tweaked in the future if it seems too long.
- Crystallized Allays won't appear as crystallized because they are rendered in a different way, expect this to be fixed in future versions.
- Some mobs may still play some movement animations when crystallized, this a very rare occurrence as I only saw it happen once, it is more common to see if they got crystallized by a potion though. I still have to investigate what causes this although I have some theories.
- When placing a crystallized creature duplicated in creative mode, all statues will rotate to the rotation of the last placed statue, since they all hold the same mob, this behavior, while not intended, doesn't do much harm since only affects creative mode and it can be avoided, but I may look into fixing it sometime.
- When releasing a crystallized creature duplicated in creative mode, the creature will instantly despawn if it has been already released and is still alive, since two entities with the same UUID cannot exist in the same world, this behavior is intended.

</details>
<details>
  <summary>Blocks</summary>
<details>
  <summary>End medium</summary>

### End medium

![imagen](https://user-images.githubusercontent.com/75187144/214419371-fe120f96-eb2c-4b7a-bfb3-26b379e0b8d3.png)

### Lore
This substance is an extremely dense liquid that forms crystals when it gets in contact with air, growing upwards and giving shape to large vertical crystal columns that take millions of years to form.

This substance is the base of life in The End, and just like end stone is the anti-cobblestone, end medium is the anti-water of this twisted and inverted, alien dimension.

The sides of this block are composed of dried out medium that has formed sharp vertical crystals while the top of the block remains partially exposed, pushing fresh medium to its surface from its insides.

### Design choices
Along with crystallization, End Medium is the main protagonist of this first release of Comet.

This block was designed with the idea in mind that it should be the equivalent to water in The End, like some sort of anti-water, much like end stone is anti-cobblestone; giving origin to all the shared properties that the living creatures of The End present though its own properties.

It's a polar opposite to its overworld equivalent, WITHOUT recurring to a simple 1:1 design by simply being "purple water" or "purple water that flows upwards", much like Striders were added accounting for the properties of unburnable boats, without recurring to repetitive, lazy or oversimplified design, that some mods added in the past through obsidian/iron and alike boats, in order to supply the need for a lava-based transport method.

![imagen](https://user-images.githubusercontent.com/75187144/214419804-be94bd09-0d75-4882-b523-c8b1599ab5c2.png)

### Mechanics
This block acts similarly to powder snow, since you can sink into it, but, since end medium is based on Non-Newtonian fluids, whenever an entity is moving with enough speed, it won't be able to sink into it, but if you stay still on top of it or sneak very slowly towards it, you will begin to very slowly sink on it.

This block generates layers of end medium on top of itself.

Whenever you are not moving on end medium you will begin to crystallize. If you move, take damage, or begin an action like attacking or using an item the crystal around you will shatter, interrupting the process, stay still and it will begin again.

All entities that get crystallized by it will be placed as a block replacing it, except players, who don't turn into blocks.

You can also set it on fire to produce End Fire.

This block also produces particles around it, very rarely, simulating upwards escaping fumes. A lot of blocks of end medium together will make this effect more apparent, giving a nice atmosphere to it.

Finally, your air meter depletes at the same rate as being underwater when inside this semi-solid, so watch out for that!

### Where to find it and how to gather it
End medium can be found anywhere across the outer End Islands, forming tall clusters of columns called "Vertical Lakes" or "Vertical Puddles".

It can be gathered with a shovel, it's a bit hard to mine, since it is a viscous material.

### Known issues and what to expect in future versions
- Right now, vertical lakes use the same generation algorithm as basalt columns from basalt deltas; it will be changed in the future to give them their own unique shape.

- Running while jumping over large surfaces of end medium in survival mode causes jittering player movement since the server will try to reposition them. I am well aware of this problem which appears to have something to do with synchronization. It is minor, but I've been struggling to fix it since I cannot spot the origin of this problem, expect it to be fixed in future versions.

</details>

<details>
  <summary>End medium layer</summary>
  
### End medium layer

![imagen](https://user-images.githubusercontent.com/75187144/214421399-c193c5ef-0f1e-40b1-a867-1ab09359d5c7.png)

### Lore
End medium sometimes releases a thin layer of substance on top of it, over thousands of years, it builds up, drying in the process, producing a new block of end medium on top, making vertical puddles grow.

These fresh layers of medium are more brittle than regular medium, but equally reactive.

### Design choices
Originally, end medium was going to be a solid block, and this layer would be a single-level fluid that would have the crystallization mechanics; midway through development I decided to change end medium to the way it is now and repurpose this layer as a you-don't-sink-in crystallization catalyst, in other words, staying on top of this block also crystallizes you, but you won't be shoved into a viscous block, also it is easier to renew than end medium itself, more on that later.

Lore-wise, these blocks build up and dry to form new medium, but I didn't want to make them do this in-game since vertical lakes would grow to the build limit if you stood enough time near them, breaking immersion a little and ruining the views of the landscape of The End; since vertical lakes are supposed to grow over millions of years, I decided not to include this mechanic, I am open to revisit it sometime though.

![imagen](https://user-images.githubusercontent.com/75187144/214422164-edd793e9-a2ae-41c6-9f23-9742e9412d1c.png)

### Mechanics
It will crystallize entities that are on top of it, replacing the block with the crystallized entity, unless it is a player.

### Where to find it and how to gather it
It can be found on top of end medium, which, after some time, generates end medium layers on top of itself.

To gather it, you require a silk touch shovel.

### Known issues and what to expect in future versions
There are no known issues regarding this block.
This block might get some more mechanics in the future.
</details>

<details>
  <summary>End fire</summary>
  
### End Fire

![imagen](https://user-images.githubusercontent.com/75187144/214423745-40862a51-d187-44dc-b6ce-696c148060e2.png)

### Lore
When dragons freely roamed The End's skies, it was common for them to gulp large amounts of end medium, this substance helped keep their scales hard and stiff and its digestion produced a very corrosive acid, so strong that it was compared with fire, it was known as dragon breath, when dragons still existed that is…

If someone were to simulate a dragon's digestion through a chemical process nowadays, it would definitely be combustion.

### Design choices
I always thought purple fire was very fitting for The End, I am not sure if purple fire is a common thing in End Mods, but I am going to assume it is; what I don't know if it's so common is the properties this fire has.

**As an additional note:**
When developing Comet, I decided to tamper a bit with the game's code and made Soul Fire render blue in the screen overlay and on entities set on fire by it, something that doesn't happen in vanilla Minecraft.

Another thing that doesn't happen in vanilla Minecraft is that, when you are set on fire by soul fire, you don't lose 1 heart per tick, you just lose half a heart, while, if you stay on top of soul fire you will lose 1 heart per tick; I changed so if you were set on fire BY soul fire, you will keep losing 1 heart until you are put out or you get set on fire by a different fire.

![imagen](https://user-images.githubusercontent.com/75187144/214425467-c8406ae0-0e72-4ea8-aebe-a766ede7fba1.png)

### Mechanics
When set on end fire, you will begin to teleport to a random nearby location every time you get damaged by the fire, similar to how an enderman teleports when it is set on fire.

When a recently de-crystallized entity touches lava, it will be set on End fire, teleporting it to safety, but receiving the fire's burn damage.

### Where to find it and how to gather it
You can get this block by setting End Medium on fire.

### Known issues and what to expect in future versions.
- While players usually render fire just fine, right now, many mobs render on blue fire when they are set on fire, I will make sure this is one of the first things that get fixed for the next release.
- The game overlay of fire may display a normal fire tick before displaying fire of a different color, I'll work to fix that too as soon as possible, as minor as it is, it's a bit annoying.
</details>

<details>
  <summary>Concentrated End medium</summary>
  
### Concentrated End Medium

### Lore
This substance is pure end medium, or more like adulterated end medium; so strong that creatures that are usually immune to the crystallization effect of end medium will be affected by this substance; being pure end medium though, it dries out very quickly, oxidizing within seconds and turning into regular end medium.

### Design choices
Concentrated end medium solved a lot of issues I had during the development of this mod: it made it possible to have a way to crystallize end creatures, which are immune to regular end medium, it served as the ingredient for crystallization potions that for a long time I wasn't sure what it was going to be, and gave a purpose to a farming mechanic explained ahead at the Endbrite Tube block entry, among other uses it will serve for in the future.

### Mechanics
If an entity enters in contact with this block it will get instantly crystallized.

End mobs, which are immune to crystallization by touching regular end medium can get crystallized by concentrated end medium.

When placed on the ground, it will dry out after some time turning into regular end medium.

When several blocks of concentrated end medium are placed on top of another, it will dry out in layers, the lowermost layers will dry out first, respecting the lore of the crystals that slowly grow by squirting liquid that dries out on its uppermost surface.

The heat given off by a froglight can interrupt this drying process, so if a concentrated end medium block is next to a froglight, and another concentrated end medium block is placed on top to the heated block none of the two will dry out, this can be repeated ad-infinitum. 

You still cannot crystallize bosses or semi-bosses with this block.

### Where to find it and how to gather it
Concentrated end medium can be obtained through farming or by picking it up from a block that can sometimes generate beneath special chorus plants, this is done using buckets.

### Known issues and what to expect in future versions.
- No issues so far with this block.
</details>

<details>
  <summary>End Drenchstone</summary>
  
### End Drenchstone

### Lore
When a chorus plant roots grow far enough into end stone, it is not uncommon to find this type of rock around them, with hollow cavities once occupied by roots that decayed long ago.
Design choices
Drenchstones are a family of blocks introduced by Comet, they were initially planned for a different release, but since the already planned expansions for Comet are interconnected, I had to work on these earlier.

There are three types of drenchstones: overworld drenchstone, nether drenchstone and end drenchstone.

While the first two can only be found in the creative inventory since they are not finished yet, end drenchstone is finished, and can be found in The End. 

Its mechanics keep building upon the already established theme: The End's place in the game's progression is the facilitation of transport.

### Mechanics
End drenchstone allows you to easily transport fluids, you can fill this stone with a single bucket of water, lava, or concentrated end medium. If you right click it with an empty bucket, you can pick back the fluid it stores, you can also mine this block while it holds a fluid and stack it up to 64 blocks, so you can carry in a single slot way more than just a bucket of fluid.

This block will also automatically store any fluid adjacent to it if said fluid block is a source block; removing that block in the process.

### Where to find it and how to gather it
Some chorus plants have roots that pierce the outer End Island they inhabit down to their very bottom, around these roots you will find end drenchstone blocks, usually filled with concentrated end medium.

### Known issues and what to expect in future versions.
- No issues found, nor planned features so far with this block.
- I've tried making it store modded fluids but it didn't work out very well, in the very far future I might revisit the idea.
</details>

<details>
  <summary>Rooted End Stone</summary>

### Rooted End Stone

### Lore
Chorus Plants not only grow tall, but they grow deep too, some plants in particular have roots that go as deep as the island they inhabit can provide, and they are a key factor for the formation of Endbrite Tubes, which is a supermaterial comparable to the legendary netherite.

### Design choices
I wanted to build this mod around the fact that The End has an ecosystem, an alien one, but an ecosystem regardless. Chorus plants are very important for this ecosystem since they are the base for The End's food chain much like real plants are in the real world. 

They provide a source of food for endermen - if we follow commonly accepted theories about the evolution of end life - and are likely the ones that gather end medium from the crevices of the raw end stone into their roots and, therefore, into end drenchstone.

These roots, when in contact with the concentrated end medium that they naturally gather, they get overstimulated and begin to work harder in their duty, not only that but they inevitably drip excess medium below them, making the formation of endbrite tubes theoretically possible.

### Mechanics
Rooted end stone blocks will get overstimulated when they are adjacent to end drenchstone that holds concentrated end medium inside, these blocks then can be used to gather more concentrated end medium with the use of endbrite tubes.

### Where to find it and how to gather it
Some chorus plants have roots that pierce their host End Island down to their very bottom; those roots are inside these blocks.

### Known issues and what to expect in future versions.
- The texture for fresh/overstimulated rooted end stone is not its definitive version.
</details>

<details>
  <summary>Thorny roots</summary>

### Thorny roots

### Lore
Thorny roots are a kind of root that hangs from below the outer End Islands, and, contrary to popular belief, they don't belong to chorus plants but to a yet to identify plant-like organism that inhabits the islands.

These roots grow thorns or spikes as a way to defend themselves from potential predators, these spikes are very bright and emit light as a warning signal to not touch them.

The way these plants have developed their defense mechanism is very peculiar. When touched, their thorns, which are made out of a material very similar to thin glass, will break and inject venom into their victims, but that's not just it, they do it in a very unique manner. 

Thorny roots do not produce their own venom, instead, they steal harming substances from animals and other creatures across dimensions by teleporting them and then teleports them directly into their victims bloodstream rather than injecting them with a stinger.

### Design choices
I wanted endbrite tubes to be gathered in a different way from other ores, these roots not only provide decoration and more life to those unexplored corners of The End, but also make the hunt for this precious material much harder.

They also serve as a support for placing blocks when the player spots endbrite tubes when flying with an elytra.

### Mechanics
These roots act like vines, they are climbable and they grow with time, their spikes emit light just like glow berries do, but unlike glow berries, these grow back after a while when broken.

When an entity touches them, a random malicious status effect will be applied to them; the most likely to be applied are Poison and Wither, but if you are lucky you might just get poisoned with Hunger; and if you are very unlucky, there is a small chance you can get the Blindness effect, and trust me, that is the worst thing you can get when flying with elytra just above the void looking for Endbrite Tubes.

### Where to find it and how to gather it
They grow below the outer End Islands, when broken they might drop their block item.

### Known issues and what to expect in future versions.
- No issues nor planned features for this block so far.
</details>

<details>
  <summary>Endbrite tube</summary>

### Endbrite tube

### Lore
When end medium drips from rooted endstone over the Thorny Roots it may have below, it begins to crystallize them, these roots serve as a support for the emerging endbrite tubes, structures that form over very long periods of time and are shaped like tubes that grow one next to the other, 'older' tubes are longer that 'younger' tubes, since they've been growing ever since they first 'sprouted'. 

This material is of a very durable nature due to its grid-like molecular structure made of even smaller tubes 'glued' together. Due to its majorly hollow composition it is also very lightweight.

A real life example of a material like this would be Aerogel, but it is also based on carbon nanotubes.
Design choices
When making the 'netherite equivalent' of a mineral in The End, I wanted to make it in its own unique way, not just recoloring ancient debris to purple and stamping a sticker with the word "Enderite'' written on it.

This block consists of tubes, similar to sea pickles or candles, they can be stacked together, but hanging from a ceiling, up to 7 times on the same block.

In order to find it, you will have to fly beneath The End's outer islands, and look for it in a forest of very venomous thorny roots. This makes it differentiate itself a little from the whole "just dig very deep and find it" trope. Since this time it will be always exposed to air and the struggle of gathering it comes from finding it in a forest of poisonous, spiky, vine-like roots and getting to it without falling into the void.

I also made their dripping mechanic, only able to happen at The End, and below a certain altitude. This is to decentralize the farming of concentrated end medium, a design philosophy trend that Minecraft started with monster spawners and has revisited with budding amethyst blocks and that I really like.

### Mechanics
If endbrite tubes are placed below Y=25 in The End, they will start to drip some liquid, if you place these tubes beneath a fresh rooted end stone block - which is a dry rooted end stone block next to an end drenchstone filled with concentrated end medium - it will drip even more, and this time, you can put a cauldron below the tubes to gather concentrated end medium.

### Where to find it and how to gather it
It can be found below the outer End Islands, between all the thorny roots that grow there, it is a bit rare to find, but not too rare.

In order to obtain the block itself, you need a silk touch diamond or above pickaxe, an easier way to obtain the block itself is to break the block that it latches into.

If you use a diamond or above pickaxe without silk touch to mine it, you will obtain endbrite shards, required for crafting endbrite gear.

Using fortune can give you more endbrite shards.
### Known issues and what to expect in future versions
- Dripping particles sometimes get stuck inside the block itself, this is because the particles the block uses are the same particles that the crying obsidian block uses; in future versions, endbrite tubes will use their own custom particles.
- I may make players unable to pick up endbrite tubes by breaking the block that supports them, but I have to give it some thought.
- I may change the name of the block since technically 'Endbrite' is the alloy that forms when this material and iron combine.
</details>

<details>
  <summary>End iron ore</summary>

### End Iron Ore

### Lore
Iron hides within the stone of these floating islands, but it is not visible to the naked eye. Life from this place sees their home world from a different point of view quite literally since their eyes are very different, they can see this iron, but we cannot, if only there was a way to see it...
Design choices
Very simple: The Nether has a lot of gold in it, a material that is essential for crafting the netherite alloy. Then The End will have a lot of iron, essential for the crafting of Endbrite, an equivalent to netherite.

Not only that but iron is a very used material in crafting and it is very useful to have a large source of this material available at the late-game. While the Caves and Cliffs update made iron appear in larger quantities in the overworld thanks to the introduction of ore veins, I believe that for those late-game players like me who don't really like building Iron Farms that much, no matter how simple they are, it may not be that bad of idea that there could be a big source of iron in the place only late game players really visit.

Also, The End is very reminiscent of an asteroid field, I always believed that it was in fact some sort of broken world or asteroid field where life managed sprout or carry on, and asteroids are known to be oftenly composed of a lot of metals including iron, so I projected that little scientific fact into the more fantasy-like world of Minecraft and made end iron ore very common in these islands.

### Mechanics
End iron ore looks exactly like end stone, unless you have the Night Vision status effect on, in that case, end iron ore will show pink spots that reveal its position and ferrous composition.

### Where to find it and how to gather it
On the surface and inside the outer End Islands, you can mine it with the same pickaxes you can mine regular iron ore: stone or above.

### Known issues and what to expect in future versions
- This block is a block entity, like a chest or a furnace, it needs to be one in order to be able to display its pink spots when the player is under the effects of night vision, its high presence makes the world just a tiny bit more laggy, since I want to increment the amount of appearance of this ore I will be working on optimizing it, I already have some plans. Still it shouldn't impact performance that much right now.
- The shape of the veins that they form will change in the future.
</details>

<details>
  <summary>Chorus humus</summary>
  
### Chorus Humus

### Lore
Chorus Flower petals that fall from their plants build up in the ground to form this podzol-like humus, it comes in fresh and dryed out versions.

### Design choices
Right now it only serves a decorative purpose and it will worked upon a lot in the future since I personally don't like soils like grass, podzol or mycelium being present in The End, but I still think there is a place for it and I've got quite a lot of mechanics planned for it.

### Mechanics
Chorus plants can be planted on them, and, for now, they only generate in them, although that will probably be reverted back to how they usually generate.

### Where to find it and how to gather it
Anywhere on the surface of the End Highlands biome (the center of The End's outer Islands).

### Known issues and what to expect in future versions
- A lot of changes are expected for this block in the future.
</details>
</details>

<details>
  <summary>Items and equipment</summary>
  
  <details>
  <summary>Endbrite shard</summary>

### Endbrite shard

### Lore
These shards are smaller pieces of endbrite tubes and can be used to craft stuff with.
Design choices
They are the prior step to netherite scrap if we want to compare them with netherite. 

I have yet to make some decisions on the crafting flow of endbrite ingots, either this item or endbrite fibers may be removed in the future if I don't find a better use for any of the two beyond 'you need this to craft this other thing'.

I wanted endbrite ingots to have the same crafting flow as netherite ingots, since both are 'dimensional alloys' (more on that concept in the endbrite ingot's entry) I want them to have some things in common, one of them being the crafting process, but I highly dislike having items with no purpose other than crafting one thing, so I'll either find additional purpose for them or remove them altogether.

I decided to add them in the first place because I wanted the player to be able to use fortune with endbrite tubes without them being able to dupe them and placing them back infinitely.

### Mechanics
They can be smelt into endbrite fibers, required in the crafting of endbrite ingots.

### Where to find it and how to gather it
You can get them by mining endbrite tubes without silk touch, with a diamond or above pickaxe. You can get more per tube mined using fortune.

### Known issues and what to expect in future versions
- I may remove this item or endbrite fibers, but I might as well not if I find good uses for both of them.
  </details>
  
  <details>
  <summary>Endbrite fibers</summary>
### Endbrite fibers

### Lore
When exposed to high temperatures, endbrite shards disassemble into very resistant fibers that form a fabric which can be used to forge endbrite ingots.

Since the resultant fabric is also very heat resistant, only the acidic breath of a dragon can make it melt and combine with iron to form endbrite ingots.

### Design choices
This is the equivalent to netherite scrap if we were to compare endbrite with netherite.

I decided to include dragon breath in the crafting recipe to further increase the value and the challenge of crafting the armor, it still shouldn't be too hard to craft it since you first face the dragon before going to the outer islands, it also gives more purpose to dragon's breath.

And also I liked the lore implications it had regarding being so resistant that only a dragon can melt it.

### Mechanics
It is used to craft endbrite ingots with 4 iron ingots and 1 bottle of dragon breath in shapeless crafting.

It is also used in the smithing table to craft the portal shield.

### Where to find it and how to gather it
It is obtained by smelting an endbrite shard.

### Known issues and what to expect in future versions
- I may remove this item or endbrite shards, but I might as well not if I find good uses for both of them.
  </details>
  
  <details>
  <summary>Endbrite ingot</summary>
  
### Endbrite ingot

### Lore
This dimensional alloy is the apex of supermaterials, at least regarding anything that can be found in The End dimension. Forged with the breath of a dragon it is not only durable, but it is also very lightweight.

### Design choices
Comet was originally going to be called Dimensional Alloys, a mod that came from the idea that, since netherite is an alloy of ancient debris and gold, there must be other of these "dimensional alloys" with the other metals: iron and copper.

Endbrite is The End's equivalent to netherite. 

It is NOT an upgrade from netherite, but a replacement to netherite, since I thought it would be more logical for dimensional alloys to be 'siblings' rather than 'descendants', and The End's difficulty is not much greater than The Nether's, if it is not even lower. 

I don't like the concept of power creeping the player with 'yet another upgrade to my armor/tools' if it is not really necessary. 

I thought it would be more natural for endbrite to be an equivalent to netherite, as well as for the Copper-based Dimensional Alloy that will come in future expansions, which will also be a replacement to netherite and not an upgrade.

I know this is a controversial take, but bear in mind that I am not against gear upgrades. I think gear upgrades have a place in the game, but their place would be in a moment further into the game's progression like a 'new super hard-late-game' dimension where a gear upgrade would be logical.

### Mechanics
It can be used in a smithing table with a diamond gear piece to upgrade it to endbrite.

### Where to find it and how to gather it
It can be crafted using 4 endbrite fibers, 4 iron ingots and 1 dragon's breath in shapeless crafting.

### Known issues and what to expect in future versions
- There are no issues found with this item so far.
- In the future you will be able to craft endbrite tools and weapons too.
  </details>
  
  <details>
  <summary>Endbrite Armor</summary>
### Endbrite Armor

### Lore
This armor is made of the ultra-resistant, ultra-lightweight endbrite material, which provides a protection equivalent to netherite, while allowing you to run faster and even fly with it.

### Design choices
This whole mod began with this idea, of an amor equivalent to netherite in protection but that exchanged some of the attributes of netherite with other things.

It has a custom model, it is not just an armor reskin, the helmet has antennas and the chestplate and leggings have rudders, I kept the model minimalistic, not too moddy, reminiscent of vanilla.

### Mechanics
Endbrite armor provides the same level of protection as netherite armor, with the same durability.

Endbrite armor, instead of giving you knockback resistance, provides you with movement speed. Each piece gives you +5% of movement speed. A full set is equivalent to the Speed I status effect that can, of course, still be applied with the potion for further speed.

Endbrite armor, instead of being unburnable, lets you equip elytra and fly with it, ONLY if you are wearing the full set, otherwise, your elytra will split from the chestplate and be returned back to you.

To equip an elytra with the armor, put the full armor on and right click an elytra in your hand.

You can retrieve the combined elytra-chestplate item from your inventory, to split both items just equip it with a missing piece of the full set or put it on the crafting grid.

When you combine the items, their custom names, enchantments and NBTs WON'T be lost, the elytra-chestplate will keep the data of the chestplate and the elytra will keep its data when it splits from the chestplate.

### Where to find it and how to gather it
It can be crafted on a smithing table using endbrite ingots in diamond armor just like you would craft a netherite armor.

### Known issues and what to expect in future versions
- Elytra chestplate only uses the durability and enchantments of the chestplate, in the future I will probably make the enchantments of the elytra apply to the chestplate too and the durability of the elytra be independent of the chestplate.
  </details>
  
  <details>
  <summary>Portal Shield</summary>
### Portal Shield

### Lore
This shield holds the teleportation powers of The End.

### Design choices
I wanted to make a special shield that reflected arrows back like a mirror, then I got a better idea :)

### Mechanics
Projectiles blocked with this shield will be teleported back to their owner, from a random point above, and around them.

If the thrower of the projectile is immune to their own projectile, or the thrower is not an entity, the projectile will be consumed by the shield and disappear.

### Where to find it and how to gather it
It can be crafted with endbrite fibers and a shield on the smithing table.

### Known issues and what to expect in future versions
- I've planned to give more cool visual and sound effects to the shield. I am still not satisfied with it.
- I might make the shield reflect projectiles like Blaze Fireballs as snowballs to harm them instead of just making them disappear.

  </details>
  
  <details>
  <summary>Concentrated End Medium Bottle and Crystallization Potions</summary>
### Concentrated End Medium Bottle and Crystallization Potions

### Mechanics
Concentrated End Medium Bottles can be obtained from a cauldron filled with concentrated end medium, that can be obtained by placing a bucket of concentrated end medium in a cauldron or by letting an endbrite tube drip over a cauldron under the right circumstances and then retrieving the product.

This bottle of concentrated end medium can be used to brew crystallization potions mixing it with an awkward potion. You can make the effect longer with redstone, and make splash, lingering potions and tipped arrows with it.

### Known issues and what to expect in future versions
- There are no issues regarding these items so far.
  </details>
</details>


## FAQ
### Can I modify or redistribute the mod?
You can work upon the mod's source and/or redistribute it, as long as you give credit to the original author and use the same license. I recommend reading the license below for more information.

### Will a port for Forge ever be made?
I do have plans to personally port this mod to Forge, but notice that these plans are in the very long run. So please, bear in mind it won't happen anytime soon and refer to this notice when asking for a port.
Also,I might change my mind in the future and not port it myself, it's unlikely but not impossible.

### Can I port the mod to Forge?
License allows you to do it, and so do I. But notice that everyone has the right to port it to Forge, and even if ports were made by third parties I may eventually publish a port under the "Official Forge Port".

## Licensing
This project is licensed under the Attribution-NonCommercial-ShareAlike 3.0 Unported license. A copy of the license can be found at https://creativecommons.org/licenses/by-nc-sa/3.0/legalcode

## Attributions
All sound effects in this project are modifications of already existing sounds in vanilla Minecraft or a mix of these sounds with the following sources, which also were modified in some cases. None of the sounds used belong to me, and credit should be given to their corresponding authors.

Sound effect:
https://freesound.org/people/kyles/sounds/452645/

<details>
  <summary>Used in:</summary>
  
  - crystallization_grows.ogg
  - concentrated_end_medium_bottle_empty_1.ogg
  - concentrated_end_medium_bottle_empty_2.ogg
  - concentrated_end_medium_bottle_fill_1.ogg
  - concentrated_end_medium_bottle_fill_2.ogg
  - concentrated_end_medium_bucket.ogg
  - concentrated_end_medium_bucket_empty_1.ogg
  - concentrated_end_medium_bucket_empty_2.ogg
  - concentrated_end_medium_bucket_fill_1.ogg
  - concentrated_end_medium_bucket_fill_2.ogg
</details>


Sound effect:
https://freesound.org/people/Garuda1982/sounds/560310/

<details>
  <summary>Used in:</summary>
  
  - creature_statue_scrap_1.ogg
  - creature_statue_scrap_2.ogg
  - creature_statue_scrap_3.ogg
</details>

Sound effects:

https://freesound.org/people/IENBA/sounds/607910/

https://freesound.org/people/cupido-1/sounds/617471/

<details>
  <summary>Used in:</summary>
  
  - thorned_roots_break_1.ogg
  - thorned_roots_break_2.ogg
  - thorned_roots_break_3.ogg
  - thorned_roots_break_4.ogg
</details>

## Contact
If you want to stay in touch with me and/or be informed about all news regarding the development of this mod you can join my Discord server with the link below.

[https://discord.gg/58C3qZsSHk](url)

If any issue is found it is preferable to report it via the "Issues" section in the mod's GitHub repository, but you can still report it via Discord if that's more convenient to you.

## Supporting me
You can support me by simply downloading the mod and sharing it with friends!
