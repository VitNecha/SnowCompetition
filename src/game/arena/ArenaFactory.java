package game.arena;

import game.enums.SnowSurface;
import game.enums.WeatherCondition;

public class ArenaFactory {
    IArena newArena = null;

    public IArena getNewArena(String arenaType, double length, SnowSurface surface, WeatherCondition condition){
        if (arenaType == "Summer") newArena = new SummerArena(length,surface,condition);
        else if (arenaType == "Winter") newArena = new WinterArena(length,surface,condition);
        return newArena;
    }
}
