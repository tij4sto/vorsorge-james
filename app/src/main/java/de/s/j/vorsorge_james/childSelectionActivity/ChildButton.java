package de.s.j.vorsorge_james.childSelectionActivity;

import android.content.Context;
import android.support.v7.view.ContextThemeWrapper;
import android.widget.Button;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

/**
 * Created by Freedurr on 16.05.2018.
 */

class ChildButton extends android.support.v7.widget.AppCompatButton {

    private static int styleID = R.style.ChildSelectionButton;

    ChildButton(DbKindDatensatz datensatz, Context context){
       // super(context);
        super(new ContextThemeWrapper(context, styleID), null, 0);

        setText(datensatz.getName());
    }

}
