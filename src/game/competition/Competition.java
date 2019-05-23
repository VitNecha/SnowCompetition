package game.competition;
import game.arena.IArena;
import game.entities.IMobileEntity;
import game.entities.sportsman.*;
import game.enums.CompetitorColor;

import java.util.*;

/**
 * @author Vitaly Nechayuk
 * @id 324359926
 * @campus Beer Sheva
 * -----------------------
 *
 *  Skier class
 *  Represents Skier (winter sportsman extension)
 */
public abstract class Competition extends Observable implements Observer, CompetitionPlan{
    private ArrayList<Competitor> activeCompetitors;
    private ArrayList<Competitor> finishedCompetitors;
    private ArrayList<Competitor> disqualifiedCompetitors;
    private IArena arena;
    private int maxCompetitors;
    private Stopper stopper;

    public Competition(){
        activeCompetitors = new ArrayList<Competitor>();
        finishedCompetitors = new ArrayList<Competitor>();
        disqualifiedCompetitors = new ArrayList<Competitor>();
    }

    /**
     * Constructor
     * @param arena (IArena)
     * @param maxCompetitors (int)
     */
    public Competition(IArena arena, int maxCompetitors){
        try {
            activeCompetitors = new ArrayList<Competitor>();
            finishedCompetitors = new ArrayList<Competitor>();
            disqualifiedCompetitors = new ArrayList<Competitor>();
            setArena(arena);
            setMaxCompetitors(maxCompetitors);
        }
        catch (Exception e){
            e.printStackTrace(System.out);
            throw e;
        }
    }

    private class Stopper implements Runnable{
        private int seconds = 0;
        private boolean running;

        public int getSeconds() { return seconds; }

        public void setRunning(boolean running) { this.running = running;}

        public Stopper(){ setRunning(true);}
        @Override
        public void run() {
            while (running){
                try {
                    Thread.sleep(100);
                    seconds++;
                    setRunning(!isFinished());
                }
                catch (Exception e){ e.printStackTrace(System.out); }
            }
        }
    }

    /**
     * getActiveCompetitors
     * @return activeCompetitors (ArrayList<Competitor>)
     */
    public ArrayList<Competitor> getActiveCompetitors() { return activeCompetitors; }

    public void setActiveCompetitors(ArrayList<Competitor> activeCompetitors) { this.activeCompetitors = activeCompetitors; }

    public ArrayList<Competitor> getDisqualifiedCompetitors() { return disqualifiedCompetitors; }

    public void setDisqualifiedCompetitors(ArrayList<Competitor> disqualifiedCompetitors) { this.disqualifiedCompetitors = disqualifiedCompetitors; }

    /**
     * getFinishedCompetitors
     * @return finishedCompetitors (ArrayList<Competitor>)
     */
    public ArrayList<Competitor> getFinishedCompetitors() { return finishedCompetitors; }

    /**
     * setArena
     * @param arena (IArena)
     */
    public void setArena(IArena arena) {
        if (arena == null) throw new IllegalArgumentException("You must build arena first");
        else this.arena = arena;
    }

    /**
     * getArena
     * @return arena (IArena)
     */
    public IArena getArena() { return arena; }
    public void setMaxCompetitors(int maxCompetitors) {
        if (maxCompetitors <= 0 || maxCompetitors > 14) throw new IllegalArgumentException("Number of competitors must be 1-14");
        else this.maxCompetitors = maxCompetitors;
    }

    public boolean isFinished(){return (!this.hasActiveCompetitors() && this.finishedCompetitors.size() > 0);}

    public synchronized boolean isActive(){
        if (this.hasActiveCompetitors()) return activeCompetitors.get(0).getLocation().getX() > 0;
        else return false;
    }
    /**getMaxCompetitors
     * @return maxCompetitors (int)
     */
    public int getMaxCompetitors() { return maxCompetitors; }

