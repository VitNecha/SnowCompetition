package game.entities.sportsman;

import game.enums.CompetitorColor;

public class ColoredSportsman extends WSDecorator {

    public ColoredSportsman(IWinterSportsman winterSportsman){
        super(winterSportsman);
    }

    @Override
    public void setAcceleration(double n) {}

    @Override
    public void setColor(CompetitorColor color) {
        ((WinterSportsman)this.getWinterSportsman()).setColor(color);
    }
}
