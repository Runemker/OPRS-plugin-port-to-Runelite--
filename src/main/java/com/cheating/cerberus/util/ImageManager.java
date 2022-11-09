package com.cheating.cerberus.util;

import java.awt.image.BufferedImage;

import com.cheating.CheatingConfig;
import com.cheating.CheatingPlugin;
import com.cheating.cerberus.domain.Phase;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.runelite.api.Prayer;
import net.runelite.client.util.ImageUtil;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ImageManager
{
    private static double RESIZE_FACTOR = 1.25D;

    private static final BufferedImage[][] images = new BufferedImage[3][6];

    public static BufferedImage getCerberusBufferedImage(final Phase phase, final Prayer prayer, final CheatingConfig.InfoBoxComponentSize size)
    {
        if (phase == Phase.AUTO)
        {
            return getCerberusPrayerBufferedImage(prayer, size);
        }

        return getCerberusPhaseBufferedImage(phase, size);
    }

    private static BufferedImage getCerberusPrayerBufferedImage(final Prayer prayer, final CheatingConfig.InfoBoxComponentSize size)
    {
        final String path;
        final int imgIdx;

        switch (prayer)
        {
            default:
            case PROTECT_FROM_MAGIC:
                path = "/Cerberus/cerberus_magic.png";
                imgIdx = 0;
                break;
            case PROTECT_FROM_MISSILES:
                path = "/Cerberus/cerberus_range.png";
                imgIdx = 1;
                break;
            case PROTECT_FROM_MELEE:
                path = "/Cerberus/cerberus_melee.png";
                imgIdx = 2;
                break;
        }

        return getBufferedImage(path, imgIdx, size);
    }

    private static BufferedImage getCerberusPhaseBufferedImage(final Phase phase, final CheatingConfig.InfoBoxComponentSize size)
    {
        final String path;
        final int imgIdx;

        switch (phase)
        {
            default:
            case TRIPLE:
                path = "/Cerberus/cerberus_triple.png";
                imgIdx = 3;
                break;
            case GHOSTS:
                path = "/Cerberus/cerberus_ghosts.png";
                imgIdx = 4;
                break;
            case LAVA:
                path = "/Cerberus/cerberus_lava.png";
                imgIdx = 5;
                break;
        }

        return getBufferedImage(path, imgIdx, size);
    }

    private static BufferedImage getBufferedImage(final String path, final int imgIdx, final CheatingConfig.InfoBoxComponentSize size)
    {
        final BufferedImage img = ImageUtil.loadImageResource(CheatingPlugin.class, path);

        final int resize = (int) (size.getSize() / RESIZE_FACTOR);

        switch (size)
        {
            default:
            case SMALL:
                if (images[0][imgIdx] == null)
                {
                    images[0][imgIdx] = ImageUtil.resizeImage(img, resize, resize);
                }
                return images[0][imgIdx];
            case MEDIUM:
                if (images[1][imgIdx] == null)
                {
                    images[1][imgIdx] = ImageUtil.resizeImage(img, resize, resize);
                }
                return images[1][imgIdx];
            case LARGE:
                if (images[2][imgIdx] == null)
                {
                    images[2][imgIdx] = ImageUtil.resizeImage(img, resize, resize);
                }
                return images[2][imgIdx];
        }
    }
}
