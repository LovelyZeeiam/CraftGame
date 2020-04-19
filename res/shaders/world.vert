#version 210

layout (location = 0) in vec3 a_pos;
layout (location = 1) in vec2 a_tex;
layout (location = 2) in vec3 a_color;

out vec3 f_color;
out vec2 f_tex;

void main()
{
    gl_Position = vec4(a_pos.xyz, 1.0);
    f_color = a_color;
    f_tex = a_tex;
    
}
