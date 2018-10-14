# THIS IS HERE TO CLEANUP THE MAIN CMAKELISTS,
# THIS SIMPLY FINDS THE PROPER STATIC LIBRARY
# TO USE AND PROPAGATES IT.
cmake_minimum_required(VERSION 3.11)

# set the parent folder of the glew.lib file
if (PROJECT_COMPILE_AS_64BIT)
    set (LIB_FOLDER "64BIT")
else()
    set (LIB_FOLDER "32BIT")
endif()

# get the lib as either debug or release
if (PROJECT_COMPILE_AS_DEBUG)
    find_library (GLEW_LIB_FOUND_LOCAL
        NAMES glew32sd
        HINTS "${CMAKE_CURRENT_LIST_DIR}/lib/Debug/${LIB_FOLDER}/")
else()
    find_library (GLEW_LIB_FOUND_LOCAL
        NAMES glew32s
        HINTS "${CMAKE_CURRENT_LIST_DIR}/lib/Release/${LIB_FOLDER}/")
endif()

set (GLEW_LIB_FOUND ${GLEW_LIB_FOUND_LOCAL} PARENT_SCOPE)