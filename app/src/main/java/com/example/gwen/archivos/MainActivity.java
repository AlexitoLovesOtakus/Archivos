package com.example.gwen.archivos;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button EscrbirInterna;
    Button LeerInterna;
    Button EscribirExterna;
    Button LeerExterna;
    TextView imprimir;
    EditText caja;
    boolean sdDisponible=false;
    boolean sdAccesoEscritura=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EscrbirInterna = findViewById(R.id.btnEscribirInterna);
        LeerInterna = findViewById(R.id.btnLeerInterna);
        EscribirExterna = findViewById(R.id.btnEscribirExterna);
        LeerExterna = findViewById(R.id.btnLeerExterna);
        imprimir=findViewById(R.id.txtImprimir);
        caja=findViewById(R.id.editTextContenido);

        EscrbirInterna.setOnClickListener(this);
        LeerInterna.setOnClickListener(this);
        EscribirExterna.setOnClickListener(this);
        LeerExterna.setOnClickListener(this);



        String estado= Environment.getExternalStorageState();

        if (estado.equals(Environment.MEDIA_MOUNTED)){


            sdAccesoEscritura=true;
            sdDisponible=true;





        }else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){


            sdDisponible=true;
            sdAccesoEscritura=false;


        }else {


            sdAccesoEscritura=false;
            sdDisponible=false;
        }


    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case (R.id.btnEscribirInterna):


                try {

                    OutputStreamWriter fout= new OutputStreamWriter(openFileOutput("archivo.txt", Activity.MODE_PRIVATE));


                    String text=caja.getText().toString();
                    fout.write(text);
                    fout.flush();
                    fout.close();



                }catch (Exception ex){


                    Log.e("Archivos","Error al escribir archivo en memoria interna");


                }


                break;


            case (R.id.btnLeerInterna):

                try {

                    BufferedReader fin=new BufferedReader(new InputStreamReader((openFileInput("archivo.txt"))));
                    String texto=fin.readLine();
                    imprimir.setText(texto);
                    fin.close();


                }catch (Exception ex){


                    Log.e("Archivo","Error al leer archivo memoria interna");
                }

                break;

            case (R.id.btnEscribirExterna):

                if (sdDisponible==true && sdAccesoEscritura==true){

                    try {

                        File rutasd=Environment.getExternalStorageDirectory();
                        File file=new File(rutasd.getAbsolutePath(),"arhivosd.txt");

                        OutputStreamWriter fout=new OutputStreamWriter(new FileOutputStream(file));

                        String text=caja.getText().toString();
                        fout.write(text);
                        fout.close();

                    }catch (Exception ex){


                        Log.e("Archivo","Error al excribir archivo memoria sd");

                    }




                }

                break;

            case (R.id.btnLeerExterna):

                if (sdDisponible){



                    try {

                        File rutaSd=Environment.getExternalStorageDirectory();
                        File file=new File(rutaSd.getAbsolutePath(),"arhivosd.txt");

                        BufferedReader fin=new BufferedReader(new InputStreamReader(new FileInputStream(file)));

                        String texto=fin.readLine();
                        imprimir.setText(texto);
                        fin.close();

                    }catch (Exception ex){


                        Log.e("Archivo","Error al leer archivo memoria sd");

                    }



                }

                break;

                default:

                    break;

        }
    }
}
