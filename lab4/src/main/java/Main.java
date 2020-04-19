import com.sun.j3d.utils.applet.MainFrame;

public class Main {
    public static void main(String[] args) {
        var obj = new App();
        MainFrame mainFrame = new MainFrame(obj, 700, 700);
        mainFrame.run();
    }
}