package dk.sdu.mmmi.cbse.map;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javax.imageio.ImageIO;
import java.io.InputStream;
import java.net.URL;


public class MapPlugin implements IGamePluginService {
    private ImageView map;

    @Override
    public void start(GameData gameData, World world) {
        System.out.println("Loading map");

        try {
            map = new ImageView(getImage());
            map.setFitWidth(800);
            map.setFitHeight(800);
            System.out.println(map.getImage().getUrl());
            System.out.println(getImage().getUrl());
        } catch (Exception e) {
            System.out.println("No map image found.");
        }

    }

    public Image getImage(){
        URL imageUrl = MapPlugin.class.getResource("/map.jpg");
        if (imageUrl != null) {
            Image image = new Image(imageUrl.toString());
            image.progressProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.doubleValue() == 1.0) {
                    System.out.println("Image loaded: " + image.getUrl());
                }
            });
            return image;
        }
        return null;
    }

    public ImageView getMap() {
        return map;
    }

    @Override
    public void stop(GameData gameData, World world) {
        map = null;
    }
}
