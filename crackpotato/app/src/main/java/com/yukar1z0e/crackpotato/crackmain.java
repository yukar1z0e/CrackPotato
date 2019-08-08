package com.yukar1z0e.crackpotato;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import static de.robv.android.xposed.XposedHelpers.findField;

public class crackmain implements IXposedHookLoadPackage {
    private XC_LoadPackage.LoadPackageParam lpparam = null;
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (loadPackageParam.packageName.contains("org.potato")) {
            findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    lpparam = loadPackageParam;
                    getInfo();
                }
            });
        }
    }

    public void getInfo() {
        final Class<?> AddContactActivity$UserLabelClass = findClass("org.potato.ui.Contact.AddContactActivity$UserLabel", lpparam.classLoader);
        final Class<?> TLRPC$UserClass = findClass("org.potato.tgnet.TLRPC$User", lpparam.classLoader);
        findAndHookMethod(AddContactActivity$UserLabelClass, "setData", TLRPC$UserClass, boolean.class, String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Field first_nameField = findField(TLRPC$UserClass, "first_name");
                Field last_nameField = findField(TLRPC$UserClass, "last_name");
                Field usernameField = findField(TLRPC$UserClass, "username");
                Field idField = findField(TLRPC$UserClass, "id");
                Field phoneField = findField(TLRPC$UserClass, "phone");
                Field lang_codeField = findField(TLRPC$UserClass, "lang_code");
                Field photoField = findField(TLRPC$UserClass, "photo");
                Field restrictedField = findField(TLRPC$UserClass, "restricted");
                Field restriction_reasonField = findField(TLRPC$UserClass, "restriction_reason");
                Field statusField = findField(TLRPC$UserClass, "status");
                Field mutual_contactField = findField(TLRPC$UserClass, "mutual_contact");
                Field explicit_contentField = findField(TLRPC$UserClass, "explicit_content");
                Log.d("crackPotato",
                        " \nfirst name: " + first_nameField.get(param.args[0])
                                + "\nlast name: " + last_nameField.get(param.args[0])
                                + "\nphone: " + phoneField.get(param.args[0])
                                + "\nid: " + idField.get(param.args[0])
                                + "\nstatus: " + statusField.get(param.args[0])
                                + "\nusername: " + usernameField.get(param.args[0])
                                + "\nlang code: " + lang_codeField.get(param.args[0])
                                + "\nphoto: " + photoField.get(param.args[0])
                                + "\nis restricted: " + restrictedField.get(param.args[0])
                                + "\nrestriction reason: " + restriction_reasonField.get(param.args[0])
                                + "\nis mutual contact: " + mutual_contactField.get(param.args[0])
                                + "\nis explicit content: " + explicit_contentField.get(param.args[0])
                );
            }
        });
    }
}
