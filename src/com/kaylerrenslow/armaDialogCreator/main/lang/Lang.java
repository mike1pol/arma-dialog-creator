/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.kaylerrenslow.armaDialogCreator.main.lang;

import javafx.stage.FileChooser;

import static com.kaylerrenslow.armaDialogCreator.main.lang.Lang.Application.APPLICATION_NAME;

public interface Lang {

	interface Application {
		String APPLICATION_NAME = "Arma Dialog Creator";
		String VERSION = "0.0.1b";
		String APPLICATION_TITLE = APPLICATION_NAME + " " + VERSION;
	}

	interface Expression {
		String IDENTIFIER_NOT_SET_F = "Identifier '%s' has no value.";
		String ERROR_NO_INPUT = "No expression typed.";
	}

	interface CanvasControls {
		String ALT_STEP = "Alternate Step:";
		String STEP = "Step:";
		String BACKGROUND_CONTROLS = "Background Controls";
		String SHOW = "Show";
		String CONTROLS = "Controls";
	}

	/** {@link com.kaylerrenslow.armaDialogCreator.gui.fx.main.ADCMenuBar} */
	interface MainMenuBar {
		String FILE = "File";
		String FILE_OPEN = "Open";
		String FILE_CLOSE_PROJECT = "Close Project";
		String FILE_SAVE = "Save";
		String FILE_EXPORT = "Export";

		String EDIT = "Edit";
		String EDIT_CHANGES = "View Changes";
		String EDIT_UNDO = "Undo";
		String EDIT_REDO = "Redo";
		String EDIT_UNDO_F = "Undo %s";
		String EDIT_REDO_F = "Redo %s";
		String EDIT_MACROS = "Edit Macros";

		String VIEW = "View";
		String VIEW_PREVIEW = "Arma Preview";
		String VIEW_SHOW_GRID = "Show Grid";
		String VIEW_CHANGE_COLORS = "Change Colors";
		String VIEW_BACKGROUND_IMAGE = "Background Image";
		String VIEW_CHANGE_BACKGROUND_IMAGE1 = "VR";
		String VIEW_CHANGE_BACKGROUND_IMAGE2 = "Altis";
		String VIEW_CHANGE_BACKGROUND_IMAGE3 = "Tanoa";
		String VIEW_CHANGE_BACKGROUND_IMAGE_CUSTOM = "Custom";
		String VIEW_CHANGE_BACKGROUND_NONE = "None";
		String VIEW_ABS_REGION = "Absolute Coordinate Region";
		String VIEW_ABS_REGION_SHOW = "Show";
		String VIEW_ABS_REGION_ALWAYS_FRONT = "Always at Front";
		String VIEW_DARK_THEME = "Use Dark Theme";
		String VIEW_FULL_SCREEN = "Full Screen";
		String VIEW_UI = "User Interface (UI)";

		String SETTINGS = "Settings";
		String SETTINGS_CONFIGURE_DIRS = "Configure Directories";

		String CREATE = "Create";
		String CREATE_MACRO = "New Macro";
		String CREATE_CONTROL_CLASS = "New Control Class";

		String HELP = "Help";
		String HELP_WIKI = "Open Wiki";
	}

	interface Popups {
		String BTN_HELP = "Help";
		String BTN_HELP_TOOLTIP = "Get help with this dialog.";
		String BTN_CANCEL = "Cancel";
		String BTN_OK = "OK";
		String GENERIC_POPUP_TITLE = "Notification";

		/** {@link com.kaylerrenslow.armaDialogCreator.gui.fx.main.popup.CanvasViewColorsPopup} */
		interface Colors {
			String POPUP_TITLE = "Change Canvas Colors";
			String SELECTION = "Selection";
			String GRID = "Grid";
			String ABS_REGION = "Absolute Region";
			String BACKGROUND = "Canvas Background Color";
		}

		/** {@link com.kaylerrenslow.armaDialogCreator.gui.fx.main.popup.SelectSaveLocationPopup} */
		interface SelectSaveLocation {
			String POPUP_TITLE = "Configure Directories";
			String LBL_APP_DATA_SAVE_DIR = "Application entry save directory (required):";
			String LBL_A3_TOOLS_DIR = "Arma 3 Tools directory (optional):";
			String BTN_CHANGE = "Change";
			String BAD_A3_TOOLS_DIR = "Not a valid Arma 3 Tools directory.";
			String BTN_CLEAR = "Clear";
		}

		/** {@link com.kaylerrenslow.armaDialogCreator.gui.fx.main.popup.editor.ControlPropertiesConfigPopup} */
		interface ControlPropertiesConfig {
			String NO_PROPERTIES_AVAILABLE = "No properties available.";
			String EXTEND_CLASS = "Extend Class";
			String IS_BACKGROUND_CONTROL = "Background Control";

			interface MoveOutOfGroupPopup {
				String POPUP_TITLE = "Control is in a Control Group";
				String MESSAGE_F = "Control '%s' is inside a Control Group. Move out of the control group?";
			}
		}

