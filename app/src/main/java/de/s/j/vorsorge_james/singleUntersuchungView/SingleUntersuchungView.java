package de.s.j.vorsorge_james.singleUntersuchungView;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKind.DbKindDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungDatensatz;
import de.s.j.vorsorge_james.database.dbUntersuchung.DbUntersuchungTyp;

/**
 * Created by Frieza on 03.05.2018.
 */

public class SingleUntersuchungView extends AppCompatActivity {

    private DbAccess dataSource;
    private Intent intent;
    private DbKindDatensatz kind;
    private DbUntersuchungDatensatz untersuchung;

    private SetUntersuchungFormular formular;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_untersuchung_view);
        formular = new SetUntersuchungFormular(this);
        formular.loadDoctorInformation();
        this.dataSource = new DbAccess(this);

        //Handling Event Data
        this.intent = getIntent();
        this.kind = this.dataSource.getKindDatensatzById(Integer.parseInt(intent.getStringExtra("kid")));
        this.untersuchung = DbUntersuchungTyp.getUntersuchungDatensatzById(Integer.parseInt(intent.getStringExtra("uid")));

        //Zeige Daten in TextView an.
        TextView tvName = findViewById(R.id.name);
        tvName.setText(this.untersuchung.getName());
        final TextView tvBeschreibung = findViewById(R.id.textViewDescriptionUntersuchung);
        tvBeschreibung.setText(this.untersuchung.getBeschreibung());
      //  tvBeschreibung.setText(this.untersuchung.getBeschreibung());

        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tvBeschreibung.setVisibility(View.VISIBLE);
                } else {
                    tvBeschreibung.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        formular.saveDoctorInformation();
    }

    void insertDatensatz(String doctor, String date){
        final int idK = kind.getId();
        final int idU = (int) untersuchung.getId();
        // TODO: Make overwriting possible.
        boolean b = dataSource.createKindHatUntersuchungDatensatz(idK, idU, doctor, date);
        finish();
    }

    DbKindDatensatz getKind(){
        return this.kind;
    }

    DbUntersuchungDatensatz getUntersuchung(){
        return this.untersuchung;
    }
}

