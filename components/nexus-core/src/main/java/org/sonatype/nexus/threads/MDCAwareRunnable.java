/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2007-2013 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.threads;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import org.slf4j.MDC;

/**
 * Runnable that properly sets MDC context before invoking the delegate. The delegate will execute in a
 * managed thread with properly set MDC context. To be used with managed threads.
 *
 * @author cstamas
 * @since 2.6
 */
public class MDCAwareRunnable implements Runnable
{
    private final Runnable delegate;

    private final Map mdcContext;

    public MDCAwareRunnable( final Runnable delegate )
    {
        this.delegate = checkNotNull( delegate );
        this.mdcContext = MDC.getCopyOfContextMap();
    }

    @Override
    public void run()
    {
        if ( mdcContext != null )
        {
            MDC.setContextMap( mdcContext );
            MDCUtils.setMDCUserIdIfNeeded();
        }
        delegate.run();
    }
}
