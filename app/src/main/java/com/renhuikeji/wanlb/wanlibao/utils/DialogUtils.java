package com.renhuikeji.wanlb.wanlibao.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.activity.CashFlowActivity;
import com.renhuikeji.wanlb.wanlibao.activity.FinancialDetailActivity;
import com.renhuikeji.wanlb.wanlibao.activity.MainActivity;
import com.renhuikeji.wanlb.wanlibao.activity.MyFriendActivity;
import com.renhuikeji.wanlb.wanlibao.activity.SettledBillsActivity;
import com.renhuikeji.wanlb.wanlibao.activity.SignInActivity;
import com.renhuikeji.wanlb.wanlibao.activity.WebShowActivity;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.widget.SortTopDialog;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/22 0022.
 * http://blog.csdn.net/l_l_b/article/details/50518763
 */

public class DialogUtils {

    /**
     * 标题+内容+2个按钮（取消，确认）
     * 标题在上方左侧，有分割线
     */
    public static NormalDialog getNormalDialog(Context mcontext, String titleString, String content) {
        BounceTopEnter mBasIn = new BounceTopEnter();
        SlideBottomExit mBasOut = new SlideBottomExit();
        NormalDialog normalDialog = new NormalDialog(mcontext);
        //设置dialog点击空白处不消失
        normalDialog.setCanceledOnTouchOutside(false);
        normalDialog.title(titleString)
                .content(content)
                .showAnim(mBasIn)
                .dismissAnim(mBasOut);
        return normalDialog;
    }

    /**
     * 标题+内容+2个按钮（取消，确认）
     * 标题在上方中间，没有分割线
     * 内容在正中间
     */
    public static NormalDialog getDialogMiddle(Context mcontext, String titleString, String content) {
        BounceTopEnter mBasIn = new BounceTopEnter();
        SlideBottomExit mBasOut = new SlideBottomExit();
        NormalDialog normalDialog = new NormalDialog(mcontext);
        normalDialog.title(titleString).style(NormalDialog.STYLE_TWO)
                .content(content)
                .showAnim(mBasIn)
                .dismissAnim(mBasOut)
                .show();
        normalDialog.setCanceledOnTouchOutside(false);
        return normalDialog;
    }

    /**
     * 黑色背景
     * 是否退出
     * 取消 确定
     */
    public static NormalDialog getBlakeDialog(Context mcontext, String content) {
        BounceTopEnter mBasIn = new BounceTopEnter();
        SlideBottomExit mBasOut = new SlideBottomExit();
        NormalDialog normalDialog = new NormalDialog(mcontext);
        normalDialog.isTitleShow(false)//
                .bgColor(Color.parseColor("#383838"))//
                .cornerRadius(5)//
                .content(content)//
                .contentGravity(Gravity.CENTER)//
                .contentTextColor(Color.parseColor("#ffffff"))//
                .dividerColor(Color.parseColor("#222222"))//
                .btnTextSize(15.5f, 15.5f)//
                .btnTextColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"))//
                .btnPressColor(Color.parseColor("#2B2B2B"))//
                .widthScale(0.85f)//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        return normalDialog;
    }

    /**
     * 标题+内容+1个按钮（btn文字自己定）
     * 标题在上方左侧，有分割线
     * 内容在中间左侧
     */
    public static NormalDialog getNormalDialogOneBtn(Context mcontext, String titleString, String content, String btnStr) {
        BounceTopEnter mBasIn = new BounceTopEnter();
        SlideBottomExit mBasOut = new SlideBottomExit();
        NormalDialog normalDialog = new NormalDialog(mcontext);
        normalDialog.content(content)
                .title(titleString)
                .btnNum(1)
                .btnText(btnStr)//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        return normalDialog;
    }

    /**
     * 标题+内容+3个按钮（btn文字自己定）
     * 标题在上方中间，没有分割线
     * 内容在中间
     */
    public static NormalDialog getNormalDialogThreeBtn(Context mcontext, String titleString, String content) {
        BounceTopEnter mBasIn = new BounceTopEnter();
        SlideBottomExit mBasOut = new SlideBottomExit();
        NormalDialog normalDialog = new NormalDialog(mcontext);
        normalDialog.title(titleString)
                .content(content)//
                .style(NormalDialog.STYLE_TWO)//
                .btnNum(3)
                .btnText("取消", "确定", "继续逛逛")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        return normalDialog;
    }

