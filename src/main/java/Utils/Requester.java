package Utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Requester {
    //region Constructor
    public Requester(Context mContext){
        mainCntext = mContext;
        mRequestQueue = Volley.newRequestQueue(mContext);
    }

    //endregion

    //region Private members
    Context mainCntext;
    RequestQueue mRequestQueue;
    JsonObject jsonResponse;

    //endregion

    //region Public Methods
    public void PostToLocation(final String url, final String token, final String radio, final String mcc, final String mnc, final String lac, final String cid, final String address) throws IOException, InterruptedException {
        Thread sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL urlToPost = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) urlToPost.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Authorization", "Bearer " + token);
                    connection.setRequestProperty("content-type", "application/json");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);

                    JsonObject cellObject = new JsonObject();
                    cellObject.addProperty("lac", lac);
                    cellObject.addProperty("cid", cid);

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("token", token);
                    jsonObject.addProperty("radio", radio);
                    jsonObject.addProperty("mcc", mcc);
                    jsonObject.addProperty("mnc", mnc);
                    jsonObject.add("cells", cellObject);
                    jsonObject.addProperty("address", address);



                    OutputStream outputStream = connection.getOutputStream();

                    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                    String jsonString = jsonObject.toString();
                    int indexStart = jsonString.indexOf("cells");
                    int indexEnd=0;
                    for(int i = indexStart; i<jsonString.length();i++) {
                        if (jsonString.charAt(i) == '}') {
                            indexEnd = i;
                            break;
                        }

                    }
                    indexEnd++;
                    jsonString = jsonString.substring(0,indexStart+7) + "[" + jsonString.substring(indexStart+7,indexEnd) + "]" + jsonString.substring(indexEnd);
                    Log.i("JSON", jsonObject.toString());
                    dataOutputStream.writeBytes(jsonString);

                    dataOutputStream.flush();
                    dataOutputStream.close();

                    Log.i("STATUS", String.valueOf(connection.getResponseCode()));
                    Log.i("MSG", connection.getResponseMessage());

                    InputStream inputStream = connection.getInputStream();

                    StringBuffer sb = new StringBuffer();
                    String resp;
                    try {
                        int chr;
                        while ((chr = inputStream.read()) != -1) {
                            sb.append((char) chr);
                        }
                        resp = sb.toString();
                    } finally {
                        inputStream.close();
                    }

                    JsonParser jsonParser = new JsonParser();
                    jsonResponse = (JsonObject) jsonParser.parse(resp);
                    AsyncGetter asyncGetter = AsyncGetter.getInstance();
                    asyncGetter.setJsonObject(jsonResponse);

                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sendThread.start();
        sendThread.join();
    }

   //endregion
}
