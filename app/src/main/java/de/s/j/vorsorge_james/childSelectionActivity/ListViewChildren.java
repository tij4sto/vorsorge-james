package de.s.j.vorsorge_james.childSelectionActivity;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.text.Layout;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.dbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

/**
 * Created by Freedurr on 16.05.2018.
 * Represents the List View of children in ChildSelectionActivity.
 * Provides method to load children into List View.
 */

class ListViewChildren {

    private dbAccess dataSource;
    private Context context;
    private ConstraintLayout layout;

    /**
     *
     * @param dataSource Access to a database from which the List View will
     *                   get children to display.
     * @param context
     * @param layout
     */
    ListViewChildren(dbAccess dataSource, Context context, ConstraintLayout layout){
        this.dataSource = dataSource;
        this.context = context;
        this.layout = layout;
    }

    /**
     *  Gets children from the connected database and displays them in the List View
     */
    void loadChildren(){
        List<DbKindDatensatz> listKinder = dataSource.getKindListe();
        View viewToConnectTo = layout.getViewById(R.id.ChildSelectionTitle);

        int idGen = 1000;
        for(DbKindDatensatz kindDatensatz : listKinder){

            // ChildButton buttonKind = new ChildButton(kindDatensatz, context);
           // ChildButton2 buttonKind = new ChildButton2(idGen++, kindDatensatz, context);
            //Button buttonKind = new Button(context, null, R.style.altButton);
            Button buttonKind = new Button(new ContextThemeWrapper(context, R.style.AltButton), null, -1);
            buttonKind.setId(idGen++);
            buttonKind.setText(kindDatensatz.getName());

          //  Button button = new Button(context, null, R.style.ChildSelectionButton);
           // button.

            ConstraintSet constraintSet = new ConstraintSet();
            layout.addView(buttonKind);
            constraintSet.clone(layout);
            constraintSet.connect(buttonKind.getId(), ConstraintSet.TOP, viewToConnectTo.getId(), ConstraintSet.BOTTOM, 32);
            constraintSet.connect(buttonKind.getId(), ConstraintSet.START, layout.getId(), ConstraintSet.START, 32);
            constraintSet.connect(buttonKind.getId(), ConstraintSet.END, layout.getId(), ConstraintSet.END, 32);
            constraintSet.applyTo(layout);
            viewToConnectTo = buttonKind;
        }
    }


}
