package com.example.alefmobitech.appinsights;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.jar.Manifest;




public class MainActivity extends AppCompatActivity {
    private static final int PERMS_REQUEST_CODE = 123;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AlefMobi";
    public static String output = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AlefMobi/";

    private RelativeLayout relativeLayout;


    Button add;
    public  TextView dis, status, dirpath;
    static Connection conn;
    static String uname; // use our server name
    static String pass; // password
    static String db;// db name with link
    public static TextView show;
    public ProgressBar pBar;
    public WebView wb;
    public TabHost tabHost;

    Button update, path_button, add_to_db;
    TextView user_name_view, password_view, path_viwe, db_name_view, port_number_view, server_name_view, path_edit;
    EditText user_name_edit, password_edit, db_name_edit, port_number_edit, server_name_edit ;

    MyDBhandler dBhandler;

    // public LinkedHashMap<String, String> record = new LinkedHashMap<>();
    public LinkedHashMap<String, LinkedHashMap<String, String>> AllRecords = new LinkedHashMap<>();

    // public String matchedword ;



    public Map<String, List<PacketGroupParent>> GroupParent = null;
    public Map<String, List<PacketStructure>> ProcessedFiles = null;
    private  List<String> list = new ArrayList<>();
    private  ArrayList<String> filesList = new ArrayList<String>();

    int count = 0;///Stop for print multipal time tosts
    private static final String TAG = "MyActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);
        LoginActivity loginActivity = new LoginActivity();
        final ProgressDialog dlg = new ProgressDialog(this);


        if (!isConnected(MainActivity.this))builder(MainActivity.this).show();
        else{
            setContentView(R.layout.activity_main);
        }


        relativeLayout = (RelativeLayout) findViewById(R.id.relative);
        add = (Button)findViewById(R.id. add );
        dis = (TextView)findViewById(R.id.show);
        status = (TextView) findViewById(R.id.status);
        dirpath = (TextView) findViewById(R.id.path);

        pBar = (ProgressBar) findViewById(R.id.progressBar);
        pBar.setVisibility(View.GONE);

        tabHost = (TabHost)findViewById(R.id.tabss);

        wb = (WebView) findViewById(R.id.deshviwe);

       // path_button = (Button)findViewById(R.id.button);
        update = (Button)findViewById(R.id.update);
        //unamesql = (TextView)findViewById(R.id.username);
        //pass_sql = (TextView)findViewById(R.id.password);
        //pathdes = (TextView)findViewById(R.id.pathdes);
        //uid = (EditText)findViewById(R.id.uid);
        //passsql = (EditText)findViewById(R.id.pass);

        update = (Button)findViewById(R.id.update);
        add_to_db = (Button)findViewById(R.id.addtodb);

      //  user_name_view = (TextView)findViewById(R.id.username);
       // password_view = (TextView)findViewById(R.id.password);
      //  path_viwe = (TextView)findViewById(R.id.path_viwe);
      //  db_name_view = (TextView)findViewById(R.id.dbname);
      //  port_number_view = (TextView)findViewById(R.id.portnumber);
        //server_name_view = (TextView)findViewById(R.id.servername);

        user_name_edit = (EditText)findViewById(R.id.uid);
        password_edit = (EditText)findViewById(R.id.pass);
        db_name_edit = (EditText)findViewById(R.id.db_name);
        port_number_edit = (EditText)findViewById(R.id.port_number);
        server_name_edit = (EditText) findViewById(R.id.server_name);
        path_edit = (TextView)findViewById(R.id.path_edit);

        dBhandler = new MyDBhandler(this , null, null, 1);

        tabHost.setup();

        //wb.loadUrl("https://app.powerbi.com/view?r=eyJrIjoiMTJjYTliNGUtNGZlZC00ZDJjLTk3MDctMTI5MDQ3MGRlZjJiIiwidCI6Ijg4MTg1OTgyLTY1NDQtNDZhYy1hY2RhLWNiZGFiNWVmOTMyMCIsImMiOjEwfQ%3D%3D");
        //wb.loadUrl("file:///android_asset/js/HTML/graph.php");
         wb.loadUrl("http://182.75.54.50/dashboard/Dashboard/index.php");
        // wb.getVisibility();
        wb.getCertificate();
        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wb.getSettings().setDomStorageEnabled(true);
        wb.setWebViewClient(new WebViewClient());
        //webSettings.getAllowUniversalAccessFromFileURLs();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(true);

        //
        // webSettings.setDisplayZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUserAgentString("Android");


        wb.getSettings().setDomStorageEnabled(true);
        wb.setWebViewClient(new WebViewClient());
        wb.setWebChromeClient(new WebChromeClient(){
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });






        TabHost.TabSpec spec = tabHost.newTabSpec("upload");
        spec.setContent(R.id.upload);
        spec.setIndicator("Upload");
        tabHost.addTab(spec);


        spec = tabHost.newTabSpec("DashBoard");
        spec.setContent(R.id.DashBoard);
        spec.setIndicator("Dashboard");
        tabHost.addTab(spec);



        spec = tabHost.newTabSpec("Settings");
        spec.setContent(R.id.Cofig);
        spec.setIndicator("Settings");
        tabHost.addTab(spec);
///////////////////////////Get Text into Edittexts All///////////////////////////////////////
        add_to_db.setEnabled(false);
        server_name_edit.setText(String.valueOf(viewData(String.valueOf("ServerName"))));
        user_name_edit.setText(String.valueOf(viewData(String.valueOf("UserName"))));
        password_edit.setText(String.valueOf(viewData(String.valueOf("Password"))));
        db_name_edit.setText(String.valueOf(viewData(String.valueOf("DbName"))));
        port_number_edit.setText(String.valueOf(viewData(String.valueOf("PortNumber"))));
        path_edit.setText(String.valueOf(viewData(String.valueOf("Path"))));
        count =0 ;

///////////////////////////Get Text into Edittexts All ENDDDDDDD///////////////////////////////////////




        add.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                dirpath.setText(String.valueOf(path_edit.getText()));

