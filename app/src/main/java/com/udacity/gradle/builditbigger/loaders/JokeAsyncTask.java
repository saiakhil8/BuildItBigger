package com.udacity.gradle.builditbigger.loaders;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.classes.TaskListener;

import java.io.IOException;

public class JokeAsyncTask extends AsyncTask<String, Void, String> {


    private static MyApi myApiService;
    private TaskListener taskListener;

    @Override
    protected void onPreExecute() {
        taskListener.onPreExecute();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

        if (strings[0] == null) {
            return "";
        }

        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)

                    .setRootUrl(strings[0])
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();
        }


        try {
            return myApiService.myEndpoint().getJoke().execute().getData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setListener(TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    public void removeListener() {
        this.taskListener = null;
    }

    @Override
    protected void onPostExecute(String s) {
        taskListener.onPostExecute(s);
        removeListener();
        super.onPostExecute(s);
    }
}
