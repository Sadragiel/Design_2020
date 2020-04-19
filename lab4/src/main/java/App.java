import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;
import light.Light;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends Applet implements ActionListener {
    private Button changeRotationButton;
    private TransformGroup transformGroup;
    private Transform3D transform3D;
    private Timer timer;

    private double angleY = 0;
    private double angleX = 0;
    private boolean rotateY = true;
    private int dimension = 3;

    public App() {
        changeRotationButton = new Button("changeRotation");
        transformGroup = new TransformGroup();
        transform3D = new Transform3D();
        timer = new Timer(50, this);

        SimpleUniverse universe = getUniverse();
        universe.addBranchGraph(getScene());
        timer.start();
    }

    private SimpleUniverse getUniverse() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas = new Canvas3D(config);
        add("Center", canvas);

        Panel panel = new Panel();
        panel.add(changeRotationButton);
        add("North", panel);

        changeRotationButton.addActionListener(this);

        SimpleUniverse universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();

        return universe;
    }

    private BranchGroup getScene() {
        var root = new BranchGroup();
        setupTransform();
        root.addChild(transformGroup);
        buildCubeRubikScene();

        Light light = new Light();
        light.applyFor(root);

        return root;
    }

    private void setupTransform() {
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        transformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
    }

    private BranchGroup getCubeGroup() {
        BranchGroup cubeGroup = new BranchGroup();
        cubeGroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        cubeGroup.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        cubeGroup.setCapability(BranchGroup.ALLOW_DETACH);
        return cubeGroup;
    }

    private void buildCubeRubikScene() {
        BranchGroup cubeGroup = getCubeGroup();

        float step = 1f / dimension;
        float width = (step / 2f) * 0.85f;
        float start = -0.5f + width;

        for (int z = 0; z < dimension; z++) {
            for (int y = 0; y < dimension; y++) {
                for (int x = 0; x < dimension; x++) {
                    Group cube = buildCube(
                            new Vector3f(start + step * x, start + step * y, start + step * z),
                            width
                    );
                    cubeGroup.addChild(cube);
                }
            }
        }
        transformGroup.addChild(cubeGroup);
    }

    private Group buildCube(Vector3f v, float width) {
        TransformGroup cubeWrapper = new TransformGroup();

        var transform = new Transform3D();
        transform.setTranslation(v);
        cubeWrapper.setTransform(transform);

        Group cube = getCube(width);
        cubeWrapper.addChild(cube);

        return cubeWrapper;
    }

    protected Group getCube(float width) {
        BranchGroup cube = new BranchGroup();
        Box box = new Box(width, width, width, Box.GENERATE_TEXTURE_COORDS, null);
        cube.addChild(box);

        int[] sidesList = new int[] {
                Box.BACK,
                Box.TOP,
                Box.BOTTOM,
                Box.LEFT,
                Box.RIGHT,
                Box.FRONT
        };

        Color[] colorsList = new Color[] {
                Color.RED,
                Color.GREEN,
                Color.YELLOW,
                Color.BLUE,
                Color.WHITE,
                new Color(255, 75, 0)
        };

        for (int i = 0; i < sidesList.length; i++) {
            Appearance ap = new Appearance();
            ap.setColoringAttributes(new ColoringAttributes(new Color3f(colorsList[i]), ColoringAttributes.SHADE_GOURAUD));
            box.setAppearance(sidesList[i], ap);
        }

        return cube;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == changeRotationButton) {
            rotateY = !rotateY;
        }
        else {
            if (rotateY) {
                transform3D.rotY(angleY);
                angleY += 0.05;
                if (angleY >= 25) {
                    rotateY = !rotateY;
                    angleY = 0;
                }
            }
            else {
                transform3D.rotX(angleX);
                angleX += 0.05;
                if (angleX >= 25) {
                    rotateY = !rotateY;
                    angleX = 0;
                }
            }
            transformGroup.setTransform(transform3D);
        }
    }
}