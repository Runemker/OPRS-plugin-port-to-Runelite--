package com.cheating;

import com.cheating.Util.PrayerHighlightMode;
import com.cheating.config.FontStyle;
import com.cheating.inferno.displaymodes.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.client.config.*;
import net.runelite.client.ui.overlay.components.ComponentOrientation;

import java.awt.*;

@ConfigGroup("Cheating")
public interface CheatingConfig extends Config
{
    @ConfigSection(
            name = "Player attack timer",
            description = "",
            position = 1,
            closedByDefault = true
    )
    String playerAttackTimer = "Player Attack Timers";

    @ConfigSection(
            name = "Gaunlet Helper",
            description = "",
            position = 3,
            closedByDefault = true
    )
    String gauntletHelper = "Gaunlet Helper";

    @ConfigSection(
            name = "Alchemical Hydra",
            description = "",
            position = 2,
            closedByDefault = true
    )
    String hydra = "Alchemical Hydra";

    @ConfigSection(
            name = "God Wars",
            description = "",
            position = 5,
            closedByDefault = true
    )
    String godWars = "God Wars";

    @ConfigSection(
            name = "Chambers of Xeric",
            description = "",
            position = 4,
            closedByDefault = true
    )
    String cox = "Chambers of Xeric";

    @ConfigSection(
            name = "Zulrah",
            description = "",
            position = 6,
            closedByDefault = true
    )
    String zulrah = "Zulrah";

    @ConfigSection(
            name = "Inferno",
            description = "",
            position = 7,
            closedByDefault = true
    )
    String inferno = "Inferno";

    @ConfigSection(
            name = "Cerberus",
            description = "",
            position = 8,
            closedByDefault = true
    )
    String cerberus = "Cerberus";

    @ConfigSection(
            name = "TOA: General settings",
            description = "",
            position = 18,
            closedByDefault = true
    )
    String toaGen = "TOA: General settings";

    @ConfigSection(
            name = "TOA: Het path",
            description = "",
            position = 19,
            closedByDefault = true
    )
    String toaHet = "TOA: Het path";

    @ConfigSection(
            name = "TOA: Crondis path",
            description = "",
            position = 20,
            closedByDefault = true
    )
    String toaCrondis = "TOA: Crondis path";

    @ConfigSection(
            name = "TOA: Scarabas path",
            description = "",
            position = 21,
            closedByDefault = true
    )
    String toaScarabas= "TOA: Scarabas path";

    @ConfigSection(
            name = "TOA: Apmeken path",
            description = "",
            position = 22,
            closedByDefault = true
    )
    String toaApmeken= "TOA: Apmeken path";

    @ConfigSection(
            name = "TOA: Prayer helper",
            description = "",
            position = 30,
            closedByDefault = true
    )
    String toaPrayer = "TOA: Prayer helper";

    @ConfigSection(
            name = "TOA: Wardens",
            description = "",
            position = 31,
            closedByDefault = true
    )
    String toaWardens= "TOA: Wardens";

    @ConfigSection(
            name = "Maiden",
            description = "",
            position = 100,
            closedByDefault = true
    )
    String maiden = "Maiden";

    @ConfigSection(
            name = "Bloat",
            description = "",
            position = 101,
            closedByDefault = true
    )
    String bloat = "Bloat";

    @ConfigSection(
            name = "Nylocas",
            description = "",
            position = 102,
            closedByDefault = true
    )
    String nylocas = "Nylocas";

    @ConfigSection(
            name = "Sotetseg",
            description = "",
            position = 103,
            closedByDefault = true
    )
    String sotetseg = "Sotetseg";

    @ConfigSection(
            name = "Xarpus",
            description = "",
            position = 104,
            closedByDefault = true
    )
    String xarpus = "Xarpus";

    @ConfigSection(
            name = "Verzik",
            description = "",
            position = 105,
            closedByDefault = true
    )
    String verzik = "Verzik";

    @ConfigSection(
            name = "Theatre Prayer Helper",
            description = "",
            position = 106,
            closedByDefault = true
    )
    String tPrayer = "Theatre Prayer Helper";


    //------------------------------------------------------------//
    // Player Attack Timers
    //------------------------------------------------------------//

    @ConfigItem(
            name = "Debug animation ids",
            description = "Show your player's current animation ID."
                    + "<br>Animation IDs can be viewed by wielding a weapon and attacking an NPC.",
            position = 0,
            keyName = "debugAnimationIdsPAT",
            section = playerAttackTimer
    )
    default boolean debugAnimationIdsPAT()
    {
        return false;
    }

    @ConfigItem(
            name = "Custom animations (one per line)",
            description = "Syntax AnimationID:TickDelay"
                    + "<br>e.g. Abyssal whip would be 1658:4"
                    + "<br>Animation Ids can be obtained by enabling the above debug setting."
                    + "<br>Weapon tick delays can be found on the wiki.",
            position = 1,
            keyName = "customAnimationsPAT",
            section = playerAttackTimer
    )
    default String customAnimationsPAT()
    {
        return "";
    }

    @ConfigItem(
            name = "Font style",
            description = "Font style can be bold, plain, or italicized.",
            position = 0,
            keyName = "fontStylePAT",
            section = playerAttackTimer
    )
    default FontStyle fontStylePAT()
    {
        return FontStyle.BOLD;
    }

    @ConfigItem(
            name = "Font shadow",
            description = "Toggle font shadow.",
            position = 1,
            keyName = "fontShadowPAT",
            section = playerAttackTimer
    )
    default boolean fontShadowPAT()
    {
        return true;
    }

    @Range(
            min = 12,
            max = 64
    )
    @ConfigItem(
            name = "Font size",
            description = "Adjust font size.",
            position = 2,
            keyName = "fontSizePAT",
            section = playerAttackTimer
    )
    default int fontSizePAT()
    {
        return 16;
    }

    @Alpha
    @ConfigItem(
            name = "Font color",
            description = "Adjust font color.",
            position = 3,
            keyName = "fontColorPAT",
            section = playerAttackTimer
    )
    default Color fontColorPAT()
    {
        return new Color(255, 255, 0, 255);
    }

    @Range(
            min = -100,
            max = 100
    )
    @ConfigItem(
            name = "Font zOffset",
            description = "Adjust the Z coordinate offset.",
            position = 4,
            keyName = "fontZOffsetPAT",
            section = playerAttackTimer
    )
    default int fontZOffsetPAT()
    {
        return 0;
    }


    //------------------------------------------------------------//
    // Corrupted Gauntlet
    //------------------------------------------------------------//


    /* Hunllef section*/

    @ConfigItem(
            name = "Display counter on Hunllef",
            description = "Overlay the Hunllef with an attack and prayer counter.",
            position = 0,
            keyName = "hunllefOverlayAttackCounter",
            section = gauntletHelper
    )
    default boolean hunllefOverlayAttackCounter()
    {
        return false;
    }

    @ConfigItem(
            name = "Counter font style",
            description = "Change the font style of the attack and prayer counter.",
            position = 1,
            keyName = "hunllefAttackCounterFontStyle",
            section = gauntletHelper
    )
    default FontStyle hunllefAttackCounterFontStyle()
    {
        return FontStyle.BOLD;
    }

    @Range(
            min = 12,
            max = 64
    )
    @ConfigItem(
            name = "Counter font size",
            description = "Adjust the font size of the attack and prayer counter.",
            position = 2,
            keyName = "hunllefAttackCounterFontSize",
            section = gauntletHelper
    )

    default int hunllefAttackCounterFontSize()
    {
        return 22;
    }


    @ConfigItem(
            name = "Outline Hunllef on wrong prayer",
            description = "Outline the Hunllef when incorrectly praying against its current attack style.",
            position = 3,
            keyName = "hunllefOverlayWrongPrayerOutline",
            section = gauntletHelper
    )
    default boolean hunllefOverlayWrongPrayerOutline()
    {
        return false;
    }

    @Range(
            min = 2,
            max = 12
    )
    @ConfigItem(
            name = "Outline width",
            description = "Change the width of the wrong prayer outline.",
            position = 4,
            keyName = "hunllefWrongPrayerOutlineWidth",
            section = gauntletHelper
    )
    default int hunllefWrongPrayerOutlineWidth()
    {
        return 4;
    }


    /* Prayer */

    @ConfigItem(
            name = "Overlay prayer",
            description = "Overlay the correct prayer to use against the Hunllef's current attack style.",
            position = 4,
            keyName = "prayerOverlay",
            section = gauntletHelper
    )
    default PrayerHighlightMode prayerOverlay()
    {
        return PrayerHighlightMode.NONE;
    }




    /* Tornadoes */

    @ConfigItem(
            name = "Overlay tornado tick counter",
            description = "Overlay tornadoes with a tick counter.",
            position = 5,
            keyName = "tornadoTickCounter",
            section = gauntletHelper
    )
    default boolean tornadoTickCounter()
    {
        return false;
    }

    @ConfigItem(
            name = "Font style",
            description = "Bold/Italics/Plain",
            position = 6,
            keyName = "tornadoFontStyle",
            section = gauntletHelper
    )
    default FontStyle tornadoFontStyle()
    {
        return FontStyle.BOLD;
    }

    @ConfigItem(
            name = "Font shadow",
            description = "Toggle font shadow of the tornado tick counter.",
            position = 7,
            keyName = "tornadoFontShadow",
            section = gauntletHelper
    )
    default boolean tornadoFontShadow()
    {
        return true;
    }

    @Range(
            min = 12,
            max = 64
    )
    @ConfigItem(
            name = "Font size",
            description = "Adjust the font size of the tornado tick counter.",
            position = 8,
            keyName = "tornadoFontSize",
            section = gauntletHelper
    )
    default int tornadoFontSize()
    {
        return 16;
    }

    @Alpha
    @ConfigItem(
            name = "Font color",
            description = "Color of the tornado tick counter font.",
            position = 9,
            keyName = "tornadoFontColor",
            section = gauntletHelper
    )
    default Color tornadoFontColor()
    {
        return Color.WHITE;
    }

    @ConfigItem(
            name = "Outline tornado tile",
            description = "Outline the tiles of tornadoes.",
            position = 10,
            keyName = "tornadoTileOutline",
            section = gauntletHelper
    )
    default boolean tornadoTileOutline()
    {
        return false;
    }

    @Range(
            min = 1,
            max = 8
    )
    @ConfigItem(
            name = "Tile outline width",
            description = "Change tile outline width of tornadoes.",
            position = 11,
            keyName = "tornadoTileOutlineWidth",
            section = gauntletHelper
    )
    default int tornadoTileOutlineWidth()
    {
        return 1;
    }

    @Alpha
    @ConfigItem(
            name = "Tile outline color",
            description = "Color to outline the tile of a tornado.",
            position = 12,
            keyName = "tornadoOutlineColor",
            section = gauntletHelper
    )
    default Color tornadoOutlineColor()
    {
        return Color.YELLOW;
    }

    @Alpha
    @ConfigItem(
            name = "Tile fill color",
            description = "Color to fill the tile of a tornado.",
            position = 13,
            keyName = "tornadoFillColor",
            section = gauntletHelper
    )
    default Color tornadoFillColor()
    {
        return new Color(255, 255, 0, 50);
    }


    /* Flash 5:1*/

    @ConfigItem(
            name = "Flash on 5:1 method",
            description = "Flash the screen to weapon switch when using 5:1 method.",
            position = 14,
            keyName = "flashOn51Method",
            section = gauntletHelper
    )
    default boolean flashOn51Method()
    {
        return false;
    }

    @Range(
            min = 10,
            max = 50
    )
    @ConfigItem(
            name = "Flash duration",
            description = "Change the duration of the flash.",
            position = 15,
            keyName = "flashOn51MethodDuration",
            section = gauntletHelper
    )
    default int flashOn51MethodDuration()
    {
        return 25;
    }

    @Alpha
    @ConfigItem(
            name = "Flash color",
            description = "Color of the flash notification.",
            position = 16,
            keyName = "flashOn51MethodColor",
            section = gauntletHelper
    )
    default Color flashOn51MethodColor()
    {
        return new Color(255, 190, 0, 50);
    }

    @ConfigItem(
            name = "Flash on wrong attack style",
            description = "Flash the screen if you use the wrong attack style.",
            position = 17,
            keyName = "flashOnWrongAttack",
            section = gauntletHelper
    )
    default boolean flashOnWrongAttack()
    {
        return false;
    }

    @Range(
            min = 10,
            max = 50
    )
    @ConfigItem(
            name = "Flash duration",
            description = "Change the duration of the flash.",
            position = 18,
            keyName = "flashOnWrongAttackDuration",
            section = gauntletHelper
    )
    default int flashOnWrongAttackDuration()
    {
        return 25;
    }

    @Alpha
    @ConfigItem(
            name = "Flash color",
            description = "Color of the flash notification.",
            position = 19,
            keyName = "flashOnWrongAttackColor",
            section = gauntletHelper
    )
    default Color flashOnWrongAttackColor()
    {
        return new Color(255, 0, 0, 70);
    }


    //------------------------------------------------------------//
    // Theatre of Blood
    //------------------------------------------------------------//

    //Maiden

