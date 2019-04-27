package com.l.zhuoyaohook;

import android.app.Application;
import android.content.Context;

import java.io.PrintWriter;
import java.io.StringWriter;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook implements IXposedHookLoadPackage {

    private final XSharedPreferences sp;

    public Hook() {
        sp = new XSharedPreferences(BuildConfig.APPLICATION_ID, "config");
        sp.makeWorldReadable();
    }

    public String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {


        if (lpparam.packageName.contains("com.tencent.gwgo")) {
            XposedBridge.log("hook gwgo----:" + lpparam.packageName + "  " + lpparam.processName + " " + lpparam.isFirstApplication);


            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);

                    XposedBridge.log("find zhuoyao");

                    ClassLoader classLoader = ((Context) param.args[0]).getClassLoader();

//                    Class<?> accelerationCounterClass = classLoader.loadClass("com.tencent.dailystepcouter.AccelerationCounter");
//                    if (accelerationCounterClass != null) {
//                        XposedBridge.log("accelerationCounterClass not null");
//                        XposedHelpers.findAndHookMethod(accelerationCounterClass, "setSteps", int.class, int.class, new XC_MethodHook() {
//                            @Override
//                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                int totalStep = (int) param.args[0];
//                                int todayStep = (int) param.args[1];
//                                totalStep += 10;
//                                todayStep += 10;
//                                XposedBridge.log("原:" + totalStep + "  " + todayStep);
//                                param.args[0] = totalStep;
//                                param.args[1] = todayStep;
//                                XposedBridge.log("后:" + totalStep + "  " + todayStep);
//                                super.beforeHookedMethod(param);
//                            }
//                        });
//                    }

//                    Class<?> sensorStepCounterClass = classLoader.loadClass("com.tencent.dailystepcouter.SensorStepCounter");
//                    if (sensorStepCounterClass != null) {
//                        XposedBridge.log("sensorStepCounterClass not null");
//                        XposedHelpers.findAndHookMethod(sensorStepCounterClass, "setTodayStep", int.class, new XC_MethodHook() {
//                            @Override
//                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                int todayStep = (int) param.args[0];
//                                XposedBridge.log("原 today step:" + todayStep);
//                                todayStep += 30;
//                                XposedBridge.log("改 today step:" + todayStep);
//                                param.args[0] = todayStep;
//                                super.beforeHookedMethod(param);
//                            }
//                        });
//                    }


//                    Class<?> helperClass = classLoader.loadClass("com.tencent.dailystepcouter.helper.PreferencesHelper");
//                    if (helperClass != null) {
//                        XposedBridge.log("helperClass not null");
//                        XposedHelpers.findAndHookMethod(helperClass, "setTodayStep", Context.class, int.class, new XC_MethodHook() {
//                            @Override
//                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                XposedBridge.log("set today step:" + param.args[1]);
//                                try {
//                                    throw new Exception("123");
//                                } catch (Exception e) {
//                                    XposedBridge.log(getStackTrace(e.getCause()));
//                                }
//                                super.beforeHookedMethod(param);
//                            }
//                        });

//                        XposedHelpers.findAndHookMethod(helperClass, "getTodayStep", Context.class, new XC_MethodHook() {
//                            @Override
//                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                XposedBridge.log("before get today step");
//                                try {
//                                    throw new Exception("123");
//                                } catch (Exception e) {
//                                    XposedBridge.log(getStackTrace(e.getCause()));
//                                }
//                                super.beforeHookedMethod(param);
//                            }
//
//                            @Override
//                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                XposedBridge.log("after get today step:" + param.getResult());
//                                super.afterHookedMethod(param);
//                            }
//                        });
//
//                        XposedHelpers.findAndHookMethod(helperClass, "getTodayDiscardStep", Context.class, new XC_MethodHook() {
//                            @Override
//                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                super.afterHookedMethod(param);
//                                param.setResult(0);
//                            }
//                        });
//
//                        XposedHelpers.findAndHookMethod(helperClass, "setTodayDiscardStep", Context.class, int.class, new XC_MethodHook() {
//                            @Override
//                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                param.args[1] = 0;
//                                super.beforeHookedMethod(param);
//                            }
//                        });
//
//                        XposedHelpers.findAndHookMethod(helperClass, "getOffsetStep", Context.class, new XC_MethodHook() {
//                            @Override
//                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                super.afterHookedMethod(param);
//                                param.setResult(0);
//                            }
//                        });
//
//                        XposedHelpers.findAndHookMethod(helperClass, "setTodayDiscardStep", Context.class, int.class, new XC_MethodHook() {
//                            @Override
//                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                param.args[1] = 0;
//                                super.beforeHookedMethod(param);
//                            }
//                        });
//                    }

                    Class<?> crashUtils_Class = classLoader.loadClass("com.tencent.gwgo.utils.CrashUtils");
                    if (crashUtils_Class != null) {
                        XposedHelpers.findAndHookMethod(crashUtils_Class, "initBugly", Context.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                param.args[0] = null;
                                super.beforeHookedMethod(param);
                            }
                        });
                    }

                    Class<?> tpConfig_Class = classLoader.loadClass("com.tencent.tpshell.TPConfig");
                    if (tpConfig_Class != null) {
                        XposedHelpers.findAndHookMethod(tpConfig_Class, "useReflectionMode", Context.class, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult(false);
                            }
                        });
                        XposedHelpers.findAndHookMethod(tpConfig_Class, "useShellMode", Context.class, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult(false);
                            }
                        });
                        XposedHelpers.findAndHookMethod(tpConfig_Class, "useTp2Info", Context.class, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult(false);
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
                        XposedHelpers.findAndHookMethod(info_b_Class, "n", Context.class, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult(null);
                            }
                        });

                        // package
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


            //        if (lpparam.processName.equals("com.tencent.gwgo") && lpparam.isFirstApplication) {
