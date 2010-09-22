package game;

public class Measure {
      Beat[] beats;
      Platform platform;
      int width = 128;
      int height = 168;
      int cornerX, cornerY;

      public Measure(int cornerX, int cornerY, Platform plat) {
            this.cornerX = cornerX;
            this.cornerY = cornerY;
      		this.platform = plat;
            beats = new Beat[4];
            for(int i = 0; i < beats.length; i++) {
                  beats[i] = new Beat(cornerX + 8 + i*32, cornerY, plat);
            }
      }
      
      public void render() {
            platform.addThing(new RectThing(cornerX + width - 2, cornerY, 4, height));
            for(int i = 0; i < beats.length; i++) {
                  beats[i].render();
            }
      }
}

