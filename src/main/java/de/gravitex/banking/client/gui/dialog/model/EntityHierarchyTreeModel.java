package de.gravitex.banking.client.gui.dialog.model;

import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import de.gravitex.banking.client.util.hierarchy.HierarchyPath;

public class EntityHierarchyTreeModel implements TreeNode {

	private List<HierarchyPath> sortedPaths;
	
	public EntityHierarchyTreeModel(List<HierarchyPath> aSortedPaths) {
		super();
		this.sortedPaths = aSortedPaths;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public TreeNode getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIndex(TreeNode node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Enumeration<? extends TreeNode> children() {
		// TODO Auto-generated method stub
		return null;
	}
}
