package game;

// GENERIC THING

import java.awt.*;

public class Thing {
   public String actionOnClick = "none";
   Color color = Color.black;
   double X[] = new double[100];
   double Y[] = new double[100];
   int n = 0;
   int IX[] = new int[100];
   int IY[] = new int[100];
   double x = 0, y = 0, mx = 0, my = 0;
   double moveX = 0, moveY = 0;
   Polygon polygon = null;
   boolean needToUpdateShape = false;
   Platform platform;
   double dx = 0, dy = 0, spawnTime = 99;
   boolean spawned = false, clickable = true;

   public boolean mouseUp(int x, int y) { return false; }
   public boolean keyUp(int key) { return false; }
   public boolean keyDown(int key) { return false; }

   public double getX() { return x; }
   public double getY() { return y; }
   public double getDx() { return dx; }
   public double getDy() { return dy; }
   public double getSpawnTime() { return spawnTime; }
   public boolean isSpawned() { return spawned; }
   public boolean isClickable() { return clickable; }

   public void setPlatform(Platform platform) { this.platform = platform; }
   public void setColor(Color color) { this.color = color; }
   public void setDx(double val) { dx = val; }
   public void setDy(double val) { dy = val; }
   public void setSpawnTime(double val) { spawnTime = val; }
   public void setSpawned(boolean val) { spawned = val; }
   public void setClickable(boolean val) { clickable = val; }
   public void setActionOnClick(String action) { actionOnClick = action; }

   public void update(Graphics g) {
      updateShape();
      g.setColor(color);
      g.fillPolygon(polygon);
      g.setColor(Color.black);
      g.drawPolygon(polygon);
   }

   public boolean contains(int x, int y) {
      updateShape();
      return polygon.contains(x, y);
   }

   public boolean mouseDown(int x, int y) {
      if (actionOnClick != "none") { doClickAction(); }
      if (isClickable()) { mx = x; my = y; }
      return false;
   }

   public boolean mouseDrag(int x, int y) {
      if (isClickable()) {
         setX(this.x + x - mx);
         setY(this.y + y - my);
         mx = x;
         my = y;
      }
      return false;
   }

   public void setX(double x) {
      moveX += x - this.x;
      this.x = x;
      needToUpdateShape = true;
   }

   public void setY(double y) {
      moveY += y - this.y;
      this.y = y;
      needToUpdateShape = true;
   }

   public void setShape(double X[], double Y[], int n) {
      this.n = n;
      for (int i = 0 ; i < n ; i++) {
         this.X[i] = X[i];
         this.Y[i] = Y[i];
      }
      needToUpdateShape = true;
   }

   void updateShape() {
      if (needToUpdateShape) {
         for (int i = 0 ; i < n ; i++) {
            X[i] += moveX;
            Y[i] += moveY;
            IX[i] = (int)X[i];
            IY[i] = (int)Y[i];
         }
	     moveX = moveY = 0;
	     polygon = new Polygon(IX, IY, n);
	     needToUpdateShape = false;
      }
   }
   
   public boolean doClickAction() {
      if (actionOnClick == "explode") {
         platform.incrementScore();
         platform.removeThing(this);
      }
      return false;
   }
}
