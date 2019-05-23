package game.competition;
import game.arena.Arena;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;
/**
 * @author Vitaly Nechayuk
 * @id 324359926
 * @campus Beer Sheva
 * -----------------------
 *
 *  Snowboard competition class (winter competition extension)
 */
public class SnowboardCompetition extends WinterCompetition{
    /**
     * Constructor
     * @param arena (Arena)
     * @param maxCompetitors (int)
     * @param discipline (Discipline enum)
     * @param league (League enum)
     * @param gender (Gender enum)
     */
    public SnowboardCompetition(Arena arena, int maxCompetitors, Discipline discipline, League league, Gender gender){
        super(arena,maxCompetitors,discipline,league,gender);
    }
    @Override
    public String toString(){
        return "SnowboardCompetition";
    }
}
