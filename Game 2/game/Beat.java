package game;

public class Beat {
      int[] notes;
      Platform platform;
      int width = 512;
      int height = 168;
      int cornerX, cornerY;

      public Beat(int cornerX, int cornerY, Platform plat) {
        cornerX = cornerX;
      	cornerY = cornerY;
      	platform = plat;
      	//notes = platform.notes;
      }
}

