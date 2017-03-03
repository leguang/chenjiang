package com.shtoone.chenjiang.mvp.view.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.event.EventData;
import com.shtoone.chenjiang.mvp.contract.base.BaseContract;
import com.shtoone.chenjiang.mvp.view.base.BaseActivity;
import com.shtoone.chenjiang.mvp.view.main.about.AboutFragment;
import com.shtoone.chenjiang.mvp.view.main.project.ProjectActivity;
import com.shtoone.chenjiang.mvp.view.main.setting.SettingFragment;
import com.shtoone.chenjiang.mvp.view.main.upload.UploadFragment;
import com.shtoone.chenjiang.mvp.view.others.LaunchActivity;
import com.shtoone.chenjiang.utils.SharedPreferencesUtils;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView tv_username;
    private TextView tv_phone_number;
    private LinearLayout llNavHeader;
    private ActionBarDrawerToggle toggle;
    private TextView tv_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_main_activity, MainFragment.newInstance());
        }
        initView();
        initData();
        revealShow();
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
        tv_header = (TextView) llNavHeader.findViewById(R.id.iv_header_nav_header_main);
        tv_username = (TextView) llNavHeader.findViewById(R.id.tv_username_nav_header_main);
        tv_phone_number = (TextView) llNavHeader.findViewById(R.id.tv_phone_number_nav_header_main);
    }

    private void initData() {
        if (BaseApplication.mUserInfoBean != null) {
            tv_username.setText("姓名：" + BaseApplication.mUserInfoBean.getUserFullName());
            tv_phone_number.setText("电话" + BaseApplication.mUserInfoBean.getUserPhoneNum());
            String strFamilyName = BaseApplication.mUserInfoBean.getUserFullName().substring(0, 1);
            tv_header.setText(strFamilyName);
        }
        navigationView.setNavigationItemSelectedListener(this);
//        llNavHeader.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawer.closeDrawer(GravityCompat.START);
//                drawer.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }, 250);
//            }
//        });
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

                    new AlertDialog.Builder(MainActivity.this)
                            .setIcon(R.drawable.ic_error_outline_red_400_48dp)
                            .setTitle(R.string.dialog_title_relogin)
                            .setMessage(R.string.dialog_content_relogin)
                            .setNegativeButton(R.string.dialog_negativeText, null)
                            .setPositiveButton(R.string.dialog_positiveText, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    go2Login();
                                }
                            })
                            .show();

                } else if (id == R.id.about) {
                    start(AboutFragment.newInstance());

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
        }, 300);
        return true;
    }

    private void go2Login() {
        //用户名和密码设为空，这样在点击重新登录后，即使没有登录，下次启动由于闪屏页的验证是需要用户名和密码，所以跳转到登录页面。
        SharedPreferencesUtils.put(BaseApplication.mContext, Constants.USERNAME, "");
        SharedPreferencesUtils.put(BaseApplication.mContext, Constants.PASSWORD, "");

        Intent mIntent = new Intent(this, LaunchActivity.class);
        //目的是为了通知LaunchActivity此时应该启动loginfragment，而不是闪屏页。
        mIntent.putExtra(Constants.FROM_TO, Constants.FROM_MAIN);
        startActivity(mIntent);
        finish();
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

    private void revealShow() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawer.setVisibility(View.VISIBLE);
            return;
        }
        drawer.post(new Runnable() {
            @Override
            public void run() {

                int cx = (drawer.getLeft() + drawer.getRight()) / 2;
                int cy = (drawer.getTop() + drawer.getBottom()) / 2;

                int w = drawer.getWidth();
                int h = drawer.getHeight();

                // 勾股定理 & 进一法
                int finalRadius = (int) Math.hypot(w, h);

                Animator anim = ViewAnimationUtils.createCircularReveal(drawer, cx, cy, 0, finalRadius);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        drawer.setVisibility(View.VISIBLE);
                        EventBus.getDefault().postSticky(new EventData(Constants.EVENT_FINISH_LAUNCH));

                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        drawer.setVisibility(View.VISIBLE);
                    }
                });
                anim.setInterpolator(new AccelerateDecelerateInterpolator());
                anim.setDuration(1000);
                anim.start();
            }
        });
    }
}
