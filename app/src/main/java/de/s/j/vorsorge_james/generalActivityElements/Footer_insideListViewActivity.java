package de.s.j.vorsorge_james.generalActivityElements;

import android.app.Activity;
import android.widget.Button;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.utils.guiUtils.OnClickSetter;

public final class Footer_insideListViewActivity {

    public Footer_insideListViewActivity(final Activity activityContext){
        Button addChildButton = activityContext.findViewById(R.id.addChildButton);
        OnClickSetter.openAddChildActivity(addChildButton);
    }
}