    /**
     * 标题+内容+3个按钮（btn文字自己定）
     * 标题在上方中间，没有分割线
     * 内容在中间
     */
    public static NormalDialog getNormalDialogOneBtn(Context mcontext, String titleString, String content) {
        BounceTopEnter mBasIn = new BounceTopEnter();
        SlideBottomExit mBasOut = new SlideBottomExit();
        NormalDialog normalDialog = new NormalDialog(mcontext);
        normalDialog.title(titleString)
                .content(content)//
                .style(NormalDialog.STYLE_TWO)//
                .btnNum(1)
                .btnText("确定")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut);//
                //.show();
        return normalDialog;
    }

    /**
     * MD风格
     * 标题+内容+2个按钮
     * 标题在上方左侧，没有分割线
     * 内容在中间
     * 按钮在右下角
     */
    public static MaterialDialog getMaterialDialogTwoBtn(Context mcontext, String titleString, String content) {
        BounceTopEnter mBasIn = new BounceTopEnter();
        SlideBottomExit mBasOut = new SlideBottomExit();
        final MaterialDialog dialog = new MaterialDialog(mcontext);
        dialog.title(titleString)
                .content(content)
                .btnText("取消", "确定")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        return dialog;
    }

    /**
     * MD风格
     * 内容+3个按钮
     * 内容在中间
     * 按钮在右下角
     */
    public static MaterialDialog getMaterialDialogThreeBtns(Context mcontext, String content) {
        BounceTopEnter mBasIn = new BounceTopEnter();
        SlideBottomExit mBasOut = new SlideBottomExit();
        final MaterialDialog dialog = new MaterialDialog(mcontext);
        dialog.isTitleShow(false)//
                .btnNum(3)
                .content(content)//
                .btnText("确定", "取消", "知道了")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        return dialog;
    }

    /**
     * MD风格
     * 内容+1个按钮
     * 内容在中间
     * 按钮在右下角
     */
    public static MaterialDialog getMaterialDialogOneBtn(Context mcontext, String content) {
        BounceTopEnter mBasIn = new BounceTopEnter();
        SlideBottomExit mBasOut = new SlideBottomExit();
        final MaterialDialog dialog = new MaterialDialog(mcontext);
        dialog.btnNum(1)
                .content(content)//
                .btnText("确定")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut);
                //.show();
        return dialog;
    }
    /**
     * MD风格
     * 内容+1个按钮
     * 内容在中间
     * 按钮在右下角
     */
    public static MaterialDialog getMaterialDialogOneBtnJump(final Context mcontext, String content, final String type) {
        final Intent i=new Intent();
        i.setClass(mcontext, MainActivity.class);
        if(!TextUtils.isEmpty(type)){
            switch (type){
                case Constant.JUMP_ACCOUNT:
                    i.setClass(mcontext, CashFlowActivity.class);
                    break;
                case Constant.JUMP_ORDER:
                    i.setClass(mcontext, SettledBillsActivity.class);
                    break;
                case Constant.JUMP_FRIEND:
                    i.setClass(mcontext, MyFriendActivity.class);
                    break;
                case Constant.JUMP_RECOM:
                    i.putExtra("url", ConfigValue.INVITE_FRIENDS);
                    i.putExtra("title", "邀请好友");
                    i.putExtra("right_text", "");
                    i.setClass(mcontext, WebShowActivity.class);
                    break;
                case Constant.JUMP_CASH:
                    i.setClass(mcontext, FinancialDetailActivity.class);
                    break;
                case Constant.JUMP_SIGN:
                    i.setClass(mcontext, SignInActivity.class);
                    break;
            }
        }
        BounceTopEnter mBasIn = new BounceTopEnter();
        SlideBottomExit mBasOut = new SlideBottomExit();
        final MaterialDialog dialog = new MaterialDialog(mcontext);
        dialog.btnNum(1)
                .content(content)//
                .btnText("确定")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        if(!TextUtils.isEmpty(type)){
                            mcontext.startActivity(i);
                        }
                        dialog.dismiss();
                    }
                });
                //.show();
        return dialog;
    }

    /**
     * 内容是集合
     * 内容item在左侧,带图片
     */
    public static NormalListDialog getNormalListDialog(Context mcontext, String title) {
        BounceTopEnter mBasIn = new BounceTopEnter();
        SlideBottomExit mBasOut = new SlideBottomExit();

        ArrayList<DialogMenuItem> mMenuItems = new ArrayList<>();
        mMenuItems.add(new DialogMenuItem("收藏", R.mipmap.ic_launcher));
        mMenuItems.add(new DialogMenuItem("下载", R.mipmap.ic_launcher));
        mMenuItems.add(new DialogMenuItem("分享", R.mipmap.ic_launcher));
        mMenuItems.add(new DialogMenuItem("删除", R.mipmap.ic_launcher));
        mMenuItems.add(new DialogMenuItem("歌手", R.mipmap.ic_launcher));
        mMenuItems.add(new DialogMenuItem("专辑", R.mipmap.ic_launcher));

        final NormalListDialog dialog = new NormalListDialog(mcontext, mMenuItems);
        dialog.title(title)//
                .titleTextSize_SP(18)//
                .titleBgColor(Color.parseColor("#409ED7"))//
                .itemPressColor(Color.parseColor("#85D3EF"))//
                .itemTextColor(Color.parseColor("#303030"))//
                .itemTextSize(14)//
                .cornerRadius(0)//
                .widthScale(0.8f)//
                .show();
        return dialog;
    }


    /**
     * 内容是集合
     * 内容item在左侧,不带图片
     */
    public static NormalListDialog getNormalListDialogStringArr(Context mcontext, String title) {
        BounceTopEnter mBasIn = new BounceTopEnter();
        SlideBottomExit mBasOut = new SlideBottomExit();

        String[] mStringItems = {"收藏", "下载", "分享", "删除", "歌手", "专辑"};
        final NormalListDialog dialog = new NormalListDialog(mcontext, mStringItems);
//        dialog.title(title)//
//                .layoutAnimation(null)
//                .show();


        dialog.title(title)//
                .titleTextSize_SP(18)//
                .titleBgColor(Color.parseColor("#409ED7"))//
                .itemPressColor(Color.parseColor("#85D3EF"))//
                .itemTextColor(Color.parseColor("#303030"))//
                .itemTextSize(14)//
                .cornerRadius(0)//
                .widthScale(0.8f)//
                .show();
        return dialog;
    }

    /**
     * MD风格
     * bottomsheet
     * 内容在中间
     */
    public static ActionSheetDialog getBottomSheetDialog(Context mcontext, String title, String[] stringItems) {
        BounceTopEnter mBasIn = new BounceTopEnter();
        SlideBottomExit mBasOut = new SlideBottomExit();

//        String[] stringItems = {"接收消息并提醒", "接收消息但不提醒", "收进群助手且不提醒", "屏蔽群消息"};
//        ActionSheetDialog dialog = new ActionSheetDialog(mcontext, stringItems, null);
//        dialog.title("选择群消息提醒方式\r\n(该群在电脑的设置:接收消息并提醒)")//
//                .titleTextSize_SP(14.5f)//
//                .show();

        //  String[] stringItems = {"接收消息并提醒", "接收消息但不提醒", "收进群助手且不提醒", "屏蔽群消息"};
        ActionSheetDialog dialog = new ActionSheetDialog(mcontext, stringItems, null);
        dialog.title(title)//
                .titleTextSize_SP(14.5f)//
                .show();
        return dialog;
    }

    /**
     * MD风格
     * bottomsheet  没有标题
     */
    public static ActionSheetDialog getBottomSheetDialogNoTitle(Context mcontext, String[] stringItems) {
        BounceTopEnter mBasIn = new BounceTopEnter();
        SlideBottomExit mBasOut = new SlideBottomExit();


        ActionSheetDialog dialog = new ActionSheetDialog(mcontext, stringItems, null);
        dialog.isTitleShow(false).show();
        return dialog;
    }

