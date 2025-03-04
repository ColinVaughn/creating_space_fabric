package com.rae.creatingspace.init.ingameobject;

import com.rae.creatingspace.CreatingSpace;
import com.rae.creatingspace.init.CreativeModeTabsInit;
import com.rae.creatingspace.init.TagsInit;
import com.rae.creatingspace.server.armor.PureOxygenBacktank;
import com.rae.creatingspace.server.items.CryoHandTank;
import com.simibubi.create.Create;
import com.simibubi.create.content.equipment.armor.AllArmorMaterials;
import com.simibubi.create.foundation.item.CombustibleItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import static com.simibubi.create.AllTags.forgeItemTag;
public class ItemInit {

    public static final ItemEntry<CryoHandTank> HAND_TANK = CreatingSpace.REGISTRATE.item(
            "hand_tank", CryoHandTank::new)
            .properties(p -> p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();
    public static final ItemEntry<PureOxygenBacktank.O2BacktankBlockItem> COPPER_BACKTANK_PLACEABLE =
            CreatingSpace.REGISTRATE
            .item("copper_oxygen_backtank_placeable",
                    p -> new PureOxygenBacktank.O2BacktankBlockItem(BlockInit.COPPER_OXYGEN_BACKTANK.get(), ItemInit.COPPER_OXYGEN_BACKTANK::get, p))
            .register();
    public static final ItemEntry<PureOxygenBacktank> COPPER_OXYGEN_BACKTANK =
            CreatingSpace.REGISTRATE
                    .item("copper_oxygen_backtank",
                            p -> new PureOxygenBacktank(AllArmorMaterials.COPPER, p, Create.asResource("copper_diving"),
                                    COPPER_BACKTANK_PLACEABLE))
                    .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
                    .tag(TagsInit.CustomItemTags.OXYGEN_SOURCES.tag)
                    .tag(forgeItemTag("armors/chestplates"))
                    .register();


    //component
    public static final ItemEntry<Item> INJECTOR = CreatingSpace.REGISTRATE.item(
            "injector",Item::new)
            .properties(p -> p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();
    public static final ItemEntry<Item> REINFORCED_INJECTOR = CreatingSpace.REGISTRATE.item(
                    "reinforced_injector",Item::new)
            .properties(p -> p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();

    public static final ItemEntry<Item> INCOMPLETE_INJECTOR = CreatingSpace.REGISTRATE.item(
            "incomplete_injector",Item::new)
            .register();

    public static final ItemEntry<Item> INJECTOR_GRID = CreatingSpace.REGISTRATE.item(
            "injector_grid",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();
    public static final ItemEntry<Item> REINFORCED_INJECTOR_GRID = CreatingSpace.REGISTRATE.item(
                    "reinforced_injector_grid",Item::new)
            .properties(p -> p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();

    public static final ItemEntry<Item> INCOMPLETE_INJECTOR_GRID = CreatingSpace.REGISTRATE.item(
            "incomplete_injector_grid",Item::new)
            .register();

    public static final ItemEntry<Item> COPPER_COIL = CreatingSpace.REGISTRATE.item(
            "copper_coil",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();


    public static final ItemEntry<Item> BASIC_CATALYST = CreatingSpace.REGISTRATE.item(
            "basic_catalyst",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();

    public static final ItemEntry<CombustibleItem> STARTER_CHARGE = CreatingSpace.REGISTRATE.item(
        "starter_charge", CombustibleItem::new)
            .onRegister(i -> i.setBurnTime(500))
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();

    public static final ItemEntry<CombustibleItem> COAL_DUST = CreatingSpace.REGISTRATE.item(
            "coal_dust", CombustibleItem::new)
            .onRegister(i -> i.setBurnTime(500))
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();
    public static final ItemEntry<Item> STURDY_PROPELLER = CreatingSpace.REGISTRATE.item(
            "sturdy_propeller",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();


    public static final ItemEntry<Item> COMBUSTION_CHAMBER = CreatingSpace.REGISTRATE.item(
            "combustion_chamber",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();


    public static final ItemEntry<Item> BELL_NOZZLE = CreatingSpace.REGISTRATE.item(
            "bell_nozzle",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();


    //food
    //exemple -> arn't registered...
    public static final ItemEntry<Item> SPACE_FOOD = CreatingSpace.REGISTRATE.item(
            "space_food",Item::new)
            .properties(p->p.food(new FoodProperties.Builder()
                        .alwaysEat()
                        .nutrition(4)
                        .saturationMod(1f)
                        .fast()
                        .effect(()->
                                    new MobEffectInstance(MobEffects.NIGHT_VISION,
                                            72000
                                            ,1)
                            , 1.0f)
                        .build())
            )
            .register();

    //minerals

    //nickel
    public static final ItemEntry<Item> RAW_NICKEL = CreatingSpace.REGISTRATE.item(
            "raw_nickel",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();


    public static final ItemEntry<Item> CRUSHED_NICKEL_ORE = CreatingSpace.REGISTRATE.item(
            "crushed_nickel_ore",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();

    public static final ItemEntry<Item> NICKEL_DUST = CreatingSpace.REGISTRATE.item(
                    "nickel_dust",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();


    public static final ItemEntry<Item> NICKEL_INGOT = CreatingSpace.REGISTRATE.item(
            "nickel_ingot",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();


    public static final ItemEntry<Item> NICKEL_NUGGET = CreatingSpace.REGISTRATE.item(
            "nickel_nugget",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();



    public static final ItemEntry<Item> NICKEL_SHEET = CreatingSpace.REGISTRATE.item(
            "nickel_sheet",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();

    //aluminium

    public static final ItemEntry<Item> RAW_ALUMINUM = CreatingSpace.REGISTRATE.item(
                    "raw_aluminum",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();


    public static final ItemEntry<Item> CRUSHED_ALUMINUM_ORE = CreatingSpace.REGISTRATE.item(
                    "crushed_aluminum_ore",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();


    public static final ItemEntry<Item> ALUMINUM_INGOT = CreatingSpace.REGISTRATE.item(
                    "aluminum_ingot",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();


    public static final ItemEntry<Item> ALUMINUM_NUGGET = CreatingSpace.REGISTRATE.item(
                    "aluminum_nugget",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();



    public static final ItemEntry<Item> ALUMINUM_SHEET = CreatingSpace.REGISTRATE.item(
                    "aluminum_sheet",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();

    //cobalt

    public static final ItemEntry<Item> RAW_COBALT = CreatingSpace.REGISTRATE.item(
                    "raw_cobalt",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();


    public static final ItemEntry<Item> CRUSHED_COBALT_ORE = CreatingSpace.REGISTRATE.item(
                    "crushed_cobalt_ore",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();


    public static final ItemEntry<Item> COBALT_INGOT = CreatingSpace.REGISTRATE.item(
                    "cobalt_ingot",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();


    public static final ItemEntry<Item> COBALT_NUGGET = CreatingSpace.REGISTRATE.item(
                    "cobalt_nugget",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();



    public static final ItemEntry<Item> COBALT_SHEET = CreatingSpace.REGISTRATE.item(
                    "cobalt_sheet",Item::new)
            .properties(p->p.tab(CreativeModeTabsInit.COMPONENT_TAB))
            .register();


    //sub classes
    

    public static void register() {}
}
