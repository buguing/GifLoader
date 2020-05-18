cmake_minimum_required(VERSION 3.4.1)

file(GLOB_RECURSE SOURCES ${CMAKE_SOURCE_DIR}/giflib/*.c)

add_library(
        # 要编译的库的名称，可以改
        native-lib

        # SHARED 表示要编译动态库
        SHARED

        native-lib.cpp
        # 把giflib源文件和native-lib.cpp添加到 native-lib 这个库中去
        ${SOURCES})

find_library(
        log-lib

        log)

target_link_libraries(
        native-lib
        # 给native-lib 添加一些依赖
        jnigraphics
        ${log-lib})