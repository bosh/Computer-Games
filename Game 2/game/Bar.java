package game;

// The Bar of measures, one per player
public class Bar {
      Measure[] measures;
      Platform platform;
      int width = 512;
      int height = 168;
      int tlCorner;

      public Bar(int topLeftCornerPixel, Platform plat) {
            tlCorner = topLeftCornerPixel;
            this.platform = plat;
            measures = new Measure[4];
            for(int i = 0; i < measures.length; i++) {
                  measures[i] = new Measure(64 + i*128, tlCorner, plat);
            }
      }
}

