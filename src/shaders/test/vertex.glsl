#version 330 core

layout (location = 0) in vec3 Pos;

void main() {
    gl_Position = vec4(Pos.xyz, 1.0f);
}