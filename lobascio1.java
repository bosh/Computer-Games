import game.*;
import java.awt.*;

public class lobascio1 extends Platform
{
   Color colors[] = {Color.red, Color.blue, Color.green, Color.pink, Color.orange};
   int w, h, b = 10;
   Thing balloons[], clouds[], birds[];
   Font font = new Font("Helvetica", Font.BOLD, 20);

   public void setup() {
      w = getWidth();
      h = getHeight();
      balloons = new Thing[60];
      clouds = new Thing[5];
      birds = new Thing[10];
      for(int i = 0; i < balloons.length; i++) {
         balloons[i] = new DiskThing( Math.random()*w, h, 30, 50);
         balloons[i].setSpawnTime(Math.random()*35);
         balloons[i].setColor(colors[(int) Math.random()*colors.length]);
      }
      for(int i = 0; i < clouds.length; i++) {
         clouds[i] = new DiskThing( Math.random()*w, Math.random()*(h-50), 125, 75);
         clouds[i].setSpawnTime(15 + Math.random()*10);
         clouds[i].setClickable(false);
         clouds[i].setColor(Color.lightGray);
      }
      for(int i = 0; i < birds.length; i++) {
         double height = Math.random()*(h-100);
         birds[i] = new PolygonThing( new double[] {-75, 0, -75}, new double[] {height + 20, height, height - 20} );
         birds[i].setSpawnTime(25 + Math.random()*10);
      }
   }

   double wind = 0, dy = -3.5;

   public void update() {
      wind = 0; //update this at random
      for(int i = 0; i < balloons.length; i++) {
         balloons[i].setY(balloons[i].getY() + dy);
         balloons[i].setX(balloons[i].getX() + balloons[i].getDx() + wind);
         balloons[i].setDx(balloons[i].getDx() + wind/2);
      }

      for(int i = 0; i < clouds.length; i++) {
         clouds[i].setX(clouds[i].getX() + wind/2);
      }

      for(int i = 0; i < birds.length; i++) {
         birds[i].setX(birds[i].getX() + wind/4 + 10);
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
      g.drawString("Total Score: " + getTotalScore(), 40, 50);
      g.drawString("Level Score: " + getLevelScore(), w - 40 - stringWidth("Level Score: " + getLevelScore(), g), 50);
   }

   public void pop() { playClip("clips/pop.wav"); }
}

