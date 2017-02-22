package com.breeze.project1.littlestar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.breeze.project1.littlestar.fragment.PhotoListFragment;

public abstract class BaseActivity extends AppCompatActivity
{

    protected abstract void doOnCreate(Bundle savedInstanceState);


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        doOnCreate(savedInstanceState);
    }
}