    @Range(max = 20)
    @ConfigItem(
            position = 0,
            keyName = "theatreFontSize",
            name = "Theatre Overlay Font Size",
            description = "Sets the font size for all theatre text overlays.",
            section = maiden
    )
    default int theatreFontSize()
    {
        return 12;
    }

    @ConfigItem(
            position = 1,
            keyName = "maidenBlood",
            name = "Maiden Blood Attack Marker",
            description = "Highlights Maiden's Blood Pools.",
            section = maiden
    )
    default boolean maidenBlood()
    {
        return true;
    }

    @ConfigItem(
            position = 2,
            keyName = "maidenSpawns",
            name = "Maiden Blood Spawns Marker",
            description = "Highlights Maiden Blood Spawns (Tomatoes).",
            section = maiden
    )
    default boolean maidenSpawns()
    {
        return true;
    }

    @ConfigItem(
            position = 3,
            keyName = "maidenReds",
            name = "Maiden Reds Health Overlay",
            description = "Displays the health of each red crab.",
            section = maiden
    )
    default boolean maidenRedsHealth()
    {
        return true;
    }

    @ConfigItem(
            position = 4,
            keyName = "maidenRedsDistance",
            name = "Maiden Reds Distance Overlay",
            description = "Displays the distance of each red crab to reach Maiden.",
            section = maiden
    )
    default boolean maidenRedsDistance()
    {
        return false;
    }

    @ConfigItem(
            position = 5,
            keyName = "MaidenTickCounter",
            name = "Maiden Tank Tick Counter",
            description = "Displays the tick counter for when she decides who to choose for tanking.",
            section = maiden
    )
    default boolean maidenTickCounter()
    {
        return true;
    }

    //BLOAT
    @ConfigItem(
            position = 6,
            keyName = "bloatIndicator",
            name = "Bloat Tile Indicator",
            description = "Highlights Bloat's Tile.",
            section = bloat
    )
    default boolean bloatIndicator()
    {
        return true;
    }

    @Alpha
    @ConfigItem(
            position = 7,
            keyName = "bloatIndicatorColorUP",
            name = "Bloat Indicator Color - UP",
            description = "Select a color for when Bloat is UP.",
            section = bloat
    )
    default Color bloatIndicatorColorUP()
    {
        return Color.CYAN;
    }

    @Alpha
    @ConfigItem(
            position = 8,
            keyName = "bloatIndicatorColorTHRESH",
            name = "Bloat Indicator Color - THRESHOLD",
            description = "Select a color for when Bloat UP and goes over 37 ticks, which allows you to know when he can go down.",
            section = bloat
    )
    default Color bloatIndicatorColorTHRESH()
    {
        return Color.ORANGE;
    }

    @Alpha
    @ConfigItem(
            position = 9,
            keyName = "bloatIndicatorColorDOWN",
            name = "Bloat Indicator Color - DOWN",
            description = "Select a color for when Bloat is DOWN.",
            section = bloat
    )
    default Color bloatIndicatorColorDOWN()
    {
        return Color.WHITE;
    }

    @Alpha
    @ConfigItem(
            position = 9,
            keyName = "bloatIndicatorColorWARN",
            name = "Bloat Indicator Color - WARN",
            description = "Select a color for when Bloat is DOWN and about to get UP.",
            section = bloat
    )
    default Color bloatIndicatorColorWARN()
    {
        return Color.RED;
    }

    @ConfigItem(
            position = 10,
            keyName = "bloatTickCounter",
            name = "Bloat Tick Counter",
            description = "Displays the tick counter for how long Bloat has been DOWN or UP.",
            section = bloat
    )
    default boolean bloatTickCounter()
    {
        return true;
    }

    @ConfigItem(
            position = 11,
            keyName = "BloatTickCountStyle",
            name = "Bloat Tick Time Style",
            description = "Count up or Count down options on bloat downed state",
            section = bloat
    )
    default BLOATTIMEDOWN BloatTickCountStyle()
    {
        return BLOATTIMEDOWN.COUNTDOWN;
    }

    @ConfigItem(
            position = 12,
            keyName = "bloatHands",
            name = "Bloat Hands Overlay",
            description = "Highlights the tiles where Bloat's hands will fall.",
            section = bloat
    )
    default boolean bloatHands()
    {
        return false;
    }

    @Alpha
    @ConfigItem(
            position = 13,
            keyName = "bloatHandsColor",
            name = "Bloat Hands Overlay Color",
            description = "Select a color for the Bloat Hands Overlay to be.",
            section = bloat
    )
    default Color bloatHandsColor()
    {
        return Color.CYAN;
    }

    @Range(max = 10)
    @ConfigItem(
            position = 14,
            keyName = "bloatHandsWidth",
            name = "Bloat Hands Overlay Thickness",
            description = "Sets the stroke width of the tile overlay where the hands fall. (BIGGER = THICKER).",
            section = bloat
    )
    default int bloatHandsWidth()
    {
        return 2;
    }

    @ConfigItem(
            name = "Hide Bloat Tank",
            keyName = "hideBloatTank",
            description = "Hides the entire Bloat tank in the center of the room",
            position = 15,
            section = bloat
    )
    default boolean hideBloatTank()
    {
        return false;
    }

    @ConfigItem(
            name = "Hide Ceiling Chains",
            keyName = "hideCeilingChains",
            description = "Hides the chains hanging from the ceiling in the Bloat room",
            position = 16,
            section = bloat
    )
    default boolean hideCeilingChains()
    {
        return false;
    }

    //Nylocas

    @ConfigItem(
            position = 0,
            keyName = "nyloPillars",
            name = "Nylocas Pillar Health Overlay",
            description = "Displays the health percentage of the pillars.",
            section = nylocas
    )
    default boolean nyloPillars()
    {
        return true;
    }

    @ConfigItem(
            position = 1,
            keyName = "nyloExplosions",
            name = "Nylocas Explosion Warning",
            description = "Highlights a Nylocas that is about to explode.",
            section = nylocas
    )
    default boolean nyloExplosions()
    {
        return true;
    }

    @Range(max = 52)
    @ConfigItem(
            position = 2,
            keyName = "nyloExplosionDisplayTicks",
            name = "Nylocas Display Last Ticks",
            description = "Displays the last 'x' amount of ticks for a Nylocas. (ex: to see the last 10 ticks, you set it to 10).",
            section = nylocas
    )
    default int nyloExplosionDisplayTicks()
    {
        return 46;
    }

    @ConfigItem(
            position = 3,
            keyName = "nyloExplosionDisplayStyle",
            name = "Nylocas Display Explosion Style",
            description = "How to display when a nylocas is about to explode.",
            section = nylocas
    )
    default EXPLOSIVENYLORENDERSTYLE nyloExplosionOverlayStyle()
    {
        return EXPLOSIVENYLORENDERSTYLE.RECOLOR_TICK;
    }

    @ConfigItem(
            position = 4,
            keyName = "nyloTimeAlive",
            name = "Nylocas Tick Time Alive",
            description = "Displays the tick counter of each nylocas spawn (Explodes on 52).",
            section = nylocas
    )
    default boolean nyloTimeAlive()
    {
        return false;
    }

    @ConfigItem(
            position = 5,
            keyName = "nyloTimeAliveCountStyle",
            name = "Nylocas Tick Time Alive Style",
            description = "Count up or Count down options on the tick time alive.",
            section = nylocas
    )
    default NYLOTIMEALIVE nyloTimeAliveCountStyle()
    {
        return NYLOTIMEALIVE.COUNTDOWN;
    }

    @ConfigItem(
            position = 6,
            keyName = "nyloRecolorMenu",
            name = "Nylocas Recolor Menu Options",
            description = "Recolors the menu options of each Nylocas to it's respective attack style.",
            section = nylocas
    )
    default boolean nyloRecolorMenu()
    {
        return false;
    }

    @ConfigItem(
            position = 7,
            keyName = "nyloHighlightOverlay",
            name = "Nylocas Highlight Overlay",
            description = "Select your role to highlight respective Nylocas to attack.",
            section = nylocas
    )
    default boolean nyloHighlightOverlay()
    {
        return false;
    }

    @ConfigItem(
            position = 8,
            keyName = "nyloAliveCounter",
            name = "Nylocas Alive Counter Panel",
            description = "Displays how many Nylocas are currently alive.",
            section = nylocas
    )
    default boolean nyloAlivePanel()
    {
        return false;
    }

    @ConfigItem(
            position = 9,
            keyName = "nyloAggressiveOverlay",
            name = "Highlight Aggressive Nylocas",
            description = "Highlights aggressive Nylocas after they spawn.",
            section = nylocas
    )
    default boolean nyloAggressiveOverlay()
    {
        return true;
    }

    @ConfigItem(
            position = 10,
            keyName = "nyloAggressiveOverlayStyle",
            name = "Highlight Aggressive Nylocas Style",
            description = "Highlight style for aggressive Nylocas after they spawn.",
            section = nylocas
    )
    default AGGRESSIVENYLORENDERSTYLE nyloAggressiveOverlayStyle()
    {
        return AGGRESSIVENYLORENDERSTYLE.TILE;
    }


    @ConfigItem(
            position = 11,
            keyName = "removeNyloEntries",
            name = "Remove Attack Options",
            description = "Removes the attack options for Nylocas immune to your current attack style.",
            section = nylocas
    )
    default boolean removeNyloEntries()
    {
        return true;
    }

    @ConfigItem(
            position = 12,
            keyName = "nylocasWavesHelper",
            name = "Nylocas Waves Helper",
            description = "Overlay's squares with wave numbers on nylo entry bridges for upcoming nylos",
            section = nylocas
    )
    default boolean nyloWavesHelper()
    {
        return false;
    }

    @ConfigItem(
            position = 13,
            keyName = "nylocasTicksUntilWave",
            name = "Nylocas Ticks Until Wave",
            description = "Prints how many ticks until the next wave could spawn",
            section = nylocas
    )
    default boolean nyloTicksUntilWaves()
    {
        return false;
    }

    @ConfigItem(
            position = 14,
            keyName = "nyloInstanceTimer",
            name = "Nylocas Instance Timer",
            description = "Displays an instance timer when the next set will potentially spawn - ENTER ON ZERO.",
            section = nylocas
    )
    default boolean nyloInstanceTimer()
    {
        return false;
    }

    @ConfigItem(
            position = 15,
            keyName = "nyloStallMessage",
            name = "Nylocas Stall Wave Messages",
            description = "Sends a chat message when you have stalled the next wave of Nylocas to spawn due to being capped.",
            section = nylocas
    )
    default boolean nyloStallMessage()
    {
        return false;
    }

    @ConfigItem(
            position = 16,
            keyName = "nylocasBigSplitsHelper",
            name = "Nylocas Big Splits",
            description = "Tells you when bigs will spawn little nylos",
            section = nylocas
    )
    default boolean bigSplits()
    {
        return false;
    }

    @ConfigItem(
            position = 17,
            keyName = "nylocasBigSplitsHighlightColor",
            name = "Highlight Color",
            description = "Color of the NPC highlight",
            section = nylocas
    )
    @Alpha
    default Color getBigSplitsHighlightColor()
    {
        return Color.YELLOW;
    }

    @ConfigItem(
            position = 18,
            keyName = "nylocasBigSplitsTileColor2",
            name = "Highlight Color Tick 2",
            description = "Color of the NPC highlight on tick 1",
            section = nylocas
    )
    @Alpha
    default Color getBigSplitsTileColor2()
    {
        return Color.ORANGE;
    }

    @ConfigItem(
            position = 19,
            keyName = "nylocasBigSplitsTileColor1",
            name = "Highlight Color Tick 1",
            description = "Color of the NPC highlight on tick 0",
            section = nylocas
    )
    @Alpha
    default Color getBigSplitsTileColor1()
    {
        return Color.RED;
    }

    @ConfigItem(
            position = 20,
            keyName = "nylocasBigSplitsTextColor2",
            name = "Text Color Tick 2",
            description = "Color of the baby tick counter on tick 2",
            section = nylocas
    )
    @Alpha
    default Color getBigSplitsTextColor2()
    {
        return Color.ORANGE;
    }

    @ConfigItem(
            position = 21,
            keyName = "nylocasBigSplitsTextColor1",
            name = "Text Color Tick 1",
            description = "Color of the baby tick counter on tick 1",
            section = nylocas
    )
    @Alpha
    default Color getBigSplitsTextColor1()
    {
        return Color.RED;
    }

    @ConfigItem(
            position = 22,
            keyName = "nyloBossAttackTickCount",
            name = "Nylocas Boss Attack Tick Counter",
            description = "Displays the ticks left until the Nylocas Boss will attack next (LEFT-MOST).",
            section = nylocas
    )
    default boolean nyloBossAttackTickCount()
    {
        return false;
    }

    @ConfigItem(
            position = 23,
            keyName = "nyloBossSwitchTickCount",
            name = "Nylocas Boss Switch Tick Counter",
            description = "Displays the ticks left until the Nylocas Boss will switch next (MIDDLE).",
            section = nylocas
    )
    default boolean nyloBossSwitchTickCount()
    {
        return true;
    }

    @ConfigItem(
            position = 24,
            keyName = "nyloBossTotalTickCount",
            name = "Nylocas Boss Total Tick Counter",
            description = "Displays the total ticks since the Nylocas Boss has spawned (RIGHT-MOST).",
            section = nylocas
    )
    default boolean nyloBossTotalTickCount()
    {
        return false;
    }

