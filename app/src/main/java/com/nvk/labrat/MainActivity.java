package com.nvk.labrat;


import android.app.Dialog;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    selectedFragment = DilutionFragment.newInstance();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                   // mTextMessage.setText(R.string.title_dashboard);
                    selectedFragment = PCRFragment.newInstance();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    selectedFragment = FormulaeFragment.newInstance();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, DilutionFragment.newInstance());
        transaction.commit();
        }

    }
    @Override
    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Are you sure you want to exit?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        MainActivity.this.finish();
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
        FragmentManager manager = getSupportFragmentManager();
        if(manager.getBackStackEntryCount() > 0 ) {
            manager.popBackStack();
        } else {

            new FancyGifDialog.Builder(this)
                    .setTitle("Are you sure you want to exit?")
                    .setMessage("")
                    .setNegativeBtnText("Nope")
                    .setPositiveBtnBackground("#FF4081")
                    .setPositiveBtnText("Yup")
                    .setNegativeBtnBackground("#FFA9A7A8")
                    .setGifResource(R.drawable.exit)   //Pass your Gif here
                    .isCancellable(true)
                    .OnPositiveClicked(new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {
                            MainActivity.this.finish();
                        }
                    })
                    .OnNegativeClicked(new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {

                        }
                    })
                    .build();
            }

    }


    public void Calculate(View v){
        float solute, solvent, totVolume;
        TextView units, totalUnits, totalVolume;
        units =(TextView) findViewById(R.id.units);
        totalUnits =(TextView) findViewById(R.id.totalUnits);
        totalVolume =(TextView) findViewById(R.id.totalVolume);
        try {
            float u = Float.parseFloat(units.getText().toString());
            float tu = Float.parseFloat(totalUnits.getText().toString());
            float tv = Float.parseFloat(totalVolume.getText().toString());
            if (u < tu){
                solute = tv/tu * u;
                solvent = tv - solute;
                totVolume = tv;
    //            Toast.makeText(this, solute + " + " + solvent +" = "+ totVolume ,
    //                    Toast.LENGTH_LONG).show();
                ShowPopup(solute, solvent, totVolume);
            } else {
                Toasty.info(this, "Please recheck values", Toast.LENGTH_SHORT, true).show();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
//            Toast.makeText(this, "All fields must be completed!" ,
//                Toast.LENGTH_LONG).show();
            Toasty.error(this, "All fields must be completed", Toast.LENGTH_SHORT, true).show();
        }
    }

    public void CalculateMolarity(View v){
        TextView concentrationM, volumeM, mWeight;
        concentrationM =(TextView) findViewById(R.id.concentration);
        volumeM =(TextView) findViewById(R.id.volume);
        mWeight =(TextView) findViewById(R.id.molWeight);
        try {
            float x = Float.parseFloat(concentrationM.getText().toString());
            float y = Float.parseFloat(volumeM.getText().toString());
            float z = Float.parseFloat(mWeight.getText().toString());
            if (x*y*z > 0){
                float mass = x*y*z;
//                Toast.makeText(this, "Mass is: " + String.format("%.2f", mass) ,
//                        Toast.LENGTH_LONG).show();
                ShowPopupMolarity(mass);
            } else {
                Toasty.info(this, "Please recheck values", Toast.LENGTH_SHORT, true).show();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
//            Toast.makeText(this, "All fields must be completed!" ,
//                Toast.LENGTH_LONG).show();
            Toasty.error(this, "All fields must be completed", Toast.LENGTH_SHORT, true).show();
        }

    }



    public void CalculateReconstitution(View v){
        TextView concentration, volume, mass;
        concentration =(TextView) findViewById(R.id.concentration);
        volume =(TextView) findViewById(R.id.volume);
        mass =(TextView) findViewById(R.id.mass);
        try {
            if (concentration.getText().toString().matches("")&& volume.getText().toString().matches("") && mass.getText().toString().matches("")){
                Toasty.error(this, "Two of the three fields must be completed", Toast.LENGTH_SHORT, true).show();
            } else if (!concentration.getText().toString().matches("")&& !volume.getText().toString().matches("") && !mass.getText().toString().matches("")){
                Toasty.error(this, "Only two of the three fields must be completed", Toast.LENGTH_SHORT, true).show();
            } else if (concentration.getText().toString().matches("")){
                float y = Float.parseFloat(volume.getText().toString());
                float z = Float.parseFloat(mass.getText().toString());

                if (z/y > 0){
                    float temp = z/y;
                    ShowPopupReconstitution(temp, 1);
                } else {
                    Toasty.info(this, "Please recheck values", Toast.LENGTH_SHORT, true).show();
                }
            } else if (volume.getText().toString().matches("")){
                float x = Float.parseFloat(concentration.getText().toString());
                float z = Float.parseFloat(mass.getText().toString());

                if (z/x > 0){
                    float temp = z/x;
                    ShowPopupReconstitution(temp, 2);
                } else {
                    Toasty.info(this, "Please recheck values", Toast.LENGTH_SHORT, true).show();
                }


            } else if (mass.getText().toString().matches("")){
                float x = Float.parseFloat(concentration.getText().toString());
                float y = Float.parseFloat(volume.getText().toString());

                if (x * y > 0){
                    float temp = x*y;
                    ShowPopupReconstitution(temp,3);
                } else {
                    Toasty.info(this, "Please recheck values", Toast.LENGTH_SHORT, true).show();
                }
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
//            Toast.makeText(this, "All fields must be completed!" ,
//                Toast.LENGTH_LONG).show();
            Toasty.error(this, "All fields must be completed", Toast.LENGTH_SHORT, true).show();
        }

    }

    public void CalculateConcVol(View v){
        TextView concentration1 =(TextView) findViewById(R.id.concentration1);
        TextView volume1 =(TextView) findViewById(R.id.volume1);
        TextView concentration2 =(TextView) findViewById(R.id.concentration2);
        TextView volume2 =(TextView) findViewById(R.id.volume2);
        if (TextUtils.isEmpty(concentration2.getText().toString()) && TextUtils.isEmpty(volume2.getText().toString())){
            Toasty.error(this, "All fields except one between c2 or v2 must be completed", Toast.LENGTH_SHORT, true).show();
        }
        else {
            try {
                float x = Float.parseFloat(concentration1.getText().toString());
                float y = Float.parseFloat(volume1.getText().toString());
                float z, result = 0;
                if (!concentration2.getText().toString().trim().equals("") && !volume2.getText().toString().trim().equals("")){
                    Toasty.error(this, "All fields except one between c2 or v2 must be completed", Toast.LENGTH_SHORT, true).show();
                } else if (!concentration2.getText().toString().trim().equals("")){
                    z = Float.parseFloat(concentration2.getText().toString());
                    result = x * y / z;
                    ShowPopupCV(x, y, z, result);
                } else {
                    z = Float.parseFloat(volume2.getText().toString());
                    result = x * y / z;
                    ShowPopupCV(x, y, result, z);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
    //            Toast.makeText(this, "All fields must be completed!" ,
    //                Toast.LENGTH_LONG).show();
                Toasty.error(this, "All fields except one between c2 or v2 must be completed", Toast.LENGTH_SHORT, true).show();
            }
        }

    }

    public void CalculateConcentration(View v){
        TextView mass, volumeM, mWeight;
        mass =(TextView) findViewById(R.id.mass);
        volumeM =(TextView) findViewById(R.id.volume);
        mWeight =(TextView) findViewById(R.id.molWeight);
        try {
            float x = Float.parseFloat(mass.getText().toString());
            float y = Float.parseFloat(volumeM.getText().toString());
            float z = Float.parseFloat(mWeight.getText().toString());
            if (((x/y)*z) > 0){
                float concetration = (x/y)/z;
//                Toast.makeText(this, "Mass is: " + String.format("%.2f", mass) ,
//                        Toast.LENGTH_LONG).show();
                ShowPopupConcentration(concetration);
            } else {
                Toasty.info(this, "Please recheck values", Toast.LENGTH_SHORT, true).show();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
//            Toast.makeText(this, "All fields must be completed!" ,
//                Toast.LENGTH_LONG).show();
            Toasty.error(this, "All fields must be completed", Toast.LENGTH_SHORT, true).show();
        }

    }

    public void CalculatePCR(View v){
        TextView pcrVol,sampleVol,dntpConc,mgcl2Conc,primerConc, negSamples, samples, error, taqPoli, bsa;

        pcrVol = (TextView) findViewById(R.id.volPRC);
        sampleVol = (TextView) findViewById(R.id.volSampTemplate);
        dntpConc = (TextView) findViewById(R.id.dNTPConc);
        mgcl2Conc = (TextView) findViewById(R.id.mgCl2Conc);
        primerConc = (TextView) findViewById(R.id.primerConc);
        negSamples = (TextView) findViewById(R.id.negSamples);
        samples = (TextView) findViewById(R.id.samples);
        error = (TextView) findViewById(R.id.error);
        taqPoli = (TextView) findViewById(R.id.taq);
        bsa = (TextView) findViewById(R.id.bsa);


        ArrayList<Float> listValues = new ArrayList<Float>();

        try {
            listValues.add(Float.parseFloat(pcrVol.getText().toString()));
            listValues.add(Float.parseFloat(sampleVol.getText().toString()));
            listValues.add(Float.parseFloat(dntpConc.getText().toString()));
            listValues.add(Float.parseFloat(mgcl2Conc.getText().toString()));
            listValues.add(Float.parseFloat(primerConc.getText().toString()));
            listValues.add(Float.parseFloat(negSamples.getText().toString()));
            listValues.add(Float.parseFloat(samples.getText().toString()));
            listValues.add(Float.parseFloat(error.getText().toString()));
            listValues.add(Float.parseFloat(taqPoli.getText().toString()));
            listValues.add(Float.parseFloat(bsa.getText().toString()));

            float h2o,pcrBuffer,mgcl2,fPrimer,rPrimer,dntps,taqPoly,bsaSR,mixVolume;
            float h2oMM,pcrBufferMM,mgcl2MM,fPrimerMM,rPrimerMM,dntpsMM,taqPolyMM,bsaMM,mixVolumeMM;
            float template, finVolReact;
            float temp;

            temp = (listValues.get(5) + listValues.get(6)) * (100 + listValues.get(7)) / 100;
            pcrBuffer = listValues.get(0) /5;
            pcrBufferMM = temp * pcrBuffer;

            mgcl2 = listValues.get(0) * listValues.get(3) / 25;
            mgcl2MM = temp * mgcl2;

            fPrimer = listValues.get(0) * listValues.get(4) / 10 ;
            fPrimerMM = temp * fPrimer;

            rPrimer = listValues.get(0) * listValues.get(4) / 10 ;
            rPrimerMM = temp * rPrimer;

            dntps = listValues.get(0) * listValues.get(2) / (2.5f);
            dntpsMM = temp * dntps;

            taqPoly = listValues.get(8);
            taqPolyMM = temp * taqPoly;

            bsaSR = listValues.get(9);
            bsaMM = temp * bsaSR;


            h2o = (listValues.get(0) - (listValues.get(1) + pcrBuffer + mgcl2 + fPrimer + rPrimer + dntps + taqPoly + bsaSR));
            h2oMM = temp * h2o;

            mixVolume = h2o + pcrBuffer + mgcl2 + fPrimer + rPrimer + dntps + taqPoly + bsaSR;
            mixVolumeMM = h2oMM + pcrBufferMM + mgcl2MM + fPrimerMM + rPrimerMM + dntpsMM + taqPolyMM + bsaMM;

            template = listValues.get(1);
            finVolReact = mixVolume + template;

            String[][] DATA_TO_SHOW = { { "H2O", String.format("%.2f", h2o), String.format("%.2f", h2oMM)},
                    { "5x PCR Buffer Flexi Green", String.format("%.2f", pcrBuffer), String.format("%.2f", pcrBufferMM)},
                    { "MgCl2 25 mM", String.format("%.2f", mgcl2), String.format("%.2f", mgcl2MM)},
                    { "Forward Primer (10 pmoles/μl)", String.format("%.2f", fPrimer), String.format("%.2f", fPrimerMM)},
                    { "Reverse Primer (10 pmoles/μl)", String.format("%.2f", rPrimer), String.format("%.2f", rPrimerMM)},
                    { "dNTPs (2.5 mM)", String.format("%.2f", dntps), String.format("%.2f", dntpsMM)},
                    { "Taq Polymerase (5U/μl)", String.format("%.2f", taqPoly), String.format("%.2f", taqPolyMM)},
                    { "BSA (mg/ml)", String.format("%.2f", bsaSR), String.format("%.2f", bsaMM)},
                    { "Mix Volume", String.format("%.2f", mixVolume), String.format("%.2f", mixVolumeMM)},
                    { "Template", String.format("%.2f", template), ""},
                    { "Final Vol. of individual reaction", String.format("%.2f", finVolReact), ""}};



            Fragment frag = new PCRResultsFragment();
            Bundle arguments = new Bundle();
            arguments.putSerializable( "PCRData",DATA_TO_SHOW);
            frag.setArguments(arguments);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, frag);
            transaction.addToBackStack(null).commit();


        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toasty.error(this, "All fields must be completed", Toast.LENGTH_SHORT, true).show();
        }

    }


    public void ShowPopup(float solute, float solvent, float totVolume) {
        new FancyGifDialog.Builder(this)
                .setTitle("If you're not part of the solution, you're part of the precipitate!")
                .setMessage("Solute: " +  String.format("%.2f", solute) + "\n\n" + "Solvent: " +  String.format("%.2f", solvent) +  "\n\n" + "Solution: " +  String.format("%.2f", totVolume))
                .setNegativeBtnText("Rate it!")
                .setPositiveBtnBackground("#FF4081")
                .setPositiveBtnText("Close")
                .setNegativeBtnBackground("#FFA9A7A8")
                .setGifResource(R.drawable.lab)   //Pass your Gif here
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
//                        Toast.makeText(MainActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                    }
                })
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        rateApp();
                    }
                })
                .build();
    }

    public void ShowPopupMolarity(float mass) {
        new FancyGifDialog.Builder(this)
                .setTitle("If Avagadro calls tell him to leave his number!")
                .setMessage("The calculated mass is: " +  String.format("%.2f", mass) + "(mg)" )
                .setNegativeBtnText("Rate it!")
                .setPositiveBtnBackground("#FF4081")
                .setPositiveBtnText("Close")
                .setNegativeBtnBackground("#FFA9A7A8")
                .setGifResource(R.drawable.lab)   //Pass your Gif here
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
//                        Toast.makeText(MainActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                    }
                })
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        rateApp();
                    }
                })
                .build();
    }

    public void ShowPopupReconstitution(float temp, int i) {
        String value = "", unit = "";
        if (i == 1){value = "concetration"; unit = "mg/ml";}
        else if (i == 2){value = "volume";unit = "ml";}
        else if (i ==3){value = "mass";unit = "mg";}
        new FancyGifDialog.Builder(this)
                .setTitle("Why can you never trust atoms? They make up everything!")
                .setMessage("The calculated "+ value +" is: " +  String.format("%.2f", temp) + "("+unit +")" )
                .setNegativeBtnText("Rate it!")
                .setPositiveBtnBackground("#FF4081")
                .setPositiveBtnText("Close")
                .setNegativeBtnBackground("#FFA9A7A8")
                .setGifResource(R.drawable.lab)   //Pass your Gif here
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
//                        Toast.makeText(MainActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                    }
                })
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        rateApp();
                    }
                })
                .build();
    }

    public void ShowPopupConcentration(float concentration) {
        new FancyGifDialog.Builder(this)
                .setTitle("I had to make these bad chemistry jokes because all the good ones Argon.")
                .setMessage("The calculated concentration is: " +  String.format("%.2f", concentration) + "(mM)" )
                .setNegativeBtnText("Rate it!")
                .setPositiveBtnBackground("#FF4081")
                .setPositiveBtnText("Close")
                .setNegativeBtnBackground("#FFA9A7A8")
                .setGifResource(R.drawable.lab)   //Pass your Gif here
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
//                        Toast.makeText(MainActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                    }
                })
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        rateApp();
                    }
                })
                .build();
    }


    public void ShowPopupCV(float c1, float v1, float c2, float v2){
        new FancyGifDialog.Builder(this)
                .setTitle("I try to tell chemistry jokes but there is no reaction!")
                .setMessage("Conc (start): " +  String.format("%.2f", c1) + "\n\n" + "Vol (start): " +  String.format("%.2f", v1) +  "\n\n"
                        + "Conc (final): " +  String.format("%.2f", c2) + "\n\n" + "Vol (final): " +  String.format("%.2f", v2))
                .setNegativeBtnText("Rate it!")
                .setPositiveBtnBackground("#FF4081")
                .setPositiveBtnText("Close")
                .setNegativeBtnBackground("#FFA9A7A8")
                .setGifResource(R.drawable.lab)   //Pass your Gif here
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
//                        Toast.makeText(MainActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                    }
                })
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        rateApp();
                    }
                })
                .build();
    }

    /*
* Start with rating the app
* Determine if the Play Store is installed on the device
*
* */
    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

}
