package me.xmrvizzy.skyblocker.skyblock.dwarven;

import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.Insets;
import io.wispforest.owo.ui.core.Positioning;
import io.wispforest.owo.ui.core.Sizing;
import io.wispforest.owo.ui.core.Surface;
import io.wispforest.owo.ui.hud.Hud;
import me.xmrvizzy.skyblocker.SkyblockerMod;
import me.xmrvizzy.skyblocker.config.SkyblockerGuiConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DwarvenHud {


    public static final MinecraftClient client = MinecraftClient.getInstance();
    public static final Identifier HUD_ID = new Identifier(SkyblockerMod.NAMESPACE, "dwarven_hud");
    public static List<Commission> commissionList = new ArrayList<>();
    public static Text commissionListText = Text.empty();


    public static final List<Pattern> COMMISSIONS = Stream.of(
            "(?:Titanium|Mithril|Hard Stone) Miner",
            "(?:Ice Walker|Goblin|Goblin Raid|Automaton|Sludge|Team Treasurite Member|Yog|Boss Corleone|Thyst) Slayer",
            "(?:Lava Springs|Cliffside Veins|Rampart's Quarry|Upper Mines|Royal Mines) Mithril",
            "(?:Lava Springs|Cliffside Veins|Rampart's Quarry|Upper Mines|Royal Mines) Titanium",
            "Goblin Raid",
            "(?:Powder Ghast|Star Sentry) Puncher",
            "(?<!Lucky )Raffle",
            "Lucky Raffle",
            "2x Mithril Powder Collector",
            "(?:Ruby|Amber|Sapphire|Jade|Amethyst|Topaz) Gemstone Collector",
            "(?:Amber|Sapphire|Jade|Amethyst|Topaz) Crystal Hunter",
            "Chest Looter"
            ).map(s -> Pattern.compile("^.*(" + s + "): (\\d+\\.?\\d*%|DONE)"))
            .collect(Collectors.toList());

    public static void init() {
        Hud.add(new Identifier(SkyblockerMod.NAMESPACE, "dwarven_hud"), () -> Containers.verticalFlow(Sizing.content(), Sizing.content()).positioning(Positioning.absolute(SkyblockerMod.getInstance().config.dwarvenMines.dwarvenHud.x(), SkyblockerMod.getInstance().config.dwarvenMines.dwarvenHud.y())));
    }
    public static void update() {
        commissionList = new ArrayList<>();
        MutableText commissionText = Text.empty();
        FlowLayout hud = (FlowLayout) Hud.getComponent(HUD_ID);
        if (client.player == null || !SkyblockerMod.getInstance().config.dwarvenMines.dwarvenHud.enabled()) return;

        client.getNetworkHandler().getPlayerList().forEach(playerListEntry -> {
            if (playerListEntry.getDisplayName() != null) {
                for (Pattern pattern : COMMISSIONS) {
                    Matcher matcher = pattern.matcher(playerListEntry.getDisplayName().getString());
                    if (matcher.find()) {
                        commissionList.add(new Commission(matcher.group(1), matcher.group(2)));
                    }

                }
            }
        });

        for (Commission commission : commissionList) {
            commissionText.append(styleCommissionText(commission));
            if (commissionList.size() != commissionList.indexOf(commission) + 1)
                commissionText.append("\n");
        }
        if (commissionText.equals(Text.empty()) || client.options.debugEnabled || client.currentScreen instanceof SkyblockerGuiConfigScreen) {
            hud.clearChildren().surface(Surface.BLANK);
            return;
        }

        hud.positioning(Positioning.absolute(SkyblockerMod.getInstance().config.dwarvenMines.dwarvenHud.x(), SkyblockerMod.getInstance().config.dwarvenMines.dwarvenHud.y())).padding(Insets.of(10));

        if (SkyblockerMod.getInstance().config.dwarvenMines.dwarvenHud.enableBackground())
            hud.surface(Surface.PANEL);
        else
            hud.surface(Surface.BLANK);

        hud.clearChildren();
        hud.child(Components.label(commissionText).id("commission_hud_text"));
    }

    public static LabelComponent getExampleComponent() {
        Commission testCommission1 = new Commission("Test Commission 1", "0%");
        Commission testCommission2 = new Commission("Test Commission 2", "DONE");
        Commission testCommission3 = new Commission("Test Commission 3", "50%");

        return (LabelComponent) Components.label(styleCommissionText(testCommission1).append("\n").append(styleCommissionText(testCommission2)).append("\n").append(styleCommissionText(testCommission3))).id("commission_hud_text").margins(Insets.of(10));
    }

    public static MutableText styleCommissionText(Commission commission) {
        return Text.literal(commission.commission).styled(style -> style.withColor(Formatting.AQUA)).append(Text.literal(": " + commission.progression).styled(style -> style.withColor(Formatting.GREEN)));
    }

    public static class Commission{
        final String commission;
        final String progression;

        public Commission(String commission, String progression){
            this.commission = commission;
            this.progression = progression;
        }
    }
}
