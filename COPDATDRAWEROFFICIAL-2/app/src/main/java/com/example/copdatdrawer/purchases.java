package com.example.copdatdrawer;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class purchases extends Fragment {

    ListView purchasedList;
    //String queryResult;
    ArrayList<ArrayList<String>> queryFinalResult;
    boolean shouldDelete = false;
    ArrayList<CheckBox> boxes;
    purchases.CustomAdapter adapter = null;
    ArrayList<String> pos = new ArrayList<String>();
    DB db;

    public purchases() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_purchases, container, false);
        purchasedList = (ListView) v.findViewById(R.id.list);
        Button del = (Button) v.findViewById(R.id.delete);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!queryFinalResult.isEmpty()) {
                    if (!boxes.isEmpty()) {
                        for (int i = 0; i < boxes.size(); i++) {
                            if (boxes.get(i).isChecked()) {
                                pos.add(queryFinalResult.get(0).get(i));
                                shouldDelete = true;
                            }
                        }
                        db = new DB();
                        db.execute();

                    }
                }
            }
        });
        db = new DB();
        db.execute();
        return v;
    }

    public class DB extends AsyncTask<Void, Void, Void> {
        private static final String url= "jdbc:mysql://10.0.2.2/database";
        private static final String user = "root1";
        private static final String pass = "mad";

        @Override
        protected Void doInBackground(Void... voids) {
            String queryString;
            Connection con = null;
            try {
                if (shouldDelete) {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection(url, user, pass);
                    Statement st2 = con.createStatement();
                    for (int i = 0; i < pos.size(); i++) {
                        queryString = "delete from cart where idProductCart='" + pos.get(i) + "'";
                        st2.executeUpdate(queryString);
                    }
                    pos.clear();
                    con.close();
                }
                queryString = "select product.idProduct, product.Productname, product.Price, product.imagePath, cart.Countcart, product.Countpurchase from cart";
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                final ResultSet rs = st.executeQuery(queryString);
                ResultSetMetaData rsmd = rs.getMetaData();
                queryFinalResult = new ArrayList<ArrayList<String>>(rsmd.getColumnCount());
                //do some things with the data you've retrieved
                int i = 0;
                while (rs.next()) {
                    for (int col = 1; col <= rsmd.getColumnCount(); col++) {
                        if (i == 0) {
                            ArrayList<String> q = new ArrayList<String>();
                            q.add(rs.getString(col));
                            queryFinalResult.add(col - 1, q);
                        } else {
                            queryFinalResult.get(col - 1).add(rs.getString(col));
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

    }

    public class CustomAdapter {
    }
}