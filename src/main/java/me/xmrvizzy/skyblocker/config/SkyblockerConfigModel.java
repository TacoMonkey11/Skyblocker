package me.xmrvizzy.skyblocker.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.ExcludeFromScreen;
import io.wispforest.owo.config.annotation.Nest;
import io.wispforest.owo.config.annotation.SectionHeader;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.xmrvizzy.skyblocker.SkyblockerMod;
import me.xmrvizzy.skyblocker.chat.ChatFilterResult;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Config(wrapperName = "SkyblockerConfig", name = SkyblockerMod.NAMESPACE)
public class SkyblockerConfigModel {

    //
    // GENERAL CONFIGURATION
    //

    @SectionHeader("general")
    public boolean enableUpdateNotification = true;
    public boolean backpackPreviewWithoutShift = false;
    @ExcludeFromScreen
    public String apiKey;
    @Nest
    public Bars bars = new Bars();
    @Nest
    public ItemList itemList = new ItemList();
    @Nest
    public ItemTooltip itemTooltip = new ItemTooltip();
    @Nest
    public Hitbox hitbox = new Hitbox();
    @ExcludeFromScreen
    public List<Integer> lockedSlots = new ArrayList<>();

    //
    // LOCATION CONFIGURATION
    //

    @SectionHeader("locations")
    @Nest
    public Dungeons dungeons = new Dungeons();

    @Nest
    public DwarvenMines dwarvenMines = new DwarvenMines();

    //
    // FILTER CONFIGURATION
    //

    @SectionHeader("messages")
    public ChatFilterResult hideAbility = ChatFilterResult.PASS;
    public ChatFilterResult hideHeal = ChatFilterResult.PASS;
    public ChatFilterResult hideAOTE = ChatFilterResult.PASS;
    public ChatFilterResult hideImplosion = ChatFilterResult.PASS;
    public ChatFilterResult hideMoltenWave = ChatFilterResult.PASS;
    public ChatFilterResult hideAds = ChatFilterResult.PASS;
    public ChatFilterResult hideTeleportPad = ChatFilterResult.PASS;
    public ChatFilterResult hideCombo = ChatFilterResult.PASS;
    public ChatFilterResult hideAutopet = ChatFilterResult.PASS;
    public boolean hideMana = false;

    //
    // DISCORD RPC CONFIGURATION
    //

    @SectionHeader("richPresence")
    public boolean enableRichPresence = false;
    public Info info = Info.LOCATION;
    public boolean cycleMode = false;
    public String customMessage = "";

    //
    // QUICK NAVAGATION CONFIGURATION
    //

    @SectionHeader("quickNav")
    public boolean enableQuickNav = true;

    @Nest
    public QuickNavItem button1 = new QuickNavItem(true, new ItemData("diamond_sword"), "Your Skills", "/skills");
    @Nest
    public QuickNavItem button2 = new QuickNavItem(true, new ItemData("painting"), "Collection", "/collection");
    @Nest
    public QuickNavItem button3 = new QuickNavItem(false, new ItemData("air"), "", "");
    @Nest
    public QuickNavItem button4 = new QuickNavItem(true, new ItemData("bone"), "Pets", "/pets");
    @Nest
    public QuickNavItem button5 = new QuickNavItem(true, new ItemData("leather_chestplate", 1, "tag:{display:{color:8991416}}"), "Wardrobe", "/wardrobe");
    @Nest
    public QuickNavItem button6 = new QuickNavItem(true, new ItemData("ender_chest"),  "Storage", "/storage");
    @Nest
    public QuickNavItem button7 = new QuickNavItem(true, new ItemData("player_head", 1, "tag:{SkullOwner:{Id:[I;-300151517,-631415889,-1193921967,-1821784279],Properties:{textures:[{Value:\"e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDdjYzY2ODc0MjNkMDU3MGQ1NTZhYzUzZTA2NzZjYjU2M2JiZGQ5NzE3Y2Q4MjY5YmRlYmVkNmY2ZDRlN2JmOCJ9fX0=\"}]}}}"), "none", "/hub");
    @Nest
    public QuickNavItem button8 = new QuickNavItem(true, new ItemData("player_head", 1, "tag:{SkullOwner:{Id:[I;1605800870,415127827,-1236127084,15358548],Properties:{textures:[{Value:\"e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg5MWQ1YjI3M2ZmMGJjNTBjOTYwYjJjZDg2ZWVmMWM0MGExYjk0MDMyYWU3MWU3NTQ3NWE1NjhhODI1NzQyMSJ9fX0=\"}]}}}"), "none", "/warp dungeon_hub");
    @Nest
    public QuickNavItem button9 = new QuickNavItem(false, new ItemData("air"), "", "");
    @Nest
    public QuickNavItem button10 = new QuickNavItem(true, new ItemData("enchanting_table"), "Enchant", "/etable");
    @Nest
    public QuickNavItem button11 = new QuickNavItem(true, new ItemData("anvil"), "Anvil", "/anvil");
    @Nest
    public QuickNavItem button12 = new QuickNavItem(true, new ItemData("crafting_table"), "Craft Item", "/craft");

