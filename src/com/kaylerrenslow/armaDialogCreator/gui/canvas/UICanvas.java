/*
 * Copyright (c) 2016 Kayler Renslow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. in no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
 */

package com.kaylerrenslow.armaDialogCreator.gui.canvas;

import com.kaylerrenslow.armaDialogCreator.gui.canvas.api.CanvasComponent;
import com.kaylerrenslow.armaDialogCreator.gui.canvas.api.Control;
import com.kaylerrenslow.armaDialogCreator.gui.canvas.api.Display;
import com.kaylerrenslow.armaDialogCreator.gui.canvas.api.Resolution;
import com.kaylerrenslow.armaDialogCreator.gui.fx.main.CanvasViewColors;
import com.kaylerrenslow.armaDialogCreator.util.Point;
import com.kaylerrenslow.armaDialogCreator.util.UpdateListener;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 @author Kayler
 Created on 05/11/2016. */
public abstract class UICanvas extends AnchorPane {
	
	/** javafx Canvas */
	protected final Canvas canvas;
	/** GraphicsContext for the canvas */
	protected final GraphicsContext gc;
	protected @NotNull Display display;
		
	/** Background image of the canvas */
	protected ImagePattern backgroundImage = null;
	
	/** Background color of the canvas */
	protected Color backgroundColor = CanvasViewColors.EDITOR_BG;
	
	/** Mouse button that is currently down */
	protected final Point lastMousePosition = new Point(-1, -1);//last x and y positions of the mouse relative to the canvas
	
	protected Keys keys = new Keys();
	
	/** All components added */
	protected ArrayList<CanvasComponent> components = new ArrayList<>();
	
	private final UpdateListener<Object> displayListener = new UpdateListener<Object>() {
		@Override
		public void update(Object data) {
			paint();
		}
	};
	
	public UICanvas(@NotNull Resolution resolution, @NotNull Display display) {
		resolution.getUpdateGroup().addListener(new UpdateListener<Resolution>() {
			@Override
			public void update(Resolution newResolution) {
				if(getCanvasHeight() != newResolution.getScreenHeight() || getCanvasWidth() != newResolution.getScreenWidth()){
					canvas.setWidth(newResolution.getScreenWidth());
					canvas.setHeight(newResolution.getScreenHeight());
				}
				display.resolutionUpdate(newResolution);
				paint();
			}
		});
		
		this.canvas = new Canvas(resolution.getScreenWidth(), resolution.getScreenHeight());
		
		this.display = display;
		this.display.getUpdateListenerGroup().addListener(displayListener);
		
		this.gc = this.canvas.getGraphicsContext2D();
		gc.setTextBaseline(VPos.CENTER);
		
		this.getChildren().add(this.canvas);
		UICanvas.CanvasMouseEvent mouseEvent = new UICanvas.CanvasMouseEvent(this);
		
		this.setOnMousePressed(mouseEvent);
		this.setOnMouseReleased(mouseEvent);
		this.setOnMouseMoved(mouseEvent);
		this.setOnMouseDragged(mouseEvent);
	}
	
	public int getCanvasWidth() {
		return (int) canvas.getWidth();
	}
	
	public int getCanvasHeight() {
		return (int) this.canvas.getHeight();
	}
	
	public void setDisplay(@NotNull Display display) {
		this.display.getUpdateListenerGroup().removeUpdateListener(displayListener);
		this.display = display;
		this.display.getUpdateListenerGroup().addListener(displayListener);
	}
		
	/** Adds a component to the canvas and repaints the canvas */
	public void addComponent(@NotNull CanvasComponent component) {
		this.components.add(component);
		paint();
	}
	
	/**
	 Removes the given component from the canvas render and user interaction.
	 
	 @param component component to remove
	 @return true if the component was removed, false if nothing was removed
	 */
	public boolean removeComponent(@NotNull CanvasComponent component) {
		boolean removed = this.components.remove(component);
		paint();
		return removed;
	}
	
	/**
	 Paint the canvas. Order of painting is:
	 <ol>
	 <li>background</li>
	 <li>display/controls</li>
	 <li>components inserted via {@link #addComponent(CanvasComponent)}</li>
	 </ol>
	 */
	public void paint() {
		gc.save();
		paintBackground();
		paintControls();
		paintComponents();
		gc.restore();
	}
	
	/** Paints all controls inside the display set {@link #display}. Each component will get an individual render space (GraphicsContext attributes will not bleed through each component). */
	protected void paintControls() {
		for (Control control : display.getControls()) {
			paintControl(control);
		}
	}
	
