package game;
import java.awt.*;

public class Note {
      Color colors[] = {Color.red, Color.orange, Color.yellow, Color.green, Color.cyan, Color.blue, Color.magenta};
      Platform platform;
      int width = 16;
      int height = 16;
      int cornerX, cornerY;
      int value;

      public Note(int cornerX, int cornerY, int value, Platform plat) {
            cornerX = cornerX;
            cornerY = cornerY;
            value = value;
      	platform = plat;
      }
      public void render() {
            RectThing note = new RectThing(cornerX, cornerY, width, height);
            note.setColor(colors[value]);
            platform.addThing(note);
      }
}