    @ConfigItem(
            position = 25,
            keyName = "removeNyloBossEntries",
            name = "Nylocas Boss Remove Attack Options",
            description = "Removes the attack options for Nylocas Boss when immune to your current attack style.",
            section = nylocas
    )
    default boolean removeNyloBossEntries()
    {
        return true;
    }

    @ConfigItem(
            keyName = "highlightMelee",
            name = "",
            description = "",
            hidden = true,
            section = nylocas,
            position = 26
    )
    default boolean getHighlightMeleeNylo()
    {
        return false;
    }

    @ConfigItem(
            keyName = "highlightMelee",
            name = "",
            description = "",
            hidden = true,
            section = nylocas,
            position = 27
    )
    void setHighlightMeleeNylo(boolean set);

    @ConfigItem(
            keyName = "highlightMage",
            name = "",
            description = "",
            hidden = true,
            section = nylocas,
            position = 28
    )
    default boolean getHighlightMageNylo()
    {
        return false;
    }

    @ConfigItem(
            keyName = "highlightMage",
            name = "",
            description = "",
            hidden = true,
            section = nylocas,
            position = 29
    )
    void setHighlightMageNylo(boolean set);

    @ConfigItem(
            keyName = "highlightRange",
            name = "",
            description = "",
            hidden = true,
            section = nylocas,
            position = 30
    )
    default boolean getHighlightRangeNylo()
    {
        return false;
    }

    @ConfigItem(
            keyName = "highlightRange",
            name = "",
            description = "",
            hidden = true,
            section = nylocas,
            position = 31
    )
    void setHighlightRangeNylo(boolean set);

    @ConfigItem(
            position = 0,
            keyName = "sotetsegMaze",
            name = "Sotetseg Maze",
            description = "Memorizes Solo Mazes and displays tiles of other chosen players.",
            section = sotetseg
    )
    default boolean sotetsegMaze()
    {
        return true;
    }

    @ConfigItem(
            position = 1,
            keyName = "sotetsegOrbAttacksTicks",
            name = "Sotetseg Small Attack Orb Ticks",
            description = "Displays the amount of ticks until it will hit you (change prayers when you see 1).",
            section = sotetseg
    )
    default boolean sotetsegOrbAttacksTicks()
    {
        return true;
    }

    @ConfigItem(
            position = 2,
            keyName = "sotetsegAutoAttacksTicks",
            name = "Sotetseg Auto Attack Ticks",
            description = "Displays a tick counter for when Sotetseg will attack next.",
            section = sotetseg
    )
    default boolean sotetsegAutoAttacksTicks()
    {
        return true;
    }

    @ConfigItem(
            position = 4,
            keyName = "sotetsegBigOrbTicks",
            name = "Sotetseg Big Ball Tick Overlay",
            description = "Displays how many ticks until the ball will explode (eat when you see 0).",
            section = sotetseg
    )
    default boolean sotetsegBigOrbTicks()
    {
        return true;
    }

    @Alpha
    @ConfigItem(
            position = 5,
            keyName = "sotetsegBigOrbTickColor",
            name = "Sotetseg Big Ball Tick Color",
            description = "Select a color for the Sotetseg Big Ball tick countdown text.",
            section = sotetseg
    )
    default Color sotetsegBigOrbTickColor()
    {
        return Color.WHITE;
    }

    @Alpha
    @ConfigItem(
            position = 6,
            keyName = "sotetsegBigOrbTileColor",
            name = "Sotetseg Big Ball Tile Color",
            description = "Select a color for the Sotetseg Big Ball tile color.",
            section = sotetseg
    )
    default Color sotetsegBigOrbTileColor()
    {
        return new Color(188, 74, 74, 255);
    }

    //Xarpus

    @ConfigItem(
            position = 0,
            keyName = "xarpusInstanceTimer",
            name = "Xarpus Instance Timer",
            description = "Displays the Xarpus Instance timer to be tick efficient with the first spawn of an exhumed - ENTER ON ZERO.",
            section = xarpus
    )
    default boolean xarpusInstanceTimer()
    {
        return true;
    }

    @ConfigItem(
            position = 2,
            keyName = "xarpusExhumed",
            name = "Xarpus Exhumed Markers",
            description = "Highlights the tiles of exhumed spawns.",
            section = xarpus
    )
    default boolean xarpusExhumed()
    {
        return true;
    }

    @ConfigItem(
            position = 3,
            keyName = "xarpusExhumedTick",
            name = "Xarpus Exhumed Ticks",
            description = "Displays how many ticks until the exhumeds will despawn.",
            section = xarpus
    )
    default boolean xarpusExhumedTick()
    {
        return true;
    }

    @ConfigItem(
            position = 4,
            keyName = "xarpusExhumedCount",
            name = "Xarpus Exhumed Count",
            description = "Count the amount of exhumeds.",
            section = xarpus
    )
    default XARPUS_EXHUMED_COUNT xarpusExhumedCount()
    {
        return XARPUS_EXHUMED_COUNT.DOWN;
    }

    @ConfigItem(
            position = 5,
            keyName = "xarpusTickP2",
            name = "Xarpus Attack Tick - P2",
            description = "Displays a tick counter for when Xarpus faces a new target to spit at.",
            section = xarpus
    )
    default boolean xarpusTickP2()
    {
        return true;
    }

    @ConfigItem(
            position = 6,
            keyName = "xarpusTickP3",
            name = "Xarpus Attack Tick - P3",
            description = "Displays a tick counter for when Xarpus will rotate.",
            section = xarpus
    )
    default boolean xarpusTickP3()
    {
        return true;
    }

    @ConfigItem(
            position = 7,
            name = "Line of Sight",
            keyName = "xarpusLineOfSight",
            description = "Displays Xarpus's Line of Sight on P3<br>Melee Tiles: Displays only the melee tiles that Xarpus can see<br>Square: Displays the whole region that Xarpus can see",
            section = xarpus
    )
    default XARPUS_LINE_OF_SIGHT xarpusLineOfSight()
    {
        return XARPUS_LINE_OF_SIGHT.OFF;
    }

    @Alpha
    @ConfigItem(
            position = 8,
            name = "Line of Sight Color",
            keyName = "xarpusLineOfSightColor",
            description = "Customize the color for Xarpus's Line of Sight",
            section = xarpus
    )
    default Color xarpusLineOfSightColor()
    {
        return Color.RED;
    }

    //Verzik

    @ConfigItem(
            position = 0,
            keyName = "verzikTileOverlay",
            name = "Verzik Tile Indicator",
            description = "Highlights Verzik's tile - If you are next to or inside of the indicator, you can be meleed.",
            section = verzik
    )
    default boolean verzikTileOverlay()
    {
        return true;
    }

    @ConfigItem(
            position = 1,
            keyName = "verzikProjectiles",
            name = "Verzik Range Tile Markers",
            description = "Highlights the tiles of Verzik's range projectiles.",
            section = verzik
    )
    default boolean verzikProjectiles()
    {
        return true;
    }

    @Alpha
    @ConfigItem(
            position = 2,
            keyName = "verzikProjectilesColor",
            name = "Verzik Range Tile Markers Color",
            description = "Select a color for the Verzik's Range Projectile Tile Overlay to be.",
            section = verzik
    )
    default Color verzikProjectilesColor()
    {
        return new Color(255, 0, 0, 50);
    }

    @ConfigItem(
            position = 3,
            keyName = "VerzikRedHP",
            name = "Verzik Reds Health Overlay",
            description = "Displays the health of red crabs during Verzik.",
            section = verzik
    )
    default boolean verzikReds()
    {
        return true;
    }

    @ConfigItem(
            position = 4,
            keyName = "verzikAutosTick",
            name = "Verzik Attack Tick Counter",
            description = "Displays the ticks until Verzik will attack next.",
            section = verzik
    )
    default boolean verzikAutosTick()
    {
        return true;
    }

    @ConfigItem(
            position = 5,
            keyName = "verzikAttackCounter",
            name = "Verzik Attack Counter",
            description = "Displays Verzik's Attack Count (useful for when P2 reds as they despawn after the 7th attack).",
            section = verzik
    )
    default boolean verzikAttackCounter()
    {
        return false;
    }

    @ConfigItem(
            position = 6,
            keyName = "verzikTotalTickCounter",
            name = "Verzik Total Tick Counter",
            description = "Displays the total amount of ticks Verzik has been alive for.",
            section = verzik
    )
    default boolean verzikTotalTickCounter()
    {
        return false;
    }

    @ConfigItem(
            position = 7,
            keyName = "verzikNyloPersonalWarning",
            name = "Verzik Nylo Direct Aggro Warning",
            description = "Highlights the Nylocas that are targeting YOU and ONLY you.",
            section = verzik
    )
    default boolean verzikNyloPersonalWarning()
    {
        return true;
    }

    @ConfigItem(
            position = 8,
            keyName = "verzikNyloOtherWarning",
            name = "Verzik Nylo Indirect Aggro Warnings",
            description = "Highlights the Nylocas that are targeting OTHER players.",
            section = verzik
    )
    default boolean verzikNyloOtherWarning()
    {
        return true;
    }

    @ConfigItem(
            position = 9,
            keyName = "lightningAttackHelper",
            name = "Lightning Attack Helper",
            description = "Displays the number of attacks before a lightning ball.",
            section = verzik
    )
    default boolean lightningAttackHelper()
    {
        return false;
    }

    @ConfigItem(
            position = 10,
            keyName = "lightningAttackTick",
            name = "Lightning Attack Tick",
            description = "Displays the number of ticks before a lightning ball hits you.",
            section = verzik
    )
    default boolean lightningAttackTick()
    {
        return false;
    }

    @ConfigItem(
            position = 11,
            keyName = "verzikAttackPurpleNyloMES",
            name = "Remove Purple Nylo MES",
            description = "Removes the ability to attack the Purple nylo if you cannot poison it",
            section = verzik
    )
    default boolean purpleCrabAttackMES()
    {
        return false;
    }

    @ConfigItem(
            position = 12,
            keyName = "weaponSet",
            name = "Poison Weapons",
            description = "If a weapon is added to this set, it will NOT deprio attack on Nylocas Athanatos.",
            section = verzik
    )
    default String weaponSet()
    {
        return "12926, 12006, 22292, 12899";
    }

    @ConfigItem(
            position = 13,
            keyName = "verzikNyloExplodeAOE",
            name = "Verzik Nylo Explosion Area",
            description = "Highlights the area of explosion for the Nylocas (Personal or Indirect Warnings MUST be enabled).",
            section = verzik
    )
    default boolean verzikNyloExplodeAOE()
    {
        return true;
    }

    @ConfigItem(
            position = 14,
            keyName = "verzikDisplayTank",
            name = "Verzik Display Tank",
            description = "Highlights the tile of the player tanking to help clarify.",
            section = verzik
    )
    default boolean verzikDisplayTank()
    {
        return true;
    }

    @ConfigItem(
            position = 15,
            keyName = "verzikYellows",
            name = "Verzik Yellows Overlay",
            description = "Highlights the yellow pools and displays the amount of ticks until you can move away or tick eat.",
            section = verzik
    )
    default boolean verzikYellows()
    {
        return true;
    }

    @ConfigItem(
            position = 16,
            keyName = "verzikGreenBall",
            name = "Verzik Green Ball Tank",
            description = "Displays who the green ball is targeting.",
            section = verzik
    )
    default boolean verzikGreenBall()
    {
        return true;
    }

    @Alpha
    @ConfigItem(
            position = 17,
            keyName = "verzikGreenBallColor",
            name = "Verzik Green Ball Highlight Color",
            description = "Select a color for the Verzik's Green Ball Tile Overlay to be.",
            section = verzik
    )
    default Color verzikGreenBallColor()
    {
        return new Color(59, 140, 83, 255);
    }

    @ConfigItem(
            position = 18,
            keyName = "verzikGreenBallMarker",
            name = "Verzik Green Ball Marker",
            description = "Choose between a tile or 3-by-3 area marker.",
            section = verzik
    )
    default VERZIKBALLTILE verzikGreenBallMarker()
    {
        return VERZIKBALLTILE.TILE;
    }

    @ConfigItem(
            position = 19,
            keyName = "verzikGreenBallTick",
            name = "Verzik Green Ball Tick",
            description = "Displays the number of ticks until the green ball nukes you.",
            section = verzik
    )
    default boolean verzikGreenBallTick()
    {
        return false;
    }

    @ConfigItem(
            position = 20,
            keyName = "verzikTornado",
            name = "Verzik Personal Tornado Highlight",
            description = "Displays the tornado that is targeting you.",
            section = verzik
    )
    default boolean verzikTornado()
    {
        return true;
    }

    @ConfigItem(
            position = 21,
            keyName = "verzikPersonalTornadoOnly",
            name = "Verzik ONLY Highlight Personal",
            description = "Displays the tornado that is targeting you ONLY after it solves which one is targeting you.",
            section = verzik
    )
    default boolean verzikPersonalTornadoOnly()
    {
        return false;
    }

