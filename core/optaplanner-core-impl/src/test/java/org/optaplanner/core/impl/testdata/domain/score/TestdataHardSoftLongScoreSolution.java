package org.optaplanner.core.impl.testdata.domain.score;

import java.util.ArrayList;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import org.optaplanner.core.impl.testdata.domain.TestdataEntity;
import org.optaplanner.core.impl.testdata.domain.TestdataObject;
import org.optaplanner.core.impl.testdata.domain.TestdataValue;

@PlanningSolution
public class TestdataHardSoftLongScoreSolution extends TestdataObject {

    public static SolutionDescriptor<TestdataHardSoftLongScoreSolution> buildSolutionDescriptor() {
        return SolutionDescriptor.buildSolutionDescriptor(TestdataHardSoftLongScoreSolution.class, TestdataEntity.class);
    }

    public static TestdataHardSoftLongScoreSolution generateSolution() {
        return generateSolution(5, 7);
    }

    public static TestdataHardSoftLongScoreSolution generateSolution(int valueListSize, int entityListSize) {
        TestdataHardSoftLongScoreSolution solution = new TestdataHardSoftLongScoreSolution("Generated Solution 0");
        List<TestdataValue> valueList = new ArrayList<>(valueListSize);
        for (int i = 0; i < valueListSize; i++) {
            TestdataValue value = new TestdataValue("Generated Value " + i);
            valueList.add(value);
        }
        solution.setValueList(valueList);
        List<TestdataEntity> entityList = new ArrayList<>(entityListSize);
        for (int i = 0; i < entityListSize; i++) {
            TestdataValue value = valueList.get(i % valueListSize);
            TestdataEntity entity = new TestdataEntity("Generated Entity " + i, value);
            entityList.add(entity);
        }
        solution.setEntityList(entityList);
        return solution;
    }

    private List<TestdataValue> valueList;
    private List<TestdataEntity> entityList;

    HardSoftLongScore score;

    public TestdataHardSoftLongScoreSolution() {
    }

    public TestdataHardSoftLongScoreSolution(String code) {
        super(code);
    }

    @ValueRangeProvider(id = "valueRange")
    @ProblemFactCollectionProperty
    public List<TestdataValue> getValueList() {
        return valueList;
    }

    public void setValueList(List<TestdataValue> valueList) {
        this.valueList = valueList;
    }

    @PlanningEntityCollectionProperty
    public List<TestdataEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<TestdataEntity> entityList) {
        this.entityList = entityList;
    }

    @PlanningScore
    HardSoftLongScore getScore() {
        return score;
    }

    public void setScore(HardSoftLongScore score) {
        this.score = score;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

}