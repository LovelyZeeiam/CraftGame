#version 330 core

layout (location = 0) in vec2 a_pos;
layout (location = 1) in vec2 a_tex;

out vec2 f_tex;

void main()
{
    gl_Position = vec4(a_pos,0.0,1.0);
    f_tex = a_tex;
    
}
