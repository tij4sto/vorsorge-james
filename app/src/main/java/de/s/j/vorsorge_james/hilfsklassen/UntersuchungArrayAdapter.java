package de.s.j.vorsorge_james.hilfsklassen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungTyp;

public class UntersuchungArrayAdapter extends ArrayAdapter<DbUntersuchungDatensatz> {

    private Context mContext;
    private List<DbUntersuchungDatensatz> alleUntersuchungen = new ArrayList<>();
    private List<DbUntersuchungDatensatz> benoetigteUntersuchungen = new ArrayList<>();

    public UntersuchungArrayAdapter(@NonNull Context context, List<DbUntersuchungDatensatz> list, DbKindDatensatz kind){
        super(context, 0, list);
        this.alleUntersuchungen = DbUntersuchungTyp.getAlleUntersuchungen();
        this.benoetigteUntersuchungen = DbUntersuchungTyp.getBenoetigteUntersuchungenByKind(kind);
        this.mContext = context;
    }

    private boolean checkItemIsInNoetigeList(DbUntersuchungDatensatz u){
        for(DbUntersuchungDatensatz d : this.benoetigteUntersuchungen){
            if(u.getId() == d.getId()) return true;
        }

        return false;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        DbUntersuchungDatensatz untersuchung = alleUntersuchungen.get(position);
        boolean b = checkItemIsInNoetigeList(untersuchung);

        if(listItem == null && b ){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_untersuchung_orange, parent, false);
        }

        else if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_untersuchung_grey, parent, false);

        }

        TextView tvName = (TextView) listItem.findViewById(R.id.name);
        tvName.setText("" + untersuchung.getId());
        TextView tvZeitraum = (TextView) listItem.findViewById(R.id.zeitraum);
        tvZeitraum.setText(DbUntersuchungTyp.getZeitraumString(untersuchung) + " nach  der Geburt");


        return listItem;
    }
}
