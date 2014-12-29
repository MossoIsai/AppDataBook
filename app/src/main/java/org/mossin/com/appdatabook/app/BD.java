package org.mossin.com.appdatabook.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class BD extends ActionBarActivity {
Connection conex = null;
Button btn1;
EditText titulo,autor,descripcion,precioVenta,precioCompra;
Statement insercciones;
String url= "jdbc:postgresql://172.16.22.222:5432/bd10090255V2";
String query= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_bd);
        //Aqui empieza mi codigo
        btn1 = (Button)findViewById(R.id.button);
        titulo =(EditText)findViewById(R.id.editText2);
        autor =(EditText)findViewById(R.id.editText3);
        descripcion =(EditText)findViewById(R.id.editText4);
        precioVenta =(EditText)findViewById(R.id.editText5);
        precioCompra =(EditText)findViewById(R.id.editText6);

        Resources res = getResources();
        TabHost tablita = (TabHost)findViewById(android.R.id.tabhost);
        tablita.setup();

        TabHost.TabSpec spec;
        //Primer pestaña

        spec = tablita.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("",res.getDrawable(R.drawable.dos));
        tablita.addTab(spec);
        //Tabla 2
        spec = tablita.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("", res.getDrawable(R.drawable.uno));
        tablita.addTab(spec);
         //Tabla 3
        spec = tablita.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("",res.getDrawable(R.drawable.tres));
        tablita.addTab(spec);

        //
        tablita.setCurrentTab(0);

        btn1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titulo.getText().toString();
                String autorC = autor.getText().toString();
                String descrip = descripcion.getText().toString();
                String pv = precioVenta.getText().toString();
                String pc = precioCompra.getText().toString();

                if(title.equals("")){
                    Toast.makeText(getApplicationContext(),"Ingresa un Titulo",Toast.LENGTH_SHORT).show();
                }else if(autorC.equals("")){
                    Toast.makeText(getApplicationContext(),"Ingresa un Autor",Toast.LENGTH_SHORT).show();
                }else if (descrip.equals("")){
                    Toast.makeText(getApplicationContext(),"Ingresa una Descripcion",Toast.LENGTH_SHORT).show();
                }else if(pv.equals("")){
                    Toast.makeText(getApplicationContext(),"Ingresa un Precio de Venta",Toast.LENGTH_SHORT).show();
                }else if(pc.equals("")){
                    Toast.makeText(getApplicationContext(),"Ingresa un Precio de Compra",Toast.LENGTH_SHORT).show();
                }else{

                    try {
                        Class.forName("org.postgresql.Driver");
                        conex = DriverManager.getConnection(url,"android","android");
                        insercciones = conex.createStatement();
                query = "INSERT INTO bd10090255V2 VALUES(' "+title+" ',' "+autorC+" ',' "+descrip+" ',"+
                  pv+","+pc+")";
                   insercciones.executeUpdate(query);
                        Toast.makeText(getApplicationContext(),"Inserccion Exitosa",Toast.LENGTH_LONG).show();
                  //Cerramos el Stament y la conexion;
                        insercciones.close();
                        conex.close();

                    } catch (Exception e) {
                        String  mensaje= e.getMessage();
                       //Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
                        if(mensaje.indexOf("Something unusual")!= -1){
                            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();

                        }else if(mensaje.indexOf("Hostname and port")!= -1){
                            Toast.makeText(getApplicationContext()," No se puede acceder al Servidor",Toast.LENGTH_LONG).show();

                        }else if(mensaje.indexOf("Llave duplicada")!= -1){
                            //Por si acaso
                            Toast.makeText(getApplicationContext(),"Ese ID ya existe ",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Error no encontrado",Toast.LENGTH_LONG).show();
                        }

                    }


                }
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
