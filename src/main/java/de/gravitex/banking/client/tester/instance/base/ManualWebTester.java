package de.gravitex.banking.client.tester.instance.base;

import java.util.List;

import de.gravitex.banking.client.accessor.BankingAccessor;
import de.gravitex.banking.client.accessor.IBankingAccessor;
import de.gravitex.banking.client.accessor.response.base.HttpResult;
import de.gravitex.banking.client.registry.ApplicationRegistry;
import de.gravitex.banking.client.tester.exception.ManualWebTesterException;
import de.gravitex.banking.client.tester.matcher.ResponseLengthValidator;
import de.gravitex.banking.client.tester.matcher.exception.ContainedStringExceptionMatcher;
import de.gravitex.banking.client.tester.matcher.exception.base.ExceptionMatcher;
import de.gravitex.banking.client.tester.reporterstub.GuiWebTestReporter;
import de.gravitex.banking.client.tester.reporterstub.base.WebTestReporterStub;
import de.gravitex.banking.client.tester.util.EntitiesRemover;
import de.gravitex.banking.client.tester.util.WebTestWatcher;
import de.gravitex.banking.client.tester.util.WebTester;
import de.gravitex.banking.client.tester.util.WebTesterObjectCache;
import de.gravitex.banking.client.tester.validation.ValueValidator;
import de.gravitex.banking.entity.Account;
import de.gravitex.banking.entity.Booking;
import de.gravitex.banking.entity.BookingImport;
import de.gravitex.banking.entity.BookingImportItem;
import de.gravitex.banking.entity.BudgetPlanning;
import de.gravitex.banking.entity.BudgetPlanningItem;
import de.gravitex.banking.entity.CreditInstitute;
import de.gravitex.banking.entity.PurposeCategory;
import de.gravitex.banking.entity.StandingOrder;
import de.gravitex.banking.entity.TradingPartner;
import de.gravitex.banking.entity.TradingPartnerBookingHistory;
import de.gravitex.banking_core.dto.BookingAdminData;
import de.gravitex.banking_core.util.StringHelper;
import de.gravitex.banking_core.util.db.info.base.DatabaseTypeInfo;

public abstract class ManualWebTester implements WebTester {

	private IBankingAccessor bankingAccessor;

	private BookingAdminData adminData;

	private DatabaseTypeInfo databaseInfo;

	private WebTesterObjectCache objectCache = new WebTesterObjectCache();

	private WebTestReporterStub webTestReporter;

	private ValueValidator validator = new ValueValidator();

	private boolean traceEnabled = false;

	private WebTestWatcher webTestWatcher;

	private boolean active;

	public ManualWebTester(WebTestWatcher aWebTestWatcher, boolean isActive) {
		
		super();
		
		webTestReporter = initTestReporter();
		webTestWatcher = aWebTestWatcher;
		
		this.active = isActive;
		
		this.bankingAccessor = new BankingAccessor();
	}

	private WebTestReporterStub initTestReporter() {
		return new GuiWebTestReporter();
	}

	public ManualWebTester connect() {

		adminData = (BookingAdminData) bankingAccessor.readAdminData().getEntity();
		databaseInfo = ApplicationRegistry.getInstance().getDatabaseAdministrator()
				.getDatabaseInfoForDriverClass(adminData.getDatabaseDriverClass());
		if (!databaseInfo.shouldRunTests()) {
			throw new ManualWebTesterException(
					"unsuitable db type for running tests {" + databaseInfo.getTypeDescription() + "}!!!");
		}
		return this;
	}

	public abstract ManualWebTester runTests();

