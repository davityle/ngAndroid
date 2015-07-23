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

package com.github.davityle.ngprocessor.map;

import com.github.davityle.ngprocessor.util.ElementUtils;
import com.github.davityle.ngprocessor.util.MessageUtils;
import com.github.davityle.ngprocessor.util.TypeUtils;
import com.github.davityle.ngprocessor.xml.XmlAttribute;
import com.github.davityle.ngprocessor.xml.XmlNode;
import com.github.davityle.ngprocessor.xml.XmlScope;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.lang.model.element.Element;

public class LayoutScopeMapper {

    private final Set<Map.Entry<String, Collection<XmlScope>>> xmlLayouts;
    private final Map<Element, List<XmlNode>> elementNodeMap = new HashMap<>();
    private final List<Element> scopes;
    private boolean isMapped;

    @Inject
    ElementUtils elementUtils;
    @Inject
    MessageUtils messageUtils;
    @Inject
    TypeUtils typeUtils;

    public LayoutScopeMapper(List<Element> scopes, Map<String, Collection<XmlScope>> fileNodeMap){
        this.scopes = scopes;
        this.xmlLayouts = fileNodeMap.entrySet();
    }


    public Map<Element, List<XmlNode>> getElementNodeMap(){
        if(!isMapped){
            linkLayouts();
        }
        return elementNodeMap;
    }

    private void linkLayouts(){
        final Map<XmlNode, Boolean> viewScopeMap = new HashMap<XmlNode, Boolean>();

        for (Map.Entry<String, Collection<XmlScope>> layout : xmlLayouts) {
            Collection<XmlScope> views = layout.getValue();
            for (Element scope : scopes) {
                if(scopeMatchesAll(scope, views)){

                }
            }
        }
        isMapped = true;
    }

    private boolean scopeMatchesAll(Element scope, Collection<XmlScope> views) {
        for(XmlScope node : views){
            for(XmlAttribute attribute : node.getAttributes()){
                
            }
        }
        return false;
    }

    private void put(XmlNode view, Element element){
            List<XmlNode> nodes =  elementNodeMap.get(element);
            if(nodes == null){
                nodes = new ArrayList<>();
                elementNodeMap.put(element, nodes);
            }
            nodes.add(view);
    }
}