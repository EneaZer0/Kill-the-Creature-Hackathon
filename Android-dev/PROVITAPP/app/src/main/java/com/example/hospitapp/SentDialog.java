package com.example.hospitapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hospitapp.ui.ListSentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * Public class used to create the Dialog that allows the user to confirm an order has been dispatched.
 */
public class SentDialog  extends AppCompatDialogFragment{

    /**
     * Private field used to store the orders that have been manufactured in a recyclerView.
     */
    private RecyclerView recyclerView;
    /**
     * Private field used to store the orders that have been manufactured in an ArrayList.
     */
    private ArrayList<Order> listOfOrders;
    /**
     * RequestQueue used to make a call to the server to get and Update information.
     */
    private RequestQueue requestQueue;
    /**
     * Used to get the situation of the app to display the messages.
     */
    private Context mContext;
    /**
     * Button used to call to the server to update the information about a selected order, to confirm that the order has been sent to the hospital.
     */
    private Button markAsSentButton;
    /**
     * EditText used to store the input of the user, it should store the id of one of the completed orders.
     */
    private EditText idInput;
    /**
     * String used to store the id previously stored in the EditText.
     */
    private String inputIdReceived;

    /**
     * It creates the display of the pop-up (dialog)
     * Allowing the user to confirm that a manufactured order has been sent to the appropriate hospital.
     * @param savedInstanceState saved data about the current app status used to create the pop-up.
     * @return the pop-up (Dialog).
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_sent, null, false);
        mContext = this.getContext();
        listOfOrders = new ArrayList<>();

        markAsSentButton = view.findViewById(R.id.markAsSent);
        idInput = view.findViewById(R.id.idInputSent);
        markAsSent();

        recyclerView = view.findViewById(R.id.recyclerSent);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fillList();


        builder.setView(view)
                .setTitle("List of sent orders")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    /**
     * Private method used to configure the button Order Sent and to call the server with the necessary information to update the information of the database.
     */
    private void markAsSent() {
        markAsSentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputIdReceived = idInput.getText().toString();
                if (!inputIdReceived.equals("")) {
                    sendSentRequest(URLS.mark_sent_url);
                    fillList();
                }
            }
        });
    }

    /**
     * Private method to call the server to request a list of all the orders to fill the list of the needed orders for display.
     */
    private void fillList() {
        makeListRequest(URLS.display_connected_orders_url);
    }

    /**
     * method used to call to the server to get the list of all the completed orders present in the database, made by the given user.
     * @param URL given URL to make the server call.
     */
    private void makeListRequest (String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    ArrayList<Order> listOrders = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject order = array.getJSONObject(i);

                        listOrders.add(new Order(
                                order.getInt("id"),
                                order.getInt("id_objeto"),
                                order.getInt("cantidad"),
                                order.getInt("id_proveedor"),
                                order.getInt("id_hospital"),
                                order.getString("fecha"),
                                order.getString("direccion_envio"),
                                order.getString("nombre_objeto"),
                                order.getInt("enviado"),
                                order.getInt("recibido"),
                                order.getInt("completado")

                        ));

                    }

                    ArrayList<Order> onlyCompleted = new ArrayList<>();

                    for (Order order : listOrders) {
                        if (order.isCompleted() && !order.isReceived() && !order.isSent()) {
                            onlyCompleted.add(order);
                        }
                    }

                    ListSentAdapter adapter = new ListSentAdapter(onlyCompleted);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mContext != null) {
                    Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("usuario", LoginActivity.userName);
                return parameters;
            }
        };


        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    /**
     * method used to call to the server update one of the orders of the user to confirm that it has been sent.
     * @param URL given URL to make the server call.
     */
    private void sendSentRequest(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getBoolean("success")){
                        Toast.makeText(MainActivity.getContext(), "Success!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.getContext(), "Unable to find the order", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(MainActivity.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), "ERROR DE CONEXION", Toast.LENGTH_SHORT).show();
            }
        }) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("usuario", LoginActivity.userName);
                parameters.put("fecha_envio", getDate());
                parameters.put("id", inputIdReceived);
                return parameters;
            }
        };
        requestQueue=Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    /**
     * Private method used to get the date of the user's phone.
     * @return String that represents the date.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getDate () {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}

