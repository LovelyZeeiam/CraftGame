package xueli.mcremake.classic.client.renderer.gui;

import xueli.game2.renderer.legacy.engine.RenderType;
import xueli.game2.resource.submanager.render.shader.Shader;

import static org.lwjgl.opengl.GL30.*;

public class RenderTypeTexture2D extends RenderType<Integer> {

	private static final String VERT_SHADER_CODE = """
#version 330

layout (location = 0) in vec2 pos;
layout (location = 1) in vec2 texPos;
layout (location = 2) in vec3 color;

out vec2 otexPos;
out vec3 ocolor;

void main(){
	gl_Position = vec4(pos, 0.0, 1.0);
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

	}

	@Override
	public void doInit() {
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
	public void doRelease() {
		this.shader.release();

	}

}
