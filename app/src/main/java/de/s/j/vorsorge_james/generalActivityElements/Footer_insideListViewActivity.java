package de.s.j.vorsorge_james.generalActivityElements;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.childListViewActivity.ChildListViewActivity;
import de.s.j.vorsorge_james.utils.guiUtils.OnClickSetter;

public final class Footer_insideListViewActivity {

    public Footer_insideListViewActivity(final Activity activityContext){
        Button addChildButton = activityContext.findViewById(R.id.addChildButton);
        OnClickSetter.openAddChildActivity(addChildButton);

        // TODO: Remove notification manual trigger
        final Button noteButton = activityContext.findViewById(R.id.button3);
        noteButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new NotificationHelper(activityContext).sendSampleNotification("Tikki");

            }
        });
    }
}
