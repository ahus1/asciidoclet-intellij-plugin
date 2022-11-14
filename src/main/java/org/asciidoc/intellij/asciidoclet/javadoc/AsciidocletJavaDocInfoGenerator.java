package org.asciidoc.intellij.asciidoclet.javadoc;

import com.intellij.codeInsight.javadoc.JavaDocInfoGenerator;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.apache.commons.io.FileUtils;
import org.asciidoc.intellij.AsciiDocWrapper;
import org.asciidoc.intellij.AsciiDocExtensionService;
import org.asciidoc.intellij.asciidoclet.settings.AsciidocletApplicationSettings;
import org.asciidoc.intellij.editor.AsciiDocPreviewEditor;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * This post-processes Javadoc content with the AsciiDoc processor to support
 * <a href="https://github.com/asciidoctor/asciidoclet">Asciidoclet</a>.
 *
 * @author Alexander Schwartz (alexander.schwartz@gmx.net) 2020
 */
public class AsciidocletJavaDocInfoGenerator extends JavaDocInfoGenerator {
  public static final String START = "<div class='content'>\n";
  public static final String END = "</div>";
  private final Project project;
  private final PsiElement element;

  public AsciidocletJavaDocInfoGenerator(Project project, PsiElement element) {
    super(project, element);
    this.project = project;
    this.element = element;
  }

  @Override
  public @Nullable String generateDocInfo(List<String> docURLs) {
    String html = super.generateDocInfo(docURLs);
    html = touchUp(html);
    return html;
  }

  @Override
  public @Nullable String generateRenderedDocInfo() {
    String html = super.generateRenderedDocInfo();
    html = touchUp(html);
    return html;
  }

  /**
   * Touch up Javadoc by extracing AsciiDoc content an re-processing it via Asciidoctor.
   * <p/>
   * Parsing the HTML content to reverse-engineer the AsciiDoc content looks a bit crude on first sight,
   * but has some distinct advantages:
   * <ul>
   *   <li>All @link and @code references have already been processed.</li>
   *   <li>Heavy lifting to parse annotations in the documentation has already been completed.</li>
   * </ul>
   * <p>
   * Known limitations: if the AsciiDoc uses something that looks like an annotation, the parsing will terminate at
   * that point. Users should change the "@" of the annotation to "{at}".
   */
  private String touchUp(String html) {
    if (!AsciidocletApplicationSettings.getInstance().getAsciidocletPreviewSettings().isAsciidocletEnabled()) {
      return html;
    }

    if (html != null) {
      int start = html.indexOf(START);
      if (start == -1) {
        // no Javadoc content found
        return html;
      }
      int end = html.lastIndexOf(END);
      // in some places the IntelliJ code adds a <p> right before the div, remove that as well.
      //noinspection StringOperationCanBeSimplified
      if (html.substring(end - 3, end).equals("<p>")) {
        end -= 3;
      }

      // extract real AsciiDoc content
      @Language("asciidoc") String content = html.substring(start + START.length(), end);

      // remove indentation spaces at beginning of the line
      int c = 0;
      while (content.length() > c && content.charAt(c) == ' ') {
        c++;
      }
      content = content.replaceAll("\n" + content.substring(0, c), "\n");
      content = content.substring(c);

      // standard replacements of Asciidoclet
      content = content.replaceAll("\\\\/", "/");
      content = content.replaceAll("\\{at}", "@");
      content = content.replaceAll("\\{slash}", "/");

      // change links to classes created by JavaDoc to pass-through content
      content = content.replaceAll("(</?(code|a)[^\n>]*>)", "+++$1+++");

      // if the result contains <p> or <strong> HTML tags, the content was HTML-formatted Javadoc,
      // therefore don't post-process this content.
      if (content.contains("<p>") || content.contains("<strong>")) {
        return html;
      }

      // Convert AsciiDoc content to HTML
      PsiFile psiFile = element.getContainingFile();
      VirtualFile virtualFile = null;
      if (psiFile != null) {
        virtualFile = psiFile.getVirtualFile();
      }
      final String config = AsciiDocWrapper.config(virtualFile, project);
      Path tempImagesPath = AsciiDocWrapper.tempImagesPath(null, null);
      List<String> extensions = ApplicationManager.getApplication().getService(AsciiDocExtensionService.class).getExtensions(project);
      try {
        File fileBaseDir = new File("");
        if (element.getProject().getBasePath() != null) {
          fileBaseDir = new File(element.getProject().getBasePath());
        }
        String name = "unkown";
        if (virtualFile != null) {
          name = virtualFile.getName();
        }
        AsciiDocWrapper asciiDoc = new AsciiDocWrapper(project, fileBaseDir,
          tempImagesPath, name);
        content = asciiDoc.render(content, config, extensions);
      } finally {
        if (tempImagesPath != null) {
          try {
            FileUtils.deleteDirectory(tempImagesPath.toFile());
          } catch (IOException _ex) {
            Logger.getInstance(AsciiDocPreviewEditor.class).warn("could not remove temp folder", _ex);
          }
        }
      }

      // place processed AsciiDoc content inside original HTML frame
      html = html.substring(0, start + START.length()) + content + html.substring(end);
    }
    return html;
  }


}
