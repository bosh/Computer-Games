package game;

public class Measure {
      Beat[] beats;
      Platform platform;
      int width = 128;
      int height = 168;
      int cornerX, cornerY;
      int number;

      public Measure(int number, int cornerX, int cornerY, Platform plat) {
            this.number = number;
            this.cornerX = cornerX;
            this.cornerY = cornerY;
            this.platform = plat;
            beats = new Beat[4];
            for(int i = 0; i < beats.length; i++) {
                  beats[i] = new Beat(i, cornerX + 8 + i*32, cornerY, plat);
            }
      }
      
      public void render() {
            platform.addThing(new RectThing(cornerX + width - 2, cornerY, 4, height));
            for(int i = 0; i < beats.length; i++) {
                  beats[i].render();
            }
      }

      public int[] constructNotes() {
            int[] notes = new int[4];
            for(int i = 0; i < beats.length; i++) {
                  notes[i] = beats[i].constructNote();
            }
            return notes;
      }
}

