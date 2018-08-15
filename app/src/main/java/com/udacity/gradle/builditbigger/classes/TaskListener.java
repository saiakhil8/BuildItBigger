package com.udacity.gradle.builditbigger.classes;

public interface TaskListener {

    void onPreExecute();

    void onPostExecute(String joke);

}
