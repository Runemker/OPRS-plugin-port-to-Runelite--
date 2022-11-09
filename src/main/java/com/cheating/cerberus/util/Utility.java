package com.cheating.cerberus.util;

import java.awt.Color;
import java.awt.Font;

import com.cheating.CheatingConfig;
import com.cheating.cerberus.domain.Phase;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.runelite.api.Prayer;
import net.runelite.client.ui.FontManager;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utility
{
    private static final Color COLOR_DEFAULT = new Color(70, 61, 50, 225);
    private static final Color COLOR_GHOSTS = new Color(255, 255, 255, 225);
    private static final Color COLOR_TRIPLE_ATTACK = new Color(0, 15, 255, 225);
    private static final Color COLOR_LAVA = new Color(82, 0, 0, 225);

    public static int calculateNpcHp(final int ratio, final int health, final int maxHp)
    {
        if (ratio < 0 || health <= 0 || maxHp == -1)
        {
            return -1;
        }

        int exactHealth = 0;

        // This is the reverse of the calculation of healthRatio done by the server
        // which is: healthRatio = 1 + (healthScale - 1) * health / maxHealth (if health > 0, 0 otherwise)
        // It's able to recover the exact health if maxHealth <= healthScale.
        if (ratio > 0)
        {
            int minHealth = 1;
            int maxHealth;
            if (health > 1)
            {
                if (ratio > 1)
                {
                    // This doesn't apply if healthRatio = 1, because of the special case in the server calculation that
                    // health = 0 forces healthRatio = 0 instead of the expected healthRatio = 1
                    minHealth = (maxHp * (ratio - 1) + health - 2) / (health - 1);
                }
                maxHealth = (maxHp * ratio - 1) / (health - 1);
                if (maxHealth > maxHp)
                {
                    maxHealth = maxHp;
                }
            }
            else
            {
                // If healthScale is 1, healthRatio will always be 1 unless health = 0
                // so we know nothing about the upper limit except that it can't be higher than maxHealth
                maxHealth = maxHp;
            }
            // Take the average of min and max possible healths
            exactHealth = (minHealth + maxHealth + 1) / 2;
        }

        return exactHealth;
    }

    public static Font getFontFromInfoboxComponentSize(final CheatingConfig.InfoBoxComponentSize size)
    {
        final Font font;

        switch (size)
        {
            case LARGE:
            case MEDIUM:
            default:
                font = FontManager.getRunescapeFont();
                break;
            case SMALL:
                font = FontManager.getRunescapeSmallFont();
                break;
        }

        return font;
    }

    public static Color getColorFromPhase(final Phase phase)
    {
        final Color color;

        switch (phase)
        {
            case TRIPLE:
                color = COLOR_TRIPLE_ATTACK;
                break;
            case LAVA:
                color = COLOR_LAVA;
                break;
            case GHOSTS:
                color = COLOR_GHOSTS;
                break;
            case AUTO:
            default:
                color = COLOR_DEFAULT;
                break;
        }

        return color;
    }

    public static Color getColorFromPrayer(final Prayer prayer)
    {
        switch (prayer)
        {
            case PROTECT_FROM_MAGIC:
                return Color.BLUE;
            case PROTECT_FROM_MISSILES:
                return Color.GREEN;
            case PROTECT_FROM_MELEE:
                return Color.RED;
            default:
                return Color.WHITE;
        }
    }
}
