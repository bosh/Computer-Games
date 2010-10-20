package game2;

// A RECTANGLE SHAPED THING

public class RectThing extends Thing
{
   public RectThing(double x, double y, double width, double height) {
      setBounds(x, y, width, height);
   }

   public void setBounds(double x, double y, double width, double height) {
      this.x = x + width / 2;
      this.y = y + height / 2;
      X[0] = x;
      Y[0] = y;
      X[1] = x + width;
      Y[1] = y;
      X[2] = x + width;
      Y[2] = y + height;
      X[3] = x;
      Y[3] = y + height;
      setShape(X, Y, 4);
   }
}

