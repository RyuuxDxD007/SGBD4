package be.iramps.ue1103.mvc.Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.DriverManager;
import java.util.ArrayList;
import be.iramps.ue1103.mvc.Model.BL.Section;
import be.iramps.ue1103.mvc.Model.BL.Status;
import be.iramps.ue1103.mvc.Model.DAL.Sections.ISectionDAO;
import be.iramps.ue1103.mvc.Model.DAL.Sections.SectionDAO;
import be.iramps.ue1103.mvc.Model.DAL.Status.IStatusDAO;
import be.iramps.ue1103.mvc.Model.DAL.Status.StatusDAO;

public class PrimaryModel implements IModel{
    private PropertyChangeSupport support;
    private ISectionDAO iSectionDAO;
    private IStatusDAO iStatusDAO;
    final String url = "jdbc:postgresql://localhost:5432/postgres";
    final String user = "postgres";
    final String password = "toto1234";
    private java.sql.Connection connexion;

    public PrimaryModel(){
        this.support = new PropertyChangeSupport(this);
        this.iSectionDAO = new SectionDAO(url, user, password);
        this.iStatusDAO = new StatusDAO(url, user, password);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    @Override
    public void getAllSection(){
        ArrayList<Section> sections = this.iSectionDAO.getSections();
        ArrayList<String> sectionsName = new ArrayList<>();
        for (Section section : sections) {
            sectionsName.add(section.getNom());
        }
        support.firePropertyChange("listeSection", "", sectionsName);        
    }

    @Override
    public void getSection(String sectionName){
        int id = this.iSectionDAO.getIDSection(sectionName);
        ArrayList<String> infoSection = new ArrayList<>();
        infoSection.add(Integer.toString(id));
        infoSection.add(sectionName);
        support.firePropertyChange("sectionSelected", "", infoSection );
    }

    @Override
    public void deleteSection(String id) {
        this.iSectionDAO.deleteSection(Integer.parseInt(id));
        this.getAllSection();
    }

    @Override
    public void updateSection(String id, String nom) {
        this.iSectionDAO.updateSection(Integer.parseInt(id), nom);
        this.getSection(nom);
    }

    @Override
    public void insertSection(String nom) {
        this.iSectionDAO.addSection(nom);
        this.getSection(nom);
    }

    @Override
    public void getAllStatus() {
        ArrayList<Status> status = this.iStatusDAO.getStatus();
        ArrayList<String> statusName = new ArrayList<>();
        for (Status statuss : status) {
            statusName.add(statuss.getNom());
        }
        support.firePropertyChange("listeStatus", "", statusName);
    }

    @Override
    public void getStatus(String status) {
        int id = this.iStatusDAO.getIDStatus(status);
        ArrayList<String> infoStatus = new ArrayList<>();
        infoStatus.add(Integer.toString(id));
        infoStatus.add(status);
        support.firePropertyChange("statusSelected", "", infoStatus );
    }

    @Override
    public void insertStatus(String status) {
        this.iStatusDAO.addStatus(status);
        this.getStatus(status);
    }

    @Override
    public void deleteStatus(String id) {
        this.iStatusDAO.deleteStatus(Integer.parseInt(id));
        this.getAllSection();
    }

    @Override
    public void updateStatus(String id, String status) {
        this.iStatusDAO.updateStatus(Integer.parseInt(id), status);
        this.getStatus(status);
    }

    @Override
    public void close() {
        this.iSectionDAO.close();
        this.iStatusDAO.close();
    }

}
