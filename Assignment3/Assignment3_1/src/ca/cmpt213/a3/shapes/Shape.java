package ca.cmpt213.a3.shapes;
import ca.cmpt213.a3.UI.Canvas;

import java.awt.*;

/**
 * Shape interface that classes will implement
 * Brandon Ha, 301333647, bbha@sfu.ca
 */
public interface Shape {

    int getLocationX();

    int getLocationY();

    void setBorderChar(char borderChar);

    char getBorderChar();

    void setColor(Color color);

    Color getColor();

    void draw(Canvas canvas);

}
