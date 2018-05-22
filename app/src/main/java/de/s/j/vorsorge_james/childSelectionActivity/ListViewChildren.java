package de.s.j.vorsorge_james.childSelectionActivity;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.view.View;

import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

/**
 * Created by Freedurr on 16.05.2018.
 * Represents the List View of children in ChildSelectionActivity.
 * Provides method to load children into List View.
 */

class ListViewChildren {

    private DbAccess dataSource;
    private Context context;
    private ConstraintLayout layout;

    /**
     *
     * @param dataSource Access to a database from which the List View will
     *                   get children to display.
     * @param context Context of the ListView to be run in
     * @param layout Constraint Layout containing the ListView
     */
    ListViewChildren(DbAccess dataSource, Context context, ConstraintLayout layout){
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
            ChildButton buttonKind = new ChildButton(kindDatensatz, context);
            buttonKind.setId(idGen++);
            addToLayout(viewToConnectTo, buttonKind);
            viewToConnectTo = buttonKind;
        }
    }

    /**
     * Adds a ChildButton to another View on top of it.
     * @param viewToConnectTo View on top of the button, which the button is to connect to
     * @param buttonKind ChildButton that is to be added to the layout
     */
    private void addToLayout(View viewToConnectTo, ChildButton buttonKind) {
        ConstraintSet constraintSet = new ConstraintSet();
        layout.addView(buttonKind);
        constraintSet.clone(layout);
        constraintSet.connect(buttonKind.getId(), ConstraintSet.TOP, viewToConnectTo.getId(), ConstraintSet.BOTTOM, 32);
        constraintSet.connect(buttonKind.getId(), ConstraintSet.START, layout.getId(), ConstraintSet.START, 32);
        constraintSet.connect(buttonKind.getId(), ConstraintSet.END, layout.getId(), ConstraintSet.END, 32);
        constraintSet.applyTo(layout);
    }


}
