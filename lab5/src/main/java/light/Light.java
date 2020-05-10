package light;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Group;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.awt.*;

public class Light {
    BoundingSphere boundingSphere;
    public Light() {
        this.boundingSphere = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100);
    }

    public void applyFor(Group group) {
        var sunLightColor = new Color(200, 255, 253);
        var lightDirect = new DirectionalLight(
                new Color3f(sunLightColor),
                new Vector3f(4.0f, -7.0f, -12.0f)
        );
        lightDirect.setInfluencingBounds(boundingSphere);
        group.addChild(lightDirect);

        var ambientLightNode = new AmbientLight(
                new Color3f(new Color(255, 226, 142))
        );
        ambientLightNode.setInfluencingBounds(boundingSphere);
        group.addChild(ambientLightNode);
    }
}