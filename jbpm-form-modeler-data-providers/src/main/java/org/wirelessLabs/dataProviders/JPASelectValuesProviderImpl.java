package org.wirelessLabs.dataProviders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.jbpm.formModeler.api.client.FormRenderContext;
import org.jbpm.formModeler.api.model.Field;
import org.jbpm.formModeler.core.config.SelectValuesProvider;
import org.jbpm.formModeler.kie.services.FormRenderContentMarshallerManager;
import org.kie.internal.task.api.ContentMarshallerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uberfire.io.IOService;

public class JPASelectValuesProviderImpl implements SelectValuesProvider {

	@Inject
	FormRenderContentMarshallerManager formRenderContentMarshallerManager;

	private Logger log = LoggerFactory
			.getLogger(JPASelectValuesProviderImpl.class);

	@Override
	public String getIdentifier() {
		return "JPASelectValuesProvider";
	}

	@Inject
	@Named("ioStrategy")
	private IOService ioService;

	@Override
	public Map<String, Object> getSelectOptions(Field field, Object value,
			FormRenderContext renderContext, Locale locale) {

		HashMap<String, Object> selectOptions = new HashMap<String, Object>();
		if (field.getRangeFormula() != null
				&& !field.getRangeFormula().equals("") && renderContext != null) {
			List queryResult = executeQuery(field.getRangeFormula(),
					renderContext);
			for (Object eachResult : queryResult) {
				selectOptions.put(eachResult.toString(), eachResult);
			}
			return selectOptions;
		}
		selectOptions.put("empty", null);

		return selectOptions;
	}

	private List executeQuery(String queryConfigured,
			FormRenderContext renderContext) {
		ClassLoader tccl = Thread.currentThread().getContextClassLoader();
		try {
			ContentMarshallerContext contextMarshaller = formRenderContentMarshallerManager
					.getContentMarshaller(renderContext.getUID());

			if (contextMarshaller != null) {
				ClassLoader classLoader = contextMarshaller.getClassloader();
				if (classLoader != null) {
					// override tccl so persistence unit can be found from
					// within kjar
					Thread.currentThread().setContextClassLoader(classLoader);

					EntityManagerFactory entityManagerFactory = Persistence
							.createEntityManagerFactory("org.jbpm.custom");
					if (entityManagerFactory != null) {
						Query query = entityManagerFactory
								.createEntityManager().createQuery(
										queryConfigured);
						List queryResult = query.getResultList();
						return queryResult;
					}
				}
			}
		} finally {
			Thread.currentThread().setContextClassLoader(tccl);
		}
		return new ArrayList<Object>();
	}

}
