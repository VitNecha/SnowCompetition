package game.entities;
import utilities.Point;
import java.util.Observable;
import java.util.Observer;
/**
 * @author Vitaly Nechayuk
 * @id 324359926
 * @campus Beer Sheva
 * -----------------------
 *
 * Abstract Entity class
 * Represents an entity in two dimensional space (x, y coordinates).
 */
public abstract class Entity extends Observable implements IMobileEntity{
    private Point location;
    /**
     * Default constructor - (x: 0,y: 0)
     */
    public Entity(){ setLocation(new Point(0,0)); }

    /**
     * Constructor
     * @param location (Point)
     */
    public Entity(Point location){
        try {
            setLocation(location);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace(System.out);
            throw e;
        }
    }

    /**
     * Copy-Constructor
     * @param other (Entity)
     */
    public Entity(Entity other){
        setLocation(other.getLocation());
    }

    /**
     * setLocation
     * @param location (Point)
     */
    public void setLocation(Point location) {
        if (location == null) throw new IllegalArgumentException("<Point> argument cannot be null");
        else this.location = new Point(location.getX(),location.getY());
    }

    /**
     * getLocation
     * @return location (Point)
     */
    public Point getLocation() {return location;}

    @Override
    public String toString(){
        return "Location: " + location.toString();
    }
}
