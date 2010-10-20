package game3;
import java.awt.*;

public class Beat {
      Platform platform;
      int width = 16;
      int height = 168;
      int cornerX, cornerY;
      Note[] notes;
      RectThing selector;
      int number;

      public Beat(int number, int cornerX, int cornerY, Platform plat) {
            this.number = number;
            this.cornerX = cornerX;
            this.cornerY = cornerY;
            this.platform = plat;
            notes = new Note[8];
            for(int i = 0; i < notes.length; i++) {
                  notes[i] = new Note(cornerX, cornerY + 20 + i*16, 7 - i, plat);
            }
      }

      public void render() {
            platform.addThing(new RectThing(cornerX + 3, cornerY, 8, height));
                  for(int i = 0; i < notes.length; i++) {
                  notes[i].render();
            }
            platform.addThing(selector = new RectThing(cornerX-2, cornerY+3, 18, 9));
            selector.setColor(Color.white);
            selector.dragOnY = true;
            platform.setCollisionTracked(selector);
      }
      public int constructNote() {
            for(int i = 0; i < notes.length; i++) {
                  if (platform.colliding(notes[i].note, selector)) {
                        return notes[i].value;
                  }
            }
            return -1;
      }
}

