package org.wirelessLabs.dataProviders;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.jbpm.formModeler.api.client.FormRenderContext;
import org.jbpm.formModeler.api.model.Field;
import org.jbpm.formModeler.core.config.SelectValuesProvider;

public class MockedSelectValuesProviderImpl implements SelectValuesProvider {

	@Inject
	EntityManagerFactory entityManagerFactory;

	@Override
	public String getIdentifier() {
		return "JPASelectValuesProvider";
	}

	@Override
	public Map<String, Object> getSelectOptions(Field field, Object value,
			FormRenderContext renderContext, Locale locale) {

		HashMap<String, Object> selectOptions = new HashMap<String, Object>();
			
		selectOptions.put("key 1", Integer.valueOf(1));
		selectOptions.put("key 2", Integer.valueOf(2));
		selectOptions.put("key 3", Integer.valueOf(3));
		selectOptions.put("key 4", Integer.valueOf(4));

		return selectOptions;
	}

	private Query createQuery(String queryConfigured) {
		return entityManagerFactory.createEntityManager().createQuery(
				queryConfigured);

	}

}
