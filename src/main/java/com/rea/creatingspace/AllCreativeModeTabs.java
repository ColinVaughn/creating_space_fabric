package com.rea.creatingspace;

import com.rea.creatingspace.init.BlockInit;
import com.rea.creatingspace.init.ItemInit;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AllCreativeModeTabs {

    public static final CreativeModeTab MACHINE_TAB = new CreativeModeTab("machine_tab") {
        @Override
        public ItemStack makeIcon() {
            return BlockItem.byBlock(BlockInit.EXPLOSIVE_STARTER.get()).getDefaultInstance();
        }
    };
    public static final CreativeModeTab COMPONENT_TAB = new CreativeModeTab("component_tab") {
        @Override
        public ItemStack makeIcon() {
            return ItemInit.INJECTOR.get().getDefaultInstance();
        }
    };
    public static final CreativeModeTab MINERALS_TAB = new CreativeModeTab("minerals_tab") {
        @Override
        public ItemStack makeIcon() {
            return BlockItem.byBlock(BlockInit.NICKEL_ORE.get()).getDefaultInstance();
        }
    };
    public static void init() {
    }
}
