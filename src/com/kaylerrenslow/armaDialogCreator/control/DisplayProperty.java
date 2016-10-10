/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.kaylerrenslow.armaDialogCreator.control;

import com.kaylerrenslow.armaDialogCreator.control.sv.SerializableValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 @author Kayler
 A {@link DisplayProperty} is similar to a {@link ControlProperty}. However, {@link DisplayProperty} is used to separate {@link ControlPropertyLookupConstant}
 instances such that {@link DisplayProperty}'s lookups are not from {@link ControlPropertyLookup}
 Created on 09/15/2016.
 */
public class DisplayProperty extends ControlProperty{
	public DisplayProperty(DisplayPropertyLookup propertyLookup, @Nullable SerializableValue value) {
		super(propertyLookup, value);
	}

	public DisplayProperty(DisplayPropertyLookup propertyLookup) {
		super(propertyLookup);
	}

	@NotNull
	@Override
	public DisplayPropertyLookup getPropertyLookup() {
		return (DisplayPropertyLookup) super.getPropertyLookup();
	}
}