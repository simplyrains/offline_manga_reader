import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/**
 * This class demonstrates how to load an Image from an external file
 */
public class LoadImageApp extends Component {  
	private static final long serialVersionUID = 523276030756839284L;
	BufferedImage img;

    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
 
    public LoadImageApp(String direc) {
       
    	try {
           img = ImageIO.read(new File(direc));
       } catch (IOException e) {
       }
       img=scaleImage(img, 700);
    }
    public BufferedImage scaleImage(BufferedImage image, int maxSideLength) {
        assert(maxSideLength > 0);
        double originalWidth = image.getWidth();
        double originalHeight = image.getHeight();
        double scaleFactor = 0.0;
        if (originalWidth > originalHeight) {
            scaleFactor = ((double) maxSideLength / originalWidth);
        }
        else {
            scaleFactor = ((double) maxSideLength / originalHeight);
        }
        // create smaller image
        BufferedImage img = new BufferedImage((int) (originalWidth * scaleFactor), (int) (originalHeight * scaleFactor), BufferedImage.TYPE_INT_RGB);
        // fast scale (Java 1.4 & 1.5)
        Graphics g = img.getGraphics();
        g.drawImage(image, 0, 0, img.getWidth(), img.getHeight(), null);
        return img;
    }
 
    public Dimension getPreferredSize() {
        if (img == null) {
             return new Dimension(100,100);
        } else {
           return new Dimension(img.getWidth(null), img.getHeight(null));
       }
    }
}