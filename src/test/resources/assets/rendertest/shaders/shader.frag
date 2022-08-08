#version 330

in vec3 ocolor;

out vec4 out_color;

void main() {
    out_color = vec4(ocolor, 1.0);
}
