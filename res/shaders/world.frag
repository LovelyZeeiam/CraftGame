#version 330 core

in vec3 f_color;
in vec2 f_tex;
uniform sampler2D texSampler;

uniform vec3 skyColor;

//Fog
in float visibility;

out vec4 FragColor;

void main()
{
    FragColor = mix(vec4(skyColor,1.0),vec4(f_color,1.0f) * texture(texSampler, f_tex),visibility);
} 
