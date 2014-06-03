/*
 * Copyright 2014 JBoss, by Red Hat, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jbpm.formModeler.panels.modeler.backend.indexing.model.terms.valueterms;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jbpm.formModeler.panels.modeler.backend.indexing.model.terms.DataHolderIndexTerm;
import org.kie.workbench.common.services.refactoring.model.index.terms.valueterms.ValueIndexTerm;
import org.uberfire.commons.validation.PortablePreconditions;

@Portable
public class ValueDataHolderIndexTerm extends DataHolderIndexTerm implements ValueIndexTerm {
    private String holderId;

    public ValueDataHolderIndexTerm() {
        //Errai marshalling
    }

    public ValueDataHolderIndexTerm(final String holderId) {
        this.holderId = PortablePreconditions.checkNotNull("holderId", holderId);
    }

    @Override
    public String getValue() {
        return holderId;
    }
}