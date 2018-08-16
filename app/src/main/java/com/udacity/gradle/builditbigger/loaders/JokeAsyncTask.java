/*
 * Copyright (c) 2018. VENKATA SAIAKHIL KUMAR VEMULA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        if (taskListener!=null) taskListener.onPreExecute();
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

    //For Avoiding crash if Async runs in background even after main activity getting destroyed
    public void removeListener() {
        this.taskListener = null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (taskListener!=null) taskListener.onPostExecute(s);
        removeListener();
        super.onPostExecute(s);
    }
}
