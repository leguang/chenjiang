package com.shtoone.chenjiang.mvp.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.view.base.BaseActivity;
import com.shtoone.chenjiang.mvp.view.main.project.ProjectActivity;
import com.shtoone.chenjiang.utils.AnimationUtils;

import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView tv_username;
    private TextView tv_phone_number;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private LinearLayout llNavHeader;
    private ActionBarDrawerToggle toggle;
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_main_activity, MainFragment.newInstance());
        }
        initView();
        initData();
        drawer.post(new Runnable() {
            @Override
            public void run() {
                AnimationUtils.show(drawer, 0, 3000);

            }
        });
    }

    @NonNull
    @Override
    protected BaseContract.Presenter createPresenter() {
        return null;
    }


    private void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        llNavHeader = (LinearLayout) navigationView.getHeaderView(0);
        tv_username = (TextView) llNavHeader.findViewById(R.id.tv_username_nav_header_main);
        tv_phone_number = (TextView) llNavHeader.findViewById(R.id.tv_phone_number_nav_header_main);
    }

    private void initData() {
        if (BaseApplication.mUserInfoBean != null) {
            tv_username.setText("姓名：" + BaseApplication.mUserInfoBean.getUserFullName());
            tv_phone_number.setText("电话" + BaseApplication.mUserInfoBean.getUserPhoneNum());
        }
        navigationView.setNavigationItemSelectedListener(this);
        llNavHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                drawer.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 250);
            }
        });
    }

    public void initToolBar(Toolbar toolbar) {
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        //延迟启动Fragment，先让侧滑控件关闭，避免看上去卡顿一下。
        drawer.postDelayed(new Runnable() {
            @Override
            public void run() {
                int id = item.getItemId();
                if (id == R.id.search) {
                    go2Project();
                } else if (id == R.id.update) {
                    start(UploadFragment.newInstance());
                } else if (id == R.id.download) {
                    start(DownloadFragment.newInstance());
                } else if (id == R.id.setting) {
                    SettingFragment fragment = findFragment(SettingFragment.class);
                    if (fragment == null) {

                        popTo(MainFragment.class, false, new Runnable() {
                            @Override
                            public void run() {

                                start(SettingFragment.newInstance());
                            }
                        });
                    } else {
                        // 如果已经在栈内,则以SingleTask模式start
                        start(fragment, SupportFragment.SINGLETASK);
                    }

                } else if (id == R.id.relogin) {

                } else if (id == R.id.about) {

                } else if (id == R.id.version) {
                    VersionFragment fragment = findFragment(VersionFragment.class);
                    if (fragment == null) {
                        popTo(MainFragment.class, false, new Runnable() {
                            @Override
                            public void run() {
                                start(VersionFragment.newInstance());
                            }
                        });
                    } else {
                        // 如果已经在栈内,则以SingleTask模式start
                        start(fragment, SupportFragment.SINGLETASK);
                    }
                }
            }
        }, 250);
        return true;
    }

    private void go2Project() {
        startActivity(new Intent(this, ProjectActivity.class));
    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }

    public void closeDrawer() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void openDrawer() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}