		/** {@link com.kaylerrenslow.armaDialogCreator.gui.fx.main.popup.editor.MissingControlPropertiesConfigPopup} */
		interface MissingControlPropertiesConfig {
			String POPUP_TITLE = "Missing Control Properties";
			String MISSING_PROPERTIES_MESSAGE = "The properties below require a value to be entered.";
		}

		/** {@link com.kaylerrenslow.armaDialogCreator.gui.fx.main.popup.newControl.NewControlPopup} */
		interface NewControl {
			String POPUP_TITLE = getPopupWindowTitle("New Control Class");
		}

		/** {@link com.kaylerrenslow.armaDialogCreator.gui.fx.main.popup.NewMacroPopup} */
		interface MacroNew {
			String POPUP_TITLE = "Create New Macro";
		}

		/** {@link com.kaylerrenslow.armaDialogCreator.gui.fx.main.popup.EditMacroPopup} */
		interface MacroEdit {
			String POPUP_TITLE = "Edit a Macro";
			String MACRO_KEY = "Key:";
			String MACRO_TYPE = "Type:";
			String MACRO_VALUE = "Value:";
			String MACRO_COMMENT = "Comment (optional):";
			String NO_TYPE_CHOSEN = "No type chosen.";
		}


		/** {@link com.kaylerrenslow.armaDialogCreator.gui.fx.main.popup.ChooseMacroPopup} */
		interface ChooseMacro {
			String POPUP_TITLE = "Choose a Macro";
			String AVAILABLE_MACROS = "Available Macros";
			String NO_AVAILABLE_MACROS = "No macros are available.";
			String NO_RECENT_MACROS = "No recently chosen macros.";
			String CHOOSE_MACRO_TITLE = "Choose a Macro below.";
		}

		interface SaveProject {
			String POPUP_TITLE = "Save Project?";
			String MESSAGE = "Do you wish to save changes to the current project?";
		}

		interface ExportProject {
			String DIALOG_TITLE = getPopupWindowTitle("Export Project");
			String TITLE_LABEL = "Project Export Configuration";
			String DISPLAY_PROPERTIES = "Display Properties";
			String EXPORT_PARAMETERS = "Export Parameters";
			String EXPORT_PREVIEW = "Export Preview";

			String OK_BUTTON_EXPORT = "Export";

			interface DisplayProperties {
				String CLASS_NAME_F = "%s's Class Name:";
				String DISPLAY_TYPE = "Display Type:";
				String DIALOG = "Dialog";
				String TITLE = "Title";
			}

			interface ExportParameters {
				String EXPORT_MACROS_TO_FILE = "Export Macros to Separate File";
				String EXPORT_MACROS_TO_FILE_TOOLTIP = "Export all Macros to their own file instead of placing them in the display/dialog's header file.";
				String EXPORT_DIRECTORY = "Export Directory:";
				String EXPORT_DIRECTORY_TOOLTIP = "Choose the directory to export all project contents to.";
				String LOCATE_EXPORT_DIRECTORY = "Locate the Export Directory";
				String EXPORT_FILE_EXTENSION = "Export File Extension:";
				String PLACE_ADC_NOTICE = "Place ADC Notice";
				String PLACE_ADC_NOTICE_TOOLTIP = "Place a comment inside the exported files saying it was exported with Arma Dialog Creator.";
			}

			interface ExportPreview {

			}
		}
	}

	interface Misc {
		String FILE_CHOOSER_BACKGROUND_IMG_TITLE = "Locate an Image";
		String NO_ITEMS_AVAILABLE = "No items available.";
		String VISIT_LINK_IN_BROWSER_F = "Visit this link in your web browser:\n%s";
		String ADC_EXPORT_NOTICE = "Exported via Arma Dialog Creator (https://github.com/kayler-renslow/arma-dialog-creator)";
	}

	interface ContextMenu {
		interface DefaultComponent {
			String CONFIGURE = "Configure Properties";
			String RENDER_QUEUE = "Render Queue";
		}

		interface ComponentTreeView {
			String NEW_FOLDER = "New Folder";
		}
	}

	interface PreviewWindow {
		String POPUP_TITLE = getPopupWindowTitle("Preview Window");
	}

	/** {@link com.kaylerrenslow.armaDialogCreator.gui.fx.main.controlPropertiesEditor.ControlPropertiesEditorPane} */
	interface ControlPropertiesEditorPane {
		String RESET_TO_DEFAULT = "Reset to Default";
		String SET_TO_MACRO = "Set to Macro";
		String VALUE_OVERRIDE = "Value Override";
		String USE_DEFAULT_EDITOR = "Use Default Editor";
		String REQUIRED = "Required";
		String OPTIONAL = "Optional";
		String EVENTS = "Events";
	}

	interface ValueEditors {
		interface FontValueEditor {
			String DEFAULT_FONT = "Default Font";
		}

		interface ImageValueEditor {
			String LOCATE_IMAGE = "Locate Image";
			String A3_TOOLS_DIR_NOT_SET = "Arma 3 Tools directory not set.";
			String SET_A3_TOOLS_BTN = "Set Arma 3 Tools directory";
			FileChooser.ExtensionFilter IMAGE_FILE_EXTENSIONS = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.paa");

