package com.hpmt.cool100.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hpmt.cool100.BuildConfig;
import com.hpmt.cool100.R;
import com.hpmt.cool100.Util.http.HttpGetData;
import com.hpmt.cool100.Util.http.HttpGetData.getDataCallBack;
import com.hpmt.cool100.Util.http.IsNetWorkConnected;
import com.hpmt.cool100.Util.tools.JsonUtils;
import com.hpmt.cool100.Util.tools.MD5Utils;
import com.hpmt.cool100.Util.tools.ShowAlertView;
import com.hpmt.cool100.adapter.HomeRightGiridAdapter;
import com.hpmt.cool100.base.BaseActivity;
import com.hpmt.cool100.config.MacrosConfig;
import com.hpmt.cool100.model.ElevatorList;
import com.hpmt.cool100.model.FloorModel;
import com.hpmt.cool100.model.PlistFileNameModel;
import com.loopj.android.http.RequestParams;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.zhy.m.permission.MPermissions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddEleActivity extends BaseActivity implements View.OnClickListener {

    private  static  String vkey;
    private  static  String eleid;
    private  static  String userid;
    private  static  String function;
    private  static  String HregisterId;
    private  static  String HregisterNum;
    private Button commit;
    private Button addhardware;
    private Button addbuild;
    private Button madeunit;
    private Button useUnit;
    private Button installUnit;
    private Button propertyUnit;
    private Button renovationUnit;
    private Button maintainUnit;
    private Button addman1;
    private Button addman3;
    private Button addman2;
    private Button addmanage1;
    private Button addmanage2;

    private Button addhardware1;
    private Button addbuild1;
    private Button madeunit1;
    private Button useUnit1;
    private Button installUnit1;
    private Button propertyUnit1;
    private Button renovationUnit1;
    private Button maintainUnit1;
    private Button addman11;
    private Button addman31;
    private Button addman21;
    private Button addmanage11;
    private Button addmanage21;

    private Button addhardware2;
    private Button addbuild2;
    private Button madeunit2;
    private Button useUnit2;
    private Button installUnit2;
    private Button propertyUnit2;
    private Button renovationUnit2;
    private Button maintainUnit2;
    private Button addman12;
    private Button addman32;
    private Button addman22;
    private Button addmanage12;
    private Button addmanage22;

    private EditText edt_f_1;
    private EditText edt_f_2;
    private EditText edt_f_3;
//    private EditText edt_f_4;
    private EditText edt_f_5;
    private EditText edt_f_6;
    private EditText edt_f_7;
    private EditText edt_f_8;
    private EditText edt_f_9;
    private EditText edt_f_10;
    //private EditText edt_f_11;
    //private EditText edt_f_12;
    private TextView edt_f_13;
    private EditText edt_f_14;
    private TextView edt_f_15;
    private TextView edt_f_16;
    private TextView edt_f_17;
    private TextView edt_f_18;
    private EditText edt_f_19;
    private TextView edt_f_20;
    private TextView edt_f_21;
    private EditText edt_f_22;
    private TextView edt_f_23;
    private TextView edt_f_24;
    private TextView edt_f_25;
    private TextView edt_f_26;
    private TextView edt_f_27;
    private TextView edt_f_28;
    private TextView edt_f_29;
    private EditText buildingId;
    private EditText manufId;
    private EditText useId;
    private EditText installId;
    private EditText propertyId;
    private EditText renovationId;
    private EditText elevactorId;
    private EditText accountId;
    private EditText maintainId;
    private EditText registerId;
    private EditText man1Id;
    private EditText man2Id;
    private EditText man3Id;
    private EditText man4Id;
    private EditText man5Id;
    private TextView change;
    private RadioButton yes;
    private RadioButton no;
    private RadioButton yes1;
    private RadioButton no1;
    private RadioButton yes2;
    private RadioButton no2;
    private RadioButton yes3;
    private RadioButton no3;
    private RadioButton yes4;
    private RadioButton yes41;
    private RadioButton no4;
    private RadioButton yes5;
    private RadioButton no5;
    private ImageButton renovationDate;
    private ImageButton installDate;
    private ImageButton resetrenovationDate;
    private ImageButton resetinstallDate;
    private  DateFormat format;
    private Calendar calendar;
    private List<String> list = new ArrayList<>();
    private List<Integer> manList = new ArrayList<>();
    private List<List<String>> manslist = new ArrayList<>();
    private List<List<String>> manageslist = new ArrayList<>();
    private int flag=1;
    private int flag1=1;
    private int flag2=1;
    private int flag3=1;
    private int flag4=1;
    private int flag5=1;
    private ImageButton imgBtn_left;
    private TextView txt_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ele);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent Mesage=getIntent();
        vkey=Mesage.getStringExtra("vkey");
        eleid=Mesage.getStringExtra("eleid");
        userid=Mesage.getStringExtra("userid");
        function=Mesage.getStringExtra("function");
        init();
        if(function.equals("add"))
        {
            txt_title.setText(R.string.add);
        }
        else if(function.equals("edit"))
        {
            txt_title.setText(R.string.edit);
            GetEleDetail();

        }
        format= DateFormat.getDateTimeInstance();
        calendar= Calendar.getInstance(Locale.CHINA);
    }
    //初始化控价
    private void init() {
        // 初始化控件
        addhardware = (Button) findViewById(R.id.addhardware);
        addhardware.setOnClickListener(this);
        addhardware1 = (Button) findViewById(R.id.addhardware1);
        addhardware1.setOnClickListener(this);
        addhardware2 = (Button) findViewById(R.id.addhardware2);
        addhardware2.setOnClickListener(this);
        commit = (Button) findViewById(R.id.btn_commit);
        commit.setOnClickListener(this);
        addbuild = (Button) findViewById(R.id.addbuild);
        addbuild.setOnClickListener(this);
        addbuild1 = (Button) findViewById(R.id.addbuild1);
        addbuild1.setOnClickListener(this);
        addbuild2 = (Button) findViewById(R.id.addbuild2);
        addbuild2.setOnClickListener(this);
        madeunit = (Button) findViewById(R.id.madeUnit);
        madeunit.setOnClickListener(this);
        madeunit1 = (Button) findViewById(R.id.madeUnit1);
        madeunit1.setOnClickListener(this);
        madeunit2 = (Button) findViewById(R.id.madeUnit2);
        madeunit2.setOnClickListener(this);
        useUnit = (Button) findViewById(R.id.useUnit);
        useUnit.setOnClickListener(this);
        useUnit1 = (Button) findViewById(R.id.useUnit1);
        useUnit1.setOnClickListener(this);
        useUnit2 = (Button) findViewById(R.id.useUnit2);
        useUnit2.setOnClickListener(this);
        installUnit = (Button) findViewById(R.id.installUnit);
        installUnit.setOnClickListener(this);
        installUnit1 = (Button) findViewById(R.id.installUnit1);
        installUnit1.setOnClickListener(this);
        installUnit2 = (Button) findViewById(R.id.installUnit2);
        installUnit2.setOnClickListener(this);
        propertyUnit = (Button) findViewById(R.id.propertyUnit);
        propertyUnit.setOnClickListener(this);
        propertyUnit1 = (Button) findViewById(R.id.propertyUnit1);
        propertyUnit1.setOnClickListener(this);
        propertyUnit2 = (Button) findViewById(R.id.propertyUnit2);
        propertyUnit2.setOnClickListener(this);
        renovationUnit = (Button) findViewById(R.id.renovationUnit);
        renovationUnit.setOnClickListener(this);
        renovationUnit1 = (Button) findViewById(R.id.renovationUnit1);
        renovationUnit1.setOnClickListener(this);
        renovationUnit2 = (Button) findViewById(R.id.renovationUnit2);
        renovationUnit2.setOnClickListener(this);
        maintainUnit = (Button) findViewById(R.id.maintainUnit);
        maintainUnit.setOnClickListener(this);
        maintainUnit1 = (Button) findViewById(R.id.maintainUnit1);
        maintainUnit1.setOnClickListener(this);
        maintainUnit2 = (Button) findViewById(R.id.maintainUnit2);
        maintainUnit2.setOnClickListener(this);
        addman1 = (Button) findViewById(R.id.addman1);
        addman1.setOnClickListener(this);
        addman11 = (Button) findViewById(R.id.addman11);
        addman11.setOnClickListener(this);
        addman12 = (Button) findViewById(R.id.addman12);
        addman12.setOnClickListener(this);
        addman2 = (Button) findViewById(R.id.addman2);
        addman2.setOnClickListener(this);
        addman21 = (Button) findViewById(R.id.addman21);
        addman21.setOnClickListener(this);
        addman22 = (Button) findViewById(R.id.addman22);
        addman22.setOnClickListener(this);
        addman3 = (Button) findViewById(R.id.addman3);
        addman3.setOnClickListener(this);
        addman31 = (Button) findViewById(R.id.addman31);
        addman31.setOnClickListener(this);
        addman32 = (Button) findViewById(R.id.addman32);
        addman32.setOnClickListener(this);
        addmanage1 = (Button) findViewById(R.id.addmanage1);
        addmanage1.setOnClickListener(this);
        addmanage11 = (Button) findViewById(R.id.addmanage11);
        addmanage11.setOnClickListener(this);
        addmanage12 = (Button) findViewById(R.id.addmanage12);
        addmanage12.setOnClickListener(this);
        addmanage2 = (Button) findViewById(R.id.addmanage2);
        addmanage2.setOnClickListener(this);
        addmanage21 = (Button) findViewById(R.id.addmanage21);
        addmanage21.setOnClickListener(this);
        addmanage22 = (Button) findViewById(R.id.addmanage22);
        addmanage22.setOnClickListener(this);
        edt_f_1=(EditText)findViewById(R.id.edt_f_1);
        edt_f_2=(EditText)findViewById(R.id.edt_f_2);
        edt_f_3=(EditText)findViewById(R.id.edt_f_3);
//        edt_f_4=(EditText)findViewById(R.id.edt_f_4);
        edt_f_5=(EditText)findViewById(R.id.edt_f_5);
        edt_f_6=(EditText)findViewById(R.id.edt_f_6);
        edt_f_7=(EditText)findViewById(R.id.edt_f_7);
//        edt_f_8=(EditText)findViewById(R.id.edt_f_8);
//        edt_f_9=(EditText)findViewById(R.id.edt_f_9);
//        edt_f_10=(EditText)findViewById(R.id.edt_f_10);
//        edt_f_11=(EditText)findViewById(R.id.edt_f_11);
//        edt_f_12=(EditText)findViewById(R.id.edt_f_12);
        edt_f_13=(TextView)findViewById(R.id.edt_f_13);
        edt_f_14=(EditText)findViewById(R.id.edt_f_14);
        edt_f_15=(TextView)findViewById(R.id.edt_f_15);
        edt_f_16=(TextView)findViewById(R.id.edt_f_16);
        edt_f_17=(TextView)findViewById(R.id.edt_f_17);
        edt_f_18=(TextView)findViewById(R.id.edt_f_18);
        edt_f_19=(EditText)findViewById(R.id.edt_f_19);
        edt_f_20=(TextView)findViewById(R.id.edt_f_20);
        edt_f_21=(TextView)findViewById(R.id.edt_f_21);
        edt_f_22=(EditText)findViewById(R.id.edt_f_22);
        edt_f_23=(TextView)findViewById(R.id.edt_f_23);
        edt_f_24=(TextView)findViewById(R.id.edt_f_24);
        edt_f_25=(TextView)findViewById(R.id.edt_f_25);
        edt_f_26=(TextView)findViewById(R.id.edt_f_26);
        edt_f_27=(TextView)findViewById(R.id.edt_f_27);
        edt_f_28=(TextView)findViewById(R.id.edt_f_28);
        edt_f_29=(TextView)findViewById(R.id.edt_f_29);
        buildingId=(EditText)findViewById(R.id.buildingId);
        manufId=(EditText)findViewById(R.id.manufId);
        useId=(EditText)findViewById(R.id.useId);
        installId=(EditText)findViewById(R.id.installId);
        propertyId=(EditText)findViewById(R.id.propertyId);
        renovationId=(EditText)findViewById(R.id.renovationId);
        elevactorId=(EditText)findViewById(R.id.elevactorId);
        accountId=(EditText)findViewById(R.id.accountId);
        accountId.setText(userid);
        elevactorId.setText(eleid);
        man1Id=(EditText)findViewById(R.id.man1Id);
        man2Id=(EditText)findViewById(R.id.man2Id);
        man3Id=(EditText)findViewById(R.id.man3Id);
        man4Id=(EditText)findViewById(R.id.man4Id);
        man5Id=(EditText)findViewById(R.id.man5Id);
        change=(TextView)findViewById(R.id.change_f);
        registerId=(EditText)findViewById(R.id.registerId);
        maintainId=(EditText)findViewById(R.id.maintainId);
        yes=(RadioButton) findViewById(R.id.yes);
        yes.setOnClickListener(this);
        no=(RadioButton) findViewById(R.id.no);
        no.setOnClickListener(this);
        yes1=(RadioButton) findViewById(R.id.yes1);
        yes1.setOnClickListener(this);
        no1=(RadioButton) findViewById(R.id.no1);
        no1.setOnClickListener(this);
        yes2=(RadioButton) findViewById(R.id.yes2);
        yes2.setOnClickListener(this);
        no2=(RadioButton) findViewById(R.id.no2);
        no2.setOnClickListener(this);
        yes3=(RadioButton) findViewById(R.id.yes3);
        yes3.setOnClickListener(this);
        no3=(RadioButton) findViewById(R.id.no3);
        no3.setOnClickListener(this);
        yes4=(RadioButton) findViewById(R.id.china);
        yes4.setOnClickListener(this);
        yes41=(RadioButton) findViewById(R.id.jian);
        yes41.setOnClickListener(this);
        no4=(RadioButton) findViewById(R.id.english);
        no4.setOnClickListener(this);
        yes5=(RadioButton) findViewById(R.id.eletype1);
        yes5.setOnClickListener(this);
        no5=(RadioButton) findViewById(R.id.eletype2);
        no5.setOnClickListener(this);
        renovationDate=(ImageButton) findViewById(R.id.renovationDate);
        renovationDate.setOnClickListener(this);
        installDate=(ImageButton) findViewById(R.id.installDate);
        installDate.setOnClickListener(this);
        resetrenovationDate=(ImageButton) findViewById(R.id.resetrenovationDate);
        resetrenovationDate.setOnClickListener(this);
        resetinstallDate=(ImageButton) findViewById(R.id.resetinstallDate);
        resetinstallDate.setOnClickListener(this);

        imgBtn_left = (ImageButton) findViewById(R.id.left_imgBtn);
        txt_title = (TextView) findViewById(R.id.center_txt);
        //imgBtn_left.setImageResource(R.drawable.fanhui);
        imgBtn_left.setVisibility(View.VISIBLE);
        imgBtn_left.setOnClickListener(this);
        txt_title.setVisibility(View.VISIBLE);



//        addhardware.setOnItemClickListener(
//                new AdapterView.OnItemClickListener() {
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//                    {
//
//
//                    }
//
//                });

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.addhardware:
                clear();
               //跳转界面
                Intent Hardwareintent = new Intent(AddEleActivity.this, selecHardware.class);
                Hardwareintent.putExtra("id", userid);
                Hardwareintent.putExtra("vkey", vkey);
                startActivityForResult(Hardwareintent,1);
                break;
            case R.id.addbuild:
                clear();
                //跳转界面
                Intent Buildintent = new Intent(AddEleActivity.this, selectBuilding.class);
                Buildintent.putExtra("id", userid);
                Buildintent.putExtra("vkey", vkey);
                startActivityForResult(Buildintent,1);
                break;
                //单位
            case R.id.madeUnit:
                clear();
                //跳转界面
                Intent Unitintent = new Intent(AddEleActivity.this, selectUnit.class);
                Unitintent.putExtra("id", userid);
                Unitintent.putExtra("vkey", vkey);
                Unitintent.putExtra("unitType","3");
                Unitintent.putExtra("unitnum","3");
                startActivityForResult(Unitintent,1);
                break;
            case R.id.useUnit:
                clear();
                //跳转界面
                Intent useintent = new Intent(AddEleActivity.this, selectUnit.class);
                useintent.putExtra("id", userid);
                useintent.putExtra("vkey", vkey);
                useintent.putExtra("unitType", "2");
                useintent.putExtra("unitnum","2");
                startActivityForResult(useintent,1);
                break;
            case R.id.installUnit:
                clear();
                //跳转界面
                Intent installintent = new Intent(AddEleActivity.this, selectUnit.class);
                installintent.putExtra("id", userid);
                installintent.putExtra("vkey", vkey);
                installintent.putExtra("unitType", "4");
                installintent.putExtra("unitnum","4");
                startActivityForResult(installintent,1);
                break;
            case R.id.propertyUnit:
                clear();
                //跳转界面
                Intent propertyintent = new Intent(AddEleActivity.this, selectUnit.class);
                propertyintent.putExtra("id", userid);
                propertyintent.putExtra("vkey", vkey);
                propertyintent.putExtra("unitType", "6");
                propertyintent.putExtra("unitnum","6");

                startActivityForResult(propertyintent,1);
                break;
            case R.id.renovationUnit:
                clear();
                //跳转界面
                Intent renovationintent = new Intent(AddEleActivity.this, selectUnit.class);
                renovationintent.putExtra("id", userid);
                renovationintent.putExtra("vkey", vkey);
                renovationintent.putExtra("unitType", "5");
                renovationintent.putExtra("unitnum","5");
                startActivityForResult(renovationintent,1);
                break;
            case R.id.maintainUnit:
                clear();
                //跳转界面
                Intent maintainintent = new Intent(AddEleActivity.this, selectUnit.class);
                maintainintent.putExtra("id", userid);
                maintainintent.putExtra("vkey", vkey);
                maintainintent.putExtra("unitType", "1");
                maintainintent.putExtra("unitnum","1");

                startActivityForResult(maintainintent,1);
                break;
            //人员按钮
            case R.id.addman1:
                clear();
                //跳转界面
                Intent man1 = new Intent(AddEleActivity.this, selectMan.class);
                man1.putExtra("id", userid);
                man1.putExtra("vkey", vkey);
                man1.putExtra("manType","1");
                man1.putExtra("mannum","1");
                startActivityForResult(man1,1);
                break;
            case R.id.addman2:
                clear();
                //跳转界面
                Intent man2 = new Intent(AddEleActivity.this, selectMan.class);
                man2.putExtra("id", userid);
                man2.putExtra("vkey", vkey);
                man2.putExtra("manType","1");
                man2.putExtra("mannum","2");
                startActivityForResult(man2,1);
                break;
            case R.id.addman3:
                clear();
                //跳转界面
                Intent man3 = new Intent(AddEleActivity.this, selectMan.class);
                man3.putExtra("id", userid);
                man3.putExtra("vkey", vkey);
                man3.putExtra("manType","1");
                man3.putExtra("mannum","3");
                startActivityForResult(man3,1);
                break;
            case R.id.addmanage1:
                clear();
                //跳转界面
                Intent man4 = new Intent(AddEleActivity.this, selectMan.class);
                man4.putExtra("id", userid);
                man4.putExtra("vkey", vkey);
                man4.putExtra("manType","2");
                man4.putExtra("mannum","4");
                startActivityForResult(man4,1);
                break;
            case R.id.addmanage2:
                clear();
                //跳转界面
                Intent man5 = new Intent(AddEleActivity.this, selectMan.class);
                man5.putExtra("id", userid);
                man5.putExtra("vkey", vkey);
                man5.putExtra("manType","2");
                man5.putExtra("mannum","5");
                startActivityForResult(man5,1);
                break;
            case R.id.addhardware1:
                clear();

                Intent sao = new Intent(AddEleActivity.this,AddDevice.class);
                sao.putExtra("vkey", vkey);
                sao.putExtra("buttontype", "add");
                startActivityForResult(sao,1);
                //跳转页面
                break;
            case R.id.addbuild1:
                clear();
                //隐藏光标
                Intent Build1intent = new Intent(AddEleActivity.this, addBuild.class);
                Build1intent.putExtra("id", userid);
                Build1intent.putExtra("vkey", vkey);
                startActivityForResult(Build1intent,1);
                break;
            //单位
            case R.id.madeUnit1:
                clear();
                //跳转界面
                Intent Unitintent1 = new Intent(AddEleActivity.this, addUnit.class);
                Unitintent1.putExtra("id", userid);
                Unitintent1.putExtra("vkey", vkey);
                Unitintent1.putExtra("unitType","3");
                Unitintent1.putExtra("unitnum","3");
                startActivityForResult(Unitintent1,1);
                break;
            case R.id.useUnit1:
                clear();
                //跳转界面
                Intent useintent1 = new Intent(AddEleActivity.this, addUnit.class);
                useintent1.putExtra("id", userid);
                useintent1.putExtra("vkey", vkey);
                useintent1.putExtra("unitType", "2");
                useintent1.putExtra("unitnum", "2");
                startActivityForResult(useintent1,1);
                break;
            case R.id.installUnit1:
                clear();
                Intent installintent1 = new Intent(AddEleActivity.this, addUnit.class);
                installintent1.putExtra("id", userid);
                installintent1.putExtra("vkey", vkey);
                installintent1.putExtra("unitType", "4");
                installintent1.putExtra("unitnum", "4");
                startActivityForResult(installintent1,1);
                break;
            case R.id.propertyUnit1:
                clear();
                //跳转界面
                Intent propertyintent1 = new Intent(AddEleActivity.this, addUnit.class);
                propertyintent1.putExtra("id", userid);
                propertyintent1.putExtra("vkey", vkey);
                propertyintent1.putExtra("unitType", "6");
                propertyintent1.putExtra("unitnum", "6");
                startActivityForResult(propertyintent1,1);
                break;
            case R.id.renovationUnit1:
                clear();
                Intent renovationintent1 = new Intent(AddEleActivity.this, addUnit.class);
                renovationintent1.putExtra("id", userid);
                renovationintent1.putExtra("vkey", vkey);
                renovationintent1.putExtra("unitType", "5");
                renovationintent1.putExtra("unitnum", "5");
                startActivityForResult(renovationintent1,1);
                break;
            case R.id.maintainUnit1:
                clear();
                //跳转界面
                Intent maintainintent1 = new Intent(AddEleActivity.this, addUnit.class);
                maintainintent1.putExtra("id", userid);
                maintainintent1.putExtra("vkey", vkey);
                maintainintent1.putExtra("unitType", "1");
                maintainintent1.putExtra("unitnum", "1");
                startActivityForResult(maintainintent1,1);

                break;

            case R.id.addman11:
                clear();
                //跳转界面
                Intent man11 = new Intent(AddEleActivity.this, addMan.class);
                man11.putExtra("id", userid);
                man11.putExtra("vkey", vkey);
                man11.putExtra("manType","1");
                man11.putExtra("mannum","1");
                startActivityForResult( man11,1);
                break;
            case R.id.addman21:
                clear();
                //跳转界面
                Intent man21 = new Intent(AddEleActivity.this, addMan.class);
                man21.putExtra("id", userid);
                man21.putExtra("vkey", vkey);
                man21.putExtra("manType","1");
                man21.putExtra("mannum","2");
                startActivityForResult(man21,1);
                break;
            case R.id.addman31:
                clear();
                //跳转界面
                Intent man31 = new Intent(AddEleActivity.this, addMan.class);
                man31.putExtra("id", userid);
                man31.putExtra("vkey", vkey);
                man31.putExtra("manType","1");
                man31.putExtra("mannum","3");
                startActivityForResult(man31,1);
                break;
            case R.id.addmanage11:
                clear();
                //跳转界面
                Intent man41 = new Intent(AddEleActivity.this, addMan.class);
                man41.putExtra("id", userid);
                man41.putExtra("vkey", vkey);
                man41.putExtra("manType","2");
                man41.putExtra("mannum","4");
                startActivityForResult(man41,1);
                break;
            case R.id.addmanage21:
                clear();
                //跳转界面
                Intent man51 = new Intent(AddEleActivity.this, addMan.class);
                man51.putExtra("id", userid);
                man51.putExtra("vkey", vkey);
                man51.putExtra("manType","2");
                man51.putExtra("mannum","5");
                startActivityForResult(man51,1);
                break;
            //重置
            case R.id.addhardware2:
                edt_f_13.setText("");
                registerId.setText("");
                break;
            case R.id.addbuild2:
                edt_f_15.setText("");
                buildingId.setText("");
                break;

            case R.id.madeUnit2:
                edt_f_16.setText("");
                 manufId.setText("");
                break;
            case R.id.useUnit2:
                edt_f_17.setText("");
                useId.setText("");
                break;
            case R.id.installUnit2:
                edt_f_18.setText("");
                installId.setText("");
                break;
            case R.id.propertyUnit2:
                edt_f_21.setText("");
                propertyId.setText("");
                break;
            case R.id.renovationUnit2:
                change.setText("");
                renovationId.setText("");
                break;
            case R.id.maintainUnit2:
                edt_f_24.setText("");
                maintainId.setText("");
                break;
            //人员按钮
            case R.id.addman12:
                edt_f_25.setText("");
                man1Id.setText("");
                break;
            case R.id.addman22:
                edt_f_26.setText("");
                man2Id.setText("");
                break;
            case R.id.addman32:
                edt_f_27.setText("");
                man3Id.setText("");
                break;
            case R.id.addmanage12:
                edt_f_28.setText("");
                man4Id.setText("");
                break;
            case R.id.addmanage22:
                edt_f_29.setText("");
                man5Id.setText("");
                break;
            //提交
            case R.id.btn_commit:
                //跳转界面
               AddEle();
                break;
            case R.id.installDate:
                //跳转界面
                showDatePickerDialog(this,0,edt_f_20,calendar);
                break;
            case R.id.renovationDate:
                //跳转界面
                showDatePickerDialog(this,0,edt_f_23,calendar);
                break;


            case R.id.resetinstallDate:
                //跳转界面
                edt_f_20.setText("");
                break;
            case R.id.resetrenovationDate:
                //跳转界面
                edt_f_23.setText("");
                break;
            case R.id.yes:
                flag=1;
                break;
            case R.id.no:
                flag=2;
                break;
            case R.id.yes1:
                flag1=1;
                break;
            case R.id.no1:
                flag1=2;
                break;
            case R.id.yes2:
                flag2=1;
                break;
            case R.id.no2:
                flag2=2;
                break;
            case R.id.yes3:
                flag3=1;
                break;
            case R.id.no3:
                flag3=2;
                break;
            case R.id.china:
                flag4=1;
                break;
            case R.id.jian:
                flag4=2;
                break;
            case R.id.english:
                flag4=3;
                break;
            case R.id.eletype1:
                flag5=1;
                break;
            case R.id.eletype2:
                flag5=2;
                break;
            case R.id.left_imgBtn:
                Intent intent = new Intent();
                setResult(19, intent);
                finish();
                break;
            default:
                break;
        }

    }

    private void GetEleDetail() {
        RequestParams params = new RequestParams();
        params.put("vkey",vkey);
        params.put("id",eleid);
        HttpGetData.post(AddEleActivity.this,
                //MacrosConfig.BaseUrl + "/MontServer/LoginServlet", params,
                MacrosConfig.BaseUrl +"/hpmt/findElevatorDetail.mvc", params,
                getResources().getString(R.string.loading),
                new HttpGetData.getDataCallBack() {
                    @Override
                    public void succcess(String res) {//这里面可以看到lists的值
                        // TODO Auto-generated method stub
                        Log.e("res", res);//打印错误信息
                        try {
                            JSONObject object = new JSONObject(res);
//                            String code = JsonUtils.getJSONString(object,
//                                    "content");
                            if (object.isNull("content")) {
                                Toast.makeText(AddEleActivity.this,
                                      R.string.ele_Ded,
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                setResult(20, intent);
                                finish();
                            }
                            // JSONArray Ele = content.getJSONArray("rows");
                            //获取数据
//                            JSONObject value = content.getJSONObject(0);
                            else {
                                JSONObject value = object.getJSONObject("content");
                                edt_f_1.setText(value.getString("elevatorNum"));
                                edt_f_2.setText(value.getString("insideNum"));
                                if (value.getString("elevatorModel").length() > 0 && !value.getString("ratedWeight").toString().equals("null")) {
                                    edt_f_3.setText(value.getString("elevatorModel"));
                                }
//
                                if (value.getString("floorDoor").length() > 0 && !value.getString("ratedWeight").toString().equals("null")) {
                                    edt_f_5.setText(value.getString("floorDoor"));
                                }
                                if (value.getString("ratedWeight").length() > 0 && !value.getString("ratedWeight").toString().equals("null")) {
                                    edt_f_6.setText(value.getString("ratedWeight"));
                                }
                                if (value.getString("ratedSpeed").length() > 0 && !value.getString("ratedWeight").toString().equals("null")) {
                                    edt_f_7.setText(value.getString("ratedSpeed"));
                                }
                                if (value.getString("voiceSts").toString().equals("1")) {
                                    flag=1;
                                    yes.setChecked(true);
                                } else {
                                    no.setChecked(true);
                                    flag=2;
                                }
                                if (value.getString("smsSts").toString().equals("1")) {
                                    flag1=1;
                                    yes1.setChecked(true);
                                } else {
                                    flag1=2;
                                    no1.setChecked(true);
                                }
                                if (value.getString("relieveSts").toString().equals("1")) {
                                    flag2=1;
                                    yes2.setChecked(true);
                                } else {
                                    flag2=2;
                                    no2.setChecked(true);
                                }
                                if (value.getString("testSts").toString().equals("1")) {
                                    flag3=1;
                                    yes3.setChecked(true);
                                } else {
                                    flag3=2;
                                    no3.setChecked(true);
                                }
                                if (value.getString("languageSts").toString().equals("1")) {
                                    flag4=1;
                                    yes4.setChecked(true);
                                } else if (value.getString("languageSts").toString().equals("2")) {
                                    flag4=2;
                                    yes41.setChecked(true);
                                } else {
                                    flag4=3;
                                    no4.setChecked(true);
                                }
                                if (value.getString("elevatorType").toString().equals("1")) {
                                    yes5.setChecked(true);
                                } else {
                                    no5.setChecked(true);
                                }


                                if (value.getString("registerNum").length() > 0 && !value.getString("registerNum").toString().equals("null")) {
                                    edt_f_13.setText(value.getString("registerNum"));
                                }
//                            edt_f_14.setText(value.getString("imsi"));
                                if (value.getString("buildingName").length() > 0 && !value.getString("buildingName").toString().equals("null")) {
                                    edt_f_15.setText(value.getString("buildingName"));
                                }
                                if (value.getString("manufactureName").length() > 0 && !value.getString("manufactureName").toString().equals("null")) {
                                    edt_f_16.setText(value.getString("manufactureName"));
                                }
                                if (value.getString("userCompanyName").length() > 0 && !value.getString("userCompanyName").toString().equals("null")) {
                                    edt_f_17.setText(value.getString("userCompanyName"));
                                }
                                if (value.getString("installationCompanyName").length() > 0 && !value.getString("installationCompanyName").toString().equals("null")) {
                                    edt_f_18.setText(value.getString("installationCompanyName"));
                                }
                                if (value.getString("installationProjDirector").length() > 0 && !value.getString("installationProjDirector").toString().equals("null")) {
                                    edt_f_19.setText(value.getString("installationProjDirector"));
                                }

                                if (value.getString("installationDate").length() > 0 && !value.getString("installationDate").toString().equals("null")) {
                                    edt_f_20.setText(value.getString("installationDate"));
                                }

                                if (value.getString("propertyCompanyName").length() > 0 && !value.getString("propertyCompanyName").toString().equals("null")) {
                                    edt_f_21.setText(value.getString("propertyCompanyName"));
                                }
                                if (value.getString("renovationCompanyName").length() > 0 && !value.getString("renovationCompanyName").toString().equals("null")) {
                                    change.setText(value.getString("renovationCompanyName"));
                                }
                                if (value.getString("renovationProjDirector").length() > 0 && !value.getString("renovationProjDirector").toString().equals("null")) {
                                    edt_f_22.setText(value.getString("renovationProjDirector"));
                                }
                                if (value.getString("renovationDate").length() > 0 && !value.getString("renovationDate").toString().equals("null")) {
                                    edt_f_23.setText(value.getString("renovationDate"));
                                }
                                if (value.getString("maintainCompanyName").length() > 0 && !value.getString("maintainCompanyName").toString().equals("null")) {
                                    edt_f_24.setText(value.getString("maintainCompanyName"));
                                }
                                //管理人员
                                JSONArray Ele = value.getJSONArray("mans");
                                if (Ele.length() > 0) {
                                    for (int i = 0; i < Ele.length(); i++) {
                                        List<String> manlist = new ArrayList<>();
                                        List<String> managelist = new ArrayList<>();
                                        JSONObject manvalue = Ele.getJSONObject(i);
                                        String type = manvalue.getString("manType");
                                        if (type.equals("1")) {
                                            manlist.add(manvalue.getString("manId"));
                                            manlist.add(manvalue.getString("manName"));
                                            manslist.add(manlist);

                                        } else if (type.equals("2")) {
                                            managelist.add(manvalue.getString("manId"));
                                            managelist.add(manvalue.getString("manName"));
                                            manageslist.add(managelist);
                                        }
                                    }
                                }
                                if (manslist.size() == 1) {
                                    man1Id.setText(manslist.get(0).get(0));
                                    edt_f_25.setText(manslist.get(0).get(1));
                                } else if (manslist.size() == 2) {
                                    man1Id.setText(manslist.get(0).get(0));
                                    edt_f_25.setText(manslist.get(0).get(1));
                                    man2Id.setText(manslist.get(1).get(0));
                                    edt_f_26.setText(manslist.get(1).get(1));
                                } else if (manslist.size() == 3) {
                                    man1Id.setText(manslist.get(0).get(0));
                                    edt_f_25.setText(manslist.get(0).get(1));
                                    man2Id.setText(manslist.get(1).get(0));
                                    edt_f_26.setText(manslist.get(1).get(1));
                                    man3Id.setText(manslist.get(2).get(0));
                                    edt_f_27.setText(manslist.get(2).get(1));
                                }

                                if (manageslist.size() == 1) {
                                    man4Id.setText(manageslist.get(0).get(0));
                                    edt_f_28.setText(manageslist.get(0).get(1));
                                } else if (manageslist.size() == 2) {
                                    man4Id.setText(manageslist.get(0).get(0));
                                    edt_f_28.setText(manageslist.get(0).get(1));
                                    man5Id.setText(manageslist.get(1).get(0));
                                    edt_f_29.setText(manageslist.get(1).get(1));
                                }

//                            edt_f_25.setText(value.getString(""));
//                            edt_f_26.setText(value.getString(""));
//                            edt_f_27.setText(value.getString(""));
//                            edt_f_28.setText(value.getString(""));
//                            edt_f_29.setText(value.getString(""));

                                elevactorId.setText(value.getString("id"));
                                buildingId.setText(value.getString("buildingId"));
                                manufId.setText(value.getString("manufId"));
                                propertyId.setText(value.getString("propertyId"));
                                renovationId.setText(value.getString("renovationId"));
                                useId.setText(value.getString("useId"));
                                maintainId.setText(value.getString("maintainId"));
                                installId.setText(value.getString("installId"));
                            }
                        }catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void fail(String error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(AddEleActivity.this, error,
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void AddEle() {
        if (edt_f_1.getText().toString().length() == 0) {
            Toast.makeText(AddEleActivity.this,
                    R.string.fill_elevator,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (edt_f_2.getText().toString().length() == 0) {
            Toast.makeText(AddEleActivity.this,
                    R.string.fill_internal,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (edt_f_13.getText().toString().length() == 0) {
            Toast.makeText(AddEleActivity.this,
                    R.string.fill_hardware,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params = new RequestParams();
        manList=new ArrayList<>();
        if(man1Id.getText().toString()!=null && !man1Id.getText().toString().equals(""))
        {
            manList.add(Integer.parseInt(man1Id.getText().toString()));
        }
        if(man2Id.getText().toString()!=null && !man2Id.getText().toString().equals(""))
        {
            manList.add(Integer.parseInt(man2Id.getText().toString()));
        }
        if(man3Id.getText().toString()!=null && !man3Id.getText().toString().equals(""))
        {
            manList.add(Integer.parseInt(man3Id.getText().toString()));
        }
        if(man4Id.getText().toString()!=null && !man4Id.getText().toString().equals(""))
        {
            manList.add(Integer.parseInt(man4Id.getText().toString()));
        }
        if(man5Id.getText().toString()!=null && !man5Id.getText().toString().equals(""))
        {
            manList.add(Integer.parseInt(man5Id.getText().toString()));
        }
        params.put("vkey",vkey);
        params.put("eleid", eleid);
        params.put("registerId",registerId.getText().toString());
        params.put("registerNum",edt_f_13.getText().toString());
        params.put("accountId",userid);
        params.put("buildingId",buildingId.getText().toString());
        params.put("manufId",manufId.getText().toString());
        params.put("installId",installId.getText().toString());
        params.put("maintainId",maintainId.getText().toString());
        params.put("useId",useId.getText().toString());
        params.put("propertyId",propertyId.getText().toString());
        params.put("renovationId",renovationId.getText().toString());
        params.put("elevatorNum",edt_f_1.getText().toString());
        params.put("insideNum",edt_f_2.getText().toString());
        params.put("elevatorModel",edt_f_3.getText().toString());
        params.put("elevatorType",flag5);
        params.put("floorDoor",edt_f_5.getText().toString());
        params.put("ratedWeight",edt_f_6.getText().toString());
        params.put("ratedSpeed",edt_f_7.getText().toString());
        params.put("installationProjDirector",edt_f_19.getText().toString());
        params.put("renovationProjDirector",edt_f_22.getText().toString());
        params.put("installationDate",edt_f_20.getText().toString());
        params.put("renovationDate",edt_f_23.getText().toString());
        params.put("e68Status","0");
        params.put("manIds",manList);
//        params.put("lastTime",elevactorId.getText().toString());
//        params.put("lastInspection",elevactorId.getText().toString());
//        params.put("nextInspetion",elevactorId.getText().toString());
//        params.put("voiceSts",edt_f_8.getText().toString());
//        params.put("smsSts",edt_f_9.getText().toString());
//        params.put("relieveSts",edt_f_10.getText().toString());
//        params.put("testSts",edt_f_11.getText().toString());
//        params.put("languageSts",edt_f_12.getText().toString());
        params.put("voiceSts",flag);
        params.put("smsSts",flag1);
        params.put("relieveSts",flag2);
        params.put("testSts",flag3);
        params.put("languageSts",flag4);
        HttpGetData.post(AddEleActivity.this,
                //MacrosConfig.BaseUrl + "/MontServer/LoginServlet", params,
                MacrosConfig.BaseUrl +"/hpmt/editElevator.mvc", params,
                getResources().getString(R.string.loading),
                new HttpGetData.getDataCallBack() {
                    @Override
                    public void succcess(String res) {//这里面可以看到lists的值
                        // TODO Auto-generated method stub
                        Log.e("res", res);//打印错误信息
                        try {
                            JSONObject object = new JSONObject(res);
                            String status = JsonUtils.getJSONString(object, "status");
                            if(status.equals("1"))
                            {
                                ShowAlertView.showDialog(
                                        AddEleActivity.this, getResources().getString(R.string.success),
                                        new ShowAlertView.ClickCallback() {

                                            @Override
                                            public void clickOk() {
                                                // TODO Auto-generated method
                                                // stub
                                                Intent intent = new Intent();
                                                setResult(18, intent);
                                               finish();
                                            }
                                        });
                            }
                            else if (status.equals("0"))
                            {
                                Toast.makeText(AddEleActivity.this, R.string.add_fail,
                                        Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void fail(String error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(AddEleActivity.this, error,
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }



    /**
     * 日期选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                tv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    //接受返回值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        //智能硬件
        if (requestCode == 1 && resultCode == 3) {
            HregisterId = data.getStringExtra("registerId");
            HregisterNum= data.getStringExtra("registerNum");
            registerId.setText(HregisterId);
            edt_f_13.setText(HregisterNum);
        }
        if (requestCode == 1 && resultCode == 13) {
            HregisterNum= data.getStringExtra("registerNum");
            edt_f_13.setText(HregisterNum);
        }
        //楼盘
        else if ((requestCode == 1 && resultCode == 4)||(requestCode == 1 && resultCode ==11)) {
            buildingId.setText(data.getStringExtra("buildId"));
            edt_f_15.setText(data.getStringExtra("buildName"));
        }
        //人员
        else if ((requestCode == 1 && resultCode == 5)||(requestCode == 1 && resultCode == 7)) {
           if(data.getStringExtra("mannum1").equals("1"))
           {
               edt_f_25.setText(data.getStringExtra("manName"));
               man1Id.setText(data.getStringExtra("manId"));
           }
           else if(data.getStringExtra("mannum1").equals("2"))
            {
                edt_f_26.setText(data.getStringExtra("manName"));
                man2Id.setText(data.getStringExtra("manId"));
            }
            else if(data.getStringExtra("mannum1").equals("3"))
            {
                edt_f_27.setText(data.getStringExtra("manName"));
                man3Id.setText(data.getStringExtra("manId"));
            }
            else if(data.getStringExtra("mannum1").equals("4"))
            {
                edt_f_28.setText(data.getStringExtra("manName"));
                man4Id.setText(data.getStringExtra("manId"));
            }
            else if(data.getStringExtra("mannum1").equals("5"))
            {
                edt_f_29.setText(data.getStringExtra("manName"));
                man5Id.setText(data.getStringExtra("manId"));
            }
        }
        //单位
        else if ((requestCode == 1 && resultCode == 6)|| (requestCode == 1 && resultCode == 8)) {
            if(data.getStringExtra("unitnum1").equals("1"))
            {
                edt_f_24.setText(data.getStringExtra("unitName"));
                maintainId.setText(data.getStringExtra("unitId"));
            }
            else if(data.getStringExtra("unitnum1").equals("2"))
            {
                edt_f_17.setText(data.getStringExtra("unitName"));
                useId.setText(data.getStringExtra("unitId"));
            }
            else if(data.getStringExtra("unitnum1").equals("3"))
            {
                edt_f_16.setText(data.getStringExtra("unitName"));
                manufId.setText(data.getStringExtra("unitId"));
            }
            else if(data.getStringExtra("unitnum1").equals("4"))
            {
                edt_f_18.setText(data.getStringExtra("unitName"));
                installId.setText(data.getStringExtra("unitId"));
            }
            else if(data.getStringExtra("unitnum1").equals("5"))
            {
                change.setText(data.getStringExtra("unitName"));
                renovationId .setText(data.getStringExtra("unitId"));
            }
            else if(data.getStringExtra("unitnum1").equals("6"))
            {
                edt_f_21.setText(data.getStringExtra("unitName"));
                propertyId.setText(data.getStringExtra("unitId"));

            }
        }


    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK){
//            Intent intent = new Intent();
//            setResult(18, intent);
//            finish();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    //清除光标
    public void clear()
    {
        edt_f_1.clearFocus();
        edt_f_2.clearFocus();
        edt_f_3.clearFocus();
        edt_f_5.clearFocus();
        edt_f_6.clearFocus();
        edt_f_7.clearFocus();
        edt_f_19.clearFocus();
        edt_f_22.clearFocus();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            setResult(19, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

//findElevatorDetail
