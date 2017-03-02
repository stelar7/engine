#version 330 core

layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 tex;

uniform mat4 mvp = mat(1);

out vec2 out_texture;

void main()
{
	gl_Position = mvp * vec4(pos, 1);
	out_texture = vec2(tex.x, 1 - tex.y);
}