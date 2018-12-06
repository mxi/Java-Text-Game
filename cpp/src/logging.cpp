#include <iostream>
#include "logging.h"

namespace jtg { namespace log {

    struct Log_Channel {
        std::ostream* out;
    };

    static Log_Channel* channelBus  { nullptr };
    static int channelBusLength     { 0 };
    static int channelBusFreeIndex  { 0 };
    static bool stdOutAlreadyInside { false };
    static bool channelBusGenerated { false };

// well the lambda doesn't work ¯\_(ツ)_/¯
#define For_Each_Non_Null_Channel(Do_This)          \
    for (int i = 0; i < channelBusFreeIndex; ++i) { \
        Log_Channel& it = channelBus[i];            \
        Do_This                                     \
    }

    /* allocates the channel bus assuming that
        lCountFiltered fits the criteria mentioned
        in Create_Internal_Log_Bus
    */
    static inline void Gen_Channel_Bus(
        int lCountFiltered)
    {
        channelBus = new Log_Channel[lCountFiltered]();
        channelBusLength = lCountFiltered;
    }

    /* Creates an array of output channels for logging events.
       If lCount <= 0 no empty, unbound, channel buffers will be created.
       If lCount > MAX_CHANNELS only MAX_CHANNELS empty, unbound, channels buffers will be allocated.
       If a bus is already created this function has no effect
    */
    void Create_Internal_Log_Bus(
        int lCount)
    {
        if (channelBusGenerated || lCount <= 0)
        {
            return;
        }
        else
        {
            Gen_Channel_Bus(lCount > MAX_CHANNELS ? MAX_CHANNELS : lCount);
            channelBusGenerated = true;
        }
    }

    /* Flushes all log channels. */
    void Flush_Internal_Log_Bus()
    {
        For_Each_Non_Null_Channel(
            (*it.out).flush();
        );
    }

    /* Flushes and deletes internal log bus. */
    void Delete_Internal_Log_Bus()
    {
        Flush_Internal_Log_Bus();
        delete[] channelBus;
        channelBusLength = 0;
        channelBusFreeIndex = 0;
        stdOutAlreadyInside = false;
        channelBusGenerated = false;
    }

    /* Gets the total amount of channel buffers reserved for the internal bus. */
    int Get_Reserved_Channel_Buffers()
    {
        return channelBusLength;
    }

    /* Gets the count of the channel buffers that are occupied. */
    int Get_Occupied_Channel_Buffers()
    {
        return channelBusFreeIndex;
    }

    /* Gets the number of free channels available. */
    int Get_Free_Channel_Buffers()
    {
        return channelBusLength - channelBusFreeIndex;
    }

    /* Adds std out as a log channel. */
    void Add_Std_Out()
    {
        if (channelBus != nullptr && 
            !stdOutAlreadyInside  && 
            channelBusFreeIndex != channelBusLength)
        {
            channelBus[channelBusFreeIndex] = { &std::cout };
            ++channelBusFreeIndex;
            stdOutAlreadyInside = true;
        }
    }

    /* Adds another std ostream to the log bus. */
    void Add_Std_Other(
        std::ostream& another)
    {
        if (channelBus != nullptr && 
            channelBusFreeIndex != channelBusLength)
        {
            channelBus[channelBusFreeIndex] = { &another };
            ++channelBusFreeIndex;
        }
    }

    /* Logs info through the log bus (USE THE MACRO "jtgINFO(msg)") */
    void Log_Info(
        const std::string& msg)
    {
        For_Each_Non_Null_Channel(
            (*it.out) << "[Info]: " << msg << '\n';
        );
    }

    /* Logs warnings through the log bus (USE THE MACRO "jtgWARN(msg)") */
    void Log_Warn(
        const std::string& warn)
    {
        For_Each_Non_Null_Channel(
            (*it.out) << "[Warn]: " << warn << '\n';
        );
    }

    /* Logs an errors through the log bus (USE THE MACRO "jtgERR(msg)") */
    void Log_Err(
        const std::string& err)
    {
        For_Each_Non_Null_Channel(
            (*it.out) << "[Erro]: " << err << '\n';
        );
    }

}}