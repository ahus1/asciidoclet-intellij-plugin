package org.asciidoc.intellij.asciidoclet.javadoc;

import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiDocCommentBase;
import org.asciidoc.intellij.asciidoclet.settings.AsciidocletApplicationSettings;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This documentation provider is used to display the rendered documentation inline in the editor.
 * It delegates to the {@link AsciidocletJavaDocInfoGenerator} that generates the HTML to be shown inline.
 */
public class AsciidocletDocumentationProvider implements DocumentationProvider {
  @Override
  public @Nls @Nullable String generateRenderedDoc(@NotNull PsiDocCommentBase comment) {
    if (!AsciidocletApplicationSettings.getInstance().getAsciidocletPreviewSettings().isAsciidocletEnabled()) {
      return null;
    }
    return new AsciidocletJavaDocInfoGenerator(comment.getProject(), comment).generateRenderedDocInfo();
  }
}
