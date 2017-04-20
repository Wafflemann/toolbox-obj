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
package com.lundellnet.toolbox.obj.elements.stream_in;

import java.util.stream.Stream;

import com.lundellnet.toolbox.obj.elements.LocatableElement;
import com.lundellnet.toolbox.obj.elements.stream_in.compilation.StrEnumElementBuilder;
import com.lundellnet.toolbox.obj.elements.stream_in.configs.StrEnumElementConf;

@FunctionalInterface
public interface LocEnumDataStreamInElement <T, R, D extends Enum<D>>
		extends EnumDataStreamInElement<T, R, D>, LocatableElement<Stream<T>, R, StrEnumElementConf<T, R, D>>
{
	public static <T, R, D extends Enum<D>>
			StrEnumElementBuilder<T, R, D, LocEnumDataStreamInElement<T, R, D>> builder()
	{ return LocEnumDataStreamInElement::createElement; }
	
	@SuppressWarnings("unchecked")
	public static <T, R, D extends Enum<D>>
			LocEnumDataStreamInElement<T, R, D> createElement(StrEnumElementConf<?, ?, D> elementConf)
	{ return () -> (StrEnumElementConf<T, R, D>) elementConf; }
}
