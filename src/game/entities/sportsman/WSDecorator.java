package game.entities.sportsman;

import game.enums.CompetitorColor;

public abstract class WSDecorator implements IWinterSportsman{

    private IWinterSportsman winterSportsman;

    public WSDecorator(IWinterSportsman winterSportsman){
        this.winterSportsman = winterSportsman;
    }

    public IWinterSportsman getWinterSportsman() {
        return winterSportsman;
    }
}
