package de.s.j.vorsorge_james.childSelectionActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;

import de.s.j.vorsorge_james.MainActivity;
import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.childActivity.ChildActivity;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

/**
 * Created by Freedurr on 16.05.2018.
 */

class ChildButton extends android.support.v7.widget.AppCompatButton {

    private static int styleID = R.style.AltButton;

    private DbKindDatensatz datensatz;
    private Context context;

    ChildButton(DbKindDatensatz datensatz, Context context){
        super(new ContextThemeWrapper(context, styleID), null, 0);
        setText(datensatz.getName());
        this.datensatz = datensatz;
        this.context = context;
        setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openChildActivity();
            }
        });
    }

    private void openChildActivity(){
        ChildActivity.currentChild = datensatz;
        Intent intent = new Intent(context, ChildActivity.class);
        context.startActivity(intent);
        // TODO: OPEN THAT ACTIVITY!!!
    }

}
