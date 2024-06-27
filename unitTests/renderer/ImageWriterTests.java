package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;

public class ImageWriterTests {

    @Test
    void testImage() {
        Color cr = new Color(234, 166, 176);
        Color back = new Color(150, 0, 100);
        Camera c = new Camera.Builder()
                .setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setVpSize(10, 16)
                .setVpDistance(1)
                .build();

        ImageWriter iw = new ImageWriter ("first", 800, 500);

        for (int i = 0; i < iw.getNy(); i++) {
            for (int j = 0; j < iw.getNx(); j++) {
                if(i%50==0 || j%50==0)
                    iw.writePixel(j,i, cr);
                else
                    iw.writePixel(j,i, back);

            }
        }
        iw.writeToImage();

    }
}