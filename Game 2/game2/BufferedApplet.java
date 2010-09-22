package game2;

/*
<body bgcolor=white>
<h2>
Java class to implement a double buffered applet
</h2>
&nbsp; &nbsp; &nbsp;
by Ken Perlin @ NYU, 1998.
<p>
<font color=red><i>
You have my permission to use freely, as long
as you keep the attribution. - Ken Perlin
</i></font>
<p>
<b>
Note: this <i>BufferedApplet.html</i> file also
works as a legal <i>BufferedApplet.java</i> file.
If you save the source under that name, you can just run
<i>javac</i> on it.
</b>
<pre>
*/
import java.awt.*;

public abstract class BufferedApplet extends java.applet.Applet implements Runnable
{
   public boolean damage = true; //<b>Flag advising app. program to rerender</b>
   public boolean animating = false;
   public abstract void render(Graphics g); //<b>App. defines render method</b>

   Image bufferImage = null;            //<b>Image for the double buffer</b>
   private Graphics bufferGraphics = null;  //<b>Canvas for double buffer</b>
   private Thread t;                    //<b>Background thread for rendering</b>
   private Rectangle r = new Rectangle(0,0,0,0); //<b>Double buffer bounds</b>

   //<b>Extend the start,stop,run methods to implement double buffering.</b>

   public void start() { if (t == null) { t = new Thread(this); t.start(); } }
   public void stop()  { if (t != null) { t.stop(); t = null; } }
   public void run() {
      try {
         while (true) { repaint(); t.sleep(30); } //<b>Repaint each 30 msecs</b>
      }
      catch(InterruptedException e){}; //<b>Catch interruptions of sleep().</b>
   }

   //<b>Update(Graphics) is called by repaint() - the system adds canvas.</b>
   //<b>Extend update method to create a double buffer whenever necessary.</b>

   public void update(Graphics g) {
      if (r.width != bounds().width || r.height != bounds().height) {
         bufferImage    = createImage(bounds().width, bounds().height);
         bufferGraphics = bufferImage.getGraphics(); //<b>Applet size change</b>
         r = bounds();                               //<b>Make double buffer</b>
         damage = true;                              //<b>Tell application.</b>
      }
      if (damage)
         render(bufferGraphics); //<b>Ask application to render to buffer,</b>
      damage = animating;        //
      paint(g);                  //<b>paste buffered image onto the applet.</b>
   }

   //<b>Separate paint method for application to extend if needed.</b>

   public void paint(Graphics g) {
      if (bufferImage != null)              //
         g.drawImage(bufferImage,0,0,this); //<b>Paste result of render().</b>
   }
}
