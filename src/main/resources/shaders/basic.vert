#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texture;
layout (location = 2) in vec3 normal;

uniform mat4 mvp = mat(1);

out vec2 out_texture;

void main()
{
	gl_Position = mvp * vec4(position, 1);
	out_texture = vec2(texture.x, 1 - texture.y);
}