    @Alpha
    @ConfigItem(
            position = 22,
            keyName = "verzikTornadoColor",
            name = "Verzik Tornado Highlight Color",
            description = "Select a color for the Verzik Tornadoes Overlay to be.",
            section = verzik
    )
    default Color verzikTornadoColor()
    {
        return Color.RED;
    }

    @ConfigItem(
            position = 23,
            keyName = "verzikPoisonTileHighlight",
            name = "Verzik Poison Tile Highlight",
            description = "Highlight tile with disappearing poison",
            section = verzik
    )
    default boolean verzikPoisonTileHighlight()
    {
        return true;
    }

    @Alpha
    @ConfigItem(
            position = 24,
            keyName = "verzikPoisonTileHighlightColor",
            name = "Verzik Poison Tile Highlight Color",
            description = "Select a color for the Verzik poison tiles.",
            section = verzik
    )
    default Color verzikPoisonTileHighlightColor()
    {
        return new Color(184, 246, 196, 152);
    }



    @ConfigItem(
            position = 25,
            keyName = "specialCounter",
            name = "Special Counter",
            description = "Counts attacks until next special on P3 Verzik.",
            section = verzik
    )
    default boolean specialCounter()
    {
        return true;
    }

    @ConfigItem(
            name = "Outline Verzik on wrong prayer",
            description = "Outline the Verzik when incorrectly praying against its current attack style.",
            position = 26,
            keyName = "VerzikOverlayWrongPrayerOutline",
            section = verzik
    )
    default boolean VerzikOverlayWrongPrayerOutline()
    {
        return true;
    }

    @Range(
            min = 1,
            max = 12
    )
    @ConfigItem(
            name = "Outline width",
            description = "Change the width of the wrong prayer outline.",
            position = 27,
            keyName = "VerzikWrongPrayerOutlineWidth",
            section = verzik
    )
    default int VerzikWrongPrayerOutlineWidth()
    {
        return 2;
    }

    //Prayer helper

    @ConfigItem(
            position = 7,
            keyName = "prayerHelper",
            name = "Prayer Helper",
            description = "Display prayer indicator in the prayer tab or in the bottom right corner of the screen",
            section = tPrayer
    )
    default boolean prayerHelper()
    {
        return true;
    }

    @ConfigItem(
            position = 8,
            keyName = "descendingBoxes",
            name = "Prayer Descending Boxes",
            description = "Draws timing boxes above the prayer icons, as if you were playing Guitar Hero",
            section = tPrayer
    )
    default boolean descendingBoxes()
    {
        return false;
    }

    @ConfigItem(
            position = 9,
            keyName = "alwaysShowPrayerHelper",
            name = "Always Show Prayer Helper",
            description = "Render prayer helper at all time, even when other inventory tabs are open.",
            section = tPrayer
    )
    default boolean alwaysShowPrayerHelper()
    {
        return true;
    }

    @Alpha
    @ConfigItem(
            position = 10,
            keyName = "prayerColor",
            name = "Box Color",
            description = "Color for descending box normal",
            section = tPrayer
    )
    default Color prayerColor()
    {
        return Color.ORANGE;
    }

    @Alpha
    @ConfigItem(
            position = 11,
            keyName = "prayerColorDanger",
            name = "Box Color Danger",
            description = "Color for descending box one tick before damage",
            section = tPrayer
    )
    default Color prayerColorDanger()
    {
        return Color.RED;
    }

    @ConfigItem(
            position = 12,
            keyName = "indicateNonPriorityDescendingBoxes",
            name = "Indicate Non-Priority Boxes",
            description = "Render descending boxes for prayers that are not the priority prayer for that tick",
            section = tPrayer
    )
    default boolean indicateNonPriorityDescendingBoxes()
    {
        return true;
    }

    @ConfigItem(
            position = 13,
            keyName = "sotetsegPrayerHelper",
            name = "Sotetseg",
            description = "Render prayers during the sotetseg fight",
            section = tPrayer
    )
    default boolean sotetsegPrayerHelper()
    {
        return false;
    }

    @ConfigItem(
            position = 14,
            keyName = "verzikPrayerHelper",
            name = "Verzik",
            description = "Render prayers during the verzik fight",
            section = tPrayer
    )
    default boolean verzikPrayerHelper()
    {
        return true;
    }


    // HYDRA

    @ConfigItem(
            keyName = "hydraImmunityOutline",
            name = "Hydra immunity outline",
            description = "Overlay the hydra with a colored outline while it has immunity/not weakened.",
            position = 0,
            section = hydra
    )
    default boolean hydraImmunityOutline()
    {
        return false;
    }

    @ConfigItem(
            keyName = "fountainOutline",
            name = "Fountain occupancy outline",
            description = "Overlay fountains with a colored outline indicating if the hydra is standing on it.",
            position = 1,
            section = hydra
    )
    default boolean fountainOutline()
    {
        return false;
    }

    @ConfigItem(
            keyName = "fountainTicks",
            name = "Fountain Ticks",
            description = "Overlay fountains with the ticks until the fountain activates.",
            position = 2,
            section = hydra
    )
    default boolean fountainTicks()
    {
        return false;
    }

    @ConfigItem(
            name = "Font style",
            description = "Fountain ticks Font style can be bold, plain, or italicized.",
            position = 3,
            keyName = "fountainTicksFontStyle",
            section = hydra
    )
    default FontStyle fountainTicksFontStyle()
    {
        return FontStyle.BOLD;
    }

    @ConfigItem(
            name = "Font shadow",
            description = "Toggle fountain ticks font shadow.",
            position = 4,
            keyName = "fountainTicksFontShadow",
            section = hydra
    )
    default boolean fountainTicksFontShadow()
    {
        return true;
    }

    @Range(
            min = 12,
            max = 64
    )
    @ConfigItem(
            name = "Font size",
            description = "Adjust fountain ticks font size.",
            position = 5,
            keyName = "fountainTicksFontSize",
            section = hydra
    )
    default int fountainTicksFontSize()
    {
        return 16;
    }

    @Alpha
    @ConfigItem(
            name = "Font color",
            description = "Adjust fountain ticks font color.",
            position = 6,
            keyName = "fountainTicksFontColor",
            section = hydra
    )
    default Color fountainTicksFontColor()
    {
        return new Color(255, 255, 255, 255);
    }

    @Range(
            min = -100,
            max = 100
    )
    @ConfigItem(
            name = "Font zOffset",
            description = "Adjust the fountain ticks  Z coordinate offset.",
            position = 7,
            keyName = "fountainTicksFontZOffset",
            section = hydra
    )
    default int fountainTicksFontZOffset()
    {
        return 0;
    }

    @ConfigItem(
            keyName = "hidePrayerOnSpecial",
            name = "Hide prayer on special attack",
            description = "Hide prayer overlay during special attacks."
                    + "<br>This can help indicate when to save prayer points.",
            position = 8,
            section = hydra
    )
    default boolean hidePrayerOnSpecial()
    {
        return false;
    }

    @ConfigItem(
            keyName = "showHpUntilPhaseChange",
            name = "Show HP until phase change",
            description = "Overlay hydra with hp remaining until next phase change.",
            position = 9,
            section = hydra
    )
    default boolean showHpUntilPhaseChange()
    {
        return false;
    }

    @ConfigItem(
            name = "Font style",
            description = "Font style can be bold, plain, or italicized.",
            position = 10,
            keyName = "fontStyleHydra",
            section = hydra
    )
    default FontStyle fontStyleHydra()
    {
        return FontStyle.BOLD;
    }

    @ConfigItem(
            name = "Font shadow",
            description = "Toggle font shadow.",
            position = 11,
            keyName = "fontShadowHydra",
            section = hydra
    )
    default boolean fontShadowHydra()
    {
        return true;
    }

    @Range(
            min = 12,
            max = 64
    )
    @ConfigItem(
            name = "Font size",
            description = "Adjust font size.",
            position = 12,
            keyName = "fontSizeHydra",
            section = hydra
    )
    default int fontSizeHydra()
    {
        return 16;
    }

    @Alpha
    @ConfigItem(
            name = "Font color",
            description = "Adjust font color.",
            position = 13,
            keyName = "fontColorHydra",
            section = hydra
    )
    default Color fontColorHydra()
    {
        return new Color(255, 255, 255, 255);
    }

    @Range(
            min = -100,
            max = 100
    )
    @ConfigItem(
            name = "Font zOffset",
            description = "Adjust the Z coordinate offset.",
            position = 14,
            keyName = "fontZOffset",
            section = hydra
    )
    default int fontZOffsetHydra()
    {
        return 0;
    }

    // Special Attacks

    @ConfigItem(
            keyName = "lightningOutline",
            name = "Lightning outline",
            description = "Overlay lightning tiles with a colored outline.",
            position = 100,
            section = hydra
    )
    default boolean lightningOutline()
    {
        return false;
    }

    @Range(
            min = 1,
            max = 8
    )
    @ConfigItem(
            name = "Outline width",
            description = "Change the stroke width of the lightning tile outline.",
            position = 101,
            keyName = "lightningStroke",
            section = hydra
    )
    default int lightningStroke()
    {
        return 1;
    }

    @Alpha
    @ConfigItem(
            name = "Outline color",
            description = "Change the tile outline color of lightning.",
            position = 102,
            keyName = "lightningOutlineColor",
            section = hydra
    )
    default Color lightningOutlineColor()
    {
        return Color.CYAN;
    }

    @Alpha
    @ConfigItem(
            name = "Outline fill color",
            description = "Change the tile fill color of lightning.",
            position = 103,
            keyName = "lightningFillColor",
            section = hydra
    )
    default Color lightningFillColor()
    {
        return new Color(0, 255, 255, 30);
    }

    @ConfigItem(
            keyName = "poisonOutline",
            name = "Poison outline",
            description = "Overlay poison tiles with a colored outline.",
            position = 104,
            section = hydra
    )
    default boolean poisonOutline()
    {
        return false;
    }

    @Range(
            min = 1,
            max = 8
    )
    @ConfigItem(
            name = "Outline width",
            description = "Change the stroke width of the poison tile outline.",
            position = 105,
            keyName = "poisonStroke",
            section = hydra
    )
    default int poisonStroke()
    {
        return 1;
    }

    @Alpha
    @ConfigItem(
            keyName = "poisonOutlineColor",
            name = "Outline color",
            description = "Outline color of poison area tiles.",
            position = 106,
            section = hydra
    )
    default Color poisonOutlineColor()
    {
        return Color.RED;
    }

    @Alpha
    @ConfigItem(
            keyName = "poisonFillColor",
            name = "Outline fill color",
            description = "Fill color of poison area tiles.",
            position = 107,
            section = hydra
    )
    default Color poisonFillColor()
    {
        return new Color(255, 0, 0, 30);
    }

    // Misc

    @Alpha
    @ConfigItem(
            keyName = "safeColor",
            name = "Safe color",
            description = "Color indicating there are at least two hydra attacks pending.",
            position = 200,
            section = hydra
    )
    default Color safeColor()
    {
        return new Color(0, 150, 0, 150);
    }

    @Alpha
    @ConfigItem(
            keyName = "warningColor",
            name = "Warning color",
            description = "Color indicating there is one hydra attack pending.",
            position = 201,
            section = hydra
    )
    default Color warningColor()
    {
        return new Color(200, 150, 0, 150);
    }

    @Alpha
    @ConfigItem(
            keyName = "dangerColor",
            name = "Danger color",
            description = "Color indiciating the hydra will change attacks.",
            position = 202,
            section = hydra
    )
    default Color dangerColor()
    {
        return new Color(150, 0, 0, 150);
    }

    // God Wars
    @ConfigItem(
            position = 1,
            keyName = "prayerWidgetHelper",
            name = "Prayer Widget Helper",
            description = "Shows you which prayer to click and the time until click.",
            section = godWars
    )
    default boolean showPrayerWidgetHelper()
    {
        return false;
    }

    @ConfigItem(
            position = 2,
            keyName = "showHitSquares",
            name = "Show Hit Squares",
            description = "Shows you where the melee bosses can hit you from.",
            section = godWars
    )
    default boolean showHitSquares()
    {
        return false;
    }

    @ConfigItem(
            position = 3,
            keyName = "changeTickColor",
            name = "Change Tick Color",
            description = "If this is enabled, it will change the tick color to white" +
                    "<br> at 1 tick remaining, signaling you to swap.",
            section = godWars
    )
    default boolean changeTickColor()
    {
        return false;
    }

    @ConfigItem(
            position = 4,
            keyName = "ignoreNonAttacking",
            name = "Ignore Non-Attacking",
            description = "Ignore monsters that are not attacking you",
            section = godWars
    )
    default boolean ignoreNonAttacking()
    {
        return false;
    }

    @ConfigItem(
            position = 4,
            keyName = "guitarHeroMode",
            name = "Guitar Hero Mode",
            description = "Render \"Guitar Hero\" style prayer helper",
            section = godWars
    )
    default boolean guitarHeroMode()
    {
        return false;
    }

    @ConfigItem(
            position = 0,
            keyName = "gwd",
            name = "God Wars Dungeon",
            description = "Show tick timers for GWD Bosses. This must be enabled before you zone in.",
            section = godWars
    )
    default boolean gwd()
    {
        return true;
    }


