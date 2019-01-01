#version 330 core

varying vec2 Tex;

out vec4 FinalColor;

uniform sampler2D U_Tex;

void main() {
    FinalColor = texture(U_Tex, Tex);
}