package com.shtoone.chenjiang.mvp.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shtoone.chenjiang.R;
import com.shtoone.chenjiang.common.AudioPlayer;
import com.shtoone.chenjiang.common.Constants;
import com.shtoone.chenjiang.common.Formula;
import com.shtoone.chenjiang.mvp.model.entity.db.CezhanData;
import com.shtoone.chenjiang.mvp.model.entity.db.YusheshuizhunxianData;
import com.socks.library.KLog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class MeasureRVPAdapter extends BaseQuickAdapter<CezhanData, BaseViewHolder> {
    private static final String TAG = MeasureRVPAdapter.class.getSimpleName();
    private int currentPosition = 0;
    private int measureIndex = 0;
    private int[] arrayBFFB = {0, Constants.b1, Constants.f1, Constants.f2, Constants.b2};
    private int[] arrayFBBF = {0, Constants.f1, Constants.b1, Constants.b2, Constants.f2};
    private int[] arrayAudioFBBF = {0, Constants.AUDIO_NEXTB, Constants.AUDIO_NEXTB, Constants.AUDIO_NEXTF, 0};
    private int[] arrayAudioBFFB = {0, Constants.AUDIO_NEXTF, Constants.AUDIO_NEXTF, Constants.AUDIO_NEXTB, 0};//对于这个0是否会报错，后面再看
    int[] arraySequence = {0};
    int[] arrayAudio = {0};
    private SimpleDateFormat df;

    public MeasureRVPAdapter() {
        super(R.layout.item_rvp_measure_right_fragment, null);
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    protected void convert(BaseViewHolder holder, CezhanData mCezhanData) {
        int position = holder.getLayoutPosition() - this.getHeaderLayoutCount();
        //设置数据
        holder.setText(R.id.tv_number_item_rvp_measure_right_fragment, mCezhanData.getNumber() + "")
                .setText(R.id.tv_measure_direction_item_rvp_measure_right_fragment, mCezhanData.getMeasureDirection())
                .setText(R.id.tv_qianshi_item_rvp_measure_right_fragment, "前视:" + mCezhanData.getQianshi())
                .setText(R.id.tv_houshi_item_rvp_measure_right_fragment, "后视:" + mCezhanData.getHoushi())
                .setText(R.id.tv_observe_type_item_rvp_measure_right_fragment, mCezhanData.getObserveType())
                .setText(R.id.tv_b1hd, mCezhanData.getB1hd())
                .setText(R.id.tv_houshijuhe, mCezhanData.getHoushijuhe())
                .setText(R.id.tv_b2hd, mCezhanData.getB2hd())
                .setText(R.id.tv_qianshijuhe, mCezhanData.getQianshijuhe())
                .setText(R.id.tv_f1hd, mCezhanData.getF1hd())
                .setText(R.id.tv_gaocha1, mCezhanData.getGaocha1())
                .setText(R.id.tv_f2hd, mCezhanData.getF2hd())
                .setText(R.id.tv_gaocha2, mCezhanData.getGaocha2())
                .setText(R.id.tv_b1r, mCezhanData.getB1r())
                .setText(R.id.tv_cezhangaocha, mCezhanData.getCezhangaocha())
                .setText(R.id.tv_b2r, mCezhanData.getB2r())
                .setText(R.id.tv_gaochengzhi, mCezhanData.getGaochengzhi())
                .setText(R.id.tv_f1r, mCezhanData.getF1r())
                .setText(R.id.tv_frdushucha, mCezhanData.getFrdushucha())
                .setText(R.id.tv_f2r, mCezhanData.getF2r())
                .setText(R.id.tv_brdushucha, mCezhanData.getBrdushucha())
                //此处要还原样式，因为View会进行复用，会出现后面的item明明没有设置背景变色也跟着前面的变色了。
                .setBackgroundRes(R.id.tv_b1hd, R.drawable.rect_bg_stroke_table)
                .setBackgroundRes(R.id.tv_b2hd, R.drawable.rect_bg_stroke_table)
                .setBackgroundRes(R.id.tv_f1hd, R.drawable.rect_bg_stroke_table)
                .setBackgroundRes(R.id.tv_f2hd, R.drawable.rect_bg_stroke_table)
                .setBackgroundRes(R.id.tv_b1r, R.drawable.rect_bg_stroke_table)
                .setBackgroundRes(R.id.tv_b2r, R.drawable.rect_bg_stroke_table)
                .setBackgroundRes(R.id.tv_f1r, R.drawable.rect_bg_stroke_table)
                .setBackgroundRes(R.id.tv_f2r, R.drawable.rect_bg_stroke_table);
        //设置样式
        if (currentPosition == position) {

            switch (arraySequence[measureIndex]) {
                case Constants.b1:
                    holder.setBackgroundRes(R.id.tv_b1hd, R.drawable.rect_bg_red_stroke_table)
                            .setBackgroundRes(R.id.tv_b1r, R.drawable.rect_bg_red_stroke_table);
                    break;
                case Constants.b2:
                    holder.setBackgroundRes(R.id.tv_b2hd, R.drawable.rect_bg_red_stroke_table)
                            .setBackgroundRes(R.id.tv_b2r, R.drawable.rect_bg_red_stroke_table);
                    break;
                case Constants.f1:
                    holder.setBackgroundRes(R.id.tv_f1hd, R.drawable.rect_bg_red_stroke_table)
                            .setBackgroundRes(R.id.tv_f1r, R.drawable.rect_bg_red_stroke_table);
                    break;
                case Constants.f2:
                    holder.setBackgroundRes(R.id.tv_f2hd, R.drawable.rect_bg_red_stroke_table)
                            .setBackgroundRes(R.id.tv_f2r, R.drawable.rect_bg_red_stroke_table);
                    break;
            }
        }
    }

    public void measure(int currentPosition, int measureIndex, String result) {
        if (currentPosition > mData.size() - 1) {
            return;
        }
        KLog.e("currentPosition::" + currentPosition);
        KLog.e("measureIndex::" + measureIndex);
        this.currentPosition = currentPosition;
        this.measureIndex = measureIndex;
        CezhanData mCezhanData = mData.get(currentPosition);

        if (mCezhanData.getObserveType().equals(Constants.BFFB)) {
            //偶数站
            arraySequence = arrayBFFB;
            arrayAudio = arrayAudioBFFB;
        } else {
            //奇数站
            arraySequence = arrayFBBF;
            arrayAudio = arrayAudioFBBF;
        }

        switch (arraySequence[measureIndex]) {
            case Constants.b1:
                mCezhanData.setB1hd(result);
                mCezhanData.setB1r(result);
                mCezhanData.setB1time(df.format(Calendar.getInstance().getTime()));
                break;
            case Constants.b2:
                mCezhanData.setB2hd(result);
                mCezhanData.setB2r(result);
                mCezhanData.setB2time(df.format(Calendar.getInstance().getTime()));
                break;
            case Constants.f1:
                mCezhanData.setF1hd(result);
                mCezhanData.setF1r(result);
                mCezhanData.setF1time(df.format(Calendar.getInstance().getTime()));
                break;
            case Constants.f2:
                mCezhanData.setF2hd(result);
                mCezhanData.setF2r(result);
                mCezhanData.setF2time(df.format(Calendar.getInstance().getTime()));
                break;
        }
        //声音的另一种实现,把这个封装起来，方便在计算的时候调用
        AudioPlayer.play(arrayAudio[measureIndex]);
        //到第四次获取数据时进行一系列运算。
        if (measureIndex == 4) {
            Formula mFormula = Formula.getInstance(mCezhanData);
            mCezhanData.setGaocha1(mFormula.gaocha1());
            mCezhanData.setGaocha2(mFormula.gaocha2());
            mCezhanData.setCezhangaocha(mFormula.cezhangaocha());
            mCezhanData.setFrdushucha(mFormula.frdushucha());
            mCezhanData.setBrdushucha(mFormula.brdushucha());
            float houshijuhe;
            float qianshijuhe;
            if (currentPosition == 0) {
                houshijuhe = 0;
                qianshijuhe = 0;
            } else {
                CezhanData preCezhanData = mData.get(currentPosition - 1);
                houshijuhe = Float.parseFloat(preCezhanData.getHoushijuhe());
                qianshijuhe = Float.parseFloat(preCezhanData.getQianshijuhe());
            }
            mCezhanData.setHoushijuhe(mFormula.houshijuhe(houshijuhe));
            mCezhanData.setQianshijuhe(mFormula.qianshijuhe(qianshijuhe));

//            mCezhanData.setGaocha1(mFormula.gaocha1());
        }

        notifyDataSetChanged();
    }

    public void chongce(YusheshuizhunxianData mYusheshuizhunxianData) {
        this.currentPosition = mYusheshuizhunxianData.getMeasurePosition();
        mYusheshuizhunxianData.setMeasureIndex(0);
        this.measureIndex = 0;
        CezhanData mCezhanData = mData.get(currentPosition);
        mCezhanData.clean();
        notifyDataSetChanged();
    }

    public void addZhuandian(YusheshuizhunxianData mYusheshuizhunxianData) {
        this.currentPosition = mYusheshuizhunxianData.getMeasurePosition();
        mYusheshuizhunxianData.setMeasureIndex(0);
        this.measureIndex = 0;

        KLog.e("currentPosition::" + currentPosition);

        //首先清除当前站并修改当前测站的相关值，目的是为了保证当前站被测量过的数据清除后重新开始测量。
        CezhanData currentCezhanData = mData.get(currentPosition);
        currentCezhanData.clean();

        int zhuandianIndex = mYusheshuizhunxianData.getZhuandianIndex() + 1;
        mYusheshuizhunxianData.setZhuandianIndex(zhuandianIndex);
        String zhuandianName = "ZD" + zhuandianIndex;

        CezhanData tempCezhan = null;
        //将后面的测站依次推后一位。
        for (int i = (currentPosition + 1); i < mData.size(); i++) {
            tempCezhan = mData.get(i);
            tempCezhan.setNumber(tempCezhan.getNumber() + 1);

            if (tempCezhan.getObserveType().equals(Constants.BFFB)) {
                tempCezhan.setObserveType(Constants.FBBF);
            } else {
                tempCezhan.setObserveType(Constants.BFFB);
            }
        }

        CezhanData zhuandianCezhan = new CezhanData();
        zhuandianCezhan.setNumber(currentPosition + 2);
        zhuandianCezhan.setShuizhunxianID(mYusheshuizhunxianData.getId());
        zhuandianCezhan.setMeasureDirection(currentCezhanData.getMeasureDirection());
        //其实不是这样的，应该根据水准线的观测类型，还要根据奇数站和偶数站的时候不同的前后后前的顺序不一样就会显示不一样,后期再处理。
        if (zhuandianCezhan.getNumber() % 2 == 0) {
            zhuandianCezhan.setObserveType(Constants.FBBF);
        } else {
            zhuandianCezhan.setObserveType(Constants.BFFB);
        }
        zhuandianCezhan.setQianshi(currentCezhanData.getQianshi());
        zhuandianCezhan.setHoushi(zhuandianName);
        mData.add(currentPosition + 1, zhuandianCezhan);
        currentCezhanData.setQianshi(zhuandianName);
        notifyDataSetChanged();
    }
}
