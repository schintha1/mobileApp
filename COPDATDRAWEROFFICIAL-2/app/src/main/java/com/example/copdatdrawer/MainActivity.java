package com.example.copdatdrawer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String uname;
    String pwd;
    int uid = 0;
    boolean isLoginCorrect;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);



        Button showhome = (Button) findViewById(R.id.btn_login);


        showhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this. home);
                // startActivity(intent);
                EditText username = findViewById(R.id.login);
                EditText password = findViewById(R.id.password);
                uname = username.getText().toString();
                pwd = password.getText().toString();
                DB db=new DB();
                db.execute();
               // int i =1000000000;
                        new CountDownTimer( 100000000,30000) {
                            public void onTick(long millisUntilFinished) {
                                DB2 db2 = new DB2();
                                db2.execute();
                            }

                            public void onFinish() {


                            }
                        }.start();
                    }


            });


        }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                fragment = new home();
                break;
            case R.id.nav_shop:
                fragment = new shop();
                break;
            case R.id.nav_cart:
                cart c= new cart();
                c.ID=Integer.parseInt(uname);
                fragment = c;
                break;
            case R.id.nav_nis:
                nis n= new nis();
                n.ID=Integer.parseInt(uname);
                fragment = new nis();
                break;
            case R.id.nav_purchases:
                fragment = new purchases();
                break;
            case R.id.nav_settings:
                settings s=new settings();
                s.ID=Integer.parseInt(uname);
                fragment = s;
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
//calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        return true;
    }

    public class DB extends AsyncTask<Void, Void, Void> {
        private static final String url = "jdbc:mysql://10.0.2.2/database";
        private static final String user = "root1";
        private static final String pass = "mad";
        boolean loginCorrect=false;

        @Override
        protected Void doInBackground(Void... voids) {
            String queryString;
            Connection con = null;
            try {
                queryString = "select Password from user where idUser= '"+uname+"'";
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                final ResultSet rs = st.executeQuery(queryString);
                ResultSetMetaData rsmd = rs.getMetaData();
                //do some things with the data you've retrieved
                int i = 0;
                while (rs.next()) {
                    String p=rs.getString(1);
                    if(!pwd.equals(rs.getString(1))){
                        loginCorrect = false;
                    }
                    else{
                        uid = Integer.parseInt(uname);
                        loginCorrect = true;
                    }
                }


                con.close(); //close database connection
            } catch (Exception e) {
                e.printStackTrace();
                //put the error into the TextView on the app screen
                //  queryResult = "Database connection failure\n" +  e.toString();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            isLoginCorrect=loginCorrect;
            if(isLoginCorrect){
                uid = Integer.parseInt(uname);
                setContentView(R.layout.activity_main);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(MainActivity.this);

                //add this line to display menu1 when the activity is loaded
                displaySelectedScreen(R.id.nav_home);
            }
            else{

            }
        }
    }
    public class DB2 extends AsyncTask<Void, Void, Void> {
        private static final String url = "jdbc:mysql://10.0.2.2/database";
        private static final String user = "root1";
        private static final String pass = "mad";
        boolean autopay=false;
        String result="";
        @Override
        protected Void doInBackground(Void... voids) {
            String queryString;
            Connection con = null;
            try {
                queryString = "select * from not_in_stock where idUser=" +uid;
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                final ResultSet rs = st.executeQuery(queryString);
                //do some things with the data you've retrieved
                while (rs.next()) {
                    int num = rs.getInt(6);
                    int pid = rs.getInt(5);
                    int nisid = rs.getInt(1);
                    int count = 0;
                    String name = "";
                    String queryString2 = "select * from Product where idProduct = "+pid;
                    Statement st2 = con.createStatement();
                    ResultSet rs2 = st2.executeQuery(queryString2);
                    while (rs2.next()) {
                        count = rs2.getInt(4);
                        name = rs2.getString(3);
                    }
                    if(count!=0){

                        int num2 =  count-num;
                        if (num2>=0) {
                            String updatePro = "UPDATE Product SET Countpurchase = "+num2+" WHERE idProduct = "+pid;
                            String updateNIS ="DELETE FROM  not_in_stock WHERE idNot_in_stock = "+nisid;
                            String updatePurchase = "INSERT INTO purchase VALUES (DEFAULT,"+uid+","+pid+","+num+")";
                            result  = name+" has back in stock, we auto purchasded "+ num +" for you!";
                            st2.executeUpdate(updatePro);
                            st2.executeUpdate(updateNIS);
                            st2.executeUpdate(updatePurchase);
                        }
                        else {
                            String updatePro = "UPDATE Product SET Countpurchase = "+0+" WHERE idProduct = "+pid;
                            num2 = num - count;
                            result =name+" has back in stock, we auto purchasded "+ num2 +" for you!";
                            String updateNIS ="UPDATE not_in_stock SET countnot_in_stock = "+ num2+" WHERE idNot_in_stock = "+nisid;
                            String updatePurchase = "INSERT INTO purchase VALUES (DEFAULT,"+uid+","+pid+","+num2+")";
                            st2.executeUpdate(updatePro);
                            st2.executeUpdate(updateNIS);
                            st2.executeUpdate(updatePurchase);
                        }
                       autopay= true;
                    }
                }



                con.close(); //close database connection
            } catch (Exception e) {
                e.printStackTrace();
                //put the error into the TextView on the app screen
                //  queryResult = "Database connection failure\n" +  e.toString();


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(result.equals("")==false) {
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(MainActivity.this);
                View Nview = getLayoutInflater().inflate(R.layout.autopaynot, null);
                TextView a = (TextView) Nview.findViewById(R.id.backitem);
                a.setText(result);
                mbuilder.setView(Nview);
                mbuilder.setPositiveButton("ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });
                AlertDialog dialog = mbuilder.create();
                dialog.show();
            }
        }
    }

}
