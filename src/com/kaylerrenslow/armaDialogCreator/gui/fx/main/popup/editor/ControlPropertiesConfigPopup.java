/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.kaylerrenslow.armaDialogCreator.gui.fx.main.popup.editor;

import com.kaylerrenslow.armaDialogCreator.arma.control.ArmaControl;
import com.kaylerrenslow.armaDialogCreator.arma.control.ArmaControlGroup;
import com.kaylerrenslow.armaDialogCreator.arma.control.ArmaDisplay;
import com.kaylerrenslow.armaDialogCreator.control.ControlProperty;
import com.kaylerrenslow.armaDialogCreator.control.sv.AColor;
import com.kaylerrenslow.armaDialogCreator.gui.fx.main.controlPropertiesEditor.ControlPropertiesEditorPane;
import com.kaylerrenslow.armaDialogCreator.gui.fx.popup.StagePopup;
import com.kaylerrenslow.armaDialogCreator.gui.fx.popup.StagePopupUndecorated;
import com.kaylerrenslow.armaDialogCreator.main.ArmaDialogCreator;
import com.kaylerrenslow.armaDialogCreator.main.lang.Lang;
import com.kaylerrenslow.armaDialogCreator.util.ValueListener;
import com.kaylerrenslow.armaDialogCreator.util.ValueObserver;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 @author Kayler
 Used for editing a control and it's control properties.
 Created on 05/31/2016. */
public class ControlPropertiesConfigPopup extends StagePopupUndecorated<VBox> {
	private ArmaControl control;
	private ControlPropertiesEditorPane editorPane;
	
	public ControlPropertiesConfigPopup(@NotNull ArmaControl control) {
		super(ArmaDialogCreator.getPrimaryStage(), new VBox(5), null);
		initializePopup();
		editorPane = new ControlPropertiesEditorPane(control);
		initializeToControl(control);
	}
	
	private void initializePopup() {
		myRootElement.getStyleClass().add("rounded-node");
		myStage.initStyle(StageStyle.TRANSPARENT);
		
		myScene.setFill(Color.TRANSPARENT);
		final double padding = 20.0;
		myRootElement.setPadding(new Insets(padding, padding, padding, padding));
	}
	
	/**
	 Configures the popup to edit the given control.
	 
	 @return true if the initialization was successful, or false if the initialization was canceled
	 */
	public boolean initializeToControl(ArmaControl c) {
		if (myRootElement.getChildren().size() > 0) {
			if (!editorPane.allValuesAreGood()) {
				return false;
			}
		}
		this.control = c;
		Color bg = control.getRenderer().getBackgroundColor();
		control.getRenderer().getBackgroundColorObserver().addValueListener(new ValueListener<AColor>() {
			@Override
			public void valueUpdated(@NotNull ValueObserver<AColor> observer, AColor oldValue, AColor newValue) {
				if (newValue != null) {
					setBorderColor(newValue.toJavaFXColor()); //update the popup's border color
				}
			}
		});
		setBorderColor(bg);
		myRootElement.getChildren().clear();
		addCloseButton();
		myRootElement.getChildren().add(editorPane);
		
		CheckBox cbIsBackgroundControl = new CheckBox(Lang.Popups.ControlPropertiesConfig.IS_BACKGROUND_CONTROL);
		cbIsBackgroundControl.setSelected(c.isBackgroundControl());
		cbIsBackgroundControl.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean selected) {
				ArmaDisplay display = c.getDisplay();
				if (c.getParent() instanceof ArmaControlGroup) {
					MoveOutOfControlGroupPopup popup = new MoveOutOfControlGroupPopup(c);
					popup.showAndWait();
					if (popup.isMoveOut()) {
						ArmaControlGroup group = (ArmaControlGroup) c.getParent();
						group.getControls().remove(c);
					}
				} else if (c.getParent() instanceof ArmaDisplay) {
					if (selected) {
						display.getControls().remove(c);
					} else {
						display.getBackgroundControls().remove(c);
					}
				} else {
					throw new IllegalStateException("unknown parent type:" + c.getParent().getClass().getName());
				}
				
				if (selected) {
					display.getBackgroundControls().add(c);
				} else {
					display.getControls().add(c);
				}
			}
		});
		
		myRootElement.getChildren().add(cbIsBackgroundControl);
		
		return true;
	}
	
	
	private void addCloseButton() {
		Button btnClose = new Button("x");
		btnClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				WindowEvent windowEvent = new WindowEvent(myStage, WindowEvent.WINDOW_CLOSE_REQUEST);
				myStage.getOnCloseRequest().handle(windowEvent);
				List<ControlProperty> missing = editorPane.getMissingProperties();
				boolean goodValues = missing.size() == 0;
				if (!windowEvent.isConsumed() && goodValues) {
					close();
				}
				if (!goodValues) {
					StagePopup popup = new MissingControlPropertiesConfigPopup(myStage, missing);
					popup.show();
					popup.requestFocus();
				}
			}
		});
		btnClose.getStyleClass().add("close-button");
		
		ComboBox<String> cbExtendClass = new ComboBox<>(FXCollections.observableArrayList("-", "RscStatic", "RscPicture"));
		cbExtendClass.getSelectionModel().select(0);
		Label lblExtendClass = new Label(Lang.Popups.ControlPropertiesConfig.EXTEND_CLASS, cbExtendClass);
		
		myRootElement.getChildren().add(new BorderPane(null, null, btnClose, null, lblExtendClass));
	}
	
	private void setBorderColor(Color bg) {
		myRootElement.setStyle(String.format("-fx-border-color: rgba(%f%%,%f%%,%f%%,%f);", bg.getRed() * 100.0, bg.getGreen() * 100.0, bg.getBlue() * 100.0, bg.getOpacity()));
	}
	
	@Override
	protected void onCloseRequest(WindowEvent event) {
		super.onCloseRequest(event);
	}
	
	public ArmaControl getControl() {
		return control;
	}
	
	private static class MoveOutOfControlGroupPopup extends StagePopup<VBox> {
		
		private boolean moveOut = false;
		
		public MoveOutOfControlGroupPopup(ArmaControl c) {
			super(ArmaDialogCreator.getPrimaryStage(), new VBox(5), Lang.Popups.ControlPropertiesConfig.MoveOutOfGroupPopup.POPUP_TITLE);
			myRootElement.getChildren().addAll(
					new Label(String.format(Lang.Popups.ControlPropertiesConfig.MoveOutOfGroupPopup.MESSAGE_F, c.getClassName())),
					new Separator(Orientation.HORIZONTAL),
					getResponseFooter(true, true, false)
			);
			myStage.initModality(Modality.APPLICATION_MODAL);
			myStage.initStyle(StageStyle.UTILITY);
			myRootElement.setPadding(new Insets(10d));
			btnOk.setText(Lang.Confirmation.YES);
			btnCancel.setText(Lang.Confirmation.NO);
		}
		
		@Override
		protected void ok() {
			moveOut = true;
			super.ok();
		}
		
		public boolean isMoveOut() {
			return moveOut;
		}
	}
	
}
