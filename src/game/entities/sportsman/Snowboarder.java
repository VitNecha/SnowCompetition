package game.entities.sportsman;
import game.competition.Competitor;
import game.enums.CompetitorColor;
import game.enums.Discipline;
import game.enums.Gender;

import java.awt.*;

/**
 * @author Vitaly Nechayuk
 * @id 324359926
 * @campus Beer Sheva
 * -----------------------
 *
 *  Snowboarder class
 *  Represents Snowboarder (winter sportsman extension)
 */
public class Snowboarder extends WinterSportsman{
    /**
     * Constructor
     * @param name (String)
     * @param age (double)
     * @param gender (Gender enum)
     * @param acceleration (double)
     * @param maxSpeed (double)
     * @param discipline (Discipline enum)
     */
    public Snowboarder(String name, double age, Gender gender, double acceleration, double maxSpeed, double moveFriction, double arenaLength, Discipline discipline, CompetitorColor color){
        super(name,age,gender,acceleration,maxSpeed,moveFriction,arenaLength,discipline,color);
    }
    @Override
    public String toString(){
        return ("Snowboarder " + this.getName());
    }
}
