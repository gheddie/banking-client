package de.gravitex.banking.client.tester.util;

import java.util.ArrayList;
import java.util.List;

import de.gravitex.banking.client.accessor.response.HttpDeleteResult;
import de.gravitex.banking.entity.base.IdEntity;

public class EntitiesRemover {
	
	private List<Class<? extends IdEntity>> entityClasses = new ArrayList<Class<? extends IdEntity>>();
	
	private WebTester webTester;
	
	public EntitiesRemover(WebTester aWebTester) {
		super();
		this.webTester = aWebTester;
	}

	public void remove() {
		for (Class<? extends IdEntity> aEntityClass : entityClasses) {
			removeEntityList(aEntityClass);
		}
	}

	private void removeEntityList(Class<? extends IdEntity> aEntityClass) {
		List<?> allEntities = webTester.getBankingAccessor().findAllEntities(aEntityClass).getEntityList();
		if (allEntities != null && !allEntities.isEmpty()) {
			System.out.println("found {" + allEntities.size() + "} entities to remove of class {"
					+ aEntityClass.getSimpleName() + "}...");
			for (Object aEntitiy : allEntities) {
				webTester.expectSuccess(removeEntity((IdEntity) aEntitiy));			
			}	
		}
	}

	private HttpDeleteResult removeEntity(IdEntity aEntitiy) {
		HttpDeleteResult deleteEntityResult = webTester.getBankingAccessor().deleteEntity(aEntitiy);
		System.out.println(deleteEntityResult + " --> " + deleteEntityResult.getStatusCode());
		return deleteEntityResult;		
	}

	public EntitiesRemover withEntityClass(Class<? extends IdEntity> aEntityClass) {
		entityClasses.add(aEntityClass);
		return this;
	}
}