package com.kaylerrenslow.armaDialogCreator.gui.main.actions.mainMenu.create;

import com.kaylerrenslow.armaDialogCreator.data.Project;
import com.kaylerrenslow.armaDialogCreator.gui.main.popup.newControl.NewCustomControlClassDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 Created by Kayler on 07/11/2016.
 */
public class CreateNewCustomControlAction implements EventHandler<ActionEvent> {
	@Override
	public void handle(ActionEvent event) {
		NewCustomControlClassDialog dialog = new NewCustomControlClassDialog();
		if (dialog.wasCancelled()) {
			return;
		}
		Project.getCurrentProject().addCustomControlClass(dialog.getCustomControlClass());
	}
}
