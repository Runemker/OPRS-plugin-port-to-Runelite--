package com.cheating.hydra.entity;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.cheating.hydra.HydraPlugin;
import com.cheating.hydra.overlay.AttackOverlay;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.SpriteID;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.util.ImageUtil;

@Getter
@RequiredArgsConstructor
public enum HydraPhase
{
    POISON(3, HydraPlugin.HYDRA_1_1, HydraPlugin.HYDRA_1_2, HydraPlugin.HYDRA_POISON, 0,
            825, HydraPlugin.BIG_ASS_GUTHIX_SPELL, new WorldPoint(1371, 10263, 0), Color.GREEN, Color.RED),
    LIGHTNING(3, HydraPlugin.HYDRA_2_1, HydraPlugin.HYDRA_2_2, 0, HydraPlugin.HYDRA_LIGHTNING,
            550, HydraPlugin.BIG_SPEC_TRANSFER, new WorldPoint(1371, 10272, 0), Color.CYAN, Color.GREEN),
    FLAME(3, HydraPlugin.HYDRA_3_1, HydraPlugin.HYDRA_3_2, 0, HydraPlugin.HYDRA_FIRE,
            275, HydraPlugin.BIG_SUPERHEAT, new WorldPoint(1362, 10272, 0), Color.RED, Color.CYAN),
    ENRAGED(1, HydraPlugin.HYDRA_4_1, HydraPlugin.HYDRA_4_2, HydraPlugin.HYDRA_POISON, 0,
            0, HydraPlugin.BIG_ASS_GUTHIX_SPELL, null, null, null);

    private final int attacksPerSwitch;
    private final int deathAnimation1;
    private final int deathAnimation2;
    private final int specialProjectileId;
    private final int specialAnimationId;
    private final int hpThreshold;

    @Getter(AccessLevel.NONE)
    private final int spriteId;

    private final WorldPoint fountainWorldPoint;

    private final Color phaseColor;
    private final Color fountainColor;

    private BufferedImage specialImage;

    public BufferedImage getSpecialImage(final SpriteManager spriteManager)
    {
        if (specialImage == null)
        {
            final BufferedImage tmp = spriteManager.getSprite(spriteId, 0);
            specialImage = tmp == null ? null : ImageUtil.resizeImage(tmp, AttackOverlay.IMAGE_SIZE, AttackOverlay.IMAGE_SIZE);
        }

        return specialImage;
    }
}
