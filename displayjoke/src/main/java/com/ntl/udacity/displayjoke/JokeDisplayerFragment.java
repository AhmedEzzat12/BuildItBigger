package com.ntl.udacity.displayjoke;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class JokeDisplayerFragment extends Fragment
{


    public JokeDisplayerFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_joke_displayer, container, false);
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            String jokeStr = bundle.getString("joke");
            TextView textView=view.findViewById(R.id.joke_fragment_tv);
            textView.setText(jokeStr);
        }
        return view;
    }

}
