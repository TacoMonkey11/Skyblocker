package me.xmrvizzy.skyblocker.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SidebarWrapper {
    private static boolean onSkyblock = false;
    private static List<String> sidebar;

    public static void update() {
        sidebar = getSidebar();
        System.out.println(onSkyblock());
    }

    public static List<String> getSidebar() {
        try {
            ClientPlayerEntity client = MinecraftClient.getInstance().player;
            if (client == null) return Collections.emptyList();
            Scoreboard scoreboard = MinecraftClient.getInstance().player.getScoreboard();
            ScoreboardObjective objective = scoreboard.getObjectiveForSlot(1);
            List<String> lines = new ArrayList<>();
            for (ScoreboardPlayerScore score : scoreboard.getAllPlayerScores(objective)) {
                Team team = scoreboard.getPlayerTeam(score.getPlayerName());
                if (team != null) {
                    String line = team.getPrefix().getString() + team.getSuffix().getString();
                    if (line.trim().length() > 0) {
                        String formatted = Formatting.strip(line);
                        lines.add(formatted);
                    }
                }
            }

            if (objective != null) {
                lines.add(objective.getDisplayName().getString());
                Collections.reverse(lines);
            }
            return lines;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static boolean onSkyblock() {
        if (sidebar == null || sidebar.size() < 1) return false;

        if (sidebar.get(0).contains("SKYBLOCK") || sidebar.get(0).contains("SKIBLOCK")) {
            if (!onSkyblock) {
                onSkyblock = true;
                SkyblockEvents.JOIN.invoker().onSkyblockJoin();
            }
            return true;
        } else {
            if (onSkyblock) {
                onSkyblock = false;
                SkyblockEvents.LEAVE.invoker().onSkyblockLeave();
            }
            return false;
        }
    }

    public static boolean inDungeons() {
        if (sidebar == null || sidebar.size() < 1) return false;
        return onSkyblock && sidebar.toString().contains("The Catacombs");
    }

    public static String getLocation() {
        if (sidebar == null || sidebar.size() < 1) return null;
        Optional<String> location = sidebar.stream().filter(s -> s.contains("⏣")).findFirst();
        return location.map(s -> s.replace("⏣", "").strip()).orElse(null);
    }

    public static double getPurse() {
        if (sidebar == null || sidebar.size() < 1) return 0;
        Optional<String> purse = sidebar.stream().filter(s -> s.contains("Piggy:") || s.contains("Purse:")).findFirst();
        return purse.map(s -> Double.parseDouble(s.replaceAll("[^0-9.]", "").strip())).orElse(0.0);
    }

    public static int getBits() {
        if (sidebar == null || sidebar.size() < 1) return 0;
        Optional<String> bits = sidebar.stream().filter(s -> s.contains("Bits:")).findFirst();
        return bits.map(s -> Integer.parseInt(s.replaceAll("[^0-9]", "").strip())).orElse(0);
    }
}
