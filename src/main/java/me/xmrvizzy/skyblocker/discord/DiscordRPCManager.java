package me.xmrvizzy.skyblocker.discord;


import me.shedaniel.autoconfig.AutoConfig;
import me.xmrvizzy.skyblocker.config.SkyblockerConfig;
import me.xmrvizzy.skyblocker.utils.SidebarWrapper;
import me.xmrvizzy.skyblocker.utils.SkyblockEvents;
import meteordevelopment.discordipc.DiscordIPC;
import meteordevelopment.discordipc.RichPresence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

public class DiscordRPCManager {
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###.##");
    public static final Logger LOGGER = LoggerFactory.getLogger("Skyblocker Discord RPC");
    public static long startTimeStamp;
    public static int cycleCount;

    public static void init(){
        SkyblockEvents.LEAVE.register(DiscordIPC::stop);
        SkyblockEvents.JOIN.register(() -> {
            startTimeStamp = System.currentTimeMillis();
            if (!SkyblockerConfig.get().richPresence.enableRichPresence) return;
            if (DiscordIPC.start(934607927837356052L, null)) {
                LOGGER.info("Discord RPC started");
            } else {
                LOGGER.error("Discord RPC failed to start");
            }
        });
    }

    public static void update(){
        if (!SidebarWrapper.onSkyblock()) return;
        // If the custom message is empty, discord will keep the last message, this is can serve as a default if the user doesn't want a custom message
        if (SkyblockerConfig.get().richPresence.customMessage.isEmpty()) {
            SkyblockerConfig.get().richPresence.customMessage = "Playing Skyblock";
            AutoConfig.getConfigHolder(SkyblockerConfig.class).save();
        }
        if ((!SidebarWrapper.onSkyblock() || !SkyblockerConfig.get().richPresence.enableRichPresence) && DiscordIPC.isConnected()){
            DiscordIPC.stop();
            LOGGER.info("Discord RPC stopped");
            return;
        }
        if (SkyblockerConfig.get().richPresence.cycleMode) cycleCount = (cycleCount + 1) % 3;
        DiscordIPC.setActivity(buildPresence());
    }

    public static RichPresence buildPresence(){
        RichPresence presence = new RichPresence();
        presence.setLargeImage("skyblocker-default", null);
        presence.setStart(startTimeStamp);
        presence.setDetails(SkyblockerConfig.get().richPresence.customMessage);
        presence.setState(getInfo());
        return presence;
    }

    public static String getInfo(){
        String info = null;
        if (!SkyblockerConfig.get().richPresence.cycleMode){
            switch (SkyblockerConfig.get().richPresence.info){
                case BITS -> info = "Bits: " + DECIMAL_FORMAT.format(SidebarWrapper.getBits());
                case PURSE -> info = "Purse: " + DECIMAL_FORMAT.format(SidebarWrapper.getPurse());
                case LOCATION -> info = "⏣ " + SidebarWrapper.getLocation();
            }
        } else if (SkyblockerConfig.get().richPresence.cycleMode){
            switch (cycleCount){
                case 0 -> info = "Bits: " + DECIMAL_FORMAT.format(SidebarWrapper.getBits());
                case 1 -> info = "Purse: " + DECIMAL_FORMAT.format(SidebarWrapper.getPurse());
                case 2 -> info = "⏣ " + SidebarWrapper.getLocation();
            }
        }
        return info;
    }
}
