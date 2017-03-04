#version 330 core

in vec2 out_texture;

out vec4 color;

uniform sampler2D sampler;

void main()
{
	color = texture(sampler, out_texture);
	color.a  = 1;
	color.b = 1;

	if(color.a < 0.1f) {
	    discard;
	}



}