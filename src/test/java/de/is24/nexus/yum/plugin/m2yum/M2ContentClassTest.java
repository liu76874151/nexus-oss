package de.is24.nexus.yum.plugin.m2yum;

import org.junit.Assert;
import org.junit.Test;
import org.sonatype.nexus.proxy.maven.maven2.M2Repository;
import org.sonatype.nexus.proxy.registry.ContentClass;
import org.sonatype.nexus.proxy.repository.Repository;

import de.is24.nexus.yum.AbstractRepositoryTester;

public class M2ContentClassTest extends AbstractRepositoryTester {

  @Test
  public void shouldOverrideDefaultMaven2ContentClass() throws Exception {
    getContainer().addComponent(new M2ContentClass(), ContentClass.class, M2ContentClass.ID);
    Repository repo = getContainer().lookup(Repository.class, M2Repository.ID);
    Assert.assertTrue(repo.getRepositoryContentClass() instanceof M2ContentClass);
  }

}
