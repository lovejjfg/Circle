/*
 * Copyright 2017 Joe.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lovejjfg.circle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 2017/2/9.
 * Email lovejjfg@gmail.com
 */

public class ShakeHelper implements SensorEventListener, DialogInterface.OnDismissListener {
    private static final String GLIDE_FRAGMENT = "com.bumptech.glide.supportManager.SupportRequestManagerFragment";
    private AlertDialog dialog;
    private StringBuilder sb;
    private Context context;
    private SensorManager mSensorManager;
//    private FragmentManager supportManager;

    private boolean isEnable = true;
    private FragmentsHandler supportFragmentHandler;
    private FragmentsHandler fragmentHandler;
    //    private android.app.FragmentManager fragmentManager;

    private ShakeHelper(Context context) {
        this.context = context;
        dialog = new AlertDialog.Builder(context).create();
        dialog.setOnDismissListener(this);
        sb = new StringBuilder();
        if (context instanceof FragmentActivity) {
            FragmentManager supportManager = ((FragmentActivity) context).getSupportFragmentManager();
            supportFragmentHandler = new SupportFragmentHandler(supportManager);
        } else if (context instanceof Activity) {
            android.app.FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
            fragmentHandler = new FragmentHandler(fragmentManager);
//            fragmentManager.getFragment();
        }

    }

    //摇一摇是否可用，默认可用
    @SuppressWarnings("unused")
    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    //获取摇一摇的实例
    public static ShakeHelper initShakeHelper(Context context) {
        return new ShakeHelper(context);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        sb.delete(0, sb.length());
    }

