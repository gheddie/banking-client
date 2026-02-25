package de.gravitex.banking.client.sorter.base;

import java.lang.reflect.Field;
import java.util.List;

public interface EntitySorter {

	List<?> sortEntities(List<?> entites, Object firstEntity, List<Field> displayableFields);
}