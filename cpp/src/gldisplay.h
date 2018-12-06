/* Abstracts the opengl setup and render/update loops */
#include <GL/glew.h>
#include <GLFW/glfw3.h>

namespace jtg {
    
    using WinMoveFunction    = void(*)(const Display&, int nxpos, nypos);
    using WinSizeFunction    = void(*)(const Display&, int nwidth, int nheight);
    using WinCloseFunction   = void(*)(const Display&);
    using WinRefreshFunction = void(*)(const Display&);
    using WinFocusFunction   = void(*)(const Display&);
    using WinIconifyFunction = void(*)(const Display&);
    using WinFbSizeFunction  = void(*)(const Display&, int nwidth, int nheight);

    struct GLFW_Events {
        WinMoveFunction    moveFunc;
        WinSizeFunction    sizeFunc;
        WinCloseFunction   closeFunc;
        WinRefreshFunction refreshFunc;
        WinFocusFunction   focusFunc;
        WinIconifyFunction iconifyFunc;
        WinFbSizeFunction  fbSizeFunc;
    };

    class Display {
    public:

        /* Create and destroy the displays */
        Display();
        ~Display();
        
        /* Getter and setter for window events */
        void Set_Events(const GLFW_Events& newEvents);
        const GLFW_Events& Get_Events() const;

        void 

    private:
        GLFWwindow* m_rawWindow;
        GLFW_Events m_events;
        int         m_fps;
        int         m_tps;
        int         m_curWidth;
        int         m_curHeight;
        int         m_curPosx;
        int         m_curPosy;
    };

    /* Creates a new display instance */
    Display New_Display();

}