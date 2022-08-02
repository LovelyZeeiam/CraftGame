#version 330

layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 texPos;
layout (location = 2) in vec4 color;

uniform mat4 projMatrix;
uniform mat4 viewMatrix;

out vec3 fragPos;
out vec2 otexPos;
out vec4 ocolor;

const float density = 0.004;
const float gradient = 6.0;
out float visibility;

void main(){
	vec4 posCam = viewMatrix * vec4(pos ,1.0);
	gl_Position = projMatrix * posCam;
	otexPos = texPos;
	fragPos = pos;
	ocolor = color;
	
	float distance = length(posCam.xyz);
	visibility = exp(-pow(distance * density, gradient));
	visibility = clamp(visibility, 0.0, 1.0);
	
}

