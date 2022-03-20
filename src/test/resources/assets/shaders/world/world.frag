#version 330

in float visibility;

in vec3 fragPos;
in vec2 otexPos;
in vec4 ocolor;

uniform vec3 skyColor;
uniform vec3 sunDirection;
uniform sampler2D texSampler;

out vec4 out_color;

void main(){
	vec4 ambient = texture(texSampler, otexPos);
	if(ambient.w == 0) discard; 	
	
	out_color = mix(vec4(skyColor, 1.0), ambient * ocolor, visibility);

	
}
