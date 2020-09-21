cmake_minimum_required(VERSION 3.10)
project(Lab2)

set(CMAKE_CXX_STANDARD 20)

find_package(MPI REQUIRED)
include_directories(${MPI_INCLUDE_PATH})

add_executable(Lab2 main.cpp)

target_link_libraries(Lab2 ${MPI_LIBRARIES})

if(MPI_COMPILE_FLAGS)
    set_target_properties(Lab2 PROPERTIES
            COMPILE_FLAGS "${MPI_COMPILE_FLAGS}")
endif()

if(MPI_LINK_FLAGS)
    set_target_properties(Lab2 PROPERTIES
            LINK_FLAGS "${MPI_LINK_FLAGS}")
endif()