#version 330

layout (location = 0) in vec3 pos;
layout (location = 1) in vec3 color;
layout (location = 2) in vec2 uv;

out vec3 ocolor;
out vec2 ouv;

void main() {
    gl_Position = vec4(pos, 1.0);
    ocolor = color;
    ouv = uv;
}
