package id.mochgani.simulasielevator;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import id.mochgani.simulasielevator.database.DatabaseHelper;
import id.mochgani.simulasielevator.model.ElevatorModel;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mDB;
    private ElevatorModel eML;

    Spinner spnFloor;
    View pintuKiri,pintuKanan;
    ImageButton btnUp, btnDown;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btnOpen,btnClose;
    TextView txtFloorTitle,txtFloor,txtCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDB = new DatabaseHelper(this);
        mDB.getWritableDatabase();

        eML = new ElevatorModel();

        spnFloor = (Spinner) findViewById(R.id.spnFloor);

        btnUp = (ImageButton) findViewById(R.id.btnUp);
        btnDown = (ImageButton) findViewById(R.id.btnDown);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btnOpen = (Button) findViewById(R.id.btnOpen);
        btnClose = (Button) findViewById(R.id.btnClose);

        txtFloorTitle = (TextView) findViewById(R.id.txtFloorTitle);
        txtFloor = (TextView) findViewById(R.id.txtFloor);
        txtCondition = (TextView) findViewById(R.id.txtCondition);

        pintuKiri = (View) findViewById(R.id.pintuKiri);
        pintuKanan = (View) findViewById(R.id.pintuKanan);

        isiSpinnerFloor();

        cekFloor();

        if(eML.kondisiElevator == false && eML.kondisiPintu == false){
            hideTombolDalam();
        }

        spnFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);

                txtFloorTitle.setText("Lantai " + item.toString());

                eML.floor = Integer.parseInt(item.toString());

                cekFloor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eML.kondisiPintu == false && eML.kondisiElevator == false && eML.floor==eML.actualFloor) {
                    bukaPintu();
                    eML.kondisiPintu = true;

                    openTombolDalam();

                    hideTombolUp();
                    hideTombolDown();
                } else if(eML.kondisiPintu == false && eML.kondisiElevator == false && eML.floor!=eML.actualFloor) {
                    if(eML.floor > eML.actualFloor){
                        eML.kondisiPosisi = "UP";
                    } else if(eML.floor < eML.actualFloor){
                        eML.kondisiPosisi = "DOWN";
                    } else {
                        eML.kondisiPosisi = "-";
                    }

                    mDB.insertHistory(eML.floor,eML.kondisiPosisi,eML.floor);

                    elevatorJalan(eML.kondisiPosisi);
                }
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eML.kondisiPintu == false && eML.kondisiElevator == false && eML.floor==eML.actualFloor) {
                    bukaPintu();
                    eML.kondisiPintu = true;

                    openTombolDalam();

                    hideTombolUp();
                    hideTombolDown();
                } else if(eML.kondisiPintu == false && eML.kondisiElevator == false && eML.floor!=eML.actualFloor) {
                    if(eML.floor > eML.actualFloor){
                        eML.kondisiPosisi = "UP";
                    } else if(eML.floor < eML.actualFloor){
                        eML.kondisiPosisi = "DOWN";
                    } else {
                        eML.kondisiPosisi = "-";
                    }

                    mDB.insertHistory(eML.floor,eML.kondisiPosisi,eML.floor);

                    elevatorJalan(eML.kondisiPosisi);
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setEnabled(false);
                if(eML.kondisiPintu == true && eML.kondisiElevator == false){
                    if(eML.floor > 1){
                        eML.kondisiPosisi = "DOWN";
                    } else if(eML.floor < 1){
                        eML.kondisiPosisi = "UP";
                    } else {
                        eML.kondisiPosisi = "-";
                    }

                    mDB.insertHistory(eML.floor,eML.kondisiPosisi,1);

                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2.setEnabled(false);
                if(eML.kondisiPintu == true && eML.kondisiElevator == false){
                    if(eML.floor > 2){
                        eML.kondisiPosisi = "DOWN";
                    } else if(eML.floor < 2){
                        eML.kondisiPosisi = "UP";
                    } else {
                        eML.kondisiPosisi = "-";
                    }

                    mDB.insertHistory(eML.floor,eML.kondisiPosisi,2);

                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn3.setEnabled(false);
                if(eML.kondisiPintu == true && eML.kondisiElevator == false){
                    if(eML.floor > 3){
                        eML.kondisiPosisi = "DOWN";
                    } else if(eML.floor < 3){
                        eML.kondisiPosisi = "UP";
                    } else {
                        eML.kondisiPosisi = "-";
                    }

                    mDB.insertHistory(eML.floor,eML.kondisiPosisi,3);

                }
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn4.setEnabled(false);
                if(eML.kondisiPintu == true && eML.kondisiElevator == false){
                    if(eML.floor > 4){
                        eML.kondisiPosisi = "DOWN";
                    } else if(eML.floor < 4){
                        eML.kondisiPosisi = "UP";
                    } else {
                        eML.kondisiPosisi = "-";
                    }

                    mDB.insertHistory(eML.floor,eML.kondisiPosisi,4);

                }
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn5.setEnabled(false);
                if(eML.kondisiPintu == true && eML.kondisiElevator == false){
                    if(eML.floor > 5){
                        eML.kondisiPosisi = "DOWN";
                    } else if(eML.floor < 5){
                        eML.kondisiPosisi = "UP";
                    } else {
                        eML.kondisiPosisi = "-";
                    }

                    mDB.insertHistory(eML.floor,eML.kondisiPosisi,5);

                }
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn6.setEnabled(false);
                if(eML.kondisiPintu == true && eML.kondisiElevator == false){
                    if(eML.floor > 6){
                        eML.kondisiPosisi = "DOWN";
                    } else if(eML.floor < 6){
                        eML.kondisiPosisi = "UP";
                    } else {
                        eML.kondisiPosisi = "-";
                    }

                    mDB.insertHistory(eML.floor,eML.kondisiPosisi,6);

                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eML.kondisiPintu == true) {
                    tutupPintu();
                    hideTombolDalam();
                    eML.kondisiPintu = false;

                    elevatorJalan(eML.kondisiPosisi);
                }
            }
        });
    }

    public void isiSpinnerFloor(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.floor_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFloor.setAdapter(adapter);
    }

    public void bukaPintu(){
        int positionRight = pintuKanan.getLeft();
        int positionLeft = pintuKiri.getLeft();


        while (positionRight <= pintuKanan.getLeft()+290) {
            pintuKanan.animate().x(positionRight).setDuration(5000).start();
            positionRight++;
        }

        while (positionLeft >= pintuKiri.getLeft()-290) {
            pintuKiri.animate().x(positionLeft).setDuration(5000).start();
            positionLeft--;
        }

    }

    public void elevatorJalan(String kondisi){
        String kondisiJalan = (kondisi.equals("UP"))? "ASC" : "DESC";

        txtCondition.setText(kondisi);

        //for (int val:mDB.getHistory(kondisiJalan)) {
        if(kondisi.equals("UP")) {
            for (int i = eML.floor; i <= mDB.getHistorySingle(); i++) {
                eML.actualFloor = i;
                final int j = i;
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDB.updateHistory(j);
                        txtFloor.setText(String.valueOf(j));
                        cekPintu();
                    }
                }, 5000);
            }
        } else {
            for (int i = eML.floor; i >= mDB.getHistorySingle(); i--) {
                eML.actualFloor = i;
                final int j = i;
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDB.updateHistory(j);
                        txtFloor.setText(String.valueOf(j));
                        cekPintu();
                    }
                }, 5000);
            }
        }
        //}


        cekFloor();
    }


    public void tutupPintu(){
        int positionRight = pintuKanan.getLeft();
        int positionLeft = pintuKiri.getLeft();


        while (positionRight >= pintuKanan.getLeft()) {
            pintuKanan.animate().x(positionRight).setDuration(5000).start();
            positionRight--;
        }

        while (positionLeft <= pintuKiri.getLeft()) {
            pintuKiri.animate().x(positionLeft).setDuration(5000).start();
            positionLeft++;
        }

    }

    public void hideTombolDalam(){
        btn1.setEnabled(false);
        btn1.getBackground().setAlpha(0);
        btn1.setAlpha(0);

        btn2.setEnabled(false);
        btn2.getBackground().setAlpha(0);
        btn2.setAlpha(0);

        btn3.setEnabled(false);
        btn3.getBackground().setAlpha(0);
        btn3.setAlpha(0);

        btn4.setEnabled(false);
        btn4.getBackground().setAlpha(0);
        btn4.setAlpha(0);

        btn5.setEnabled(false);
        btn5.getBackground().setAlpha(0);
        btn5.setAlpha(0);

        btn6.setEnabled(false);
        btn6.getBackground().setAlpha(0);
        btn6.setAlpha(0);

        btnOpen.setEnabled(false);
        btnOpen.getBackground().setAlpha(0);
        btnOpen.setAlpha(0);

        btnClose.setEnabled(false);
        btnClose.getBackground().setAlpha(0);
        btnClose.setAlpha(0);
    }

    public void openTombolDalam(){
        btn1.setEnabled(true);
        btn1.getBackground().setAlpha(255);
        btn1.setAlpha(1);

        btn2.setEnabled(true);
        btn2.getBackground().setAlpha(255);
        btn2.setAlpha(1);

        btn3.setEnabled(true);
        btn3.getBackground().setAlpha(255);
        btn3.setAlpha(1);

        btn4.setEnabled(true);
        btn4.getBackground().setAlpha(255);
        btn4.setAlpha(1);

        btn5.setEnabled(true);
        btn5.getBackground().setAlpha(255);
        btn5.setAlpha(1);

        btn6.setEnabled(true);
        btn6.getBackground().setAlpha(255);
        btn6.setAlpha(1);

        btnOpen.setEnabled(true);
        btnOpen.getBackground().setAlpha(255);
        btnOpen.setAlpha(1);

        btnClose.setEnabled(true);
        btnClose.getBackground().setAlpha(255);
        btnClose.setAlpha(1);
    }

    public void hideTombolUp(){
        btnUp.setEnabled(false);
        btnUp.getBackground().setAlpha(0);
        btnUp.setImageAlpha(0);
    }

    public void hideTombolDown(){
        btnDown.setEnabled(false);
        btnDown.getBackground().setAlpha(0);
        btnDown.setImageAlpha(0);
    }

    public void openTombolUp(){
        btnUp.setEnabled(true);
        btnUp.getBackground().setAlpha(255);
        btnUp.setImageAlpha(255);
    }

    public void openTombolDown(){
        btnDown.setEnabled(true);
        btnDown.getBackground().setAlpha(255);
        btnDown.setImageAlpha(255);
    }

    public void cekFloor(){
        if(eML.floor == 1){
            openTombolUp();
            hideTombolDown();
        }else if(eML.floor == 6){
            openTombolDown();
            hideTombolUp();
        } else {
            openTombolUp();
            openTombolDown();
        }
    }

    public void cekPintu(){
        if(eML.kondisiPintu == false && eML.floor==eML.actualFloor){
            bukaPintu();
            eML.kondisiPintu = true;

            openTombolDalam();

            hideTombolUp();
            hideTombolDown();
        }
    }

}
