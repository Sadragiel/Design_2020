package background;

import loader.Loader;

import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Group;
import javax.vecmath.Point3d;

public class BackgroundImage {
    private String source = "backgrounds/background.jpg";
    Background background;


    public BackgroundImage(Loader loader) {
        this.background = new Background(loader.loadImage(source));
        background.setImageScaleMode(Background.SCALE_FIT_MAX);
        background.setApplicationBounds(new BoundingSphere(new Point3d(),1000));
        background.setCapability(Background.ALLOW_IMAGE_WRITE);
    }

    public void applyFor(Group root) {
        root.addChild(background);
    }
}
