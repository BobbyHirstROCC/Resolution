package org.optaplanner.core.config.heuristic.selector.move.generic;

import java.util.Comparator;

import jakarta.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum SubPillarType {

    /**
     * Pillars will only be affected in their entirety.
     */
    NONE,
    /**
     * Pillars may also be affected partially, and the resulting subpillar returned in an order according to a given
     * {@link Comparator}.
     */
    SEQUENCE,
    /**
     * Pillars may also be affected partially, the resulting subpillar returned in random order.
     */
    ALL;
}