    @ConfigItem(
            position = 5,
            keyName = "fontStyle",
            name = "Font Style",
            description = "Plain | Bold | Italics",
            section = godWars
    )
    default FontStyle fontStyleGWD()
    {
        return FontStyle.BOLD;
    }

    @Range(
            min = 1,
            max = 40
    )
    @ConfigItem(
            position = 6,
            keyName = "textSize",
            name = "Text Size",
            description = "Text Size for Timers.",
            section = godWars
    )
    default int textSizeGWD()
    {
        return 16;
    }

    @ConfigItem(
            position = 7,
            keyName = "shadows",
            name = "Shadows",
            description = "Adds Shadows to text.",
            section = godWars
    )
    default boolean shadowsGWD()
    {
        return false;
    }

    //Chambers of Xeric
    @ConfigItem(
            position = 0,
            keyName = "muttadile",
            name = "Muttadile Marker",
            description = "Places an overlay around muttadiles showing their melee range.",
            section = cox
    )
    default boolean muttadile()
    {
        return true;
    }

    @ConfigItem(
            position = 1,
            keyName = "muttaColor",
            name = "Muttadile Tile Color",
            description = "Change hit area tile color for muttadiles",
            section = cox
    )
    default Color muttaColor()
    {
        return new Color(0, 255, 99);
    }

    @ConfigItem(
            position = 10,
            keyName = "tekton",
            name = "Tekton Marker",
            description = "Places an overlay around Tekton showing his melee range.",
            section = cox
    )
    default boolean tekton()
    {
        return true;
    }

    @ConfigItem(
            position = 11,
            keyName = "tektonTickCounter",
            name = "Tekton Tick Counters",
            description = "Counts down current phase timer, and attack ticks.",
            section = cox
    )
    default boolean tektonTickCounter()
    {
        return true;
    }

    @ConfigItem(
            position = 12,
            keyName = "tektonColor",
            name = "Tekton Tile Color",
            description = "Change hit area tile color for Tekton",
            section = cox
    )
    default Color tektonColor()
    {
        return new Color(193, 255, 245);
    }

    @ConfigItem(
            position = 20,
            keyName = "guardians",
            name = "Guardians Overlay",
            description = "Places an overlay near Guardians showing safespot.",
            section = cox
    )
    default boolean guardians()
    {
        return true;
    }

    @ConfigItem(
            position = 21,
            keyName = "guardinTickCounter",
            name = "Guardians Tick Timing",
            description = "Places an overlay on Guardians showing attack tick timers.",
            section = cox
    )
    default boolean guardinTickCounter()
    {
        return true;
    }

    @ConfigItem(
            position = 22,
            keyName = "guardColor",
            name = "Guardians Tile Color",
            description = "Change safespot area tile color for Guardians",
            section = cox
    )
    default Color guardColor()
    {
        return new Color(0, 255, 99);
    }


    @ConfigItem(
            position = 30,
            keyName = "vangHighlight",
            name = "Highlight Vanguards",
            description = "Color is based on their attack style.",
            section = cox
    )
    default boolean vangHighlight()
    {
        return true;
    }

    @ConfigItem(
            position = 31,
            keyName = "vangHealth",
            name = "Show Vanguards Current HP",
            description = "This will create an infobox with vanguards current hp.",
            section = cox
    )
    default boolean vangHealth()
    {
        return true;
    }

    @ConfigItem(
            position = 40,
            keyName = "prayAgainstOlm",
            name = "Olm Show Prayer",
            description = "Shows what prayer to use during olm.",
            section = cox
    )
    default boolean prayAgainstOlm()
    {
        return true;
    }

    @Range(
            min = 40,
            max = 100
    )
    @ConfigItem(
            position = 41,
            keyName = "prayAgainstOlmSize",
            name = "Olm Prayer Size",
            description = "Change the Size of the Olm Infobox.",
            section = cox
    )
    @Units(Units.PIXELS)
    default int prayAgainstOlmSize()
    {
        return 40;
    }

    @ConfigItem(
            position = 42,
            keyName = "timers",
            name = "Olm Show Burn/Acid Timers",
            description = "Shows tick timers for burns/acids.",
            section = cox
    )
    default boolean timers()
    {
        return true;
    }

    @ConfigItem(
            position = 43,
            keyName = "burnColor",
            name = "Burn Victim Color",
            description = "Changes tile color for burn victim.",
            section = cox
    )
    default Color burnColor()
    {
        return new Color(255, 100, 0);
    }


    @ConfigItem(
            position = 44,
            keyName = "acidColor",
            name = "Acid Victim Color",
            description = "Changes tile color for acid victim.",
            section = cox
    )
    default Color acidColor()
    {
        return new Color(69, 241, 44);
    }

    @ConfigItem(
            position = 45,
            keyName = "tpOverlay",
            name = "Olm Show Teleport Overlays",
            description = "Shows Overlays for targeted teleports.",
            section = cox
    )
    default boolean tpOverlay()
    {
        return true;
    }

    @ConfigItem(
            position = 46,
            keyName = "tpColor",
            name = "Teleport Target Color",
            description = "Changes tile color for teleport target.",
            section = cox
    )
    default Color tpColor()
    {
        return new Color(193, 255, 245);
    }

    @ConfigItem(
            position = 47,
            keyName = "olmTick",
            name = "Olm Tick Counter",
            description = "Show Tick Counter on Olm",
            section = cox
    )
    default boolean olmTick()
    {
        return true;
    }

    @ConfigItem(
            position = 48,
            keyName = "olmDebug",
            name = "Olm Debug Info",
            description = "Dev tool to show info about olm",
            section = cox
    )
    default boolean olmDebug()
    {
        return false;
    }

    @ConfigItem(
            position = 49,
            keyName = "olmPShowPhase",
            name = "Olm Phase Type",
            description = "Will highlight olm depending on which phase type is active. Red=Flame Green=Acid Purple=Crystal",
            section = cox
    )
    default boolean olmPShowPhase()
    {
        return false;
    }


    @ConfigItem(
            position = 50,
            keyName = "olmSpecialColor",
            name = "Olm Special Color",
            description = "Changes color of a special on Olm's tick counter",
            section = cox
    )
    default Color olmSpecialColor()
    {
        return new Color(89, 255, 0);
    }


    @ConfigItem(
            position = 51,
            keyName = "fontStyle",
            name = "Font Style",
            description = "Bold/Italics/Plain",
            section = cox
    )
    default FontStyle fontStyleCOX()
    {
        return FontStyle.BOLD;
    }

    @Range(
            min = 9,
            max = 20
    )
    @ConfigItem(
            position = 52,
            keyName = "textSize",
            name = "Text Size",
            description = "Text Size for Timers.",
            section = cox
    )
    default int textSizeCOX()
    {
        return 14;
    }

    @ConfigItem(
            position = 53,
            keyName = "shadows",
            name = "Shadows",
            description = "Adds Shadows to text.",
            section = cox
    )
    default boolean shadowsCOX()
    {
        return true;
    }

    @ConfigItem(
            position = 60,
            keyName = "fourToOne",
            name = "4:1 helper",
            description = "Marks tiles for 4:1 rotation",
            section = cox
    )
    default boolean fourToOne()
    {
        return true;
    }

    @ConfigItem(
            position = 61,
            keyName = "solo",
            name = "4:1 solo only",
            description = "Only activate 4:1 in Solo's",
            section = cox
    )
    default boolean solo()
    {
        return true;
    }

    @ConfigItem(
            position = 62,
            keyName = "weaponSetCOX",
            name = "Melee hand weapons",
            description = "If a weapon is added to this set, it will activate 4:1 tiles if this weapon is equiped",
            section = cox
    )
    default String weaponSetCOX()
    {
        return "22978, 12006, 11889";
    }

    @ConfigItem(
            name = "Outline Olm on wrong prayer",
            description = "Outline the Olm when incorrectly praying against its current attack style. Don't use in combination with phase type.",
            position = 63,
            keyName = "OlmOverlayWrongPrayerOutline",
            section = cox
    )
    default boolean OlmOverlayWrongPrayerOutline() {
        return false;
    }


    //Zulrah

    @ConfigItem(
            name = "Font Type",
            keyName = "fontTypeZulrah",
            description = "Configure the font for the plugin overlays to use",
            position = 500,
            section = zulrah
    )
    default FontType fontTypeZulrah()
    {
        return FontType.SMALL;
    }

    @ConfigItem(
            name = "Text Outline",
            keyName = "textOutlineZulrah",
            description = "Use an outline around text instead of a text shadow",
            position = 501,
            section = zulrah
    )
    default boolean textOutlineZulrah()
    {
        return false;
    }

    @ConfigItem(
            name = "Outline Width",
            keyName = "outlineWidthZulrah",
            description = "Configures the stroke for the plugin polygons",
            position = 502,
            section = zulrah
    )
    @Range(
            max = 3,
            min = 1
    )
    @Units("px")
    default int outlineWidthZulrah()
    {
        return 2;
    }

    @ConfigItem(
            name = "Fill Alpha",
            keyName = "fillAlphaZulrah",
            description = "Configures the fill intesity for the plugin polygons",
            position = 503,
            section = zulrah

    )
    @Range(
            max = 255,
            min = 1
    )
    @Units("%")
    default int fillAlphaZulrah()
    {
        return 15;
    }

    @ConfigItem(
            name = "Phase Tick Counter",
            keyName = "phaseTickCounterZulrah",
            description = "Displays a tick counter on Zulrah showing how long until the next phase",
            position = 0,
            section = zulrah
    )
    default boolean phaseTickCounterZulrah()
    {
        return false;
    }

    @ConfigItem(
            name = "Attack Tick Counter",
            keyName = "attackTickCounterZulrah",
            description = "Displays a attack tick counter on Zulrah showing how long until the next auto attack",
            position = 0,
            section = zulrah
    )
    default boolean attackTickCounterZulrah()
    {
        return false;
    }

    @ConfigItem(
            name = "Tick Counter Colors",
            keyName = "tickCounterColorsZulrah",
            description = "Configure the color for the Zulrah tick counters",
            position = 2,
            section = zulrah
    )
    @Alpha
    default Color tickCounterColorsZulrah()
    {
        return Color.WHITE;
    }

    @ConfigItem(
            name = "Total Tick Counter (InfoBox)",
            keyName = "totalTickCounterZulrah",
            description = "Displays a total tick counter infobox showing how long Zulrah has been alive for in ticks",
            position = 3,
            section = zulrah
    )
    default boolean totalTickCounterZulrah()
    {
        return false;
    }

    @ConfigItem(
            name = "Display Zulrah's Tile",
            keyName = "displayZulrahTile",
            description = "Highlights Zulrah's current tile in a 5x5",
            position = 4,
            section = zulrah
    )
    default boolean displayZulrahTile()
    {
        return false;
    }

    @ConfigItem(
            name = "Zulrah's Tile Color",
            keyName = "zulrahTileColor",
            description = "Configures the color for Zulrah's tile highlight",
            position = 5,
            section = zulrah
    )
    @Alpha
    default Color zulrahTileColor()
    {
        return Color.LIGHT_GRAY;
    }

    @ConfigItem(
            name = "Prayer Helper",
            keyName = "prayerHelperZulrah",
            description = "Displays an overlay showing the correct prayer to use for the entirity of the Zulrah fight<br>Changes color dependent on whether or not you're praying correctly or not",
            position = 10,
            section = zulrah
    )
    default boolean prayerHelperZulrah()
    {
        return true;
    }

    @ConfigItem(
            name = "Prayer Marker",
            keyName = "prayerMarkerZulrah",
            description = "Marks the correct prayer to use in the prayer book to use for the entirity of the Zulrah fight<br>Changes color dependent on whether or not you're praying correctly or not",
            position = 11,
            section = zulrah
    )
    default boolean prayerMarkerZulrah()
    {
        return true;
    }

    @ConfigItem(
            name = "Prayer Conservation",
            keyName = "prayerConservationZulrah",
            description = "Displays text over your head showing when it's safe to turn off your overheads<br>Overlay gets displayed when Zulrah is not actively targeting you and your overheads are on",
            position = 12,
            section = zulrah
    )
    default boolean prayerConservationZulrah()
    {
        return false;
    }

    @ConfigItem(
            name = "Stand Locations",
            keyName = "standLocationsZulrah",
            description = "Highlights the tiles to stand on for the current and next Zulrah phase",
            position = 13,
            section = zulrah
    )
    default boolean standLocationsZulrah()
    {
        return true;
    }

    @ConfigItem(
            name = "Stand/Next Tile Color",
            keyName = "standAndNextColorZulrah",
            description = "Configure the color for the stand/next GROUPED tile and text",
            position = 14,
            section = zulrah
    )
    @Alpha
    default Color standAndNextTileColorZulrah()
    {
        return Color.GRAY;
    }

    @ConfigItem(
            name = "Stand Tile Color",
            keyName = "standTileColorZulrah",
            description = "Configure the color for the current stand tile and text",
            position = 15,
            section = zulrah
    )
    @Alpha
    default Color standTileColorZulrah()
    {
        return Color.CYAN;
    }

    @ConfigItem(
            name = "Next Tile Color",
            keyName = "nextStandTileColorZulrah",
            description = "Configure the color for the next stand tile and text",
            position = 16,
            section = zulrah
    )
    @Alpha
    default Color nextTileColorZulrah()
    {
        return Color.GREEN;
    }

