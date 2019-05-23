package game.competition;
import game.entities.IMobileEntity;
import java.util.Observable;
import java.util.Observer;
/**
 * @author Vitaly Nechayuk
 * @id 324359926
 * @campus Beer Sheva
 * -----------------------
 *
 *  Competitor interface
 */
public interface Competitor extends IMobileEntity,Runnable{
    /**
     * Race initialization
     */
    void initRace();
}
