package com.udacity.builditbigger;

import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.util.Log;

import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.JokeLoadListener;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@RunWith(AndroidJUnit4.class)
public class TestAsyncTask extends TestCase implements JokeLoadListener
{
    private CountDownLatch signal;

    @Before
    public void initialize()
    {
        signal = new CountDownLatch(1);
    }

    @Test
    public void testAsyncJoke() throws InterruptedException
    {
        EndpointsAsyncTask.pullJokeFromServer(this);
        signal.await();
    }

    @Override
    public void jokeDownloadCompleted(String joke)
    {
        signal.countDown();
        if (joke != null)
            Log.d("test", joke);
        assertTrue(!TextUtils.isEmpty(joke));
    }
}
