cmake_minimum_required(VERSION 3.8)

#+-----------------------------------------+
#|             CMAKE SETUP                 |
#+-----------------------------------------+
# @NOTE(max): Whenever the game actually has a name
#             update it here.
set (PROJECT_TITLE "Java-Text-Game")
set (EXE_TARGET ${PROJECT_TITLE})
project (${PROJECT_TITLE})


# Whether the project is compiled in debug/release
if (${CMAKE_BUILD_TYPE} STREQUAL "Debug")
    option (PROJECT_COMPILE_AS_DEBUG ON)
else()
    option (PROJECT_COMPILE_AS_DEBUG OFF)
endif()

message ("PROJECT COMPILING IN DEBUG MODE: ${PROJECT_COMPILE_AS_DEBUG}")

set (CMAKE_CXX_FLAGS "-std=c++11")

# Whether the project is compiled as 32/64bit.
if (${CMAKE_SIZEOF_VOID_P} EQUAL 8)
    option (PROJECT_COMPILE_AS_64BIT ON)
else()
    option (PROJECT_COMPILE_AS_64BIT OFF)
endif()

message ("PROJECT COMPILING IN 64BIT: ${PROJECT_COMPILE_AS_64BIT}")

#+-----------------------------------------+
#|                SOURCES                  |
#+-----------------------------------------+
add_executable(${EXE_TARGET}
    "src/main.cpp")

#+-----------------------------------------+
#|                 GLFW                    |
#+-----------------------------------------+
# Add glfw dependency, the includes should
# be automatically propagated.
message ("EXECUTING add_subdirectory FOR glfw")
add_subdirectory("glfw")

target_include_directories(${EXE_TARGET} 
    PRIVATE "glfw/include/")


#+-----------------------------------------+
#|                 GLEW                    |
#+-----------------------------------------+
# Now we need to find glew because it still
# doesn't have cmake as it's build generator.
# @UPDATE(max): Just add a sub directory script to clean it up a bit.
message ("EXECUTING add_subdirectory FOR glew")
add_subdirectory("glew")

target_include_directories(${EXE_TARGET}
    PRIVATE "glew/include/")

# Needed to use glew as a static library:
add_definitions("-DGLEW_STATIC")

#+-----------------------------------------+
#|                 LINK                    |
#+-----------------------------------------+
message ("LINKING LIBRARIES...")
target_link_libraries(${EXE_TARGET}
    PRIVATE "opengl32"
    PRIVATE "glfw"
    PRIVATE "${GLEW_LIB_FOUND}")
