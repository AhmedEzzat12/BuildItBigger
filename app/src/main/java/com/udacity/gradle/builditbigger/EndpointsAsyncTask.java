package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.ntl.udacity.jokegenerator.JokeTelling;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Void, Void, String>
{
    private static MyApi myApiService = null;
    private JokeLoadListener jokeLoadListener;

    public EndpointsAsyncTask(JokeLoadListener jokeLoadListener)
    {
        this.jokeLoadListener = jokeLoadListener;
    }

    public static void pullJokeFromServer(JokeLoadListener jokeLoadListener)
    {
        new EndpointsAsyncTask(jokeLoadListener).execute();
    }

    @Override
    protected String doInBackground(Void... voids)
    {
        Log.d(EndpointsAsyncTask.class.getSimpleName(), "start download");
        if (myApiService == null)
        {
            // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer()
                    {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException
                        {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try
        {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e)
        {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result)
    {

        Log.d(EndpointsAsyncTask.class.getSimpleName(), "finish download");
        if (!result.equals(new JokeTelling().getJoke()))
        {
            jokeLoadListener.jokeDownloadCompleted(null);
        } else
            jokeLoadListener.jokeDownloadCompleted(result);

    }
}
