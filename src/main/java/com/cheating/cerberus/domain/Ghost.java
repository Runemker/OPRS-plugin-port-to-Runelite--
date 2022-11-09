package com.cheating.cerberus.domain;

import com.google.common.collect.ImmutableMap;
import java.awt.Color;
import java.util.Map;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.Skill;

@Getter
@RequiredArgsConstructor
public enum Ghost
{
    RANGE(NpcID.SUMMONED_SOUL, Skill.RANGED, Color.GREEN),
    MAGE(NpcID.SUMMONED_SOUL_5868, Skill.MAGIC, Color.BLUE),
    MELEE(NpcID.SUMMONED_SOUL_5869, Skill.ATTACK, Color.RED);

    private static final Map<Integer, Ghost> MAP;

    static
    {
        final ImmutableMap.Builder<Integer, Ghost> builder = new ImmutableMap.Builder<>();

        for (final Ghost ghost : values())
        {
            builder.put(ghost.getNpcId(), ghost);
        }

        MAP = builder.build();
    }

    private final int npcId;
    private final Skill type;
    private final Color color;

    /**
     * Try to identify if NPC is ghost
     *
     * @param npc npc
     * @return optional ghost
     */
    @Nullable
    public static Ghost fromNPC(final NPC npc)
    {
        return MAP.get(npc.getId());
    }
}
