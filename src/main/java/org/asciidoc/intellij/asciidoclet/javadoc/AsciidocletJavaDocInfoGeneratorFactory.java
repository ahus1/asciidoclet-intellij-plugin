package org.asciidoc.intellij.asciidoclet.javadoc;

import com.intellij.codeInsight.javadoc.JavaDocInfoGenerator;
import com.intellij.codeInsight.javadoc.JavaDocInfoGeneratorFactory;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

public class AsciidocletJavaDocInfoGeneratorFactory extends JavaDocInfoGeneratorFactory {
  public static JavaDocInfoGeneratorFactory getInstance() {
    return ServiceManager.getService(JavaDocInfoGeneratorFactory.class);
  }

  @Override
  protected JavaDocInfoGenerator createImpl(Project project, PsiElement element) {
    return new AsciidocletJavaDocInfoGenerator(project, element);
  }
}
