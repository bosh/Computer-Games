package game;

public class Measure {
      Beat[] beats;
      Platform platform;
      int width = 128;
      int height = 168;
      int cornerX, cornerY;

      public Measure(int cornerX, int cornerY, Platform plat) {
            cornerX = cornerX;
            cornerY = cornerY;
      		this.platform = plat;
            beats = new Beat[4];
            for(int i = 0; i < beats.length; i++) {
                  beats[i] = new Beat(cornerX + 16 + i*32, cornerY, plat);
            }
      }
}
