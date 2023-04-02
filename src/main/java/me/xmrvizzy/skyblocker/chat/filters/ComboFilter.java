package me.xmrvizzy.skyblocker.chat.filters;

import me.xmrvizzy.skyblocker.chat.ChatFilterResult;
import me.xmrvizzy.skyblocker.config.SkyblockerConfigOld;

public class ComboFilter extends SimpleChatFilter {
    public ComboFilter() {
        super("^(\\+\\d+ Kill Combo \\+\\d+(% ✯ Magic Find| coins per kill|% Combat Exp)" +
                "|Your Kill Combo has expired! You reached a \\d+ Kill Combo!)$");
    }

    @Override
    public ChatFilterResult state() {
        return SkyblockerConfigOld.get().messages.hideCombo;
    }
}
