/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jena.ontapi.model;

import org.apache.jena.rdf.model.RDFNode;

/**
 * A technical interface to provide {@link OntList Ontology []-list} instance.
 *
 * @param <E> any {@link RDFNode} - a type for list's item
 * @see HasRDFNodeList
 * @see SetComponents
 */
interface WithOntList<E extends RDFNode> extends HasRDFNodeList<E> {
    /**
     * Gets a modifiable []-list with items of the type {@link E}.
     *
     * @return {@link OntList Ontology []-list} with items of the type {@link E}
     */
    @Override
    OntList<E> getList();
}
