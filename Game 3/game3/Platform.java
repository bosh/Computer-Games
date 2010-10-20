package game3;
// GENERIC PLATFORM, WITH SUPPORT FOR DISPLAY, PICKING AND AUDIO.
import java.awt.*;
import java.text.*;
import java.util.*;
import java.awt.event.*;
import java.lang.reflect.*;
import javax.sound.sampled.*;
import java.io.*;
import java.net.*;

public class Platform extends BufferedApplet {
   public int w = 0, h = 0;
   public Color bgColor = Color.white;
   public Thing selectedThing = null;
   public ArrayList things = new ArrayList();

   ArrayList collisionTrackedThings = new ArrayList();
   ArrayList trackedThings = new ArrayList();
   ArrayList shadowOfTrackedThings = new ArrayList();
   boolean isColliding[][];
   int verbosity = 0;
   boolean isMouseOverStatus = false;
   StringBuffer buffer = new StringBuffer();
   StringBuffer eventBuffer = new StringBuffer();
   TextIO textIO;
   String logFileName = null;
   String projectName = "project";
   boolean copyingLogToStdOut = false;

   public void toggleCover() {} //OVERRIDE IN GAME
   public void play(int playerNumber) {} //OVERRIDE IN GAME
   
   // GET THE iTH THING IN THIS PLATFORM
   public Thing thing(int i) { return ((Thing)things.get(i)); }

   // GET THE WIDTH AND HEIGHT OF THE DISPLAY WINDOW
   public int getWidth() { return w; }
   public int getHeight() { return h; }

   public void setProjectName(String name) {
      projectName = name;
   }

   public void setLogFile(String name) {
      logFileName = name;
   }

   public void output(String s) {
      if (textIO == null) {
         textIO = new TextIO();
         textIO.openOutput(logFileName + ".txt");
      }
      textIO.writeOutput(s);
      if (copyingLogToStdOut)
         System.out.print(s);
   }

   public void startCollision(Thing one, Thing two) { }
   public void endCollision(Thing one, Thing two) { }

   public void setVerbosity(int level) {
      verbosity = level;
      eventBuffer.append(" VERBOSITY " + verbosity);
   }

   public void setCollisionTracked(Thing thing) {
      addToSet(collisionTrackedThings, thing);
   }

   public boolean mouseMove(Event e, int x, int y) {
      isMouseOverStatus = y >= getHeight() - 20;
      if (verbosity >= 3)
    eventBuffer.append(" MOUSE_MOVE { " + x + " " + y + " }");
      return true;
   }

   // DEFAULT BEHAVIOR IS DRAGGING THINGS
   public boolean mouseDown(Event e, int x, int y) {
      damage = true;
      if (isMouseOverStatus) {
         setVerbosity((verbosity + 1) % 4);
         return true;
      }

      selectedThing = null;
      for (int i = things.size() - 1 ; i >= 0 ; i--)
         if (thing(i).contains(x, y)) {
            selectedThing = thing(i);
            if (verbosity >= 1)
               eventBuffer.append(" DOWN_ON " + selectedThing.getId());
            return thing(i).mouseDown(x, y);
         }
      if (verbosity >= 3)
         eventBuffer.append(" MOUSE_DOWN { " + x + " " + y + " }");
      return false;
   }

   public boolean mouseDrag(Event e, int x, int y) {
      damage = true;
      if (selectedThing != null) {
         if (verbosity >= 2)
            eventBuffer.append(" DRAG_ON " + selectedThing.getId());
         selectedThing.mouseDrag(x, y);
      }
      if (verbosity >= 3)
         eventBuffer.append(" MOUSE_DRAG { " + x + " " + y + " }");
      return false;
   }