//            XposedBridge.log("hook sensor:" + lpparam.packageName + "  " + lpparam.processName + " " + lpparam.isFirstApplication);
//            Class<?> sensorEventQueue_Class = Class.forName("android.hardware.SystemSensorManager$SensorEventQueue");
//            if (sensorEventQueue_Class != null) {
//                XposedBridge.hookAllMethods(sensorEventQueue_Class, "dispatchSensorEvent", new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        sp.reload();
//                        boolean isSwitch = sp.getBoolean("isSwitch", false);
//                        //XposedBridge.log("hook sensor switch:" + isSwitch);
//                        if (isSwitch) {
//                            //先拿manager
//                            Field mfield = param.thisObject.getClass().getSuperclass().getDeclaredField("mManager");
//                            mfield.setAccessible(true);
//                            Object mManager = mfield.get(param.thisObject);
//                            //再拿mmanager下的sensor
//                            Field field = param.thisObject.getClass().getEnclosingClass().getDeclaredField("mHandleToSensor");
//                            field.setAccessible(true);
//                            int handle = (Integer) param.args[0];
//                            Sensor sensor = ((HashMap<Integer, Sensor>) field.get(mManager)).get(handle);
//
//                            if (sensor.getType() == 19 || sensor.getName().equals("Step Counter"))//StepCounter sensor
//                            {
//                                XposedBridge.log("sensor name:" + sensor.getName());
//                                XposedBridge.log("args = " + ((float[]) param.args[1])[0] + " " + ((float[]) param.args[1])[1] + " " + ((float[]) param.args[1])[2] + " " + ((float[]) param.args[1])[3]);
//                                //((float[]) param.args[1])[0] = stepCount;
//                            }
//
////                            int multi = sp.getInt("num", 2);
////                            float origin = ((float[]) param.args[1])[0];
////                            //XposedBridge.log("捉妖开关打开");
////                            //XposedBridge.log("倍数：" + multi);
////                            XposedBridge.log("原步数：" + origin + "  " + "修改后:" + origin * multi);
////                            ((float[]) param.args[1])[0] = origin * multi;
//                        }
//                        super.beforeHookedMethod(param);
//                    }
//                });
//            }

//        }

//                Class<?> nativeBridgeClass = lpparam.classLoader.loadClass("com.tencent.gwgo.NativeBridge");
//                if (nativeBridgeClass != null) {
//                    XposedHelpers.findAndHookMethod(nativeBridgeClass, "sendMessage", String.class, JSONObject.class, new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            if (param.args[0].equals("OnStepCountChanged")) {
//                                JSONObject jsonObject = (JSONObject) param.args[1];
//                                XposedBridge.log("OnStepCountChanged:" + jsonObject.toString());
//                            }
//                            super.beforeHookedMethod(param);
//                        }
//                    });
//                }
        }
    }
}
