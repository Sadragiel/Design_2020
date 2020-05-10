package turtle;

import com.sun.j3d.loaders.Scene;
import loader.Loader;

import javax.media.j3d.*;
import java.io.*;
import java.util.Map;

public class Turtle  {
    private String modelLocation = "turtle/Turtle_Mesh.OBJ";

    TransformGroup self;

    private Scene layout;
    Map<String, Shape3D> nameMap;
    Loader loader;

    public Turtle(Loader loader) throws FileNotFoundException {
        this.loader = loader;
        this.setLayout();
        this.setLayoutNameMap();
        this.setupSelf();
        this.setupAppearance();
    }

    public void applyFor(Group root) {
        root.addChild(self);
    }

    public void setTransform(Transform3D transform) {
        self.setTransform(transform);
    }

    private void setLayout() throws FileNotFoundException {
        this.layout = this.loader.loadModel(this.modelLocation);
    }

    private void setupSelf() {
        self = new TransformGroup();
        self.addChild(this.layout.getSceneGroup());
        self.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    }

    private void setLayoutNameMap() {
        nameMap = layout.getNamedObjects();
    }

    private void setupAppearance() {
        this.appearance("malla_torutga", "turtle/Turtle_Dif.jpg");
        this.appearance("caparazon", "turtle/Shell_Dif.tif");
        this.appearance("circle008", "turtle/Eye.png");
        this.appearance("object002", "turtle/Eye.png");
        this.appearance("object001", "turtle/Eye.png");
        this.appearance("circle005", "turtle/Eye.png");
    }

    private void appearance(String shapeName, String pathToTexture) {
        Appearance appearance = new Appearance();
        Texture texture = this.loader.loadTexture(pathToTexture);
        appearance.setTexture(texture);
        Material material = new Material();
        material.setLightingEnable(true);
        appearance.setMaterial(material);

        Shape3D shape = this.nameMap.get(shapeName);
        shape.setAppearance(appearance);
    }



}
