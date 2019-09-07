package com.example.copdatdrawer;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * A simple {@link Fragment} subclass.
 */
public class cart extends Fragment {
    int ID;
    ListView cartList;
    //String queryResult;
    ArrayList<ArrayList<String>> queryFinalResult;
    boolean shouldDelete=false;
    ArrayList<CheckBox> boxes;
    CustomAdapter adapter=null;
    ArrayList<String> pos=new ArrayList<String>();
    DB db;
    public cart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.cart_list_layout, container, false);
        cartList=(ListView) v.findViewById(R.id.list);
        Button del= (Button) v.findViewById(R.id.delete);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!queryFinalResult.isEmpty()){
                    if(!boxes.isEmpty()) {
                        for(int i=0; i<boxes.size(); i++) {
                            if(boxes.get(i).isChecked()) {
                                pos.add(queryFinalResult.get(0).get(i));
                                shouldDelete=true;
                            }
                        }
                        db=new DB();
                        db.execute();

                    }
                }
            }
        });
        db=new DB();
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
            Connection con=null;
            try {
                if(shouldDelete){
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection(url, user, pass);
                    Statement st2 = con.createStatement();
                    for(int i=0; i<pos.size(); i++) {
                        queryString="delete from cart where idCart='"+pos.get(i)+"'";
                        st2.executeUpdate(queryString);
                    }
                    pos.clear();
                    con.close();
                }
                queryString ="select cart.idCart, cart.idNot_in_stockcart, product.Productname, product.Price, product.pic, cart.Countcart from cart, product where cart.idUser='"+ID+"' && product.idProduct=cart.idProductcart order by cart.idNot_in_stockcart";
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
            boxes=new ArrayList<CheckBox>();
            adapter= new CustomAdapter();
            cartList.setAdapter(adapter);
        }

    }
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(queryFinalResult.isEmpty()){
                return -1;
            }
            else {
                return queryFinalResult.get(0).size();
            }
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.cartlistview,null);
            RelativeLayout layout=(RelativeLayout) view.findViewById(R.id.listColor);
            CheckBox checkBox=view.findViewById(R.id.checkBox);
            boxes.add(checkBox);
            ImageView image=view.findViewById(R.id.productImage);
            TextView name=view.findViewById(R.id.produtName);
            TextView price=view.findViewById(R.id.productPrice);
            EditText quantity=view.findViewById(R.id.qty);
            for(int j=1; j<queryFinalResult.size(); j++) {
                String x=queryFinalResult.get(j).get(i);
                switch (j){
                    case 1: if(x!=null){
                        layout.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    }
                    else{
                        //do Nothing
                    }
                        break;
                    case 2: name.setText(x);
                        break;
                    case 3: price.setText(x);
                        break;
                    case 4: if(x!=null){image.setImageResource(getResources().getIdentifier(x, "drawable", getActivity().getPackageName()));}
                        break;
                    case 5: if(Integer.parseInt(x)>0){quantity.setText(Integer.parseInt(x)+"");}
                }
            }
            return view;
        }
    }
}
