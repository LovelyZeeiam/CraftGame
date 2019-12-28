#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoords;
layout (location = 2) in vec4 color;

out vec2 pass_texCoords;
out vec4 pass_color;

uniform mat4 projMatrix;
uniform mat4 transMatrix;
uniform mat4 viewMatrix;

void main(void){
	gl_Position = projMatrix * viewMatrix * transMatrix * vec4(position.xyz,1.0);
	pass_texCoords = texCoords;
	pass_color = color;
}
