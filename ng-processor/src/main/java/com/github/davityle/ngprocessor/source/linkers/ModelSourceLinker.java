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

package com.github.davityle.ngprocessor.source.linkers;

import com.github.davityle.ngprocessor.source.links.NgModelSourceLink;
import com.github.davityle.ngprocessor.util.ElementUtils;
import com.github.davityle.ngprocessor.util.MessageUtils;
import com.github.davityle.ngprocessor.util.TypeUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by tyler on 3/30/15.
 */
public class ModelSourceLinker {

    private final Map<String, Element> modelBuilderMap;

    @Inject ElementUtils elementUtils;
    @Inject MessageUtils messageUtils;
    @Inject TypeUtils typeUtils;

    public ModelSourceLinker(Map<String, Element> modelBuilderMap) {
        this.modelBuilderMap = modelBuilderMap;
    }

    public List<NgModelSourceLink> getSourceLinks(){
        Set<Map.Entry<String, Element>> models = modelBuilderMap.entrySet();
        List<NgModelSourceLink> modelSourceLinks = new ArrayList<>();
        for(Map.Entry<String, Element> model : models) {
            Element element = model.getValue();
            modelSourceLinks.add(getSourceLink(element));
        }
        return modelSourceLinks;
    }

    private NgModelSourceLink getSourceLink(Element element) {

        TypeMirror fieldType = element.asType();
        TypeElement typeElement = typeUtils.asTypeElement(fieldType);
        String packageName = elementUtils.getPackageName(typeElement);
        String fullName = elementUtils.getFullName(typeElement);

        List<com.github.davityle.ngprocessor.source.SourceField> fields = new ArrayList<com.github.davityle.ngprocessor.source.SourceField>();

        String modelName = elementUtils.stripClassName(fieldType);

        List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
        boolean isInterface = typeElement.getKind().isInterface();

        Set<String> duplicateCheck = new HashSet<>();

        for(int index = 0; index < enclosedElements.size(); index++){
            Element enclosedElement = enclosedElements.get(index);
            if(elementUtils.isSetter(enclosedElement)) {
                ExecutableElement setter = (ExecutableElement) enclosedElement;
                if (!elementUtils.returnsVoid(setter)) {
                    messageUtils.error(element, "Setter '%s' must not return a value", element.toString());
                    continue;
                }
                Set<Modifier> modifiers = setter.getModifiers();
                if(modifiers.contains(Modifier.PRIVATE) || modifiers.contains(Modifier.PROTECTED)){
                    messageUtils.error(setter, "Unable to access field '%s' from scope '%s'. Must have default or public access", element.toString(), element.getEnclosingElement().toString());
                    continue;
                }
                String fName = setter.getSimpleName().toString().substring(3).toLowerCase();

                if(duplicateCheck.contains(fName)){
                    messageUtils.error(setter, "Field '%s' in model '%s' is a duplicate.", setter.getSimpleName().toString().substring(3), fullName);
                    continue;
                }
                duplicateCheck.add(fName);

                TypeMirror typeMirror = setter.getParameters().get(0).asType();
                String type = typeMirror.toString();
                com.github.davityle.ngprocessor.source.SourceField sourceField = new com.github.davityle.ngprocessor.source.SourceField(fName, type);
                sourceField.setSetter(setter.getSimpleName().toString());
                // TODO O(n^2) is the best
                boolean getterFound = false;
                for(Element possGetter : enclosedElements) {
                    if(elementUtils.isGetterForField(possGetter, fName, typeMirror.getKind())){
                        sourceField.setGetter(possGetter.getSimpleName().toString());
                        getterFound = true;
                        break;
                    }
                }
                if(!getterFound){
                    messageUtils.error(setter, "Field '%s' is missing a corresponding getter", fName);
                }
                fields.add(sourceField);
            }
        }
        return new NgModelSourceLink(modelName, packageName, fullName, isInterface, fields, element);
    }
}