package com.lpb.lifecardsdk.widget.facedetector;

import android.graphics.Bitmap;
import android.media.Image;

public interface CameraViewEventListener {
    void onBufferReady(Image image);

    void onCameraError(String str);

    void onCapturedImage(Bitmap bitmap);

    void onPreviewReady();
}
