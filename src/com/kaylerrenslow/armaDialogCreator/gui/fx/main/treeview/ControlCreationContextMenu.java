/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.kaylerrenslow.armaDialogCreator.gui.fx.main.treeview;

import com.kaylerrenslow.armaDialogCreator.control.ControlType;
import com.kaylerrenslow.armaDialogCreator.control.ControlTypeGroup;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.BorderedImageView;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.treeView.CellType;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.treeView.EditableTreeView;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.treeView.TreeItemDataCreator;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.treeView.TreeViewMenuItemBuilder;
import com.kaylerrenslow.armaDialogCreator.main.Lang;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;

import static com.kaylerrenslow.armaDialogCreator.gui.fx.main.treeview.EditorComponentTreeView.createFolderIcon;

/**
 ContextMenu for inserting a new control into the EditorComponentTreeView

 @author Kayler
 @since 06/20/2016. */
public class ControlCreationContextMenu extends ContextMenu {

	public ControlCreationContextMenu(EditorComponentTreeView treeView) {
		ResourceBundle bundle = Lang.ApplicationBundle();
		MenuItem newFolder = new MenuItem(bundle.getString("ContextMenu.ComponentTreeView.new_folder"), createFolderIcon());
		getItems().add(newFolder);
		TreeViewMenuItemBuilder.setNewFolderAction(treeView, new TreeItemDataCreator<TreeItemEntry>() {
			@Nullable
			@Override
			public TreeItemEntry createNew(@NotNull CellType cellType, @NotNull EditableTreeView<TreeItemEntry> treeView) {
				return new FolderTreeItemEntry(newFolder.getText());
			}

		}, newFolder);

		Menu groupMenu;
		MenuItem menuItemType;
		for (ControlTypeGroup group : ControlTypeGroup.values()) {
			groupMenu = new Menu(group.getDisplayName());
			for (ControlTreeItemDataCreatorLookup creator : ControlTreeItemDataCreatorLookup.values()) {
				ControlType controlType = creator.controlType;
				if (controlType.getGroup() != group) {
					continue;
				}
				if (!controlType.betaSupported()) {
					continue;
				}
				menuItemType = new MenuItem(controlType.fullDisplayText(), new BorderedImageView(controlType.getIcon()));
				if (controlType.isDeprecated()) {
					menuItemType.getStyleClass().add("deprecated-menu-item");
				}
				if (creator.allowsSubControls) {
					TreeViewMenuItemBuilder.setNewCompositeItemAction(treeView, creator.creator, menuItemType);
				} else {
					TreeViewMenuItemBuilder.setNewItemAction(treeView, creator.creator, menuItemType);
				}

				groupMenu.getItems().add(menuItemType);
			}
			if (groupMenu.getItems().size() > 0) {
				getItems().add(groupMenu);
			}
			//			if (groupMenu.getItems().size() == 0) {
			//				MenuItem miNone = new MenuItem(bundle.getString("Misc.no_items_available"));
			//				miNone.setDisable(true);
			//				groupMenu.getItems().add(miNone);
			//			}
		}
	}
}
