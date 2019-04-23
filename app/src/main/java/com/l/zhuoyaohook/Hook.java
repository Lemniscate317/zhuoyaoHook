package com.l.zhuoyaohook;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook implements IXposedHookLoadPackage {

    int multi = 2;


    public Hook() {
        File file = new File(Environment.getExternalStorageDirectory() + "/zhuoyaohookconfig.txt");
        String string = readString(file.getAbsolutePath(), "utf-8");
        if (!TextUtils.isEmpty(string)) {
            try {
                Integer integer = Integer.valueOf(string);
                int i = integer.intValue();
                multi = i;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String readString(String file, String charset) {
        byte[] data = readBytes(file);
        String ret = null;

        try {
            ret = new String(data, charset);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ret;
    }

    public static byte[] readBytes(String file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            int len = fis.available();
            byte[] buffer = new byte[len];
            fis.read(buffer);
            fis.close();
            return buffer;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.contains("com.tencent.gwgo")) {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);

                    XposedBridge.log("find zhuoyao");

                    ClassLoader classLoader = ((Context) param.args[0]).getClassLoader();

                    Class<?> sensorEventQueue_Class = Class.forName("android.hardware.SystemSensorManager$SensorEventQueue");
                    if (sensorEventQueue_Class != null) {
                        XposedBridge.hookAllMethods(sensorEventQueue_Class, "dispatchSensorEvent", new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                                ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * multi;
                                super.beforeHookedMethod(param);
                            }
                        });
                    }

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
                        XposedHelpers.findAndHookMethod(info_a_Class, "R", Context.class, new XC_MethodHook() {
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
