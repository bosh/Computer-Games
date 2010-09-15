
import game.*;
import java.awt.*;

public class pong extends Platform
{
   int w, h, b = 10, score1 = 0, score2 = 0;
   Thing leftWall, rightWall, topWall, bottomWall, puck, paddle1, paddle2;
   Font font = new Font("Helvetica", Font.BOLD, 30);

   public void setup() {
      w = getWidth();
      h = getHeight();
      addThing(leftWall   = new RectThing(0, 0, b, h));
      addThing(rightWall  = new RectThing(w-b, 0, b, h));
      addThing(topWall    = new RectThing(0, 0, w, b));
      addThing(bottomWall = new RectThing(0, h-b, w, b));
      addThing(paddle1    = new RectThing(b + 20, h/2 - 30, 10, 60));
      addThing(paddle2    = new RectThing(w - b - 30, h/2 - 30, 10, 60));
      addThing(puck = new DiskThing(w / 2 - 20, h / 2 - 20, 40, 40));
   }

   double dx = 3.5, dy = 4.5;

   public void update() {
      puck.setX(puck.getX() + dx);
      puck.setY(puck.getY() + dy);

      paddle1.setX(b + 30);
      paddle2.setX(w - b - 30);

      if (colliding(puck, leftWall  )) { if (dx < 0) { score2++; click(); } dx =  Math.abs(dx); }
      if (colliding(puck, rightWall )) { if (dx > 0) { score1++; click(); } dx = -Math.abs(dx); }
      if (colliding(puck, topWall   )) { if (dy < 0) click(); dy =  Math.abs(dy); }
      if (colliding(puck, bottomWall)) { if (dy > 0) click(); dy = -Math.abs(dy); }

      if (colliding(puck, paddle1   )) { if (dx < 0) click(); dx =  Math.abs(dx); }
      if (colliding(puck, paddle2   )) { if (dx > 0) click(); dx = -Math.abs(dx); }
   }

   public void overlay(Graphics g) {
      g.setColor(Color.black);
      g.setFont(font);
      g.drawString("" + score1, b + 20, 50);
      g.drawString("" + score2, w - b - 20 - stringWidth("" + score2, g), 50);
   }

   void click() { playClip("clips/click.wav"); }
}

