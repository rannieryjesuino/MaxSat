package br.uece.lotus.tools.MaxSat;

import br.uece.lotus.Component;
import br.uece.lotus.State;
import br.uece.lotus.Transition;
import br.uece.lotus.project.ProjectExplorer;
import br.uece.seed.app.UserInterface;
import br.uece.seed.ext.ExtensionManager;
import br.uece.seed.ext.Plugin;
import br.uece.lotus.tools.MaxSat.MaxSatAlgorithm;

/**
 * Created by Ranniery on 11/02/2016.
 */

public class MaxSatPlugin extends Plugin {

    private UserInterface mUserInterface;
    private ProjectExplorer mProjectExplorer;

    @Override
    public void onStart(ExtensionManager extensionManager) throws Exception {
        mUserInterface = (UserInterface) extensionManager.get(UserInterface.class);
        mProjectExplorer = (ProjectExplorer) extensionManager.get(ProjectExplorer.class);

        mUserInterface.getMainMenu().newItem("3-SAT/MaxSat")
                .setWeight(Integer.MAX_VALUE)
                .setAction(() -> {
                    MaxSatAlgorithm.Magic();
                })
                .create();

    }
}