	private void evaluateRequestResult(HttpResult aHttpResult, boolean aShouldSuceed, String aVariableName,
			ExceptionMatcher aExceptionMatcher, ResponseLengthValidator aResponseLengthValidator) {
		
		if (aShouldSuceed && (aExceptionMatcher != null)) {
			throw new ManualWebTesterException(
					"exception matcher must not be provided on expecting a positive request result {"
							+ aHttpResult.getRequestUrl() + "}!!!");
		}
		if (aShouldSuceed) {
			if (!aHttpResult.hasValidStatusCode()) {
				throw new ManualWebTesterException(
						"request [" + aHttpResult.getRequestType() + "] for url {" + aHttpResult.getRequestUrl()
								+ "} should have suceeded, but it failed --> " + aHttpResult.getErrorMessage());
			}
		} else {
			if (aHttpResult.hasValidStatusCode()) {
				throw new ManualWebTesterException(
						"request [" + aHttpResult.getRequestType() + "] for url {" + aHttpResult.getRequestUrl()
								+ "} should have failed, but it suceeded --> " + aHttpResult.getErrorMessage());
			}
		}
		if (aHttpResult.hasValidStatusCode()) {
			if (!StringHelper.isBlank(aVariableName)) {
				aHttpResult.cacheRequestResult(objectCache, aVariableName);
			}
			if (aResponseLengthValidator != null) {
				aResponseLengthValidator.acceptResponseLength(aHttpResult.getActualResponseLength());
			}			
		} else {
			if (aExceptionMatcher != null) {
				if (!aExceptionMatcher.matchesException(aHttpResult.getErrorMessage())) {
					throw new ManualWebTesterException("expected error message {" + aExceptionMatcher.getExceptionPart()
							+ "} does match {" + aHttpResult.getErrorMessage() + "}!!!");
				}
			}
		}

		evaluateStatusCode(aHttpResult, aShouldSuceed);

		webTestReporter.acceptSuccess(aHttpResult, aShouldSuceed, traceEnabled);
		webTestWatcher.acceptSuccess(aHttpResult, aShouldSuceed, traceEnabled, this);
	}

	private void evaluateStatusCode(HttpResult aHttpResult, boolean aShouldSuceed) {
		if (aHttpResult.hasValidStatusCode()) {
			if (!aShouldSuceed) {
				throw new ManualWebTesterException(null);
			}
		} else {
			if (aShouldSuceed) {
				throw new ManualWebTesterException(null);
			}
		}
	}

	public IBankingAccessor getBankingAccessor() {
		return bankingAccessor;
	}

	protected WebTesterObjectCache getObjectCache() {
		return objectCache;
	}

	protected ExceptionMatcher errorMessageContains(String aContainedString) {
		return new ContainedStringExceptionMatcher(aContainedString);
	}

	public void proclaimSuccess() {
		webTestReporter.onTestSucceed(this);
		webTestWatcher.onTestSucceed(this);
	}

	private void acceptHttpResult(HttpResult aHttpResult, boolean aShouldSuceed, String aVariableName,
			ExceptionMatcher aExceptionMatcher, ResponseLengthValidator aResponseLengthValidator) {
		evaluateRequestResult(aHttpResult, aShouldSuceed, aVariableName, aExceptionMatcher, aResponseLengthValidator);
	}

	public void expectSuccess(HttpResult aHttpResult, String aVariableName,
			ResponseLengthValidator aResponseLengthValidator) {
		acceptHttpResult(aHttpResult, true, aVariableName, null, aResponseLengthValidator);
	}
	
	public void expectFailure(HttpResult aHttpResult) {
		expectFailure(aHttpResult, null);
	}

	public void expectFailure(HttpResult aHttpResult, ExceptionMatcher aExceptionMatcher) {
		acceptHttpResult(aHttpResult, false, null, aExceptionMatcher, null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void describeCachedObject(String aVariableName) {
		Object object = getObjectCache().getObject(aVariableName);
		if (object instanceof List) {
			List<Object> list = (List) object;
			System.out.println(aVariableName + " is list with {" + list.size() + "} entries...");
		} else {
			System.out.println(aVariableName + " is object of class {" + object.getClass().getSimpleName() + "}...");
		}
	}
	
	public ManualWebTester removeEntities() {
		
		new EntitiesRemover(this)			
			.withEntityClass(BookingImportItem.class)
			.withEntityClass(BookingImport.class)			
			.withEntityClass(TradingPartnerBookingHistory.class)
			.withEntityClass(Booking.class)
			.withEntityClass(Account.class)
			.withEntityClass(BudgetPlanning.class)
			.withEntityClass(BudgetPlanningItem.class)
			.withEntityClass(CreditInstitute.class)
			.withEntityClass(TradingPartner.class)			
			.withEntityClass(PurposeCategory.class)
			.withEntityClass(StandingOrder.class)
			.remove();
		
		return this;
	}
	
	protected ValueValidator getValidator() {
		return validator;
	}

	public ManualWebTester enableTrace() {
		traceEnabled = true;
		return this;
	}
}