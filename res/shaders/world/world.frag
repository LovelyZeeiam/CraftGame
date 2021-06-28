#version 330

const vec3 sunColor = vec3(1.0, 1.0, 1.0);

in vec3 fragPos;
in vec2 otexPos;
in float visibility;
in vec3 onormal;
in vec4 olighting;

uniform vec3 skyColor;
uniform vec3 sunDirection;
uniform sampler2D texSampler;

out vec4 out_color;

float diff() {
	vec3 norm = normalize(onormal);
	vec3 lightDir = normalize(sunDirection);
	return -max(dot(norm, lightDir), 0.0);
}

void main(){
	vec3 sunLightColor = sunColor * olighting.w;
	vec3 real_sun_color = mix(olighting.rgb / 15.0, sunLightColor / 15.0, 0.5);
	
	vec4 ambient = texture(texSampler, otexPos);
	if(ambient.w == 0) discard; 	
	
	vec3 color = ambient.xyz * vec3(mix(0.8,2.0,diff())) * real_sun_color;
	
	out_color = mix(vec4(skyColor, 1.0), vec4(color, ambient.w), visibility);

	
}
