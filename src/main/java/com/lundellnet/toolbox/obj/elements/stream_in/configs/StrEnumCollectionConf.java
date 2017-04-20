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
package com.lundellnet.toolbox.obj.elements.stream_in.configs;

import com.lundellnet.toolbox.obj.collections.configs.EnumDataCollectionConf;
import com.lundellnet.toolbox.obj.elements.stream_in.EnumDataStreamInElement;
import com.lundellnet.toolbox.obj.elements.stream_in.compilation.StrEnumElementBuilder;

public interface StrEnumCollectionConf <D extends Enum<D>, R, E extends EnumDataStreamInElement<?, ?, D>>
		extends StrDataCollectionConf<R, E, StrEnumElementBuilder<?, ?, D, E>>, EnumDataCollectionConf<D, R, E, StrEnumElementBuilder<?, ?, D, E>>
{}
