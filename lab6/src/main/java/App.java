import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class App extends JFrame  {
    private final static String camouflageTextureLocation = "textures/texture.jpg";
    private final static String helicopterModelLocation = "helicopter/helicopter.obj";
    private final static String backgroundLocation = "backgrounds/sky.jpg";

    private ClassLoader classLoader;
    private BranchGroup root;
    private SimpleUniverse universe;
    private Canvas3D canvas;

    public App() {
        classLoader = Thread.currentThread().getContextClassLoader();
        root = new BranchGroup();

        configureWindow();
        configureCanvas();
        configureUniverse();
        configureNavigation();
        addLightToUniverse();
        configureSceneGraph();
    }

    private void configureWindow() {
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void configureCanvas() {
        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setDoubleBufferEnable(true);
        getContentPane().add(canvas, BorderLayout.CENTER);
    }

    private void configureUniverse() {
        universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
    }

    private void configureNavigation() {
        var ob = new OrbitBehavior(canvas);
        ob.setReverseRotate(true);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(), Double.MAX_VALUE));
        universe.getViewingPlatform().setViewPlatformBehavior(ob);
    }

    private void addLightToUniverse() {
        var dirLight = new DirectionalLight(
                new Color3f(Color.WHITE),
                new Vector3f(4.0f, -7.0f, -12.0f)
        );
        dirLight.setInfluencingBounds(new BoundingSphere(new Point3d(), 1000));
        root.addChild(dirLight);
    }

    private void configureSceneGraph() {
        addImageInTheSkyBackground();
        var helicopter = getSceneHelicopterFromFile();
        var helicopterTransformGroup = new TransformGroup();

        TransformGroup initTransformGroup = new TransformGroup();
        setInitialTransformation(initTransformGroup);

        var helicopterSceneGroup = helicopter.getSceneGroup();
        helicopterSceneGroup.removeChild((Shape3D)helicopter.getNamedObjects().get("big_propeller"));
        helicopterSceneGroup.removeChild((Shape3D)helicopter.getNamedObjects().get("small_propeller"));

        initTransformGroup.addChild(helicopterSceneGroup);
        initTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        initTransformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);

        helicopterTransformGroup.addChild(initTransformGroup);
        helicopterTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(helicopterTransformGroup);
        addAppearanceForHelicopter(helicopter);

        helicopterSceneGroup.addChild(
                applyRotationForShape(((Shape3D)helicopter.getNamedObjects().get("big_propeller")).cloneTree(),
                        transform3D -> transform3D.setTranslation(new Vector3f(0, 0, -0.212f)), 320));

        helicopterSceneGroup.addChild(
                applyRotationForShape(((Shape3D)helicopter.getNamedObjects().get("small_propeller")).cloneTree(),
                        transform3D -> {
                            transform3D.rotZ(Math.PI/2);
                            transform3D.setTranslation(new Vector3f(0, 0.061f, 0.845f));
                        }, 200));

        root.compile();
        universe.addBranchGraph(root);
    }

    private void setInitialTransformation(TransformGroup transformGroup) {
        var transformYAxis = new Transform3D();
        transformYAxis.rotY(0);
        var transformXAxis = new Transform3D();
        transformXAxis.rotX(0);
        transformYAxis.mul(transformXAxis);
        transformGroup.setTransform(transformYAxis);
    }

    private Node applyRotationForShape(Node shape, Consumer<Transform3D> transformInitialPosition, int rotateDuration) {
        var transformGroup = new TransformGroup();
        transformGroup.addChild(shape);

        var transform3D = new Transform3D();
        transformInitialPosition.accept(transform3D);

        var alphaRotation = new Alpha(Integer.MAX_VALUE, Alpha.INCREASING_ENABLE,
                0,0, rotateDuration,0,0,
                0, 0,0);

        var rotationInterpolator = new RotationInterpolator(alphaRotation, transformGroup,
                transform3D, (float) Math.PI * 2, 0.0f);

        var bounds = new BoundingSphere(new Point3d(), Double.MAX_VALUE);

        rotationInterpolator.setSchedulingBounds(bounds);
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformGroup.addChild(rotationInterpolator);

        return transformGroup;
    }

    private void addAppearanceForHelicopter(Scene helicopter) {
        var mainBody = (Shape3D) helicopter.getNamedObjects().get("main_body_");
        var somePartOfBody = (Shape3D) helicopter.getNamedObjects().get("decal");
        addAppearanceForShapes(appearance -> {
            appearance.setTexture(getCamouflageTexture());
            var texAttr = new TextureAttributes();
            texAttr.setTextureMode(TextureAttributes.MODULATE);
            appearance.setTextureAttributes(texAttr);
            var color = new Color3f(new Color(134, 133, 133));
            appearance.setMaterial(new Material(color, color, color, color,10));
        }, mainBody, somePartOfBody);

        var glass = (Shape3D) helicopter.getNamedObjects().get("glass");
        setAppearanceMaterialAsColorForShapes(new Color(185, 207, 210), 300, glass);

        var smallPropeller = (Shape3D) helicopter.getNamedObjects().get("small_propeller");
        var bigPropeller = (Shape3D) helicopter.getNamedObjects().get("big_propeller");
        setAppearanceMaterialAsColorForShapes(new Color(1, 28, 1), 3, smallPropeller, bigPropeller);

        var otherParts = (Shape3D) helicopter.getNamedObjects().get("main_");
        var anotherParts = (Shape3D) helicopter.getNamedObjects().get("alpha");
        setAppearanceMaterialAsColorForShapes(new Color(25, 29, 25), 3, otherParts, anotherParts);

        var rocketHeadings = (Shape3D) helicopter.getNamedObjects().get("missile_gl");
        setAppearanceMaterialAsColorForShapes(new Color(92, 10, 10), 3, rocketHeadings);

        var rockets = (Shape3D) helicopter.getNamedObjects().get("missile_1");
        setAppearanceMaterialAsColorForShapes(new Color(42, 61, 42), 3, rockets);
    }

    private void setAppearanceMaterialAsColorForShapes(Color color, int shininess, Shape3D... shapes) {
        addAppearanceForShapes(appearance -> {
            var colorVector = new Color3f(color);
            appearance.setMaterial(new Material(colorVector, colorVector, colorVector, colorVector, shininess));
        }, shapes);
    }

    private void addAppearanceForShapes(Consumer<Appearance> changeAppearance, Shape3D... shapes) {
        for (var shape : shapes) {
            var appearance = new Appearance();
            changeAppearance.accept(appearance);
            shape.setAppearance(appearance);
        }
    }

    private Scene getSceneHelicopterFromFile() {
        var file = new ObjectFile(ObjectFile.RESIZE);
        file.setFlags(ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
        var inputStream = classLoader.getResourceAsStream(helicopterModelLocation);
        try {
            return file.load(new BufferedReader(new InputStreamReader(inputStream)));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return null;
        }
    }

    private TextureLoader getTextureLoader(String path) {
        var textureResource = classLoader.getResource(path);
        return new TextureLoader(textureResource.getPath(), canvas);
    }

    private Texture getCamouflageTexture() {
        var texture = getTextureLoader(camouflageTextureLocation).getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(1, 1, 1, 1));
        return texture;
    }

    private void addImageInTheSkyBackground() {
        var background = new Background(getTextureLoader(backgroundLocation).getImage());
        background.setImageScaleMode(Background.SCALE_FIT_MAX);
        background.setApplicationBounds(new BoundingSphere(new Point3d(), Double.MAX_VALUE));
        background.setCapability(Background.ALLOW_IMAGE_WRITE);
        root.addChild(background);
    }
}
