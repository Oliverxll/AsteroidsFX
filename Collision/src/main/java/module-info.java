import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonPlayer;
    requires CommonAsteroids;
    requires CommonBullet;
    requires CommonEnemy;
    requires java.net.http;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetector;
}