
//import java.lang.reflect.*;
import java.io.*;
import java.awt.*;
import java.util.*;
import game.*;

public class simpleMidiGame extends Platform
{
   int N = 8;
   SimpleScale scale = new SimpleScale();
   Score score = new Score();
   Random R = new Random(0);
   Font bigFont = new Font("Helvetica", Font.BOLD, 24);

   public void setup() {
      int w = getWidth(), h = getHeight();

      setProjectName("G4L");

      // CREATE A SET OF MUSICAL KEYS

      int x = 0;
      for (int n = 0 ; n < N ; n++) {
         int b = 10 + 5 * (N - n);
         RectThing t = new RectThing();
         t.setBounds(x += 2 * b, h / 2 - b / 2, b, b);
         addThing(t);
         t.setColor(Color.cyan);
         t.setLabel("" + n);
	 track(t, "THING_" + n, "x y");
      }

      // CREATE A MALLET

      addThing(new DiskThing(w/2 - 20, h/4 - 20, 40, 40));
      thing(N).setColor(Color.red);

      setCollisionTracked(thing(N));

      track(score, "SCORE");
   }

   public void startCollision(Thing one, Thing two) {
      two.setColor(Color.black);
      scale.addNote(two.getId());
      score.sum += two.getId();
      if (score.sum == score.targetSum)
         score.score++;
      if (score.sum >= score.targetSum) {
         score.round++;
         score.sum = 0;
	 score.targetSum = Math.abs(R.nextInt()) % 20;
      }
   }

   public void endCollision(Thing one, Thing two) {
      two.setColor(Color.cyan);
   }

   public void update() {
      scale.playNotes();
   }

   public void overlay(Graphics g) {
      Font font = g.getFont();
      g.setFont(bigFont);
      g.setColor(Color.black);
      g.drawString("target: " + score.targetSum, 5, 24);
      g.drawString("sum: " + score.sum, 5, 24 + 30);
      g.setColor(Color.red);
      g.drawString("score: " + score.score, 5, 24 + 30 * 3);
      g.setFont(font);
   }

   // SUPPORT FOR MIDI IN GAME

   public void stop() {
      super.stop();
      scale.stop();
   }
}

