package com.example.copdatdrawer;

import android.widget.ImageView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;


public class shop_kroger extends Fragment {
    ArrayList<Products> checkItem = new ArrayList();
    ArrayList <String> checkString = new ArrayList();
    ArrayList <CheckBox> CheckBoxes = new ArrayList();
    ArrayList <EditText> numberList = new ArrayList();
    DB db = null;
    View v= null;
    adddb addc =null;
    public shop_kroger() {
    }


    // public shop() {

    // }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        //return inflater.inflate(R.layout.fragment_shop, container, false);
        getActivity().setTitle("Kroger");

        v = inflater.inflate(R.layout.fragment_shopkroger, container, false);

        final FloatingActionButton addcart = v.findViewById(R.id.addCart);
        db =new DB();
        db.execute();
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addc =new adddb();
                addc.execute();
            }
        });
        return v;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        getActivity().getMenuInflater().inflate(R.menu.main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.goAmazon:
                // do stuff

                Fragment fragment = new shop_amazon();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                break;
            case R.id.goKroger:
                // do stuff
                Fragment fragment2 = new shop_kroger();
                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                fragmentManager2.beginTransaction().replace(R.id.content_frame, fragment2).addToBackStack(null).commit();
                break;
            case R.id.goWalmart:
                // do stuff
                Fragment fragment3 = new shop_walmart();
                FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                fragmentManager3.beginTransaction().replace(R.id.content_frame, fragment3).addToBackStack(null).commit();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public class DB extends AsyncTask<Void, Void, Void> {
        private static final String url= "jdbc:mysql://10.0.2.2/database";
        private static final String user = "root1";
        private static final String pass = "mad";
        @Override
        protected Void doInBackground(Void... voids) {
            checkItem.clear();
            LinearLayout x  = v.findViewById(R.id.itemListK);
            String queryString = "select * from Product where idVendor =2";
            try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                final ResultSet rs = st.executeQuery(queryString);
                ResultSetMetaData rsmd = rs.getMetaData();

                //do some things with the data you've retrieved
                while (rs.next()) {
                    Products tempPro = new Products();
                    tempPro.setId(rs.getInt(1));
                    tempPro.setVendor("Kroger");
                    tempPro.setName(rs.getString(3));
                    tempPro.setSize(rs.getInt(4));
                    tempPro.setColor(rs.getString(5));
                    tempPro.setPrice(rs.getInt(8));
                    checkItem.add(tempPro);
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
            //put the results into the TextView on the app screen
            LinearLayout x  = v.findViewById(R.id.itemListK);
            for (int i=0 ;i < checkItem.size(); i++){


                LinearLayout tempLin1  = new LinearLayout(getActivity());
                LinearLayout tempLin2 = new LinearLayout(getActivity());
                LinearLayout tempLin3 = new LinearLayout(getActivity());
                LinearLayout tempLinv = new LinearLayout(getActivity());
                tempLinv.setOrientation(LinearLayout.VERTICAL);
                tempLin3.setOrientation(LinearLayout.HORIZONTAL);
                tempLin1.setOrientation(LinearLayout.HORIZONTAL);
                tempLin2.setOrientation(LinearLayout.HORIZONTAL);
                CheckBox tempbox = new CheckBox(getActivity());
                tempbox.setText(checkItem.get(i).getName());
                tempbox.setContentDescription(checkItem.get(i).getId()+"");
                tempbox.setId(Integer.valueOf(checkItem.get(i).getId()));
                CheckBoxes.add(tempbox);
                ImageView tempIm = new ImageView(getActivity());
                tempIm.setBackgroundResource(R.drawable.bread);
                EditText number = new EditText(getActivity());
                number.setInputType(InputType.TYPE_CLASS_NUMBER);
                numberList.add (number);
                TextView info = new TextView(getActivity());
                info.setText("Vendor: "+checkItem.get(i).getVendor()+"   Price: "+checkItem.get(i).getPrice());
                tempLin3.addView(tempIm);
                //tempLin.addView(tempv);
                tempLin1.addView(tempbox);
                tempLin2.addView(info);
                tempLin1.addView(number);
                tempIm.getLayoutParams().height = 250;
                tempIm.getLayoutParams().width = 250;

                tempLinv.addView(tempLin1);
                tempLinv.addView(tempLin2);

                tempLin3.addView(tempLinv);
                tempIm.setRight(tempLinv.getId());
                x.addView(tempLin3);
            }

        }
        protected  void addToCart(){
            LinearLayout x  = v.findViewById(R.id.itemList);
            String result = "";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();

                for (int i = 0; i<CheckBoxes.size(); i++){
                    if (CheckBoxes.get(i).isChecked()){
                        String id = CheckBoxes.get(i).getContentDescription().toString();
                        int num = Integer.parseInt(numberList.get(i).getText().toString());
                        String queryString = "INSERT INTO cart Values (DEFAULT,"+id+","+1+","+num+")";

                        st.executeQuery(queryString);
                        CheckBoxes.get(i).setChecked(false);
                        TextView t = new TextView(getActivity());
                        t.setText("correct");
                        x.addView(t);

                    }

                }
                //do some things with the data you've retrieved


                con.close(); //close database connection
            } catch (Exception e) {
                e.printStackTrace();
                //put the error into the TextView on the app screen
                result = "Database connection failure\n" +  e.toString();
                TextView t = new TextView(getActivity());
                t.setText(result);
                x.addView(t);

            }

        }
    }
    private class adddb extends AsyncTask<Void, Void, Void> {
        //references: http://developer.android.com/reference/android/os/AsyncTask.html
        //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
        private static final String url= "jdbc:mysql://10.0.2.2/database";
        private static final String user = "root1";
        private static final String pass = "mad";

        protected Void doInBackground(Void... arg0)  {
            String results ="";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();

                for (int i = 0; i<CheckBoxes.size(); i++){
                    if (CheckBoxes.get(i).isChecked()){
                        String id = CheckBoxes.get(i).getContentDescription().toString();
                        //  LinearLayout x  = v.findViewById(R.id.itemList);
                        String getNumber   = "select * from Product where idProduct="+id;
                        ResultSet rs = st.executeQuery(getNumber);
                        int count=0;
                        while (rs.next()) {
                            count  = rs.getInt(4);
                        }
                        int num = Integer.parseInt(numberList.get(i).getText().toString());
                        if (num <=count ){
                            String queryString = "INSERT INTO cart Values (DEFAULT,"+id+","+1+","+num+")";
                            st.executeUpdate(queryString);
                        }
                        else {
                            int cartnum = count;
                            int nisnum = num -count;
                            String queryString = "INSERT INTO cart Values (DEFAULT,"+id+","+1+","+cartnum+")";
                            String queryString2= "INSERT INTO not_in_stock Values (DEFAULT,"+1+","+id+","+nisnum+")";
                            st.executeUpdate(queryString);
                            st.executeUpdate(queryString2);
                        }


                    }

                }
                //do some things with the data you've retrieved


                con.close(); //close database connection
            } catch (Exception e) {
                e.printStackTrace();
                //put the error into the TextView on the app screen
                results= "Database connection failure\n" +  e.toString();
                //TextView t = new TextView(getActivity());
                // t.setText(result);
                //  x.addView(t);

            }

            return null;
        }//end database connection via doInBackground

        //after processing is completed, post to the screen
        protected void onPostExecute(Void result) {
        }
    }//end getDataFromDatabase()


}



