package de.s.j.vorsorge_james.activities;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.hilfsklassen.OnClickSetter;

public final class Footer_insideListViewActivity {

    public Footer_insideListViewActivity(final Activity activityContext){
        Button addChildButton = activityContext.findViewById(R.id.addChildButton);
        OnClickSetter.openAddChildActivity(addChildButton);

        // TODO: Remove notification manual trigger
        final Button noteButton = activityContext.findViewById(R.id.homeButton);
        noteButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new NotificationHelper(activityContext).sendSampleNotification("Tikki");

            }
        });
    }
}
