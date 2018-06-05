package de.s.j.vorsorge_james.hilfsklassen;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import de.s.j.vorsorge_james.activities.addChildActivity.AddChildActivity;

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
