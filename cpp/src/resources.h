/* Easily retrieve resource for the game */
#ifndef __JTG_RESOURCES_H
#define __JTG_RESOURCES_H

#include <string>

namespace jtg { namespace res {

    /* Sets the base directory for this game's resources and whatnot. */
    void Set_Base_Dir(
        const std::string& cstr_path);

    /* Gets the base directory for this game's resources. */
    const std::string& Get_Base_Dir();

    /* Reads the contents of the file specified via the
       relative path. Final path: baseDir + relative_path
    */
    std::string Read_Content_Entirely(
        const std::string& relative_path);

}}

#endif // __JTG_RESOURCES_H