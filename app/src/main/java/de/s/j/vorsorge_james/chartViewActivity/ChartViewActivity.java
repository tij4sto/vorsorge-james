package de.s.j.vorsorge_james.chartViewActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.s.j.vorsorge_james.R;
import de.s.j.vorsorge_james.database.DbAccess;
import de.s.j.vorsorge_james.database.dbKindHatGewichtUndGroesse.DbKindHatGewichtUndGroesse;
import de.s.j.vorsorge_james.hilfsklassen.DateFormatter;

public class ChartViewActivity extends AppCompatActivity {

    private int idKind;
    private DbAccess datasource;
    Intent intent;
    GraphView graphGroesse;
    GraphView graphGewicht;
    List<DbKindHatGewichtUndGroesse> liste;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view);
        this.datasource = new DbAccess(this);
        this.graphGroesse = (GraphView) findViewById(R.id.graph1);
        this.graphGewicht = (GraphView) findViewById(R.id.graph2);
        this.intent = getIntent();
        this.idKind = Integer.parseInt(intent.getStringExtra("idK"));

        /* Testdaten
        this.datasource.saveGewichtUndGroesseInDb(2, "05/01/2018", 50, 1);
        this.datasource.saveGewichtUndGroesseInDb(2, "05/02/2018", 51, 1);
        this.datasource.saveGewichtUndGroesseInDb(2, "05/03/2018", 52, 1);
        this.datasource.saveGewichtUndGroesseInDb(2, "05/04/2018", 53, 2);
        this.datasource.saveGewichtUndGroesseInDb(2, "05/05/2018", 54, 2);
        this.datasource.saveGewichtUndGroesseInDb(2, "05/06/2018", 55, 2);
        this.datasource.saveGewichtUndGroesseInDb(2, "05/07/2018", 56, 2);
        this.datasource.saveGewichtUndGroesseInDb(2, "05/08/2018", 57, 2);
        this.datasource.saveGewichtUndGroesseInDb(2, "05/09/2018", 58, 3);
        this.datasource.saveGewichtUndGroesseInDb(2, "05/10/2018", 59, 2);
        this.datasource.saveGewichtUndGroesseInDb(2, "05/11/2018", 60, 3);
        this.datasource.saveGewichtUndGroesseInDb(2, "05/12/2018", 65, 5);
        this.datasource.saveGewichtUndGroesseInDb(2, "05/13/2018", 165, 15);
        */


        this.liste = datasource.getGewichtGroesseListe(idKind);

        if(liste.size() > 0) showGraph();
    }

    private void showGraph(){
        LineGraphSeries<DataPoint> groesseGraph = new LineGraphSeries<>();
        for(int i = 0; i < liste.size(); i++){
            groesseGraph.appendData(new DataPoint(new Date(liste.get(i).getDate()), liste.get(i).getCm()), true, liste.size());
        }

        groesseGraph.setDrawBackground(true);
        groesseGraph.setBackgroundColor(getResources().getColor(R.color.tealLightestTransparent));
        groesseGraph.setColor(getResources().getColor(R.color.colorPrimaryDark));
        groesseGraph.setDrawDataPoints(true);

        groesseGraph.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Number d = dataPoint.getX();
                Date date = new Date(d.longValue());
                String dateString = DateFormatter.formatDate(date);
                Toast.makeText(ChartViewActivity.this, "Datum: " + dateString + " Grösse: " + dataPoint.getY() + " cm", Toast.LENGTH_SHORT).show();
            }
        });

        graphGroesse.addSeries(groesseGraph);
        graphGroesse.getGridLabelRenderer().setLabelFormatter(new CustomLabelFormatter(this , "cm"));
        graphGroesse.getGridLabelRenderer().setNumHorizontalLabels(liste.size() < 4 ? liste.size() : 4); //4 bei 4 Zoll. Je größer  desto mehr.
        graphGroesse.getViewport().setMinX(new Date(liste.get(0).getDate()).getTime());
        graphGroesse.getViewport().setMaxX(new Date(liste.get(liste.size()-1).getDate()).getTime());
        graphGroesse.getViewport().setXAxisBoundsManual(true);
        graphGroesse.getGridLabelRenderer().setHumanRounding(false);

        LineGraphSeries<DataPoint> gewichtGraph = new LineGraphSeries<>();
        for(int i = 0; i < liste.size(); i++){
            gewichtGraph.appendData(new DataPoint(new Date(liste.get(i).getDate()), liste.get(i).getKg()), true, liste.size());
        }

        gewichtGraph.setDrawBackground(true);
        gewichtGraph.setBackgroundColor(getResources().getColor(R.color.tealLightestTransparent));
        gewichtGraph.setColor(getResources().getColor(R.color.colorPrimaryDark));
        gewichtGraph.setDrawDataPoints(true);

        gewichtGraph.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Number d = dataPoint.getX();
                Date date = new Date(d.longValue());
                String dateString = DateFormatter.formatDate(date);
                Toast.makeText(ChartViewActivity.this, "Datum: " + dateString + " Gewicht: " + dataPoint.getY() + " kg", Toast.LENGTH_SHORT).show();
            }
        });

        graphGewicht.addSeries(gewichtGraph);
        graphGewicht.getGridLabelRenderer().setLabelFormatter(new CustomLabelFormatter(this , "kg"));
        graphGewicht.getGridLabelRenderer().setNumHorizontalLabels(liste.size() < 4 ? liste.size() : 4); //4 bei 4 Zoll. Je größer  desto mehr.
        graphGewicht.getViewport().setMinX(new Date(liste.get(0).getDate()).getTime());
        graphGewicht.getViewport().setMaxX(new Date(liste.get(liste.size()-1).getDate()).getTime());
        graphGewicht.getViewport().setXAxisBoundsManual(true);
        graphGewicht.getGridLabelRenderer().setHumanRounding(false);

    }

    class CustomLabelFormatter extends DefaultLabelFormatter{
        /**
         * the date format that will convert
         * the unix timestamp to string
         */
        protected final DateFormat mDateFormat;

        /**
         * calendar to avoid creating new date objects
         */
        protected final Calendar mCalendar;

        protected String label;

        /**
         * create the formatter with the Android default date format to convert
         * the x-values.
         *
         * @param context the application context
         */
        public CustomLabelFormatter(Context context, String label) {
            mDateFormat = android.text.format.DateFormat.getDateFormat(context);
            mCalendar = Calendar.getInstance();
            this.label = label;
        }

        /**
         * create the formatter with your own custom
         * date format to convert the x-values.
         *
         * @param context the application context
         * @param dateFormat custom date format
         */
        public CustomLabelFormatter(Context context, DateFormat dateFormat) {
            mDateFormat = dateFormat;
            mCalendar = Calendar.getInstance();
        }

        /**
         * formats the x-values as date string.
         *
         * @param value raw value
         * @param isValueX true if it's a x value, otherwise false
         * @return value converted to string
         */
        @Override
        public String formatLabel(double value, boolean isValueX) {
            if (isValueX) {
                // format as date
                mCalendar.setTimeInMillis((long) value);
                return mDateFormat.format(mCalendar.getTimeInMillis());
            } else {
                return super.formatLabel(value, isValueX) + " " + label;
            }
        }
    }
}