                if (isFileFound()){

                    add.setEnabled(false);
                    // pBar.setVisibility(View.VISIBLE);
                    new creste().execute();
                    // pBar.setMax(150);
                    status.setText(String.valueOf("CSV file Creating "));

                    // Intent intent = getIntent();
                    // finish();
                    //startActivity(intent);
                    status.setText(String.valueOf("CSV file Crated"));

                    System.out.println("------------------------------DB UPLOADER IS ON--------------------------");

                    status.setText(String.valueOf("Uploading Files"));
                    new Mytask().execute();// here I am calling constructor of my Mytask
                    //  pBar.setVisibility(View.GONE);

                    // dlg.hide();
                    //  dlg.dismiss();
                 }
                else{
                    Snackbar snackbar = Snackbar.make(relativeLayout, "File Not Found at Directory Path", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }



            }
        });



      /*  path_button.setOnClickListener(new View.OnClickListener() {

            private String m_chosenDir = "";
            private boolean m_newFolderEnabled = true;

            @Override
            public void onClick(View v) {

                // Create DirectoryChooserDialog and register a callback
                DirectoryChooserDialog directoryChooserDialog =
                        new DirectoryChooserDialog(MainActivity.this,
                                new DirectoryChooserDialog.ChosenDirectoryListener()
                                {
                                    @Override
                                    public void onChosenDir(String chosenDir)
                                    {
                                        m_chosenDir = chosenDir;
                                        Toast.makeText(
                                                MainActivity.this, "Chosen directory: " +
                                                        chosenDir, Toast.LENGTH_LONG).show();
                                        path_edit.setText(String.valueOf(chosenDir));
                                    }
                                });
                // pathdes.setText(String.valueOf(chosenDir));
                // Toggle new folder button enabling
                directoryChooserDialog.setNewFolderEnabled(m_newFolderEnabled);
                // Load directory chooser dialog for initial 'm_chosenDir' directory.
                // The registered callback will be called upon final directory selection.
                directoryChooserDialog.chooseDirectory(m_chosenDir);
                m_newFolderEnabled = ! m_newFolderEnabled;

            }


        });
        */


        //////////-------------//////////////////////

        uname = String.valueOf(user_name_edit.getText()); // use our server name
        pass=String.valueOf(password_edit.getText()); // password
      //  db="jdbc:mysql://123.63.253.140:3306/".concat(String.valueOf(db_name_edit.getText()).concat( "?user=".concat( uname)));// db name with link
        db = "jdbc:mysql://"+String.valueOf(server_name_edit.getText())+":"+String.valueOf(port_number_edit.getText())+"/"+String.valueOf(db_name_edit.getText())+"?user="+uname;
       // String db1="jdbc:mysql://"+String.valueOf(server_name_edit.getText()).concat(":".concat(String.valueOf(port_number_edit.getText()).concat("/").concat(String.valueOf(db_name_edit.getText()).concat( "?user=".concat( uname)))));
      /////////////-----GETING SERVER INFO FROM SETTING TAB----/////////////




    }











     public void showdir(View v){
        //LoginActivity loginActivity = new LoginActivity();

         if (hasPermission() && hasPermission_write()){

         }else{
             requestPerms();
             requestPerms_write();
         }



         final String[] m_chosenDir = {""};
        boolean m_newFolderEnabled = true;

        // Create DirectoryChooserDialog and register a callback
        DirectoryChooserDialog directoryChooserDialog =
                new DirectoryChooserDialog(MainActivity.this,
                        new DirectoryChooserDialog.ChosenDirectoryListener()
                        {
                            @Override
                            public void onChosenDir(String chosenDir)
                            {
                                m_chosenDir[0] = chosenDir;
                                Toast.makeText(
                                        MainActivity.this, "Chosen directory: " +
                                                chosenDir, Toast.LENGTH_SHORT).show();
                                path_edit.setText(String.valueOf(chosenDir));
                            }
                        });
        // pathdes.setText(String.valueOf(chosenDir));
        // Toggle new folder button enabling
        directoryChooserDialog.setNewFolderEnabled(m_newFolderEnabled);
        // Load directory chooser dialog for initial 'm_chosenDir' directory.
        // The registered callback will be called upon final directory selection.
        directoryChooserDialog.chooseDirectory(m_chosenDir[0]);
        m_newFolderEnabled = ! m_newFolderEnabled;

    }


    public boolean isFileFound(){
        //boolean flag = false;
        creste prcessobj = new creste();
        File dir = new File(String.valueOf(path_edit.getText()));
        List <String> dirlist = prcessobj.subdir(dir);

        if (dirlist.size() == 0 && dirlist.isEmpty()){

            return false;
        }else {
            return true;
        }




    }




        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle presses on the action bar items
            switch (item.getItemId()) {
                case R.id.logout:
                    //logout code
                    finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        //rest of app


    private boolean hasPermission(){
        int res = 0;

        String[] permissions = new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE};

        for (String perms:permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }

    private void requestPerms(){
        String[] permission = new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permission, PERMS_REQUEST_CODE);
        }
    }


    private boolean hasPermission_write(){
        int res = 0;

        String[] permissions = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

        for (String perms:permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }

    private void requestPerms_write(){
        String[] permission = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permission, PERMS_REQUEST_CODE);
        }
    }




    ///////////////////////////////////////ADD_DATA_TO_SQLITE_DB_User////////////////////////////////////////////
    public void addtodb(View view){


        adduser(String.valueOf("ServerName"), String.valueOf(server_name_edit.getText()));
        adduser(String.valueOf("UserName"), String.valueOf(user_name_edit.getText()));
        adduser(String.valueOf("Password"), String.valueOf(password_edit.getText()));
        adduser(String.valueOf("DbName"), String.valueOf(db_name_edit.getText()));
        adduser(String.valueOf("PortNumber"), String.valueOf(port_number_edit.getText()));
        adduser(String.valueOf("Path"), String.valueOf(path_edit.getText()));

        count = 0;
        update.setVisibility(View.VISIBLE);
        add_to_db.setVisibility(View.GONE);
    }

//


    public void adduser(String key_value, String value)
    {
        UserData userData = new UserData(key_value, value);

        boolean isInserted = dBhandler.addData(userData);

        if (isInserted ){
            if (count==0){
                Toast.makeText(MainActivity.this, "Setting Saved", Toast.LENGTH_SHORT).show();
                count++;
            }

        }

        add.setEnabled(true);

        //printDatabase(key_value);
    }
