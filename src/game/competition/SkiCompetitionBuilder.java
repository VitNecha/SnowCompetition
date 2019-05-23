package game.competition;

import game.arena.Arena;
import game.arena.ArenaFactory;
import game.entities.sportsman.Skier;
import game.entities.sportsman.Sportsman;
import game.enums.*;
import java.util.Random;

public class SkiCompetitionBuilder implements WinterCompetitionBuilder {
    private SkiCompetition competition;
    private int numberOfCompetitors;

    public SkiCompetitionBuilder(int numberOfCompetitors){
        competition = new SkiCompetition();
        competition.setMaxCompetitors(numberOfCompetitors);
        this.numberOfCompetitors = numberOfCompetitors;
    }

    @Override
    public void buildArena() {
        ArenaFactory factory = new ArenaFactory();
        competition.setArena(factory.getNewArena("Winter",780, SnowSurface.ICE, WeatherCondition.SUNNY));
    }

    @Override
    public void buildDiscipline() { competition.setDiscipline(Discipline.FREESTYLE); }

    @Override
    public void buildGender() { competition.setGender(Gender.FEMALE); }

    @Override
    public void buildLeague() { competition.setLeague(League.ADULT);}


    @Override
    public void buildCompetitors() {
        try {
            competition.setMaxCompetitors(14);
            if (numberOfCompetitors > 14) numberOfCompetitors = 14;
            competition.addCompetitor(new Skier("Clone 1", 22, Gender.FEMALE, (double) (((new Random()).nextInt(10)) + 1) / 100, 15,
                    competition.getArena().getFriction(), ((Arena) competition.getArena()).getLength(),
                    competition.getDiscipline(), CompetitorColor.Orange) {
            });
            for (int i = 1; i < numberOfCompetitors; i++) {
                competition.prototypeCompetitor(0, CompetitorColor.values()[(new Random()).nextInt(5)]);
                ((Sportsman) competition.getActiveCompetitors().get(competition.getActiveCompetitors().size() - 1)).setAcceleration((double) (((new Random()).nextInt(10)) + 1) / 100 +
                        League.calcAccelerationBonus(((Sportsman) competition.getActiveCompetitors().get(competition.getActiveCompetitors().size() - 1)).getAge()));
                ((Sportsman) competition.getActiveCompetitors().get(competition.getActiveCompetitors().size() - 1)).setName("Clone " + (i + 1));
            }
        }
        catch (Exception er){er.printStackTrace(System.out);}
    }
    public SkiCompetition getCompetition() {
        return competition;
    }
}
