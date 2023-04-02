package me.xmrvizzy.skyblocker.chat.filters;

import me.xmrvizzy.skyblocker.chat.ChatFilterResult;
import me.xmrvizzy.skyblocker.config.SkyblockerConfigOld;

public class MoltenWaveFilter extends SimpleChatFilter {
    public MoltenWaveFilter() {
        super("^Your Molten Wave hit " + NUMBER + " enemy(?:y|ies) for " + NUMBER + " damage\\.$");
    }

    @Override
    public ChatFilterResult state() {
        return SkyblockerConfigOld.get().messages.hideMoltenWave;
    }
}
