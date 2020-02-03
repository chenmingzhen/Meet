package com.example.framework.db;

import com.example.framework.utils.LogUtils;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class LitePalHelper {

    private static volatile LitePalHelper singleton;

    private LitePalHelper() {
    }

    public static LitePalHelper getInstance() {
        if (singleton == null) {
            synchronized (LitePalHelper.class) {
                if (singleton == null) {
                    singleton = new LitePalHelper();
                }
            }
        }
        return singleton;
    }

    /**
     * 保存基类
     * @param litePalSupport
     */
    private void baseSave(LitePalSupport litePalSupport){
        litePalSupport.save ();
    }

    /**
     * 保存新朋友
     * @param msg
     * @param id
     */
    public void saveNewFriend(String msg,String id){
        LogUtils.i("saveNewFriend");
        NewFriend newFriend = new NewFriend();
        newFriend.setMsg(msg);
        newFriend.setUserId (id);
        newFriend.setIsAgree(-1);
        newFriend.setSaveTime(System.currentTimeMillis());
        baseSave(newFriend);
    }

    /**
     * 查询基类
     * @param cls
     * @return
     */
    private List<? extends LitePalSupport> baseQuery(Class cls){
        return LitePal.findAll (cls);
    }

    /**
     * 查询新朋友
     * @return
     */
    public List<NewFriend> queryNewFriend(){
        return (List<NewFriend>)baseQuery (NewFriend.class);
    }

    /**
     * 更新新朋友的数据库状态
     * @param userId
     * @param agree
     */
    public void updateNewFriend(String userId,int agree){
       NewFriend newFriend=new NewFriend ();
       newFriend.setIsAgree (agree);
       newFriend.updateAll ("userId=?",userId);
    }
}