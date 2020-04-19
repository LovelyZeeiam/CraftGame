#version 210

in vec3 f_color;
in vec2 f_tex;
uniform sampler2D texSampler;

out vec4 FragColor;

void main()
{
    FragColor = texture(texSampler, f_tex);
} 
