package com.wanweitong.smartbj.rx2;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private TextView mTv;
    private Subscription mSubscribe;
    Handler mHandler = new Handler();
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = (TextView) findViewById(R.id.tv);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this, 1000);
                init();
                count++;
            }
        };
        mHandler.postDelayed(r, 1000);
    }

    private void init() {
        mSubscribe = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(count);
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(TAG, "call: "+integer);
                mTv.setText(integer+"");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 销毁了");
       mSubscribe.unsubscribe();
    }
}
