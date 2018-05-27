package de.s.j.vorsorge_james.hilfsklassen;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungTyp;

public class UntersuchungArrayAdapter extends ArrayAdapter<DbUntersuchungDatensatz> {
    private Context mContext;
    private List<DbUntersuchungDatensatz> alleUntersuchungen = new ArrayList<>();
    private List<DbUntersuchungDatensatz> benoetigteUntersuchungen = new ArrayList<>();

    public UntersuchungArrayAdapter(Context context, List<DbUntersuchungDatensatz> list, DbKindDatensatz kind){
        super(context, 0, list);
        alleUntersuchungen = DbUntersuchungTyp.getAlleUntersuchungen();
        benoetigteUntersuchungen = DbUntersuchungTyp.getBenoetigteUntersuchungenByKind(kind);
    }
}
