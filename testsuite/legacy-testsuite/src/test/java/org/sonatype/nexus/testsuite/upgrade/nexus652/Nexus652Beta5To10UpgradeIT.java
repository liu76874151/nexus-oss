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

package org.sonatype.nexus.testsuite.upgrade.nexus652;

import org.sonatype.nexus.configuration.model.Configuration;
import org.sonatype.nexus.integrationtests.AbstractNexusIntegrationTest;
import org.sonatype.nexus.integrationtests.TestContainer;
import org.sonatype.security.configuration.model.SecurityConfiguration;
import org.sonatype.security.configuration.source.SecurityConfigurationSource;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

// FIXME: This test (according to javadoc) is for ancient nexus, why are we keeping this around and running it?!

/**
 * Test nexus.xml after and upgrade from 1.0.0-beta-5 to 1.0.0.
 */
public class Nexus652Beta5To10UpgradeIT
    extends AbstractNexusIntegrationTest
{
  @BeforeClass
  public static void setSecureTest() {
    TestContainer.getInstance().getTestContext().setSecureTest(true);
  }

  @Test
  public void checkNexusConfig()
      throws Exception
  {
    // if we made it this far the upgrade worked...

    SecurityConfigurationSource securitySource = lookup(SecurityConfigurationSource.class, "file");
    SecurityConfiguration securityConfig = securitySource.loadConfiguration();

    // we need this to have access to uncrypted password (see assertion below)
    Configuration nexusConfig = getNexusConfigUtil().loadAndUpgradeNexusConfiguration();

    Assert.assertEquals("Smtp host:", "foo.org", nexusConfig.getSmtpConfiguration().getHostname());
    Assert.assertEquals("Smtp password:", "now", nexusConfig.getSmtpConfiguration().getPassword());
    Assert.assertEquals("Smtp username:", "void", nexusConfig.getSmtpConfiguration().getUsername());
    Assert.assertEquals("Smtp port:", 465, nexusConfig.getSmtpConfiguration().getPort());

    Assert.assertEquals("Security anon username:", "User3", securityConfig.getAnonymousUsername());
    Assert.assertEquals("Security anon password:", "y6i0t9q1e3", securityConfig.getAnonymousPassword());
    Assert.assertEquals("Security anon access:", true, securityConfig.isAnonymousAccessEnabled());
    Assert.assertEquals("Security realm size:", 2, securityConfig.getRealms().size());
    Assert.assertEquals("Security realm:", "XmlAuthenticatingRealm", securityConfig.getRealms().get(0));
    Assert.assertEquals("Security realm:", "XmlAuthorizingRealm", securityConfig.getRealms().get(1));

    Assert.assertEquals("http proxy:", true, nexusConfig.getHttpProxy().isEnabled());

    Assert.assertEquals("Base url:", AbstractNexusIntegrationTest.nexusBaseUrl,
        nexusConfig.getRestApi().getBaseUrl());

    // we will glance over the repos, because the unit tests cover this.
    Assert.assertEquals("Repository Count:", 9, nexusConfig.getRepositories().size());

    Assert.assertNotNull("repo: central", getNexusConfigUtil().getRepo("central"));
    Assert.assertNotNull("repo: apache-snapshots", getNexusConfigUtil().getRepo("apache-snapshots"));
    Assert.assertNotNull("repo: codehaus-snapshots", getNexusConfigUtil().getRepo("codehaus-snapshots"));
    Assert.assertNotNull("repo: releases", getNexusConfigUtil().getRepo("releases"));
    Assert.assertNotNull("repo: snapshots", getNexusConfigUtil().getRepo("snapshots"));
    Assert.assertNotNull("repo: thirdparty", getNexusConfigUtil().getRepo("thirdparty"));

    // FIXME: These assertions are very fragile and can easily break when the default security configuration changes

    org.sonatype.security.model.Configuration secConfig = getSecurityConfigUtil().getSecurityConfig();

    assertThat(secConfig.getUsers(), hasSize(7));
    assertThat(secConfig.getRoles(), hasSize(34));
  }
}
