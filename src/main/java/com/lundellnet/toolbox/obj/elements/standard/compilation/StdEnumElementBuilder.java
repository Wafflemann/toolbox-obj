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
package com.lundellnet.toolbox.obj.elements.standard.compilation;

import java.lang.reflect.Field;
import java.util.function.Supplier;

import com.lundellnet.toolbox.obj.elements.compilation.ElementBuilder;
import com.lundellnet.toolbox.obj.elements.standard.EnumDataElement;
import com.lundellnet.toolbox.obj.elements.standard.configs.StdEnumElementConf;

@FunctionalInterface
public interface StdEnumElementBuilder <T, D extends Enum<D>, E extends EnumDataElement<T, D>>
		extends ElementBuilder<StdEnumElementConf<T, D>, E>
{
	default E build(Class<?> parentClass, Supplier<?> parentSupplier, Field elementField, Class<D> enumClass, Field enumConstField)
			{ return build(new StdEnumElementConf<>(parentClass, parentSupplier, elementField, enumClass, enumConstField)); }
}
