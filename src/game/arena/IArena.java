package game.arena;
import game.entities.IMobileEntity;
/**
 * @author Vitaly Nechayuk
 * @id 324359926
 * @campus Beer Sheva
 * -----------------------
 *
 *  Arena interface
 */
public interface IArena {
    /**
     * getFriction
     * @return (double)
     */
    double getFriction();

    /**
     * Checks if mobile entity is finished the arena
     * @param me
     * @return true/false
     */
    boolean isFinished(IMobileEntity me);
}
