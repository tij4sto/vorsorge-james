package de.s.j.vorsorge_james.chartViewActivity;

import android.os.Bundle;
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
    GraphView graph;
    List<DbKindHatGewichtUndGroesse> liste;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view);
        this.datasource = new DbAccess(this);
        this.graph = (GraphView) findViewById(R.id.graph);
        this.liste = datasource.getGewichtGroesseListe();
    }

    private void showGraph(){
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
