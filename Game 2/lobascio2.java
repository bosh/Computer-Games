import java.awt.*;
import java.util.*;
import game.*;

public class lobascio2 extends Platform {
   MidiSynth synth = new MidiSynth();
   int scale[] = {0, 2, 4, 5, 7, 9, 11, 12};
   double noteTime[] = new double[scale.length];
   int nNotes = 0, notes[] = new int[100];
   Font font = new Font("Helvetica", Font.BOLD, 16);

   Bar topBar;
   Bar botBar;
   RectThing cover, coverToggle, play1, play2;
   boolean covered = true, showScore = false;
   int score = 0;

   public void setup() {
      int w = getWidth(), h = getHeight();
      bgColor = Color.black;
      
      topBar = new Bar(48, this);
      botBar = new Bar(264, this);
      
      topBar.render();
      botBar.render();

      addThing(cover = new RectThing(64, 264, 510, 168));
      cover.setColor(Color.darkGray);

      addThing(coverToggle = new RectThing(587, 344, 48, 24));
      coverToggle.setColor(Color.darkGray);
      coverToggle.onClick = "cover";

      addThing(play1 = new RectThing(13, 104, 38, 24));
      play1.setColor(Color.white);
      play1.onClick = "play1";
      addThing(play2 = new RectThing(13, 344, 38, 24));
      play2.setColor(Color.white);
      play2.onClick = "play2";
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
      //addNote(n);  //----- ADD A NOTE
      //playNotes(); //----- PLAY ALL THE NOTES FOR THIS UPDATE
   }

   public void overlay(Graphics g) {
      g.setColor(Color.black);
      g.setFont(font);
      g.drawString("Play", 16, 124);
      g.drawString("Play", 16, 364);
      g.drawString((covered)?"Show":"Hide", 590, 364);
      if (showScore) {
         g.setColor(Color.white);
         g.drawString("Score: " + score + "/16", 32, 24);
      }
   }

   public void play(int playerNumber) {
      int[] notes;
      showScore = false;
      score = 0;
      if (playerNumber == 1) {
         notes = topBar.constructNotes();
         int[] notesToMatch = botBar.constructNotes();
         playNotes(notes);
         for(int i = 0; i < notes.length; i++) {
            if (notes[i] == notesToMatch[i]) { score++; }
         }
         showScore = true;
      } else if (playerNumber == 2) {
         playNotes(botBar.constructNotes());
      }
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

   public void playNotes(int[] values) {
      double currentTime = System.currentTimeMillis();
      animating = true;

      for(int i = 0; i < values.length; i++) {
         currentTime = System.currentTimeMillis();
         if (values[i] == -1) {
            while(System.currentTimeMillis() < currentTime + 420){};
         } else {
            playNote(scale[values[i]]);
            while(System.currentTimeMillis() < currentTime + 420){};
            offNote(scale[values[i]]);
         }
      }
   }

   public void playNote(int note) {
      synth.noteOn(0, 64 + note, 63);
   }
   
   public void offNote(int note) {
      synth.noteOff(0, 64 + note,  0);
   }
}
