package me.xmrvizzy.skyblocker;

import io.wispforest.owo.config.ui.ConfigScreen;
import me.xmrvizzy.skyblocker.chat.ChatMessageListener;
import me.xmrvizzy.skyblocker.config.SkyblockerConfigOld;
import me.xmrvizzy.skyblocker.config.SkyblockerConfigScreen;
import me.xmrvizzy.skyblocker.discord.DiscordRPCManager;
import me.xmrvizzy.skyblocker.skyblock.HotbarSlotLock;
import me.xmrvizzy.skyblocker.skyblock.api.StatsCommand;
import me.xmrvizzy.skyblocker.skyblock.dwarven.DwarvenHud;
import me.xmrvizzy.skyblocker.skyblock.item.PriceInfoTooltip;
import me.xmrvizzy.skyblocker.skyblock.item.WikiLookup;
import me.xmrvizzy.skyblocker.skyblock.itemlist.ItemRegistry;
import me.xmrvizzy.skyblocker.utils.UpdateChecker;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;

public class SkyblockerInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HotbarSlotLock.init();
        SkyblockerConfigOld.init();
        PriceInfoTooltip.init();
        WikiLookup.init();
        ItemRegistry.init();
        StatsCommand.init();
        DwarvenHud.init();
        ChatMessageListener.init();
        UpdateChecker.init();
        DiscordRPCManager.init();
        ConfigScreen.registerProvider(SkyblockerMod.NAMESPACE, screen -> new SkyblockerConfigScreen(new Identifier(SkyblockerMod.NAMESPACE, "config"), SkyblockerMod.getInstance().config, screen));
    }
}
