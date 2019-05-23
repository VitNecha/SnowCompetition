package game.entities.sportsman;
import game.competition.Competitor;
import game.entities.MobileEntity;
import game.enums.CompetitorColor;
import game.enums.Gender;
import utilities.Point;

import java.util.Random;

/**
 * @author Vitaly Nechayuk
 * @id 324359926
 * @campus Beer Sheva
 * -----------------------
 *
 * Abstract sportsman class
 * Represents sportsman (extends MobileEntity) with attributes of name, age and gender
 * of sportsman/woman.
 */
public abstract class Sportsman extends MobileEntity implements Competitor{
    private String name;
    private double age;
    private Gender gender;
    private double moveFriction = 0;
    private double arenaLength = 0;
    private double positionInTheCompetition = 0;
    private int sportsmanID = 0;
    private CompetitorColor color = CompetitorColor.Red;
    private SportsmanState state;
    private int physicalBreakdownTime = 1000;
    private boolean injuryValidated = false;

    /**
     * Constructor
     * @param name (String)
     * @param age (double)
     * @param gender (Gender enum)
     * @param acceleration (double)
     * @param maxSpeed (double)
     */
    public Sportsman(String name, double age, Gender gender, double acceleration, double maxSpeed, double moveFriction,double arenaLength,CompetitorColor color){
        super(acceleration,maxSpeed);
        try {
            setName(name);
            setAge(age);
            setGender(gender);
            setMoveFriction(moveFriction);
            setArenaLength(arenaLength);
            setColor(color);
            setState(new ActiveState());
            determinePhysicalBreakdownTime();
        }
        catch (Exception e){
            e.printStackTrace(System.out);
            throw e;
        }
    }

    //Set state method
    public void setState(SportsmanState state) { this.state = state; }

    //Set injury validation
    public void setInjuryValidated(boolean injuryValidated) { this.injuryValidated = injuryValidated; }

    //Is injury validated?
    public boolean isInjuryValidated() { return injuryValidated; }

    //Injure method
    public void injure(){
        this.setState(new InjuredState());
        this.setSpeed((this.getSpeed() / 10));
        this.setAcceleration(0.1);
        if (!isInjuryValidated()) validateInjury();
        setChanged();
        notifyObservers();
    }

    //Set finished
    public void setFinished(){
        this.setState(new FinishedState());
        this.setAcceleration(0);
        setChanged();
        notifyObservers();
    }

    //Get competitor's current state
    public String getCurrentState() {return state.getState(); }

    //Get competitor's color
    public CompetitorColor getColor() {
        return color;
    }

    //Set competitor's color
    public void setColor(CompetitorColor color) {
        this.color = color;
    }

    //Set arena length
    public void setArenaLength(double arenaLength) {
        if (arenaLength < 0) throw new IllegalArgumentException("Arena length cannot be negative");
        else this.arenaLength = arenaLength;
    }

    //Set physical breakdown time
    public void setPhysicalBreakdownTime(int physicalBreakDownTime) { this.physicalBreakdownTime = physicalBreakDownTime; }

    //Get physical breakdown time
    public int getPhysicalBreakdownTime() { return physicalBreakdownTime; }

    //Determine physical breakdown time (randomly)
    public void determinePhysicalBreakdownTime(){
        Random rand = new Random();
        int temp = (rand.nextInt(5));
        if (temp == 0){
            setPhysicalBreakdownTime(rand.nextInt(100) + 1);
        }
    }

    //Set disqualified
    public void disqualify(){
        this.state = new DisqualifiedState();
        this.setLocation(new Point(this.getArenaLength(),this.getLocation().getY()));
        this.setSpeed(0);
        this.setAcceleration(0);
        setChanged();
        notifyObservers();
    }

    //Injury validation
    public void validateInjury(){
        Random rand = new Random();
        if (getCurrentState() == "Injured"){
            this.setInjuryValidated(true);
            if ((rand.nextInt(3)) == 0) this.disqualify();
        }
    }

    //Get sportsman's ID
    public double getSportsmanID() { return sportsmanID; }

    //ID generator
    public void generateID(){
        int rand = 0;
        for (int i = 0; i < 5; i++) rand = rand * 10 + (new Random()).nextInt(8) + 1;
        this.sportsmanID = rand * 10 + (int) getPositionInTheCompetition();
    }

    //Get competitor's position in the competition
    public double getPositionInTheCompetition() {
        return positionInTheCompetition;
    }

    //Set competitor's position in the competition
    public void setPositionInTheCompetition(double positionInTheCompetition) {
        if (positionInTheCompetition < 0) throw new IllegalArgumentException("Competition position cannot be negative");
        else this.positionInTheCompetition = positionInTheCompetition;
    }

    //Get arena length
    public double getArenaLength() {
        return arenaLength;
    }

    //Set move friction
    public void setMoveFriction(double moveFriction) {
        if (moveFriction < 0) throw new IllegalArgumentException("Friction cannot be negative");
        else this.moveFriction = moveFriction;
    }

    //Get move friction
    public double getMoveFriction() { return moveFriction; }

    /**
     * getAge
     * @return age (double)
     */
    public double getAge() { return age; }

    /**
     * setAge
     * @param age (double)
     */
    public void setAge(double age) {
        if (age <= 0) throw new IllegalArgumentException("<double> [age] argument cannot be negative/zero");
        else this.age = age;
    }

    /**
     * getGender
     * @return gender (Gender enum)
     */
    public Gender getGender() { return gender;}

    /**
     * setGender
     * @param gender (Gender enum)
     */
    public void setGender(Gender gender) {this.gender = gender; }

    /**
     * getName
     * @return name (String)
     */
    public String getName() { return name; }

    /**
     * setName
     * @param name (String)
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("<String> [name] argument cannot be null/empty");
        else this.name = new String(name);
    }


    @Override
    public void run(){
        while (this.getLocation().getX() < this.getArenaLength()) {
            try {
                Thread.sleep(100);
                this.move(this.moveFriction);
                if (this.getLocation().getX() > this.getArenaLength()) ((Point)this.getLocation()).setX(this.getArenaLength());
                this.setChanged();
                this.notifyObservers();
            } catch (Exception e) { e.printStackTrace(System.out); }
        }
    }
    @Override
    public String toString(){
        return "Name: " + this.getName() +  "\nAge: " + this.getAge() + "\nGender: " + this.getGender().toString() + super.toString();
    }
}
