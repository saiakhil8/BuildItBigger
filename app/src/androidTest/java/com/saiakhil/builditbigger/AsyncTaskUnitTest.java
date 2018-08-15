/*
 * Copyright (c) 2018. SAIAKHIL V
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

package com.saiakhil.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.classes.TaskListener;
import com.udacity.gradle.builditbigger.loaders.JokeAsyncTask;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

@RunWith(AndroidJUnit4.class)
public class AsyncTaskUnitTest {

    @Test
    public void testEndPoint(){

        JokeAsyncTask jokeAsyncTask = new JokeAsyncTask();
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        jokeAsyncTask.setListener(new TaskListener() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onPostExecute(String joke) {

                Assert.assertNotSame(joke,null);
                countDownLatch.countDown();
            }
        });

        jokeAsyncTask.execute(MainActivity.GCLOUD_LOCAL_URL);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.fillInStackTrace();
        }


    }

}
