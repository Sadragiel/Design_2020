import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class App extends  JPanel implements ActionListener {
    private static int maxWidth;
    private static int maxHeight;

    private static int scale = 15;

    private static int Scale(double base) {
        return (int)(base * scale);
    }

    Timer timer;
    private double opacityScale = 1;
    private double opacityDelta = 0.01;

    private double tx;
    private double ty;
    int currentAngular = 0;
    int maxAngular = 360;

    private static int pathRadius;

    private static int positionOfFullPicture_Y;
    private static int positionOfFullPicture_X;

    public App() {
        timer = new Timer(10, this);
        timer.start();
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHints(rh);
        addBorderSetToApp(graphics2D);

        graphics2D.translate(tx, ty);
        graphics2D.setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)opacityScale)
        );
        addBoxToApp(graphics2D);
        addScreenToApp(graphics2D);
        addButtonSetToApp(graphics2D);
        addAntennaSetToApp(graphics2D);
    }

    private void addBoxToApp(Graphics2D app) {
        Color previousColor = app.getColor();
        int width = Scale(6);
        int height = Scale(4);
        int positionX = Scale(1) + positionOfFullPicture_X;
        int positionY = Scale(2) + positionOfFullPicture_Y;
        Color color = new Color(255, 163, 10);
        app.setPaint(color);
        app.fillRect(positionX, positionY, width, height);
        app.setPaint(previousColor);
    }

    private void addScreenToApp(Graphics2D app) {
        Color previousColor = app.getColor();
        int width = Scale(3);
        int height = Scale(2);
        int positionX = Scale(2) + positionOfFullPicture_X;
        int positionY = Scale(3) + positionOfFullPicture_Y;
        Color color1 = new Color(100, 100, 100);
        Color color2 = new Color(200, 200, 200);

        int x1 = Scale(3), y1 = Scale(4);
        int x2 = Scale(3.5), y2 = Scale(3.5);

        GradientPaint gradient = new GradientPaint(x1, y1, color1, x2, y2, color2, true);
        app.setPaint(gradient);
        app.fillRect(positionX, positionY, width, height);
        app.setPaint(previousColor);
    }

    private void addButtonSetToApp(Graphics2D app) {
        Color previousColor = app.getColor();
        int positionX = Scale(6) + positionOfFullPicture_X;
        int positionY = Scale(3) + positionOfFullPicture_Y;
        int verticalIncrement = Scale(1);
        int radius = Scale(0.3);
        Color buttonColor = new Color(9, 0, 16);
        int numberOfButtons = 3;

        app.setPaint(buttonColor);
        for(int i = 0; i < numberOfButtons; i++) {
            app.fillOval(positionX, positionY + i * verticalIncrement, radius, radius);
        }
        app.setPaint(previousColor);
    }

    private void addAntennaSetToApp(Graphics2D app) {
        int[] xPoints = {2, 3, 4};
        int[] yPoints = {1, 2, 1};
        int nPoints = 3;
        int width = Scale(0.2);
        for(int i = 0; i < xPoints.length; i++){
            xPoints[i] = Scale(xPoints[i]) + positionOfFullPicture_X;
            yPoints[i] = Scale(yPoints[i]) + positionOfFullPicture_Y;
        }

        BasicStroke stroke = new BasicStroke(width, BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_BEVEL);
        app.setStroke(stroke);
        app.drawPolyline(xPoints, yPoints, nPoints);

        BasicStroke defaultStroke = new BasicStroke();
        app.setStroke(defaultStroke);
    }

    private void addBorderSetToApp(Graphics2D app) {
        int width = Scale(2);
        BasicStroke stroke = new BasicStroke(width, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER);
        app.setStroke(stroke);

        int shift = Scale(3);

        app.drawRect(shift, shift, maxWidth - 2 * shift, maxHeight - 2* shift);

        BasicStroke defaultStroke = new BasicStroke();
        app.setStroke(defaultStroke);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.add(new App());
        frame.setVisible(true);

        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
        maxWidth = size.width - insets.left - insets.right - 1;
        maxHeight = size.height - insets.top - insets.bottom - 1;

        pathRadius = (maxHeight - Scale(15)) / 2;
        positionOfFullPicture_X = maxWidth / 2 - Scale(3.8);
        positionOfFullPicture_Y = maxHeight / 2 - Scale(3.5);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (opacityScale < 0.01 || opacityScale > 0.99) {
            opacityDelta *= -1;
        }
        opacityScale += opacityDelta;

        tx = pathRadius * Math.cos(currentAngular * Math.PI / 180);
        ty = pathRadius * Math.sin(currentAngular * Math.PI / 180);
        currentAngular++;
        if (currentAngular > maxAngular)
            currentAngular = 0;

        repaint();
    }
}
