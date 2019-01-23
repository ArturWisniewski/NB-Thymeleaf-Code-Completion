package org.netbeans.modules.thymeleafcodecompletion.wizard;

import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class ThymeleafWizardPanel1 implements WizardDescriptor.Panel<WizardDescriptor> {

    private ThymeleafVisualPanel1 component;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public ThymeleafVisualPanel1 getComponent() {
        if (component == null) {
            component = new ThymeleafVisualPanel1();
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        getComponent().read(wiz);
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        getComponent().store(wiz);
    }

}
