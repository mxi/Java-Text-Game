package test.magneticstudio.transience.ui;

import com.magneticstudio.transience.devkit.Shader;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class OpenGLTest {

    private static void createDisplay() throws Exception {
        Display.setDisplayMode(new DisplayMode(960, 640));
        Display.create();
    }

    public static void main(String[] args) throws Exception {
        createDisplay();

        FloatBuffer vertexData = BufferUtils.createFloatBuffer(3 * 3);
        vertexData.put(new float[] {
                -0.5f, -0.5f, 0.0f,
                0.0f,  0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        });
        vertexData.flip();

        int vertexArr = glGenVertexArrays();
        glBindVertexArray(vertexArr);

        int vertexBuff = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuff);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        Shader triangleShader = Shader.Factory.loadFromJar("test");

        while (!Display.isCloseRequested()) {
            glClearColor(0.9f, 0.7f, 0.4f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            triangleShader.bind();

            glBindVertexArray(vertexArr);
            glDrawArrays(GL_TRIANGLES, 0, 3);
            glBindVertexArray(0);

            triangleShader.unbind();

            Display.update();
        }

        Display.destroy();
    }
}
