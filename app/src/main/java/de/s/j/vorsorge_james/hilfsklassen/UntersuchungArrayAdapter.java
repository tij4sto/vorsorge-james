package de.s.j.vorsorge_james.hilfsklassen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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
    DbKindDatensatz kind;

    public UntersuchungArrayAdapter(@NonNull Context context, List<DbUntersuchungDatensatz> list, DbKindDatensatz kind){
        super(context, 0, list);
        this.alleUntersuchungen = DbUntersuchungTyp.getAlleUntersuchungen();
        this.benoetigteUntersuchungen = DbUntersuchungTyp.getBenoetigteUntersuchungenByKind(kind);
        this.mContext = context;
        this.kind = kind;
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
        ViewHolder holder = null;
        DbUntersuchungDatensatz untersuchung = alleUntersuchungen.get(position);

        if(listItem == null || ((ViewHolder) listItem.getTag()).id != this.alleUntersuchungen.get(position).getId()){
            boolean b = checkItemIsInNoetigeList(untersuchung);

            if(b){
                listItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_untersuchung_orange, parent, false);
            }

            else{
                listItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_untersuchung_grey, parent, false);
            }

            holder = new ViewHolder();
            holder.tvName = (TextView) listItem.findViewById(R.id.name);
            holder.tvZeitraum = (TextView) listItem.findViewById(R.id.zeitraum);

            holder.id = this.alleUntersuchungen.get(position).getId();

            listItem.setTag(holder);
            listItem.setTag(R.id.name, holder.tvName);
            listItem.setTag(R.id.zeitraum, holder.tvZeitraum);
        }

        else{
            holder = (ViewHolder) listItem.getTag();
        }

        holder.tvName.setText("" + untersuchung.getId());
        holder.tvZeitraum.setText(DbUntersuchungTyp.getZeitraumString(untersuchung, this.kind) + " nach  der Geburt");
        return listItem;
    }

    static class ViewHolder{
        public long id;
        public TextView tvName;
        public TextView tvZeitraum;
    }
}
