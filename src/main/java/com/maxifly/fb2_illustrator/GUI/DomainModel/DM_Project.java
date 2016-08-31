package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.model.Illustration;
import com.maxifly.fb2_illustrator.model.Project;

import java.util.List;

/**
 * Created by Maxim.Pantuhin on 31.08.2016.
 */
public class DM_Project extends DM_Abstract {
    private Project project;



    public DM_Project(Project project) {
        this.project = project;
    }

    public List<Illustration> getIllList() {
       return project.getIllustrations();
    }



}
