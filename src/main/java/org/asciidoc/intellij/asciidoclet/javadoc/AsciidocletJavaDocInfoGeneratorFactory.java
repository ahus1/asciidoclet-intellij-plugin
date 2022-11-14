package org.asciidoc.intellij.asciidoclet.javadoc;

import com.intellij.codeInsight.javadoc.JavaDocInfoGenerator;
import com.intellij.codeInsight.javadoc.JavaDocInfoGeneratorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class AsciidocletJavaDocInfoGeneratorFactory extends JavaDocInfoGeneratorFactory {

  @Override
  protected JavaDocInfoGenerator createImpl(@NotNull Project project, PsiElement element) {
    return new AsciidocletJavaDocInfoGenerator(project, element);
  }
}
