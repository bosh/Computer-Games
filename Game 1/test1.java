
import java.awt.*;
import game.*;

public class test1 extends Platform
{
   double X0[] = { 100, 200, 200, 100 };
   double Y0[] = { 100, 100, 200, 200 };

   double X1[] = { 200, 250, 300 };
   double Y1[] = { 300, 200, 300 };

   public void setup() {
      addThing("game.Thing");
      addThing("game.Thing");
      thing(0).setShape(X0, Y0, X0.length);
      thing(1).setShape(X1, Y1, X1.length);
   }

   public void update() {
      boolean isColliding = colliding(thing(0), thing(1));

      thing(0).setColor(isColliding ? Color.red : Color.black);
      thing(1).setColor(isColliding ? Color.red : Color.black);

      if (isColliding && ! wasColliding)
         playClip("clips/click.wav");

      wasColliding = isColliding;;
   }

   boolean wasColliding = false;
}

