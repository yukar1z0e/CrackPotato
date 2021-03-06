package com.example.crackpotato;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XposedHelpers;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class crackmain implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log(lpparam.packageName);

        if (lpparam.packageName.contains("org.potato")) {
            final Class<?> BuildVarsClass = lpparam.classLoader.loadClass("org.potato.messenger.BuildVars");
            final Class<?> StringBuilderClass = lpparam.classLoader.loadClass("java.lang.StringBuilder");
            final Class<?> HttpUtilsClass = lpparam.classLoader.loadClass("org.potato.ui.moment.HttpUrlUtils");
            final Class<?> HttpUtilsDecryptClass = lpparam.classLoader.loadClass("org.potato.ui.moment.HttpUrlUtils");
            final Class<?> TLRPCClass = lpparam.classLoader.loadClass("org.potato.tgnet.TLRPC");
            final Class<?> UserClass = lpparam.classLoader.loadClass("org.potato.tgnet.TLRPC$User");
            final Class<?> AbstractSerializedDataClass = lpparam.classLoader.loadClass("org.potato.tgnet.AbstractSerializedData");
            final Class<?> CallbackClass = lpparam.classLoader.loadClass("org.potato.ui.Contact.AddContactActivity$0");
            final Class<?> SerializedDataClass = lpparam.classLoader.loadClass("org.potato.tgnet.SerializedData");
            final Class<?> LaunchActivityClass = lpparam.classLoader.loadClass("org.potato.ui.LaunchActivity");
            final Class<?> AddContactActivity$ListAdapterClass = lpparam.classLoader.loadClass("org.potato.ui.Contact.AddContactActivity$ListAdapter");
            final Class<?> AddContactActivityClass = lpparam.classLoader.loadClass("org.potato.ui.Contact.AddContactActivity");
            final Class<?> AddContactActivity$0Class = lpparam.classLoader.loadClass("org.potato.ui.Contact.AddContactActivity$0");
            final Class<?> AddContactActivity$0$0Class = lpparam.classLoader.loadClass("org.potato.ui.Contact.AddContactActivity$0$0");
            final Class<?> ContactsControllerClass = lpparam.classLoader.loadClass("org.potato.messenger.ContactsController");
            final Class<?> ActionBarClass = lpparam.classLoader.loadClass("org.potato.ui.ActionBar.ActionBar");
            final Class<?> ActionBar$ActionBarMenuOnItemClickClass = lpparam.classLoader.loadClass("org.potato.ui.ActionBar.ActionBar$ActionBarMenuOnItemClick");
            final Class<?> WindowManagerGlobalClass = lpparam.classLoader.loadClass("android.view.WindowManagerGlobal");
            final Class<?> DBHelperClass = lpparam.classLoader.loadClass("com.pt.ip.DBHelper");
            final Class<?> CryptoClass = lpparam.classLoader.loadClass("com.pt.ip.Crypto");
            final Class<?> AsyncHttpURLConnectionClass = lpparam.classLoader.loadClass("org.appspot.apprtc.util.AsyncHttpURLConnection");
            final Class<?> AsyncHttpClass = lpparam.classLoader.loadClass("org.appspot.apprtc.util.AsyncHttpURLConnection$AsyncHttpEvents");


            //formatName 查询结果显示函数
            findAndHookMethod(ContactsControllerClass, "formatName", String.class, String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.d("ContactsController", "--->first name--->" + param.args[0] + "--->second name--->" + param.args[0]);
                }
            });


            //修改BuildVars的DEBUG值
            Field DEBUG = BuildVarsClass.getDeclaredField("DEBUG");
            Field DEBUG_MOMENT = BuildVarsClass.getDeclaredField("DEBUG_MOMENT");
            Field DEBUG_PRIVATE_VERSION = BuildVarsClass.getDeclaredField("DEBUG_PRIVATE_VERSION");
            Field DEBUG_RPM = BuildVarsClass.getDeclaredField("DEBUG_RPM");
            Field DEBUG_UPDATE = BuildVarsClass.getDeclaredField("DEBUG_UPDATE");
            Field DEBUG_VERSION = BuildVarsClass.getDeclaredField("DEBUG_VERSION");
            Field DEBUG_WALLET = BuildVarsClass.getDeclaredField("DEBUG_WALLET");
            DEBUG.setAccessible(true);
            XposedHelpers.setStaticObjectField(BuildVarsClass, "DEBUG", true);
            XposedHelpers.setStaticObjectField(BuildVarsClass, "DEBUG_MOMENT", true);
            XposedHelpers.setStaticObjectField(BuildVarsClass, "DEBUG_PRIVATE_VERSION", true);
            XposedHelpers.setStaticObjectField(BuildVarsClass, "DEBUG_RPM", true);
            XposedHelpers.setStaticObjectField(BuildVarsClass, "DEBUG_UPDATE", true);
            XposedHelpers.setStaticObjectField(BuildVarsClass, "DEBUG_VERSION", true);
            XposedHelpers.setStaticObjectField(BuildVarsClass, "DEBUG_WALLET", true);

            //String Builder Hook
            findAndHookMethod(StringBuilderClass, "toString", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    Log.d("Hooked", "before");
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("Hooked", "after--->");
                    Log.d("Hooked", param.getResultOrThrowable().toString());
                }
            });

            //HttpUrlUtils hook
            findAndHookMethod(HttpUtilsClass, "getUrl", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    String url = (String) param.args[0];
                    Log.d("Before", "Hook HttpUtils--->its url is: " + url);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    String urlRev = (String) param.getResult();
                    Log.d("After", "Hook HttpUtils--->its url is: " + urlRev);
                }
            });

            //对任意URL解密
            findAndHookMethod(HttpUtilsDecryptClass, "getUrl", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    String url = (String) param.args[0];
                    Log.d("Before", "Hook HttpUtils--->its url is: " + url);
                    param.args[0] = "[加密的URL]";
                    String urlAfterChange = (String) param.args[0];
                    Log.d("Before", "Hook HttpUtils After Change--->its url is: " + urlAfterChange);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    String urlDecypt = (String) param.getResult();
                    Log.d("After", "Hook HttpUtils--->its url is: " + urlDecypt);
                }
            });

            //getFriendInfo Hook (虚拟货币)
            findAndHookMethod("org.potato.ui.VirtualCurrencyActivity$JsInterface$getFriendInfo$0", lpparam.classLoader, "onReceiveValue", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("getFriendInfo$0", "Before--->String str: " + param.args[0]);
                }
            });

            //TLRPC
            Field CHAT_FLAG_IS_PUBLIC = TLRPCClass.getDeclaredField("CHAT_FLAG_IS_PUBLIC");
            Log.d("TLRPC", "--->CHAT_FLAG_IS_PUBLIC--->" + CHAT_FLAG_IS_PUBLIC.get(TLRPCClass));

            //User
            Field first_name = UserClass.getDeclaredField("first_name");
            Log.d("TLRPC", "--->first_name--->" + first_name.get(null));

            //AbstractSerializedData [无法Hook抽象方法]
            findAndHookMethod(AbstractSerializedDataClass, "readBytes", byte[].class, boolean.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("AbstractSerializedData", "--->Byte--->" + param.args[0] + "--->Boolean--->" + param.args[0].toString());
                }
            });

            //SerializedData
            findAndHookMethod(SerializedDataClass, "readBytes", byte[].class, boolean.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("AbstractSerializedData", "--->Byte--->" + param.args[0] + "--->Boolean--->" + param.args[0].toString());
                }
            });

            //AddContactActivity$ListAdapter 参数读取
            findAndHookMethod(AddContactActivity$ListAdapterClass, "setSearchText", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("ListAdapter", "--->Set Search String--->" + param.args[0].toString());
                }
            });

            //AddContactActivity$ListAdapter 修改传入参数[失败]
            findAndHookMethod(AddContactActivity$ListAdapterClass, "setSearchText", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    param.args[0] = "【假手机号】";
                    Log.d("ListAdapter", "--->Set Search String--->" + param.args[0].toString());
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Log.d("ListAdapter", "--->Change Search String--->" + param.args[0].toString());
                }
            });

            //onSearch 修改传入参数
            findAndHookMethod(CallbackClass, "onSearch", CharSequence.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.args[0] = "[假手机号]";
                    Log.d("createView", "--->onSearch--->param--->" + param.args[0]);
                }
            });

            //onTextChanged
            findAndHookMethod(CallbackClass, "onTextChanged", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.d("createView", "--->onTextChanged--->param--->" + param.args[0]);
                }
            });

            //searchUser
            findAndHookMethod(AddContactActivityClass, "searchUser", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.args[0] = "【要注入的手机号】";
                }
            });

            //失败 无法直接调用searchUser
            XposedHelpers.callMethod(AddContactActivityClass, "searchUser", "【要注入的手机号】");
            findAndHookMethod(LaunchActivityClass, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("LaunchActivity", "Entered");
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Object AddContactActivity = AddContactActivityClass.newInstance();
                    XposedHelpers.callMethod(AddContactActivity, "searchUser", "【需要注入的手机号】");
                }
            });

            findAndHookMethod(AddContactActivity$ListAdapterClass, "onCreateViewHolder", ViewGroup.class, int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("ListAdapter", "Enter onCreateViewHolder--->" + param.thisObject.getClass());
                    Field isPhoneVal = param.thisObject.getClass().getDeclaredField("isPhone");
                    Field isSearchResultVal = param.thisObject.getClass().getDeclaredField("isSearchResult");
                    isPhoneVal.setAccessible(true);
                    isSearchResultVal.setAccessible(true);
                    XposedHelpers.setObjectField(param.thisObject, "isPhone", true);
                    XposedHelpers.setObjectField(param.thisObject, "isSearchResult", true);
                    Log.d("ListAdapter", "Enter onCreateViewHolder--->isPhone--->" + isPhoneVal.get(param.thisObject) + "--->isSearchResult--->" + isSearchResultVal.get(param.thisObject));

                    try {
                        Object AddContactActivity$0 = AddContactActivity$0Class.newInstance();
                        XposedHelpers.callMethod(AddContactActivity$0, "onSearch", "00000000000");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("AddContactActivity$0", "Call searchUser Method");

                    //报is not accessible的错误
                    Object AddContactActivity = AddContactActivityClass.newInstance();
                    XposedHelpers.callMethod(AddContactActivityClass, "searchUser", "【要查询的手机号】");

                    Object AddContactActivity$0 = AddContactActivity$0Class.newInstance();
                    XposedHelpers.callMethod(AddContactActivity$0, "onSearch", "【要查询的手机号】");

                    Method onSearchMethod = AddContactActivity$0Class.getDeclaredMethod("onSearch", CharSequence.class);
                    Log.d("AddContanctActivity$0", onSearchMethod.getName());
                    onSearchMethod.setAccessible(true);
                    onSearchMethod.invoke(AddContactActivity$0Class.newInstance(), new Object[0], "【要查询的手机号】");
                }
            });

            //报错 没有onBindViewHolder
            findAndHookMethod(AddContactActivity$ListAdapterClass, "onBindViewHolder", ViewGroup.class, int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("ListAdapter", "Enter onBindViewHolder--->" + param.thisObject.getClass());
                    Field isPhoneVal = param.thisObject.getClass().getDeclaredField("isPhone");
                    Field isSearchResultVal = param.thisObject.getClass().getDeclaredField("isSearchResult");
                    isPhoneVal.setAccessible(true);
                    isSearchResultVal.setAccessible(true);
                    XposedHelpers.setObjectField(param.thisObject, "isPhone", true);
                    XposedHelpers.setObjectField(param.thisObject, "isSearchResult", true);
                    Log.d("ListAdapter", "Enter onCreateViewHolder--->isPhone--->" + isPhoneVal.get(param.thisObject) + "--->isSearchResult--->" + isSearchResultVal.get(param.thisObject));

                    try {
                        Object AddContactActivity$0 = AddContactActivity$0Class.newInstance();
                        XposedHelpers.callMethod(AddContactActivity$0, "onSearch", "【要查询的手机号】");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("AddContactActivity$0", "Call searchUser Method");
                }
            });

            //堆栈溢出
            findAndHookMethod(AddContactActivity$ListAdapterClass, "onCreateViewHolder", ViewGroup.class, int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.d("ListAdapter", param.args[0].toString());
                    final Object viewGroup = (ViewGroup) param.args[0];
                    callMethod(param.thisObject, "onCreateViewHolder", viewGroup, 0);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("ListAdapter", "After Hook" + param.args[0].toString());
                }
            });

            //获取ActionBar的init 并调用点击 失败
            findAndHookMethod(ActionBarClass, "init", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Object ActionBar$ActionBarMenuOnItemClickObj = ActionBar$ActionBarMenuOnItemClickClass.newInstance();
                    callMethod(ActionBar$ActionBarMenuOnItemClickObj, "onItemClick", 0);
                }
            });

            //反射ui父类 mViewsField no recevier
            Field mViewsField = WindowManagerGlobalClass.getDeclaredField("mViews");
            Field sDefaultWindowManagerField = WindowManagerGlobalClass.getDeclaredField("sDefaultWindowManager");
            mViewsField.setAccessible(true);
            sDefaultWindowManagerField.setAccessible(true);
            Object instance = sDefaultWindowManagerField.get(null);
            Log.d("ReflectUI", "mWindowManagerString--->" + instance);
            Collections.unmodifiableList((ArrayList<View>) mViewsField.get(instance));
            Log.d("ReflectUI", "mViewsFieldString--->" + mViewsField.get(instance).toString());
            //com.pt.ip.* 的都hook不到
            findAndHookMethod(CryptoClass, "decrypt", byte[].class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("pt.ip", "cypted args--->" + param.args[0].toString());
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("pt.ip", "decrypted args--->" + param.getResult().toString());
                }
            });
            findAndHookMethod(DBHelperClass, "getLatestIP", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.d("pt.ip", "ip--->" + param.args[0].toString());
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    Log.d("pt.ip", "ip--->" + param.getResult().toString());
                }
            });

            //StringBuilder.append 软件崩溃 原因不详
            findAndHookMethod(StringBuilderClass, "append", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("StringBuilder", "Param--->" + param.args[0].toString());
                }
            });

            //AsyncHttpURLConnection
            findAndHookMethod(AsyncHttpURLConnectionClass, "sendHttpMessage", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Field messageField = param.thisObject.getClass().getDeclaredField("message");
                    messageField.setAccessible(true);
                    Log.d("AsyncHttpURLConnection", "message--->" + messageField.get(param.thisObject));
                }
            });

            findAndHookConstructor(AsyncHttpURLConnectionClass, String.class, String.class, String.class, AsyncHttpClass, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d("AsyncHttpURLConnection", "url--->" + param.args[0].toString() + "--->message--->" + param.args[0].toString());
                }
            });
        }
    }
}
