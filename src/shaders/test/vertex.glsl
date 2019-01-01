#version 330 core

layout (location = 0) in vec3 Cpu_Pos;
layout (location = 1) in vec2 Cpu_Tex;

uniform mat4 Cpu_Projection;

varying vec2 Tex;

void main() {
    Tex = Cpu_Tex;
    gl_Position = Cpu_Projection * vec4(Cpu_Pos.xyz, 1.0f);
}