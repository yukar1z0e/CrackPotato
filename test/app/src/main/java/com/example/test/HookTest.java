package com.example.test;
import de.robv.android.xposed.IXposedHookLoadPackage;

import de.robv.android.xposed.XC_MethodHook;

import de.robv.android.xposed.XposedBridge;

import de.robv.android.xposed.XposedHelpers;

import  static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookTest implements IXposedHookLoadPackage {
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        findAndHookMethod(
                "com.example.test.MainActivity",
                loadPackageParam.classLoader,
                "toastMessage",

                new XC_MethodHook() {
                    protected void beforHookedMethod(MethodHookParam param)throws Throwable{
                    }
                    protected void afterHookedMethod(MethodHookParam param)throws Throwable{
                        param.setResult("hooked");
                    }
                }

        );
    }
}
