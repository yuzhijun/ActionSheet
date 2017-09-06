package com.lenovohit.actionsheet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yuzhijun on 2017/9/6.
 */
public class ActionSheet {
    private ViewGroup parentView;
    private View rootView;
    private View actionSheet;
    private LinearLayout lLayout_content;
    private TextView tvTitle;
    private TextView tvCancel;

    public ActionSheet(View view,int resId,String title) {
        parentView = findSuitableParent(view);
        actionSheet =  LayoutInflater.from(parentView.getContext()).inflate(R.layout.action_sheet,parentView,false);
        lLayout_content = (LinearLayout) actionSheet.findViewById(R.id.lLayout_content);
        tvTitle = (TextView)actionSheet.findViewById(R.id.txt_title);
        tvCancel = (TextView) actionSheet.findViewById(R.id.txt_cancel);
        rootView = LayoutInflater.from(view.getContext()).inflate(resId,lLayout_content,true);

        tvTitle.setText(title == null || "".equalsIgnoreCase(title) ? "" : title);
        initEvent();
    }

    private void initEvent() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        for (int i = 0;i < lLayout_content.getChildCount();i++){
            View view = lLayout_content.getChildAt(i);
            final int index = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClick != null){
                        mItemClick.onItemClick(index);
                    }
                }
            });
        }
    }

    public static ActionSheet makeActionSheet(View view,int resId,String title){
        return new ActionSheet(view,resId,title);
    }

    public ActionSheet show(){
        if (actionSheet.getParent() != null){
            parentView.removeView(actionSheet);
        }
        //将布局添加进FrameLayout
        parentView.addView(actionSheet);

        return this;
    }

    public void dismiss(){
        ObjectAnimator ani = ObjectAnimator.ofFloat(actionSheet,"translationY",0,actionSheet.getHeight());
        ani.setDuration(600);
        ani.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                parentView.removeView(actionSheet);
                actionSheet = null;
            }
        });
        ani.start();
    }

    private IItemClick mItemClick;
    public void registerItemClick(IItemClick itemClick){
        this.mItemClick = itemClick;
    }
    interface IItemClick{
        void onItemClick(int index);
    }

    /**
     * 向上追溯找到id为content的FrameLayout
     * */
    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        do {
            if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    return (ViewGroup) view;
                } else {
                    fallback = (ViewGroup) view;
                }
            }
            if (view != null) {
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);
        return fallback;
    }
}
