package com.matrawy.a7oda.gorcoo.Data;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.matrawy.a7oda.gorcoo.CallBack;
import com.matrawy.a7oda.gorcoo.Internet;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by 7oda on 6/24/2017.
 */

public class Data_Parsing {
    private RequestQueue queue;
    private Context m;
    //private Home_Fragment call;
    private ProgressDialog progress;
    private DB db;
    private String Sig;
    private SQLiteDatabase db_helepr;
    static String url = Internet.URLL;
    public int Operation_Num = 0;
    private CallBack call;
    private String Data_Version;
    private int count = 0;
    private String[] Directory = new String[]{"getallcustomers.php", "getallusers.php",
            "getallhistories.php", "getallitems.php", "getallmaintain.php",
            "getallreminder.php", "getallsold_items.php", "getlastitemid.php"
            , "getlasthistoryid.php", "getlastsolditemid.php", "QueryExec.php", "getversions.php"};

    public Data_Parsing(Context m, ProgressDialog progress) {
        url = Internet.URLL;
        this.m = m;
        this.progress = progress;
        db = new DB(m);
        db_helepr = db.getWritableDatabase();
        try {
            queue = Volley.newRequestQueue(m);
            Sig = hmacSha1("Auth", "key");
        } catch (Exception e) {
            Log.e("asas", e.toString());
        }
        //Sig = hash_hmac("md5","Welcome","MatCop");
    }

    public void trunclate() {
        db.onUpgrade(db_helepr, 1, 2);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private void Open_Connection() {
        db_helepr = db.getWritableDatabase();
    }

    private void Close_Connection() {
        db_helepr.close();
    }

    public boolean Get_Data() {
        //Log.e("Test", "Launching Gate_Data---> " + url + Directory[Operation_Num]);
        JSONObject jsonBody = new JSONObject();
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url + Directory[Operation_Num], jsonBody,
                new Response.Listener<JSONObject>() {
                    private int num = Operation_Num;

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            switch (num) {
                                case 0:
                                    db.Set_Customers(response, db_helepr);
                                    count++;
                                    call_back();
                                    break;
                                case 1:
                                    db.Set_Users(response, db_helepr);
                                    count++;
                                    call_back();
                                    break;
                                case 2:
                                    db.Set_History(response, db_helepr);
                                    count++;
                                    call_back();
                                    break;
                                case 3:
                                    db.Set_Item(response, db_helepr);
                                    count++;
                                    call_back();
                                    break;
                                case 4:
                                    db.Set_Maintain(response, db_helepr);
                                    count++;
                                    call_back();
                                    break;
                                case 5:
                                    db.Set_Remainder(response, db_helepr);
                                    count++;
                                    call_back();
                                    break;
                                case 6:
                                    db.Set_ItemSold(response, db_helepr);
                                    count++;
                                    call_back();
                                    break;
                                case 7: {
                                    checkID(response.getJSONArray("item").getJSONObject(0).getInt("item_id"));
                                }
                                break;
                                case 8: {
                                    checkID(response.getJSONArray("history_count").getJSONObject(0).getInt("history_id"));
                                }
                                break;
                                case 9: {
                                    checkID(response.getJSONArray("solditem_count").getJSONObject(0).getInt("solditem_id"));
                                }
                                break;
                                case 11: {
                                    Data_Version = response.getJSONArray("counters").getJSONObject(0).getString("id");
                                    if (call != null)
                                        call.callBackFunc(0);

                                }
                                default:
                                    break;
                            }
                        } catch (Exception e) {
                            Log.e("ONRESPONCE_CATCH", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Tocken", Sig);
                return params;
            }
        };
        queue.add(postRequest);
        return true;
    }

    public void call_back() {
        progress.setProgress(progress.getProgress() + 1 * 100 / 7);
        if (count == 7) {
            progress.dismiss();
            call.callBackFunc(1);
        }
    }

    public static String hmacSha1(String value, String key)
            throws UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeyException {
        String type = "HmacSHA1";
        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), type);
        Mac mac = Mac.getInstance(type);
        mac.init(secret);
        byte[] bytes = mac.doFinal(value.getBytes());
        return bytesToHex(bytes);
    }

    private final static char[] hexArray = "0123456789abcdef".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public void setOperation_Num(int operation_Num) {
        Operation_Num = operation_Num;
    }

    public void checkID(int id) {
        Log.e("CheckID", id + "");
        if (Operation_Num == 7) {

        }
    }

    public void Insert_Data(final String Query) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url + Directory[Operation_Num],
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        Log.e("Request",Query);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Query", Query);
                return params;
            }
        };
        queue.add(postRequest);
    }

    public String getData_Version() {
        return Data_Version;
    }

    public void setData_Version(String data_Version) {
        Data_Version = data_Version;
    }

    public ProgressDialog getProgress() {
        return progress;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public CallBack getCall() {
        return call;
    }

    public void setCall(CallBack call) {
        this.call = call;
    }
}