    //
    // ENUMS AND CLASSES
    //

    public static class QuickNavItem {
        public QuickNavItem(Boolean render, ItemData itemData, String uiTitle, String clickEvent) {
            this.render = render;
            this.item = itemData;
            this.clickEvent = clickEvent;
            this.uiTitle = uiTitle;
        }

        public Boolean render;

        @Nest
        public ItemData item;

        public String uiTitle;
        public String clickEvent;
    }

    public static class ItemData {
        public ItemData(String itemName, int count, String nbt) {
            this.itemName = itemName;
            this.count = count;
            this.nbt = nbt;
        }

        public ItemData(String itemName) {
            this.itemName = itemName;
            this.count = 1;
            this.nbt = "";
        }

        public String itemName;
        public int count;
        public String nbt;
    }

    public static class Bars {
        public boolean enableBars = true;
        @Nest
        public BarPositions barpositions = new BarPositions();
    }

    public static class BarPositions {
        public BarPosition healthBarPosition = BarPosition.LAYER1;
        public BarPosition manaBarPosition = BarPosition.LAYER1;
        public BarPosition defenceBarPosition = BarPosition.LAYER1;
        public BarPosition experienceBarPosition = BarPosition.LAYER1;

    }

    public enum BarPosition {
        LAYER1,
        LAYER2,
        RIGHT,
        NONE;

        public int toInt() {
            return switch (this) {
                case LAYER1 -> 0;
                case LAYER2 -> 1;
                case RIGHT -> 2;
                case NONE -> -1;
            };
        }
    }

    public static class Hitbox {
        public boolean oldFarmlandHitbox = true;
        public boolean oldLeverHitbox = false;
    }

    public static class ItemList {
        public boolean enableItemList = true;
    }

    public enum Average {
        ONE_DAY,
        THREE_DAY,
        BOTH
    }

    public static class ItemTooltip {
        public boolean enableNPCPrice = true;
        public boolean enableAvgBIN = true;
        public Average avg = Average.THREE_DAY;
        public boolean enableLowestBIN = true;
        public boolean enableBazaarPrice = true;
        public boolean enableMuseumDate = true;
    }

    public static class Dungeons {
        @ConfigEntry.Gui.Tooltip()
        public boolean croesusHelper = true;
        public boolean enableMap = true;
        public boolean solveThreeWeirdos = true;
        public boolean blazesolver = true;
        public boolean solveTrivia = true;

        @Nest
        public Terminals terminals = new Terminals();
    }

    public static class Terminals {
        public boolean solveColor = true;
        public boolean solveOrder = true;
        public boolean solveStartsWith = true;
    }

    public static class DwarvenMines {
        public boolean enableDrillFuel = true;
        public boolean solveFetchur = true;
        public boolean solvePuzzler = true;
        @Nest
        public DwarvenHud dwarvenHud = new DwarvenHud();
    }

    public static class DwarvenHud {
        public boolean enabled = true;
        public Backgrounds background = Backgrounds.TRANSLUCENT;
        public int x = 10;
        public int y = 10;
    }

    public enum Info {
        PURSE,
        BITS,
        LOCATION
    }

    public enum Backgrounds {
        PANEL,
        DARK_PANEL,
        TRANSLUCENT,
        NONE
    }
}
