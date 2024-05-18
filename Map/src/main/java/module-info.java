import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Map {
    exports dk.sdu.mmmi.cbse.map;
    requires Common;
    requires javafx.graphics;
    requires java.desktop;
    provides IGamePluginService with dk.sdu.mmmi.cbse.map.MapPlugin;
}