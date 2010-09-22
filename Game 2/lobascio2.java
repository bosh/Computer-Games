import java.awt.*;
import java.util.*;
import game.*;

public class lobascio2 extends Platform {
   int N = 8;
   Random R = new Random(0);
   boolean wasColliding[] = new boolean[N];
   MidiSynth synth = new MidiSynth();
   int scale[] = {0, 2, 4, 5, 7, 9, 11, 12};
   double noteTime[] = new double[scale.length];
   int nNotes = 0, notes[] = new int[100];

   public void setup() {
      int w = getWidth(), h = getHeight();

      // CREATE A SET OF MUSICAL KEYS
      int x = 0;
      for (int n = 0 ; n < N ; n++) {
         int b = 10 + 5 * (N - n);
         addThing(new RectThing(x += 2 * b, h / 2 - b / 2, b, b));
      }

      // CREATE A MALLET
      addThing(new DiskThing(w/2 - 20, h/4 - 20, 40, 40));
      thing(N).setColor(Color.red);
   }

   public void update() {
      nNotes = 0; //----- START WITH NO NOTES PLAYED FOR THIS UPDATE
      for (int n = 0 ; n < N ; n++) {
         // CHECK FOR A KEY COLLIDING WITH THE MALLET
         boolean isColliding = colliding(thing(n), thing(N));
         // HIGHLIGHT A KEY WHILE IT IS COLLIDING WITH THE MALLET
         thing(n).setColor(isColliding ? Color.black : Color.cyan);
         // WHENEVER THE KEY FIRST COLLIDES WITH THE MALLET, PLAY A NOTE
         if (isColliding && ! wasColliding[n]) {
            addNote(n);  //----- ADD A NOTE
         }
         wasColliding[n] = isColliding;
      }
      playNotes(); //----- PLAY ALL THE NOTES FOR THIS UPDATE
   }

   // SUPPORT FOR MIDI IN GAME
   public void stop() {
      super.stop();
      // TURN OFF ALL LINGERING SOUNDS WHEN APPLET EXITS
      for (int note = 0 ; note < scale.length ; note++) {
         synth.noteOff(0, 64 + scale[note],  0);
      }
   }

   public void playNotes() {
      double currentTime = System.currentTimeMillis() / 1000.0;
      // PLAY, AND TIMESTAMP, ALL NOTES IN THE NOTE LIST
      for (int n = 0 ; n < nNotes ; n++) {
         int note = notes[n];
         if (note >= 0 && note < scale.length) {
            synth.noteOn(0, 64 + scale[note], 63);
            noteTime[note] = currentTime;
         }
      }
      // TURN OFF ALL NOTE SOUNDS THAT HAVE BEEN PLAYING TOO LONG
      for (int note = 0 ; note < scale.length ; note++) {
         if (noteTime[note] < currentTime - 0.5) {
            synth.noteOff(0, 64 + scale[note],  0);
         }
      }
      animating = true;
   }

   // ADD ONE NOTE TO THE NOTE LIST
   void addNote(int note) { notes[nNotes++] = note; }
}
