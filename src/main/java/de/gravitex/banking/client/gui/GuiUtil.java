package de.gravitex.banking.client.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class GuiUtil {

    public static JPanel nestComponent(Component aComponent, String aTitle) {

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        Border aBorder = BorderFactory.createTitledBorder(aTitle);
        panel.setBorder(aBorder);
        panel.add(new JScrollPane(aComponent), BorderLayout.CENTER);

        return panel;
    }
    
    public static void expandAllNodes(final JTree aTree, final boolean aExpand) {
        SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				expandAllNodes(aTree, 0, aTree.getRowCount(), aExpand);				
			}
        });
    }
    
    private static void expandAllNodes(JTree aTree, int startingIndex, int rowCount, boolean aExpand) {
        for (int i = startingIndex; i < rowCount; ++i) {
            if (aExpand) {
                aTree.expandRow(i);
            } else {
                aTree.collapseRow(i);
            }
        }
        if (aTree.getRowCount() != rowCount) {
            expandAllNodes(aTree, rowCount, aTree.getRowCount(), aExpand);
        }
    }
}