package org.optaplanner.quarkus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collections;

import jakarta.inject.Inject;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.optaplanner.core.api.domain.common.DomainAccessType;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.quarkus.testdata.normal.constraints.TestdataQuarkusConstraintProvider;
import org.optaplanner.quarkus.testdata.normal.domain.TestdataQuarkusEntity;
import org.optaplanner.quarkus.testdata.normal.domain.TestdataQuarkusSolution;

import io.quarkus.test.QuarkusUnitTest;

class OptaPlannerProcessorXMLNoneTest {

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addClasses(TestdataQuarkusEntity.class,
                            TestdataQuarkusSolution.class, TestdataQuarkusConstraintProvider.class));

    @Inject
    SolverConfig solverConfig;
    @Inject
    SolverFactory<TestdataQuarkusSolution> solverFactory;

    @Test
    void solverConfigXml_default() {
        assertNotNull(solverConfig);
        assertEquals(TestdataQuarkusSolution.class, solverConfig.getSolutionClass());
        assertEquals(DomainAccessType.GIZMO, solverConfig.getDomainAccessType());
        assertEquals(Collections.singletonList(TestdataQuarkusEntity.class), solverConfig.getEntityClassList());
        assertEquals(TestdataQuarkusConstraintProvider.class,
                solverConfig.getScoreDirectorFactoryConfig().getConstraintProviderClass());
        // No termination defined (solverConfig.xml isn't included)
        assertNull(solverConfig.getTerminationConfig().getSecondsSpentLimit());
        assertNotNull(solverFactory);
        assertNotNull(solverFactory.buildSolver());
    }

}
