package com.cheating.coxhelper.FourToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.coords.WorldPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class FourToOneConstant {
    private static final int MAX_TICKS = 15;

    @AllArgsConstructor
    @Getter
    public enum TileLocation
    {
        //East tiles olm DIFFICULT
        RotateHeadMiddleEast(WorldPoint.fromRegion(12889, 37,45,0), 3, 2, 0, true),
        FirstAttackEast(WorldPoint.fromRegion(12889, 37,41,0), 4,4, 1, true), // Hit on Specials:3
        RunFarRightEast(WorldPoint.fromRegion(12889, 36,38,0), 4,2, 2, true), // Run  far right to dodge specials
        SecondHitEast(WorldPoint.fromRegion(12889, 37,38,0), 1,4, 3, true), // Second hit on Nauto:3
        WalkAfterSecondEast(WorldPoint.fromRegion(12889, 37,36,0),1,2, 4, true), //Walk one tick from 3rd hit location
        DodgeFlameEast(WorldPoint.fromRegion(12889, 36,38,0),2,4, 5, true), //Dodge in case of flame wall
        ThirdAttackEast(WorldPoint.fromRegion(12889, 37,38,0),2,3, 6, true), //Hit on Null:3
        FourthAttackWalkEast(WorldPoint.fromRegion(12889, 36,41,0),2,1, 7, true), //Dodge in case of flame wall
        FourthAttackEast(WorldPoint.fromRegion(12889, 37,41,0),3,3, 8, true), //Hit on Sauto:3

        //West tiles olm DIFFICULT
        RotateHeadMiddleWest(WorldPoint.fromRegion(12889, 28,43,0), 3, 2, 0, false),
        FirstAttackWest(WorldPoint.fromRegion(12889, 28,47,0), 4,4, 1, false), // Hit on Specials:3
        RunFarRightWest(WorldPoint.fromRegion(12889, 29,50,0), 4,2, 2, false), // Run  far right to dodge specials
        SecondHitWest(WorldPoint.fromRegion(12889, 28,50,0), 1,4, 3, false), // Second hit on Nauto:3
        WalkAfterSecondWest(WorldPoint.fromRegion(12889, 28,52,0),1,2, 4, false), //Walk one tick from 3rd hit location
        DodgeFlameWest(WorldPoint.fromRegion(12889, 29,50,0),2,4, 5, false), //Dodge in case of flame wall
        ThirdAttackWest(WorldPoint.fromRegion(12889, 28,50,0),2,3, 6, false), //Hit on Null:3
        FourthAttackWalkWest(WorldPoint.fromRegion(12889, 29,47,0),2,1, 7, false), //Dodge in case of flame wall
        FourthAttackWest(WorldPoint.fromRegion(12889, 28,47,0),3,3, 8, false); //Hit on Sauto:3


        private final WorldPoint point;

        //1 = Nauto, 2 = Null, 3 = Sauto, 4 = special
        private final int nextAttack;
        private final int ticksTillAttack;
        private final int rotationId;
        private boolean east;
    }


    public Collection<ColorTileMarker> getTiles(int nextAttack, int ticksTillAttack, boolean isEast){
        ArrayList<ColorTileMarker> points = new ArrayList<>();
        int currentTickInCycle = (nextAttack-1) * 4 + (4-ticksTillAttack);
        TileLocation nextTile = nextTileLocation(currentTickInCycle, isEast);

        for (TileLocation loc : TileLocation.values()) {
            int ticksTillLocation = ticksTillLocation(loc, currentTickInCycle);
            if (nextTile == loc){
                //Make next tile blue
                points.add(new ColorTileMarker(loc.point, Color.CYAN, String.valueOf(ticksTillLocation), ticksTillLocation));
            } else if (loc.isEast() == isEast){
                //Make all other tiles orange
                points.add(new ColorTileMarker(loc.point, Color.ORANGE, String.valueOf(ticksTillLocation), ticksTillLocation));
            }
        }

        //Create a list we can cycle through while removing items from the main list.
        ArrayList<ColorTileMarker> filterList = new ArrayList<>(points);

        //Check if tiles are in the same location and only keep the one with the lowest tick counter.
        for (ColorTileMarker tileMarker : filterList){
            for (ColorTileMarker compareTile : filterList){
                //Check if a tile marker has the same Worldpoint as another tile marker and tile with an higher tick counter.
                if (tileMarker.getWorldPoint().distanceTo(compareTile.getWorldPoint()) == 0 && tileMarker.getTicks() > compareTile.getTicks()){
                    for (int i = points.size() - 1; i >= 0; i--) {
                        //if worldpoint & ticks is equal to compare tile, remove it from the list.
                        if (points.get(i).getWorldPoint().distanceTo(compareTile.getWorldPoint()) == 0 && points.get(i).getTicks() == tileMarker.getTicks()) {
                            points.remove(i);
                        }
                    }
                }
            }
        }

        return points;
    }

    private TileLocation nextTileLocation(int currentTickInCycle, boolean isEast){
        int ticksUntilNextTile = MAX_TICKS+1;
        TileLocation nextLocation = null;

        for (TileLocation tileLocation : TileLocation.values()){
            if (ticksTillLocation(tileLocation, currentTickInCycle) < ticksUntilNextTile && tileLocation.isEast() == isEast) {
                ticksUntilNextTile = ticksTillLocation(tileLocation, currentTickInCycle);
                nextLocation = tileLocation;
            }
        }

        return nextLocation;
    }

    private int ticksTillLocation(TileLocation tileLocation, int currentTickInCycle){
        int tickLocation = (tileLocation.getNextAttack()-1)*4+(4- tileLocation.ticksTillAttack);

        if (tickLocation > currentTickInCycle){
            return (tickLocation - currentTickInCycle);
        } else {
            return (MAX_TICKS - currentTickInCycle + 1) + tickLocation;
        }
    }
}
