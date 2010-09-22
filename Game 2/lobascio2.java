import java.awt.*;
import java.util.*;
import game.*;

public class lobascio2 extends Platform {
   MidiSynth synth = new MidiSynth();
   int scale[] = {0, 2, 4, 5, 7, 9, 11, 12};
   double noteTime[] = new double[scale.length];
   int nNotes = 0, notes[] = new int[100];

   Bar topBar;
   Bar botBar;
   RectThing cover, coverToggle, play1, play2;
   boolean covered = true;

   public void setup() {
      int w = getWidth(), h = getHeight();
      bgColor = Color.black;
      
      topBar = new Bar(48, this);
      botBar = new Bar(264, this);
      
      topBar.render();
      botBar.render();

      addThing(cover = new RectThing(64, 264, 510, 168));
      cover.setColor(Color.darkGray);

      addThing(coverToggle = new RectThing(590, 345, 32, 24));
      coverToggle.setColor(Color.darkGray);
      coverToggle.onClick = "cover";

      addThing(play1 = new RectThing(16, 104, 32, 24));
      play1.setColor(Color.white);
      addThing(play2 = new RectThing(16, 344, 32, 24));
      play2.setColor(Color.white);
   }

   public void toggleCover() {
      if (covered) {
         removeThing(cover);
         cover = null;
         covered = false;
      } else {
         addThing(cover = new RectThing(64, 264, 510, 168));
         cover.setColor(Color.darkGray);
         covered = true;
      }
   }

   public void update() {
      //      addNote(n);  //----- ADD A NOTE
      playNotes(); //----- PLAY ALL THE NOTES FOR THIS UPDATE
   }

   // ADD ONE NOTE TO THE NOTE LIST
   void addNote(int note) { notes[nNotes++] = note; }

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
}