    @ConfigItem(
            name = "Stall Locations",
            keyName = "stallLocationsZulrah",
            description = "Highlights the tile to pillar stall a Zulrah phase if it supports it",
            position = 17,
            section = zulrah
    )
    default boolean stallLocationsZulrah()
    {
        return false;
    }

    @ConfigItem(
            name = "Stall Tile Color",
            keyName = "stallTileColorZulrah",
            description = "Configures the color for the stall tile and text",
            position = 18,
            section = zulrah
    )
    @Alpha
    default Color stallTileColorZulrah()
    {
        return Color.PINK;
    }

    @ConfigItem(
            name = "Display Type",
            keyName = "phaseDisplayTypeZulrah",
            description = "Overlay: Displays Zulrah's phases details on an overlay<br>Tile: Displays Zulrah's phases details on tiles",
            position = 30,
            section = zulrah
    )
    default DisplayType phaseDisplayTypeZulrah()
    {
        return DisplayType.BOTH;
    }

    @ConfigItem(
            name = "Display Mode",
            keyName = "phaseDisplayModeZulrah",
            description = "Current: Only displays the current Zulrah phase<br>Next: Only displays the next Zulrah phase<br>",
            position = 31,
            section = zulrah
    )
    default DisplayMode phaseDisplayModeZulrah()
    {
        return DisplayMode.BOTH;
    }

    @ConfigItem(
            name = "Rotation Name",
            keyName = "phaseRotationNameZulrah",
            description = "Requires: Display Type ('Overlay' or 'Both')<br>Displays text above InfoBox overlay showing the rotation name or unidentified",
            position = 32,
            section = zulrah
    )
    default boolean phaseRotationNameZulrah()
    {
        return false;
    }

    @ConfigItem(
            name = "Hats",
            keyName = "phaseHatsZulrah",
            description = "Displays Zulrah's skill type as an icon on the tile overlay",
            position = 33,
            section = zulrah
    )
    default boolean phaseHatsZulrah()
    {
        return true;
    }

    @ConfigItem(
            name = "Tags",
            keyName = "phaseTagsZulrah",
            description = "Tags each Zulrah phase on the tile overlay with:<br>[Current] = Current Zulrah phase<br>[Next] = Definite next Zulrah phase<br>[P. Next] = Potentially Zulrah's next phase",
            position = 34,
            section = zulrah
    )
    default boolean phaseTagsZulrah()
    {
        return true;
    }

    @ConfigItem(
            name = "Instance Timer",
            keyName = "instanceTimerZulrah",
            description = "Displays an overlay showing how long Zulrah has been alive in minutes:seconds format<br>Timer resets on Zulrah death and/or leaving of the instance of any fashion",
            position = 40,
            section = zulrah
    )
    default boolean instanceTimerZulrah()
    {
        return false;
    }

    @ConfigItem(
            name = "Snakeling",
            keyName = "snakelingSetting",
            description = "Remove Att. Op.: Removes the 'Attack' option from all the active Snakelings<br>Entity Hider: Hides all the active Snakelings",
            position = 41,
            section = zulrah
    )
    default SnakelingSettings snakelingSetting()
    {
        return SnakelingSettings.OFF;
    }

    @ConfigItem(
            name = "Snakeling Hotkey",
            keyName = "snakelingMesHotkey",
            description = "Override the Snakeling MES to show attack options while hotkey is pressed",
            position = 42,
            section = zulrah
    )
    default Keybind snakelingMesHotkey()
    {
        return Keybind.NOT_SET;
    }

    @ConfigItem(
            name = "Toxic Clouds",
            keyName = "displayToxicClouds",
            description = "Highlights the Toxic Clouds and displays their time until despawn",
            position = 43,
            section = zulrah
    )
    default boolean displayToxicClouds()
    {
        return false;
    }

    @ConfigItem(
            name = "Toxic Clouds Color",
            keyName = "toxicCloudColor",
            description = "Configures the color of the Toxic Cloud highlight",
            position = 44,
            section = zulrah
    )
    @Alpha
    default Color toxicCloudsColor()
    {
        return Color.GREEN;
    }

    @ConfigItem(
            name = "Projectiles",
            keyName = "displayProjectilesZulrah",
            description = "Displays when and where the Snakeling/Toxic Clouds projectile will approximately land",
            position = 45,
            section = zulrah
    )
    default boolean displayProjectilesZulrah()
    {
        return false;
    }

    @ConfigItem(
            name = "Projectiles Color",
            keyName = "projectilesColorzulrah",
            description = "Configures the color of the Projectiles highlight",
            position = 46,
            section = zulrah
    )
    @Alpha
    default Color projectilesColorzulrah()
    {
        return Color.LIGHT_GRAY;
    }

    //Inferno

    @ConfigItem(
            position = 0,
            keyName = "prayerDisplayMode",
            name = "Prayer Display Mode",
            description = "Display prayer indicator in the prayer tab or in the bottom right corner of the screen",
            section = inferno
    )
    default InfernoPrayerDisplayMode prayerDisplayMode()
    {
        return InfernoPrayerDisplayMode.BOTH;
    }

    @ConfigItem(
            position = 1,
            keyName = "indicateWhenPrayingCorrectly",
            name = "Indicate When Praying Correctly",
            description = "Indicate the correct prayer, even if you are already praying that prayer",
            section = inferno
    )
    default boolean indicateWhenPrayingCorrectly()
    {
        return true;
    }

    @ConfigItem(
            position = 2,
            keyName = "descendingBoxesInferno",
            name = "Descending Boxes",
            description = "Draws timing boxes above the prayer icons, as if you were playing Piano Tiles",
            section = inferno
    )
    default boolean descendingBoxesInferno()
    {
        return true;
    }

    @ConfigItem(
            position = 3,
            keyName = "indicateNonPriorityDescendingBoxesInferno",
            name = "Indicate Non-Priority Boxes",
            description = "Render descending boxes for prayers that are not the priority prayer for that tick",
            section = inferno
    )
    default boolean indicateNonPriorityDescendingBoxesInferno()
    {
        return true;
    }

    @ConfigItem(
            position = 4,
            keyName = "alwaysShowPrayerHelperInferno",
            name = "Always Show Prayer Helper",
            description = "Render prayer helper at all time, even when other inventory tabs are open.",
            section = inferno
    )
    default boolean alwaysShowPrayerHelperInferno()
    {
        return false;
    }

    @ConfigItem(
            position = 4,
            keyName = "safespotDisplayMode",
            name = "Tile Safespots",
            description = "Indicate safespots on the ground: safespot (white), pray melee (red), pray range (green), pray magic (blue) and combinations of those",
            section = inferno
    )
    default InfernoSafespotDisplayMode safespotDisplayMode()
    {
        return InfernoSafespotDisplayMode.AREA;
    }

    @ConfigItem(
            position = 5,
            keyName = "safespotsCheckSize",
            name = "Tile Safespots Check Size",
            description = "The size of the area around the player that should be checked for safespots (SIZE x SIZE area)",
            section = inferno
    )
    default int safespotsCheckSize()
    {
        return 6;
    }

    @ConfigItem(
            position = 6,
            keyName = "indicateNonSafespotted",
            name = "Non-safespotted NPC's Overlay",
            description = "Red overlay for NPC's that can attack you",
            section = inferno
    )
    default boolean indicateNonSafespotted()
    {
        return false;
    }

    @ConfigItem(
            position = 7,
            keyName = "indicateTemporarySafespotted",
            name = "Temporary safespotted NPC's Overlay",
            description = "Orange overlay for NPC's that have to move to attack you",
            section = inferno
    )
    default boolean indicateTemporarySafespotted()
    {
        return false;
    }

    @ConfigItem(
            position = 8,
            keyName = "indicateSafespotted",
            name = "Safespotted NPC's Overlay",
            description = "Green overlay for NPC's that are safespotted (can't attack you)",
            section = inferno
    )
    default boolean indicateSafespotted()
    {
        return false;
    }

    @ConfigItem(
            position = 20,
            keyName = "waveDisplay",
            name = "Wave Display",
            description = "Shows monsters that will spawn on the selected wave(s).",
            section = inferno
    )
    default InfernoWaveDisplayMode waveDisplay()
    {
        return InfernoWaveDisplayMode.BOTH;
    }

    @ConfigItem(
            position = 21,
            keyName = "npcNaming",
            name = "NPC Naming",
            description = "Simple (ex: Bat) or Complex (ex: Jal-MejRah) NPC naming",
            section = inferno
    )
    default InfernoNamingDisplayMode npcNaming()
    {
        return InfernoNamingDisplayMode.SIMPLE;
    }

    @ConfigItem(
            position = 22,
            keyName = "npcLevels",
            name = "NPC Levels",
            description = "Show the combat level of the NPC next to their name",
            section = inferno
    )
    default boolean npcLevels()
    {
        return false;
    }

    @ConfigItem(
            position = 23,
            keyName = "getWaveOverlayHeaderColor",
            name = "Wave Header",
            description = "Color for Wave Header",
            section = inferno
    )
    default Color getWaveOverlayHeaderColor()
    {
        return Color.ORANGE;
    }

    @ConfigItem(
            position = 24,
            keyName = "getWaveTextColor",
            name = "Wave Text Color",
            description = "Color for Wave Texts",
            section = inferno
    )
    default Color getWaveTextColor()
    {
        return Color.WHITE;
    }

    @ConfigItem(
            position = 30,
            keyName = "indicateObstacles",
            name = "Obstacles",
            description = "Indicate obstacles that NPC's cannot pass through",
            section = inferno
    )
    default boolean indicateObstacles()
    {
        return false;
    }

    @ConfigItem(
            position = 31,
            keyName = "spawnTimerInfobox",
            name = "Spawn Timer Infobox",
            description = "Display an Infobox that times spawn sets during Zuk fight.",
            section = inferno
    )
    default boolean spawnTimerInfobox()
    {
        return false;
    }

    @ConfigItem(
            position = 40,
            keyName = "indicateNibblers",
            name = "Indicate Nibblers",
            description = "Indicates nibblers that are alive",
            section = inferno
    )
    default boolean indicateNibblers()
    {
        return true;
    }

    @ConfigItem(
            position = 41,
            keyName = "indicateCentralNibbler",
            name = "Indicate Central Nibbler",
            description = "Indicate the most central nibbler. If multiple nibblers will freeze the same amount of other nibblers, " +
                    "the nibbler closest to the player's location is chosen.",
            section = inferno
    )
    default boolean indicateCentralNibbler()
    {
        return true;
    }

    @ConfigItem(
            position = 50,
            keyName = "prayerBat",
            name = "Prayer Helper Bat",
            description = "Indicate the correct prayer when this NPC attacks",
            section = inferno
    )
    default boolean prayerBat()
    {
        return true;
    }

    @ConfigItem(
            position = 51,
            keyName = "ticksOnNpcBat",
            name = "Ticks on NPC Bat",
            description = "Draws the amount of ticks before an NPC is going to attack on the NPC",
            section = inferno
    )
    default boolean ticksOnNpcBat()
    {
        return true;
    }

    @ConfigItem(
            position = 52,
            keyName = "safespotsBat",
            name = "Safespots Bat",
            description = "Enable or disable safespot calculation for this specific NPC. 'Tile Safespots' in the 'Safespots' category needs to be turned on for this to take effect.",
            section = inferno
    )
    default boolean safespotsBat()
    {
        return true;
    }

    @ConfigItem(
            position = 53,
            keyName = "indicateNpcPositionBat",
            name = "Indicate Main Tile Bat",
            description = "Indicate the main tile for multi-tile NPC's. This tile is used for and pathfinding.",
            section = inferno
    )
    default boolean indicateNpcPositionBat()
    {
        return false;
    }

    @ConfigItem(
            position = 60,
            keyName = "prayerBlob",
            name = "Prayer Helper Blob",
            description = "Indicate the correct prayer when this NPC attacks",
            section = inferno
    )
    default boolean prayerBlob()
    {
        return true;
    }

    @ConfigItem(
            position = 61,
            keyName = "indicateBlobDetectionTick",
            name = "Indicate Blob Detection Tick",
            description = "Show a prayer indicator (default: magic) for the tick on which the blob will detect prayer",
            section = inferno
    )
    default boolean indicateBlobDetectionTick()
    {
        return true;
    }

    @ConfigItem(
            position = 62,
            keyName = "indicateBlobDeathLocation",
            name = "Indicate Blob Death Location",
            description = "Highlight the death tiles with a tick countdown until mini-blobs spawn",
            section = inferno
    )
    default boolean indicateBlobDeathLocation()
    {
        return false;
    }

    @ConfigItem(
            position = 63,
            keyName = "getBlobDeathLocationColor",
            name = "Blob Death Color",
            description = "Color for blob death location outline",
            section = inferno
    )
    default Color getBlobDeathLocationColor()
    {
        return Color.ORANGE;
    }

    @ConfigItem(
            position = 64,
            keyName = "blobDeathLocationFade",
            name = "Fade Tile Blob",
            description = "Fades the death tile for a smoother transition.",
            section = inferno
    )
    default boolean blobDeathLocationFade()
    {
        return true;
    }


