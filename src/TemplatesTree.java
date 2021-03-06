import java.awt.Component;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class TemplatesTree extends JPanel implements TreeSelectionListener {

	private JTree tree;
	private StaticContentPane staticPane;

	public TemplatesTree(StaticContentPane pane) {

		super(new GridLayout(1, 0));

		this.staticPane = pane;

		FileSystemModel fileSystemDataModel = new FileSystemModel();

		JTree tree = new JTree(fileSystemDataModel);
		tree.setRootVisible(false);
		tree.setCellRenderer(new MyRenderer());
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		tree.addTreeSelectionListener(this);

		JScrollPane scrollPane = new JScrollPane(tree);
		add(scrollPane);
	}

	class FileSystemModel implements TreeModel {
		private String root; // The root identifier

		private Vector listeners; // Declare the listeners vector

		private PrepsTemplate templ; // Preps template

		public FileSystemModel() {

			// root = System.getProperty("user.home");
			root = "pre-templates";
			listeners = new Vector();
		}

		public Object getRoot() {
			return (new File(root));
		}

		public Object getChild(Object parent, int index) {

			Object child = new Object();

			File file = (File) parent;
			if (file.isDirectory()) {

				String[] directoryMembers = file.list();
				child = new File(file, directoryMembers[index]);

			} else if (file.isFile()) {
				child = templ.getSignature(index);
			}

			return child;
		}

		public int getChildCount(Object parent) {

			File fileSystemMember = (File) parent;
			if (fileSystemMember.isDirectory()) {
				String[] directoryMembers = fileSystemMember.list();
				return directoryMembers.length;

			} else {

				return templ.getSignaturesCount();
			}
		}

		public int getIndexOfChild(Object parent, Object child) {

			File file = (File) parent;
			int result = -1;

			if (child instanceof File) {

				File directoryMember = (File) child;
				String[] directoryMemberNames = file.list();

				for (int i = 0; i < directoryMemberNames.length; ++i) {
					if (directoryMember.getName().equals(
							directoryMemberNames[i])) {
						result = i;
						break;
					}
				}
			} else if (child instanceof Signature) {

				Signature sign = (Signature) child;
				List<Signature> signatures = templ.getSignaturesList();
				for (int i = 0; i < signatures.size(); ++i) {
					if (sign.getFirstLine().equals(
							signatures.get(i).getFirstLine())) {
						result = i;
						break;
					}
				}
			}

			return result;
		}

		public boolean isLeaf(Object node) {

			if (node instanceof File) {

				File file = (File) node;
				if (file.isFile()) {
					templ = new PrepsTemplate(file);
					if (templ.getSignaturesCount() > 1) {
						return false;
					}
				} else {
					return false;
				}
			}

			return true;

		}

		public void addTreeModelListener(TreeModelListener l) {
			if (l != null && !listeners.contains(l)) {
				listeners.addElement(l);
			}
		}

		public void removeTreeModelListener(TreeModelListener l) {
			if (l != null) {
				listeners.removeElement(l);
			}
		}

		public void valueForPathChanged(TreePath path, Object newValue) {
			// Does Nothing!
		}

		public void fireTreeNodesInserted(TreeModelEvent e) {
			Enumeration listenerCount = listeners.elements();
			while (listenerCount.hasMoreElements()) {
				TreeModelListener listener = (TreeModelListener) listenerCount
						.nextElement();
				listener.treeNodesInserted(e);
			}
		}

		public void fireTreeNodesRemoved(TreeModelEvent e) {
			Enumeration listenerCount = listeners.elements();
			while (listenerCount.hasMoreElements()) {
				TreeModelListener listener = (TreeModelListener) listenerCount
						.nextElement();
				listener.treeNodesRemoved(e);
			}

		}

		public void fireTreeNodesChanged(TreeModelEvent e) {
			Enumeration listenerCount = listeners.elements();
			while (listenerCount.hasMoreElements()) {
				TreeModelListener listener = (TreeModelListener) listenerCount
						.nextElement();
				listener.treeNodesChanged(e);
			}

		}

		public void fireTreeStructureChanged(TreeModelEvent e) {
			Enumeration listenerCount = listeners.elements();
			while (listenerCount.hasMoreElements()) {
				TreeModelListener listener = (TreeModelListener) listenerCount
						.nextElement();
				listener.treeStructureChanged(e);
			}

		}
	}

	private class MyRenderer extends DefaultTreeCellRenderer {

		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);

			if (value instanceof File) {

				File node = (File) value;
				setText(node.getName());
				return this;

			} else { // if value is a signature

				Signature sign = (Signature) value;
				setText(sign.getSigName());
				return this;
			}
		}
	}

	public void valueChanged(TreeSelectionEvent e) {

		Object object = e.getPath().getLastPathComponent();
		Signature sign = null;

		if (object instanceof File) {

			File node = (File) object;
			if (node.isFile()) {
				PrepsTemplate templ = new PrepsTemplate(node);
				sign = templ.getSignature(0);
			} else {

				return;
			}

		} else if (object instanceof Signature) {

			sign = (Signature) object;
		}

		staticPane.setPressSheetSize(sign);
		staticPane.setWorkstyle(sign);
		staticPane.setPageHeight(sign);

		// System.out.println("Imposition: " +
		// PrepsTemplate.getColumnsCount(sign) + "x" +
		// PrepsTemplate.getRowsCount(sign));
	
		staticPane.getPrepLayoutPanel().makeImpositionGrid(sign);
	}

}
