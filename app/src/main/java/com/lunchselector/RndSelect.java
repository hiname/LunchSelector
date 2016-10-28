package com.lunchselector;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class RndSelect extends AppCompatActivity implements View.OnClickListener {
    Button btn1, btn2, btn3;
    String btn1Arr[] = {"중식","일식","한식","분식","편의점",};
    String btn2Arr[] = {"고기(육지)","고기(바다)","풀(육지)","풀(바다)","면","밥",};
    String btn3Arr[] = {"얼큰","짭짤",};

    String dataArr[][] = {
            {"볶음밥", "#중식#밥"},
            {"탕수육", "#중식#고기(육지)"},
            {"깐풍기", "#중식#고기(육지)"},
            {"짬뽕", "#중식#풀(육지)#면#얼큰#시원"},
            {"사천탕수육", "#중식#고기(육지)"},
            {"짜장면", "#중식#면#짭짤"},
            {"초밥", "#일식#고기(바다)#밥#풀(바다)"},
            {"참치회", "#일식#고기(바다)#밥#풀(바다)"},
            {"라멘", "#일식#면#얼큰#시원"},
            {"장어덮밥", "#일식#고기(바다)#밥#짭짤#풀(바다)"},
            {"우동", "#일식#고기(바다)#면#시원"},
            {"냉면", "#한식#면#시원"},
            {"설렁탕", "#한식#고기(육지)#밥#얼큰"},
            {"닭도리탕", "#한식#고기(육지)#밥#얼큰"},
            {"간장게장", "#한식#고기(바다)#밥#짭짤"},
            {"김치찌게", "#한식#고기(육지)#밥#시원"},
            {"낙지", "#한식#고기(바다)"},
            {"불고기백반", "#한식#고기(육지)#밥#얼큰"},
            {"곱창", "#한식#고기(육지)#밥#얼큰#시원"},
            {"떡볶이", "#분식"},
            {"순대", "#분식"},
            {"보쌈", "#분식#밥"},
            {"오뎅", "#분식#고기(바다)#시원"},
            {"샌드위치", "#편의점#고기(육지)"},
            {"삶은 계란", "#편의점#고기(육지)#짭짤"},
            {"빵", "#편의점"},
            {"컵라면", "#편의점#면#얼큰#시원"},
            {"삼각김밥", "#편의점#밥"},
    };

    String[][] menuCodeList = {
            {"볶음밥", "china"},
            {"탕수육", "china"},
            {"깐풍기", "china"},
            {"짬뽕", "china"},
            {"사천탕수육", "china"},
            {"짜장면", "china"},
            {"초밥", "jpn"},
            {"참치회", "jpn"},
            {"라멘", "jpn"},
            {"장어덮밥", "jpn"},
            {"우동", "jpn"},
            {"냉면", "kor"},
            {"설렁탕", "kor"},
            {"닭도리탕", "kor"},
            {"간장게장", "kor"},
            {"김치찌게", "kor"},
            {"낙지", "kor"},
            {"불고기백반", "kor"},
            {"곱창", "kor"},
            {"떡볶이", "snack"},
            {"순대", "snack"},
            {"보쌈", "snack"},
            {"오뎅", "snack"},
            {"샌드위치", "gs"},
            {"삶은 계란", "gs"},
            {"빵", "gs"},
            {"컵라면", "gs"},
            {"삼각김밥", "gs"},
    };

    int[] rankList = new int[dataArr.length];
    int maxIdx = -1;


    public void rndSwapArr(String[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            int rnd = (int)(Math.random() * arr.length);
            String[] tmp = arr[i];
            arr[i] = arr[rnd];
            arr[rnd] = tmp;
        }
    }

    public void arrPrint(String[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            String result = "data[" + i + "][] : ";
            for (int j = 0; j < arr[i].length; j++) {
                result += arr[i][j] + ",";
            }
            Log.d("d", result);
        }
    }

    public String getIdxList(String data, String[][] arr) {
        String idxList = "";

        for (int i = 0; i < arr.length; i++) {
            if (arr[i][1].contains(data))
                idxList += i + ",";
        }

        if(idxList.length() > 0) idxList = idxList.substring(0, idxList.length() - 1);

        return idxList;
    }

    public void modifiedRankValue(String idxList) {
        String[] idxListArr = idxList.split(",");

        for(String idxStr : idxListArr) {
            int idx = Integer.parseInt(idxStr);
            rankList[idx]++;
        }
    }

    boolean isThreadStart1 = true, isThreadStart2 = true, isThreadStart3 = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rndSwapArr(dataArr);
        arrPrint(dataArr);

        // Log.d("d", "idxList : " + getIdxList("#밥", dataArr));

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);


    }

    public int getMaxRankIdx() {
        int maxRank = -9999;
        int maxIdx = -1;
        for (int i = 0; i < rankList.length; i++) {
            if (maxRank < rankList[i]) {
                maxRank = rankList[i];
                maxIdx = i;
            }
        }

        return maxIdx;
    }

    public void threadEnd() {
        if (!isThreadStart1 && !isThreadStart2 && !isThreadStart3) {
            String str1 = btn1.getText().toString();
            String str2 = btn2.getText().toString();
            String str3 = btn3.getText().toString();

            String idxList = getIdxList(str1, dataArr) + "," + getIdxList(str2, dataArr) + "," + getIdxList(str3, dataArr);
            modifiedRankValue(idxList);
            for (int i = 0; i < rankList.length; i++) {
                Log.d("d", "rank[" + i + "] : " + rankList[i]);
            }

            maxIdx = getMaxRankIdx();
            rankList = new int[rankList.length];
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String selectMenu = dataArr[maxIdx][0];
                    ((TextView)findViewById(R.id.tvResult)).setText(selectMenu);
                    Log.d("d", "selectMenu : " + selectMenu);

                    String menuCode = null;

                    for (String[] menuRow : menuCodeList) {
                        if (menuRow[0].equals(selectMenu)) {
                            menuCode = menuRow[1];
                            break;
                        }
                    }

                    int resId = 0;
                    if(menuCode.equals("china")) resId = R.drawable.china;
                    else if(menuCode.equals("jpn")) resId = R.drawable.jpn;
                    else if(menuCode.equals("kor")) resId = R.drawable.kor;
                    else if(menuCode.equals("snack")) resId = R.drawable.snack;
                    else if(menuCode.equals("gs")) resId = R.drawable.gs;

                    ((ImageView)findViewById(R.id.img)).setImageResource(resId);
                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        if (v == btn1) {
            findViewById(R.id.prg1).setVisibility(View.VISIBLE);
            startThread1();
        } else if (v == btn2) {
            findViewById(R.id.prg2).setVisibility(View.VISIBLE);
            startThread2();
        } else if (v == btn3) {
            findViewById(R.id.prg3).setVisibility(View.VISIBLE);
            startThread3();
        }
    }

    int delay = 1000;
    int rndCnt = 50;
    int minCnt = 30;

    public void startThread1() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                int rnd = (int) (Math.random() * rndCnt) + minCnt;
                for (int i = 0; i < rnd; i++) {
                    final int cnt = i % btn1Arr.length;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btn1.setText(btn1Arr[cnt]);
                        }
                    });
                    SystemClock.sleep((int)((float)i / rnd * delay));
                }
                isThreadStart1 = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.prg1).setVisibility(View.INVISIBLE);
                    }
                });
                threadEnd();
            }
        };
        thread.start();
    }

    public void startThread2() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                int rnd = (int) (Math.random() * rndCnt) + minCnt;
                for (int i = 0; i < rnd; i++) {
                    final int cnt = i % btn2Arr.length;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btn2.setText(btn2Arr[cnt]);
                        }
                    });
                    SystemClock.sleep((int)((float)i / rnd * delay));
                }
                isThreadStart2 = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.prg2).setVisibility(View.INVISIBLE);
                    }
                });
                threadEnd();
            }
        };
        thread.start();
    }

    public void startThread3() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                int rnd = (int) (Math.random() * rndCnt) + minCnt;
                for (int i = 0; i < rnd; i++) {
                    final int cnt = i % btn3Arr.length;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btn3.setText(btn3Arr[cnt]);
                        }
                    });
                    SystemClock.sleep((int)((float)i / rnd * delay));
                }
                isThreadStart3 = false;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.prg3).setVisibility(View.INVISIBLE);
                    }
                });

                threadEnd();
            }
        };
        thread.start();
    }

}
