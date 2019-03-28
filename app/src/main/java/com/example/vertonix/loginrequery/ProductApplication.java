package com.example.vertonix.loginrequery;

import android.app.Application;
    import android.content.Context;
    import android.os.StrictMode;
    import android.support.multidex.MultiDex;
    import android.util.Log;

    import com.example.vertonix.loginrequery.database.Models;
    import com.example.vertonix.loginrequery.database.UserDetailsEntity;

    import io.requery.Persistable;
    import io.requery.android.sqlite.DatabaseSource;
    import io.requery.reactivex.ReactiveEntityStore;
    import io.requery.reactivex.ReactiveSupport;
    import io.requery.sql.Configuration;
    import io.requery.sql.EntityDataStore;
    import io.requery.sql.TableCreationMode;

public class ProductApplication extends Application {
    private static final String TAG = "ProductApplication";

    private ReactiveEntityStore<Persistable> dataStore;
    public static String userId="";
    public static String firstName="";
    public static String lastName="";
    public static String email ="";
    public static String phone = "";
    public static String password="";
    public static String cPassword="";
    //    public static int target = 0;
//    public static int completedTarget = 0;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
      /*  DB_PATH = "/data/data/"
                + getApplicationContext().getPackageName()
                + "/databases/";    */
        StrictMode.enableDefaults();
        if(dataStore==null){
            dataStore=getData();
        }
        UserDetailsEntity entity=dataStore.select(UserDetailsEntity.class).get().firstOrNull();
        if(entity!=null){
            Log.i(TAG, "onCreate: in ProductApplcation Userid= "+entity.getUserId());
            userId=entity.getUserId();
            firstName=entity.getFirstName();
            lastName=entity.getLastName();
            email=entity.getEmail();

            password=entity.getPassword();
            cPassword=entity.getConfirmPassword();
       }
    }
    public ReactiveEntityStore<Persistable> getData(){
        if (dataStore == null) {
            // override onUpgrade to handle migrating to a new version
            DatabaseSource source = new DatabaseSource(this, Models.DEFAULT,1);//DB_PATH+DB_NAME, 3);
            if (BuildConfig.DEBUG) {
                // use this in development mode to drop and recreate the tables on every upgrade
                source.setTableCreationMode(TableCreationMode.CREATE_NOT_EXISTS);
            }
            Configuration configuration = source.getConfiguration();
            dataStore = ReactiveSupport.toReactiveStore(
                    new EntityDataStore<Persistable>(configuration)
            );
        }
        return dataStore;
    }

//    public static Handler UIHandler;

//    static
//    {
//        UIHandler = new Handler(Looper.getMainLooper());
//    }
//    public static void runOnUI(Runnable runnable) {
//        UIHandler.post(runnable);
//    }
}
