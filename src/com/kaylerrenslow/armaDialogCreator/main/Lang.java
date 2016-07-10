package com.kaylerrenslow.armaDialogCreator.main;

public interface Lang {
	interface Application {
		String APPLICATION_TITLE = "Arma Dialog Creator - V1.0.0";
	}

	interface CanvasControls {
		String ALT_STEP = "Alternate Step:";
		String STEP = "Step:";
		String VIEWPORT_SNAP = "Snap Relative to Viewport";

	}

	interface MainMenuBar {
		String FILE = "File";
		String FILE_NEW = "New";
		String FILE_OPEN = "Open";
		String FILE_SAVE = "Save";
		String FILE_SAVE_AS = "Save As";

		String EDIT = "Edit";
		String EDIT_CHANGES = "View Changes";
		String EDIT_UNDO = "Undo";
		String EDIT_REDO = "Redo";
		String EDIT_UNDO_F = "Undo %s";
		String EDIT_REDO_F = "Redo %s";

		String VIEW = "View";
		String VIEW_PREVIEW = "Preview";
		String VIEW_SHOW_GRID = "Show Grid";
		String VIEW_CHANGE_COLORS = "Change Colors";
		String VIEW_BACKGROUND_IMAGE = "Background Image";
		String VIEW_CHANGE_BACKGROUND_IMAGE1 = "VR";
		String VIEW_CHANGE_BACKGROUND_IMAGE2 = "Altis";
		String VIEW_CHANGE_BACKGROUND_IMAGE3 = "Altis Late";
		String VIEW_CHANGE_BACKGROUND_IMAGE_CUSTOM = "Custom";
		String VIEW_CHANGE_BACKGROUND_NONE = "None";
		String VIEW_ABS_REGION = "Absolute Coordinate Region";
		String VIEW_ABS_REGION_SHOW = "Show";
		String VIEW_ABS_REGION_ALWAYS_FRONT = "Always at Front";
		String VIEW_DARK_THEME = "Use Dark Theme";
		String VIEW_FULL_SCREEN = "Full Screen";

		String SETTINGS = "Settings";
		String SETTINGS_CONFIGURE_DIRS = "Configure Directories";
	}

	interface Popups {
		String BTN_HELP = "Help";
		String BTN_CANCEL = "Cancel";
		String BTN_OK = "OK";

		interface Colors {
			String POPUP_TITLE = "Change Canvas Colors";
			String SELECTION = "Selection";
			String GRID = "Grid";
			String ABS_REGION = "Absolute Region";
			String BACKGROUND = "Canvas Background Color";
		}

		interface SelectSaveLocation {
			String POPUP_TITLE = "Configure Directories";
			String LBL_APP_DATA_SAVE_DIR = "Application entry save directory:";
			String LBL_A3_TOOLS_DIR = "Arma 3 Tools directory (optional):";
			String BTN_CHANGE = "Change";
		}

		interface ControlPropertiesConfig {
			String SQF_CODE = "SQF Code to Execute";
			String TEXTURE = "Texture String";
			String IMAGE_PATH = "Image Path String";
			String FILE_NAME = "File Path String";
			String NO_PROPERTIES_AVAILABLE = "No properties available.";
			String STRING = "String";
			String EXTEND_CLASS = "Extend Class";
			String IS_BACKGROUND_CONTROL = "Background Control";
			String INT = "Integer";
			String FLOAT = "Floating Point Number";
		}

		interface MissingControlPropertiesConfig {
			String POPUP_TITLE = "Missing Control Properties";
			String MISSING_PROPERTIES_MESSAGE = "The properties below require a value to be entered.";
		}

		interface NewControl {
			String POPUP_TITLE = "Arma Dialog Creator - New Control";

			interface AddPropertiesPopup {
				String POPUP_TITLE = "Arma Dialog Creator - New Control - Add Properties";
				String COLUMN_PROPERTY_NAME = "Property Name";
				String COLUMN_PROPERTY_TYPE = "Property Type";
				String COLUMN_PROPERTY_DESCRIPTION = "Property Description";
			}
		}
	}

	interface Misc {
		String FILE_CHOOSER_BACKGROUND_IMG_TITLE = "Locate an Image";
	}

	interface ContextMenu {
		interface DefaultComponent {
			String CONFIGURE = "Configure Properties";
			String RENDER_QUEUE = "Render Queue";
		}

		interface ComponentTreeView {
			String NEW_FOLDER = "New Folder";
			String TITLE = "New Control";
		}
	}

	interface PreviewWindow {
		String POPUP_TITLE = "Arma Dialog Creator - Preview Window";
	}

	interface ControlPropertiesEditorPane {
		String RESET_TO_DEFAULT = "Reset to Default";
		String RESET_TO_DEFAULT_TOOLTIP = "Set the value to its default/original value.";
		String SET_TO_MACRO = "Set to Macro";
		String SET_TO_MACRO_TOOLTIP = "Set the value equal to a macro.";
		String VALUE_OVERRIDE = "Value Override";
		String VALUE_OVERRIDE_TOOLTIP = "Enter anything for a value.";
		String USE_DEFAULT_EDITOR = "Use Default Editor";
		String USE_DEFAULT_EDITOR_TOOLTIP = "Use the editor specialized towards editing this data.";
	}

	interface Macros {
		String MACRO = "Macro";
		String COMMENT = "Comment";
		String VALUE = "Value";
		String NEW_MACRO = "New Macro";
		String CHOOSE_MACRO = "Choose Macro";

		interface ChooseMacroPopup {
			String POPUP_TITLE = "Choose a Macro";
			String AVAILABLE_MACROS = "Available Macros:";
			String MO_AVAILABLE_MACROS = "No macros are available.";
			String NO_RECENT_MACROS = "No recently chosen macros.";
		}

	}
}
