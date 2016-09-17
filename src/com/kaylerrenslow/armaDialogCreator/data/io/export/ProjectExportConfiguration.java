/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.kaylerrenslow.armaDialogCreator.data.io.export;

import com.kaylerrenslow.armaDialogCreator.data.Project;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 Created by Kayler on 09/13/2016.
 */
public class ProjectExportConfiguration {

	private static final String DEFAULT_CLASS_NAME = "MyDialog";

	private String exportClassName;
	private File exportLocation;
	private Project project;
	private boolean placeAdcNotice;
	private boolean exportMacrosToFile;
	private HeaderFileType fileType;

	public ProjectExportConfiguration(
			@NotNull String exportClassName,
			@NotNull File exportLocation,
			@NotNull Project project,
			boolean placeAdcNotice,
			boolean exportMacrosToFile,
			@NotNull HeaderFileType fileType
	) {
		this.exportClassName = exportClassName;
		this.exportLocation = exportLocation;
		this.project = project;
		this.placeAdcNotice = placeAdcNotice;
		this.exportMacrosToFile = exportMacrosToFile;
		this.fileType = fileType;
	}

	public boolean shouldExportMacrosToFile() {
		return exportMacrosToFile;
	}

	public boolean shouldPlaceAdcNotice() {
		return placeAdcNotice;
	}

	@NotNull
	public File getExportLocation() {
		return exportLocation;
	}

	@NotNull
	public String getExportClassName() {
		return exportClassName;
	}

	@NotNull
	public Project getProject() {
		return project;
	}

	@NotNull
	public HeaderFileType getHeaderFileType() {
		return fileType;
	}

	public void setFileType(@NotNull HeaderFileType fileType) {
		this.fileType = fileType;
	}

	public void setExportClassName(@NotNull String exportClassName) {
		this.exportClassName = exportClassName;
	}

	public void setExportLocation(@NotNull File exportLocation) {
		this.exportLocation = exportLocation;
	}

	public void setProject(@NotNull Project project) {
		this.project = project;
	}

	public void setPlaceAdcNotice(boolean placeAdcNotice) {
		this.placeAdcNotice = placeAdcNotice;
	}

	public void setExportMacrosToFile(boolean exportMacrosToFile) {
		this.exportMacrosToFile = exportMacrosToFile;
	}

	public static ProjectExportConfiguration getDefaultConfiguration(@NotNull Project project) {
		return new ProjectExportConfiguration(
				DEFAULT_CLASS_NAME,
				project.getProjectSaveDirectory(),
				project,
				false,
				false,
				HeaderFileType.DEFAULT
				);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ProjectExportConfiguration)) {
			return false;
		}

		ProjectExportConfiguration that = (ProjectExportConfiguration) o;

		if (placeAdcNotice != that.placeAdcNotice) {
			return false;
		}
		if (exportMacrosToFile != that.exportMacrosToFile) {
			return false;
		}
		if (!exportClassName.equals(that.exportClassName)) {
			return false;
		}
		if (!exportLocation.equals(that.exportLocation)) {
			return false;
		}
		if (project != that.project) {
			return false;
		}
		return fileType == that.fileType;
	}

}
