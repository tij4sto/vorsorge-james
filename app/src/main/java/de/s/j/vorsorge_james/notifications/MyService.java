package de.s.j.vorsorge_james.notifications;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MyService extends JobService {

    BackgroundTask backgroundTask;
    public static Activity activity;

    @Override
    public boolean onStartJob(final JobParameters job) {

        backgroundTask = new BackgroundTask(this.activity){
            @Override
            protected void onPostExecute(Activity savedActivity){

                Toast.makeText(getApplicationContext(),
                        "Activity not null: " + (savedActivity != null) , Toast.LENGTH_LONG).show();
                notificationHelper.sendSampleNotification(savedActivity, "any text");
                jobFinished(job, false);
            }
        };
        backgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }

    public static class BackgroundTask extends AsyncTask<Void, Void, Activity> {

        Activity activity;
        NotificationHelper notificationHelper;

        BackgroundTask(Activity activity){
            this.notificationHelper = new NotificationHelper(activity);
            this.activity = activity;
            Log.d("Background activity", " Task is running " + activity.toString());
        }

        @Override
        protected Activity doInBackground(Void... voids) {

            return activity;
        }
    }
}
