package com.l.zhuoyaohook;

import android.app.Application;
import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook implements IXposedHookLoadPackage {

    private final XSharedPreferences sp;

    public Hook() {
        sp = new XSharedPreferences(BuildConfig.APPLICATION_ID);
        sp.makeWorldReadable();
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.contains("com.tencent.gwgo")) {

            XposedBridge.log("hook sensor");
            Class<?> sensorEventQueue_Class = Class.forName("android.hardware.SystemSensorManager$SensorEventQueue");
            if (sensorEventQueue_Class != null) {
                XposedBridge.hookAllMethods(sensorEventQueue_Class, "dispatchSensorEvent", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        boolean isSwitch = sp.getBoolean("isSwitch", false);
                        if (isSwitch) {
                            int multi = sp.getInt("num", 2);
                            ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * multi;
                        }
                        super.beforeHookedMethod(param);
                    }
                });
            }

            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);

                    XposedBridge.log("find zhuoyao");

                    ClassLoader classLoader = ((Context) param.args[0]).getClassLoader();

                    //vm fake camera vbox
                    Class<?> info_b_Class = classLoader.loadClass("com.tencent.bugly.msdk.crashreport.common.info.b");
                    if (info_b_Class != null) {
                        XposedHelpers.findAndHookMethod(info_b_Class, "i", Context.class, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult("");
                            }
                        });

                        XposedHelpers.findAndHookMethod(info_b_Class, "j", Context.class, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult("");
                            }
                        });

                        // is virtual
                        XposedHelpers.findAndHookMethod(info_b_Class, "l", Context.class, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult(false);
                            }
                        });
                        //hook
                        XposedHelpers.findAndHookMethod(info_b_Class, "m", Context.class, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult(false);
                            }
                        });

                        //hook
                        XposedHelpers.findAndHookMethod(info_b_Class, "r", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult(0);
                            }
                        });

                        XposedHelpers.findAndHookMethod(info_b_Class, "o", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult(0);
                            }
                        });

                        XposedHelpers.findAndHookMethod(info_b_Class, "s", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult(0);
                            }
                        });

                        XposedHelpers.findAndHookMethod(info_b_Class, "t", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult(0);
                            }
                        });
                    }

                    Class<?> info_a_Class = classLoader.loadClass("com.tencent.bugly.msdk.crashreport.common.info.a");
                    if (info_a_Class != null) {
                        XposedHelpers.findAndHookMethod(info_a_Class, "R", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult(false);
                            }
                        });
                        //does it hook frame
                        XposedHelpers.findAndHookMethod(info_a_Class, "S", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult(false);
                            }
                        });
                    }
                }
            });
        }
    }
}
