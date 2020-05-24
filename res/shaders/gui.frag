#version 330 core

in vec3 f_color;
in vec2 f_tex;
uniform sampler2D texSampler;

out vec4 FragColor;

void main()
{
    FragColor = vec4(f_color,1.0f) * texture(texSampler, f_tex);
} 
