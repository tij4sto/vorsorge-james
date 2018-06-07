package de.s.j.vorsorge_james.activities;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.activities.addChildActivity.AddChildActivity;
import de.s.j.vorsorge_james.activities.childListViewActivity.ChildListViewActivity;

public final class Footer {

    public Footer(final Activity activityContext){
        Button homeButton = activityContext.findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityContext.finish();
                Log.d("footer", "click registered");
                Intent openChildListViewActivity = new Intent(activityContext, ChildListViewActivity.class);
                activityContext.startActivity(openChildListViewActivity);
            }
        });

        Button addChildButton = activityContext.findViewById(R.id.addChildButton);
        addChildButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openAddChildActivity = new Intent(activityContext, AddChildActivity.class);
                activityContext.startActivity(openAddChildActivity);
            }
        });

        Button notificationButton = activityContext.findViewById(R.id.showNotificationsButton);
        notificationButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
