package game;
import java.awt.*;

// The Bar of measures, one per player
public class Bar {
      Measure[] measures;
      Platform platform;
      int width = 512;
      int height = 168;
      int tlCorner;
      RectThing bar;

      public Bar(int topLeftCornerPixel, Platform plat) {
            this.tlCorner = topLeftCornerPixel;
            this.platform = plat;
            measures = new Measure[4];
            for(int i = 0; i < measures.length; i++) {
                  measures[i] = new Measure(64 + i*128, tlCorner, plat);
            }
      }

      public void render() {
            bar = new RectThing(64, tlCorner, width, height);
            bar.setColor(Color.lightGray);
            platform.addThing(bar);
            for(int i = 0; i < measures.length; i++) {
                  measures[i].render();
            }
      }
}

