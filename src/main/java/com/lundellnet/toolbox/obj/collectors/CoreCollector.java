/*
 Copyright 2017 Appropriate Technologies LLC.

 This file is part of toolbox-obj, a component of the Lundellnet Java Toolbox.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.lundellnet.toolbox.obj.collectors;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import com.lundellnet.toolbox.obj.collections.DataElementCollection;
import com.lundellnet.toolbox.obj.parsers.ParsingTools;

public interface CoreCollector <T, P extends ParsingTools.Parser<T, C, ?, R>, C extends DataElementCollection<R, ?>, R>
		extends java.util.stream.Collector<T, P, R>
{
	static final Set<Characteristics> DEFAULT_COLLECTOR_CHARACTERISTICS =
			((Supplier<Set<Characteristics>>) () -> new HashSet<Characteristics>(Arrays.asList(CONCURRENT, UNORDERED))).get();

	@Override
	default Set<Characteristics> characteristics() {
		return DEFAULT_COLLECTOR_CHARACTERISTICS;
	}
}
