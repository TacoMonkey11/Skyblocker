package me.xmrvizzy.skyblocker.skyblock.quicknav;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.xmrvizzy.skyblocker.SkyblockerMod;
import me.xmrvizzy.skyblocker.config.SkyblockerConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.StringNbtReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuickNav {
    private static final String skyblockHubIconNbt = "{id:\"minecraft:player_head\",Count:1,tag:{SkullOwner:{Id:[I;-300151517,-631415889,-1193921967,-1821784279],Properties:{textures:[{Value:\"e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDdjYzY2ODc0MjNkMDU3MGQ1NTZhYzUzZTA2NzZjYjU2M2JiZGQ5NzE3Y2Q4MjY5YmRlYmVkNmY2ZDRlN2JmOCJ9fX0=\"}]}}}}";
    private static final String dungeonHubIconNbt = "{id:\"minecraft:player_head\",Count:1,tag:{SkullOwner:{Id:[I;1605800870,415127827,-1236127084,15358548],Properties:{textures:[{Value:\"e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg5MWQ1YjI3M2ZmMGJjNTBjOTYwYjJjZDg2ZWVmMWM0MGExYjk0MDMyYWU3MWU3NTQ3NWE1NjhhODI1NzQyMSJ9fX0=\"}]}}}}";
    public static List<QuickNavButton> init(String screenTitle) {
        List<QuickNavButton> buttons = new ArrayList<>();
        SkyblockerConfig data = SkyblockerMod.getInstance().config;
        try {
            if (data.button1.render()) buttons.add(parseButton(data.button1.item.itemName(), data.button1.uiTitle(), data.button1.clickEvent(), data.button1.item.nbt(), screenTitle, 0));
            if (data.button2.render()) buttons.add(parseButton(data.button2.item.itemName(), data.button2.uiTitle(), data.button2.clickEvent(), data.button2.item.nbt(), screenTitle, 1));
            if (data.button3.render()) buttons.add(parseButton(data.button3.item.itemName(), data.button3.uiTitle(), data.button3.clickEvent(), data.button3.item.nbt(), screenTitle, 2));
            if (data.button4.render()) buttons.add(parseButton(data.button4.item.itemName(), data.button4.uiTitle(), data.button4.clickEvent(), data.button4.item.nbt(), screenTitle, 3));
            if (data.button5.render()) buttons.add(parseButton(data.button5.item.itemName(), data.button5.uiTitle(), data.button5.clickEvent(), data.button5.item.nbt(), screenTitle, 4));
            if (data.button6.render()) buttons.add(parseButton(data.button6.item.itemName(), data.button6.uiTitle(), data.button6.clickEvent(), data.button6.item.nbt(), screenTitle, 5));
            if (data.button7.render()) buttons.add(parseButton(data.button7.item.itemName(), data.button7.uiTitle(), data.button7.clickEvent(), data.button7.item.nbt(), screenTitle, 6));
            if (data.button8.render()) buttons.add(parseButton(data.button8.item.itemName(), data.button8.uiTitle(), data.button8.clickEvent(), data.button8.item.nbt(), screenTitle, 7));
            if (data.button9.render()) buttons.add(parseButton(data.button9.item.itemName(), data.button9.uiTitle(), data.button9.clickEvent(), data.button9.item.nbt(), screenTitle, 8));
            if (data.button10.render()) buttons.add(parseButton(data.button10.item.itemName(), data.button10.uiTitle(), data.button10.clickEvent(), data.button10.item.nbt(), screenTitle, 9));
            if (data.button11.render()) buttons.add(parseButton(data.button11.item.itemName(), data.button11.uiTitle(), data.button11.clickEvent(), data.button11.item.nbt(), screenTitle, 10));
            if (data.button12.render()) buttons.add(parseButton(data.button12.item.itemName(), data.button12.uiTitle(), data.button12.clickEvent(), data.button12.item.nbt(), screenTitle, 11));
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return buttons;
    }

    private static QuickNavButton parseButton(String itemName, String uiTitle, String clickEvent, String nbt, String screenTitle, int id) throws CommandSyntaxException {
        String nbtString = "{id:\"minecraft:" + itemName.toLowerCase(Locale.ROOT) + "\",Count:1";
        if (nbt.length() > 2) nbtString += "," + nbt;
        nbtString += "}";
        return new QuickNavButton(id,
                screenTitle.contains(uiTitle),
                clickEvent,
                ItemStack.fromNbt(StringNbtReader.parse(nbtString))
        );
    }
}
