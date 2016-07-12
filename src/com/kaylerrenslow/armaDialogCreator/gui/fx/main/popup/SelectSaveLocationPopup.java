package com.kaylerrenslow.armaDialogCreator.gui.fx.main.popup;

import com.kaylerrenslow.armaDialogCreator.gui.fx.popup.StagePopup;
import com.kaylerrenslow.armaDialogCreator.main.ArmaDialogCreator;
import com.kaylerrenslow.armaDialogCreator.main.Lang;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.File;

/**
 Created by Kayler on 05/26/2016.
 */
public class SelectSaveLocationPopup extends StagePopup<VBox> {

	private TextField tfAppDataSaveDir = new TextField();
	private TextField tfA3ToolsDir = new TextField();

	private Button btnChangeAppData = new Button(Lang.Popups.SelectSaveLocation.BTN_CHANGE);
	private Button btnChangeA3Tools = new Button(Lang.Popups.SelectSaveLocation.BTN_CHANGE);
	
	private boolean a3AppSaveDirGood = false;
	private boolean a3ToolsDirGood = true;

	/** True if the popup is closing and no action should be performed on exit, false otherwise. */
	private boolean cancel = false;

	/**
	 Creates the "change directories" popup
	 */
	public SelectSaveLocationPopup(@NotNull File initialDirectoryAppDataSave, @Nullable File a3ToolsDir) {
		super(ArmaDialogCreator.getPrimaryStage(), new VBox(5), Lang.Popups.SelectSaveLocation.POPUP_TITLE);
		initialize(initialDirectoryAppDataSave, a3ToolsDir);
		myStage.setMinWidth(600d);
		myStage.initModality(Modality.APPLICATION_MODAL);
		myStage.initStyle(StageStyle.UTILITY);
	}

	private void initialize(@NotNull File initialAppSaveDirectory, @Nullable File a3ToolsDir) {
		tfA3ToolsDir.setEditable(false);
		tfAppDataSaveDir.setEditable(false);

		if (a3ToolsDir != null) {
			tfA3ToolsDir.setText(a3ToolsDir.getPath());
		}
		a3AppSaveDirGood = initialAppSaveDirectory.exists();
		tfAppDataSaveDir.setText(initialAppSaveDirectory.getPath());

		Label lblAppDataSaveDir = new Label(Lang.Popups.SelectSaveLocation.LBL_APP_DATA_SAVE_DIR);
		Label lblA3ToolsDir = new Label(Lang.Popups.SelectSaveLocation.LBL_A3_TOOLS_DIR);

		
		/*set events*/
		btnChangeAppData.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DirectoryChooser dc = new DirectoryChooser();
				dc.setInitialDirectory(initialAppSaveDirectory);
				dc.setTitle(lblAppDataSaveDir.getText());
				File f = dc.showDialog(myStage);
				if (f == null) {
					return;
				}
				chooseAppDataSaveDir(f);
			}
		});
		btnChangeA3Tools.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DirectoryChooser dc = new DirectoryChooser();
				dc.setInitialDirectory(a3ToolsDir);
				dc.setTitle(lblA3ToolsDir.getText());
				File f = dc.showDialog(myStage);
				if (f == null) {
					return;
				}
				chooseA3ToolsSaveDir(f);
			}
		});
		

		HBox hbTop = new HBox(5);
		hbTop.getChildren().addAll(tfAppDataSaveDir, btnChangeAppData);
		HBox.setHgrow(tfAppDataSaveDir, Priority.ALWAYS);

		HBox hbMid = new HBox(5);
		hbMid.getChildren().addAll(tfA3ToolsDir, btnChangeA3Tools);
		HBox.setHgrow(tfA3ToolsDir, Priority.ALWAYS);


		myRootElement.getChildren().addAll(lblAppDataSaveDir, hbTop, lblA3ToolsDir, hbMid, new Separator(Orientation.HORIZONTAL), getResponseFooter(true, true, true));
		myRootElement.setPadding(new Insets(5));
		myRootElement.setMinHeight(165d);
	}

	private void chooseA3ToolsSaveDir(File f) {
		tfA3ToolsDir.setText(f.getPath());
	}

	private void chooseAppDataSaveDir(File f) {
		a3AppSaveDirGood = true;
		tfAppDataSaveDir.setText(f.getPath());
	}

	@NotNull
	public String getApplicationDataSaveLocationPath() {
		return tfAppDataSaveDir.getText().trim();
	}

	@Nullable
	public String getArma3ToolsDirectoryPath() {
		String s = tfA3ToolsDir.getText().trim();
		if (s.length() == 0 || !a3ToolsDirGood) {
			return null;
		}
		return s;
	}

	@Override
	protected void cancel() {
		cancel = true;
		close();
	}

	@Override
	protected void onCloseRequest(WindowEvent event) {
		if (a3AppSaveDirGood && a3ToolsDirGood) {
			super.onCloseRequest(event);
		} else {
			event.consume();
			btnChangeAppData.requestFocus();
			Toolkit.getDefaultToolkit().beep();
		}
	}

	@Override
	protected void closing() {
		if (cancel) {
			return;
		}
		ArmaDialogCreator.getSaveDataManager().setAppSaveDataLocation(new File(getApplicationDataSaveLocationPath()));
		if (getArma3ToolsDirectoryPath() != null) {
			ArmaDialogCreator.getSaveDataManager().setArma3ToolsLocation(new File(getArma3ToolsDirectoryPath()));
		}
	}

}
