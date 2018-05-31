package de.s.j.vorsorge_james.singleUntersuchungView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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
        TextView tvBeschreibung = findViewById(R.id.beschreibung);
        tvBeschreibung.setText(this.untersuchung.getBeschreibung());

    }

    @Override
    protected void onStop() {
        super.onStop();
        formular.saveDoctorInformation();
    }

    void insertDatensatz(String doctor, String date){
        final int idK = kind.getId();
        final int idU = (int) untersuchung.getId();
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

