package org.optaplanner.constraint.streams.drools.common;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

import org.drools.base.reteoo.BaseTuple;
import org.drools.base.rule.Declaration;
import org.drools.core.reteoo.SubnetworkTuple;
import org.drools.model.Variable;
import org.kie.api.runtime.rule.FactHandle;
import org.optaplanner.core.api.score.stream.uni.UniConstraintCollector;

final class UniAccumulator<A, ResultContainer_, Result_> extends AbstractAccumulator<ResultContainer_, Result_> {

    private final String varA;
    private final BiFunction<ResultContainer_, A, Runnable> accumulator;

    private Declaration declaration;
    private UnaryOperator<BaseTuple> tupleExtractor;
    private boolean isSubnetwork;

    public UniAccumulator(Variable<A> varA, UniConstraintCollector<A, ResultContainer_, Result_> collector) {
        super(collector.supplier(), collector.finisher());
        this.varA = varA.getName();
        this.accumulator = Objects.requireNonNull(collector.accumulator());
    }

    @Override
    protected Runnable accumulate(ResultContainer_ context, BaseTuple leftTuple, FactHandle handle,
            Declaration[] innerDeclarations) {
        FactHandle factHandle = getInternalFactHandle(leftTuple, handle);
        A a = (A) declaration.getValue(null, factHandle.getObject());
        return accumulator.apply(context, a);
    }

    private FactHandle getInternalFactHandle(BaseTuple leftTuple, FactHandle handle) {
        if (tupleExtractor == null) { // Happens either when the tuple offset is 0, or when not a subnetwork.
            if (isSubnetwork) {
                return leftTuple.getFactHandle();
            } else {
                return handle;
            }
        }
        return tupleExtractor.apply(leftTuple).getFactHandle();
    }

    @Override
    protected void initialize(BaseTuple leftTuple, Declaration[] innerDeclarations) {
        for (Declaration declaration : innerDeclarations) {
            if (declaration.getBindingName().equals(varA)) {
                this.declaration = declaration;
                break;
            }
        }
        isSubnetwork = leftTuple instanceof SubnetworkTuple;
        if (isSubnetwork) {
            tupleExtractor = ValueExtractor.getTupleExtractor(declaration, leftTuple);
        }
    }

}
