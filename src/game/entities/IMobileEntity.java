package game.entities;
import utilities.Point;
/**
 * @author Vitaly Nechayuk
 * @id 324359926
 * @campus Beer Sheva
 * -----------------------
 *
 * MobileEntity interface
 */
public interface IMobileEntity {
    /**
     * Moves mobile entity.
     * @param friction (double)
     */
    void move(double friction);

    /**
     * getLocation
     * @return (Point)
     */
    Point getLocation();
}
