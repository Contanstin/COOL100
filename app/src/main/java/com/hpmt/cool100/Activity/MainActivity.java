package com.hpmt.cool100.Activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.debug.hv.ViewServer;
import com.hpmt.cool100.R;
import com.hpmt.cool100.Util.Jpush.TagAliasOperatorHelper;
import com.hpmt.cool100.Util.http.Constants;
import com.hpmt.cool100.Util.http.HttpGetData;
import com.hpmt.cool100.Util.http.IsNetWorkConnected;
import com.hpmt.cool100.Util.tools.JsonUtils;
import com.hpmt.cool100.Util.websocket.WebSocketUtils;
import com.hpmt.cool100.adapter.ElevatorListAdapter;
import com.hpmt.cool100.adapter.HomeRightGiridAdapter;
import com.hpmt.cool100.base.BaseActivity;
import com.hpmt.cool100.broadcast.NetReceiver;
import com.hpmt.cool100.config.MacrosConfig;
import com.hpmt.cool100.model.ElevatorList;
import com.hpmt.cool100.model.FloorModel;
import com.hpmt.cool100.model.LeftFloorModel;

import com.loopj.android.http.RequestParams;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;
import com.zhy.m.permission.MPermissions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import static com.hpmt.cool100.broadcast.NetReceiver.Network_judgment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private WebSocket mSocket;
    NetReceiver myReceiver;
    private long sendTime = 0L;
    private static final long HEART_BEAT_RATE = 2* 1000;
    private static String eleNum="";
    //接收
    private static List<WebSocket> _sockets = new ArrayList<WebSocket>();
    private static String JpushDate;
    private static String Identification_Number="";//设备编号
    private static String Car_Direction;//上下行方向
    private static String Car_Position;//当前楼层
    private static String Car_Status;//电梯状态
    private static String Door_Zone;//是否平层
    private static String Door_status;//开门状态
    private static String Door_lock;//门锁
    private static String Overload;//超载
    private static String onlineSts="";
    private static String faultCode;//故障信息
    private static String connectSts;
    private static String faultSts;//故障状态
    private static String insSts;//检修
    private static String lockSts;//锁梯
    private static String Service_Mode;//
    private static String allFloor;//所有楼梯
    private static JSONArray inCall;//轿内召梯楼层
    private static int a=0;//检修
    int isStart=0;
    //声明相关变量
    private List<ElevatorList> lists = new ArrayList<>();//
    private List<String> floorlists = new ArrayList<>();
    private List<String> arr=new ArrayList<>();
    private List<String> temp= new ArrayList<>();
    private List<ElevatorList> Orlist = new ArrayList<>();//
    private List<String> list = new ArrayList<>();
    private List<String> RightList = new ArrayList<>();
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private List<String> ElevatorData = new ArrayList<>();
    private static final String TAG = "res";
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView listView;
    private ListView lvLeftMenu;
    private ArrayAdapter arrayAdapter;
    TextView floor;
    private LeftFloorModel lmode = new LeftFloorModel();
    ElevatorListAdapter adapter;
    Runnable runnable;
    private int FloorTimes = 0;

    private TextView apptitle;
    private Button FaultRecord;
    private Button delete;
    private Button flush;
    private Button flushinfo;
    private ImageButton sao;// 扫一扫
    private ImageButton menu;// 打开侧滑
    private Button ele_manage;//电梯管理
    private final JSONObject json = new JSONObject();
    private static ImageView mDoorleft;// 左门
    private static ImageView mDoorright;// 右门
    private static  String Tel;
    private static  String FNum;
    private  static  String vkey;
    private  static  String id;
    private static AnimatorSet mSetAnim; // 动画

    private GridView right_gView;// 内召容器

    private HomeRightGiridAdapter rightAdapter;

    private ArrayList<LeftFloorModel> leftData = new ArrayList<LeftFloorModel>();// 外召数据
    private ArrayList<FloorModel> rightData = new ArrayList<FloorModel>();// 内召数据

    private AnimationDrawable animationDrawable;

    private ImageView imgVi_wallArrow;// 箭头
    private TextView txtVi_wallFloor;// 楼层显示
    private int tag = 0;
    private TextView ele_id;
    private TextView name;
    /**
     * 底部显示状态
     */
    private ImageView imgVi_tap0;
    private TextView txtVi_tap0;

    private ImageView imgVi_tap1;
    private TextView txtVi_tap1;

    private ImageView imgVi_tap2;
    private TextView txtVi_tap2;

    private ImageView imgVi_tap3;
    private TextView txtVi_tap3;

    private ImageView imgVi_tap4;
    private TextView txtVi_tap4;
    private EditText et_filter;

    private RelativeLayout rl_1;
    private RelativeLayout rl_2;
    private RelativeLayout rl_3;

    // private boolean isAnnimation;
    private int arrowTag = 0; // 0 ：上行 1：下行 2:停止
    private int faltTag = 0;// 如果出现故障 0 是无需跳转 1要跳转
    private boolean isFault;

    private ImageButton btn_open;
    private ImageButton btn_close;

    private Handler myhHandler = new Handler(); // 建立一个定时器，用于D组参数即时显示和待机界面的即时显示
    private SharedPreferences share;

    private ArrayList<String> mData = new ArrayList<String>();

    private static final int MIN_CLICK_DELAY_TIME = 3000;
    private static long lastClickTime;
    private static int Netflag=0;
    private static int Netflag1=0;
    private PopupWindow popupWindow;
    private  Button scanHardware;
    private  Button elemanage;
    private  Button wifisetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_main);
        Intent Mesage=getIntent();
        vkey=Mesage.getStringExtra("vkey");
        id=Mesage.getStringExtra("id");
        Tel=Mesage.getStringExtra("Tel");
        start();
        //http
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//       别名
        //极光推送设置别名
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        JPushInterface.setAlias(MainActivity.this, TagAliasOperatorHelper.sequence,Tel);
        Log.e("sequence",Integer.toString(TagAliasOperatorHelper.sequence));
        //左边
        GetDate();//获取列表
        init();
        findViews();
        name=(TextView)findViewById(R.id.name);
        name.setText(Tel);
        sao=(ImageButton)findViewById(R.id.scan);
        menu=(ImageButton)findViewById(R.id.menu);
