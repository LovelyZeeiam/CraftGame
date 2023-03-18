package xueli.mcremake.client.renderer.world;

import java.util.function.Predicate;

import org.lwjgl.opengl.GL30;
import org.lwjgl.utils.vector.Matrix4f;
import org.lwjgl.utils.vector.Vector2i;

import xueli.game2.ecs.ResourceListImpl;
import xueli.game2.resource.submanager.render.shader.Shader;
import xueli.mcremake.registry.TerrainTextureAtlas;

public class RenderTypeSolid extends ChunkRenderType {

	private static final String VERT_SHADER_CODE = """
#version 330

layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 texPos;
layout (location = 2) in vec3 color;

uniform mat4 projMatrix;
uniform mat4 viewMatrix;

out vec2 otexPos;
out vec3 ocolor;

void main(){
	vec4 posCam = viewMatrix * vec4(pos, 1.0);
	gl_Position = projMatrix * posCam;
	otexPos = texPos;
	ocolor = color;
	
}
""";

	private static final String FRAG_SHADER_CODE = """
#version 330

in vec2 otexPos;
in vec3 ocolor;

uniform sampler2D texSampler;

out vec4 out_color;

void main(){
	vec4 ambient = texture(texSampler, otexPos);
	if(ambient.w == 0) discard;
	
	out_color = ambient * vec4(ocolor, 1.0);
	
}

""";

	private final Shader shader;
	private final TerrainTextureAtlas texture;

	public RenderTypeSolid(ResourceListImpl renderResource) {
		this.shader = Shader.compile(VERT_SHADER_CODE, FRAG_SHADER_CODE);
		this.texture = renderResource.get(TerrainTextureAtlas.class);

	}

	@Override
	public void render(Predicate<Vector2i> selector) {
		GL30.glEnable(GL30.GL_DEPTH_TEST);
		GL30.glEnable(GL30.GL_CULL_FACE);
		this.shader.bind();
		texture.bind();
		super.render(selector);
		texture.unbind();
		this.shader.unbind();
		GL30.glDisable(GL30.GL_DEPTH_TEST);
		GL30.glDisable(GL30.GL_CULL_FACE);
		
	}

	@Override
	public void applyMatrix(String name, Matrix4f matrix) {
		if(this.shader.isBound()) {
			shader.setUniformMatrix(shader.getUnifromLocation(name), matrix);
		} else {
			shader.bind();
			shader.setUniformMatrix(shader.getUnifromLocation(name), matrix);
			shader.unbind();
		}

	}

	@Override
	public void doRelease() {
		this.shader.release();

	}
	
	public TerrainTextureAtlas getTexture() {
		return texture;
	}
	
}
