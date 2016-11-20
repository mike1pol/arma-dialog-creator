/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.kaylerrenslow.armaDialogCreator.data;

import com.kaylerrenslow.armaDialogCreator.control.Macro;
import com.kaylerrenslow.armaDialogCreator.control.MacroRegistry;
import com.kaylerrenslow.armaDialogCreator.control.sv.SVNumber;
import com.kaylerrenslow.armaDialogCreator.expression.Value;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 Holds all macros for the current {@link Project}.

 @author Kayler
 @since 07/05/2016. */
public class ProjectMacroRegistry implements MacroRegistry {

	private final List<Macro> macros = new ArrayList<>();

	ProjectMacroRegistry() {
	}

	public List<Macro> getMacros() {
		return macros;
	}

	/**
	 Get an {@link Value} instance by finding the {@link Macro} instance where it's {@link Macro#getKey()}.equals(identifier). Only Macro's where their value extends {@link SVNumber} are allowed.

	 @return {@link Nullable} instance, or null if no identifier matches a macro where it's value extends {@link SVNumber}
	 */
	public Value.NumVal getMacroValue(String identifier) {
		for (Macro m : macros) {
			if (m.getKey().equals(identifier)) {
				if (m.getValue() instanceof SVNumber) {
					return new Value.NumVal(((SVNumber) m.getValue()).getNumber());
				}
			}
		}
		return null;
	}

	@Override
	@Nullable
	public Macro findMacroByKey(@NotNull String macroKey) {
		for (Macro macro : getMacros()) {
			if (macro.getKey().equals(macroKey)) {
				return macro;
			}
		}
		return null;
	}
}