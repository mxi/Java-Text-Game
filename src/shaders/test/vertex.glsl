#version 330 core

layout (location = 0) in vec3 Pos;
layout (location = 1) in vec2 Tex;

out vec2 Vert_Tex_Coord;

void main() {
    gl_Position = vec4(Pos.xyz, 1.0f);
    Vert_Tex_Coord = Tex;
}