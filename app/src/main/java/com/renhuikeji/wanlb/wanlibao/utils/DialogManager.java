package com.renhuikeji.wanlb.wanlibao.utils;

import com.renhuikeji.wanlb.wanlibao.widget.PushDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public class DialogManager {

    private static List<PushDialog> dialogs=new ArrayList<>();

    public static  void add(PushDialog dialog){
        dialogs.add(dialog);
    }

    public static void clear(){
        if(dialogs.isEmpty()){
            return;
        }
        for(PushDialog d:dialogs){
            if(d!=null){
              d.deleteDialog();
            }
        }
    }
}
