package game.entities;
import utilities.Point;
/**
 * @author Vitaly Nechayuk
 * @id 324359926
 * @campus Beer Sheva
 * -----------------------
 *
 * Abstract mobile entity class
 * Represents mobile entity in two dimensional space (x,y coordinates) with attributes of
 * maximum speed, acceleration, and current speed of the entity.
 */
public abstract class  MobileEntity extends Entity{
    private double maxSpeed;
    private double acceleration;
    private double speed;

    /**
     * Constructor
     * @param acceleration (double)
     * @param maxSpeed (double)
     */
    public MobileEntity(double acceleration, double maxSpeed){
        super();
        try {
            setSpeed(0);
            setMaxSpeed(maxSpeed);
            setAcceleration(acceleration);
        }
        catch (Exception e){
            e.printStackTrace(System.out);
            throw e;
        }
    }

    /**
     * getAcceleration
     * @return acceleration (double)
     */
    public double getAcceleration() { return acceleration; }
    /**
     * getMaxSpeed
     * @return maxSpeed (double)
     */
    public double getMaxSpeed() { return maxSpeed; }

    /**
     * getSpeed
     * @return speed (double)
     */
    public double getSpeed() { return speed; }

    /**
     * setAcceleration
     * @param acceleration (double)
     */
    public void setAcceleration(double acceleration) {
        if (acceleration < 0) throw new IllegalArgumentException("Acceleration cannot be negative");
        else this.acceleration = acceleration;
    }

    /**
     * setMaxSpeed
     * @param maxSpeed (double)
     */
    public void setMaxSpeed(double maxSpeed) {
        if (maxSpeed <= 0) throw new IllegalArgumentException("<Maximum speed cannot be negative");
        else this.maxSpeed = maxSpeed;
    }

    /**
     * setSpeed
     * @param speed (double)
     */
    public void setSpeed(double speed) {
        if (speed < 0) throw new IllegalArgumentException("<double> [speed] argument cannot be negative/zero");
        else this.speed = speed;
    }

    /**
     * Moves MobileEntity according to current speed, acceleration and friction.
     * @param friction (double)
     */
    public synchronized void move(double friction){
        if (speed + (acceleration * (1-friction)) >  maxSpeed) speed = maxSpeed;
        else speed = speed + (acceleration * (1-friction));
        for (double i = 0.0; i < speed; i = i + 0.1) {
            super.setLocation(new Point(super.getLocation().getX() + 0.1, 0));
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Return MobileEntity's current location.
     * @return (location)
     */
    public Point getLocation(){
        return super.getLocation();
    }

    @Override
    public String toString() {
        return super.toString() + "\nMax speed: " + this.getMaxSpeed() + "\nAcceleration: " + this.getAcceleration() + "\nSpeed: " + this.getSpeed();
    }
}
