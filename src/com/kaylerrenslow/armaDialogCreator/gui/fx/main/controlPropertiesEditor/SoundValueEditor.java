/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.kaylerrenslow.armaDialogCreator.gui.fx.main.controlPropertiesEditor;

import com.kaylerrenslow.armaDialogCreator.control.sv.ASound;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.inputfield.ArmaStringChecker;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.inputfield.DoubleChecker;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.inputfield.InputField;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.inputfield.StringChecker;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 Created by Kayler on 07/13/2016.
 */
public class SoundValueEditor implements ValueEditor<ASound> {
	
	protected InputField<ArmaStringChecker, String> inSoundName = new InputField<>(new ArmaStringChecker());
	protected InputField<DoubleChecker, Double> inDb = new InputField<>(new DoubleChecker());
	protected InputField<DoubleChecker, Double> inPitch = new InputField<>(new DoubleChecker());
	private FlowPane flowPane = new FlowPane(5, 10, inSoundName, inDb, inPitch);
	
	private final InputField<StringChecker, String> overrideField = new InputField<>(new StringChecker());
	private final StackPane masterPane = new StackPane(flowPane);
	
	public SoundValueEditor() {
		flowPane.setPrefWrapLength(300d);
	}
	
	@Override
	public ASound getValue() {
		if (inSoundName.getValue() == null) {
			return null;
		}
		if (inDb.getValue() == null) {
			return null;
		}
		if (inPitch.getValue() == null) {
			return null;
		}
		return new ASound(inSoundName.getValue(), inDb.getValue(), inPitch.getValue());
	}
	
	@Override
	public void setValue(@Nullable ASound val) {
		if (val == null) {
			inSoundName.setValue(null);
			inDb.setValue(null);
			inPitch.setValue(null);
		} else {
			inSoundName.setValue(val.getSoundName());
			inDb.setValue(val.getDb());
			inPitch.setValue(val.getPitch());
		}
	}
	
	@Override
	public @NotNull Node getRootNode() {
		return masterPane;
	}
	
	@Override
	public void setToOverride(boolean override) {
		masterPane.getChildren().clear();
		if (override) {
			masterPane.getChildren().add(overrideField);
		} else {
			masterPane.getChildren().add(flowPane);
		}
	}
	
	@Override
	public InputField<StringChecker, String> getOverrideTextField() {
		return overrideField;
	}
	
	@Override
	public void focusToEditor() {
		if (inSoundName.getValue() == null) {
			inSoundName.requestFocus();
		} else if (inDb.getValue() == null) {
			inDb.requestFocus();
		} else if (inPitch.getValue() == null) {
			inPitch.requestFocus();
		} else {
			inSoundName.requestFocus();
		}
	}
}
