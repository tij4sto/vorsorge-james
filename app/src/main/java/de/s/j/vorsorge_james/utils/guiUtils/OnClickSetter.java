package de.s.j.vorsorge_james.utils.guiUtils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;

import de.s.j.vorsorge_james.addChildActivity.AddChildActivity;
import de.s.j.vorsorge_james.childListViewActivity.ChildListViewActivity;

/**
 * Class that provides static methods for setting OnClickListeners
 * on View-objects.
 */
public final class OnClickSetter  {

    public static void openAddChildActivity(final View view){
        final Context context = view.getContext();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openAddChildActivity = new Intent(context, AddChildActivity.class);
                context.startActivity(openAddChildActivity);
            }
        });
    }


}
