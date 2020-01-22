package Utils;

import com.google.gson.JsonObject;

public class AsyncGetter {
    //region Constructor
    public AsyncGetter(){

    }

    //endregion

    //region Private Members
    private static AsyncGetter single_instance = null;
    private JsonObject jsonObject;

    //endregion


    //region Public Methods

    public static AsyncGetter getInstance(){
        if (single_instance == null)
            single_instance = new AsyncGetter();

        return single_instance;
    }

    public void setJsonObject(JsonObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    //endregion
}
