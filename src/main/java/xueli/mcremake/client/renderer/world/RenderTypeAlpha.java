package xueli.mcremake.client.renderer.world;

import java.util.function.Predicate;

import org.lwjgl.opengl.GL30;
import org.lwjgl.utils.vector.Vector2i;

import xueli.mcremake.client.CraftGameClient;

public class RenderTypeAlpha extends RenderTypeSolid {

    public RenderTypeAlpha(CraftGameClient ctx) {
        super(v -> new MyRenderBufferSortedDistance(), ctx);
    }
    
    @Override
    public void render(Predicate<Vector2i> selector) {
        GL30.glEnable(GL30.GL_BLEND); 
        GL30.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        GL30.glEnable(GL30.GL_DEPTH_TEST); // TODO: Duplicate source! How to simplify them?
		GL30.glEnable(GL30.GL_CULL_FACE);
		this.shader.bind();
		this.texture.bind();
		
		ctx.checkGLError("Pre-render alpha layer");
		buffers.forEach((t, b) -> {
			if(selector.test(t)) {
				((MyRenderBufferSortedDistance)b).renderSorted(ctx.state.player.positionOnRender);
			}
		});
		ctx.checkGLError("Post-render alpha layer");
		
		this.texture.unbind();
		this.shader.unbind();
		GL30.glDisable(GL30.GL_DEPTH_TEST);
		GL30.glDisable(GL30.GL_CULL_FACE);
        GL30.glDisable(GL30.GL_BLEND);

    }

    

}