    //回调Activity的onStart()
    public void onStart() {
        //获取 SensorManager 负责管理传感器
        mSensorManager = ((SensorManager) context.getSystemService(Context.SENSOR_SERVICE));
        if (isEnable && mSensorManager != null) {
            //获取加速度传感器
            Sensor mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mAccelerometerSensor != null) {
                mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    //回调Activity的onPause()
    public void onPause() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        if (type == Sensor.TYPE_ACCELEROMETER) {//accelerometer
            //获取三个方向值
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            if (!dialog.isShowing() && (Math.abs(x) > 17 || Math.abs(y) > 17 || Math
                    .abs(z) > 17)) {
                handleFragment();
            }
        }
    }

    private void handleFragment() {
        if (supportFragmentHandler != null) {
            supportFragmentHandler.handleFragment();
        } else if (fragmentHandler != null) {
            fragmentHandler.handleFragment();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    interface FragmentsHandler {

        void handleFragment();
    }

    private class FragmentHandler implements FragmentsHandler {

        private android.app.FragmentManager manager;

        FragmentHandler(android.app.FragmentManager manager) {
            this.manager = manager;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void handleFragment() {
            List<android.app.Fragment> topFragments = getTopFragments();
            if (topFragments == null) {
                sb.append(context.getClass().getSimpleName());
                dialog.setMessage(sb.toString());
                dialog.show();
                //只有Activity不包含Fragment
                return;
            }
            //从最top的Fragment回溯parent，到了root的时候结束。
            ArrayList<android.app.Fragment> names;
            for (android.app.Fragment topFragment : topFragments) {
                //Glide 使用Fragment来控制相关的request，不再考虑的范围内
                if (GLIDE_FRAGMENT.equals(topFragment.getClass().getName())) {
                    continue;
                }
                //先添加Activity的名称
                sb.append(context.getClass().getSimpleName());
                names = new ArrayList<>();
                //倒序找ParentFragment
                while (topFragment != null) {
                    names.add(0, topFragment);//反转顺序
                    topFragment = topFragment.getParentFragment();
                }
                int length = names.size();
                for (int i = 0; i < length; i++) {
                    android.app.Fragment name = names.get(i);
                    sb.append("\n");
                    for (int j = 0; j <= i; j++) {
                        sb.append(">");
                    }
                    sb.append(name.getClass().getSimpleName());
                }
                sb.append("\n\n");

            }
            if (sb.length() == 0) {
                sb.append(context.getClass().getSimpleName());
            }
            dialog.setMessage(sb.toString());
            dialog.show();
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        android.app.Fragment getTopFragment(android.app.FragmentManager manager) {
            int size = manager.getBackStackEntryCount();
            if (size == 0) {
                return null;
            }
            for (int i = size - 1; i >= 0; i--) {
                android.app.FragmentManager.BackStackEntry backEntry = manager.getBackStackEntryAt(size);
                String tag = backEntry.getName();
                android.app.Fragment f = manager.findFragmentByTag(tag);
                if (f.isAdded() && !f.isHidden() && f.getUserVisibleHint()) {
                    android.app.Fragment tTopFragment = getTopFragment(f.getChildFragmentManager());
                    return tTopFragment == null ? f : tTopFragment;
                }
            }
            return null;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        private List<android.app.Fragment> getTopFragments() {
            if (manager == null) {
                return null;
            }

            List<android.app.Fragment> topFragments = new ArrayList<>();
            int size = manager.getBackStackEntryCount();
            if (size == 0) {
                return null;
            }
            for (int i = size - 1; i >= 0; i--) {
                android.app.FragmentManager.BackStackEntry backEntry = manager.getBackStackEntryAt(size);
                String tag = backEntry.getName();
                android.app.Fragment f = manager.findFragmentByTag(tag);

                if (f.isAdded() && !f.isHidden() && f.getUserVisibleHint()) {
                    android.app.Fragment t = getTopFragment(f.getChildFragmentManager());//递归
                    if (t != null) {
                        topFragments.add(t);
                    } else {
                        topFragments.add(f);
                    }
                }

            }
            return topFragments;
        }
    }

    private class SupportFragmentHandler implements FragmentsHandler {

        private FragmentManager manager;

        SupportFragmentHandler(FragmentManager manager) {
            this.manager = manager;
        }

        @Override
        public void handleFragment() {
            List<Fragment> topFragments = getSupportTopFragments();
            if (topFragments == null) {
                sb.append(context.getClass().getSimpleName());
                dialog.setMessage(sb.toString());
                dialog.show();
                //只有Activity不包含Fragment
                return;
            }
            //从最top的Fragment回溯parent，到了root的时候结束。
            ArrayList<Fragment> names;
            for (Fragment topFragment : topFragments) {
                //Glide 使用Fragment来控制相关的request，不再考虑的范围内
                if (GLIDE_FRAGMENT.equals(topFragment.getClass().getName())) {
                    continue;
                }
                //先添加Activity的名称
                sb.append(context.getClass().getSimpleName());
                names = new ArrayList<>();
                //倒序找ParentFragment
                while (topFragment != null) {
                    names.add(0, topFragment);//反转顺序
                    topFragment = topFragment.getParentFragment();
                }
                int length = names.size();
                for (int i = 0; i < length; i++) {
                    Fragment name = names.get(i);
                    sb.append("\n");
                    for (int j = 0; j <= i; j++) {
                        sb.append(">");
                    }
                    sb.append(name.getClass().getSimpleName());
                }
                sb.append("\n\n");

            }
            if (sb.length() == 0) {
                sb.append(context.getClass().getSimpleName());
            }
            dialog.setMessage(sb.toString());
            dialog.show();
        }

        @Nullable
        private List<Fragment> getSupportTopFragments() {
            if (manager == null) {
                return null;
            }
            List<Fragment> fragments = manager.getFragments();
            List<Fragment> topFragments = new ArrayList<>();
            if (fragments == null) {
                return null;
            }
            int size = fragments.size();
            for (int i = size - 1; i >= 0; i--) {
                Fragment f = fragments.get(i);
                if (f.isAdded() && !f.isHidden() && f.getUserVisibleHint()) {
                    Fragment t = getSupportTopFragment(f.getChildFragmentManager());//递归
                    if (t != null) {
                        topFragments.add(t);
                    } else {
                        topFragments.add(f);
                    }
                }

            }
            return topFragments;
        }

        @Nullable
        private Fragment getSupportTopFragment(FragmentManager manager) {
            List<Fragment> fragments = manager.getFragments();
            if (fragments == null) {
                return null;
            }
            int size = fragments.size();
            for (int i = size - 1; i >= 0; i--) {
                Fragment f = fragments.get(i);
                if (f.isAdded() && !f.isHidden() && f.getUserVisibleHint()) {
                    Fragment tTopFragment = getSupportTopFragment(f.getChildFragmentManager());
                    return tTopFragment == null ? f : tTopFragment;
                }
            }
            return null;
        }
    }


}
