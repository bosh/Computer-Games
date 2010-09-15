import game.*;
import java.awt.*;
import java.util.ArrayList;

public class lobascio1 extends Platform
{
   int w, h, b = 10, level_score = 0, total_score = 0;
   Thing backgound;
   ArrayList balloons, clouds, birds;
   Font font = new Font("Helvetica", Font.BOLD, 30);

   public void setup() {
      w = getWidth();
      h = getHeight();
      balloons = new ArrayList(0);
      clouds = new ArrayList(0);
      birds = new ArrayList(0);
      addThing(background = new RectThing(0, 0, w, h));
      
      
//      addThing(puck = new DiskThing(w / 2 - 20, h / 2 - 20, 40, 40));
   }

   double wind = 0, dy = -3.5;

   public void update() {
      wind = 0; //update this
      for(int i = 0; i < balloons.length(); i++) {
         balloons[i].setY(balloons[i].getY() + dy);
         balloons[i].setX(balloons[i].getX() + balloons[i].getCurrentVelocity() + wind]
      }

      for(int i = 0; i < clouds.length(); i++) {
         clouds[i].setX(clouds[i].getX() + wind/2]
      }
/*
      if (colliding(puck, leftWall  )) { if (dx < 0) { score2++; click(); } dx =  Math.abs(dx); }
      if (colliding(puck, rightWall )) { if (dx > 0) { score1++; click(); } dx = -Math.abs(dx); }
      if (colliding(puck, topWall   )) { if (dy < 0) click(); dy =  Math.abs(dy); }
      if (colliding(puck, bottomWall)) { if (dy > 0) click(); dy = -Math.abs(dy); }

      if (colliding(puck, paddle1   )) { if (dx < 0) click(); dx =  Math.abs(dx); }
      if (colliding(puck, paddle2   )) { if (dx > 0) click(); dx = -Math.abs(dx); }
*/
   }

   public void overlay(Graphics g) {
      g.setColor(Color.black);
      g.setFont(font);
      g.drawString("Total Score: " + total_score, 40, 50);
      g.drawString("Level Score: " + level_score, w - 40 - stringWidth("Level Score: " + level_score, g), 50);
   }

   void pop() { playClip("clips/pop.wav"); }
}

