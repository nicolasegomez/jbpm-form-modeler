package org.jbpm.formModeler.core.processing.fieldHandlers.checkboxList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.jbpm.formModeler.api.client.FormRenderContext;
import org.jbpm.formModeler.api.client.FormRenderContextManager;
import org.jbpm.formModeler.api.model.Field;
import org.jbpm.formModeler.core.config.SelectValuesProvider;
import org.jbpm.formModeler.core.processing.fieldHandlers.InputTextFieldHandler;
import org.jbpm.formModeler.service.cdi.CDIBeanLocator;

@Named("org.jbpm.formModeler.core.processing.fieldHandlers.checkboxList.CheckboxListFieldHandler")
public class CheckboxListFieldHandler extends InputTextFieldHandler {
    
	@Inject
    private FormRenderContextManager formRenderContextManager;
    
	/**
     * Determine the list of class types this field can generate. That is, normally,
     * a field can generate multiple outputs (an input text can generate Strings,
     * Integers, ...)
     *
     * @return the set of class types that can be generated by this handler.
     */
    public String[] getCompatibleClassNames() {
        return new String[]{List.class.getName()};
    }

    /**
     * Read a parameter value (normally from a request), and translate it to
     * an object with desired class (that must be one of the returned by this handler)
     *
     * @return a object with desired class
     * @throws Exception
     */
    public Object getValue(Field field, String inputName, Map parametersMap, Map filesMap, String desiredClassName, Object previousValue) throws Exception {
        String[] paramValue = (String[]) parametersMap.get(inputName + "[]");
        if (paramValue == null || paramValue.length == 0) return null;
        SelectValuesProvider provider = (SelectValuesProvider) CDIBeanLocator.getBeanByNameOrType(field.getCustomFieldType());
        Map<String, Object> fieldRange = provider.getSelectOptions(field, previousValue, formRenderContextManager.getRootContext(inputName), null);
        
        if ("".equals(paramValue[0]))
             return null;
        
        ArrayList result = new ArrayList();
        for (int i = 0; i < paramValue.length; i++) {
        	String key = paramValue[i];
        	result.add(fieldRange.get(key));
		}
        return result;
    }
}