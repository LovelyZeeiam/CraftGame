#version 330 core

layout (location = 0) in vec3 a_pos;
layout (location = 1) in vec2 a_tex;
layout (location = 2) in vec3 a_color;

out vec3 f_color;
out vec2 f_tex;

//Fog
const float density = 0.00015, gradient = 2.1;
out float visibility;

uniform mat4 projMatrix;
uniform mat4 viewMatrix;

void main()
{
	vec4 camPos = viewMatrix * vec4(a_pos.xyz, 1.0);
	vec4 screenPos = projMatrix * camPos;
    gl_Position = screenPos;
    
    f_color = a_color;
    f_tex = a_tex;
    
    float distance = length(camPos.xyz);
    visibility = exp(-pow((distance * density * (256-a_pos.y)),gradient));
    visibility = clamp(visibility,0.0,1.0);
    
}
