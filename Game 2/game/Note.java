package game;
import java.awt.*;

public class Note {
      Color colors[] = {Color.red, Color.orange, Color.yellow, Color.green, Color.cyan, Color.blue, Color.magenta, Color.red};
      Platform platform;
      int width = 16;
      int height = 16;
      int cornerX, cornerY;
      int value;
      RectThing note;
      
      public Note(int cornerX, int cornerY, int value, Platform plat) {
            this.cornerX = cornerX;
            this.cornerY = cornerY;
            this.value = value;
      	this.platform = plat;
      }

      public void render() {
            note = new RectThing(cornerX, cornerY, width-2, height);
            note.setColor(colors[value]);
            platform.addThing(note);
      }
}

