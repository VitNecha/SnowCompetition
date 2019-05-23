package game.entities.sportsman;
import game.competition.Competitor;
import game.enums.CompetitorColor;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;
import utilities.Point;

import java.awt.*;

/**
 * @author Vitaly Nechayuk
 * @id 324359926
 * @campus Beer Sheva
 * -----------------------
 * Winter sportsman class
 * Represents winter sportsman that extends sportsman class with additional attribute of
 * discipline of the sportsman
 */
public class WinterSportsman extends Sportsman implements Cloneable, IWinterSportsman{
    private Discipline discipline;

    /**
     * Constructor
     * @param name (String)
     * @param age (double)
     * @param gender (Gender enum)
     * @param acceleration (double)
     * @param maxSpeed (double)
     * @param discipline (Discipline enum)
     */
    public WinterSportsman(String name, double age, Gender gender, double acceleration, double maxSpeed, double moveFriction, double arenaLength, Discipline discipline, CompetitorColor color){
        super(name,age,gender,acceleration + League.calcAccelerationBonus(age),maxSpeed,moveFriction,arenaLength,color);
        setDiscipline(discipline);
    }

    /**
     * setDiscipline
     * @param discipline (Discipline enum)
     */
    public void setDiscipline(Discipline discipline) { this.discipline = discipline; }

    /**
     *
     * @return discipline (Discipline enum)
     */
    public Discipline getDiscipline() { return discipline; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Race initialization - Sets WinterSportsman at (0,0).
     */
    public void initRace(){ this.setLocation(new Point(0,this.getPositionInTheCompetition())); }
    @Override
    public String toString(){
        return "Discipline: " + this.getDiscipline().toString() + "\n" + super.toString();
    }
}