    @ConfigItem(
            position = 65,
            keyName = "ticksOnNpcBlob",
            name = "Ticks on NPC  Blob",
            description = "Draws the amount of ticks before an NPC is going to attack on the NPC",
            section = inferno
    )
    default boolean ticksOnNpcBlob()
    {
        return true;
    }

    @ConfigItem(
            position = 66,
            keyName = "safespotsBlob",
            name = "Safespots Blob",
            description = "Enable or disable safespot calculation for this specific NPC. 'Tile Safespots' in the 'Safespots' category needs to be turned on for this to take effect.",
            section = inferno
    )
    default boolean safespotsBlob()
    {
        return true;
    }

    @ConfigItem(
            position = 67,
            keyName = "indicateNpcPositionBlob",
            name = "Indicate Main Tile Blob",
            description = "Indicate the main tile for multi-tile NPC's. This tile is used for pathfinding.",
            section = inferno
    )
    default boolean indicateNpcPositionBlob()
    {
        return false;
    }


    @ConfigItem(
            position = 70,
            keyName = "prayerMeleer",
            name = "Prayer Helper Meleer",
            description = "Indicate the correct prayer when this NPC attacks",
            section = inferno
    )
    default boolean prayerMeleer()
    {
        return true;
    }

    @ConfigItem(
            position = 71,
            keyName = "ticksOnNpcMeleer",
            name = "Ticks on NPC Meleer",
            description = "Draws the amount of ticks before an NPC is going to attack on the NPC",
            section = inferno
    )
    default boolean ticksOnNpcMeleer()
    {
        return true;
    }

    @ConfigItem(
            position = 72,
            keyName = "ticksOnNpcMeleerDig",
            name = "Dig Timer",
            description = "Draws the amount of ticks before the melee will begin the dig animation.\n" +
                    "The amount of time is currently unknown. Plugin will count up to 50 \n" +
                    "then count up with the danger overlay. This should give a general idea of the time. \n" +
                    "Once more data can be collected this can be improved",
            section = inferno
    )
    default boolean ticksOnNpcMeleerDig()
    {
        return false;
    }

    @Range(
            min = 1,
            max = 50
    )
    @ConfigItem(
            position = 73,
            keyName = "digTimerThreshold",
            name = "Tick Draw Threshold",
            description = "Number at which the dig timer should be drawn",
            section = inferno
    )
    default int digTimerThreshold()
    {
        return 20;
    }

    @Range(
            min = 30,
            max = 70
    )
    @ConfigItem(
            position = 74,
            keyName = "digTimerDangerThreshold",
            name = "Tick Danger Threshold",
            description = "Number at which the dig timer should be dangerous",
            section = inferno
    )
    default int digTimerDangerThreshold()
    {
        return 50;
    }


    @ConfigItem(
            position = 75,
            keyName = "getMeleeDigSafeColor",
            name = "Dig Safe Color",
            description = "Color for melee when can not dig",
            section = inferno
    )
    default Color getMeleeDigSafeColor()
    {
        return Color.LIGHT_GRAY;
    }

    @ConfigItem(
            position = 76,
            keyName = "getMeleeDigDangerColor",
            name = "Dig Danger Color",
            description = "Color for melee when it can dig",
            section = inferno
    )
    default Color getMeleeDigDangerColor()
    {
        return Color.ORANGE;
    }

    @Range(min = 10,
            max = 48)
    @ConfigItem(
            position = 77,
            keyName = "getMeleeDigFontSize",
            name = "Font size Meleer",
            description = "Font size to use under the melee",
            section = inferno
    )
    default int getMeleeDigFontSize()
    {
        return 11;
    }

    @ConfigItem(
            position = 78,
            keyName = "safespotsMeleer",
            name = "Safespots Meleer",
            description = "Enable or disable safespot calculation for this specific NPC. 'Tile Safespots' in the 'Safespots' category needs to be turned on for this to take effect.",
            section = inferno
    )
    default boolean safespotsMeleer()
    {
        return true;
    }

    @ConfigItem(
            position = 79,
            keyName = "indicateNpcPositionMeleer",
            name = "Indicate Main Tile Meleer",
            description = "Indicate the main tile for multi-tile NPC's. This tile is used for pathfinding.",
            section = inferno
    )
    default boolean indicateNpcPositionMeleer()
    {
        return false;
    }

    @ConfigItem(
            position = 90,
            keyName = "prayerRanger",
            name = "Prayer Helper Ranger",
            description = "Indicate the correct prayer when this NPC attacks",
            section = inferno
    )
    default boolean prayerRanger()
    {
        return true;
    }

    @ConfigItem(
            position = 91,
            keyName = "ticksOnNpcRanger",
            name = "Ticks on NPC Ranger",
            description = "Draws the amount of ticks before an NPC is going to attack on the NPC",
            section = inferno
    )
    default boolean ticksOnNpcRanger()
    {
        return true;
    }

    @ConfigItem(
            position = 92,
            keyName = "safespotsRanger",
            name = "Safespots Ranger",
            description = "Enable or disable safespot calculation for this specific NPC. 'Tile Safespots' in the 'Safespots' category needs to be turned on for this to take effect.",
            section = inferno
    )
    default boolean safespotsRanger()
    {
        return true;
    }

    @ConfigItem(
            position = 93,
            keyName = "indicateNpcPositionRanger",
            name = "Indicate Main Tile Ranger",
            description = "Indicate the main tile for multi-tile NPC's. This tile is used for pathfinding.",
            section = inferno
    )
    default boolean indicateNpcPositionRanger()
    {
        return false;
    }

    @ConfigItem(
            position = 100,
            keyName = "prayerMage",
            name = "Prayer Helper Ranger",
            description = "Indicate the correct prayer when this NPC attacks",
            section = inferno
    )
    default boolean prayerMage()
    {
        return true;
    }

    @ConfigItem(
            position = 101,
            keyName = "ticksOnNpcMage",
            name = "Ticks on NPC Mage",
            description = "Draws the amount of ticks before an NPC is going to attack on the NPC",
            section = inferno
    )
    default boolean ticksOnNpcMage()
    {
        return true;
    }

    @ConfigItem(
            position = 102,
            keyName = "safespotsMage",
            name = "Safespots Mage",
            description = "Enable or disable safespot calculation for this specific NPC. 'Tile Safespots' in the 'Safespots' category needs to be turned on for this to take effect.",
            section = inferno
    )
    default boolean safespotsMage()
    {
        return true;
    }

    @ConfigItem(
            position = 103,
            keyName = "indicateNpcPositionMage",
            name = "Indicate Main Tile Mage",
            description = "Indicate the main tile for multi-tile NPC's. This tile is used for pathfinding.",
            section = inferno
    )
    default boolean indicateNpcPositionMage()
    {
        return false;
    }

    @ConfigItem(
            position = 110,
            keyName = "prayerHealersJad",
            name = "Prayer Helper Jad Healer",
            description = "Indicate the correct prayer when this NPC attacks",
            section = inferno
    )
    default boolean prayerHealerJad()
    {
        return false;
    }

    @ConfigItem(
            position = 111,
            keyName = "ticksOnNpcHealersJad",
            name = "Ticks on NPC Jad Healer",
            description = "Draws the amount of ticks before an NPC is going to attack on the NPC",
            section = inferno
    )
    default boolean ticksOnNpcHealerJad()
    {
        return false;
    }

    @ConfigItem(
            position = 112,
            keyName = "safespotsHealersJad",
            name = "Safespots Jad Healer",
            description = "Enable or disable safespot calculation for this specific NPC. 'Tile Safespots' in the 'Safespots' category needs to be turned on for this to take effect.",
            section = inferno
    )
    default boolean safespotsHealerJad()
    {
        return true;
    }

    @ConfigItem(
            position = 113,
            keyName = "indicateActiveHealersJad",
            name = "Indicate Active Jad Healers",
            description = "Indicate healers that are still healing Jad",
            section = inferno
    )
    default boolean indicateActiveHealerJad()
    {
        return true;
    }

    @ConfigItem(
            position = 120,
            keyName = "prayerJad",
            name = "Prayer Helper Jad",
            description = "Indicate the correct prayer when this NPC attacks",
            section = inferno
    )
    default boolean prayerJad()
    {
        return true;
    }

    @ConfigItem(
            position = 121,
            keyName = "ticksOnNpcJad",
            name = "Ticks on NPC Jad",
            description = "Draws the amount of ticks before an NPC is going to attack on the NPC",
            section = inferno
    )
    default boolean ticksOnNpcJad()
    {
        return true;
    }

    @ConfigItem(
            position = 122,
            keyName = "safespotsJad",
            name = "Safespots (Melee Range Only) Jad",
            description = "Enable or disable safespot calculation for this specific NPC. 'Tile Safespots' in the 'Safespots' category needs to be turned on for this to take effect.",
            section = inferno
    )
    default boolean safespotsJad()
    {
        return true;
    }

    @ConfigItem(
            position = 130,
            keyName = "indicateActiveHealersZuk",
            name = "Indicate Active Zuk Healers (UNTESTED)",
            description = "Indicate healers that are still healing Zuk",
            section = inferno
    )
    default boolean indicateActiveHealerZuk()
    {
        return true;
    }

    @ConfigItem(
            position = 140,
            keyName = "ticksOnNpcZuk",
            name = "Ticks on NPC Zuk",
            description = "Draws the amount of ticks before an NPC is going to attack on the NPC",
            section = inferno
    )
    default boolean ticksOnNpcZuk()
    {
        return true;
    }

    @ConfigItem(
            position = 141,
            keyName = "safespotsZukShieldBeforeHealers",
            name = "Safespots Zuk(Before Healers) ",
            description = "Indicate the zuk shield safespots. 'Tile Safespots' in the 'Safespots' category needs to be turned on for this to take effect. Shield must go back and forth at least 1 time before the predict option will work.",
            section = inferno
    )
    default InfernoZukShieldDisplayMode safespotsZukShieldBeforeHealers()
    {
        return InfernoZukShieldDisplayMode.PREDICT;
    }

    @ConfigItem(
            position = 142,
            keyName = "safespotsZukShieldAfterHealers",
            name = "Safespots Zuk(After Healers)",
            description = "Indicate the zuk shield safespots. 'Tile Safespots' in the 'Safespots' category needs to be turned on for this to take effect.",
            section = inferno
    )
    default InfernoZukShieldDisplayMode safespotsZukShieldAfterHealers()
    {
        return InfernoZukShieldDisplayMode.LIVE;
    }

    @ConfigItem(
            position = 143,
            keyName = "hideTzKalZukDeath",
            name = "Hide On Death",
            description = "Hide TzKal-Zuk on death animation",
            section = inferno
    )
    default boolean hideZukDeath()
    {
        return false;
    }

    @ConfigItem(
            position = 144,
            keyName = "ticksOnNpcZukShield",
            name = "Ticks on Zuk Shield",
            description = "Draws the amount of ticks before Zuk attacks on the floating shield",
            section = inferno
    )
    default boolean ticksOnNpcZukShield()
    {
        return false;
    }

    //Cerberus

    @ConfigItem(
            keyName = "drawGhostTiles",
            name = "Show ghost tiles",
            description = "Overlay ghost tiles with respective colors and attack timers.",
            position = 0,
            section = cerberus
    )
    default boolean drawGhostTiles()
    {
        return false;
    }

    @ConfigItem(
            keyName = "calculateAutoAttackPrayer",
            name = "Calculate auto attack prayer",
            description = "Calculate prayer for auto attacks based on your equipment defensive bonuses."
                    + "<br>Default is Protect from Magic.",
            position = 1,
            section = cerberus
    )
    default boolean calculateAutoAttackPrayer()
    {
        return false;
    }

    // Current Attack Section

    @ConfigItem(
            keyName = "showCurrentAttackCerberus",
            name = "Show current attack",
            description = "Overlay the current attack in a separate infobox.",
            position = 10,
            section = cerberus
    )
    default boolean showCurrentAttackCerberus()
    {
        return false;
    }

    @ConfigItem(
            keyName = "showCurrentAttackTimerCerberus",
            name = "Show current attack timer",
            description = "Display a timer on the current attack infobox.",
            position = 11,
            section = cerberus
    )
    default boolean showCurrentAttackTimerCerberus()
    {
        return false;
    }

    // Upcoming Attacks Section

    @ConfigItem(
            keyName = "showUpcomingAttacksCerberus",
            name = "Show upcoming attacks",
            description = "Overlay upcoming attacks in stacked info boxes.",
            position = 20,
            section = cerberus
    )
    default boolean showUpcomingAttacksCerberus()
    {
        return false;
    }

    @Range(
            min = 1,
            max = 10
    )
    @ConfigItem(
            keyName = "amountOfAttacksShownCerberus",
            name = "# of attacks",
            description = "Number of upcoming attacks to render.",
            position = 21,
            section = cerberus
    )
    default int amountOfAttacksShown()
    {
        return 4;
    }

    @ConfigItem(
            keyName = "reverseUpcomingAttacksCerberus",
            name = "Reverse order",
            description = "Reverse the order of the upcoming attacks.",
            position = 22,
            section = cerberus
    )
    default boolean reverseUpcomingAttacksCerberus()
    {
        return false;
    }

