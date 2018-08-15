package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.saiakhil.displayjoke.JokeActivity;
import com.udacity.gradle.builditbigger.classes.TaskListener;
import com.udacity.gradle.builditbigger.loaders.JokeAsyncTask;



public class MainActivity extends AppCompatActivity implements TaskListener {

    ProgressBar progressBar;
    private JokeAsyncTask jokeAsyncTask;
    public static final String GCLOUD_LOCAL_URL = "http://10.0.2.2:8080/_ah/api/";
    private static final int JOKE_ACTIVITY_REQUEST_CODE = 0;
    private MainActivityFragment mainActivityFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        progressBar = findViewById(R.id.progressBarMain);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {

        if (progressBar.getVisibility() == View.GONE) {

            jokeAsyncTask = new JokeAsyncTask();
            jokeAsyncTask.setListener(this);
            jokeAsyncTask.execute(GCLOUD_LOCAL_URL);


        }

    }

    @Override
    protected void onDestroy() {
        if (jokeAsyncTask!=null) jokeAsyncTask.removeListener();
        super.onDestroy();
    }

    @Override
    public void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostExecute(String joke) {
        progressBar.setVisibility(View.GONE);
        if (joke==null) return;
        Toast.makeText(MainActivity.this,joke,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, JokeActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT,joke);
        startActivityForResult(intent,JOKE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==JOKE_ACTIVITY_REQUEST_CODE){

            if (BuildConfig.IS_PAID){
                mainActivityFragment.showAd();
            }

        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
