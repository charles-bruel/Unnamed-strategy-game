#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 tcs;

uniform mat4 pr_matrix;
uniform mat4 sc_matrix;
uniform mat4 tr_matrix;
uniform float wrapWidth;
uniform float scale;
uniform float offSize;

out DATA
{
	vec2 tc;
} vs_out;

void main()
{
	vec4 tempPos = tr_matrix * position * sc_matrix;
	float ta = gl_VertexID % 2;
	ta *= 2;
	ta *= scale;
	ta *= offSize;
	float effectiveX = position.x - ta;
	float mta = 2*scale;
	while(tempPos.x > 16 + ta)
	{
		tempPos.x -= wrapWidth*scale;
	}
	while(tempPos.x < -16-mta + ta)
	{
		tempPos.x += wrapWidth*scale;
	}
	gl_Position = pr_matrix * tempPos;
	vs_out.tc = tcs;
}