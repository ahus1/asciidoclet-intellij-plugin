package org.asciidoc.intellij.asciidoclet.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.ui.EditorNotifications;
import com.intellij.util.messages.Topic;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.Property;
import org.asciidoctor.SafeMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@State(
  name = "AsciidocletApplicationSettings",
  storages = @Storage("asciidoclet.xml")
)
public class AsciidocletApplicationSettings implements PersistentStateComponent<org.asciidoc.intellij.asciidoclet.settings.AsciidocletApplicationSettings.State>,
  AsciidocletPreviewSettings.Holder {

  private State myState = new State();

  /* this is a transient state, will be discarded on every restart
    as the setting is changed for each project, we keep a state for each project.
   */
  private Map<String, Boolean> extensionsEnabled = new ConcurrentHashMap<>();
  private Map<String, Boolean> extensionsPresent = new ConcurrentHashMap<>();

  @NotNull
  public static org.asciidoc.intellij.asciidoclet.settings.AsciidocletApplicationSettings getInstance() {
    return ServiceManager.getService(org.asciidoc.intellij.asciidoclet.settings.AsciidocletApplicationSettings.class);
  }

  @Nullable
  @Override
  public State getState() {
    return myState;
  }

  @Override
  public void loadState(@NotNull State state) {
    XmlSerializerUtil.copyBean(state, myState);
  }

  @Override
  public void setAsciidocletPreviewSettings(@NotNull AsciidocletPreviewSettings settings) {
    myState.myPreviewSettings = settings;
  }

  @NotNull
  @Override
  public AsciidocletPreviewSettings getAsciidocletPreviewSettings() {
    return myState.myPreviewSettings;
  }

  public static class State {
    @Property(surroundWithTag = false)
    @NotNull
    private AsciidocletPreviewSettings myPreviewSettings = AsciidocletPreviewSettings.DEFAULT;
  }

}