			interface ConvertingPaaPopup {
				String POPUP_TITLE = "Converting Image";
				String MESSAGE_F = "Converting \"%s\" to a read-able format.";
				String UNKNOWN_IMAGE_CONVERSION_ERROR = "An un-described error occurred during the conversion.";
				String CONVERT_ERROR_POPUP_TITLE = "Conversion Failed";
			}

			interface ImageAlreadyExistsDialog {
				String DIALOG_TITLE = "Image Already Exists";
				String MESSAGE_F = "Image '%s' already exists. Do you wish to overwrite, save as a new name, or cancel this operation?";
				String EXISTING_IMAGE = "Existing image:";
				String OVERWRITE = "Overwrite.";
				String NEW_NAME = "Use new name.";
			}
		}

		interface ControlStyleGroupEditor {
			String SELECT_STYLES = "Select Styles";
		}
	}

	interface Macros {
		String MACRO = "Macro";
		String COMMENT = "Comment";
		String VALUE = "Value";
		String CHOOSE_MACRO = "Choose Macro";

	}

	static String getPopupWindowTitle(String popupName) {
		return APPLICATION_NAME + " - " + popupName;
	}

	interface ProjectInitWindow {
		String WINDOW_TITLE = getPopupWindowTitle("Set the Project");
		String TAB_NEW = "New";
		String TAB_OPEN = "Open";
		String TAB_IMPORT = "Import";
		String NEW_PROJECT_TITLE = "Create a New Project";
		String NEW_PROJECT_DESCRIPTION = "Project Description:";
		String NEW_PROJECT_OK = "Create New Project";
		String PROJECT_NAME = "Project Name:";
		String UNTITLED = "untitled";
		String OPEN_PROJECT_TITLE = "Open an Existing Project";
		String OPEN_PROJECT_OK = "Load Project";
		String DETECTED_PROJECTS = "Projects";
		String IMPORT_PROJECT_TITLE = "Import an Arma 3 Dialog";
		String IMPORT_PROJECT_OK = "Import Dialog";
		String LOCATE_DESCRIPTION_EXT = "Locate description.ext";
		String PROJECT_SETUP = "Project Setup";
		String OPEN_FROM_FILE_TITLE = "Project not listed?";
		String OPEN_FROM_FILE = "Open from file";
		String FC_LOCATE_PROJECT_TITLE = "Locate a project.xml";
		FileChooser.ExtensionFilter FC_FILTER = new FileChooser.ExtensionFilter("project.xml", "*.xml");
		String COULD_NOT_LOAD_PROJECT = "Could not load project.";
		String REASON = "Reason:";

		interface ProjectResultErrorPopup {
			String POPUP_TITLE = "Project Parsed With Errors";
			String ERROR_MESSAGE = "Error:";
			String RECOVERED = "Recovered:";
			String YES = "Yes";
			String NO = "No";
			String RECOVER_MESSAGE = "Recover Message:";
			String ERRORS_TITLE = "Errors occurred while parsing.";
		}
	}

	interface XmlParse {
		String FAILED_TO_READ_XML = "Failed to read the XML.";
		String GENERIC_RECOVER_MESSAGE_F = "Value '%s' is now the current value.";

		interface ProjectLoad {
			String BAD_VALUES_F = "Bad values: '%s'.";
			String BAD_MACRO_KEY_OR_TYPE_F = "Bad macro key or type. key:'%s', type:'%s'";
			String BAD_MACRO_PROPERTY_TYPE_F = "Bad macro property type: '%s'.";
			String BAD_VALUE_CREATION_COUNT_F = "Not enough values for %s. Count:'%d'.";
			String MISSING_CONTROL_NAME = "Missing control name for tag:'%s'.";
			String BAD_CONTROL_PROPERTY_LOOKUP_ID_F = "Bad control property lookup id: '%s' for control '%s'.";
			String BAD_DISPLAY_PROPERTY_LOOKUP_ID_F = "Bad display property lookup id: '%s'.";
			String BAD_CONTROL_TYPE_F = "Bad control type: '%s' for control '%s'.";
			String BAD_RENDERER_F = "Bad renderer ('%s') for control '%s'.";
			String BAD_CONTROL_IDC_F = "Bad control idc: '%s' for control '%s'.";
			String MISSING_CONTROL_PROPERTY_F = "Missing control property: '%s' for control '%s'.";
			String NOT_A_PROJECT_SAVE = "Not a project save.";
		}

		interface ApplicationPropertyLoad {
			String BAD_SAVED_VALUE_F = "Application property '%s' failed to load saved value. Using '%s' as new value.";
		}
	}

	interface XmlWrite {
		interface ProjectSave {
			String CONTROL_PROPERTIES_MISSING_F = "Control '%s' is missing at least 1 required control property.";
		}
	}

	interface Confirmation {
		String YES = "Yes";
		String NO = "No";
	}

	interface DisplayPropertiesEditorPane {
		String REMOVE_PROPERTY_TOOLTIP = "Remove display property.";
		String ADD_DISPLAY_PROPERTY = "Add Property";
		String ADD_DISPLAY_PROPERTY_TOOLTIP = "Add a new display property";
	}
}
