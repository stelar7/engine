#version 330 core

layout (location = 0) in vec3 pos;

uniform float scale;

void main()
{
	gl_Position = vec4(pos * scale, 1);
}