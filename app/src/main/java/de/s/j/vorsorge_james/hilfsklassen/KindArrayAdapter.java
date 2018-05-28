package de.s.j.vorsorge_james.hilfsklassen;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;

public class KindArrayAdapter extends ArrayAdapter<DbKindDatensatz> {

    private Context mContext;
    private List<DbKindDatensatz> mList = new ArrayList<>();

    public KindArrayAdapter(@NonNull Context context, List<DbKindDatensatz> list) {
        super(context, 0, list);
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_show_children_list, parent, false);
        }

        DbKindDatensatz kind = mList.get(position);

        TextView tvName = (TextView) listItem.findViewById(R.id.name);
        TextView tvGeburtstag = (TextView) listItem.findViewById(R.id.geburtstag);
        tvName.setText(kind.getName());
        tvGeburtstag.setText(DateFormatter.formatDate(kind.getDatum()));

        return listItem;
    }
}
