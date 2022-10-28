#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 tcs;

uniform mat4 pr_matrix;
uniform float progress;

void main()
{
	gl_Position = position;
	if(tcs.x == 1)
	{
		gl_Position.x = -10 + progress*20;
	}
	gl_Position = pr_matrix * gl_Position;
}