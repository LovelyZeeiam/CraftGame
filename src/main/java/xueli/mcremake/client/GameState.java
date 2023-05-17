package xueli.mcremake.client;

import xueli.game2.input.KeyBindings;
import xueli.mcremake.client.state.ClientPlayerState;
import xueli.mcremake.core.item.ItemType;
import xueli.mcremake.core.world.WorldDimension;
import xueli.mcremake.registry.GameRegistry;

// TODO: maybe each type of game state can also be a component! 
public class GameState {

    public int tickCount = 0;
    public double partialTick = 0.0;
    
    public final ClientPlayerState player = new ClientPlayerState();
    public ItemType selectedItemType = GameRegistry.ITEM_BLOCK_DIRT;

    WorldDimension worldDirect;
    public ListenableBufferedWorldAccessible world;

    public final KeyBindings keyBindings = new KeyBindings();

}
