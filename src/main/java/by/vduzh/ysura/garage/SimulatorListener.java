/*
 * Copyright (c) 1996, 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package by.vduzh.ysura.garage;

/**
 * Base interface
 */
public interface SimulatorListener {

    /**
     * Invoked when an action occurs.
     */
    public void handleEvent(SimulatorEvent e);
}
