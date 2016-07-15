package com.kaylerrenslow.armaDialogCreator.gui.fx.main.popup;

import com.kaylerrenslow.armaDialogCreator.control.Macro;
import com.kaylerrenslow.armaDialogCreator.main.ArmaDialogCreator;
import com.kaylerrenslow.armaDialogCreator.main.Lang;


/**
 @author Kayler
 Popup for creating a new macro.
 Created on 07/10/2016. */
public class NewMacroPopup extends MacroEditBasePopup {

	public NewMacroPopup() {
		myStage.setTitle(Lang.Popups.MacroNew.POPUP_TITLE);
	}

	@Override
	protected void ok() {
		Macro macro = getMacro();
		if (macro != null) {
			ArmaDialogCreator.getApplicationData().getMacroRegistry().getMacros().add(macro);
		}
		close();
	}
}