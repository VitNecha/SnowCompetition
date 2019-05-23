package game.arena;
import game.entities.IMobileEntity;
import game.enums.SnowSurface;
import game.enums.WeatherCondition;
/**
 * @author Vitaly Nechayuk
 * @id 324359926
 * @campus Beer Sheva
 * -----------------------
 *
 *  Winter arena class
 *  Represents a winter arena for competition with attributes of length, surface and condition.
 */
public abstract class Arena implements IArena{
    double length;
    SnowSurface surface;
    WeatherCondition condition;

    /**
     * Constructor
     * @param length (double)
     * @param surface (SnowSurface surface)
     * @param condition (WeatherCondition)
     */
    public Arena(double length, SnowSurface surface, WeatherCondition condition){
        try {
            setLength(length);
            setSurface(surface);
            setCondition(condition);
        }
        catch (Exception e){
            e.printStackTrace(System.out);
            throw e;
        }
    }

    /**
     * setLength
     * @param length (double)
     */
    public void setLength(double length) {
        if (length < 700 || length > 900) throw new IllegalArgumentException("Arena length must be 700 - 900");
        else this.length = length;
    }

    /**
     * getLength
     * @return length (double)
     */
    public double getLength() { return length; }

    /**
     * setSurface
     * @param surface (SnowSurface enum)
     */
    public void setSurface(SnowSurface surface) { this.surface = surface; }

    /**
     * getSurface
     * @return surface (SnowSurface enum)
     */
    public SnowSurface getSurface() { return surface; }

    /**
     * setCondition
     * @param condition (WeatherCondition)
     */
    public void setCondition(WeatherCondition condition) { this.condition = condition; }

    /**
     * getCondition
     * @return condition (WeatherCondition enum)
     */
    public WeatherCondition getCondition() { return condition; }

    /**
     * getFriction
     * @return (SnowSurface enum)
     */
    public double getFriction() { return surface.getFriction(); }

    /**
     * Checks if <IMobileEntity> [me] finished the current arena.
     * @param me (IMobileEntity)
     * @return true/false
     */
    public boolean isFinished(IMobileEntity me) { return me.getLocation().getX() >= this.length ; }
}
