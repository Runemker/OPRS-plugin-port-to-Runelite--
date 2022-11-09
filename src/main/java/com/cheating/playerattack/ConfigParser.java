package com.cheating.playerattack;
import com.google.common.base.Splitter;

import java.util.HashMap;
import java.util.Optional;


public final class ConfigParser
{
    private ConfigParser()
    {

    }

    public static boolean parse_config(final String value)
    {
        return parse(value).isPresent();
    }

    public static Optional<HashMap<Integer, Integer>> parse(final String value)
    {
        if (value.isEmpty() || value.isBlank())
        {
            return Optional.empty();
        }

        final Splitter NEWLINE_SPLITTER = Splitter
                .on("\n")
                .omitEmptyStrings()
                .trimResults();

        HashMap<Integer, Integer> tickMap = new HashMap<>();

        for (String line : NEWLINE_SPLITTER.split(value))
        {
            String[] segments = line.split(":");

            try
            {
                int animation = Integer.parseInt(segments[0]);
                int delay = Integer.parseInt(segments[1]);

                tickMap.put(animation, delay);
            }
            catch (Exception e)
            {
                return Optional.empty();
            }
        }

        return Optional.of(tickMap);
    }
}
