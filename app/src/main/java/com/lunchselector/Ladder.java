package com.lunchselector;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

/**
 * Created by USER on 2016-10-28.
 */
public class Ladder extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new LdView(this));

    }

    class LdView extends View {
        Paint paint = new Paint();
        int width, height;
        boolean isPost = false;

        public LdView(Context context) {
            super(context);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(5.0f);
            post(new Runnable() {
                @Override
                public void run() {
                    width = getWidth();
                    height = getHeight();
                    isPost = true;
                }
            });
        }

        int ldWidth, ldHeight;


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawLine(50, 100, 50, 500, paint);
            canvas.drawLine(100, 100, 100, 500, paint);
            canvas.drawLine(150, 100, 150, 500, paint);
            canvas.drawLine(200, 100, 200, 500, paint);
            canvas.drawLine(250, 100, 250, 500, paint);
        }


    }
}