   public boolean mouseUp(Event e, int x, int y) {
      damage = true;
      if (selectedThing != null) {
         if (verbosity >= 1)
            eventBuffer.append(" UP_ON " + selectedThing.getId());
         selectedThing.mouseUp(x, y);
      }
      if (verbosity >= 3)
         eventBuffer.append(" MOUSE_UP { " + x + " " + y + " }");
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
      if (key == 'V' - '@')
         copyingLogToStdOut = ! copyingLogToStdOut;
      damage = true;
      for (int i = things.size() - 1 ; i >= 0 ; i--)
         if (thing(i).contains(e.x, e.y))
            return thing(i).keyUp(key);
      return false;
   }

   // THE THREE MAIN CALLBACKS THAT CAN BE OVERRIDDEN BY THE APPLICATION
   public void setup() {}               // WHERE TO DECLARE THINGS
   public void update() {}              // BEHAVIOR PER ANIMATION FRAME
   public void overlay(Graphics g) {}   // DRAW GRAPHICS ON TOP OF THE SCENE PER ANIMATION FRAME

   // THE SUPERVISORY RENDERING LOOP, WHICH CALLS APPLICATION PROGRAMMER'S CALLBACKS
   long time0;

   public void render(Graphics g) {
      if (w == 0) {
         w = bounds().width;
         h = bounds().height;
         setup();
         isColliding = new boolean[collisionTrackedThings.size()][things.size()];
         time0 = System.currentTimeMillis();
         Format format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
         if (logFileName == null)
            logFileName = getClass().getName() + "-" + time0;
         output(projectName + " report version 0.0 " + getClass().getName() + " " + format.format(new Date()) + "\n");
      }

      long time = System.currentTimeMillis() - time0;

      for (int i = 0 ; i < collisionTrackedThings.size() ; i++) {
         Thing thing1 = (Thing)collisionTrackedThings.get(i);
         for (int j = 0 ; j < things.size() ; j++) {
            Thing thing2 = (Thing)things.get(j);
            if (thing1 != thing2) {
               boolean collision = isColliding(thing1, thing2);

               if (collision && ! isColliding[i][j]) {
                  startCollision(thing1, thing2);
                  if (verbosity >= 1)
                     eventBuffer.append(" START_COLLISION { " + thing1.getId() + " " + thing2.getId() + " }");
               } else if (! collision && isColliding[i][j]) {
                     endCollision(thing1, thing2);
                     if (verbosity >= 1)
                        eventBuffer.append(" END_COLLISION { " + thing1.getId() + " " + thing2.getId() + " }");
               }
               isColliding[i][j] = collision;
            }
         }
      }

      buffer.setLength(0);
      buffer.append((time / 10));
      int bufferLength0 = buffer.length();
      buffer.append(eventBuffer);
      eventBuffer.setLength(0);
      update();
      reportChangesToTrackedObjects();
      if (buffer.length() > bufferLength0)
         output(buffer + "\n");

      g.setColor(bgColor);
      g.fillRect(0, 0, w, h);

      for (int i = 0 ; i < things.size() ; i++)
         thing(i).update(g);

      overlay(g);

      if (isMouseOverStatus) {
         g.setColor(Color.black);
         g.drawString("verbose = " + verbosity, 5, h - 5);
      }

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
      thing.setId(things.size());
      things.add(thing);
      thing.setPlatform(this);
   }

   public void removeThing(Thing thing) { things.remove(things.indexOf(thing)); }

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

   public boolean isColliding(Thing thing1, Thing thing2) {
      return CollisionDetector.isCollision(thing1.X, thing1.Y, thing1.n, thing2.X, thing2.Y, thing2.n);
   }


   // RETURN THE WIDTH, IN PIXELS, OF A TEXT STRING
   public int stringWidth(String s, Graphics g) {
      return g.getFontMetrics(g.getFont()).stringWidth(s);
   }

