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

import com.github.davityle.ngprocessor.model.Scope;
import com.github.davityle.ngprocessor.util.ElementUtils;
import com.github.davityle.ngprocessor.util.TypeUtils;

import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by tyler on 3/30/15.
 */
public class ScopeSourceLinker {

//    private static final TupleComparator TUPLE_COMPARATOR = new TupleComparator();

//    private final Set<Scope> scopes;
    private final Map<String, Collection<Scope>> scopeMap;
//    private final Map<Element, List<XmlNode>> elementNodeMap;
    private final String manifestPackageName;

    @Inject ElementUtils elementUtils;
    @Inject TypeUtils typeUtils;

    public ScopeSourceLinker(Map<String, Collection<Scope>> scopeMap, String manifestPackageName) {
//        this.scopes = scopes;
        this.scopeMap = scopeMap;
//        this.elementNodeMap = elementNodeMap;
        this.manifestPackageName = manifestPackageName;
    }

//    private NgScopeSourceLink getSourceLink(Element scopeClass){
//
//        TypeElement scopeType = (TypeElement) scopeClass;
//        String packageName = elementUtils.getPackageName(scopeType);
//        String className = elementUtils.getClassName(scopeType, packageName);
//        String fullName = elementUtils.getFullName(scopeType);
//        String scopeName = className + ScopeUtils.SCOPE_APPENDAGE;
//        String key = packageName + "." + scopeName;
//
//        // TODO KEY is now layout file
//        Collection<Scope> elements = scopeMap.get(key);
//
//        List<SourceField> fields = new ArrayList<>();
//        for(Element element : elements){
//            Name fieldName = element.getSimpleName();
//            TypeMirror fieldType = element.asType();
//            TypeElement typeElement = typeUtils.asTypeElement(fieldType);
//            String pack = elementUtils.getPackageName(typeElement);
//            String modelName = elementUtils.stripClassName(fieldType);
//            fields.add(new SourceField(fieldName.toString(), pack + '.' + modelName));
//        }
//
//        Element[] els = elements.toArray(new Element[elements.size() + 1]);
//        els[elements.size()] = scopeClass;

//        List<XmlNode> xmlNodes = elementNodeMap.get(scopeClass);
//        Map<String, Set<XmlNode>> layouts = new HashMap<>();
//
//        if(xmlNodes != null) {
            // This could be done by just mapping elements to the layout instead of linking to the
            // node in LayoutScopeMapper
//            for (XmlNode xmlNode : xmlNodes) {
//                String layoutName = xmlNode.getLayoutName();
//                Set<XmlNode> ids = layouts.get(layoutName);
//                if (ids == null) {
//                    ids = new TreeSet<>(TUPLE_COMPARATOR);
//                    layouts.put(layoutName, ids);
//                }
//                ids.add(xmlNode);
//            }
//        }
//        return new NgScopeSourceLink(className, packageName, fullName, fields, els, /*layouts*/ null, manifestPackageName);
//    }

}

//class TupleComparator implements Comparator<XmlNode> {
//    @Override
//    public int compare(XmlNode o1, XmlNode o2) {
//        return o1.getId().compareTo(o2.getId());
//    }
//}
