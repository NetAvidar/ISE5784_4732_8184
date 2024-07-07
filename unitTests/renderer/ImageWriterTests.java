package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;
import scene.*;

public class ImageWriterTests {

    @Test
    void testImage() {
        Scene s=new Scene("ttt");
        Color cr = new Color(234, 166, 176);
        Color back = new Color(250, 125, 100);

        Camera c = new Camera.Builder()
                .setDirection(new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setVpSize(10, 16)
                .setVpDistance(1)
                .setRayTracer(new SimpleRayTracer(s))                                 //todo:why do we need this line?
                .setImageWriter(new ImageWriter("check",800,500)) //todo:why do we need this line?
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