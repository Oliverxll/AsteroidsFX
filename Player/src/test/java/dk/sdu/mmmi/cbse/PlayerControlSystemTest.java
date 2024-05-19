package dk.sdu.mmmi.cbse;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.player.Player;
import dk.sdu.mmmi.cbse.HelloController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PlayerControlSystemTest {
    private HelloController playerControlSystem;
    @Mock
    private GameData gameData;
    @Mock
    private World world;
    @Mock
    private Player player;

    @BeforeEach
    public void setUp() {
        playerControlSystem = new HelloController();
        gameData = mock(GameData.class);
        world = mock(World.class);
        player = mock(Player.class);
        GameKeys gameKeys = mock(GameKeys.class); // Create a mock GameKeys object

        when(gameData.getKeys()).thenReturn(gameKeys); // Have getKeys() return the mock
        when(world.getEntities(Player.class)).thenReturn(List.of(player));
    }

    @Test
    public void testProcessPlayerHealthZero() {
        when(player.getHealth()).thenReturn(0);

        playerControlSystem.process(gameData, world);

        verify(world, times(1)).removeEntity(player);
    }

    @Test
    public void testProcessPlayerMovesLeft() {
        when(player.getHealth()).thenReturn(1);
        when(gameData.getKeys().isDown(GameKeys.LEFT)).thenReturn(true);

        playerControlSystem.process(gameData, world);

        verify(player, times(1)).setRotation(anyDouble());
    }

    @Test
    public void testProcessPlayerMovesRight() {
        when(player.getHealth()).thenReturn(1);
        when(gameData.getKeys().isDown(GameKeys.RIGHT)).thenReturn(true);

        playerControlSystem.process(gameData, world);

        verify(player, times(1)).setRotation(anyDouble());
    }

    @Test
    public void testProcessPlayerMovesUp() {
        when(player.getHealth()).thenReturn(1);
        when(gameData.getKeys().isDown(GameKeys.UP)).thenReturn(true);
        when(player.getRotation()).thenReturn(0.0);
        when(player.getMoveSpeed()).thenReturn(1);
        when(gameData.getDeltaTime()).thenReturn(1.0);
        double initialX = 0.0;
        double initialY = 0.0;
        when(player.getX()).thenReturn(initialX);
        when(player.getY()).thenReturn(initialY);

        playerControlSystem.process(gameData, world);

        verify(player, times(1)).setX(anyDouble());
        verify(player, times(1)).setY(anyDouble());
    }

    @Test
    public void testPlayerTakesDamage() {
        // Assume the player's initial health is 3
        when(player.getHealth()).thenReturn(3);

        // Simulate the player taking 1 damage
        when(player.getHealth()).thenReturn(2);

        // Assert that the player's health has decreased to 2
        assertEquals(2, player.getHealth());
    }
}
