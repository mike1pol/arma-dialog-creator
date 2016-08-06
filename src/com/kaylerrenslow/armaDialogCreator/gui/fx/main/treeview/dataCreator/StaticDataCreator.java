/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.kaylerrenslow.armaDialogCreator.gui.fx.main.treeview.dataCreator;

import com.kaylerrenslow.armaDialogCreator.arma.control.impl.StaticControl;
import com.kaylerrenslow.armaDialogCreator.control.ControlStyle;
import com.kaylerrenslow.armaDialogCreator.control.sv.Expression;
import com.kaylerrenslow.armaDialogCreator.data.DataKeys;
import com.kaylerrenslow.armaDialogCreator.expression.Env;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.treeView.CellType;
import com.kaylerrenslow.armaDialogCreator.gui.fx.control.treeView.TreeItemDataCreator;
import com.kaylerrenslow.armaDialogCreator.gui.fx.main.treeview.ControlTreeItemEntry;
import com.kaylerrenslow.armaDialogCreator.gui.fx.main.treeview.TreeItemEntry;
import com.kaylerrenslow.armaDialogCreator.main.ArmaDialogCreator;

/**
 Created by Kayler on 06/19/2016.
 */
public class StaticDataCreator implements TreeItemDataCreator<TreeItemEntry> {
	private static int id = 0; //delete this later on as its for testing

	public static final StaticDataCreator INSTANCE = new StaticDataCreator();

	@Override
	public TreeItemEntry createNew(CellType cellType) {
		StaticControl control = new StaticControl("static_control" + id, 0, ControlStyle.CENTER.styleGroup, new Expression("0", getEnv()), new Expression("0", getEnv()), new Expression("1", getEnv()), new Expression("1", getEnv()), DataKeys.ARMA_RESOLUTION.get(ArmaDialogCreator.getApplicationData()), getEnv());
		id++;
		return new ControlTreeItemEntry(control);
	}

	private Env getEnv() {
		return ArmaDialogCreator.getApplicationData().getGlobalExpressionEnvironment();
	}
}
