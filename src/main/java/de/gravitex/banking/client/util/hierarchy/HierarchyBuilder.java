package de.gravitex.banking.client.util.hierarchy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import de.gravitex.banking.entity.util.HierarchyItem;

public class HierarchyBuilder {

	private List<HierarchyItem> hierarchyItems = new ArrayList<>();
	
	private Map<String, HierarchyItem> itemMap = new HashMap<>();

	private List<HierarchyPath> paths = new ArrayList<>();

	private DefaultMutableTreeNode treeRoot;

	private Set<String> mappedAsNode= new HashSet<>();

	public HierarchyBuilder withItem(HierarchyItem aHierarchyItem) {
		hierarchyItems.add(aHierarchyItem);
		return this;
	}
	
	public HierarchyBuilder buildHierarchy() {
		mapItems();
		while (!itemMap.isEmpty()) {
			HierarchyItem poppedItem = popItem();
			paths.add(makePath(poppedItem));
		}
		return this;
	}

	private HierarchyPath makePath(HierarchyItem aHierarchyItem) {
		List<HierarchyItem> path = new ArrayList<>();
		path.add(aHierarchyItem);
		while (aHierarchyItem.getParentItem() != null) {
			path.add(0, aHierarchyItem.getParentItem());	
			aHierarchyItem = aHierarchyItem.getParentItem();
		}
		return new HierarchyPath(path);
	}

	private HierarchyItem popItem() {
		String itemKey = new ArrayList<>(itemMap.keySet()).get(0);
		HierarchyItem popped = itemMap.get(itemKey);
		itemMap.remove(itemKey);
		return popped;
	}

	private void mapItems() {
		for (HierarchyItem aHierarchyItem : hierarchyItems) {
			itemMap.put(aHierarchyItem.getHierarchyKey(), aHierarchyItem);
		}
	}

	public List<HierarchyPath> getPathsSorted() {
		Collections.sort(paths, new HierarchyPathComparator());
		return paths;
	}
	
	private class HierarchyPathComparator implements Comparator<HierarchyPath> {
		@Override
		public int compare(HierarchyPath o1, HierarchyPath o2) {
			return Integer.valueOf(o1.getPathLength()).compareTo(Integer.valueOf(o2.getPathLength()));
		}
	}

	public DefaultTreeModel buildTreeModel() {
		
		for (HierarchyPath aHierarchyPath : getPathsSorted()) {
			System.out.println(aHierarchyPath);
		}
		
		return null;
	}

	private DefaultMutableTreeNode mapAsTreeNode(HierarchyItem aHierarchyItem) {
		mappedAsNode.add(aHierarchyItem.getHierarchyKey());
		return new DefaultMutableTreeNode(aHierarchyItem);
	}

	private boolean mappedAsTreeNode(HierarchyItem aHierarchyItem) {
		return mappedAsNode.contains(aHierarchyItem.getHierarchyKey());
	}
}