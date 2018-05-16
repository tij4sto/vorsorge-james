package de.s.j.vorsorge_james.childSelectionActivity;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.view.ContextThemeWrapper;
import android.widget.TextView;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

/**
 * Created by Freedurr on 16.05.2018.
 */

class ChildButton2 extends ConstraintLayout {

    private static int styleID = R.style.ChildSelectionButton;
    private static int textViewStyleID = R.style.ChildSelectionButton_TextView;

    ChildButton2(int id, DbKindDatensatz datensatz, Context context){
        //super(context);

        super(new ContextThemeWrapper(context, styleID), null, 0);
        setId(id);
        addTextView(datensatz.getName());
    }

    private void addTextView(String text){
       // TextView textView = new TextView(this.getContext());
        TextView textView = new TextView(new ContextThemeWrapper(this.getContext(), textViewStyleID), null, 0);
        int id = this.getId();
        textView.setId(id * 2);
        textView.setText(text);

        ConstraintSet constraintSet = new ConstraintSet();
        this.addView(textView);


        constraintSet.clone(this);
        int t = textView.getMeasuredWidth();
        constraintSet.centerHorizontally(textView.getId(), this.getId());
        constraintSet.constrainDefaultWidth(textView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainMaxWidth(textView.getId(), ConstraintSet.WRAP_CONTENT);

      /*  constraintSet.connect(textView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 8);
        constraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 8);
        constraintSet.connect(textView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 8);
        constraintSet.connect(textView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 8);*/
        constraintSet.applyTo(this);
    }
}
