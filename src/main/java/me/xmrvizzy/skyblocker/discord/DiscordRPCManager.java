package me.xmrvizzy.skyblocker.discord;


import me.shedaniel.autoconfig.AutoConfig;
import me.xmrvizzy.skyblocker.config.SkyblockerConfigOld;
import me.xmrvizzy.skyblocker.utils.SkyblockEvents;
import me.xmrvizzy.skyblocker.utils.Utils;
import meteordevelopment.discordipc.DiscordIPC;
import meteordevelopment.discordipc.RichPresence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.time.Instant;

public class DiscordRPCManager {
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###.##");
    public static final Logger LOGGER = LoggerFactory.getLogger(DiscordRPCManager.class.getName());
    public static long startTimeStamp;
    public static int cycleCount;

    public static void init(){
        SkyblockEvents.LEAVE.register(DiscordIPC::stop);
    }

    public void update(){
        if (SkyblockerConfigOld.get().richPresence.customMessage != null ) {
            if (SkyblockerConfigOld.get().richPresence.customMessage.isBlank()) {
                SkyblockerConfigOld.get().richPresence.customMessage = "All on Fabric!";
                AutoConfig.getConfigHolder(SkyblockerConfigOld.class).save();
            }
        }
        if (!SkyblockerConfigOld.get().richPresence.enableRichPresence || !Utils.isOnSkyblock){
            if (DiscordIPC.isConnected()) DiscordIPC.stop();
        }
        if (SkyblockerConfigOld.get().richPresence.enableRichPresence && Utils.isOnSkyblock && !DiscordIPC.isConnected()){
            if (!DiscordIPC.start(934607927837356052L, () -> {
                LOGGER.info("Started up rich presence");
                startTimeStamp = Instant.now().getEpochSecond();
            })){
                LOGGER.info("An error occurred while attempting to connect to discord");
                return;
            }
        }
        if (SkyblockerConfigOld.get().richPresence.cycleMode)
            cycleCount = (cycleCount + 1) % 3;
        buildPresence();
    }

    public void buildPresence(){
        RichPresence presence = new RichPresence();
        presence.setLargeImage("skyblocker-default", null);
        presence.setStart(startTimeStamp);
        presence.setDetails(SkyblockerConfigOld.get().richPresence.customMessage);
        presence.setState(getInfo());
        DiscordIPC.setActivity(presence);
    }

    public String getInfo(){
        String info = null;
        if (!SkyblockerConfigOld.get().richPresence.cycleMode){
            switch (SkyblockerConfigOld.get().richPresence.info){
                case BITS -> info = "Bits: " + DECIMAL_FORMAT.format(Utils.getBits());
                case PURSE -> info = "Purse: " + DECIMAL_FORMAT.format(Utils.getPurse());
                case LOCATION -> info = "⏣ " + Utils.getLocation();
            }
        } else if (SkyblockerConfigOld.get().richPresence.cycleMode){
            switch (cycleCount){
                case 0 -> info = "Bits: " + DECIMAL_FORMAT.format(Utils.getBits());
                case 1 -> info = "Purse: " + DECIMAL_FORMAT.format(Utils.getPurse());
                case 2 -> info = "⏣ " + Utils.getLocation();
            }
        }
        return info;
    }
}
