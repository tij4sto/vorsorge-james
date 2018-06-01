package de.s.j.vorsorge_james.chartViewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKindHatGewichtUndGroesse.DbKindHatGewichtUndGroesse;

public class ChartViewActivity extends AppCompatActivity {

    private int idKind;
    private DbAccess datasource;
    Intent intent;
    GraphView graph;
    List<DbKindHatGewichtUndGroesse> liste;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view);
        this.datasource = new DbAccess(this);
        this.graph = (GraphView) findViewById(R.id.graph);
        this.intent = getIntent();
        this.idKind = Integer.parseInt(intent.getStringExtra("idK"));

        this.liste = datasource.getGewichtGroesseListe(idKind);
        this.datasource.saveGewichtUndGroesseInDb(1, "05/31/2018", 50, 3);
        this.datasource.saveGewichtUndGroesseInDb(1, "05/30/2018", 49, 3);
        this.datasource.saveGewichtUndGroesseInDb(1, "05/29/2018", 48, 2);
        this.datasource.saveGewichtUndGroesseInDb(1, "05/28/2018", 44, 1);
        showGraph();
    }

    private void showGraph(){

        LineGraphSeries<DataPoint> punkte = new LineGraphSeries<>();
        for(int i = 1; i < liste.size(); i++){
            
        }


        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
    }
}
