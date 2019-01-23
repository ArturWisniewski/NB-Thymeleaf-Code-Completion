package org.netbeans.modules.thymeleafcodecompletion.wizard;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.Sources;
import org.netbeans.api.templates.TemplateRegistration;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;

@TemplateRegistration(
        folder = "Other",
        displayName = "Thymeleaf HTML template",
        iconBase = "org/netbeans/modules/thymeleafcodecompletion/Thymeleaf.png",
        scriptEngine = "freemarker",
        description = "description.html",
        content = "thymeleaf.html.template",
        category = "simple-files",
        position = 210
)
public final class ThymeleafWizardIterator implements WizardDescriptor.InstantiatingIterator<WizardDescriptor> {

    public static final String WIZ_DIALECT = "dialect";

    private WizardDescriptor wizard;
    private WizardDescriptor.Panel<WizardDescriptor> wizardPanel;
    private ThymeleafWizardPanel1 bottomPanel;

    @Override
    public Set<?> instantiate() throws IOException {
        // Create file from template
        final FileObject foDir = Templates.getTargetFolder(wizard);
        final FileObject foTemplate = Templates.getTemplate(wizard);
        DataObject doTemplate = DataObject.find(foTemplate);
        DataFolder df = DataFolder.findFolder(foDir);
        Map<String, Object> props = new HashMap<>();
        props.put(WIZ_DIALECT, wizard.getProperty(WIZ_DIALECT));
        DataObject doCreated = doTemplate.createFromTemplate(df, Templates.getTargetName(wizard), props);
        FileObject foCreated = doCreated.getPrimaryFile();
        return Collections.singleton(foCreated);
    }

    @Override
    public void initialize(WizardDescriptor wizard) {
        this.wizard = wizard;
        wizard.putProperty(WIZ_DIALECT, 0);
        bottomPanel = new ThymeleafWizardPanel1();
        // force creation of visual part
        Project project = Templates.getProject(wizard);
        Sources sources = ProjectUtils.getSources(project);
        wizardPanel = Templates.
                buildSimpleTargetChooser(project, sources.getSourceGroups(Sources.TYPE_GENERIC))
                .bottomPanel(bottomPanel)
                .create();
    }

    @Override
    public void uninitialize(WizardDescriptor wizard) {
        System.out.println("org.netbeans.modules.thymeleafcodecompletion.wizard.ThymeleafWizardIterator.uninitialize()");
        wizard.putProperty(WIZ_DIALECT, null);
        bottomPanel = null;
        wizardPanel = null;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> current() {
        return wizardPanel;
    }

    @Override
    public String name() {
        return "1 of 1";
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public void nextPanel() {
    }

    @Override
    public void previousPanel() {
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

}
