package com.nori6272.wherearemytms.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.nori6272.wherearemytms.WhereAreMyTMs;

public class WAMTBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            WhereAreMyTMs.MODID);

    public static final RegistryObject<Block> TM_MACHINE_BLOCK = BLOCKS.register("tm_machine",
            () -> new TMMachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().requiresCorrectToolForDrops().strength(1.0F)));

}
