package game;

public class Beat {
      Platform platform;
      int width = 16;
      int height = 168;
      int cornerX, cornerY;
      Note[] notes;

      public Beat(int cornerX, int cornerY, Platform plat) {
        this.cornerX = cornerX;
      	this.cornerY = cornerY;
      	this.platform = plat;
      	notes = new Note[8];
      	for(int i = 0; i < notes.length; i++) {
      		notes[i] = new Note(cornerX, cornerY + 20 + i*16, i, plat);
      	}
      }

      public void render() {
            platform.addThing(new RectThing(cornerX + 4, cornerY, 8, height));
            for(int i = 0; i < notes.length; i++) {
                  notes[i].render();
            }
      }
}

