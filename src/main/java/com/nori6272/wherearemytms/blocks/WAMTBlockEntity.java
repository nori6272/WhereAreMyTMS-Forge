package com.nori6272.wherearemytms.blocks;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.nori6272.wherearemytms.WhereAreMyTMs;

public class WAMTBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, WhereAreMyTMs.MODID);

    public static final RegistryObject<BlockEntityType<TMMachineBlockEntity>> TM_MACHINE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("tm_machine_block_entity", () ->
                    BlockEntityType.Builder.of(TMMachineBlockEntity::new,
                            WAMTBlocks.TM_MACHINE_BLOCK.get()).build(null));
}
