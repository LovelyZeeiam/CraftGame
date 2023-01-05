package xueli.mcremake.client.renderer.gui;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.lwjgl.utils.vector.Matrix4f;
import org.lwjgl.utils.vector.Vector2f;

import xueli.game2.renderer.legacy.RenderType;
import xueli.game2.resource.submanager.render.shader.Shader;

public class RenderTypeTexture2D extends RenderType<Integer> {

	private static final String VERT_SHADER_CODE = """
#version 330

layout (location = 0) in vec2 pos;
layout (location = 1) in vec2 texPos;
layout (location = 2) in vec3 color;

uniform vec2 displayDimension;

out vec2 otexPos;
out vec3 ocolor;

void main(){
	vec2 normalPos = vec2(mix(-1.0, 1.0, pos.x / displayDimension.x), mix(1.0, -1.0, pos.y / displayDimension.y));
	gl_Position = vec4(normalPos, 0.0, 1.0);
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

	private Shader shader;

	public RenderTypeTexture2D() {
		super(i -> new MyRenderBuffer2D());
		this.shader = Shader.compile(VERT_SHADER_CODE, FRAG_SHADER_CODE);

	}

	@Override
	public void render() {
		this.shader.bind();
		buffers.forEach((i, a) -> {
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, i);
			a.render();
			glBindTexture(GL_TEXTURE_2D, 0);
		});
		this.shader.unbind();

	}

	@Override
	public void applyMatrix(String name, Matrix4f matrix) {
	}

	public void setDisplayDimension(float width, float height) {
		this.shader.bind();
		shader.setUniformVector2f(shader.getUnifromLocation("displayDimension"), new Vector2f(width, height));
		this.shader.unbind();

	}

	@Override
	public void doRelease() {
		this.shader.release();

	}

}
