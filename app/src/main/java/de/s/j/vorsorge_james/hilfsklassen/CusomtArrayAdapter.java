package de.s.j.vorsorge_james.hilfsklassen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Property;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class CusomtArrayAdapter extends ArrayAdapter<Property> {

    public CusomtArrayAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }


}