///////////////////////////////////////ADD_DATA_TO_SQLITE_DB_User END////////////////////////////////////////////

    ///////////////////////////////////////UPDATE_DATA_TO_SQLITE_DB_User////////////////////////////////////////////
    public void updatetodb(View view){

        updatetodb(String.valueOf("ServerName"), String.valueOf(server_name_edit.getText()));
        updatetodb(String.valueOf("UserName"), String.valueOf(user_name_edit.getText()));
        updatetodb(String.valueOf("Password"), String.valueOf(password_edit.getText()));
        updatetodb(String.valueOf("DbName"), String.valueOf(db_name_edit.getText()));
        updatetodb(String.valueOf("PortNumber"), String.valueOf(port_number_edit.getText()));
        updatetodb(String.valueOf("Path"), String.valueOf(path_edit.getText()));

        count = 0;

    }


    public void updatetodb(String key_value, String value){

        boolean isUpdate = dBhandler.updatetodb(key_value, value);
        if (isUpdate){
            if (count==0){
                Toast.makeText(MainActivity.this, "Update Done!", Toast.LENGTH_SHORT).show();
                count++;
            }

        }
        else{

            if (count == 0){
                Toast.makeText(MainActivity.this, "Update occer error!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, " And Fill Authentication Data", Toast.LENGTH_SHORT).show();
                count++;
            }

        }

    }
///////////////////////////////////////UPDATE_DATA_TO_SQLITE_DB_User_END////////////////////////////////////////////

///////////////////////////////////////GET_DATA_TO_SQLITE_DB_User////////////////////////////////////////////
    public String viewData(String key_value){

        StringBuffer buffer = new StringBuffer();



        String value = dBhandler.getAllData(key_value);

        //buffer.append(value.getString(value.getColumnIndex("value")));

        if (value != null) {

            add_to_db.setEnabled(false);
            add_to_db.setVisibility(View.INVISIBLE);
            update.setVisibility(View.VISIBLE);///

            return value;

        }
        else{

            if (count == 0){
                Toast.makeText(MainActivity.this, "Upload is Disable Settings are Missing", Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, " And Fill Authentication Data", Toast.LENGTH_SHORT).show();
                count++;
            }
            add_to_db.setEnabled(true);
            update.setVisibility(View.GONE);///
            add.setEnabled(false);
            return "";
        }

    }
///////////////////////////////////////GET_DATA_TO_SQLITE_DB_User_END////////////////////////////////////////////

////////////////////////////////checking Internet Status///////////////////////////////////////////////


    public boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            android.net.NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            //NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))return true;
            else return false;
        }
        else
            return false;

    }


    public AlertDialog.Builder builder (Context c){
        AlertDialog.Builder builder = new  AlertDialog.Builder(c);

        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to go in seeting and turn ON Wifi or Data Pack");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        return builder;
    }


