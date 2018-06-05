package de.s.j.vorsorge_james.childListViewActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.Calendar;
import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.alarmManager.FireingTime;
import de.s.j.vorsorge_james.alarmManager.MyAlarm;
import de.s.j.vorsorge_james.alarmManager.MyAlarmManager;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.generalActivityElements.Footer_insideListViewActivity;
import de.s.j.vorsorge_james.hilfsklassen.KindArrayAdapter;
import de.s.j.vorsorge_james.notifications.MyService;
import de.s.j.vorsorge_james.notifications.NotificationHelper;
import de.s.j.vorsorge_james.singleChildViewActivity.SingleChildView;

/**
 * Created by Frieza on 03.05.2018.
 */

public class ChildListViewActivity extends AppCompatActivity {

    private DbAccess dataSource;

    private static final String scheduleJobID = "scheduleVorsorgeJames";
    private FirebaseJobDispatcher jobDispatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_list_view);
        this.dataSource = new DbAccess(this);
        showChildren();

        new Footer_insideListViewActivity(this);
     /*   Button addChildButton = findViewById(R.id.addChildButton);
        OnClickSetter.openAddChildActivity(addChildButton);*/





        MyService.activity = this;
        this.jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Button startJobButton = findViewById(R.id.rightFooterButton);
        startJobButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
               //setupJobBuilder();
                Calendar calendar = Calendar.getInstance();
                Calendar startTime = FireingTime.getTime();
                Log.d("MyAlarm", "Minute in 'startTime' " + startTime.get(Calendar.MINUTE));
                Log.d("MyAlarm", "Stunde in 'startTime' " + startTime.get(Calendar.HOUR_OF_DAY));
               /* startTime.set(2018,
                        startTime.get(Calendar.MONTH),
                        startTime.get(Calendar.DATE),
                        startTime.get(Calendar.HOUR_OF_DAY),
                        startTime.get(Calendar.MINUTE) + 1,
                        30);*/
               // Toast.makeText(ChildListViewActivity.this, "started", Toast.LENGTH_LONG).show();

                //Toast.makeText(ChildListViewActivity.this, "" + calendar.getTime() + " " + startTime.getTime(), Toast.LENGTH_LONG);
                Log.d("MyAlarm", "" + calendar.getTime() + " " + startTime.getTime());
                new MyAlarmManager(ChildListViewActivity.this).start();
                //setupAlarm(startTime);
            }
        });

        Button middle = findViewById(R.id.button3);
        middle.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("cancel", "before");
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(ChildListViewActivity.this, MyAlarm.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(ChildListViewActivity.this, 0, intent, 0);
                alarmManager.cancel(pendingIntent);
                Log.d("cancel", "after");
            }
        });

    }

    private void setupAlarm(Calendar startTime){

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), (1000 * 30), pendingIntent);
    }

/*    private void setupJobBuilder(){
        Job.Builder jobBuilder = jobDispatcher.newJobBuilder();
        jobBuilder.setService(MyService.class);
        jobBuilder.setLifetime(Lifetime.FOREVER);
        jobBuilder.setRecurring(true);
        jobBuilder.setTag(scheduleJobID);
        jobBuilder.setTrigger(Trigger.executionWindow(5, 7));
        jobBuilder.setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL);
        jobBuilder.setReplaceCurrent(false);
        jobBuilder.setConstraints(Constraint.ON_ANY_NETWORK);

        Job job = jobBuilder.build();
        jobDispatcher.mustSchedule(job);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        showChildren();
    }

    private void showChildren(){
        List<DbKindDatensatz> kinder = dataSource.getKindListe();
        final ListView lv = (ListView) findViewById(R.id.children_list);
        int i = lv.getId();

        final KindArrayAdapter kinderAdapter = new KindArrayAdapter(this, kinder);
        lv.setAdapter(kinderAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChildListViewActivity.this, SingleChildView.class);
                intent.putExtra("id", "" + kinderAdapter.getItem(position).getId());
                Log.d("LEL1: ", "" + id);
                String s = intent.getStringExtra("id");
                ChildListViewActivity.this.startActivity(intent);
            }
        });
    }
}
