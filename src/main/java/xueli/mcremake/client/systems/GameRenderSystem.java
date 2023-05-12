package xueli.mcremake.client.systems;

import org.lwjgl.opengl.GL30;

import xueli.game2.Vector;
import xueli.game2.camera3d.MovableCamera;
import xueli.game2.renderer.ui.Gui;
import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.client.IGameSystem;
import xueli.mcremake.client.WorldRenderer;
import xueli.mcremake.client.renderer.item.ItemRenderMaster;

public class GameRenderSystem implements IGameSystem {

    @Override
    public void start(CraftGameClient ctx) {
        
    }

    @Override
    public void update(CraftGameClient ctx) {
        GL30.glClearColor(0.7f, 0.7f, 1.0f, 1.0f);
        this.renderWorld(ctx);
        this.renderHud(ctx);

    }

    private void renderWorld(CraftGameClient ctx) {
        Vector renderPos = ctx.state.player.positionOnRender;
        
        WorldRenderer renderer = ctx.getRenderResources(WorldRenderer.class);
        renderer.setCamera(new MovableCamera(renderPos));
        renderer.render();

    }

    private void renderHud(CraftGameClient ctx) {
        Gui gui = ctx.getGuiManager();
		gui.begin(ctx.getWidth(), ctx.getHeight());
		if(ctx.state.selectedItemType != null) {
            ItemRenderMaster renderer = ctx.getRenderResources(ItemRenderMaster.class);
			renderer.renderUI(ctx.state.selectedItemType, null, ctx.getWidth() - 128, 0, 128, 128, gui);
		}
		gui.finish();
        
    }

    @Override
    public void release(CraftGameClient ctx) {
        
    }
    
}