   void addToSet(ArrayList set, Object object) {
      for (int i = 0 ; i < set.size() ; i++)
         if (object == set.get(i))
            return;
      set.add(object);  
   }
/***************************************************************************************************
   Strategy for tracking objects:
   -- instantiate a shadow fields: an array of field valuess corresponding to the ones in the original object.
   -- at each update:
      -- compare object's field values with corresponding shadow field values.
      -- if any field values have changed:
         -- write out name/value pairs of all changed fields of the object.
         -- for all changed fields: set shadow field values to the object's field values.
*/

   ArrayList trackedName = new ArrayList();
   ArrayList trackedVars = new ArrayList();
   ArrayList tracked = new ArrayList();
   ArrayList shadows = new ArrayList();

   public void track(Object object, String name) {
      track(object, name, "");
   }

   public void track(Object object, String name, String varNames) {
      ArrayList vars = new ArrayList();
      for (StringTokenizer st = new StringTokenizer(varNames) ; st.hasMoreTokens() ; )
         vars.add(st.nextToken());

      trackedName.add(name);
      trackedVars.add(vars);
      tracked.add(object);
      shadows.add(newShadowObject(object, vars));
   }

   ArrayList newShadowObject(Object object, ArrayList vars) {
      ArrayList shadow = new ArrayList();
      try {
         Field field[] = object.getClass().getDeclaredFields();
         for (int i = 0 ; i < field.length ; i++) {
            field[i].setAccessible(true);
            shadow.add(field[i].get(object));
         }
         for (int i = 0 ; i < vars.size() ; i++)
            shadow.add(fieldValue(object, (String)vars.get(i)));
      }
      catch (Throwable e) { System.err.println(e); }
      return shadow;
   }

   Object fieldValue(Object object, String fieldName) {
      try {
         Class c = object.getClass();
         for ( ; c != null ; c = c.getSuperclass()) {
      Field field[] = c.getDeclaredFields();
            for (int i = 0 ; i < field.length ; i++) {
               field[i].setAccessible(true);
               if (field[i].getName().equals(fieldName))
                  return field[i].get(object);
            }
         }
      }
      catch (Throwable e) { System.err.println(e); }
      return null;
   }

   public void reportChangesToTrackedObjects() {
      for (int i = 0 ; i < tracked.size() ; i++) {

         Object    object = tracked.get(i);
         ArrayList shadow = (ArrayList)shadows.get(i);
         ArrayList vars   = (ArrayList)trackedVars.get(i);

         if (! hasEqualValues(shadow, object, vars)) {
       buffer.append(" " + trackedName.get(i));
            reportChanges(shadow, object, vars);
         }
      }
   }

   boolean hasEqualValues(ArrayList shadow, Object object, ArrayList vars) {
      try {
         Field field[] = object.getClass().getDeclaredFields();
         for (int i = 0 ; i < field.length ; i++) {
           field[i].setAccessible(true);
           if (! shadow.get(i).equals(field[i].get(object)) )
        return false;
         }
         for (int i = 0 ; i < vars.size() ; i++)
            if ( ! shadow.get(field.length + i).equals( fieldValue(object, (String)vars.get(i)) ) )
          return false;
      }
      catch (Throwable e) { System.err.println(e); }
      return true;
   }

   void reportChanges(ArrayList shadow, Object object, ArrayList vars) {
      try {
         buffer.append(" { ");

         Field field[] = object.getClass().getDeclaredFields();
         for (int i = 0 ; i < field.length ; i++) {
       field[i].setAccessible(true);
       if (! shadow.get(i).equals(field[i].get(object)) ) {
          buffer.append(field[i].getName() + " " + field[i].get(object) + " ");
               shadow.set(i, field[i].get(object) );
            }
         }
    for (int i = 0 ; i < vars.size() ; i++) {
       Object value = fieldValue(object, (String)vars.get(i));
       if (! shadow.get(field.length + i).equals(value)) {
          buffer.append(vars.get(i) + " " + value + " ");
          shadow.set(field.length + i, value);
       }
    }

         buffer.append("}");
      }
      catch (Throwable e) { System.err.println(e); }
   }
}