    @ConfigItem(
            keyName = "showUpcomingAttackNumberCerberus",
            name = "Show attack number",
            description = "Display the attack pattern number on each upcoming attack." +
                    "<br>See http://pastebin.com/hWCvantS",
            position = 23,
            section = cerberus
    )
    default boolean showUpcomingAttackNumberCerberus()
    {
        return false;
    }

    @ConfigItem(
            keyName = "upcomingAttacksOrientationCerberus",
            name = "Upcoming attacks orientation",
            description = "Display upcoming attacks vertically or horizontally.",
            position = 24,
            section = cerberus
    )
    default InfoBoxOrientation upcomingAttacksOrientationCerberus()
    {
        return InfoBoxOrientation.VERTICAL;
    }

    @ConfigItem(
            keyName = "infoBoxComponentSizeCerberus",
            name = "Info box size",
            description = "Size of the upcoming attacks infoboxes.",
            position = 25,
            section = cerberus
    )
    default InfoBoxComponentSize infoBoxComponentSizeCerberus()
    {
        return InfoBoxComponentSize.SMALL;
    }

    // Guitar Hero Mode Section

    @ConfigItem(
            keyName = "guitarHeroModeCerberus",
            name = "Guitar Hero mode",
            description = "Display descending boxes indicating the correct prayer for the current attack.",
            position = 30,
            section = cerberus
    )
    default boolean guitarHeroModeCerberus()
    {
        return false;
    }

    @Range(
            min = 1,
            max = 10
    )
    @ConfigItem(
            keyName = "guitarHeroTicksCerberus",
            name = "# of ticks",
            description = "The number of ticks, before the upcoming current attack, to render.",
            position = 31,
            section = cerberus
    )
    default int guitarHeroTicksCerberus()
    {
        return 4;
    }



    @Alpha
    @ConfigItem(
            position = 0,
            keyName = "trueTileColorToa",
            name = "True tile color",
            description = "Sets color of npc highlights",
            section = toaGen
    )
    default Color trueTileColorToa()
    {
        return Color.BLACK;
    }

    @Alpha
    @ConfigItem(
            position = 1,
            keyName = "trueTileFillColorToa",
            name = "True Tile Fill Color",
            description = "Sets the fill color of npc highlights",
            section = toaGen
    )
    default Color trueTileFillColorToa()
    {
        return new Color(0, 255, 255, 20);
    }

    @Alpha
    @ConfigItem(
            position = 3,
            keyName = "dangerTileColorToa",
            name = "Danger tile color",
            description = "Sets color of dangerTiles",
            section = toaGen
    )
    default Color dangerTileColorToa()
    {
        return Color.RED;
    }

    @Alpha
    @ConfigItem(
            position = 4,
            keyName = "dangerTileFillColorToa",
            name = "Danger Tile Fill Color",
            description = "Sets the fill color of npc highlights",
            section = toaGen
    )
    default Color dangerTileFillColorToa()
    {
        return new Color(255, 0, 0, 20);
    }

    @ConfigItem(
            position = 1000,
            keyName = "toaDebug",
            name = "Toa Debug Info",
            description = "Dev tool to show info about Toa",
            section = toaGen
    )
    default boolean toaDebug()
    {
        return false;
    }


    @ConfigItem(
            name = "Akkha: Wrong prayer",
            description = "Outline the Akkha when incorrectly praying against its current attack style.",
            position = 1,
            keyName = "AkkhaOverlayWrongPrayerOutline",
            section = toaHet
    )
    default boolean AkkhaOverlayWrongPrayerOutline() {
        return true;
    }

    @ConfigItem(
            name = "Akkha: Elements sequence",
            description = "Renders the tick counter sequence of elements during Akkha.",
            position = 2,
            keyName = "AkkhaSequence",
            section = toaHet
    )
    default boolean AkkhaSequence() {
        return true;
    }

    @ConfigItem(
            position = 3,
            keyName = "orbTrueTile",
            name = "Orb True Tile ",
            description = "Highlights true tile of orbs.",
            section = toaHet
    )
    default boolean orbTrueTile()
    {
        return true;
    }


    @ConfigItem(
            name = "Orbs: Hide orbs",
            description = "Unrender the orbs",
            position = 4,
            keyName = "HideOrbs",
            section = toaHet
    )
    default boolean hideOrbs() {
        return false;
    }

    @ConfigItem(
            position = 0,
            keyName = "waveTrueTile",
            name = "Wave true tile",
            description = "Highlights true tile of wave.",
            section = toaCrondis
    )
    default boolean waveTrueTile()
    {
        return true;
    }

    @ConfigItem(
            position = 1,
            keyName = "boulderTrueTile",
            name = "Highlight Boulder",
            description = "Highlights true tile of boulder.",
            section = toaCrondis
    )
    default boolean boulderTrueTile()
    {
        return true;
    }

    @ConfigItem(
            position = 2,
            keyName = "poisonTileToa",
            name = "Highlight Poison",
            description = "Highlights tile of poison.",
            section = toaCrondis
    )
    default boolean poisonTileToa()
    {
        return true;
    }

    @ConfigItem(
            position = 0,
            keyName = "kephriAttackRadius",
            name = "Kephri Attack Radius",
            description = "Highlights tiles of Kephri's attack.",
            section = toaScarabas
    )
    default boolean kephriAttackRadius()
    {
        return true;
    }

    @ConfigItem(
            position = 1,
            keyName = "fliesOnCharacter",
            name = "Flies attack",
            description = "Highlights flies",
            section = toaScarabas
    )
    default boolean fliesOnCharacter()
    {
        return true;
    }

    @ConfigItem(
            position = 2,
            keyName = "hideUnattackableSwams",
            name = "Hide unattackable swarms",
            description = "Hide unattackable swarms",
            section = toaScarabas
    )
    default boolean hideUnattackableSwams()
    {
        return true;
    }

    @ConfigItem(
            position = 10,
            keyName = "scarabasAttackRadius",
            name = "Boulder Attack Radius",
            description = "Highlights tiles of Boulder attack.",
            section = toaScarabas
    )
    default boolean scarabasAttackRadius()
    {
        return true;
    }

    @ConfigItem(
            position = 11,
            keyName = "scarabasPuzzleSolver",
            name = "Solve matching puzzles",
            description = "Solve matching puzzles.",
            section = toaScarabas
    )
    default boolean scarabasPuzzleSolver()
    {
        return true;
    }

    @ConfigItem(
            position = 0,
            keyName = "bouldersTrueTileBaba",
            name = "Boulder true tile",
            description = "Highlights true tile of boulders.",
            section = toaApmeken
    )
    default boolean bouldersTrueTileBaba()
    {
        return true;
    }

    @ConfigItem(
            position = 1,
            keyName = "shockwaveRadiusToa",
            name = "Shockwave Radius",
            description = "Highlights the shockwave Radius",
            section = toaApmeken
    )
    default boolean shockwaveRadiusToa()
    {
        return true;
    }

    @ConfigItem(
            position = 2,
            keyName = "boulderDangerToa",
            name = "Falling boulders",
            description = "Highlights the falling boulders",
            section = toaApmeken
    )
    default boolean boulderDangerToa()
    {
        return true;
    }

    @ConfigItem(
            position = 3,
            keyName = "bananasToa",
            name = "Highlight Bananas",
            description = "Highlights the bananas",
            section = toaApmeken
    )
    default boolean bananasToa()
    {
        return true;
    }

    @ConfigItem(
            position = 4,
            keyName = "sacrophagusToa",
            name = "Sacrophagus flame",
            description = "Highlights the sacrophagus flame",
            section = toaApmeken
    )
    default boolean sacrophagusToa()
    {
        return true;
    }

    @ConfigItem(
            position = 5,
            keyName = "ticksrenderOnBaba",
            name = "Ticks on Baba",
            description = "Renders ticks till next attack on baba.",
            section = toaApmeken
    )
    default boolean babaRenderTicks() {return true;}

    @ConfigItem(
            position = 11,
            keyName = "prayerHelperToa",
            name = "Prayer Helper",
            description = "Display prayer indicator in the prayer tab or in the bottom right corner of the screen",
            section = toaPrayer
    )
    default boolean prayerHelperToa()
    {
        return true;
    }

    @ConfigItem(
            position = 13,
            keyName = "descendingBoxesToa",
            name = "Prayer Descending Boxes",
            description = "Draws timing boxes above the prayer icons, as if you were playing Guitar Hero",
            section = toaPrayer
    )
    default boolean descendingBoxesToa()
    {
        return true;
    }

    @ConfigItem(
            position = 14,
            keyName = "alwaysShowPrayerHelperToa",
            name = "Always Show Prayer Helper",
            description = "Render prayer helper at all time, even when other inventory tabs are open.",
            section = toaPrayer
    )
    default boolean alwaysShowPrayerHelperToa()
    {
        return true;
    }

    @Alpha
    @ConfigItem(
            position = 15,
            keyName = "prayerColorToa",
            name = "Box Color",
            description = "Color for descending box normal",
            section = toaPrayer
    )
    default Color prayerColorToa()
    {
        return Color.ORANGE;
    }

    @Alpha
    @ConfigItem(
            position = 16,
            keyName = "prayerColorDangerToa",
            name = "Box Color Danger",
            description = "Color for descending box one tick before damage",
            section = toaPrayer
    )
    default Color prayerColorDangerToa()
    {
        return Color.RED;
    }

    @ConfigItem(
            position = 17,
            keyName = "indicateNonPriorityDescendingBoxesToa",
            name = "Indicate Non-Priority Boxes",
            description = "Render descending boxes for prayers that are not the priority prayer for that tick",
            section = toaPrayer
    )
    default boolean indicateNonPriorityDescendingBoxesToa()
    {
        return true;
    }

    @ConfigItem(
            position = 12,
            keyName = "prayerInfoboxToa",
            name = "Prayer Infobox",
            description = "Renders a prayer infobox for attacks in ToA",
            section = toaPrayer
    )
    default boolean prayerInfoboxToa()
    {
        return true;
    }

    @ConfigItem(
            position = 0,
            keyName = "zebakPrayerHelper",
            name = "Zebak prayer helper",
            description = "Render prayers during the Zebak fight",
            section = toaPrayer
    )
    default boolean zebakPrayerHelper()
    {
        return true;
    }

    @ConfigItem(
            position = 0,
            keyName = "Wardenballticks",
            name = "Waden Ballticks onplayer",
            description = "Shows ticks till ball hits player",
            section = toaWardens
    )
    default boolean wardenObeliskBallTicks()
    {
        return true;
    }

    @Alpha
    @ConfigItem(
            position = 1,
            keyName = "boulderoutline",
            name = "Boulder falling P3 tile outline",
            description = "",
            section = toaWardens
    )
    default Color wardenBoulderOutline()
    {
        return new Color(255, 0, 0, 20);
    }

    @Alpha
    @ConfigItem(
            position = 2,
            keyName = "boulderFill",
            name = "Boulder falling P3 tile fill",
            description = "",
            section = toaWardens
    )

    default Color wadernBoulderFill()
    {
        return new Color(255, 0, 0, 20);
    }

    //ENUM
    enum BLOATTIMEDOWN
    {
        COUNTUP,
        COUNTDOWN
    }

    enum NYLOTIMEALIVE
    {
        COUNTUP,
        COUNTDOWN
    }

    enum EXPLOSIVENYLORENDERSTYLE
    {
        TILE,
        RECOLOR_TICK
    }

    enum AGGRESSIVENYLORENDERSTYLE
    {
        TILE,
        HULL
    }

    enum XARPUS_EXHUMED_COUNT
    {
        OFF,
        DOWN,
        UP;
    }

    enum XARPUS_LINE_OF_SIGHT
    {
        OFF,
        SQUARE,
        MELEE_TILES;
    }

    enum VERZIKBALLTILE
    {
        TILE,
        AREA
    }

    public static enum SnakelingSettings
    {
        OFF("Off"),
        MES("Remove Att. Op."),
        ENTITY("Entity Hider");

        private final String name;

        public String toString()
        {
            return this.name;
        }

        private SnakelingSettings(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return this.name;
        }
    }

    public static enum DisplayMode
    {
        CURRENT("Current"),
        NEXT("Next"),
        BOTH("Both");

        private final String name;

        public String toString()
        {
            return this.name;
        }

        private DisplayMode(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return this.name;
        }
    }

    public static enum DisplayType
    {
        OFF("Off"),
        OVERLAY("Overlay"),
        TILE("Tile"),
        BOTH("Both");

        private final String name;

        public String toString()
        {
            return this.name;
        }

        private DisplayType(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return this.name;
        }
    }

    @Getter
    @RequiredArgsConstructor
    enum InfoBoxOrientation
    {
        HORIZONTAL("Horizontal layout", ComponentOrientation.HORIZONTAL),
        VERTICAL("Vertical layout", ComponentOrientation.VERTICAL);

        private final String name;
        private final ComponentOrientation orientation;

        @Override
        public String toString()
        {
            return name;
        }
    }

    @Getter
    @RequiredArgsConstructor
    enum InfoBoxComponentSize
    {
        SMALL("Small boxes", 40), MEDIUM("Medium boxes", 60), LARGE("Large boxes", 80);

        private final String name;
        private final int size;

        @Override
        public String toString()
        {
            return name;
        }
    }
}
