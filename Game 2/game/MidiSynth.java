package game;

import java.util.*;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class MidiSynth
{
   public MidiSynth() {
      try {
         synth = MidiSystem.getSynthesizer();
         synth.open();

      } catch (Exception e) { e.printStackTrace(); }
   }

   private MidiChannel channel(int c) { return (synth.getChannels())[c]; }

   public String getInstrumentInfo(int i) {
      Instrument[] instr = synth.getDefaultSoundbank().getInstruments();
      if (i < 0 || i >= instr.length)
         return null;
      return instr[i].toString();
   }

   public String getInstrumentName(int i) {
      String info = getInstrumentInfo(i);
      return info == null ? "" : extract(info, "Instrument ", " (bank ");
   }

   public int getInstrumentBank(int i) {
      String info = getInstrumentInfo(i);
      return info == null ? -1 : value(extract(info, " (bank ", " program "));
   }

   public int getInstrumentProgram(int i) {
      String info = getInstrumentInfo(i);
      return info == null ? -1 : value(extract(info, " program ", ")"));
   }

   public boolean setInstrument(int c, String str) {
      Instrument[] instr = synth.getDefaultSoundbank().getInstruments();
      for (int i = 0 ; i < instr.length ; i++)
         if (str.equals(getInstrumentName(i))) {
            setInstrument(c, getInstrumentBank(i), getInstrumentProgram(i));
            return true;
         }
      return false;
   }

   public void setInstrument(int c, int bank, int program) {
      channel(c).programChange(bank, program);
   }

   public void noteOn(int c, int note, int velocity) {
      for (int i = 0 ; i < voice.length ; i++)
         if (voice[i] == 0 || voice[i] == note) {
            channel(c).noteOn(note, velocity);
            voice[i] = note;
	    break;
         }
   }

   public void noteOff(int c, int note, int velocity) {
      for (int i = 0 ; i < voice.length ; i++)
         if (voice[i] == note) {
	    channel(c).noteOff(note, velocity);
            voice[i] = 0;
	    break;
         }
   }

   public void close() {
      synth.close();
   }

   String extract(String s, String preKey, String postKey) {
      return s.substring(s.indexOf(preKey)+preKey.length(), s.indexOf(postKey));
   }

   private int value(String s) { return (new Integer(s)).intValue(); }

   private int voice[] = new int[20];
   private Synthesizer synth;
}


