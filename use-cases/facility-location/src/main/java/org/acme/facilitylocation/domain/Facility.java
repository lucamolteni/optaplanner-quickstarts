/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.acme.facilitylocation.domain;

import java.util.ArrayList;
import java.util.List;

import org.acme.facilitylocation.solver.FacilityLocationConstraintProvider;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.InverseRelationShadowVariable;

/**
 * Facility satisfies consumers' demand. Cumulative demand of all consumers assigned to this facility must not exceed
 * the facility's capacity. This requirement is expressed by the {@link FacilityLocationConstraintProvider#facilityCapacity
 * facility capacity} constraint.
 */
// This is a shadow planning entity, not a genuine planning entity, because it has a shadow variable (consumers).
@PlanningEntity
public class Facility {

    private long id;
    private Location location;
    private long setupCost;
    private long capacity;

    @InverseRelationShadowVariable(sourceVariableName = "facility")
    private List<Consumer> consumers = new ArrayList<>();

    public Facility() {
    }

    public Facility(long id, Location location, long setupCost, long capacity) {
        this.id = id;
        this.location = location;
        this.setupCost = setupCost;
        this.capacity = capacity;
    }

    public long getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getSetupCost() {
        return setupCost;
    }

    public void setSetupCost(long setupCost) {
        this.setupCost = setupCost;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public long getUsedCapacity() {
        return consumers.stream().mapToLong(Consumer::getDemand).sum();
    }

    public boolean isUsed() {
        return !consumers.isEmpty();
    }

    @Override
    public String toString() {
        return "Facility " + id +
                " ($" + setupCost
                + ", " + capacity + " cap)";
    }
}
