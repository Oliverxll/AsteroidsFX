package dk.sdu.mmmi.cbse;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class HelloFrom implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        System.out.println("Hello from Player");
    }

    @Override
    public void stop(GameData gameData, World world) {

    }
}
