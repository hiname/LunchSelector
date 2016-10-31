package com.lunchselector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by USER on 2016-10-28.
 */
public class Ladder extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new LdView(Ladder.this));
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                setContentView(new LdView(Ladder.this));
//                new Handler().postDelayed(this, 2000);
//            }
//        }, 2000);
    }

    class LdView extends View {
        Paint linePaint = new Paint();
        Paint dotPaint = new Paint();
        int width, height;
        boolean isPost = false;

        public LdView(Context context) {
            super(context);
            linePaint.setColor(Color.BLACK);
            linePaint.setStrokeWidth(5.0f);
            dotPaint.setColor(Color.BLACK);
            dotPaint.setStrokeWidth(5.0f);
            userPaint.setColor(Color.RED);
            userPaint.setStrokeWidth(5.0f);
            post(new Runnable() {
                @Override
                public void run() {
                    width = getWidth();
                    height = getHeight();
                    isPost = true;
                    wPart = width / 6;
                    wPart /= 10;
                    wPart *= 10;
                    hPart = height / 6;
                    hPart /= 10;
                    hPart *= 10;
                    makeRndDotPoint();

                    Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.china);
                    bmp1 = ResMgr.getResizedBitmap(bmp1, 72, 72);
                    userList.add(new User(bmp1, wPart * 1, 0));

                    Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.gs);
                    bmp2 = ResMgr.getResizedBitmap(bmp2, 72, 72);
                    userList.add(new User(bmp2, wPart * 2, 0));

                    Bitmap bmp3 = BitmapFactory.decodeResource(getResources(), R.drawable.jpn);
                    bmp3 = ResMgr.getResizedBitmap(bmp3, 72, 72);
                    userList.add(new User(bmp3, wPart * 3, 0));

                    Bitmap bmp4 = BitmapFactory.decodeResource(getResources(), R.drawable.kor);
                    bmp4 = ResMgr.getResizedBitmap(bmp4, 72, 72);
                    userList.add(new User(bmp4, wPart * 4, 0));

                    Bitmap bmp5 = BitmapFactory.decodeResource(getResources(), R.drawable.snack);
                    bmp5 = ResMgr.getResizedBitmap(bmp5, 72, 72);
                    userList.add(new User(bmp5, wPart * 5, 0));

                    invalidate();
                }
            });
        }

        int wPart, hPart = 20;
        int startX = 100, endX = 0;
        int startY = 100, endY = 500;
        int yLength = endY - startY;
        int vertSize = 5;

        ArrayList<Integer> problemDotIdx = new ArrayList<Integer>();
        ArrayList<User> userList = new ArrayList<User>();
        ArrayList<DotPoint> dotPointList = new ArrayList<DotPoint>();

        class User {
            int x = startX;
            int y = startY;
            int endX, endY;
            int moveX, moveY;
            boolean isBranch = false;
            String direction = "down";
            Bitmap bitmap;

            public User(Bitmap bitmap, int x, int y) {
                this.bitmap = bitmap;
                this.x = x;
                this.y = y;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getDirection() {
                return direction;
            }

            public void move() {

                if (y > 550) return;

                if (direction.equals("down")) {
                    moveX = 0;
                    moveY = 10;
                } else if (direction.equals("left")) {
                    moveX = -10;
                    moveY = 0;
                } else if (direction.equals("right")) {
                    moveX = 10;
                    moveY = 0;
                }

                x += moveX;
                y += moveY;
            }

            public void setBranch(boolean branch) {
                isBranch = branch;
            }

            public boolean getBranch() {
                return isBranch;
            }
        }

        class DotPoint {
            int x, y;
            int stopX, stopY;

            public DotPoint(int x, int y) {
                this.x = x;
                this.y = y;
                stopX = x + wPart;
                stopY = y;
            }
        }

        Paint userPaint = new Paint();


        public String getBranchDirection(User user){
            for (DotPoint dot : dotPointList)
                if (dot.x == user.x && dot.y == user.y)
                    return "right";
                else if (dot.stopX == user.x && dot.stopY == user.y)
                    return "left";

            return null;
        }

        public boolean isBranchX(User user){
            for (DotPoint dot : dotPointList)
                if (dot.x == user.x)
                    return true;

            DotPoint lastDp = dotPointList.get(dotPointList.size() - 1);
            if (lastDp.stopX == user.x) return true;

            return false;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //
            for (int i = 1; i <= vertSize; i++) {
                canvas.drawLine(wPart * i, startY, wPart * i, endY, linePaint);
            }
            for (int i = 0; i < dotPointList.size(); i++) {
                int rndX = dotPointList.get(i).x;
                int rndY = dotPointList.get(i).y;
                canvas.drawLine(rndX, rndY, rndX + wPart, rndY, dotPaint);
            }
            for (int i = 0; i < userList.size(); i++) {
                User nowUser = userList.get(i);
                int userX = nowUser.x;
                int userY = nowUser.y;


                Log.d("d", "nowUser.getBranch() : " + nowUser.getBranch());

                if (nowUser.getDirection().equals("down")) {
                    String direction = getBranchDirection(nowUser);
                    if(direction != null){
                        nowUser.setDirection(direction);
                    }
                } else if (nowUser.getDirection().equals("left")) {
                    if(isBranchX(nowUser)){
                        nowUser.setDirection("down");
                    }
                } else if (nowUser.getDirection().equals("right")) {
                    if(isBranchX(nowUser)){
                        nowUser.setDirection("down");
                    }
                }

                nowUser.move();

                canvas.drawBitmap(nowUser.bitmap, userX - (nowUser.bitmap.getWidth() / 2), userY - (nowUser.bitmap.getHeight() / 2), null);
                canvas.drawRect(wPart * 1 - (36), 560 - 36, wPart * 1 + 72 - 36, 632 - 36, dotPaint);
                // canvas.drawCircle(userX, userY, 5.0f, userPaint);
                Log.d("d", "userList.get(i).x, y : " + userList.get(i).x + ", " + userList.get(i).y);
                String dpPrint = "";
                for (DotPoint dp : dotPointList) dpPrint += "[" + dp.x + ", " + dp.y + "], ";
                Log.d("d", "dpPrint : " + dpPrint);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            }, 100);
        }

        public void makeRndDotPoint() {
            for (int i = 1; i < 5; i++) {
                int rndVertSize = (int) (Math.random() * 3) + 2;
                for (int j = 1; j <= rndVertSize; j++) {
                    // int rndVert = (int) (Math.random() * (vertSize - 1)) + 1;
                    int rndX = wPart * i;
                    int rndY = 0;
                    int whileChecker = 0;
                    while (true) {
                        boolean isRngDup = false;
                        rndY = (int) (Math.random() * (yLength - startY - 50)) + startY + 50;
                        rndY /= 10;
                        rndY *= 10;
                        if (dotPointList.size() > 0)
                            for (DotPoint tmpDot : dotPointList) {
                                if (Math.abs(tmpDot.y - rndY) < 20) {
                                    isRngDup = true;
                                    break;
                                }
                            }
                        if (!isRngDup) break;
                        if (whileChecker++ > 200) {
                            dotPointList.clear();
                            i = 1;
                            j = 1;
                        }
                    }
                    dotPointList.add(new DotPoint(rndX, rndY));
                }
            }
        }
    }
}
