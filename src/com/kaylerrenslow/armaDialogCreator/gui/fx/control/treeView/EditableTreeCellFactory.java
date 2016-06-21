package com.kaylerrenslow.armaDialogCreator.gui.fx.control.treeView;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class EditableTreeCellFactory<E extends TreeItemData> extends TreeCell<E> {
	/** How long it takes for the current hovered tree item that is expandable for it to expand and show its children. Value is in milliseconds. */
	private static final long WAIT_DURATION_TREE_VIEW_FOLDER = 500;
	private static final Color COLOR_TREE_VIEW_DRAG = Color.ORANGE;

	private static final long DOUBLE_CLICK_WAIT_TIME_MILLIS = 250;
	private static final String HOVER_TREE_CELL = "hover-tree-cell";

	private final TreeCellSelectionUpdate treeCellSelectionUpdate;
	private final EditableTreeView<E> treeView;

	private TextField textField;

	private long waitStartTime = 0;

	/** Millisecond epoch when this cell was selected (used for double click detection) */
	private long lastSelectedTime = -1;

	private static TreeItem<?> dragging; //must be static since the factory is created for each cell and dragging takes place over more than once cell
	private boolean hasDoubleClicked;

	private static TreeCell<?> hoveredEmptyParent; //static for the same reasons as dragging

	EditableTreeCellFactory(@NotNull EditableTreeView<E> treeView, @Nullable TreeCellSelectionUpdate treeCellSelectionUpdate) {
		this.treeCellSelectionUpdate = treeCellSelectionUpdate;
		this.treeView = treeView;
		this.setEditable(true);
		// first method called when the user clicks and drags a tree item
		TreeCell<E> myTreeCell = this;
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				long now = System.currentTimeMillis();
				hasDoubleClicked = now - lastSelectedTime <= DOUBLE_CLICK_WAIT_TIME_MILLIS;
				if (hasDoubleClicked) {
					startEdit();
					hasDoubleClicked = false; //I don't know why this statement makes it work correctly
				}
				lastSelectedTime = now;
			}
		});
		this.setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (getTreeItem() == null) {
					event.consume();
					return;
				}
				// disables right clicking to start drag and drop
				if (event.isSecondaryButtonDown()) {
					event.consume();
					return;
				}

				EditableTreeView view = (EditableTreeView) getTreeView();

				Dragboard dragboard = view.startDragAndDrop(TransferMode.MOVE);
				ClipboardContent content;
				dragging = getTreeItem();
				content = new ClipboardContent();
				content.putString("");// don't remove. drag and drop doesn't work if this doesn't happen for a strange reason
				dragboard.setContent(content);

				event.consume();

			}
		});
		this.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				TreeItem<E> dragging = getDragging();
				// not dragging on the tree
				if (getTreeItem() == null) {
					return;
				}

				// trying to drag into itself
				if (getTreeItem().getValue().equals(getTreeView().getSelectionModel().getSelectedItem().getValue())) {
					return;
				}

				// if it's a folder, we need to make sure it doesn't drag itself into its children
				if (dragging.getValue().canHaveChildren()) {
					if (TreeUtil.hasDescendant(dragging, getTreeItem())) {
						return;
					}
				}

				// auto expands the hovered tree item if it has children after a period of time.
				// timer is reset when the mouse moves away from the current hovered item
				if (!getTreeItem().isLeaf() || getTreeItem().getValue().canHaveChildren()) {
					if (getTreeItem().getChildren().size() == 0) {
						hoveredEmptyParent = myTreeCell;
						getStyleClass().add(HOVER_TREE_CELL);
					} else {
						if (!getTreeItem().isExpanded()) {
							if (waitStartTime == 0) {
								waitStartTime = System.currentTimeMillis();
							} else {
								long now = System.currentTimeMillis();
								// wait a while before the tree item is expanded
								if (waitStartTime + WAIT_DURATION_TREE_VIEW_FOLDER <= now) {
									getTreeItem().setExpanded(true);
									waitStartTime = 0;
								}
							}
						}
					}
				} else {
					setEffect(new InnerShadow(1.0, 0, 2.0, COLOR_TREE_VIEW_DRAG));
				}

				// when it reaches this point, the tree item is okay to move
				event.acceptTransferModes(TransferMode.MOVE);
			}
		});

		this.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				TreeItem<E> dragging = getDragging();
				event.acceptTransferModes(TransferMode.MOVE);

				// remove the dragging item's last position
				treeView.removeChild(dragging.getParent(), dragging);
				if (getTreeItem().getValue().canHaveChildren() && getTreeItem().getChildren().size() == 0) { //no children
					treeView.addChildToParent(getTreeItem(), dragging);
				} else {
					int index = getTreeItem().getParent().getChildren().lastIndexOf(getTreeItem());
					treeView.addChildToParent(getTreeItem().getParent(), dragging, index);
					getTreeView().getSelectionModel().select(index + 1);
				}


				EditableTreeCellFactory.dragging = null;
				getTreeView().getSelectionModel().clearSelection();
				if (hoveredEmptyParent != null) {
					hoveredEmptyParent.getStyleClass().clear(); //for some reason, removing by the class name doesn't work
				}
			}

		});

		this.setOnDragExited(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if(hoveredEmptyParent != null){
					hoveredEmptyParent.getStyleClass().clear();
				}
				if (getTreeItem() == null) {
					// this happens when something is still being dragged but it isn't over the tree
					return;
				}

				waitStartTime = 0;
				setEffect(null);
				setBorder(null);
			}

		});
	}

	EditableTreeCellFactory<E> getNewInstance() {
		return new EditableTreeCellFactory<>(this.treeView, this.treeCellSelectionUpdate);
	}

	@SuppressWarnings("unchecked")
	private TreeItem<E> getDragging() {
		return (TreeItem<E>) EditableTreeCellFactory.dragging;
	}

	@Override
	public void startEdit() {
		if (!hasDoubleClicked) {
			return;
		}
		if (getTreeItem() == null) {
			return;
		}
		super.startEdit();
		if (textField == null) {
			createTextField();
		}
		setText(null);
		setGraphic(textField);
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText(getTreeItem().getValue().getText());
		setGraphic(getTreeItem().getValue().getGraphic());
		textField = null;
	}

	@Override
	protected void updateItem(E node, boolean empty) {
		super.updateItem(node, empty);
		// this adds a textfield to the tree item to get a new name
		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				if (textField != null) {
					textField.setText(getString());
				}
				setText(null);
				setGraphic(textField);
				textField.selectAll();
			} else {
				if (textField != null) {
					setText(textField.getText());
					getTreeItem().getValue().setText(textField.getText());
					textField = null;
				} else {
					setText(getItem().getText());
				}
				setGraphic(getTreeItem().getValue().getGraphic());
			}
		}
		if (node != null) {
			node.setText(getText());
		}
	}

	private void createTextField() {
		textField = new TextField(getString());
		textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent t) {
				if (t.getCode() == KeyCode.ENTER) {
					commitEdit(getItem());
				} else if (t.getCode() == KeyCode.ESCAPE) {
					cancelEdit();
				}
			}
		});
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean focused) {
				if (!focused) {
					cancelEdit();
				}
			}
		});

	}

	@Override
	public void updateSelected(boolean selected) {
		super.updateSelected(selected);
		if (getTreeItem() == null) {
			if (treeCellSelectionUpdate != null) {
				treeCellSelectionUpdate.selectionUpdate(null, null);
			}
			return;
		}
		if (treeCellSelectionUpdate != null) {
			CellType parentType = (getTreeItem().getParent() != null ? getTreeItem().getParent().getValue().getCellType() : null);
			if (getTreeItem().isLeaf()) {
				treeCellSelectionUpdate.selectionUpdate(CellType.LEAF, parentType);
			} else if (getTreeItem().getValue().isFolder()) {
				treeCellSelectionUpdate.selectionUpdate(CellType.FOLDER, parentType);
			} else if (getTreeItem().getValue().isComposite()) {
				treeCellSelectionUpdate.selectionUpdate(CellType.COMPOSITE, parentType);
			} else {
				treeCellSelectionUpdate.selectionUpdate(null, parentType);
			}
		}
	}

	private String getString() {
		return getItem() == null ? "" : getItem().toString();
	}

}
