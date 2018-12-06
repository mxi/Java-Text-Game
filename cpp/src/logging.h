/* Provides logging utility */
#ifndef __JTG_LOGGING_H
#define __JTG_LOGGING_H

#include <string>

namespace jtg { namespace log {

    constexpr int MAX_CHANNELS = 16;

    /* Creates an array of output channels for logging events. 
       If lCount <= 0 no empty, unbound, channel buffers will be created.
       If lCount > MAX_CHANNELS only MAX_CHANNELS empty, unbound, channels buffers will be allocated.
       If a bus is already created this function has no effect
    */
    void Create_Internal_Log_Bus(
        int lCount);

    /* Flushes all internal log channels. */
    void Flush_Internal_Log_Bus();

    /* Flushes and deletes the internal log bus. */
    void Delete_Internal_Log_Bus();

    /* Gets the total amount of channel buffers reserved for the internal bus. */
    int Get_Reserved_Channel_Buffers();

    /* Gets the count of the channel buffers that are occupied. */
    int Get_Occupied_Channel_Buffers();

    /* Gets the number of free channels available. */
    int Get_Free_Channel_Buffers();

    /* Adds std out as a log channel. */
    void Add_Std_Out();

    /* Adds another std ostream to the log bus. */
    void Add_Std_Other(
        std::ostream& another);

    /* Logs info through the log bus (USE THE MACRO "jtgINFO(msg)") */
    void Log_Info(
        const std::string& msg);

    /* Logs warnings through the log bus (USE THE MACRO "jtgWARN(msg)") */
    void Log_Warn(
        const std::string& warn);

    /* Logs an errors through the log bus (USE THE MACRO "jtgERR(msg)") */
    void Log_Err(
        const std::string& err);

#ifdef JTG_ENABLE_DEBUG_FEATURE_LOG
  #define jtgINFO(msg) jtg::log::Log_Info(msg)
  #define jtgWARN(msg) jtg::log::Log_Warn(msg)
  #define jtgERR(msg)  jtg::log::Log_Err(msg)
#else
  #define jtgINFO(msg)
  #define jtgWARN(msg)
  #define jtgERR(msg)
#endif

}}

#endif // __JTG_LOGGING_H