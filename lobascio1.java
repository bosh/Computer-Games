import game.*;
import java.awt.*;

public class lobascio1 extends Platform {
   Color colors[] = {Color.red, Color.red, Color.red, Color.blue, Color.green, Color.orange, Color.white};
   int w, h, b = 10;
   Thing balloons[], clouds[], birds[];
   Font font = new Font("Helvetica", Font.BOLD, 20);
   double wind = 0, dy = -4.5;

   public void setup() {
      bgColor = new Color(100, 215, 240);
      w = getWidth();
      h = getHeight();
      balloons = new Thing[60];
      clouds = new Thing[7];
      birds = new Thing[12];
      for(int i = 0; i < balloons.length; i++) {
         balloons[i] = new DiskThing( 50 + Math.random()*(w-100), h, 30, 50);
         balloons[i].setSpawnTime(Math.random()*35);
         balloons[i].setColor(colors[(int) (Math.random()*colors.length)]);
//         addThing(balloons[i]); //TEMP
      }
      for(int i = 0; i < clouds.length; i++) {
         clouds[i] = new DiskThing( Math.random()*w, 20 + Math.random()*(h-70), 100 + Math.random()*50, 50 + Math.random()*50);
         clouds[i].setSpawnTime(15 + Math.random()*10);
         clouds[i].setClickable(false);
         clouds[i].setColor(Color.lightGray);
//         addUIThing(clouds[i]); //TEMP
      }
      for(int i = 0; i < birds.length; i++) {
         double height = Math.random()*(h-100);
         birds[i] = new PolygonThing( new double[] {-100, -25, -100}, new double[] {height + 20, height, height - 20} );
         birds[i].setSpawnTime(25 + Math.random()*10);
//         addThing(birds[i]); //TEMP
      }
   }

   public void update() {
      wind = (wind + Math.random()*6 - 3)/2;
      for(int i = 0; i < balloons.length; i++) {
         if (balloons[i].isSpawned()) {
            balloons[i].setY(balloons[i].getY() + dy);
            balloons[i].setX(balloons[i].getX() + balloons[i].getDx() + wind);
            balloons[i].setDx(balloons[i].getDx() + wind/2);
         }
      }

      for(int i = 0; i < clouds.length; i++) {
         if (clouds[i].isSpawned()) { clouds[i].setX(clouds[i].getX() + wind/3); }
      }

      for(int i = 0; i < birds.length; i++) {
         if (selectedThing == birds[i]) { continue; }
         if (birds[i].isSpawned()) { birds[i].setX(birds[i].getX() + wind/5 + 10); }
      }

      for(int i = 0; i < birds.length; i++) {
         if (birds[i].isSpawned())
         for(int j = 0; j < balloons.length; j++) {
            if (balloons[j].isSpawned()) {
               if (colliding(birds[i], balloons[j])) {
                  balloons[j].setSpawned(false);
                  balloons[j].setX(-100);
                  balloons[j].setY(-100);
                  decrementScore();
                  pop();
               }
            }
         }
      }
   }

   public void overlay(Graphics g) {
      g.setColor(Color.black);
      g.setFont(font);
      g.drawString("Total Score: " + getTotalScore(), 40, 50);
      g.drawString("Level Score: " + getLevelScore(), w - 40 - stringWidth("Level Score: " + getLevelScore(), g), 50);
   }

   public void pop() { playClip("clips/pop.wav"); }
}

