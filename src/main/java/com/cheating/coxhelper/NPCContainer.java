package com.cheating.coxhelper;

import com.cheating.Util.AttackStyle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Actor;
import net.runelite.api.NPC;
import net.runelite.api.NPCComposition;

@Getter(AccessLevel.PACKAGE)
public class NPCContainer
{

    private final NPC npc;
    private final int npcIndex;
    private final String npcName;
    private int npcSize;
    @Setter(AccessLevel.PACKAGE)
    private int ticksUntilAttack;
    @Setter(AccessLevel.PACKAGE)
    private int intermissionPeriod;
    @Setter(AccessLevel.PACKAGE)
    private int npcSpeed;
    @Setter(AccessLevel.PACKAGE)
    private Actor npcInteracting;
    @Setter(AccessLevel.PACKAGE)
    private AttackStyle attackStyle;


    NPCContainer(NPC npc)
    {
        this.npc = npc;
        this.npcName = npc.getName();
        this.npcIndex = npc.getIndex();
        this.npcInteracting = npc.getInteracting();
        this.npcSpeed = 0;
        this.ticksUntilAttack = 0;
        this.intermissionPeriod = 0;
        this.attackStyle = AttackStyle.UNKNOWN;
        final NPCComposition composition = npc.getTransformedComposition();

        if (composition != null)
        {
            this.npcSize = composition.getSize();
        }
    }
}
