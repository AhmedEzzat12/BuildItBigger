package com.ntl.udacity.displayjoke;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class JokeDisplayer extends AppCompatActivity
{

    private static final String JOKE_FRAGMENT = "joke_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_displayer);
        String joke = getIntent().getStringExtra("joke");
        Bundle bundle = new Bundle();
        bundle.putString("joke", joke);

        Fragment foundFragment = getSupportFragmentManager().findFragmentByTag(JOKE_FRAGMENT);

        if (foundFragment != null)
        {
            foundFragment.setArguments(bundle);
        } else
        {
            JokeDisplayerFragment jokeDisplayerFragment = new JokeDisplayerFragment();
            jokeDisplayerFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.joke_fragment_containter, jokeDisplayerFragment, JOKE_FRAGMENT).commit();
        }

    }
}