	/** Paints all components. Each component will get an individual render space (GraphicsContext attributes will not bleed through each component). */
	protected void paintComponents() {
		this.components.sort(CanvasComponent.RENDER_PRIORITY_COMPARATOR);
		for (CanvasComponent component : components) {
			paintComponent(component);
		}
	}
	
	protected void paintBackground() {
		gc.setFill(backgroundColor);
		gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
		if (backgroundImage == null) {
			return;
		}
		gc.setFill(backgroundImage);
		gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
	}
	
	protected void paintControl(Control control){
		gc.save();
		paintComponent(control.getRenderer());
		gc.restore();
	}
	
	protected void paintComponent(CanvasComponent component) {
		if (component.isGhost()) {
			return;
		}
		gc.save();
		component.paint(gc);
		gc.restore();
	}
	
	/** Sets canvas background image and automatically repaints */
	public void setCanvasBackgroundImage(@Nullable ImagePattern background) {
		this.backgroundImage = background;
	}
	
	/** Sets canvas background color and repaints the canvas */
	public void setCanvasBackgroundColor(@NotNull Color color) {
		this.backgroundColor = color;
		paint();
	}
	
	
	/**
	 This is called when the mouse listener is invoked and a mouse press was the event. Default implementation does nothing.
	 
	 @param mousex x position of mouse relative to canvas
	 @param mousey y position of mouse relative to canvas
	 @param mb mouse button that was pressed
	 */
	protected void mousePressed(int mousex, int mousey, @NotNull MouseButton mb) {
	}
	
	/**
	 This is called when the mouse listener is invoked and a mouse release was the event. Default implementation does nothing.
	 
	 @param mousex x position of mouse relative to canvas
	 @param mousey y position of mouse relative to canvas
	 @param mb mouse button that was released
	 */
	protected void mouseReleased(int mousex, int mousey, @NotNull MouseButton mb) {
	}
	
	/**
	 This is called when the mouse is moved and/or dragged inside the canvas. Default implementation does nothing.
	 
	 @param mousex x position of mouse relative to canvas
	 @param mousey y position of mouse relative to canvas
	 */
	protected void mouseMoved(int mousex, int mousey) {
	}
	
	
	/**
	 This should be called when any mouse event occurs (press, release, drag, move, etc)
	 
	 @param shiftDown true if the shift key is down, false otherwise
	 @param ctrlDown true if the ctrl key is down, false otherwise
	 @param altDown true if alt key is down, false otherwise
	 */
	public void keyEvent(String key, boolean keyIsDown, boolean shiftDown, boolean ctrlDown, boolean altDown) {
		keys.update(key, keyIsDown, shiftDown, ctrlDown, altDown);
		paint();
	}
	
	
	/** This is called after mouseMove is called. This will ensure that no matter how mouse move exits, the last mouse position will be updated */
	private void setLastMousePosition(int mousex, int mousey) {
		lastMousePosition.set(mousex, mousey);
	}
	
	
	@Override
	protected double computeMinWidth(double height) {
		return getCanvasWidth();
	}
	
	@Override
	protected double computeMinHeight(double width) {
		return getCanvasHeight();
	}
	
	@Override
	protected double computePrefWidth(double height) {
		return getCanvasWidth();
	}
	
	@Override
	protected double computePrefHeight(double width) {
		return getCanvasHeight();
	}
	
	@Override
	protected double computeMaxWidth(double height) {
		return super.computeMaxWidth(height);
	}
	
	@Override
	protected double computeMaxHeight(double width) {
		return super.computeMaxHeight(width);
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	/**
	 Created by Kayler on 05/13/2016.
	 */
	private static class CanvasMouseEvent implements EventHandler<MouseEvent> {
		private final UICanvas canvas;
		
		CanvasMouseEvent(UICanvas canvas) {
			this.canvas = canvas;
		}
		
		@Override
		public void handle(MouseEvent event) {
			MouseButton btn = event.getButton();
			if (!(event.getTarget() instanceof Canvas)) {
				return;
			}
			
			Canvas c = (Canvas) event.getTarget();
			Point2D p = c.sceneToLocal(event.getSceneX(), event.getSceneY());
			int mousex = (int) p.getX();
			int mousey = (int) p.getY();
			
			if (event.getEventType() == MouseEvent.MOUSE_MOVED || event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				canvas.mouseMoved(mousex, mousey);
				canvas.setLastMousePosition(mousex, mousey);
			} else {
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					canvas.mousePressed(mousex, mousey, btn);
				} else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
					canvas.mouseReleased(mousex, mousey, btn);
				}
			}
			canvas.paint();
			
		}
		
	}
	
}
