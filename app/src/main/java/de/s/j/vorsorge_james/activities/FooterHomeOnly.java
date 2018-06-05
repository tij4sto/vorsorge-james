package de.s.j.vorsorge_james.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.activities.childListViewActivity.ChildListViewActivity;

public final class FooterHomeOnly {

    public FooterHomeOnly(final Activity activityContext){
        Button homeButton = activityContext.findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityContext.finish();
                Intent openChildListViewActivity = new Intent(activityContext, ChildListViewActivity.class);
                activityContext.startActivity(openChildListViewActivity);
            }
        });
    }

}
