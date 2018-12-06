#include "jtg.h"
#include "logging.h"
#include "resources.h"

static void Create_Global_Logging_System()
{
    using namespace jtg::log;

    Create_Internal_Log_Bus(MAX_CHANNELS);
    Add_Std_Out();
    // @TODO(max): Add log file: Add_Std_Other(file);
}

static void Create_Global_Resource_System()
{
    using namespace jtg::res;

    Set_Base_Dir("D:/Common/JavaProjects/Java-Text-Game/cpp/debug_mode_log_output");
}

int main(void)
{
    // Sets up the logger and resource management system.
    Create_Global_Logging_System();
    Create_Global_Resource_System();

    jtg::Start_Game();

    return 0;
}