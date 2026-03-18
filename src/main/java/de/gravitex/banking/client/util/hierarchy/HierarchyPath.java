package de.gravitex.banking.client.util.hierarchy;

import java.util.List;

import de.gravitex.banking.entity.util.HierarchyItem;
import de.gravitex.banking_core.util.StringHelper;

public class HierarchyPath {

	private List<HierarchyItem> items;

	public HierarchyPath(List<HierarchyItem> aItems) {
		super();
		this.items = aItems;
	}

	public int getPathLength() {
		return items.size();
	}
	
	@Override
	public String toString() {
		return StringHelper.seperateList(items.toArray(new HierarchyItem[items.size()]), ",");
	}

	public HierarchyItem[] getItems() {
		return items.toArray(new HierarchyItem[items.size()]);
	}

	public HierarchyItem getLastPathItem() {
		return items.get(items.size()-1);
	}

	public HierarchyItem getFirstPathItem() {
		return items.get(0);
	}
}