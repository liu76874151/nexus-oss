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
 * {@link Condition} related events.
 *
 * @since 2.0
 */
public class ConditionEvent
    extends AbstractEvent<Condition>
{

  public ConditionEvent(final Condition condition) {
    super(condition);
  }

  public Condition getCondition() {
    return getEventSender();
  }

  @Override
  public String toString() {
    return getCondition().toString();
  }

  /**
   * Event fired when a condition becomes satisfied.
   *
   * @since 2.0
   */
  public static class Satisfied
      extends ConditionEvent
  {

    public Satisfied(final Condition condition) {
      super(condition);
    }

    @Override
    public String toString() {
      return super.toString() + " is satisfied";
    }

  }

  /**
   * Event fired when a condition becomes unsatisfied.
   *
   * @since 2.0
   */
  public static class Unsatisfied
      extends ConditionEvent
  {

    public Unsatisfied(final Condition condition) {
      super(condition);
    }

    @Override
    public String toString() {
      return super.toString() + " is unsatisfied";
    }

  }

}