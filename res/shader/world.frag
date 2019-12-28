#version 330 core

in vec2 pass_texCoords;
in vec4 pass_color;

out vec4 out_color;

uniform sampler2D texSampler;

void main(void){
	out_color = texture(texSampler,pass_texCoords);
}
