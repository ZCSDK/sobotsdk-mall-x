package com.sobotmall.demo;

import android.content.Context;
import android.text.TextUtils;

import com.sobot.chat.SobotApi;
import com.sobot.chat.api.enumtype.SobotChatTitleDisplayMode;
import com.sobot.chat.api.model.ConsultingContent;
import com.sobot.chat.api.model.Information;
import com.sobot.chat.api.model.OrderCardContentModel;
import com.sobot.chat.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 *
 * Created by Administrator on 2017/12/12.
 */

public class SobotUtils {

    private static int enumType = 0;//0 默认,  1  自定义,  2  公司name;
    private static long sobot_show_history_ruler = 0;//显示多少分钟内的历史记录  10分钟 -24小时

    public static void startSobot(Context context) {
        if (context == null) {
            return;
        }

        //设置用户自定义字段
        Information info = new Information();
        Map<String, String> customerFields = new HashMap<>();
        customerFields.put("weixin", "55555");
        customerFields.put("weibo", "66666");
        customerFields.put("userSex", "女");
        customerFields.put("birthday", "2017-05-17");
        customerFields.put("cardNo", "142201198704102222");
        info.setCustomerFields(customerFields);
        info.setUseRobotVoice(false);//这个属性默认都是false。想使用需要付费。付费才可以设置为true。
        SobotSPUtil.getStringData(context, "sobot_key1", "");
        SobotSPUtil.getStringData(context, "sobot_key2", "");
        info.setUname(SobotSPUtil.getStringData(context, "person_uName", ""));
        info.setRealname(SobotSPUtil.getStringData(context, "sobot_realname", ""));
        info.setTel(SobotSPUtil.getStringData(context, "sobot_tel", ""));
        info.setEmail(SobotSPUtil.getStringData(context, "sobot_email", ""));
        info.setQq(SobotSPUtil.getStringData(context, "sobot_qq", ""));
        info.setRemark(SobotSPUtil.getStringData(context, "sobot_reMark", ""));
        info.setFace(SobotSPUtil.getStringData(context, "sobot_face", ""));
        info.setVisitTitle(SobotSPUtil.getStringData(context, "sobot_visitTitle", ""));
        info.setVisitUrl(SobotSPUtil.getStringData(context, "sobot_visitUrl", ""));
        Map<String, String> customInfo = new HashMap<>();
        customInfo.put("sobot_key1", SobotSPUtil.getStringData(context, "sobot_key1", ""));
        customInfo.put("sobot_key2", SobotSPUtil.getStringData(context, "sobot_key2", ""));
        info.setCustomInfo(customInfo);
        //用户信息设置结束

        //咨询信息设置开始
        boolean isShow = SobotSPUtil.getBooleanData(context, "sobot_goods_is_show_info", false);
        if (isShow) {
            ConsultingContent consult = new ConsultingContent();
            consult.setSobotGoodsTitle(SobotSPUtil.getStringData(context, "sobot_goods_title_value", ""));
            consult.setSobotGoodsDescribe(SobotSPUtil.getStringData(context, "sobot_goods_describe_value", ""));
            consult.setSobotGoodsLable(SobotSPUtil.getStringData(context, "sobot_goods_lable_value", ""));
            consult.setSobotGoodsImgUrl(SobotSPUtil.getStringData(context, "sobot_goods_imgUrl_value", ""));
            consult.setSobotGoodsFromUrl(SobotSPUtil.getStringData(context, "sobot_goods_fromUrl_value", ""));
            info.setConsultingContent(consult);
        } else {
            info.setConsultingContent(null);
        }
        //咨询信息设置结束

        //自定义应答设置开始
        SobotApi.setCustomAdminHelloWord(context, SobotSPUtil.getStringData(context, "sobot_customAdminHelloWord", ""));//自定义客服欢迎语,默认为空 （如果传入，优先使用该字段）
        SobotApi.setCustomRobotHelloWord(context, SobotSPUtil.getStringData(context, "sobot_customRobotHelloWord", ""));//自定义机器人欢迎语,默认为空 （如果传入，优先使用该字段）
        SobotApi.setCustomUserTipWord(context, SobotSPUtil.getStringData(context, "sobot_customUserTipWord", ""));//自定义用户超时提示语,默认为空 （如果传入，优先使用该字段）
        SobotApi.setCustomAdminTipWord(context, SobotSPUtil.getStringData(context, "sobot_customAdminTipWord", ""));//自定义客服超时提示语,默认为空 （如果传入，优先使用该字段）
        SobotApi.setCustomAdminNonelineTitle(context, SobotSPUtil.getStringData(context, "sobot_customAdminNonelineTitle", ""));// 自定义客服不在线的说辞,默认为空 （如果传入，优先使用该字段）
        SobotApi.setCustomUserOutWord(context, SobotSPUtil.getStringData(context, "sobot_customUserOutWord", ""));// 自定义用户超时下线提示语,默认为空 （如果传入，优先使用该字段）
        //自定义应答设置结束

        //启动参数设置开始
        info.setUid(SobotSPUtil.getStringData(context, "sobot_partnerId", ""));
        info.setReceptionistId(SobotSPUtil.getStringData(context, "sobot_receptionistId", ""));
        info.setRobotCode(SobotSPUtil.getStringData(context, "sobot_demo_robot_code", ""));
        info.setTranReceptionistFlag(SobotSPUtil.getBooleanData(context, "sobot_receptionistId_must", false) ? 1 : 0);
        if (SobotSPUtil.getBooleanData(context, "sobot_isArtificialIntelligence", false)) {
            info.setArtificialIntelligence(true);
            if (!TextUtils.isEmpty(SobotSPUtil.getStringData(context, "sobot_isArtificialIntelligence_num_value", "")) && !"".equals(SobotSPUtil.getStringData(context, "sobot_isArtificialIntelligence_num_value", ""))) {
                info.setArtificialIntelligenceNum(Integer.parseInt(SobotSPUtil.getStringData(context, "sobot_isArtificialIntelligence_num_value", "")));
            }
        } else {
            info.setArtificialIntelligence(false);
        }
        info.setUseVoice(SobotSPUtil.getBooleanData(context, "sobot_isUseVoice", true));
        info.setShowSatisfaction(SobotSPUtil.getBooleanData(context, "sobot_isShowSatisfaction", false));
        if (!TextUtils.isEmpty(SobotSPUtil.getStringData(context, "sobot_rg_initModeType", ""))) {
            info.setInitModeType(Integer.parseInt(SobotSPUtil.getStringData(context, "sobot_rg_initModeType", "")));
        }
        info.setSkillSetName(SobotSPUtil.getStringData(context, "sobot_demo_skillname", ""));
        info.setSkillSetId(SobotSPUtil.getStringData(context, "sobot_demo_skillid", ""));
        boolean sobot_isOpenNotification = SobotSPUtil.getBooleanData(context, "sobot_isOpenNotification", false);
        boolean sobot_evaluationCompletedExit = SobotSPUtil.getBooleanData(context, "sobot_evaluationCompletedExit_value", false);
        String sobot_title_vlaue = SobotSPUtil.getStringData(context, "sobot_title_value", "");
        if (!TextUtils.isEmpty(SobotSPUtil.getStringData(context, "sobot_title_value_show_type", ""))) {
            enumType = Integer.parseInt(SobotSPUtil.getStringData(context, "sobot_title_value_show_type", ""));
        }
        if (!TextUtils.isEmpty(SobotSPUtil.getStringData(context, "sobot_show_history_ruler", ""))) {
            sobot_show_history_ruler = Long.parseLong(SobotSPUtil.getStringData(context, "sobot_show_history_ruler", ""));
        }
        String transferKey = SobotSPUtil.getStringData(context, "sobot_transferKeyWord", "");
        if (!TextUtils.isEmpty(transferKey)) {
            String[] transferKeys =  transferKey.split(",");
            if (transferKeys.length > 0){
                HashSet<String> tmpSet = new HashSet<>();
                for (int i = 0; i < transferKeys.length; i++) {
                    tmpSet.add(transferKeys[i]);
                }
                info.setTransferKeyWord(tmpSet);//设置转人工关键字
            }
        }
        //启动参数设置结束

        String ed_hot_question_value = SobotSPUtil.getStringData(context, "ed_hot_question_value", "");
        if (!TextUtils.isEmpty(ed_hot_question_value)){
            try {
                Map<String,String> questionRecommendParams = new HashMap<>();

                String[] values =  ed_hot_question_value.split(",");
                if (values.length > 0 ){
                    for (String value1 : values) {
                        String[] value = value1.split(":");
                        if (value.length > 0) {
                            questionRecommendParams.put(value[0], value[1]);
                        }
                    }
                }
                info.setQuestionRecommendParams(questionRecommendParams);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        String appkey = SobotSPUtil.getStringData(context, "sobot_appkey", "");
        String customerCode = SobotSPUtil.getStringData(context, "sobot_customerCode_value", "");

        info.setCustomerCode(customerCode);
        info.setAppkey(appkey);

        //设置标题显示模式
        SobotApi.setChatTitleDisplayMode(context,
                SobotChatTitleDisplayMode.values()[enumType], sobot_title_vlaue,true);
        //设置是否开启消息提醒
        SobotApi.setNotificationFlag(context, sobot_isOpenNotification
                , R.drawable.sobot_demo_logo_small_icon, R.drawable.sobot_demo_logo);
        SobotApi.hideHistoryMsg(context, sobot_show_history_ruler);
        String sobot_flowCompanyId_value = SobotSPUtil.getStringData(context, "sobot_flowCompanyId_value", "");
        String sobot_flowGroupId_value = SobotSPUtil.getStringData(context, "sobot_flowGroupId_value", "");
        SobotApi.setFlowCompanyId(context,sobot_flowCompanyId_value);
        SobotApi.setFlowGroupId(context, sobot_flowGroupId_value);
        SobotApi.setEvaluationCompletedExit(context, sobot_evaluationCompletedExit);
        ConsultingContent consultingContent = new ConsultingContent(); //咨询内容
        consultingContent.setSobotGoodsTitle("乐视超级电视 S50 Air 全配版50英寸2D智能LED黑色（Letv S50 Air）"); //咨询内容标题
        consultingContent.setSobotGoodsImgUrl("https://sobot-test.oss-cn-beijing.aliyuncs.com/console/66a522ea3ef944a98af45bac09220861/kb/image/d19dafc0ccd941f3841f8f9858910157.png"); //咨询内容图片 选填
        consultingContent.setSobotGoodsFromUrl("https://www.baidu.com/admin/order/detail/302"); //发送内容
        consultingContent.setSobotGoodsDescribe("乐视超级电视 S50 Air 全配版50英寸2D智能LED黑色（Letv S50 Air）乐视超级电视 S50 Air 全配版50英寸2D智能LED黑色（Letv S50 Air）");
        consultingContent.setSobotGoodsLable("￥2150");
        consultingContent.setAutoSend(true);
        info.setContent(consultingContent); //可以设置为null


        //----------订单卡片-----------
        List<OrderCardContentModel.Goods> goodsList = new ArrayList<>();
        goodsList.add(new OrderCardContentModel.Goods("苹果", "https://img.sobot.com/chatres/66a522ea3ef944a98af45bac09220861/msg/20190930/7d938872592345caa77eb261b4581509.png"));
        goodsList.add(new OrderCardContentModel.Goods("苹果1111111", "https://img.sobot.com/chatres/66a522ea3ef944a98af45bac09220861/msg/20190930/7d938872592345caa77eb261b4581509.png"));
        goodsList.add(new OrderCardContentModel.Goods("苹果2222", "https://img.sobot.com/chatres/66a522ea3ef944a98af45bac09220861/msg/20190930/7d938872592345caa77eb261b4581509.png"));
        goodsList.add(new OrderCardContentModel.Goods("苹果33333333", "https://img.sobot.com/chatres/66a522ea3ef944a98af45bac09220861/msg/20190930/7d938872592345caa77eb261b4581509.png"));
        OrderCardContentModel orderCardContent = new OrderCardContentModel();
        //订单编号（必填）
        orderCardContent.setOrderCode("zc32525235425");
        //订单状态
        orderCardContent.setOrderStatus(1);
        //订单总金额
        orderCardContent.setTotalFee(1234);
        //订单商品总数
        orderCardContent.setGoodsCount("4");
        //订单链接
        orderCardContent.setOrderUrl("https://item.jd.com/1765513297.html");
        //订单创建时间
        orderCardContent.setCreateTime(System.currentTimeMillis() + "");
        orderCardContent.setAutoSend(true);
        //订单商品集合
        orderCardContent.setGoods(goodsList);
        //订单卡片内容
        info.setOrderGoodsInfo(orderCardContent);
        SobotApi.startSobotChat(context, info);
    }
}