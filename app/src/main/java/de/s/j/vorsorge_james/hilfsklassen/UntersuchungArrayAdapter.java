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
import java.util.Date;
import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbKindHatUntersuchung.DbKindHatUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungTyp;

public class UntersuchungArrayAdapter extends ArrayAdapter<DbUntersuchungDatensatz> {

    private Context mContext;
    private List<DbUntersuchungDatensatz> alleUntersuchungen = new ArrayList<>();
    private List<DbUntersuchungDatensatz> benoetigteUntersuchungen = new ArrayList<>();
    DbKindDatensatz kind;
    private DbAccess datasource;

    public UntersuchungArrayAdapter(@NonNull Context context, List<DbUntersuchungDatensatz> list, DbKindDatensatz kind){
        super(context, 0, list);
        this.alleUntersuchungen = DbUntersuchungTyp.getAlleUntersuchungen();
        this.benoetigteUntersuchungen = DbUntersuchungTyp.getBenoetigteUntersuchungenByKind(kind);
        this.mContext = context;
        this.kind = kind;
        this.datasource = new DbAccess(context);
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
            boolean isNoetig = checkItemIsInNoetigeList(untersuchung);
           // boolean isEingetragen = isKindInUntersuchung(datasource.getKindHatUntersuchungListe(), this.kind.getId(), untersuchung.getId());
            boolean isEingetragen = datasource.isKindInUntersuchung(this.kind.getId(), untersuchung.getId());

            if(isEingetragen){
                listItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_untersuchung_green, parent, false);

                DbKindHatUntersuchungDatensatz data = this.datasource.getKindHatUntersuchungByPK(this.kind.getId(), (int) untersuchung.getId());
                String  date = DateFormatter.formatDate(new Date(data.getTermin()));
                //Setze Holder Tags
                holder = new ViewHolder();
                holder.tvName = (TextView) listItem.findViewById(R.id.name);
                holder.tvZeitraum = (TextView) listItem.findViewById(R.id.zeitraum);
                holder.id = this.alleUntersuchungen.get(position).getId();

                listItem.setTag(holder);
                listItem.setTag(R.id.name, holder.tvName);
                listItem.setTag(R.id.zeitraum, holder.tvZeitraum);

                //Fülle View mit Daten
                holder.tvName.setText("" + untersuchung.getName());
                holder.tvZeitraum.setText("" + date + " bei Arzt: " + data.getArzt());

            }

            else if(isNoetig){
                listItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_untersuchung_orange, parent, false);

                holder = new ViewHolder();
                holder.tvName = (TextView) listItem.findViewById(R.id.name);
                holder.tvZeitraum = (TextView) listItem.findViewById(R.id.zeitraum);

                holder.id = this.alleUntersuchungen.get(position).getId();

                listItem.setTag(holder);
                listItem.setTag(R.id.name, holder.tvName);
                listItem.setTag(R.id.zeitraum, holder.tvZeitraum);
                holder.tvName.setText("" + untersuchung.getName());
                holder.tvZeitraum.setText(DbUntersuchungTyp.getZeitraumString(untersuchung, this.kind));
            }

            else{
                listItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_untersuchung_grey, parent, false);

                holder = new ViewHolder();
                holder.tvName = (TextView) listItem.findViewById(R.id.name);
                holder.tvZeitraum = (TextView) listItem.findViewById(R.id.zeitraum);

                holder.id = this.alleUntersuchungen.get(position).getId();

                listItem.setTag(holder);
                listItem.setTag(R.id.name, holder.tvName);
                listItem.setTag(R.id.zeitraum, holder.tvZeitraum);
                holder.tvName.setText("" + untersuchung.getName());
                holder.tvZeitraum.setText(DbUntersuchungTyp.getZeitraumString(untersuchung, this.kind));
            }
        }

        else{
            holder = (ViewHolder) listItem.getTag();
        }

        return listItem;
    }

 /*   private boolean isKindInUntersuchung(List<DbKindHatUntersuchungDatensatz> list, int idK, int idU){
        for(DbKindHatUntersuchungDatensatz data : list){
            if(idK == data.getIdKind() && idU == data.getIdUnterschung()) return true;
        }

        return false;
    }*/

    static class ViewHolder{
        public long id;
        public TextView tvName;
        public TextView tvZeitraum;
    }
}
