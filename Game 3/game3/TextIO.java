/*
   Save text to a local file.
   Load text from a URL, local file or input stream.
*/

package game3;

import java.io.*;
import java.net.*;

public class TextIO
{
   FileOutputStream fout;

   public void openOutput(String fileName) {
      try {
         fout = new FileOutputStream(new File(fileName));
      } catch (Exception e) { System.err.println(e); }
   }

   public void writeOutput(String text) {
      try {
         fout.write(text.getBytes());
      } catch (Exception e) { System.err.println(e); }
   }

   public void closeOutput() {
      try {
         fout.close();
      } catch (Exception e) { System.err.println(e); }
   }

   public void save(String fileName, String text) {
      try {
         FileOutputStream fout = new FileOutputStream(new File(fileName));
         fout.write(text.getBytes());
         fout.close();
      } catch (Exception e) { System.err.println(e); }
   }

   public String load(URL url) {
      try {
         return load(url.openStream());
      } catch (Exception e) { }
      return null;
   }

   public String load(String fileName) {
      try {
         File file = new File(fileName);
         URL  url  = file.toURI().toURL();
         return load(url);
      } catch (Exception e) { }
      return null;
   }

   public String load(InputStream in) {
      try {
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         byte buf[] = new byte[1024];
         while (true) {
            int n = in.read(buf);
            if (n < 0)
               break;
            out.write(buf, 0, n);
         }
         return new String(out.toByteArray());
      } catch (Exception e) { e.printStackTrace(); }
      return null;
   }
}