////////////////////////////////checking Internet Status///////////////////////////////////////////////


    private  class creste extends AsyncTask<Void, Void, Void>
    {
        boolean flag = false;
        @Override
        protected void onPreExecute() {
            dirpath.setText(String.valueOf(path_edit.getText()));
            pBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

        try {

            ProcessFiles();
                //
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return null;
        }


        public void disaply(final int n)
        {

            try{}catch (Exception e ){
                System.out.println(e);
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    //Toast.makeText(getApplicationContext(),n,Toast.LENGTH_SHORT).show();

                    dis.setText(String.valueOf(n));
                    dis.invalidate();
                }
            });


        }

        public void ProcessFiles() throws IOException, ParseException{


           // MainActivity obj = new MainActivity();
            try {
                //obj.ProcessFiles();


                File files=null;
                ArrayList<String> dir = new ArrayList<String>();
              //  obj = new MainActivity();

               if (hasPermission() && hasPermission_write()){
                   files = new File(String.valueOf(path_edit.getText()));
               }else{
                   requestPerms();
                   requestPerms_write();
               }

                int count = 0;

                for(File dirin : files.listFiles()){
                    //if (dirin.isDirectory()) {
                    String dirName = dirin.getAbsolutePath();
                    dirName= dirName.substring(dirName.lastIndexOf("/"));
                    dirName = dirName.replace("/", "");
                    list.clear();
                    // record.clear();
                    AllRecords.clear();
//                 if (!dirName.isEmpty()) {
//                    dir.add(dirName);
//               }
                    System.out.println(dirName);

                    list = subdir(dirin.getAbsoluteFile());



                    //for (int i = 0; i < list.size(); i++)
                    for (Object ls : list) {

                        LinkedHashMap<String, String> record = new LinkedHashMap<>();




                        String sb = ls.toString();
                        sb = sb.substring(sb.lastIndexOf("/"));
                        sb = sb.replace("/", "");
                        //System.out.println(sb+"     "+"                                    "+record.size()+"           "+count);//////////Geting Single entiry while reading
                        count++;
                        System.out.println("Number of file read  "+count);

                        try{
                            //Toast.makeText(getActivity(), sb,Toast.LENGTH_SHORT).show();

                            //show.setText(count);
                            //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                           // disaply(count);
                            dis.setText(String.valueOf(count));
                            //dispaly(count);
                            //Snackbar snackbar = Snackbar
                            //      .make(getApplicationContext(), "Welcome to AndroidHive", Snackbar.LENGTH_LONG);

                            //    snackbar.show();
                        }catch(Exception ex)
                        {

                            System.out.println(ex);
                        }

                        String dirpath = (String)ls;
                        String[] RunInfo = dirName.split("_");
                        String FileName = sb;
                        String CustomerName = "";
                        String Scenario = "";
                        String StepNo = "";
                        String InTransit = "";
                        String IsAlef = "";
                        String RunNo = "";

                        if (RunInfo.length == 6){
                            CustomerName = RunInfo[0];
                            Scenario = RunInfo[1];
                            StepNo = RunInfo[2];
                            InTransit = RunInfo[3];
                            IsAlef = RunInfo[4];
                            RunNo = RunInfo[5];
                        }
                        record.put("FileName", FileName);
                        record.put("CustomerName", CustomerName);
                        record.put("Scenario", Scenario);
                        record.put("StepNo", StepNo);
                        record.put("InTransit", InTransit);
                        record.put("IsAlef", IsAlef);
                        record.put("RunNo", RunNo);


                        //  ReadFile( dirpath,  record);
                        //       System.out.println("" +  ReadFile((File) list.get(i),  record));
                        //count++;

                        //  AllRecords.put(dirpath,record);

                        AllRecords.put(dirpath,ReadFile( dirpath,  record));


                    }

                    // System.out.println("All records"+AllRecords.size());
                    BulkUpload(AllRecords);
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        private boolean hasPermission(){
            int res = 0;

            String[] permissions = new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE};

            for (String perms:permissions){
                res = checkCallingOrSelfPermission(perms);
                if (!(res == PackageManager.PERMISSION_GRANTED)){
                    return false;
                }
            }
            return true;
        }

        private void requestPerms(){
            String[] permission = new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(permission, PERMS_REQUEST_CODE);
            }
        }


        private boolean hasPermission_write(){
            int res = 0;

            String[] permissions = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

            for (String perms:permissions){
                res = checkCallingOrSelfPermission(perms);
                if (!(res == PackageManager.PERMISSION_GRANTED)){
                    return false;
                }
            }
            return true;
        }

        private void requestPerms_write(){
            String[] permission = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(permission, PERMS_REQUEST_CODE);
            }
        }

        public  String interconnection() {
            String location = Environment.getExternalStorageDirectory().getAbsolutePath().concat(String.valueOf(path_edit.getText()));
            // String location = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Proxymon";
            //try {
            ///    Class.forName("com.mysql.jdbc.Driver");
            //    Connection conn = DriverManager.getConnection(db, uname, pass);
            //   Statement st = conn.createStatement();
            //    java.sql.ResultSet rs = (java.sql.ResultSet) st.executeQuery("select * from location");
            //    if (rs.first()) {
            //       location = Environment.getExternalStorageDirectory().getAbsolutePath() + rs.getString(1);

            //   }
            // } catch (Exception ex) {
            //     System.out.println("Throws " + ex);
            // }

            return location;
        }

        public ArrayList<String> subdir(File dir)
        {
            //ArrayList<File> list = new ArrayList<File>();
            int count = 0;
            File[] subdir = dir.listFiles();

            try{

                for (File file : subdir)
                {
                    if(file.isFile()&&file.getName().endsWith(".dat"))
                    {
                        count++;
                        filesList.add(file.getAbsolutePath());
                    }
                    else if (file.isDirectory())
                    {
                        subdir(file);
                    }

                }

            }catch (Exception ex){
                System.out.println(ex);

            }

            return (ArrayList<String>) filesList;
        }

        public LinkedHashMap<String, String> ReadFile(String FileName,  LinkedHashMap<String, String> record) throws FileNotFoundException, IOException
        {
            System.out.println(FileName);
            String line =null;
            boolean isSuccess = false;
            String firstField = "utc";
            FileReader fileReader = new FileReader(FileName);


            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //String temp = bufferedReader.readLine();
            ///  System.out.println(FileName);

            //  line = bufferedReader.readLine();
            int counter=0;
            // System.out.println(line);
            //ArrayList<CSVBean> csvBeanLst = new ArrayList<CSVBean>();


            //  String str = FileUtil         s.readFileToString(new File(FileName));
            while ((line = bufferedReader.readLine()) != null) {

                // matchedword = null;
                String[] val = line.split(":");

                // System.out.println(val);
                if (val.length == 2)
                {

                    // Incrementing counter for first field
                    String field = val[0].replace("#", "").trim();
                    if (field.equals(firstField) && record.size() > 0)
                    {
                        counter++;
                        // System.out.println(counter);
                    }

                    if (ValidateField(field))
                    {
                        if (!record.containsKey(field + "_" + counter))
                            record.put(field + "_" + counter, val[1].trim());
                    }
                }
                if (val.length == 3)
                {

                    String field = val[0].replace("#", "").trim();
                    if (ValidateField(field) && ValidateTriplet(field))
                    {
                        if (!record.containsKey(field + "_" + counter))
                        {
                            record.put(field + "_" + counter, val[1].trim());
                            record.put(field + "port" + "_" + counter, val[2].trim());
                        }
                    }
                }
                if (val.length == 4 && val[0].equals("Date") )
                {
                    if (!record.containsKey(val[0] + "_" + counter))
                    {
                        record.put(val[0] + "_" + counter, val[1] + "-" + val[2] + "-" + val[3]);
                    }
                }


                if (val.length == 1 && !isNullOrBlank(val[0].trim()))
                {
                    String matchFeild = CheckKeyword(val[0].trim());
                    if (matchFeild != null)
                    {
                        if (!record.containsKey("HttpResponse" + "_" + counter) && !record.containsKey("HttpRequest" + "_" + counter))
                        {
                            if (matchFeild.equals("HTTP/1.1 2"))
                                record.put("HttpResponse" + "_" + counter, val[0].trim());
                            else
                                record.put("HttpRequest" + "_" + counter, val[0].trim());
                        }
                    }
                }
            }


            // System.out.println(counter);
            return record;
        }

        public String CheckKeyword(String value)
        {
            String keywords = "GET,POST,DELETE,PUT,HTTP/1.1 2";
            String[] keywordArr = keywords.split(",");
            for (String item : keywordArr)
            {
                if (value.contains(item))
                {
                    return item;
                }
            }
            return null;
        }

        public boolean ValidateField(String value)
        {
            boolean isField = false;
            String fields = "utc,src,dst,len,User-Agent,Accept-Language,Content-Type,Content-Encoding,Host,Connection,Accept-Encoding,Access-Control-Allow-Origin,ETag,Pragma,Cache-Control,facebook-api-version,Expires,Content-Type,x-fb-trace-id,x-fb-rev,Vary,Content-Encoding,X-FB-Debug,Date,Connection,Content-Length,Date,Server,Transfer-Encoding";
            //string[] fieldArr = fields.Split(',');

            isField = fields.contains(value);
            return isField;
        }

        public boolean ValidateTriplet(String value)
        {
            boolean isValidTriplet = false;

            String fields = "src,dst";
            //String[] fieldArr = fields.Split(',');
            isValidTriplet = fields.contains(value);

            return isValidTriplet;
        }


        private boolean isNullOrBlank(String s)
        {
            return (s==null || s.trim().equals(""));
        }


        //////////////////////////////////////////////////////////


        public void BulkUpload(LinkedHashMap<String, LinkedHashMap<String, String>> FileRecords) throws ParseException, UnsupportedEncodingException, IOException
        {
            //System.out.println("BulkUpload called");

            ProcessedFiles = CreateProcessedFileData(FileRecords);
            UpdateRecordSets( ProcessedFiles);
            ConvertToCsv((LinkedHashMap<String, List<PacketStructure>>) ProcessedFiles);
            //    MessageBox.Show("File/s Created successfully");
        }

        public Map<String, List<PacketStructure>> CreateProcessedFileData(LinkedHashMap<String, LinkedHashMap<String, String>> FileRecords) throws ParseException
        {
            int requestCounter   = 0;
            int counter = 0;
            String firstField = "utc";
            ProcessedFiles = new LinkedHashMap<>();
            GroupParent = new LinkedHashMap<>();
            PacketStructure packet = null;
            List<PacketStructure> packetList = new ArrayList();
            for (Map.Entry<String, LinkedHashMap<String, String>> recordList : FileRecords.entrySet())
            {
                counter = 0;
                requestCounter = 0;




                for (Map.Entry<String, String> record : recordList.getValue().entrySet())
                {
                    if(counter > 6)
                    {
                        String key = record.getKey().substring(0, record.getKey().indexOf("_"));

                        if(requestCounter == 0 && key.equals(firstField))
                        {

                            if(packet != null && !packetList.contains(packet))

                                packetList.add(packet);

                            packet = new PacketStructure();

                            ColumnMap("FilePath", "Header", packet, recordList.getKey());

                            int internalCounter = 0;
                            for (Map.Entry<String, String> header : recordList.getValue().entrySet())
                            {
                                if(internalCounter <= 6)
                                {
                                    ColumnMap(header.getKey(), "Header", packet, header.getValue());
                                } else{
                                    break;
                                }
                                internalCounter++;
                            }
                            requestCounter = 1;
                        }
                        else if (requestCounter == 1 && key.equals(firstField))
                        {
                            requestCounter = 0;
                        }

                        if(packet != null)
                        {
                            if(requestCounter == 1)
                            {
                                ColumnMap(key, "Request", packet, record.getValue());

                            }

                            if(requestCounter == 0)
                            {
                                ColumnMap(key, "Response", packet, record.getValue());

                                if(key.equals(firstField))
                                {

                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS");
                                    Date Response = packet.ResponseTime;
                                    Date Request = packet.RequestTime;
                                    Float subtrck = Float.valueOf(packet.ResponseTime.getTime() - packet.RequestTime.getTime());
                                    packet.CResponseTime = String.valueOf(Float.valueOf(subtrck));
                                }
                            }
                        }
                    }

                    counter++;
                }
                if(packet != null)
                {
                    packetList.add(packet);
                }
                String runKey = String.format("%1$s_%2$s_%3$s_%4$s_%5$s_%6$s", packet.CustomerName, packet.Scenario, packet.StepNo, packet.InTransit, packet.IsAlef, packet.RunNo);
                String groupKey = String.format("%1$s_%2$s_%3$s_%4$s_%5$s", packet.CustomerName, packet.Scenario, packet.StepNo, packet.InTransit, packet.IsAlef);

                if(!ProcessedFiles.containsKey(runKey))
                {
//                ProcessedFiles.get(runKey).addAll(packetList);
//            }
//            else
//            {
                    ProcessedFiles.put(runKey, packetList);
                }


            }

            return ProcessedFiles;



        }


        public void ColumnMap(String ColumnName, String Type, PacketStructure PacketStructure, String value) throws ParseException

        {
        //DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = null;


        //  SimpleDateFormat dateFormatGmt = new SimpleDateFormat("d MM yyyy HH:mm:ss");
        //   dateFormatGmt.setTimeZone(TimeZone.getTimeZone(value));
            DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            Calendar calender=Calendar.getInstance();

        switch (Type + "_" + ColumnName)
        {

            case "Header_FilePath":
            {
                PacketStructure.FilePath = value;
                //PacketStructure.CreatedDate = df.format(d);
                //Date date = sdf.parse(LocalDate.now().toString());
                Calendar cal = Calendar.getInstance();
                PacketStructure.CreatedDate =sdf.format(new Date());
                //PacketStructure.CreatedDate = sdf.parse(sdf.format(date));
                break;
            }
            case "Header_FileName":
            {
                PacketStructure.FileName = value;
                break;
            }
            case "Header_CustomerName":
            {
                PacketStructure.CustomerName = value;
                break;
            }
            case "Header_Scenario":
            {
                PacketStructure.Scenario = value;
                break;
            }
            case "Header_StepNo":
            {
                PacketStructure.StepNo = value;
                break;
            }
            case "Header_InTransit":
            {
                PacketStructure.InTransit = value;
                break;
            }
            case "Header_IsAlef":
            {
                PacketStructure.IsAlef = value;
                break;
            }
            case "Header_RunNo":
            {
                PacketStructure.RunNo = value;
                PacketStructure.CUniqueId = PacketStructure.CustomerName + PacketStructure.Scenario + PacketStructure.StepNo + PacketStructure.InTransit + PacketStructure.IsAlef + PacketStructure.RunNo;
                break;
            }
            case "Request_utc":
            {   PacketStructure.RequestUTC = value;
                //PacketStructure.RequestTime = DateTimeOffset.FromUnixTimeMilliseconds(Int64.Parse(value, System.Globalization.NumberStyles.HexNumber)).DateTime;

                try{PacketStructure.RequestTime = getUTC(value);
                }
                catch(Exception e){System.out.println(e);}
                //PacketStructure.RequestTime = getUTC(value);
                //PacketStructure.CRunDate = PacketStructure.RequestTime.ToString("dd-MMM-yyyy");
                calender.setTime(getUTC(value));
                //PacketStructure.CRunDate = calender.
                PacketStructure.CRunDate = sdf.format(getUTC(value)).substring(0, 10);   //.substring(11, 13)
                //PacketStructure.CRunHr = PacketStructure.RequestTime.Hour.ToString();
                PacketStructure.CRunHr = sdf.format(getUTC(value)).substring(11, 13);
                break;
            }
            case "Request_src":
            {
                PacketStructure.RequestSource = value;
                break;
            }
            case "Request_srcport":
            {
                PacketStructure.RequestSourcePort = value;
                break;
            }
            case "Request_dst":
            {
                PacketStructure.RequestDestination = value;
                break;
            }
            case "Request_dstport":
            {
                PacketStructure.RequestDestinationPort = value;
                break;
            }

            case "Request_len":
            {
                //int res = 0;
                //int.TryParse(value, out res);
                int res = Integer.parseInt(value);
                PacketStructure.RequestLength = String.valueOf(res);
                //PacketStructure.RequestLength = res.toString();
                break;
            }
            case "Request_HttpRequest":
            {
                PacketStructure.RequestHttpRequest = value;
                break;
            }
            case "Request_User-Agent":
            {
                PacketStructure.RequestHttpUserAgent = value;
                break;
            }
            case "Request_Host":
            {
                PacketStructure.RequestHttpHost = value;
                break;
            }
            case "Request_Connection":
            {
                PacketStructure.RequestHttpConnection = value;
                break;
            }
            case "Request_Accept-Encoding":
            {
                PacketStructure.RequestAcceptEncoding = value;
                break;
            }
            case "Response_utc":
            {


                PacketStructure.ResponseUTC = value;
                //PacketStructure.ResponseTime = DateTimeOffset.FromUnixTimeMilliseconds(Int64.Parse(value, System.Globalization.NumberStyles.HexNumber)).DateTime;
                //DateFormat sdf = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");

                PacketStructure.ResponseTime = getUTC(value);
                //PacketStructure.ResponseTime = getUTC(value);
                break;}
            case "Response_src":
            {
                PacketStructure.ResponseSource = value;
                break;
            }
            case "Response_srcport":
            {
                PacketStructure.ResponseSourcePort = value;
                break;
            }
            case "Response_dst":
            {
                PacketStructure.ResponseDestination = value;
                break;
            }
            case "Response_dstport":
            {
                PacketStructure.ResponseDestinationPort = value;
                break;
            }
            case "Response_len":
            {
                //   int res = 0;
                // int.TryParse(value, out res);
                // PacketStructure.ResponseLength = res.ToString();
                int res = Integer.parseInt(value);
                PacketStructure.ResponseLength = String.valueOf(res);
                break;
            }

            case "Response_HttpResponse":
            {
                PacketStructure.ResponseHttpResponse = value;
                break;
            }
            case "Response_Content-Disposition":
            {
                PacketStructure.ResponseContentDisposition = value;
                break;
            }
            case "Response_Content-Type":
            {
                PacketStructure.ResponseContentType = value;
                break;
            }
            case "Response_Content-Length":
            {

                //int res = 0;
                //int.TryParse(value, out res);
                //PacketStructure.ResponseContentLength = res.ToString();

                int res = Integer.parseInt(value);
                PacketStructure.ResponseContentLength = String.valueOf(res);

                break;
            }
            case "Response_Content-Encoding":
            {
                PacketStructure.ResponseContentEncoding = value;
                break;
            }
            case "Response_Date":
            {

                PacketStructure.ResponseDate = value;
                break;
            }

            case "Response_Server":
            {
                PacketStructure.ResponseServer = value;
                break;
            }
            case "Response_Cache-Control":
            {
                PacketStructure.ResponseCacheControl = value;
                PacketStructure.CIsCached = CacheCheck(value);
                break;
            }
            case "Response_Connection":
            {
                PacketStructure.ResponseConnection = value;
                break;
            }
            case "Response_Transfer-Encoding":
            {
                PacketStructure.ResponseTransferEncoding = value;
                break;
            }

            default:
                break;
        }


    }
        public  String CacheCheck(String value)///////////////////////CHANGING VALE AS STRING
        {
            if (value.contains("no-cache") ||isNullOrBlank(value))
                return "false";

            return "true";
        }



        public  String CsvBlock(String value)
        {
            String tempStr;

            tempStr = "\"";
            if (!isNullOrBlank(value))
            {
                tempStr = tempStr + value.replace("\"", "").replace(",", ";");
            }
            tempStr = tempStr + "\",";

            return tempStr;


        }


        public Date getUTC(String value)
        {
            long utc2 = new BigInteger(value, 16).longValue();
            Calendar c=Calendar.getInstance();
            c.setTimeInMillis(utc2);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            //String dt = df.format(c.getTime());
            // System.out.println();

            return c.getTime();
        }



        public void UpdateRecordSets(Map<String, List<PacketStructure>> ProcessedFiles) throws ParseException {
            System.out.println("UpdateRecordSets called");

            for(Map.Entry<String, List<PacketStructure>> file : ProcessedFiles.entrySet())
            //for (int i = 0; i < ProcessedFiles.size(); i++)
            {
                if (file.getValue().size() > 0)
                {

                    List<PacketStructure> value = file.getValue();

                    ArrayList<Date> val1= new ArrayList<Date>();
                    for (int i = 0; i < value.size(); i++) {

                        try{
                            //String s = value.get(i).RequestTime;
                            val1.add(value.get(i).RequestTime);

                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();}
                    }



                    //Date min = file.getValue().retainAll(Collections.min(file, f));

//                    List list = Arrays.asList(file.getValue().get(0).RequestTime);
  //                  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:MM:ss");
                    //Date mintime = (Date) Collections.min(list);
                    Date mintime =  Collections.min(val1);
                    for (PacketStructure call : file.getValue())
                    {
                        if (call.ResponseTime != null)
                        {
                            // call.CElapsedTime=  call.RequestTime.Subtract(mintime).TotalSeconds.ToString();

                            // long subtrck =  call.RequestTime.getTime() - mintime.getTime();
                            //  call.CElapsedTime = Long.toString(subtrck);
                            Date Request = call.RequestTime;

                            //Float subtrck =  Float.valueOf(Request.getTime()- mintime.getTime());
                           // call.CElapsedTime = String.valueOf((subtrck)/1000);

                            double subtrck = (Request.getTime() - mintime.getTime())/1000.000;
                            //float subtrck= Reqcal.getTimeInMillis()- cal.getTimeInMillis();
                            call.CElapsedTime = String.valueOf(subtrck);

                            //long subtrck =  Request.getTime() - mintime.getTime();
                            //call.CElapsedTime = Long.toString(subtrck);

                        }
                    }
                }

            }
        }

        public void ConvertToCsv(LinkedHashMap<String, List<PacketStructure>> ProcessedFiles) throws FileNotFoundException, UnsupportedEncodingException, IOException
        {
            //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            // LocalDateTime now = LocalDateTime.now();
            Date date = null;
            Calendar cal = Calendar.getInstance();
            cal.setTime(cal.getTime());
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            StringBuilder csvFile = new StringBuilder();
            String ColumnNames = "FilePath,FileName,CustomerName,Scenario,StepNo,InTransit,IsAlef,RunNo,RequestUTC,RequestTime,RequestSource,RequestSourcePort,RequestDestination,RequestDestinationPort,RequestLength,RequestHttpRequest,RequestHttpUserAgent,RequestHttpHost,RequestHttpConnection,RequestAcceptEncoding,ResponseUTC,ResponseTime,ResponseSource,ResponseSourcePort,ResponseDestination,ResponseDestinationPort,ResponseLength,ResponseHttpResponse,ResponseContentDisposition,ResponseContentType,ResponseContentLength,ResponseContentEncoding,ResponseDate,ResponseServer,ResponseCacheControl,ResponseConnection,ResponseTransferEncoding,ElapsedTime,TotalResponseTime,IsCached,RunDate,RunHr,CreatedDate";
            String DbInputPath = output;

            for(Map.Entry<String, List<PacketStructure>> file : ProcessedFiles.entrySet())

            //for (int i = 0; i < ProcessedFiles.size(); i++)
            {
                if (file.getValue().size()>0) {

//           String filename = DbInputPath + "\\" + ProcessedFiles.get("CustomerName") + "_" + ProcessedFiles.get("Scenario") + "_" + ProcessedFiles.get("StepNo") + "_"
//                                         + ProcessedFiles.get("InTransit") + "_" + ProcessedFiles.get("IsAlef") + "_" + ProcessedFiles.get("RunNo") + "_" +
//                                         date.getDayOfMonth() + "-" + date.getMonth() + "-" + date.getYear() + ".csv";
//
                    String filename = DbInputPath + "" + file.getValue().get(0).CustomerName + "_" + file.getValue().get(0).Scenario + "_" + file.getValue().get(0).StepNo + "_" + file.getValue().get(0).InTransit + "_" + file.getValue().get(0).IsAlef + "_" + file.getValue().get(0).RunNo + "_" +cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.MONTH) + "-" +cal.get(Calendar.YEAR) + ".csv";

                    FileWriter fw = new FileWriter(filename);
                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write(ColumnNames);
                    csvFile.append(System.getProperty("line.separator"));

                    for (PacketStructure call : file.getValue()) {

                        if (call.ResponseTime != null)
                        {
                            csvFile.append(CsvBlock(call.FilePath));
                            csvFile.append(CsvBlock(call.FileName));
                            csvFile.append(CsvBlock(call.CustomerName));
                            csvFile.append(CsvBlock(call.Scenario));
                            csvFile.append(CsvBlock(call.StepNo));
                            csvFile.append(CsvBlock(call.InTransit));
                            csvFile.append(CsvBlock(call.IsAlef));
                            csvFile.append(CsvBlock(call.RunNo));
                            csvFile.append(CsvBlock(call.RequestUTC));
                            csvFile.append(CsvBlock(df.format((call.RequestTime))));
                            csvFile.append(CsvBlock(call.RequestSource));
                            csvFile.append(CsvBlock(call.RequestSourcePort));
                            csvFile.append(CsvBlock(call.RequestDestination));
                            csvFile.append(CsvBlock(call.RequestDestinationPort));
                            csvFile.append(CsvBlock(call.RequestLength));
                            csvFile.append(CsvBlock(call.RequestHttpRequest));
                            csvFile.append(CsvBlock(call.RequestHttpUserAgent));
                            csvFile.append(CsvBlock(call.RequestHttpHost));
                            csvFile.append(CsvBlock(call.RequestHttpConnection));
                            csvFile.append(CsvBlock(call.RequestAcceptEncoding));
                            csvFile.append(CsvBlock(call.ResponseUTC));
                            csvFile.append(CsvBlock(df.format((call.ResponseTime))));
                            csvFile.append(CsvBlock(call.ResponseSource));
                            csvFile.append(CsvBlock(call.ResponseSourcePort));
                            csvFile.append(CsvBlock(call.ResponseDestination));
                            csvFile.append(CsvBlock(call.ResponseDestinationPort));
                            csvFile.append(CsvBlock(call.ResponseLength));
                            csvFile.append(CsvBlock(call.ResponseHttpResponse));
                            csvFile.append(CsvBlock(call.ResponseContentDisposition));
                            csvFile.append(CsvBlock(call.ResponseContentType));
                            csvFile.append(CsvBlock(call.ResponseContentLength));
                            csvFile.append(CsvBlock(call.ResponseContentEncoding));
                            csvFile.append(CsvBlock(call.ResponseDate));
                            csvFile.append(CsvBlock(call.ResponseServer));
                            csvFile.append(CsvBlock(call.ResponseCacheControl));
                            csvFile.append(CsvBlock(call.ResponseConnection));
                            csvFile.append(CsvBlock(call.ResponseTransferEncoding));
                            //Elapsed Time Calculation
                            csvFile.append(CsvBlock(call.CElapsedTime));
                            csvFile.append(CsvBlock(call.CResponseTime));
                            csvFile.append(CsvBlock(call.CIsCached));
                            csvFile.append(CsvBlock(call.CRunDate));
                            csvFile.append(CsvBlock(call.CRunHr));
                            csvFile.append(CsvBlock(call.CreatedDate.toString()));
                            csvFile.append(System.getProperty("line.separator"));


                        }
                    }
                    System.out.println("CSv cal");
                    bw.write(csvFile.toString());
                    bw.flush();
                    bw.close();
                }
            }
        }






        protected void onPostExecute(Void result) {
          //  pBar.setVisibility(View.GONE);
        }
    }


    private class Mytask extends AsyncTask<Void, Void, Void>
    {

        int count = 1;


        protected void onPreExecute() {

//            pBar.setVisibility(View.VISIBLE);
        }

        ArrayList<CSVBean> csvBeanLst = new ArrayList<CSVBean>();



        @Override
        protected Void doInBackground(Void... arg0) {

            try {

                File currentDir = new File(path);
                displayDirectoryContents(currentDir);
                System.out.println("Data Upload");
            }
            catch(Exception e){
                System.out.println(e);
            }

            return null;
        }
        protected void onPostExecute(Void result)
        {
            // show.setText(get);

            status.setText(String.valueOf("Upload Completed"));
            super.onPostExecute(result);


            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Status");
            alertDialog.setMessage("Upload Completed");
            alertDialog.setButton(AlertDialog. BUTTON_NEUTRAL , "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();



            add.setEnabled(true);

            pBar.setVisibility(View.GONE);



        }

        public void displayDirectoryContents(File dir) throws IOException, ParseException {

            try {

                File[] files = dir.listFiles();
                for (File fileEx : files) {
                    if (fileEx.isDirectory()) {
                        //System.out.println("directory:" + file.getCanonicalPath());
                        displayDirectoryContents(fileEx);
                    } else {
                        System.out.println("directory:" + fileEx.getCanonicalPath());
                        if (String.valueOf(fileEx.getCanonicalFile()).contains(".csv")){

                            File file = new File(String.valueOf(fileEx.getCanonicalFile()));
                            FileReader fileReader = new FileReader(file);
                            BufferedReader bufferedReader = new BufferedReader(fileReader);
                            String line;
                            int cnt = 0;

                            while ((line = bufferedReader.readLine()) != null) {
                                cnt++;
                                if (cnt == 1) {
                                    continue;
                                }
                                CSVBean csvBean = CSVBean.mapCSVBean(line.split("\",\""));
                                csvBeanLst.add(csvBean);
                            }

                            fileReader.close();

                            //System.out.println("Number of file Read"+count);

                            //connection pool
                            //count++;
                            //Toast.makeText(getApplicationContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();

                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection(db,uname,pass);
                            Statement st = conn.createStatement();


                            String qur = "INSERT INTO test1(FilePath ,FileName, CustomerName, Scenario, StepNo, InTransit, IsAlef, RunNo, RequestUTC, RequestTime, RequestSource, RequestSourcePort, RequestDestination, RequestDestinationPort, RequestLength, RequestHttpRequest, RequestHttpUserAgent, RequestHttpHost, RequestHttpConnection, RequestAcceptEncoding, ResponseUTC, ResponseTime, ResponseSource, ResponseSourcePort, ResponseDestination, ResponseDestinationPort, ResponseLength, ResponseHttpResponse, ResponseContentDisposition, ResponseContentType, ResponseContentLength, ResponseContentEncoding, ResponseDate, ResponseServer, ResponseCacheControl, ResponseConnection, ResponseTransferEncoding, ElapsedTime, TotalResponseTime, IsCached, RunDate, RunHr) VALUES"+"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                            java.sql.PreparedStatement ps = conn.prepareStatement(qur);

                            for (CSVBean csvBean :csvBeanLst) {

                                ps.setString(1, csvBean.getFilePath());
                                ps.setString(2, csvBean.getFileName());
                                ps.setString(3, csvBean.getCustomerName());
                                ps.setString(4, csvBean.getScenario());
                                ps.setString(5, csvBean.getStepNo());
                                ps.setString(6, csvBean.getInTransit());
                                ps.setString(7, csvBean.getIsAlef());
                                ps.setString(8, csvBean.getRunNo());
                                ps.setString(9, csvBean.getRequestUTC());
                                ps.setString(10, csvBean.getRequestTime());
                                ps.setString(11, csvBean.getRequestSource());
                                ps.setString(12, csvBean.getRequestSourcePort());
                                ps.setString(13, csvBean.getRequestDestination());
                                ps.setString(14, csvBean.getRequestDestinationPort());
                                ps.setString(15, csvBean.getRequestLength());
                                ps.setString(16, csvBean.getRequestHttpRequest());
                                ps.setString(17, csvBean.getRequestHttpUserAgent());
                                ps.setString(18, csvBean.getRequestHttpHost());
                                ps.setString(19, csvBean.getRequestHttpConnection());
                                ps.setString(20, csvBean.getRequestAcceptEncoding());
                                ps.setString(21, csvBean.getResponseUTC());
                                ps.setString(22, csvBean.getResponseTime());
                                ps.setString(23, csvBean.getResponseSource());
                                ps.setString(24, csvBean.getRequestSourcePort());
                                ps.setString(25, csvBean.getResponseDestination());
                                ps.setString(26, csvBean.getResponseDestinationPort());
                                ps.setString(27, csvBean.getResponseLength());
                                ps.setString(28, csvBean.getRequestHttpRequest());
                                ps.setString(29, csvBean.getResponseContentDisposition());
                                ps.setString(30, csvBean.getResponseContentType());
                                ps.setString(31, csvBean.getResponseContentLength());
                                ps.setString(32, csvBean.getResponseContentEncoding());
                                ps.setString(33, csvBean.getResponseDate());
                                ps.setString(34, csvBean.getResponseServer());
                                ps.setString(35, csvBean.getResponseCacheControl());
                                ps.setString(36, csvBean.getResponseConnection());
                                ps.setString(37, csvBean.getResponseTransferEncoding());
                                ps.setString(38, csvBean.getElapsedTime());
                                ps.setString(39, csvBean.getTotalResponseTime());
                                ps.setString(40, csvBean.getIsCached());
                                ps.setString(41, csvBean.getRunDate());
                                ps.setString(42, csvBean.getRunHr());

                                ps.addBatch();

                            }
                            ps.executeBatch();
                        }

                    }
                    csvBeanLst.clear();

                }

            }catch(Exception e){
                e.printStackTrace();
            }





        }



    }



}