//    /**
//     * MD风格
//     * 带编辑框的dialog
//     */
//    public static CustomEditDialog getEditDialog(Context mcontext) {
//        final CustomEditDialog dialog = new CustomEditDialog(mcontext);
//        dialog.show();
//        dialog.setCanceledOnTouchOutside(false);
//
//        return dialog;
//    }

    /**
     * 弹出底部分享界面
     */
    public static SortTopDialog getShareBottomDialog(Context mcontext, String s) {
        SortTopDialog dialog = new SortTopDialog(mcontext, s);
        //dialog.show();
        //dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }


//    /**
//     * 带进度的dialog
//     */
//    private static Dialog dialog;
//
//    public static void showDialogForLoading(Context context, String msg, boolean cancelable) {
//        // if (null == dialog) {
//        if (null == context) return;
//        View view = LayoutInflater.from(context).inflate(R.layout.layout_loading_dialog, null);
//        TextView loadingText = (TextView) view.findViewById(R.id.loading_tip_text);
//        loadingText.setText(msg);
//
//        dialog = new Dialog(context, R.style.loading_dialog_style);
//        dialog.setCancelable(cancelable);
//        dialog.setCanceledOnTouchOutside(cancelable);
//        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//        Activity activity = (Activity) context;
//        if (activity.isFinishing()) return;
//        dialog.show();
//        // }
//    }

//    public static void hideDialogForLoading(Context context) {
//        dialog.dismiss();
//    }

    static ProgressDialog progressDlg = null;

    /**
     * 启动进度条
     *
     * @param strMessage 进度条显示的信息
     * @param //         当前的activity
     */
    public static void showProgressDlg(Context ctx, String strMessage) {

//        if (null == progressDlg) {
            if (ctx == null) return;
            progressDlg = new ProgressDialog(ctx);
            //设置进度条样式
            progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //提示的消息
            progressDlg.setMessage(strMessage);
            progressDlg.setIndeterminate(false);
            progressDlg.setCancelable(true);
           if(progressDlg.isShowing()) {
               return;
           }
            progressDlg.show();
//        }
    }



    /**
     * 结束进度条
     */
    public static void stopProgressDlg() {
        if (null != progressDlg && progressDlg.isShowing()) {
            progressDlg.dismiss();
            progressDlg = null;
        }
//        if (null != dialog && dialog.isShowing()) {
//            dialog.dismiss();
//            dialog = null;
//        }
    }

    public static void showPushDialog(){

    }


}
