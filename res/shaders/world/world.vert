#version 330

layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 texPos;
layout (location = 2) in vec3 normal;
layout (location = 3) in vec4 lighting;

uniform mat4 projMatrix;
uniform mat4 viewMatrix;

out vec3 fragPos;
out vec2 otexPos;
out vec3 onormal;
out vec4 olighting;

const float density = 0.004;
const float gradient = 6.0;
out float visibility;

void main(){
	vec4 posCam = viewMatrix * vec4(pos ,1.0);
	gl_Position = projMatrix * posCam;
	otexPos = texPos;
	fragPos = pos;
	onormal = normal;
	olighting = lighting;
	
	float distance = length(posCam.xyz);
	visibility = exp(-pow(distance * density, gradient));
	visibility = clamp(visibility, 0.0, 1.0);
	
}

