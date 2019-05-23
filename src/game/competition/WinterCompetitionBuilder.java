package game.competition;

public interface WinterCompetitionBuilder {
    void buildArena();
    void buildCompetitors();
    void buildDiscipline();
    void buildLeague();
    void buildGender();
    Competition getCompetition();
}
