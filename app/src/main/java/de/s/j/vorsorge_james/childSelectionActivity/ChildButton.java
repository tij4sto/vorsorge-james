package de.s.j.vorsorge_james.childSelectionActivity;

import android.content.Context;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;

import java.time.LocalDate;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

/**
 * Created by Freedurr on 16.05.2018.
 */

class ChildButton extends android.support.v7.widget.AppCompatButton {

    private static int styleID = R.style.AltButton;

    private int childID;

    ChildButton(DbKindDatensatz datensatz, Context context){
        super(new ContextThemeWrapper(context, styleID), null, 0);
        setText(datensatz.getName());
        this.childID = datensatz.getId();
        setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openChildActivity();
            }
        });
    }

    private void openChildActivity(){
        // TODO: OPEN THAT ACTIVITY!!!
    }

}
