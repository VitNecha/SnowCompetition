package game.competition;

public class DefaultCompetitionBuilder {
    WinterCompetitionBuilder bob;

    public DefaultCompetitionBuilder(WinterCompetitionBuilder bob){ this.bob = bob;}

    public void buildCompetition(){
        bob.buildArena();
        bob.buildDiscipline();
        bob.buildGender();
        bob.buildLeague();
        bob.buildCompetitors();
    }
    public Competition getCompetition(){ return bob.getCompetition(); }
}
