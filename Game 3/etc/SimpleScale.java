
import game.*;

public class SimpleScale
{
   public void stop() {
      for (int note = 0 ; note < scale.length ; note++)
         synth.noteOff(0, 64 + scale[note],  0);
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

      for (int note = 0 ; note < scale.length ; note++)
         if (noteTime[note] < currentTime - 0.5)
            synth.noteOff(0, 64 + scale[note],  0);

      nNotes = 0;
   }

   // ADD ONE NOTE TO THE NOTE LIST

   void addNote(int note) {
      notes[nNotes++] = note;
   }

   MidiSynth synth = new MidiSynth();
   int scale[] = {0, 2, 4, 5, 7, 9, 11, 12};
   double noteTime[] = new double[scale.length];
   int nNotes = 0, notes[] = new int[100];
}

