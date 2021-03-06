/*
 * Copyright 2015 Tyler Davis
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.ngandroid.demo;

import android.test.ActivityInstrumentationTestCase2;

import com.ngandroid.demo.ui.pages.main.DemoActivity;

/**
 * Created by tyler on 3/9/15.
 */
public class ViewBindTests  extends ActivityInstrumentationTestCase2<DemoActivity> {
    public ViewBindTests() {
        super(DemoActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

    }

//    @UiThreadTest
//    public void testSettingValuesAfterInflation(){
//        ViewScope scope = new ViewScope();
//        NgAndroid.getInstance().inflate(scope, LayoutInflater.from(getActivity()), R.layout.test_view, null);
//        scope.note.setId(100);
//        assertEquals(100, scope.note.getId());
//    }
//
//    public void testInflaterFactoryNotReCreated() throws NoSuchFieldException, IllegalAccessException {
//        LayoutInflater inflater = LayoutInflater.from(getActivity());
//
//        AttributeBinder attributeBinder = new AttributeBinder(inflater, new TestScope(), null);
//        Field f = AttributeBinder.class.getDeclaredField("mInflater");
//        f.setAccessible(true);
//        LayoutInflater i1 = (LayoutInflater) f.get(attributeBinder);
//        assertEquals(inflater, i1);
//        BindingInflaterFactory factory1 = (BindingInflaterFactory) i1.getFactory();
//        attributeBinder = new AttributeBinder(inflater, new TestScope(), null);
//        LayoutInflater i2 = (LayoutInflater) f.get(attributeBinder);
//        assertEquals(inflater, i2);
//        assertEquals(factory1, i2.getFactory());
//    }




}
