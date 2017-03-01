#version 330 core

layout (location = 0) in vec3 pos;

uniform mat4 transformation;
uniform mat4 projection;
//uniform mat4 view;

void main()
{
	gl_Position = projection * /*view **/ transformation * vec4(pos, 1);
}