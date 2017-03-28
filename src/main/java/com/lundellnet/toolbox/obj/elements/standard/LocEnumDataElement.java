/*
 Copyright 2017 Appropriate Technologies LLC.

 This file is part of toolbox-obj, a component of the Lundellnet Java Toolbox.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.lundellnet.toolbox.obj.elements.standard;

import com.lundellnet.toolbox.obj.elements.LocatableElement;
import com.lundellnet.toolbox.obj.elements.standard.builders.StdEnumElementBuilder;
import com.lundellnet.toolbox.obj.elements.standard.configs.StdEnumElementConf;

@FunctionalInterface
public interface LocEnumDataElement <T, D extends Enum<D>>
		extends EnumDataElement<T, D>, LocatableElement<T, T, StdEnumElementConf<T, D>>
{
	public static <T, D extends Enum<D>>
			StdEnumElementBuilder<T, D, LocEnumDataElement<T, D>> builder() 
	{ return LocEnumDataElement::createElement; }
	
	@SuppressWarnings("unchecked")
	public static <T, D extends Enum<D>>
			LocEnumDataElement<T, D> createElement(StdEnumElementConf<?, D> elementConfig)
	{ return () -> (StdEnumElementConf<T, D>) elementConfig; }
}
