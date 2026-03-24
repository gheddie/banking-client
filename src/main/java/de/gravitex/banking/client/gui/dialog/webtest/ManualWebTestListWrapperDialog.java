package de.gravitex.banking.client.gui.dialog.webtest;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import de.gravitex.banking.client.accessor.response.HttpPatchResult;
import de.gravitex.banking.client.accessor.response.HttpPutResult;
import de.gravitex.banking.client.accessor.response.base.HttpResult;
import de.gravitex.banking.client.gui.EntityTablePanel;
import de.gravitex.banking.client.gui.EntityTablePanelListener;
import de.gravitex.banking.client.gui.action.filter.ActionFilter;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.client.tester.exception.ManualWebTesterException;
import de.gravitex.banking.client.tester.executor.ManualWebTesterExecutor;
import de.gravitex.banking.client.tester.instance.base.ManualWebTester;
import de.gravitex.banking.client.tester.reporterstub.util.HttpResultWrapper;
import de.gravitex.banking.client.tester.util.ManualWebTestDefinition;
import de.gravitex.banking.client.tester.util.WebTestWatcher;
import de.gravitex.banking.entity.base.IdEntity;
import de.gravitex.banking.entity.base.NoIdEntity;

public class ManualWebTestListWrapperDialog extends JDialog implements EntityTablePanelListener, ActionListener {

	private static final long serialVersionUID = 2440545099409907985L;
	
	private List<ManualWebTestDefinition> testDefinitions;

	private JTabbedPane testPane;

	private JButton ok;

	private Map<Class<? extends ManualWebTester>, PublishTestResultPanel> publishedResults = new HashMap<>();

	public ManualWebTestListWrapperDialog(List<ManualWebTestDefinition> aTestDefinitions, Frame aParent) {
		
		super(aParent);
		this.testDefinitions = aTestDefinitions;
		
		setSize(900, 600);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		testPane = new JTabbedPane();
		add(testPane, BorderLayout.CENTER);
		ok = new JButton("OK");
		ok.setEnabled(false);
		ok.addActionListener(this);
		add(ok, BorderLayout.SOUTH);
		
		setTitle(buildTitle());
	}

	private String buildTitle() {
		
		List<ManualWebTestDefinition> activeTestDefinitions = new ArrayList<>();
		List<ManualWebTestDefinition> inactiveTestDefinitions = new ArrayList<>();
		
		for (ManualWebTestDefinition aTestDefinition : testDefinitions) {
			if (aTestDefinition.isActive()) {				
				activeTestDefinitions.add(aTestDefinition);
			} else {
				inactiveTestDefinitions.add(aTestDefinition);
			}
		}
		
		String title = activeTestDefinitions.size() + " Tests";
		
		if (!inactiveTestDefinitions.isEmpty()) {
			title += " ("+inactiveTestDefinitions.size()+" inaktiv)";
		}
		
		return title;
	}

	public void publishResult(ManualWebTester aManualWebTester, List<HttpResultWrapper> aResultWrappers) {
		
		PublishTestResultPanel panel = new PublishTestResultPanel(this, aResultWrappers, aManualWebTester);
		testPane.addTab(aManualWebTester.getClass().getSimpleName(), panel);
		
		publishedResults.put(aManualWebTester.getClass(), panel);
	}

	@Override
	public void onEntitySelected(Object aEntity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityDoubleClicked(Object aEntity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getSelectedObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpPatchResult acceptEditedEntity(IdEntity aEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpPutResult acceptCreatedEntity(IdEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFilter getActionFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends NoIdEntity> reloadEntities(Class<?> aEntityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}

	public void onTestsFinished() {
		ok.setEnabled(true);
		for (Class<? extends ManualWebTester> aTestClass : publishedResults.keySet()) {
			publishedResults.get(aTestClass).enableRerun();
		}
	}
	
	private class PublishTestResultPanel extends JPanel implements ActionListener, WebTestWatcher {

		private static final long serialVersionUID = 6479776524167962586L;
		
		private EntityTablePanelListener entityTableListener;

		private JButton rerunTest;

		private List<HttpResultWrapper> resultWrappers;

		private ManualWebTester manualWebTester;
		
		public PublishTestResultPanel(EntityTablePanelListener aEntityTableListener,
				List<HttpResultWrapper> aResultWrappers, ManualWebTester aManualWebTester) {

			super();

			this.entityTableListener = aEntityTableListener;
			this.resultWrappers = aResultWrappers;
			this.manualWebTester = aManualWebTester;

			setLayout(new BorderLayout());
			EntityTablePanel resultWrapperTable = new EntityTablePanel("Resultate", entityTableListener, false,
					HttpResultWrapper.class);
			add(resultWrapperTable, BorderLayout.CENTER);
			rerunTest = new JButton("Test wiederholen");
			rerunTest.setEnabled(false);
			rerunTest.addActionListener(this);
			add(rerunTest, BorderLayout.NORTH);
			if (resultWrappers != null) {
				resultWrapperTable.displayEntities(resultWrappers);
			}
		}		

		public void enableRerun() {
			rerunTest.setEnabled(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new ManualWebTesterExecutor().runForInstance(manualWebTester.getClass(), this);
		}

		@Override
		public void acceptSuccess(HttpResult aHttpResult, boolean aShouldSuceed, boolean aTraceEnabled,
				ManualWebTester aManualWebTester) {

		}

		@Override
		public void onTestSucceed(ManualWebTester aManualWebTester) {
			ApplicationRegistry.getInstance().getInteractionHandler()
					.confirm("Test {" + manualWebTester.getClass().getSimpleName() + "} beendet!!!", true, this);			
		}

		@Override
		public void handleTestException(ManualWebTesterException aManualWebTesterException,
				ManualWebTester aManualWebTester) {
			aManualWebTesterException.printStackTrace();
		}
	}
}