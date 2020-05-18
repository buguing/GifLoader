//
// Created by Administrator on 2019/12/27.
//

#include "giflib/gif_lib.h"
#include <android/bitmap.h>

#ifndef GIFLOADER_GIF_H
#define GIFLOADER_GIF_H

extern "C"
int drawFrame(GifFileType *gif, AndroidBitmapInfo *info, int *pixels, int frame_no,
              bool force_dispose_1);

#endif //GIFLOADER_GIF_H

