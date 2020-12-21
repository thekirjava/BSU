cmake_minimum_required(VERSION 3.10)
project(Lab4)

set(CMAKE_CXX_STANDARD 20)

find_package(MPI REQUIRED)
include_directories(${MPI_INCLUDE_PATH})

add_executable(Lab4 main.cpp)

target_link_libraries(Lab4 ${MPI_LIBRARIES})

if(MPI_COMPILE_FLAGS)
    set_target_properties(Lab4 PROPERTIES
            COMPILE_FLAGS "${MPI_COMPILE_FLAGS}")
endif()

if(MPI_LINK_FLAGS)
    set_target_properties(Lab4 PROPERTIES
            LINK_FLAGS "${MPI_LINK_FLAGS}")
endif()