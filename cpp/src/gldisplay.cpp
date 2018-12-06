/* Implementation of the abstract opengl window and update/render loop */
#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <stdexcept>

#include "gldisplay.h"

namespace jtg {

    static bool glfw_setup{ false };
    static bool glew_setup{ false };

    /* creates a new opengl context window */
    GLFWwindow* Gl_Summon_New_Window(
        const char* title, 
        int w, 
        int h)
    {
        if (!glfw_setup) {
            if (!glfwInit()) {
                throw std::runtime_error("GLFW failed to initialize.");
            }

            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
            glfwWindowHint(GLFW_OPENGL_CORE_PROFILE, GLFW_TRUE);

            glfw_setup = true;
        }

        GLFWwindow* nContext = glfwCreateWindow(
            w, h, title, nullptr, nullptr
        );
        glfwMakeContextCurrent(nContext);

        if (!glew_setup) {
            if (!glewInit()) {
                throw std::runtime_error("GLEW failed to initialize");
            }
            glew_setup = true;
        }

        return nContext;
    }
}