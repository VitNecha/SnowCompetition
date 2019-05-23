package game.competition;
import game.arena.Arena;
import game.entities.sportsman.Skier;
import game.entities.sportsman.Snowboarder;
import game.entities.sportsman.WinterSportsman;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;
/**
 * @author Vitaly Nechayuk
 * @id 324359926
 * @campus Beer Sheva
 * -----------------------
 *
 * Winter competition class
 * Represents winter competition that extends competition class with additional attributes of discipline, league
 * and gender of the competition
 */
public class WinterCompetition extends Competition{
    private Discipline discipline;
    private League league;
    private Gender gender;

    public WinterCompetition(){ }
    /**
     * Constructor
     * @param arena (Arena)
     * @param maxCompetitors (int)
     * @param discipline (Discipline enum)
     * @param league (League enum)
     * @param gender (Gender enum)
     */

    public WinterCompetition(Arena arena, int maxCompetitors, Discipline discipline, League league, Gender gender){
        super(arena,maxCompetitors);
        try {
            setDiscipline(discipline);
            setGender(gender);
            setLeague(league);
        }
        catch (Exception e){
            e.printStackTrace(System.out);
            throw e;
        }
    }

    /**
     * setDiscipline
     * @param discipline (Discipline enum)
     */
    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    /**
     * setLeague
     * @param league (League enum)
     */
    public void setLeague(League league) { this.league = league; }

    /**
     * setGender
     * @param gender (Gender enum)
     */
    public void setGender(Gender gender) { this.gender = gender; }

    /**
     * getDiscipline
     * @return discipline (Discipline enum)
     */
    public Discipline getDiscipline() { return discipline; }

    /**
     * getLeague
     * @return league (League enum)
     */
    public League getLeague() { return league; }

    /**
     * getGender
     * @return gender (Gender enum)
     */
    public Gender getGender() { return gender; }

    @Override
    public boolean isValidCompetitor(Competitor competitor){
        if (competitor == null) throw new IllegalArgumentException("<Competitor> [competitor] cannot be  null");
        else if (((WinterSportsman) competitor).getDiscipline() == null
                || ((WinterSportsman) competitor).getGender() == null) throw new IllegalArgumentException("<Gender> and <Discipline>  cannot be null");
        else return ((competitor instanceof WinterSportsman)
                && ((this instanceof SkiCompetition && competitor instanceof Skier)
                    || (this instanceof  SnowboardCompetition && competitor instanceof Snowboarder))
                && this.discipline.equals(((WinterSportsman) competitor).getDiscipline())
                && this.gender.equals(((WinterSportsman) competitor).getGender())
                && league.isInLeague(((WinterSportsman) competitor).getAge()));
    }
}
