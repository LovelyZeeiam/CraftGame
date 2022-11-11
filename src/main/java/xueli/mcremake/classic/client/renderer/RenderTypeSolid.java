package xueli.mcremake.classic.client.renderer;

import xueli.game2.resource.submanager.render.shader.Shader;

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
	vec4 posCam = viewMatrix * vec4(pos ,1.0);
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

	private Shader shader;

	@Override
	protected void doInit() {
		this.shader = Shader.getShader(VERT_SHADER_CODE, FRAG_SHADER_CODE);

	}

	@Override
	public void render() {
		this.shader.bind();
		super.render();
		this.shader.unbind();

	}

	@Override
	public void release() {
		this.shader.release();

	}

}