//       ele_manage=(Button)findViewById(R.id.ele_manage);
//       ele_manage.setOnClickListener(this);
        sao.setOnClickListener(this);
        menu.setOnClickListener(this);
        mDrawerLayout.openDrawer(Gravity.LEFT);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }


        };

        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //电梯列表
        et_filter=(EditText)findViewById(R.id.search);

        listView = (ListView) findViewById(R.id.lv_left_menu);
        delete=(Button) findViewById(R.id.searchdelete);
        delete.setOnClickListener(this);
        FaultRecord=(Button) findViewById(R.id.FaultRecord);
        FaultRecord.setOnClickListener(this);
        adapter = new ElevatorListAdapter(MainActivity.this, R.layout.elelist, lists);
        flush=(Button) findViewById(R.id.flush);
        flush.setOnClickListener(this);
        flushinfo=(Button) findViewById(R.id.flushinfo);
        flushinfo.setOnClickListener(this);

        listView.setAdapter(adapter);

        et_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterData(s.toString());
            }
        });

        //内召
        rightAdapter= new HomeRightGiridAdapter(this, rightData);
        right_gView.setAdapter(rightAdapter);
        right_gView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        if (!isFastClick()) {
                            Toast.makeText(getApplicationContext(),
                                    R.string.click_3s,
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        FloorModel RightList1 = rightData.get(position);
                        String a=RightList1.getFloorNum();
                        int b=Integer.parseInt(allFloor)-position;
                        FNum=RightList1.getFloorNum();
                        if(RightList.contains(FNum))
                        {
                            Toast.makeText(getApplicationContext(),
                                    R.string.repeat_ele,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if(IsNetWorkConnected.isNetWorkConnected(MainActivity.this) && IsNetWorkConnected.isConnByHttp())
                        {
                            SendFloor(eleNum,1,b,vkey);
                        }

                    }

                });

        //获取数据实时显示
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ElevatorList elevatorList = lists.get(position);
                hideSoftKeyboard(MainActivity.this);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                //实时获取电梯信息
                if(IsNetWorkConnected.isNetWorkConnected(MainActivity.this) && IsNetWorkConnected.isConnByHttp())
                { eleNum = elevatorList.getElevatorNum();
                    setFloor();
                    apptitle.setText("");
                    jiemianclear();
                    mSocket.send(JsonToString(eleNum, 1, vkey));
            }
            }
        });

        //注册广播
        myReceiver = new NetReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myReceiver, intentFilter);
        //检查网络
        handler2.postDelayed(runnable2, 0);
        //实时连接
        mHandler.postDelayed(r, 100);//延时100毫秒
        //控制电梯
        btn_open = (ImageButton) findViewById(R.id.open_door);
        btn_close = (ImageButton) findViewById(R.id.close_door);

        btn_close.setOnClickListener(this);
        btn_open.setOnClickListener(this);


    }

    private void findViews() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        lvLeftMenu = (ListView) findViewById(R.id.lv_left_menu);
    }



    //获取列表
    private void GetDate() {
        RequestParams params = new RequestParams();
        params.put("vkey",vkey);
        params.put("id",id);
        //params.put("vkey","ed04d1");
        params.put("pageNumber", 1);
        params.put("pageSize",4000);
        HttpGetData.post(MainActivity.this,
                //MacrosConfig.BaseUrl + "/MontServer/LoginServlet", params,
                MacrosConfig.BaseUrl +"/hpmt/findElevatorNumListByVkey.mvc", params,
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
                            JSONObject content = object.getJSONObject("content");
                            JSONArray Ele = content.getJSONArray("rows");
                            for (int i = 0; i < Ele.length(); i++) {

                                JSONObject value = Ele.getJSONObject(i);
                                String E = value.getString("elevatorNum");
                                String I = value.getString("insideNum");
                                String EleId = value.getString("id");
                                //所有监控电梯的长连接
                                Orlist.add(new ElevatorList(E, I,EleId));
                                lists.add(new ElevatorList(E, I,EleId));

                            }
                            adapter.notifyDataSetChanged();
                            String status = JsonUtils.getJSONString(object, "status");


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void fail(String error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(MainActivity.this, error,
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void init() {
        // 初始化控件
        mDoorleft = (ImageView) findViewById(R.id.fragement_home_door_left_img);
        mDoorright = (ImageView) findViewById(R.id.fragement_home_door_right_img);
        apptitle=(TextView)findViewById(R.id.online);

        right_gView = (GridView) findViewById(R.id.right_gridview);

        imgVi_tap0 = (ImageView) findViewById(R.id.fragement_home_bottom_tap_img_0);
        txtVi_tap0 = (TextView) findViewById(R.id.fragement_home_bottom_tap_txt_0);

        imgVi_tap1 = (ImageView) findViewById(R.id.fragement_home_bottom_tap_img_1);
        txtVi_tap1 = (TextView) findViewById(R.id.fragement_home_bottom_tap_txt_1);

        imgVi_tap2 = (ImageView) findViewById(R.id.fragement_home_bottom_tap_img_2);
        txtVi_tap2 = (TextView) findViewById(R.id.fragement_home_bottom_tap_txt_2);

        imgVi_tap3 = (ImageView) findViewById(R.id.fragement_home_bottom_tap_img_3);
        txtVi_tap3 = (TextView) findViewById(R.id.fragement_home_bottom_tap_txt_3);

        imgVi_tap4 = (ImageView) findViewById(R.id.fragement_home_bottom_tap_img_4);
        txtVi_tap4 = (TextView) findViewById(R.id.fragement_home_bottom_tap_txt_4);

        imgVi_wallArrow = (ImageView) findViewById(R.id.fragement_wall_arrows);
        txtVi_wallFloor = (TextView) findViewById(R.id.fragement_wall_floors);

        rightAdapter = new HomeRightGiridAdapter(this, rightData);
        right_gView.setAdapter(rightAdapter);
        ele_id=(TextView) findViewById(R.id.ele_id);
    }


    //显示内召
    public void LeleNum(String allFloor,List<String> arr,String Identification_Number,int flag) {
        if(flag==1)
        {
            if (eleNum.equals(Identification_Number)) {
                rightData.clear();
                int st1 = Integer.parseInt(allFloor, 10);
                for (int i = st1-1; i >= 0; i--) {
                    FloorModel mode = new FloorModel();
                    //右边
                    mode.setFloorNum(floorlists.get(i));
                    if (arr.indexOf(String.valueOf(i+1)) >= 0) {
                        mode.setFloorStaus("1");
                    } else {
                        mode.setFloorStaus("0");
                    }
                    rightData.add(mode);
                }

            }


        }
        else
        {
            rightData.clear();
        }
        rightAdapter.notifyDataSetChanged();
    }


    //动画类
    public void Openanimate(final int duration,
                            final TimeInterpolator interpolator) {

        // Post this on the UI thread's message queue. It's needed for the items
        // to be already measured
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                mSetAnim = new AnimatorSet();
                mDoorleft.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                mDoorright.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                mSetAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // clean(destActivity);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // clean(destActivity);
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                // Animating the 2 parts away from each other
                Animator anim1 = ObjectAnimator.ofFloat(mDoorleft,
                        "translationX", mDoorleft.getWidth() * -1);
                Animator anim2 = ObjectAnimator.ofFloat(mDoorright,
                        "translationX", mDoorright.getWidth());

                if (interpolator != null) {
                    anim1.setInterpolator(interpolator);
                    anim2.setInterpolator(interpolator);
                }

                mSetAnim.setDuration(duration);
                mSetAnim.playTogether(anim1, anim2);
                mSetAnim.start();
            }
        });
    }

    public void Closeanimate(final int duration,
                             final TimeInterpolator interpolator) {

        // Post this on the UI thread's message queue. It's needed for the items
        // to be already measured
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                mSetAnim = new AnimatorSet();
                mDoorleft.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                mDoorright.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                mSetAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // clean(destActivity);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // clean(destActivity);
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                // Animating the 2 parts away from each other
                Animator anim1 = ObjectAnimator.ofFloat(mDoorleft,
                        "translationX", 0);
                Animator anim2 = ObjectAnimator.ofFloat(mDoorright,
                        "translationX", 0);

                if (interpolator != null) {
                    anim1.setInterpolator(interpolator);
                    anim2.setInterpolator(interpolator);
                }

                mSetAnim.setDuration(duration);
                mSetAnim.playTogether(anim1, anim2);
                mSetAnim.start();
            }
        });
    }

    /**
     * 上行动画
     */
    private void upAnimation() {

        imgVi_wallArrow.setVisibility(View.VISIBLE);
        imgVi_wallArrow.setImageResource(R.drawable.up_animationlist);
        animationDrawable = (AnimationDrawable) imgVi_wallArrow.getDrawable();
        animationDrawable.start();

    }

    /**
     * 下行动画
     */
    private void downAnimation() {
        imgVi_wallArrow.setVisibility(View.VISIBLE);
        imgVi_wallArrow.setImageResource(R.drawable.down_animationlist);
        animationDrawable = (AnimationDrawable) imgVi_wallArrow.getDrawable();
        animationDrawable.start();

    }

    /**
     * 停止动画
     */
    private void stopAnimation() {

        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable = (AnimationDrawable) imgVi_wallArrow
                    .getDrawable();
            animationDrawable.stop();
            imgVi_wallArrow.setVisibility(View.VISIBLE);
            if (arrowTag == 0) {
                imgVi_wallArrow.setImageResource(R.drawable.floor_wall_up_6);
            } else if (arrowTag == 1) {
                imgVi_wallArrow.setImageResource(R.drawable.floor_wall_down_6);
            } else if (arrowTag == 2) {
                imgVi_wallArrow.setVisibility(View.INVISIBLE);
            }

        } else {
            imgVi_wallArrow.setVisibility(View.VISIBLE);
            if (arrowTag == 0) {
                imgVi_wallArrow.setImageResource(R.drawable.floor_wall_up_6);
            } else if (arrowTag == 1) {
                imgVi_wallArrow.setImageResource(R.drawable.floor_wall_down_6);
            } else if (arrowTag == 2) {
                imgVi_wallArrow.setVisibility(View.INVISIBLE);
            }
        }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        myhHandler.removeCallbacks(runnable);
        ViewServer.get(this).removeWindow(this);
        unregisterReceiver(myReceiver);
        //handler2.removeCallbacks(runnable2);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        myhHandler.removeCallbacks(runnable);
        // popupWindow.dismiss();
        //handler2.removeCallbacks(runnable2);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        myhHandler.postDelayed(runnable, 300);
        ViewServer.get(this).setFocusedWindow(this);
    }
    private void openDoor() {
        // 开门
        Openanimate(2000, new DecelerateInterpolator());
    }

    private void closeDoor() {
        // 关门
        Closeanimate(2000, new DecelerateInterpolator());
    }

    private void openFlautWay() {
        // TODO Auto-generated method stub

        // 无故障则不许点击
        if (!isFault) {
            return;
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open_door:
                if(eleNum.length()>0 &&IsNetWorkConnected.isNetWorkConnected(MainActivity.this) && IsNetWorkConnected.isConnByHttp()) {
                    DoorOp(eleNum, 4, 1, vkey);
                }
                break;
            case R.id.close_door:
                if(eleNum.length()>0 && IsNetWorkConnected.isNetWorkConnected(MainActivity.this) && IsNetWorkConnected.isConnByHttp()) {
                    Log.e("2",eleNum);
                    DoorOp(eleNum, 4, 2, vkey);
                }

                break;
            case R.id.scan:
//                scanQRCode();
                showPopupWindow();
                break;
            case R.id.menu:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
//            case R.id.ele_manage:
//                //跳转电梯管理界面
//                Intent flush = new Intent(MainActivity.this,test.class);
//                startActivity(flush);
//                break;
            case R.id.searchdelete:
                et_filter.setText("");
                lists.clear();
                lists.addAll(Orlist);
                adapter.notifyDataSetChanged();
                break;
            case R.id.FaultRecord:
                Intent record = new Intent(MainActivity.this,FaultRecord.class);
                record.putExtra("eleNum", eleNum);
                record.putExtra("vkey", vkey);
                startActivityForResult(record,2);
                //startActivity(record);
                break;
            case R.id.flush:
                setFloor();
                break;
            case R.id.flushinfo:
                et_filter.setText("");
                lists.clear();
                Orlist.clear();
                GetDate();
                break;
            case R.id.scanHardware:
                Intent sao = new Intent(MainActivity.this,AddDevice.class);
                sao.putExtra("vkey", vkey);
                sao.putExtra("buttontype", "sao");
                startActivityForResult(sao,2);
                // startActivity(sao);

                break;
            case R.id.elemanage:
                //跳转电梯管理界面
                Intent manage = new Intent(MainActivity.this,ElevatorManage.class);
                eleNum="";
                 mSocket.cancel();
                //manage.putExtra("eleNum", eleNum);
                manage.putExtra("vkey", vkey);
                manage.putExtra("id", id);
                startActivityForResult(manage,2);
                // startActivity(manage);
                break;

            case R.id.wifisetting:
                //跳转电梯管理界面
                Intent wifi = new Intent(MainActivity.this,EspTouchActivity.class);
//                eleNum="";
//                mSocket.cancel();
                startActivityForResult(wifi,2);
//                startActivity(wifi);
                break;
            default:
                break;
        }
    }

    //
    /**
     *开关门
     */
    private void DoorOp(String elevatorNum, int controlType, int floorType,String vkey) {
        //String url = "ws://hpmontserver.com:3388/SpringIot/ws.mvc";
        String url = MacrosConfig.wsUrl;
        JSONObject json1 = new JSONObject();
        try {
            json1.put("elevatorNum", elevatorNum);
            json1.put("controlType", controlType);
            json1.put("floorType", floorType);
            json1.put("vkey", vkey);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        WebSocketUtils.requestWebSocket(url, null, json1.toString(), Constants.Macros.KEEP_UPDATE);
    }

    //呼梯
    private void SendFloor(String elevatorNum, int controlType, int callFloor,String vkey) {
        //String url = "ws://hpmontserver.com:3388/SpringIot/ws.mvc";
        String url = MacrosConfig.wsUrl;
        JSONObject json3 = new JSONObject();
        try {
            json3.put("elevatorNum", elevatorNum);
            json3.put("controlType", controlType);
            json3.put("callFloor", callFloor);
            json3.put("vkey", vkey);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("huti",json3.toString());
        WebSocketUtils.requestWebSocket(url, null, json3.toString(), Constants.Macros.KEEP_UPDATE);
    }

    Handler handler2 = new Handler();
    // 实现一个Runnable接口处理业务
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
//            if(!IsNetWorkConnected.isNetWorkConnected(MainActivity.this))
            if(Network_judgment==-1)
            {
                if(Netflag1==0) {
                    apptitle.setText(R.string.network_not);
                    btn_open.setVisibility(View.INVISIBLE);
                    txtVi_wallFloor.setText("");
                    imgVi_wallArrow.setVisibility(View.INVISIBLE);
                    btn_close.setVisibility(View.INVISIBLE);
                    right_gView.setVisibility(View.INVISIBLE);
                    show(getResources().getString(R.string.network_check));
                }
                //写一个线程用于发送
                Netflag1=1;
                Netflag=0;
            }
            else if(!IsNetWorkConnected.isConnByHttp())
            {
                if(Netflag1==0) {
                    apptitle.setText(R.string.service_not);
                    btn_open.setVisibility(View.INVISIBLE);
                    btn_close.setVisibility(View.INVISIBLE);
                    right_gView.setVisibility(View.INVISIBLE);
                    show( getResources().getString(R.string.service_check));
                }
                //写一个线程用于发送
                Netflag1=1;
                Netflag=0;
            }
            else if(IsNetWorkConnected.isNetWorkConnected(MainActivity.this) && IsNetWorkConnected.isConnByHttp())
            {
                if(Netflag==0) {
                    if(eleNum.length()<=0)
                    {
                        apptitle.setText("");
                    }
                    else
                    {
                        mSocket.cancel();
                        start();
                        Log.e("soo", "断网重连");
                    }
//                    btn_open.setVisibility(View.VISIBLE);
//                    btn_close.setVisibility(View.VISIBLE);
//                    right_gView.setVisibility(View.VISIBLE);
                    Netflag=1;
                    Netflag1=0;
                }
                else
                {       boolean isSuccess = mSocket.send("");//发送一个空消息给服务器，通过发送消息的成功失败来判断长连接的连接状态
                        if (!isSuccess) {//长连接已断开
                         //   mmHandler.removeCallbacks(heartBeatRunnable);
                            mSocket.cancel();//取消掉以前的长连接
                            start();//创建一个新的连接
                            Log.e("soo", "心跳重连");
                        } else {//长连接处于连接状态

                        }
                }

            }

            handler2.postDelayed(this, 4000);

        }
    };





    //保持更新
    private void keepFloor(String elevatorNum, int tMonitorIng, String vkey) {
        //String url = "ws://hpmontserver.com:3388/SpringIot/ws.mvc";
        String url =MacrosConfig.wsUrl;
        JSONObject json4 = new JSONObject();
        try {
            json4.put("elevatorNum", elevatorNum);
            json4.put("tMonitorIng", tMonitorIng);
            json4.put("vkey", vkey);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        WebSocketUtils.requestWebSocket(url, null, json4.toString(), Constants.Macros.KEEP_UPDATE);
    }





    //处理请求数据的返回
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case Constants.Macros.KEEP_UPDATE:
                    break;
                case Constants.Macros.QUERY_DATA:
                    break;
            }
        }
    };

    /**
     * 摁两次返回键强制退出程序*/

    private long extiTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - extiTime) > 2000) {
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.exit_toadt),
                        Toast.LENGTH_SHORT).show();
                extiTime = System.currentTimeMillis();

            } else {

                finish();
                JPushInterface.deleteAlias(MainActivity.this, TagAliasOperatorHelper.sequence);
                android.os.Process.killProcess(android.os.Process.myPid());
                application.exit();

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
    //查询
    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<ElevatorList> filterDateList = new ArrayList<ElevatorList>();

        if (TextUtils.isEmpty(filterStr)) {
            lists.clear();
            lists.addAll(Orlist);
        } else {
            lists.clear();
            for (ElevatorList elist : Orlist) {
                String name = elist.getElevatorNum();
                if (name.indexOf(filterStr.toString()) != -1 ) {
                    lists.add(elist);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }
    //隐藏软键盘
    private void hideSoftKeyboard(Context context){
        if (et_filter == null || context == null)return;
        InputMethodManager imm = (InputMethodManager) context. getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm == null)return;
        imm.hideSoftInputFromWindow(et_filter.getWindowToken(), 0); //强制隐藏键盘
    }
    //显示软键盘
    private void showSoftKeyboard(Context context){
        if (et_filter == null || context == null)return;
        InputMethodManager imm = (InputMethodManager)context. getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm == null)return;
        imm.showSoftInput(et_filter, InputMethodManager.SHOW_FORCED);
    }
    //键盘控制

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);

            int left = l[0];
            int top = l[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();

            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }

        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    Handler mHandler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            //do something
            //每隔1s循环执行run方法
            if(eleNum!=null && eleNum!="" && eleNum.length()>0 ) {

                keepFloor(eleNum, 1, vkey);
                mHandler.postDelayed(this, 50000);//50000
                Log.e("5s","mei");
            }
            else
            {
                Log.e("未绑定电梯","cuo");
                mHandler.postDelayed(this, 50000);
            }
        }};
    //除掉重复
    protected List<String> repeat(String[] str)
    {
        List<String> list = new ArrayList<String>();

        for (int i=0; i<str.length; i++) {
            if (!list.contains(str[i])) {
                list.add(str[i]);
            }
        }
        return list;
    }
    //接收广播数据
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        GetDate();
        JpushDate=intent.getStringExtra("Jpush");
        eleNum=JpushDate;
        //Log.e("Jpush",JpushDate);
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        mSocket.send(JsonToString(JpushDate,1,vkey));
    }
    //除掉重复召梯
    public  static List<String>  removeDuplicate(List<String> list)  {
        for  ( int  i  =0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j= list.size()- 1 ; j  >  i; j -- )  {
                if  (list.get(j).equals(list.get(i)))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }
    //防止快速点击
    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    public void show(String str)
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle(R.string.remind1);
        mBuilder.setMessage(str);
        mBuilder.setPositiveButton(R.string.cancel, null);
        mBuilder.create();
        if (!getActivity(mBuilder.getContext()).isFinishing()) {
            mBuilder.show();
        }
        // mBuilder.show();
    }
    //获取楼层
    //获取列表
    private void setFloor() {
        RequestParams params = new RequestParams();
        params.put("vkey",vkey);
        params.put("elevatorNum",eleNum);
        HttpGetData.post(MainActivity.this,
                //MacrosConfig.BaseUrl + "/MontServer/LoginServlet", params,
                MacrosConfig.BaseUrl +"/hpmt/showFloor.mvc", params,
                getResources().getString(R.string.loading),
                new HttpGetData.getDataCallBack() {
                    @Override
                    public void succcess(String res) {//这里面可以看到lists的值
                        // TODO Auto-generated method stub
                        Log.e("res", res);//打印错误信息
                        try {
                            floorlists.clear();
                            JSONObject object = new JSONObject(res);
//                            String code = JsonUtils.getJSONString(object,
//                                    "content");
                            // JSONObject content = object.getJSONObject("content");
                            JSONArray Ele = object.getJSONArray("floor");
                            for (int i = 0; i < Ele.length(); i++) {
                                floorlists.add(Ele.get(i).toString());

                            }


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void fail(String error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(MainActivity.this, error,
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }

    //显示
    private void showPopupWindow(){
        //获取自定义菜单的布局文件
        final View popupWindow_view = getLayoutInflater().inflate(R.layout.menu_layout,null,false);
        //创建popupWindow，设置宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
        //内部控件的点击事件
        scanHardware =(Button) popupWindow_view.findViewById(R.id.scanHardware);
        scanHardware.setOnClickListener(this);
        elemanage =(Button) popupWindow_view.findViewById(R.id.elemanage);
        elemanage.setOnClickListener(this);
        //设置wifi
        wifisetting =(Button) popupWindow_view.findViewById(R.id.wifisetting);
        wifisetting.setOnClickListener(this);
        //设置菜单的显示位置
        popupWindow.showAsDropDown(sao,0,10);
        //兼容5.0点击其他位置隐藏popupWindow
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //必须写 v.performClick();解决与单击事件的冲突
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //如果菜单不为空，且菜单正在显示
                        if(popupWindow!=null&&popupWindow.isShowing()){
                            popupWindow.dismiss();//隐藏菜单
                            popupWindow = null;//初始化菜单
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        view.performClick();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

//        @Override
//    protected void onRestart() {
//        super.onRestart();
//        Intent intent = getIntent();
//        overridePendingTransition(0, 0);
//       //
//        finish();
//        overridePendingTransition(0, 0);
//        eleNum="";
//        startActivity(intent);
//    }


    //接受返回值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        //电梯管理
        if (resultCode == 16) {
            // Intent intent = getIntent();
            //  overridePendingTransition(0, 0);
            eleNum = "";
            popupWindow.dismiss();
            ele_id.setText(eleNum);//设置电梯楼层
            apptitle.setText("");//设置在线离线
            txtVi_wallFloor.setText("");//设置当前楼层
            //设置内召按钮
            closeDoor();//设置开关门
            rightData.clear();
            et_filter.setText("");
            right_gView.setVisibility(View.INVISIBLE);
            imgVi_wallArrow.setVisibility(View.INVISIBLE);//设置箭头
            //锁梯
            txtVi_tap0.setText(getResources().getString( R.string.control_examine));
            imgVi_tap0.setImageResource(R.drawable.ic_home_diantimoshi_wuxiao);
//故障
            txtVi_tap1.setText(getResources().getString( R.string.elevator_falult));
            imgVi_tap1.setImageResource(R.drawable.ic_home_guzhang_wuxiao);
//超载
            txtVi_tap2.setText(getResources().getString(
                    R.string.control_overload));
            imgVi_tap2
                    .setImageResource(R.drawable.ic_home_chaozai_wuxiao);
//门锁
            txtVi_tap3.setText(getResources().getString(
                    R.string.door_lock));
            imgVi_tap3
                    .setImageResource(R.drawable.ic_home_mensuo_wuxiao);
//平层
            imgVi_tap4
                    .setImageResource(R.drawable.ic_home_pingceng_wuxiao);
            mSocket.cancel();

            if(IsNetWorkConnected.isNetWorkConnected(MainActivity.this) && IsNetWorkConnected.isConnByHttp()) {
                //   finish();
                //  overridePendingTransition(0, 0);
                lists.clear();
                Orlist.clear();
                GetDate();
            }

            // mDrawerLayout.openDrawer(Gravity.LEFT);
            //   startActivity(intent);

        }
        //智能硬件
        else if(resultCode == 15)
        {
            popupWindow.dismiss();
        }

        //故障记录
        else if(resultCode == 18)
        {
             popupWindow.dismiss();
        }

    }


    private Activity getActivity(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }else
            return null;
    }
    //显示当前楼层
    public String floorNow(int i) {
        String now="";
        if(i>0) {
            now=floorlists.get(i-1);
        }
        return now;
    }


   private synchronized void start() {
        //加锁并判断当前msocket是否可用
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
                .build();

//        Request request = new Request.Builder().url("ws://hpmontserver.com:3388/SpringIot/ws.mvc").build();
        Request request = new Request.Builder().url(MacrosConfig.wsUrl).build();
        EchoWebSocketListener socketListener = new EchoWebSocketListener();
        mOkHttpClient.newWebSocket(request, socketListener);
        mOkHttpClient.dispatcher().executorService().shutdown();

    }



    private final class EchoWebSocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            mSocket = webSocket;
            Log.e("soopen","连接成功！");
            if(eleNum.length()>0)
            {
                mSocket.send(JsonToString(eleNum, 1, vkey));
            }

        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            Log.e("soonMessage","接收");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            Log.e("soonMessage","心跳接收");
            Log.e("soonMessage",text);
            try {
                JSONObject object = new JSONObject(text);
                JSONArray Ele = object.getJSONArray("obj");
                JSONObject value = Ele.getJSONObject(0);
                if (value.has("onlineSts")) {
                    Identification_Number = value.getString("Identification_Number");
                    Car_Direction = value.getString("Car_Direction");
                    Car_Position = floorNow(Integer.parseInt(value.getString("Car_Position")));
                    Car_Status = value.getString("Car_Status");
                    Door_Zone = value.getString("Door_Zone");
                    Door_status = value.getString("Door_status");
                    Door_lock = value.getString("Service_Lock");
                    faultCode = value.getString("faultCode");
                    faultSts = value.getString("faultSts");
                    insSts = value.getString("insSts");
                    onlineSts = value.getString("onlineSts");
                    Overload = value.getString("Overload");
                    lockSts = value.getString("lockSts");
                    allFloor = value.getString("allFloor");
                    inCall = value.getJSONArray("inCall");
                    RightList.clear();

                    if (inCall.length() > 0) {
                        for (int j = 0; j < inCall.length(); j++) {
                            String y = inCall.getString(j);
                            RightList.add(y);
                        }
                    }
                    Service_Mode = value.getString("Service_Mode");
                } else {
                    Identification_Number = value.getString("Identification_Number");
                    onlineSts = "";
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
            }
            if (eleNum.equals(Identification_Number)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ele_id.setText(eleNum);
                        btn_open.setVisibility(View.VISIBLE);
                        btn_close.setVisibility(View.VISIBLE);
                        right_gView.setVisibility(View.VISIBLE);
                      //  right_gView.setVisibility(View.VISIBLE);
                        if (onlineSts.equals("0") || onlineSts.equals("1")) {
                            //显示在线离线
                            txtVi_wallFloor.setVisibility(View.VISIBLE);
                            txtVi_wallFloor.setText(Car_Position);
                            if (onlineSts.equals("1")) {
                                apptitle.setText(getResources().getString(
                                        R.string.online));
                                apptitle.setTextColor(Color.parseColor("white"));
                                if (Car_Direction.equals("0")) {
                                    imgVi_wallArrow.setVisibility(View.INVISIBLE);
                                } else if (Car_Direction.equals("1")) {
                                    upAnimation();
                                } else if (Car_Direction.equals("2")) {
                                    downAnimation();
                                } else {
                                    imgVi_wallArrow.setVisibility(View.INVISIBLE);
                                }

                            } else {
                                apptitle.setText(getResources().getString(
                                        R.string.offline));
                                apptitle.setTextColor(Color.parseColor("black"));
                                imgVi_wallArrow.setVisibility(View.INVISIBLE);

                            }
//
                            //设置内外梯显示
                            LeleNum(allFloor, RightList, Identification_Number, 1);
                            //设置开门关门
                            if (Door_status.equals("0") && onlineSts.equals("1")) {
                                openDoor();
                            } else {
                                closeDoor();
                            }

                            //电梯故障
                            if (faultSts.equals("1")) {
                                txtVi_tap1.setText(getResources().getString(
                                        R.string.elevator_falult));
                                imgVi_tap1
                                        .setImageResource(R.drawable.ic_home_guzhang_youxiao);
                            } else {
                                imgVi_tap1
                                        .setImageResource(R.drawable.ic_home_guzhang_wuxiao);
                            }
                            //运行模式
                            if (lockSts.equals("1")) {
                                // 锁梯
                                txtVi_tap0.setText(getResources().getString(
                                        R.string.locked));
                                imgVi_tap0
                                        .setImageResource(R.drawable.ic_control_anquanbihe);
                            } else if (Service_Mode.equals("0")) {
                                // 自动
                                txtVi_tap0.setText(getResources().getString(
                                        R.string.control_automatic));
                                imgVi_tap0
                                        .setImageResource(R.drawable.ic_home_zidong);
                            } else if (Service_Mode.equals("1")) {
                                // 检修
                                txtVi_tap0.setText(getResources().getString(
                                        R.string.control_examine));
                                imgVi_tap0
                                        .setImageResource(R.drawable.ic_home_jianxiu);
                            } else if (Service_Mode.equals("3")) {
                                // 井道自学习
                                txtVi_tap0.setText(getResources().getString(
                                        R.string.control_selflearning));
                                imgVi_tap0
                                        .setImageResource(R.drawable.ic_home_jianxiu);
                            } else if (Service_Mode.equals("9")) {
                                // vip
                                txtVi_tap0.setText(getResources().getString(
                                        R.string.vipyunxing));
                                imgVi_tap0
                                        .setImageResource(R.drawable.otheryunxing);
                            } else if (Service_Mode.equals("4")) {
                                // 消防反基站
                                txtVi_tap0.setText(getResources().getString(
                                        R.string.control_firefighting_station));
                                imgVi_tap0
                                        .setImageResource(R.drawable.ic_control_xiaofang);
                            } else if (Service_Mode.equals("5")) {
                                // 消防员模式
                                txtVi_tap0.setText(getResources().getString(
                                        R.string.control_firefighting_model));
                                imgVi_tap0
                                        .setImageResource(R.drawable.ic_home_xiaofang);
                            } else if (Service_Mode.equals("6")) {
                                // 司机模式
                                txtVi_tap0.setText(getResources().getString(
                                        R.string.control_driver));
                                imgVi_tap0
                                        .setImageResource(R.drawable.ic_home_siji);
                            }
                            //其他三种模式
                            //独立运行
                            else if (Service_Mode.equals("7")) {

                                txtVi_tap0.setText(getResources().getString(
                                        R.string.duli));
                                imgVi_tap0
                                        .setImageResource(R.drawable.otheryunxing);
                            }
                            //返平层运行
                            else if (Service_Mode.equals("8")) {
                                txtVi_tap0.setText(getResources().getString(
                                        R.string.fanpingceng));
                                imgVi_tap0
                                        .setImageResource(R.drawable.otheryunxing);
                            }

                            //应急运行
                            else if (Service_Mode.equals("2")) {

                                txtVi_tap0.setText(getResources().getString(
                                        R.string.yingji));
                                imgVi_tap0
                                        .setImageResource(R.drawable.otheryunxing);
                            }



                            else {
                                // 运行
                                txtVi_tap0.setText(getResources().getString(
                                        R.string.control_runing));
                                imgVi_tap0
                                        .setImageResource(R.drawable.ic_home_diantimoshi_wuxiao);
                            }

                            //超载
                            if (Overload.equals("2")) {
                                txtVi_tap2.setText(getResources().getString(
                                        R.string.control_overload));
                                imgVi_tap2
                                        .setImageResource(R.drawable.ic_home_chaozai_youxiao);
                            } else {
                                txtVi_tap2.setText(getResources().getString(
                                        R.string.control_overload));
                                imgVi_tap2
                                        .setImageResource(R.drawable.ic_home_chaozai_wuxiao);
                            }
                            //门锁
                            if (Door_lock.equals("1")) {
                                txtVi_tap3.setText(getResources().getString(
                                        R.string.door_lock));
                                imgVi_tap3
                                        .setImageResource(R.drawable.ic_home_mensuo_youxiao);
                            } else {
                                imgVi_tap3
                                        .setImageResource(R.drawable.ic_home_mensuo_wuxiao);
                            }
                            //平层
                            if (Door_Zone.equals("1")) {
                                imgVi_tap4
                                        .setImageResource(R.drawable.ic_home_pingceng_youxiao);
                            } else {
                                imgVi_tap4
                                        .setImageResource(R.drawable.ic_home_pingceng_wuxiao);
                            }
                        }
                        // }
                        else {
                            apptitle.setText(getResources().getString(
                                    R.string.offline));
                            jiemianclear();
                        }
                    }
                });
            }
    }


        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
         //   mSocket.cancel();
            Log.e("sooclosed","已关闭");
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
           // mSocket.cancel();
            Log.e("sooclosing","关闭中");
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);

            // mSocket.send("重发");
           // mSocket.cancel();
            //start();
            Log.e("soofailure","失败");
        }
       //  mmHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);

    }


    public String JsonToString(String ele,double t,String vkey)
    {
        JSONObject json=new JSONObject();
        JSONArray array = new JSONArray();
        array.put(ele);
        try {
            json.put("Identification_Number", array);
            json.put("duration", t);
            json.put("vkey", vkey);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public void jiemianclear()
    {
        closeDoor();
        ele_id.setText(eleNum);
        apptitle.setTextColor(Color.parseColor("black"));
        List<String> arr = new ArrayList<>();
        LeleNum("0", arr, "0", 0);
        imgVi_wallArrow.setVisibility(View.INVISIBLE);
        txtVi_wallFloor.setVisibility(View.INVISIBLE);
        imgVi_tap1
                .setImageResource(R.drawable.ic_home_guzhang_wuxiao);
        txtVi_tap0.setText(getResources().getString(
                R.string.control_runing));
        imgVi_tap0
                .setImageResource(R.drawable.ic_home_diantimoshi_wuxiao);
        txtVi_tap2.setText(getResources().getString(
                R.string.control_overload));
        imgVi_tap2
                .setImageResource(R.drawable.ic_home_chaozai_wuxiao);
        imgVi_tap4
                .setImageResource(R.drawable.ic_home_pingceng_wuxiao);
        imgVi_tap3
                .setImageResource(R.drawable.ic_home_mensuo_wuxiao);
    }

}