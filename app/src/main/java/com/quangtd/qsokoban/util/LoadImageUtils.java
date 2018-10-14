package com.quangtd.qsokoban.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;

public class LoadImageUtils {
    public static Bitmap[] loadSubImage(Context context, @DrawableRes int id, int rows, int cols) {
        try {
            Bitmap bigImage = loadImage(context, id);
            int w = bigImage.getWidth() / cols;
            int h = bigImage.getHeight() / rows;
            Bitmap[] b = new Bitmap[rows * cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Rect copyRect = new Rect(j * w, i * h, j * w + w, i * h + h);
                    b[(i * cols) + j] = getSubImage(bigImage, copyRect);
                }
            }
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getSubImage(Bitmap b, Rect copyRect) {
        // Extracts a part of a Bitmap defined by copyRect.
        Bitmap subImage = Bitmap.createBitmap(copyRect.width(),
                copyRect.height(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(subImage);
        c.drawBitmap(b, copyRect, new Rect(0, 0, copyRect.width(), copyRect.height()), null);
        return subImage;
    }

    public static Bitmap loadImage(Context context, @DrawableRes int id) {
        return BitmapFactory.decodeResource(context.getResources(), id);
    }
}
