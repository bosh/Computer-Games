package game;

public class PolygonThing extends Thing
{
   public PolygonThing(double[] x, double[] y) {
      int length = Math.min(x.length, y.length);
      double sumX = 0, sumY = 0;
      for(int i = 0; i < length; i++) {
        sumX += x[i];
        sumY += y[i];
      }
      this.x = sumX / length;
      this.y = sumY / length;
      X = x;
      Y = y;
      setShape(X, Y, length);
   }
}

