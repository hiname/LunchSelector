package com.lunchselector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by USER on 2016-10-31.
 */
public class Ladder2 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new LadderView(this));
    }

    int[][] tile = new int[][]{
            {1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 1},
            {1, 1, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 1, 1},
            {1, 0, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1},
    };


    int startX = 0;
    int startY = 0;
    int itvX = 50;
    int itvY = 70;

    class LadderView extends View {
        int margins = 20;
        Paint paint = new Paint();
        Rect[] rects = new Rect[9];

        public LadderView(Context context) {
            super(context);
            startX += margins;
            startY += margins;
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(5.0f);



        }

        public Rect idxToRect(int startX, int startY, int row, int col) {
            Rect rect = new Rect(startX, startY, itvX * col, itvY * row);
            return rect;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawLine(canvas, 0, 0, 0, 300, paint);
            // drawLine(canvas, 100, 0, 100, 300, paint);
        }

        public void drawLine(Canvas canvas, float startX, float startY, float stopX, float stopY, Paint paint) {
            canvas.drawLine(startX + margins, startY + margins,
                            stopX + margins, stopY - margins,
                            paint);
        }
    }
}
