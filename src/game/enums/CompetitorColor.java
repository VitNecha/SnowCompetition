package game.enums;
import java.awt.*;

public enum CompetitorColor {
    Red(Color.RED),Blue(Color.BLUE),Yellow(Color.YELLOW),Cyan(Color.CYAN),Orange(Color.ORANGE);
    Color color;
    CompetitorColor(Color color){
        this.color = color;
    }
    public Color getColor() {
        return color;
    }
}
