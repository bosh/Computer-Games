package game;

// GENERIC PLATFORM, WITH SUPPORT FOR DISPLAY, PICKING AND AUDIO.

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.lang.reflect.*;
import javax.sound.sampled.*;
import java.io.*;
import java.net.*;

public class Platform extends BufferedApplet
{
   int w = 0, h = 0;
   Color bgColor = Color.white;
   Thing selectedThing = null;

   // GET THE iTH THING IN THIS PLATFORM

   public Thing thing(int i) { return ((Thing)things.get(i)); }

   // GET THE WIDTH AND HEIGHT OF THE DISPLAY WINDOW

   public int getWidth() { return w; }
   public int getHeight() { return h; }

   // DEFAULT BEHAVIOR IS DRAGGING THINGS

   public boolean mouseDown(Event e, int x, int y) {
      damage = true;
      selectedThing = null;
      for (int i = things.size() - 1 ; i >= 0 ; i--)
          if (thing(i).contains(x, y)) {
	      selectedThing = thing(i);
	      return thing(i).mouseDown(x, y);
          }
      return false;
   }

   public boolean mouseDrag(Event e, int x, int y) {
      damage = true;
      if (selectedThing != null)
         selectedThing.mouseDrag(x, y);
      return false;
   }

   public boolean mouseUp(Event e, int x, int y) {
      damage = true;
      if (selectedThing != null)
         selectedThing.mouseUp(x, y);
      return false;
   }

   public boolean keyDown(Event e, int key) {
      damage = true;
      for (int i = things.size() - 1 ; i >= 0 ; i--)
          if (thing(i).contains(e.x, e.y))
	     return thing(i).keyDown(key);
      return false;
   }

   public boolean keyUp(Event e, int key) {
      damage = true;
      for (int i = things.size() - 1 ; i >= 0 ; i--)
          if (thing(i).contains(e.x, e.y))
	     return thing(i).keyUp(key);
      return false;
   }

   // THE THREE MAIN CALLBACKS THAT CAN BE OVERRIDDEN BY THE APPLICATION

   public void setup() {               // WHERE TO DECLARE THINGS
   }

   public void update() {              // BEHAVIOR PER ANIMATION FRAME
   }

   public void overlay(Graphics g) {   // DRAW GRAPHICS ON TOP OF THE SCENE PER ANIMATION FRAME
   }

   // THE SUPERVISORY RENDERING LOOP, WHICH CALLS APPLICATION PROGRAMMER'S CALLBACKS

   public void render(Graphics g) {
      if (w == 0) {
         w = bounds().width;
         h = bounds().height;
	 setup();
      }

      update();

      g.setColor(bgColor);
      g.fillRect(0, 0, w, h);

      for (int i = 0 ; i < things.size() ; i++)
         thing(i).update(g);

      overlay(g);

      animating = true;
   }

   // ADD A THING TO THIS PLATFORM, GIVEN THE CLASS NAME OF THE THING

   public void addThing(String className) {
      Thing thing = null;
      try {
         thing = (Thing)Class.forName(className).newInstance();
      }
      catch (Throwable e) {
         System.err.println("nonexisting thing class name " + className);
      }
      addThing(thing);
   }

   // ADD AN ALREADY EXISTING THING TO THIS PLATFORM

   public void addThing(Thing thing) {
      things.add(thing);
      thing.setPlatform(this);
   }

   ArrayList things = new ArrayList();

   // HANDLE PLAYING AN AUDIO CLIP, WHETHER FROM A URL OR A LOCAL FILE

   public Clip playClip(String fileName) {
      Clip clip = null;
      try {
         clip = AudioSystem.getClip();
         clip.open(fileName.substring(0, 4).equals("http") ? AudioSystem.getAudioInputStream(new URL(fileName))
                                                           : AudioSystem.getAudioInputStream(new File(fileName)));
         clip.start();
      }
      catch (Exception e) { e.printStackTrace(); }
      return clip;
   }

   // HANDLE COLLISION BETWEEN TWO THINGS

   public boolean colliding(Thing one, Thing two) {
      return CollisionDetector.isCollision(one.X, one.Y, one.n, two.X, two.Y, two.n);
   }

   // RETURN THE WIDTH, IN PIXELS, OF A TEXT STRING

   public int stringWidth(String s, Graphics g) {
      return g.getFontMetrics(g.getFont()).stringWidth(s);
   }
}

