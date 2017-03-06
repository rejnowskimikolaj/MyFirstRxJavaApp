package com.example.rent.myfirstrxjavaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    CompositeDisposable subscriptions = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Disposable dis = Observable.fromArray(1, 2, 3)
                .flatMap(integer -> Observable.just(integer).doOnNext(value ->
                        Log.d(TAG, "On next " + value + " thread " + Thread.currentThread().getName())).delay(2, TimeUnit.SECONDS))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    Log.d(TAG, "On subscribe " + integer + " thread " + Thread.currentThread().getName());
                }, throwable -> {
                    Log.e(TAG, "Blad", throwable);
                });
        subscriptions.add(dis);
    }
}