    /**
     * Checks if the competitor is valid for the competition.
     * NOT IMPLEMENTED HERE - OVERRIDED FURTHER
     * @param competitor
     * @return true
     */
    public boolean isValidCompetitor(Competitor competitor){return true;}
    public void addCompetitor(Competitor competitor){
        if (activeCompetitors.size() == this.maxCompetitors) throw new IllegalStateException("Competition is full. Unable to add another competitor.");
        else {
            if (isValidCompetitor(competitor)) {
                ((Sportsman)competitor).setPositionInTheCompetition(this.getActiveCompetitors().size());
                ((Sportsman)competitor).generateID();
                competitor.initRace();
                activeCompetitors.add(competitor);
                ((Sportsman) competitor).setState(new ActiveState());
                ((Observable)competitor).addObserver(this);
           } else throw new IllegalArgumentException("competitor " + competitor.toString() +  " is not valid for the " + this.toString());
        }
    }

    public void prototypeCompetitor(int i,CompetitorColor color) throws Exception{
        Competitor clonedCompetitor;
        try {
            if (getActiveCompetitors().isEmpty()) throw new Exception("Competition has no competitor!");
            else if (getActiveCompetitors().size() <= i) throw  new Exception("There is no such competitor!");
            else if (getActiveCompetitors().size() >= getMaxCompetitors()) throw new Exception("There is no more place in the competition!");
            else {
                clonedCompetitor = (Competitor)((WinterSportsman) activeCompetitors.get(i)).clone();
                ((Sportsman)clonedCompetitor).setColor(color);
                ((Sportsman)clonedCompetitor).setPositionInTheCompetition(this.getActiveCompetitors().size());
                ((Sportsman)clonedCompetitor).generateID();
                clonedCompetitor.initRace();
                activeCompetitors.add(clonedCompetitor);
                ((Sportsman) clonedCompetitor).setState(new ActiveState());
                ((Sportsman) clonedCompetitor).determinePhysicalBreakdownTime();
                ((Observable)clonedCompetitor).addObserver(this);
                }
            }
        catch (Exception e){
            e.printStackTrace(System.out);
            throw e;
        }
    }

    /**
     * Checks if the competition haas active competitors in the arena.
     * @return true/false
     */
    public boolean hasActiveCompetitors(){ return (activeCompetitors.size() != 0); }

    public void addAcceleration(int competitorIndex, double acceleration){
        IWinterSportsman dec = new SpeedySportsman((IWinterSportsman) getActiveCompetitors().get(competitorIndex));
        dec.setAcceleration(acceleration);
    }

    public void decorate(int competitorIndex, CompetitorColor color){
        IWinterSportsman dec = new ColoredSportsman((WinterSportsman)getActiveCompetitors().get(competitorIndex));
        dec.setColor(color);
    }
    @Override
    public synchronized void update(Observable o, Object arg) {
        Collections.sort(activeCompetitors, new Comparator<Competitor>() {
            @Override
            public int compare(Competitor o1, Competitor o2) {
                return (int) (o2.getLocation().getX() - o1.getLocation().getX());
            }
        });
        if (arena.isFinished((IMobileEntity)o) && !((Sportsman)o).getCurrentState().equals("Disqualified")) {
            if (!finishedCompetitors.contains(o)) finishedCompetitors.add((Competitor) o);
            if ((((Sportsman)o).getCurrentState().equals("Active"))) ((Sportsman)o).setFinished();
            activeCompetitors.remove((Competitor) o);
        }
        else {
            if (stopper.getSeconds() >= ((Sportsman)o).getPhysicalBreakdownTime() && ((Sportsman)o).getCurrentState() == "Active"){
                ((Sportsman)o).injure();
                if (((Sportsman)o).getCurrentState().equals("Disqualified")) {
                    disqualifiedCompetitors.add((Competitor) o);
                    activeCompetitors.remove((Competitor) o);
                }
            }
        }

    }

    /**
     * Activates one turn for each active competitor in the competition.
     * If competitor has finished the race (according to arena length) it moved from activeCompetitors list to finishedCompetitors list.
     */

    public void startRace(){
        if (hasActiveCompetitors()) {
            stopper = new Stopper();
            Thread t1 = new Thread(stopper);
            t1.start();
            for (int i = 0; i < activeCompetitors.size(); i++) {
                Thread t2 = new Thread((Sportsman) activeCompetitors.get(i));
                t2.start();
            }
        }
    }
}

