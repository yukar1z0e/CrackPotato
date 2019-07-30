package com.example.crackpotato;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.os.Handler;

import java.lang.reflect.Field;
import java.lang.Class;

import javax.security.auth.callback.Callback;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;


public class crackmain implements IXposedHookLoadPackage{
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam)throws Throwable{
        if(lpparam.packageName.contains("org.potato")){
            XposedBridge.log(lpparam.packageName);


/*           //修改BuildVars的DEBUG值
            Class BuildVarsClass=XposedHelpers.findClass("org.potato.messenger.BuildVars",lpparam.classLoader);
            Field DEBUG=BuildVarsClass.getDeclaredField("DEBUG");
            Field DEBUG_MOMENT=BuildVarsClass.getDeclaredField("DEBUG_MOMENT");
            Field DEBUG_PRIVATE_VERSION=BuildVarsClass.getDeclaredField("DEBUG_PRIVATE_VERSION");
            Field DEBUG_RPM=BuildVarsClass.getDeclaredField("DEBUG_RPM");
            Field DEBUG_UPDATE=BuildVarsClass.getDeclaredField("DEBUG_UPDATE");
            Field DEBUG_VERSION=BuildVarsClass.getDeclaredField("DEBUG_VERSION");
            Field DEBUG_WALLET=BuildVarsClass.getDeclaredField("DEBUG_WALLET");
            DEBUG.setAccessible(true);
            XposedHelpers.setStaticObjectField(BuildVarsClass,"DEBUG",true);
            XposedHelpers.setStaticObjectField(BuildVarsClass,"DEBUG_MOMENT",true);
            XposedHelpers.setStaticObjectField(BuildVarsClass,"DEBUG_PRIVATE_VERSION",true);
            XposedHelpers.setStaticObjectField(BuildVarsClass,"DEBUG_RPM",true);
            XposedHelpers.setStaticObjectField(BuildVarsClass,"DEBUG_UPDATE",true);
            XposedHelpers.setStaticObjectField(BuildVarsClass,"DEBUG_VERSION",true);
            XposedHelpers.setStaticObjectField(BuildVarsClass,"DEBUG_WALLET",true);
            DEBUG.setAccessible(true);
            XposedBridge.log("After Change HOOK STATIC FIELD toGenericiSting--->"+DEBUG.toGenericString()+"<---哈哈哈");
            XposedBridge.log("After Change HOOK STATIC FIELD get--->"+DEBUG.get(BuildVarsClass)+"<---哈哈哈");
            XposedBridge.log("After Change HOOK STATIC FIELD toGenericiSting--->"+DEBUG_MOMENT.toGenericString()+"<---哈哈哈");
            XposedBridge.log("After Change HOOK STATIC FIELD get--->"+DEBUG_MOMENT.get(BuildVarsClass)+"<---哈哈哈");
            XposedBridge.log("After Change HOOK STATIC FIELD toGenericiSting--->"+DEBUG_PRIVATE_VERSION.toGenericString()+"<---哈哈哈");
            XposedBridge.log("After Change HOOK STATIC FIELD get--->"+DEBUG_PRIVATE_VERSION.get(BuildVarsClass)+"<---哈哈哈");
            XposedBridge.log("After Change HOOK STATIC FIELD toGenericiSting--->"+DEBUG_RPM.toGenericString()+"<---哈哈哈");
            XposedBridge.log("After Change HOOK STATIC FIELD get--->"+DEBUG_RPM.get(BuildVarsClass)+"<---哈哈哈");
            XposedBridge.log("After Change HOOK STATIC FIELD toGenericiSting--->"+DEBUG_UPDATE.toGenericString()+"<---哈哈哈");
            XposedBridge.log("After Change HOOK STATIC FIELD get--->"+DEBUG_UPDATE.get(BuildVarsClass)+"<---哈哈哈");
            XposedBridge.log("After Change HOOK STATIC FIELD toGenericiSting--->"+DEBUG_VERSION.toGenericString()+"<---哈哈哈");
            XposedBridge.log("After Change HOOK STATIC FIELD get--->"+DEBUG_VERSION.get(BuildVarsClass)+"<---哈哈哈");
            XposedBridge.log("After Change HOOK STATIC FIELD toGenericiSting--->"+DEBUG_WALLET.toGenericString()+"<---哈哈哈");
            XposedBridge.log("After Change HOOK STATIC FIELD get--->"+DEBUG_WALLET.get(BuildVarsClass)+"<---哈哈哈");

            //String Builder Hook
            final Class StringBuilderClass=lpparam.classLoader.loadClass("java.lang.StringBuilder");
            findAndHookMethod(StringBuilderClass,"toString",new XC_MethodHook(){
                @Override
                protected void beforeHookedMethod(MethodHookParam param){
                    Log.d("Hooked","before");
                }
                @Override
                protected void afterHookedMethod(MethodHookParam param)throws Throwable{
                    Log.d("Hooked","after--->");
                    Log.d("Hooked",param.getResultOrThrowable().toString());
                }
            });

           //HttpUrlUtils hook
            final Class HttpUtilsClass=lpparam.classLoader.loadClass("org.potato.ui.moment.HttpUrlUtils");
            findAndHookMethod(HttpUtilsClass, "getUrl", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param)throws Throwable{
                    String url=(String)param.args[0];
                    Log.d("Before","Hook HttpUtils--->its url is: "+url);
                }
                @Override
                protected void afterHookedMethod(MethodHookParam param)throws Throwable{
                    String urlRev=(String)param.getResult();
                    Log.d("After","Hook HttpUtils--->its url is: "+urlRev);
                }
            });

           //对任意URL解密
           final Class HttpUtilsDecryptClass=lpparam.classLoader.loadClass("org.potato.ui.moment.HttpUrlUtils");
           findAndHookMethod(HttpUtilsDecryptClass, "getUrl", String.class, new XC_MethodHook() {
               @Override
               protected void beforeHookedMethod(MethodHookParam param)throws Throwable{
                   String url=(String)param.args[0];
                   Log.d("Before","Hook HttpUtils--->its url is: "+url);
                   param.args[0]="[加密的URL]";
                   String urlAfterChange=(String)param.args[0];
                   Log.d("Before","Hook HttpUtils After Change--->its url is: "+urlAfterChange);
               }
               @Override
               protected void afterHookedMethod(MethodHookParam param)throws Throwable{
                   String urlDecypt=(String)param.getResult();
                   Log.d("After","Hook HttpUtils--->its url is: "+urlDecypt);
               }
           });

           //getFriendInfo Hook (虚拟货币)
            findAndHookMethod("org.potato.ui.VirtualCurrencyActivity$JsInterface$getFriendInfo$1",lpparam.classLoader, "onReceiveValue", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param)throws Throwable{
                    Log.d("getFriendInfo$1","Before--->String str: "+param.args[0]);
                }
            });

            //TLRPC
            Class TLRPCClass=lpparam.classLoader.loadClass("org.potato.tgnet.TLRPC");
            Field CHAT_FLAG_IS_PUBLIC=TLRPCClass.getDeclaredField("CHAT_FLAG_IS_PUBLIC");
            Log.d("TLRPC","--->CHAT_FLAG_IS_PUBLIC--->"+CHAT_FLAG_IS_PUBLIC.get(TLRPCClass));

            //User
            Class UserClass=lpparam.classLoader.loadClass("org.potato.tgnet.TLRPC$User");
            Field first_name=UserClass.getDeclaredField("first_name");
            Log.d("TLRPC","--->first_name--->"+first_name.get(null));

            //AbstractSerializedData [无法Hook抽象方法]
            Class AbstractSerializedDataClass=lpparam.classLoader.loadClass("org.potato.tgnet.AbstractSerializedData");
            findAndHookMethod(AbstractSerializedDataClass, "readBytes", byte[].class, boolean.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("AbstractSerializedData","--->Byte--->"+param.args[0]+"--->Boolean--->"+param.args[1].toString());
                }
            });

            //SerializedData
            Class SerializedDataClass=lpparam.classLoader.loadClass("org.potato.tgnet.SerializedData");
            findAndHookMethod(SerializedDataClass, "readBytes", byte[].class, boolean.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("AbstractSerializedData","--->Byte--->"+param.args[0]+"--->Boolean--->"+param.args[1].toString());
                }
            });


            Class ListAdapterClass=lpparam.classLoader.loadClass("org.potato.ui.Contact.AddContactActivity$ListAdapter");
            //AddContactActivity$ListAdapter 参数读取
            findAndHookMethod(ListAdapterClass, "setSearchText", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                     Log.d("ListAdapter","--->Set Search String--->"+param.args[0].toString());
                }
            });

            //AddContactActivity$ListAdapter 修改传入参数[失败]
            findAndHookMethod(ListAdapterClass, "setSearchText", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    param.args[0]="【假手机号】";
                    Log.d("ListAdapter","--->Set Search String--->"+param.args[0].toString());
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param)throws Throwable{
                    super.afterHookedMethod(param);
                    Log.d("ListAdapter","--->Change Search String--->"+param.args[0].toString());
                }
            });

            Class CallbackClass=lpparam.classLoader.loadClass("org.potato.ui.Contact.AddContactActivity$1");
           //onSearch 修改传入参数
            findAndHookMethod(CallbackClass, "onSearch", CharSequence.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.args[0]="[假手机号]";
                    Log.d("createView","--->onSearch--->param--->"+param.args[0]);
                }
            });

            //onTextChanged
            findAndHookMethod(CallbackClass, "onTextChanged", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.d("createView","--->onTextChanged--->param--->"+param.args[0]);
                }
            });

            //searchUser
            final Class<?> AddContactActivityClass=lpparam.classLoader.loadClass("org.potato.ui.Contact.AddContactActivity");
            findAndHookMethod(AddContactActivityClass, "searchUser", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.args[0]="【要注入的手机号】";
                }
            });

           //失败 无法直接调用searchUser
            XposedHelpers.callMethod(AddContactActivityClass,"searchUser","【要注入的手机号】");

            final Class<?> LaunchActivityClass=lpparam.classLoader.loadClass("org.potato.ui.LaunchActivity");
            final Class<?> AddContactActivity$1Class=lpparam.classLoader.loadClass("org.potato.ui.Contact.AddContactActivity$1");
            final Class<?> AddContactActivity$3$1Class=lpparam.classLoader.loadClass("org.potato.ui.Contact.AddContactActivity$3$1");
            findAndHookMethod(LaunchActivityClass, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("LaunchActivity","Entered");
                }
                @Override
                protected void afterHookedMethod(MethodHookParam param)throws Throwable{
                    Object AddContactActivity=AddContactActivityClass.newInstance();
                    XposedHelpers.callMethod(AddContactActivity,"searchUser","【需要注入的手机号】");
                }
            });*/




            //formatName 查询结果显示函数
            final Class<?> ContactsControllerClass=lpparam.classLoader.loadClass("org.potato.messenger.ContactsController");
            findAndHookMethod(ContactsControllerClass, "formatName", String.class, String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.d("ContactsController","--->first name--->"+param.args[0]+"--->second name--->"+param.args[1]);
                }
            });
        }
    }
}
