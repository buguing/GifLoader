package com.wellee.gifloader;

import android.graphics.Bitmap;

/**
 * @author : liwei
 * 创建日期 : 2019/12/26 18:43
 * 邮   箱 : liwei@worken.cn
 * 功能描述 :
 */
public class GifHandle {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private volatile long gifInfo;

    public GifHandle(String path) {
        gifInfo = openFile(path);
    }

    public synchronized int getWidth() {
        return getWidthN(gifInfo);
    }

    public synchronized int getHeight() {
        return getHeightN(gifInfo);
    }

    public synchronized int getLength() {
        return getLengthN(gifInfo);
    }

    public long renderFrame(Bitmap bitmap, int index) {
        return renderFrameN(bitmap, index, gifInfo);
    }

    private native long openFile(String path);

    private native int getWidthN(long gifInfo);

    private native int getHeightN(long gifInfo);

    private native int getLengthN(long gifInfo);

    private native long renderFrameN(Bitmap bitmap, int index, long gifInfo);


}
