#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
	vec2 tc;
} fs_in;

uniform sampler2D tex;
uniform int render = 1;

void main()
{
	vec4 temp = texture(tex, fs_in.tc);
	if(temp.w < 1 || render != 1)
	{
		discard;
	}
	color = temp;
}