cmake_minimum_required(VERSION 3.10)
project(Lab1 C)

set(CMAKE_C_STANDARD 11)

find_package(MPI REQUIRED)
include_directories(${MPI_INCLUDE_PATH})

add_executable(Lab1 main.c)

target_link_libraries(Lab1 ${MPI_LIBRARIES})

if(MPI_COMPILE_FLAGS)
    set_target_properties(Lab1 PROPERTIES
            COMPILE_FLAGS "${MPI_COMPILE_FLAGS}")
endif()

if(MPI_LINK_FLAGS)
    set_target_properties(Lab1 PROPERTIES
            LINK_FLAGS "${MPI_LINK_FLAGS}")
endif()