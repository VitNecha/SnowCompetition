package game.competition;

import game.arena.IArena;

import java.util.ArrayList;

public interface CompetitionPlan {
    void setArena(IArena arena);
    void setMaxCompetitors(int maxCompetitors);
    void setActiveCompetitors(ArrayList<Competitor> activeCompetitors);
}
