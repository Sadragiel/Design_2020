import background.BackgroundImage;
import com.sun.j3d.utils.universe.SimpleUniverse;
import light.Light;
import loader.Loader;
import turtle.Turtle;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.swing.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class App extends JFrame implements KeyListener {
    private Canvas3D canvas;
    private SimpleUniverse universe;
    private BranchGroup root;

    private Turtle turtle;
    private BackgroundImage backgroundImage;

    private float x_eye_loc = -3.985f;
    private float y_eye_loc = -0.07f;
    private float z_eye_loc = 1.635f;
    private float angle_eye = 0;
    private final Transform3D transform3D = new Transform3D();
    private final Transform3D rotateTransformX = new Transform3D();
    private final Transform3D rotateTransformY = new Transform3D();
    private final Transform3D rotateTransformZ = new Transform3D();


    public App() throws IOException {
        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        universe = new SimpleUniverse(canvas);
        root = new BranchGroup();

        this.configure();

        Loader loader = new Loader(this.canvas);
        Light light = new Light();
        light.applyFor(this.root);
        turtle = new Turtle(loader);
        turtle.applyFor(this.root);
        backgroundImage = new BackgroundImage(loader);
        backgroundImage.applyFor(this.root);

        changeViewAngle(0);

        root.compile();
        universe.addBranchGraph(root);
    }

    private void configure() {
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas.setDoubleBufferEnable(true);
        getContentPane().add(canvas, BorderLayout.CENTER);
        canvas.addKeyListener(this);

        universe.getViewingPlatform().setNominalViewingTransform();
    }

    private void changeViewAngle(float angle) {
        var vp = universe.getViewingPlatform();
        var transform = new Transform3D();
        transform.lookAt(
                new Point3d(x_eye_loc * Math.cos(angle), y_eye_loc, z_eye_loc + x_eye_loc * Math.sin(angle)),
                new Point3d(1, 0, 0),
                new Vector3d(1, 0, 0)
        );
        transform.invert();
        vp.getViewPlatformTransform().setTransform(transform);
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        int keyCode = e.getKeyCode();
        float diff = 0.05f;

        switch (keyCode) {
            case KeyEvent.VK_LEFT: {
                angle_eye += diff;
                changeViewAngle(angle_eye);
            } break;
            case KeyEvent.VK_RIGHT: {
                angle_eye -= diff;
                changeViewAngle(angle_eye);
            } break;
            case KeyEvent.VK_X: {
                rotateTransformX.rotX(diff);
                transform3D.mul(rotateTransformX);
                this.turtle.setTransform(transform3D);
            } break;
            case KeyEvent.VK_C: {
                rotateTransformY.rotY(diff);
                transform3D.mul(rotateTransformY);
                this.turtle.setTransform(transform3D);
            } break;
            case KeyEvent.VK_Z: {
                rotateTransformZ.rotZ(diff);
                transform3D.mul(rotateTransformZ);
                this.turtle.setTransform(transform3D);
            } break;
            case KeyEvent.VK_BACK_SPACE: {
                rotateTransformX.setIdentity();
                rotateTransformY.setIdentity();
                rotateTransformZ.setIdentity();
                transform3D.setIdentity();
                angle_eye = 0;
                changeViewAngle(angle_eye);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }
}
