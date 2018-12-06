#include <sstream>
#include <fstream>

#include "logging.h"
#include "resources.h"

namespace jtg { namespace res {

    std::string baseDir;

    /* Sets the base directory for this game's resources and whatnot. */
    void Set_Base_Dir(
        const std::string& cstr_path)
    {
        baseDir = cstr_path;
    }

    /* Gets the base directory for this game's resources. */
    const std::string& Get_Base_Dir()
    {
        return baseDir;
    }

    /* Reads the contents of the file specified via the
       relative path. Final path: baseDir + relative_path
    */
    std::string Read_Content_Entirely(
        const std::string& relative_path)
    {
        jtgINFO("Reading resource content entirely of: " + baseDir + relative_path);
        std::ifstream input { baseDir + relative_path };

        std::ostringstream output_buffer;
        std::copy(std::istreambuf_iterator<char>(input),
                  std::istreambuf_iterator<char>(),
                  std::ostreambuf_iterator<char>(output_buffer));

        return output_buffer.str();
    }

}} // namespace jtg::res