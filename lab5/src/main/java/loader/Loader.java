package loader;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.image.TextureLoader;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Texture;
import java.io.*;
import java.net.URL;

public class Loader {
    private ClassLoader classLoader;
    private Canvas3D canvas;

    public Loader(Canvas3D canvas) {
        this.classLoader = Thread.currentThread().getContextClassLoader();
        this.canvas = canvas;
    }

    public Texture loadTexture(String path) {
        URL textureResource = classLoader.getResource(path);
        Texture texture =  new TextureLoader(textureResource.getPath(), canvas).getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        return texture;
    }
    
    public ImageComponent2D loadImage(String path) {
        URL textureResource = classLoader.getResource(path);
        return new TextureLoader(textureResource.getPath(), canvas).getImage();

    }
 
    public Scene loadModel(String path) throws FileNotFoundException {
        ObjectFile file = new ObjectFile(ObjectFile.RESIZE);
        file.setFlags(ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
        InputStream inputStream = classLoader.getResourceAsStream(path);
        return file.load(new BufferedReader(new InputStreamReader(inputStream)));
    }

}
