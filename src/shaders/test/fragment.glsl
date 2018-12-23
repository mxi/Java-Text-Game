#version 330 core

in vec2 Vert_Tex_Coord;

out vec4 FinalColor;

uniform sampler2D U_Tex;

void main() {
    FinalColor = texture(U_Tex, Vert_Tex_Coord);
}