package com.quangtd.qsokoban.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;

import java.util.ArrayList;

public class LoadImageUtils {
    public static ArrayList<Bitmap> loadSubImage(Context context, @DrawableRes int id, int rows, int cols) {
        ArrayList<Bitmap> b = new ArrayList<>();
        try {
            Bitmap bigImage = loadImage(context, id);
            int w = bigImage.getWidth() / cols;
            int h = bigImage.getHeight() / rows;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Rect copyRect = new Rect(j * w, i * h, j * w + w, i * h + h);
                    b.add(getSubImage(bigImage, copyRect));
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

    /**
     * <p>Hàm tính toán matrix khi draw bitmap source lên bitmap đích</p>
     * <br/>
     * <br/>
     *
     * @param srcWidth  : width của bitmap source
     * @param srcHeight : height của bitmap source
     * @param desWidth  : width của bitmap đích
     * @param desHeight : height của bitmap dích
     * @return <p>kết quả sẽ là matrix để bitmap source nằm ở trung tâm bitmap đích</p>
     */
    public static Matrix getMatrix(int srcWidth, int srcHeight, int desWidth, int desHeight) {
        Matrix matrix = new Matrix();
        float scaleX;
        float scaleY;
        float newWidth;
        float newHeight;

        scaleX = desWidth * 1.0f / srcWidth;
        newWidth = desWidth;
        newHeight = srcHeight * scaleX;
        if (newHeight > desHeight) {
            scaleY = desHeight * 1.0f / newHeight;
            newHeight = desHeight;
            newWidth = newWidth * scaleY;
        }
        matrix.setScale(newWidth * 1.0f / srcWidth, newHeight * 1.0f / srcHeight);
        matrix.postTranslate((desWidth - newWidth) / 2, (desHeight - newHeight) / 2);
        return matrix;
    }

    public static void setupMatrixFitCell(Matrix matrix, int newWidth, int newHeight, int widthBitmap, int heightBitmap, float widthCell, float x, float y) {
        matrix.reset();
        matrix.setScale(newWidth * 1.0f / widthBitmap, newHeight * 1.0f / heightBitmap);
        matrix.postTranslate(widthCell * x + widthCell / 2 - newWidth / 2, widthCell * y + widthCell / 2 - newHeight / 2);
    }
}
