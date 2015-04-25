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

package com.ngandroid.lib.ngattributes;

import android.view.View;

import com.ngandroid.lib.R;
import com.ngandroid.lib.ng.Scope;
import com.ngandroid.lib.utils.Tuple;

/**
 * Created by tyler on 1/28/15.
 */
class NgLongClick implements NgAttribute {
    private static NgLongClick ourInstance = new NgLongClick();

    static NgLongClick getInstance() {
        return ourInstance;
    }

    private NgLongClick() {
    }

    @Override
    public void attach(Scope scope, View view, int layoutId, int viewId, Tuple<String, String>[] models) {
        NgClick.getInstance().attach(scope, view, layoutId, viewId, getAttribute(), true);
    }

    @Override
    public int getAttribute() {
        return R.styleable.ngAndroid_ngLongClick;
    }
}
