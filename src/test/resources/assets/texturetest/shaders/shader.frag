#version 330

in vec3 ocolor;
in vec2 ouv;

uniform sampler2D textureSampler;

out vec4 out_color;

void main() {
    out_color = vec4(ocolor, 1.0) * texture(textureSampler, ouv);
}
