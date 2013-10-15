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

package org.sonatype.nexus.plugins.capabilities;

import org.sonatype.nexus.events.AbstractEvent;

/**
 * {@link CapabilityRegistry} related events.
 *
 * @since 2.0
 */
public class CapabilityRegistryEvent
    extends AbstractEvent<CapabilityRegistry>
{

  public CapabilityRegistryEvent(final CapabilityRegistry capabilityRegistry) {
    super(capabilityRegistry);
  }

  @Override
  public String toString() {
    return getEventSender().toString();
  }

  /**
   * Event fired after capabilities were loaded loaded from persistence store.
   *
   * @since 2.0
   */
  public static class AfterLoad
      extends CapabilityRegistryEvent
  {

    public AfterLoad(final CapabilityRegistry capabilityRegistry) {
      super(capabilityRegistry);
    }

    @Override
    public String toString() {
      return "Loaded " + super.toString();
    }

  }

}