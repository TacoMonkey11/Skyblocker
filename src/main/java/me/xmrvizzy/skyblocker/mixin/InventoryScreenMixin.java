package me.xmrvizzy.skyblocker.mixin;

import me.xmrvizzy.skyblocker.config.SkyblockerConfig;
import me.xmrvizzy.skyblocker.skyblock.itemlist.ItemListWidget;
import me.xmrvizzy.skyblocker.utils.SidebarWrapper;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin {
    @Redirect(
            method = "<init>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/client/gui/screen/recipebook/RecipeBookWidget"
            )
    )
    RecipeBookWidget skyblocker$constructor() {
        if (SidebarWrapper.onSkyblock() && SkyblockerConfig.get().general.itemList.enableItemList)
            return new ItemListWidget();
        else
            return new RecipeBookWidget();
    }
}
