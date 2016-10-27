package com.shtoone.chenjiang.mvp.view.main;

import android.os.Bundle;
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

import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.mvp.view.base.BaseActivity;
import com.shtoone.chenjiang.utils.AnimationUtils;
import com.socks.library.KLog;

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

    private void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        llNavHeader = (LinearLayout) navigationView.getHeaderView(0);
        tv_username = (TextView) llNavHeader.findViewById(R.id.tv_username_nav_header_main);
        tv_phone_number = (TextView) llNavHeader.findViewById(R.id.tv_phone_number_nav_header_main);
    }

    private void initData() {
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
    public void initPresenter() {

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
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {

        } else if (id == R.id.update) {

        } else if (id == R.id.download) {

        } else if (id == R.id.setting) {
            KLog.e("11111111");
            SettingFragment fragment = findFragment(SettingFragment.class);
            if (fragment == null) {
                KLog.e("222222");

                popTo(MainFragment.class, false, new Runnable() {
                    @Override
                    public void run() {
                        KLog.e("333333");

                        start(SettingFragment.newInstance());
                    }
                });
            } else {
                // 如果已经在栈内,则以SingleTask模式start
                start(fragment, SupportFragment.SINGLETASK);
                KLog.e("44444");

            }

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
