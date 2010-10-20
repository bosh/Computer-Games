package game2;

// DETECT COLLISIONS BETWEEN TWO CONVEX POLYGONS

public class CollisionDetector
{
   // THERE IS A COLLISION IF NO EDGE OF EITHER POLYGON DIVIDES THE TWO POLYGONS FROM EACH OTHER

   public static boolean isCollision(double X1[], double Y1[], int n1, double X2[], double Y2[], int n2) {

      for (int i = 0 ; i < n1 ; i++)
         if (dividedBy(X1, Y1, n1, X2, Y2, n2, X1[(i + 1) % n1] - X1[i], Y1[(i + 1) % n1] - Y1[i]))
	    return false;

      for (int i = 0 ; i < n2 ; i++)
         if (dividedBy(X1, Y1, n1, X2, Y2, n2, X2[(i + 1) % n2] - X2[i], Y2[(i + 1) % n2] - Y2[i]))
	    return false;

      return true;
   }

   // ARE THE TWO POLYGONS DIVIDED BY THIS LINE?

   static boolean dividedBy(double X1[], double Y1[], int n1, double X2[], double Y2[], int n2, double dx, double dy) {

      double min1 = 1000000, max1 = -min1;
      for (int i = 0 ; i < n1 ; i++) {
         double t = dy * X1[i] - dx * Y1[i];
	 min1 = Math.min(min1, t);
	 max1 = Math.max(max1, t);
      }

      double min2 = 1000000, max2 = -min2;
      for (int i = 0 ; i < n2 ; i++) {
         double t = dy * X2[i] - dx * Y2[i];
	 min2 = Math.min(min2, t);
	 max2 = Math.max(max2, t);
      }

      return Math.max(min1, min2) > Math.min(max1, max2);
   }
}

