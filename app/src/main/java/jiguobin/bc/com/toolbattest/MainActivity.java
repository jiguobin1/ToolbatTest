package jiguobin.bc.com.toolbattest;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import jiguobin.bc.com.toolbattest.activity.GUIActivity;
import jiguobin.bc.com.toolbattest.fragment.Fragment1;
import jiguobin.bc.com.toolbattest.fragment.Fragment2;
import jiguobin.bc.com.toolbattest.fragment.Fragment3;
import jiguobin.bc.com.toolbattest.fragment.Fragment4;
import jiguobin.bc.com.toolbattest.fragment.ImageFragment;
import jiguobin.bc.com.toolbattest.fragment.NewsFragment;
import jiguobin.bc.com.toolbattest.fragment.WeatherFragemnt;


public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Context mContext;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ListView lv;
    private  ArrayList<String> arrayList;
    private FragmentManager fm;
    private FragmentTransaction ft;
    Fragment1 f1;
    Fragment2 f2;
    Fragment3 f3;
    Fragment4 f4;
    NewsFragment f5;
    ImageFragment imageFragment;
    WeatherFragemnt weatherFragemnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        menujianting();
        NavigationIcon();
        drawerLayoutmenujianting();
        fm=getSupportFragmentManager();
        ft=fm.beginTransaction();
        f1=new Fragment1();
        f2=new Fragment2();
        f3=new Fragment3();
        f4=new Fragment4();
        f5=new NewsFragment();
        imageFragment=new ImageFragment();
        weatherFragemnt =new WeatherFragemnt();
        ft.add(R.id.rongqi,f1);
        ft.add(R.id.rongqi,f2);
        ft.add(R.id.rongqi,f3);
        ft.add(R.id.rongqi,f4);
        ft.add(R.id.rongqi,f5);
        ft.add(R.id.rongqi,imageFragment);
        ft.add(R.id.rongqi, weatherFragemnt);
        ft.show(f5);
        ft.hide(f1);
        ft.hide(f2);
        ft.hide(f3);
        ft.hide(f4);
        ft.hide(imageFragment);
        ft.hide(weatherFragemnt);
        ft.commit();
    }

    private void init() { //初始化
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mContext=this;
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        mToolbar.setNavigationIcon(R.drawable.ic_portrait_white_24dp);
        mToolbar.inflateMenu(R.menu.toolbar); //关联menu文件
        navigationView= (NavigationView) findViewById(R.id.navigationView);
        lv= (ListView) findViewById(R.id.listView);
        //initListView();
   }


    //toolbar menu的监听
    public void menujianting(){
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId){
                    case R.id.fenxiang:
                       // Toast.makeText(mContext,"您点击了分享",Toast.LENGTH_SHORT).show();
                        showShare();
                        break;
                    case R.id.shezhi:
                        startActivity(new Intent(mContext, GUIActivity.class));
                        //SMSSD();官网提供的注册界面
                       // Toast.makeText(mContext,"您点击了设置",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tuichu:
                        Toast.makeText(mContext,"您点击了退出",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }
    //导航图标的监听 打开侧滑
    public void NavigationIcon(){
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }
    //侧滑menu的监听
    public void drawerLayoutmenujianting(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId){
                    case R.id.view1:
                        Toast.makeText(mContext,"您点击了新闻",Toast.LENGTH_SHORT).show();
                        ft=fm.beginTransaction();
                        ft.show(f5);
                        ft.hide(f1);
                        ft.hide(f3);
                        ft.hide(f4);
                        ft.hide(f2);
                        ft.hide(imageFragment);
                        ft.hide(weatherFragemnt);
                        ft.commit();
                        break;
                    case R.id.view2:
                        ft=fm.beginTransaction();
                        ft.show(imageFragment);
                        ft.hide(f2);
                        ft.hide(f1);
                        ft.hide(f4);
                        ft.hide(f5);
                        ft.hide(f3);
                        ft.hide(weatherFragemnt);
                        ft.commit();
                        break;
                    case R.id.view3:
                        Toast.makeText(mContext,"您点击了设置",Toast.LENGTH_SHORT).show();
                        ft=fm.beginTransaction();
                        ft.show(weatherFragemnt);
                        ft.hide(f2);
                        ft.hide(f3);
                        ft.hide(f1);
                        ft.hide(f5);
                        ft.hide(f4);
                        ft.hide(imageFragment);
                        ft.commit();
                        break;
                }
                //点击菜单元素退出侧滑菜单栏
                drawerLayout.closeDrawer(Gravity.LEFT);
                //点击某条目改为选中状态
                item.setChecked(true);
                return false;
            }
        });
    }
    //界面的数据列表显示
    public void initListView(){
        arrayList = new ArrayList<>();
        for(int i=0;i<100;i++){
            arrayList.add("這是第"+i+"個條目");
            lv.setAdapter(new MyAdapter(MainActivity.this,arrayList));
        }
    }
    class MyAdapter extends BaseAdapter{
        private List<String> arrayList;
        private  Context content;
        public MyAdapter(Context content,List<String> arrayList){
            this.content=content;
            this.arrayList=arrayList;

        }
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return arrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view= View.inflate(content,R.layout.lv_item,null);
            TextView tv=(TextView)view.findViewById(R.id.tv);
            tv.setText(arrayList.get(i));
            return view;
        }
    }
    //分享
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    //短信验证
    public void SMSSD(){
    //打开注册页面
    RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
// 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

// 提交用户信息（此方法可以不调用）
                    //registerUser(country, phone);
                }
            }
        });
        registerPage.show(mContext);
}

}
