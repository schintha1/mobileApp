package com.example.copdatdrawer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;


public class settings_Personal extends Fragment {
    boolean shouldSave=false;
    DB db;
    ArrayList<ArrayList<String>> queryFinalResult;
    EditText eFName;
    EditText eLastName;
    EditText eHomephone;
    EditText eAddress;
    EditText eCity;
    EditText eState;
    EditText eZipcode;
    int ID;
    int customerID;


    EditText eEmail;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        //   return inflater.inflate(R.layout.fragment_settings, container, false);
        // }




        View v = inflater.inflate(R.layout.fragment_settings_personal, container, false);



        v.findViewById(R.id.btn_Personal).setOnClickListener(mListener);

        v.findViewById(R.id.btn_Card).setOnClickListener(mListener);

        v.findViewById(R.id.btn_Preferences).setOnClickListener(mListener);
        eFName=v.findViewById(R.id.editFName);
        eAddress=v.findViewById(R.id.editAddress);
        eCity=v.findViewById(R.id.editCity);
        eLastName=v.findViewById(R.id.editLastName);
        eEmail=v.findViewById(R.id.editEMail);
        eHomephone=v.findViewById(R.id.editHomephone);
        eState=v.findViewById(R.id.editState);
        eZipcode=v.findViewById(R.id.editZipcode);
        db=new DB();
        db.execute();
        Button save= (Button) v.findViewById(R.id.btnpersonal);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shouldSave=true;
                customerID=Integer.parseInt(queryFinalResult.get(0).get(0));
                db=new DB();
                db.execute();
            }
        });
        return v;
    }



    private final View.OnClickListener mListener = new View.OnClickListener() {


        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_Personal:
                    // do stuff

                    Fragment fragment = new settings_Personal();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                    break;
                case R.id.btn_Card:
                    // do stuff
                    Fragment fragment2 = new settings_Card();
                    FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                    fragmentManager2.beginTransaction().replace(R.id.content_frame, fragment2).addToBackStack(null).commit();
                    break;
                case R.id.btn_Preferences:
                    // do stuff
                    Fragment fragment3 = new settings_Preferences();
                    FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                    fragmentManager3.beginTransaction().replace(R.id.content_frame, fragment3).addToBackStack(null).commit();
                    break;
            }
        }
    };


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        Button Save = (Button) view.findViewById(R.id.btnpersonal);
        getActivity().setTitle("User Settings");
    }

    public class DB extends AsyncTask<Void, Void, Void> {
        private static final String url= "jdbc:mysql://10.0.2.2/database";
        private static final String user = "root1";
        private static final String pass = "mad";



        @Override
        protected Void doInBackground(Void... voids) {
            String queryString;
            Connection con=null;
            try {
                if(shouldSave){
                    shouldSave=false;
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection(url, user, pass);
                    Statement st2 = con.createStatement();
                    queryString="UPDATE Customer SET idCustomer ='"+customerID+"', idUser ='"+ ID+"', Firstname = '"+eFName.getText().toString()+"', Lastname = '"+eLastName.getText().toString()+"', Cardnumber = NULL, Phonenumber = '"+eHomephone.getText().toString()+"', Address = '"+eAddress.getText().toString()+"', City = '"+eCity.getText().toString()+"', State = '"+eState.getText().toString()+"', Zipcode = '"+eZipcode.getText().toString()+"', Emailaddress = '"+eEmail.getText().toString()+"', Cvs= NULL, idPurchase = NULL, idNot_in_stock = NULL, Size = NULL, Color = NULL Where idCustomer="+customerID;
                    st2.executeUpdate(queryString);
                    con.close();
                }
                queryString="select idCustomer, Firstname, Lastname, Phonenumber, Address, City, State, Zipcode, Emailaddress from Customer where idUser="+ID;

                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                final ResultSet rs = st.executeQuery(queryString);
                ResultSetMetaData rsmd = rs.getMetaData();
                queryFinalResult=new ArrayList<ArrayList<String>>(rsmd.getColumnCount());
                //do some things with the data you've retrieved
                int i=0;
                while (rs.next()) {
                    for (int col = 1; col <= rsmd.getColumnCount(); col++) {
                        if(i==0) {
                            ArrayList<String> q= new ArrayList<String >();
                            q.add(rs.getString(col));
                            queryFinalResult.add(col-1, q);
                        }
                        else{
                            queryFinalResult.get(col-1).add(rs.getString(col));
                        }
                    }
                    i++;
                }


                con.close(); //close database connection
            } catch (Exception e) {
                e.printStackTrace();
                //put the error into the TextView on the app screen
                //  queryResult = "Database connection failure\n" +  e.toString();
            }

            return null;
        }
        protected void onPostExecute(Void result) {
            eFName.setText(queryFinalResult.get(1).get(0));
            eLastName.setText(queryFinalResult.get(2).get(0));
            eHomephone.setText(queryFinalResult.get(3).get(0));
            eAddress.setText(queryFinalResult.get(4).get(0));
            eCity.setText(queryFinalResult.get(5).get(0));
            eState.setText(queryFinalResult.get(6).get(0));
            eZipcode.setText(queryFinalResult.get(7).get(0));
            eEmail.setText(queryFinalResult.get(8).get(0));
        }

    }

}


