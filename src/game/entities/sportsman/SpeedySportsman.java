package game.entities.sportsman;

import game.enums.CompetitorColor;

public class SpeedySportsman extends WSDecorator{

    public SpeedySportsman(IWinterSportsman winterSportsman){
        super(winterSportsman);
    }

    @Override
    public void setAcceleration(double n) {
        ((WinterSportsman)this.getWinterSportsman()).setAcceleration(((WinterSportsman) this.getWinterSportsman()).getAcceleration() + n);
    }

    @Override
    public void setColor(CompetitorColor color) {}
}
