package xueli.mcremake.client.renderer.world;

import java.util.function.Predicate;

import org.lwjgl.opengl.GL30;
import org.lwjgl.utils.vector.Vector2i;

import xueli.game2.ecs.ResourceListImpl;

public class RenderTypeAlpha extends RenderTypeSolid {

    public RenderTypeAlpha(ResourceListImpl renderResource) {
        super(renderResource);
    }
    
    @Override
    public void render(Predicate<Vector2i> selector) {
        GL30.glEnable(GL30.GL_BLEND);
        GL30.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        super.render(selector);
        GL30.glDisable(GL30.GL_BLEND);
    }

}
