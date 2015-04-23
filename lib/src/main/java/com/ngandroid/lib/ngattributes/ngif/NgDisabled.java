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

package com.ngandroid.lib.ngattributes.ngif;

import android.view.View;

import com.ngandroid.lib.R;
import com.ngandroid.lib.ng.Model;
import com.ngandroid.lib.ng.ModelMethod;

/**
 * Created by tyler on 2/10/15.
 */
public class NgDisabled extends NgIf {
    private static NgDisabled ngDisabled = new NgDisabled();
    private NgDisabled(){}

    public static NgDisabled getInstance(){
        return ngDisabled;
    }

    @Override
    protected ModelMethod getModelMethod(final Model model, final View view, final String field) {
        return new ModelMethod() {
            @Override
            public Object invoke(String fieldName, Object... args) {
                try {
                    if(model.getValue(field)){
                        view.setEnabled(false);
                    }else{
                        view.setEnabled(true);
                    }
                } catch (Throwable throwable) {
                    // TODO - error
                    throwable.printStackTrace();
                }
                return null;
            }
        };
    }

    @Override
    public int getAttribute() {
        return R.styleable.ngAndroid_ngDisabled;
    }
}
