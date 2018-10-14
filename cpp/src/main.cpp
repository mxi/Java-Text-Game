#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <cstdio>

int main(
    int argc, 
    char* argv[])
{

    if (!glfwInit())
    {
        std::perror("FAILURE TO INIT GLFW");
        return 1;
    }
    
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    glfwWindowHint(GLFW_OPENGL_CORE_PROFILE, GLFW_TRUE);

    GLFWwindow* window = glfwCreateWindow(960, 640, "W2", nullptr, nullptr);

    if (window == nullptr)
    {
        std::perror("GLFW WINDOW CREATION FAILED!");
        return 1;
    }

    glfwMakeContextCurrent(window);

    if (glewInit() != GLEW_OK)
    {
        std::perror("GLEW INIT FAILED!");
        return 1;
    }

    while (!glfwWindowShouldClose(window))
    {
        glfwPollEvents();

        glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        glfwSwapBuffers(window);
    }

    glfwTerminate();

    std::getchar();
    return 0;
}