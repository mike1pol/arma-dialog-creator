/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.kaylerrenslow.armaDialogCreator.gui.fx.main;

import com.kaylerrenslow.armaDialogCreator.gui.fx.main.editor.SnapConfiguration;
import com.kaylerrenslow.armaDialogCreator.gui.fx.main.treeview.EditorComponentTreeView;
import com.kaylerrenslow.armaDialogCreator.gui.fx.main.treeview.TreeItemEntry;
import com.kaylerrenslow.armaDialogCreator.main.lang.Lang;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 @author Kayler
 Holds the step configuration contorls and the tree view for editor components.
 Created on 05/15/2016. */
class CanvasControls extends VBox implements SnapConfiguration {

	private final ADCCanvasView canvasView;
	private final EditorComponentTreeView<? extends TreeItemEntry> editorComponentTreeView = new EditorComponentTreeView<>();
	private final ChoiceBox<Percentage> cbAltStep = new ChoiceBox<>();
	private final ChoiceBox<Percentage> cbStep = new ChoiceBox<>();

	public static final double PREFERRED_WIDTH = 250d;
	
	CanvasControls(ADCCanvasView canvasView) {
		super(5);
		this.canvasView = canvasView;
		initializeUI();
	}

	private void initializeUI() {
		initializeStepChoiceboxes();
		HBox hboxStep = new HBox(5);
		hboxStep.getChildren().addAll(new Label(Lang.CanvasControls.STEP), cbStep, new Label(Lang.CanvasControls.ALT_STEP), cbAltStep);

		getChildren().addAll(hboxStep, editorComponentTreeView);
		this.setPadding(new Insets(5, 5, 0, 5));

		VBox.setVgrow(editorComponentTreeView, Priority.ALWAYS);
		
		//setup tree view to show all controls
		
		
	}

	private void initializeStepChoiceboxes() {
		cbStep.getItems().addAll(new Percentage(1), new Percentage(2.50), new Percentage(5), new Percentage(10));
		cbAltStep.getItems().addAll(new Percentage(0.5));
		for (int i = 1; i <= 10; i++) {
			cbAltStep.getItems().add(new Percentage(i));
		}
		cbStep.getSelectionModel().select(1);
		cbAltStep.getSelectionModel().selectLast();
		cbStep.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Percentage>() {
			@Override
			public void changed(ObservableValue<? extends Percentage> observable, Percentage oldValue, Percentage newValue) {
				canvasView.repaintCanvas();
			}
		});
	}


	@Override
	public double alternateSnapPercentage() {
		return cbAltStep.getSelectionModel().getSelectedItem().percentDecimal;
	}

	@Override
	public double snapPercentage() {
		return cbStep.getSelectionModel().getSelectedItem().percentDecimal;
	}


	public EditorComponentTreeView<? extends TreeItemEntry> getEditorComponentTreeView() {
		return editorComponentTreeView;
	}

	private static class Percentage {
		private final double percentDecimal;
		private final String str;

		Percentage(double value) {
			this.percentDecimal = value / 100.0;
			this.str = value + "%";
		}

		@Override
		public String toString() {
			return str;
		}
	}

}
