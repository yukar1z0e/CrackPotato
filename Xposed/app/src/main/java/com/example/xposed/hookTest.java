package com.example.xposed;

import de.robv.android.xposed.IXposedHookLoadPackage;

import de.robv.android.xposed.XC_MethodHook;

import de.robv.android.xposed.XposedBridge;

import de.robv.android.xposed.XposedHelpers;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class hookTest implements IXposedHookLoadPackage{

    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        if (loadPackageParam.packageName.equals("com.example.xposed")) {

            XposedBridge.log(" has Hooked!");

            Class clazz = loadPackageParam.classLoader.loadClass(

                    "com.example.xposed.MainActivity");

            XposedHelpers.findAndHookMethod(clazz, "toastMessage", new XC_MethodHook() {

                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    super.beforeHookedMethod(param);

                    XposedBridge.log(" has Hooked!");

                }

                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    param.setResult("hooked");

                }

            });

        }

    }
}
