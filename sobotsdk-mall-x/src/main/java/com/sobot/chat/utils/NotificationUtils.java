package com.sobot.chat.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;

import com.sobot.chat.api.model.SobotLeaveReplyModel;
import com.sobot.chat.api.model.ZhiChiPushMessage;

public class NotificationUtils {

    private static final String SOBOT_CHANNEL_ID = "sobot_channel_id";

    public static void createNotification(Context context, String title, String content, String ticker, int id, ZhiChiPushMessage pushMessage) {

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }

        Intent detailIntent = new Intent(ZhiChiConstant.SOBOT_NOTIFICATION_CLICK);
        if (pushMessage != null) {
            detailIntent.putExtra("sobot_appId", pushMessage.getAppId());
        }
        detailIntent.setPackage(context.getPackageName());
        PendingIntent pendingIntent2 = null;
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            // Create a PendingIntent using FLAG_IMMUTABLE
            pendingIntent2 = PendingIntent.getBroadcast(context, 0,
                    detailIntent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent2 = PendingIntent.getBroadcast(context, 0,
                    detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        int smallicon = SharedPreferencesUtil.getIntData(context, ZhiChiConstant
                .SOBOT_NOTIFICATION_SMALL_ICON, ResourceUtils.getIdByName(context, "drawable", "sobot_logo_small_icon"));
        int largeicon = SharedPreferencesUtil.getIntData(context, ZhiChiConstant
                .SOBOT_NOTIFICATION_LARGE_ICON, ResourceUtils.getIdByName(context, "drawable", "sobot_logo_icon"));

        BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(largeicon);
        Bitmap bitmap = bd.getBitmap();
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(smallicon) // ??????????????????????????????????????????????????????24??24?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????setLargeIcon(Bitmap
                // icon)
               // .setLargeIcon(bitmap)
                .setTicker(ticker)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent2);

        boolean compatFlag = CommonUtils.getTargetSdkVersion(context) >= 26;
        if (Build.VERSION.SDK_INT >= 26 && compatFlag) {
            String SOBOT_CHANNEL_NAME = ResourceUtils.getResString(context, "sobot_notification_name");//"????????????";
            NotificationChannel mChannel = new NotificationChannel(SOBOT_CHANNEL_ID, SOBOT_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(mChannel);
            builder.setChannelId(SOBOT_CHANNEL_ID);
        }

        Notification notify2 = builder.getNotification();
        notify2.flags |= Notification.FLAG_AUTO_CANCEL;

        notify2.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;
        manager.notify(id, notify2);
    }

    public static void createLeaveReplyNotification(Context context, String title, String content, String ticker, int id, String companyId, String uid, SobotLeaveReplyModel leaveReplyModel) {

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }

        Intent detailIntent = new Intent(ZhiChiConstant.SOBOT_LEAVEREPLEY_NOTIFICATION_CLICK);
        if (leaveReplyModel != null) {
            Bundle bundle=new Bundle();
            bundle.putSerializable("sobot_leavereply_model", leaveReplyModel);
            bundle.putString("sobot_leavereply_companyId", companyId);
            bundle.putString("sobot_leavereply_uid", uid);
            detailIntent.putExtras(bundle);
        }
        detailIntent.setPackage(context.getPackageName());
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0,
                detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallicon = SharedPreferencesUtil.getIntData(context, ZhiChiConstant
                .SOBOT_NOTIFICATION_SMALL_ICON, ResourceUtils.getIdByName(context, "drawable", "sobot_logo_small_icon"));
        int largeicon = SharedPreferencesUtil.getIntData(context, ZhiChiConstant
                .SOBOT_NOTIFICATION_LARGE_ICON, ResourceUtils.getIdByName(context, "drawable", "sobot_logo_icon"));

        BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(largeicon);
        Bitmap bitmap = bd.getBitmap();
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(smallicon) // ??????????????????????????????????????????????????????24??24?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????setLargeIcon(Bitmap
                // icon)
//                .setLargeIcon(bitmap)
                .setTicker(ticker)
                .setContentTitle(title)
                .setWhen(leaveReplyModel.getReplyTime()*1000)
                .setShowWhen(true)
                .setContentText(Html.fromHtml(content))
                .setContentIntent(pendingIntent2);

        boolean compatFlag = CommonUtils.getTargetSdkVersion(context) >= 26;
        if (Build.VERSION.SDK_INT >= 26 && compatFlag) {
            String SOBOT_CHANNEL_NAME = ResourceUtils.getResString(context, "sobot_notification_name");//"????????????";
            NotificationChannel mChannel = new NotificationChannel(SOBOT_CHANNEL_ID, SOBOT_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(mChannel);
            builder.setChannelId(SOBOT_CHANNEL_ID);
        }else{

        }

        Notification notify2 = builder.getNotification();
        notify2.flags |= Notification.FLAG_AUTO_CANCEL;

        notify2.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;
        manager.notify(getNotificationId(), notify2);
    }

    public static void cancleAllNotification(Context context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm != null) {
            try {
                nm.cancelAll();
            } catch (Exception e) {
                //ignore
            }
        }
    }


    public static int tmpNotificationId = 1000;
    /**
     * ???????????????id  ??????id?????????1999???????????????0??????1000????????????
     *
     * @return
     */
    public static final int getNotificationId() {
        if (tmpNotificationId == 1999) {
            tmpNotificationId = 1000;
        }
        tmpNotificationId++;
        return tmpNotificationId;
    }
}