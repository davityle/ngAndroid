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

package com.github.davityle.ngprocessor.util;

import com.github.davityle.ngprocessor.attrcompiler.sources.Source;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

/**
 * Created by tyler on 3/30/15.
 */
public class ElementUtils {

    private static Elements elementUtils;

    public static void setElements(Elements elements){
        elementUtils = elements;
    }

    public static boolean isSetter(Element elem){
        if(elem == null)
            return false;
        String name = elem.getSimpleName().toString();
        return ExecutableElement.class.isInstance(elem)
                && elem.getKind() == ElementKind.METHOD
                && name.startsWith("set")
                && name.length() > 3
                && Character.isUpperCase(name.charAt(3))
                && ((ExecutableElement) elem).getParameters().size() == 1;
    }

    public static boolean isGetter(Element elem){
        if(elem == null)
            return false;
        String name = elem.getSimpleName().toString();
        return ExecutableElement.class.isInstance(elem)
                && elem.getKind() == ElementKind.METHOD
                && name.startsWith("get")
                && name.length() > 3
                && Character.isUpperCase(name.charAt(3))
                && ((ExecutableElement) elem).getReturnType().getKind() != TypeKind.VOID
                && ((ExecutableElement) elem).getParameters().size() == 0;
    }

    public static boolean isGetterForField(Element elem, String field, TypeKind typeKind){
        return elem != null && ExecutableElement.class.isInstance(elem)
                && elem.getKind() == ElementKind.METHOD
                && elem.getSimpleName().toString().toLowerCase().equals("get" + field.toLowerCase())
                && ((ExecutableElement) elem).getReturnType().getKind() == typeKind
                && ((ExecutableElement) elem).getParameters().size() == 0;
    }

    public static TypeMirror getElementType(TypeElement model, String field){
        TypeMirror typeMirror = null;
        String fieldName = field.toLowerCase();
        for(Element f : model.getEnclosedElements()){
            if(f instanceof ExecutableElement) {
                String fName = f.getSimpleName().toString().toLowerCase();
                ExecutableElement exec = (ExecutableElement) f;
                if (fName.equals("set" + fieldName) && isSetter(f)) {
                    TypeMirror setType = exec.getParameters().get(0).asType();
                    if (typeMirror != null) {
                        checkMatch(model, field, typeMirror, setType);
                    }
                    typeMirror = setType;
                } else if (fName.equals("get" + fieldName) && isGetter(f)) {
                    TypeMirror getType = exec.getReturnType();
                    if (typeMirror != null) {
                        checkMatch(model, field, typeMirror, getType);
                    }
                    typeMirror = getType;
                }
            }
        }
        return typeMirror;
    }

    private static void checkMatch(TypeElement model, String field, TypeMirror typeMirror, TypeMirror t){
        if(!TypeUtils.isSameType(typeMirror, t))
            MessageUtils.error(model, "Getter and Setter for field '%s' do not match", field);
    }

    public static boolean hasGetterAndSetter(TypeElement model, String field){
        boolean hasGetter = false, hasSetter = false;
        String fieldName = field.toLowerCase();
        for(Element f : model.getEnclosedElements()){
            String name = f.getSimpleName().toString().toLowerCase();
            if(name.equals("set" + fieldName)){
                hasSetter = true;
                if(hasGetter)
                    break;
            }else if(name.equals("get" + fieldName)){
                hasGetter = true;
                if(hasSetter)
                    break;
            }
        }
        return hasGetter && hasSetter;
    }

    public static Tuple<String,String> getGetAndSetMethodNames(TypeElement model, String field){
        String get = null, set = null;
        String fieldName = field.toLowerCase();
        for(Element f : model.getEnclosedElements()){
            String name = f.getSimpleName().toString().toLowerCase();
            // TODO better check than this - check parameters and so forth
            if(name.equals("get" + fieldName)){
                get = f.getSimpleName().toString();
                if(set != null)
                    break;
            }else if(name.equals("set" + fieldName)){
                set = f.getSimpleName().toString();
                if(get != null)
                    break;
            }
        }
        return Tuple.of(get, set);
    }

    public static boolean returnsVoid(ExecutableElement method){
        TypeMirror type = method.getReturnType();
        return (type != null && type.getKind() == TypeKind.VOID);
    }

    public static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    public static String stripClassName(TypeMirror type) {
        String fullName = type.toString();
        int pIndex = fullName.lastIndexOf('.');
        if(pIndex == -1 || pIndex == fullName.length() - 1)
            return fullName;
        return fullName.substring(pIndex + 1);
    }

    public static boolean hasAnnotationWithName(Element element, String simpleName) {
        for (AnnotationMirror mirror : element.getAnnotationMirrors()) {
            String annotationName = mirror.getAnnotationType().asElement().getSimpleName().toString();
            if (simpleName.equals(annotationName)) {
                return true;
            }
        }
        return false;
    }

    public static String getFullName(TypeElement type) {
        return type.getQualifiedName().toString();
    }

    public static String getPackageName(Element type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    public static boolean isAccessible(Element element) {
        Set<Modifier> modifiers = element.getModifiers();
        return !(modifiers.contains(Modifier.PRIVATE) || modifiers.contains(Modifier.PROTECTED));
    }
}
