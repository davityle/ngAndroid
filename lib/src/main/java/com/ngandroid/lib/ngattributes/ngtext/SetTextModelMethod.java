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

package com.ngandroid.lib.ngattributes.ngtext;

import android.util.Log;
import android.widget.TextView;

import com.ngandroid.lib.ng.ModelMethod;
import com.ngandroid.lib.ng.Scope;

/**
* Created by tyler on 3/10/15.
*/
class SetTextModelMethod implements ModelMethod {


    private final Scope scope;
    private final int layoutId, viewId, attr;
    private final TextView hasText;

    SetTextModelMethod(Scope scope, int layoutId, int viewId, int attr, TextView hasText) {
        this.scope = scope;
        this.layoutId = layoutId;
        this.viewId = viewId;
        this.attr = attr;
        this.hasText = hasText;
    }

    @Override
    public Object invoke(String fieldName, Object... args) {
        try {
            Object value = scope.execute(layoutId, viewId, attr);
            if(value == null){
                Log.e("NgAndroid", "NgText value was null");
                return null;
            }
            hasText.setText(value.toString());
        } catch (Throwable throwable) {
            // TODO
            throwable.printStackTrace();
        }
        return null;
    }
}
