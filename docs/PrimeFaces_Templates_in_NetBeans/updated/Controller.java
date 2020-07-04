
<#if comment>

  TEMPLATE DESCRIPTION:

  This is Java template for 'JSF Pages From Entity Beans' controller class

. Templating
  is performed using FreeMaker (http://freemarker.org/) - see its documentation
  for full syntax. Variables available for templating are:

    controllerClassName - controller class name

(type: String)
    controllerPackageName - controller package name (type: String)
    entityClassName - entity class name

without package (type: String)
    importEntityFullClassName - whether to import entityFullClassName or not
    entityFullClassName - fully qualified entity class name

(type: String)
    ejbClassName - EJB class name

(type: String)
    importEjbFullClassName - whether to import ejbFullClassName or not
    ejbFullClassName - fully qualified EJB class name

(type: String)
    managedBeanName - name of managed bean (type: String)
    keyEmbedded - is entity primary key is an embeddable class

(type: Boolean)
    keyType - fully qualified class name
of entity primary key
    keyBody - body of Controller.Converter.getKey() method
    keyStringBody - body of Controller.Converter.getStringKey() method
    keyGetter - entity getter method returning primaty key instance
    keySetter - entity setter method to set primary key instance
    embeddedIdFields - contains information about embedded primary IDs
    cdiEnabled - project contains beans.xml, so Named beans can be used
    bundle - name of the variable defined in the JSF config file for the resource bundle (type: String)

  This template is accessible via top level menu Tools->Templates and can
  be found in category JavaServer Faces->JSF from Entity.

</#if>
package ${controllerPackageName};

<#if importEntityFullClassName?? && importEntityFullClassName == true>
import ${entityFullClassName};
</#if>
import ${controllerPackageName}.util.JsfUtil;
import ${controllerPackageName}.util.JsfUtil.PersistAction;
<#if importEjbFullClassName?? && importEjbFullClassName == true>
    <#if ejbClassName??>
import ${ejbFullClassName};
    <#elseif jpaControllerClassName??>
import ${jpaControllerFullClassName};
    </#if>
</#if>

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
<#if isInjected?? && isInjected==true>
import javax.annotation.Resource;
</#if>
<#if ejbClassName??>
import javax.ejb.EJB;
</#if>
import javax.ejb.EJBException;
<#if managedBeanName??>
<#if cdiEnabled?? && cdiEnabled>
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
<#else>
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
</#if>
</#if>
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
<#if jpaControllerClassName??>
  <#if isInjected?? && isInjected==true>
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
  <#else>
import javax.persistence.Persistence;
  </#if>
</#if>


<#if managedBeanName??>
<#if cdiEnabled?? && cdiEnabled>
@Named("${managedBeanName}")
<#else>
@ManagedBean(name = "${managedBeanName}")
</#if>
@SessionScoped


</#if>
public class $ {

    controllerClassName
}
implements Serializable {

<#if isInjected?? && isInjected==true>
    @Resource
        protected UserTransaction utx = null;
    @PersistenceUnit<#if persistenceUnitName??>(unitName = "${persistenceUnitName}")</#if>
    protected EntityManagerFactory emf = null;
</#if>

<#if ejbClassName??>
    @EJB
        protected ${ejbFullClassName} ejbFacade;
<#elseif jpaControllerClassName??>
    protected ${jpaControllerClassName} jpaController = null;
</#if>
    protected List<${entityClassName}> items = null;
    protected ${entityClassName} selected;
    protected ${entityClassName}[] selectedRows;
    protected boolean needLoadItems = true;

    public ${controllerClassName}() {
    }

    public ${entityClassName} getSelected() {
        return selected;
    }

    public void setSelected(${entityClassName} selected) {
        this.selected = selected;
    }

    public ${entityClassName}[] getSelectedRows() {
        return selectedRows;
    }

    public void setSelectedRows(${entityClassName}[] selectedRows) {
        this.selectedRows = selectedRows;
        if (selectedRows != null && selectedRows.length == 1) {
            selected = selectedRows[0];
        } else {
            selected = null;
        }
    }

    protected void setEmbeddableKeys() {
<#list embeddedIdFields as fields>
            selected.${keyGetter}().${fields.getEmbeddedSetter()}(selected.${fields.getCodeToPopulate()});
</#list>
    }

    protected void initializeEmbeddableKey() {
<#if keyEmbedded>
        selected.${keySetter}(new ${keyType}());
</#if>
    }

<#if ejbClassName??>
    protected ${ejbClassName} getFacade() {
        return ejbFacade;
    }
<#elseif jpaControllerClassName??>
    protected ${jpaControllerClassName} getJpaController() {
        if (jpaController == null) {
<#if isInjected?? && isInjected==true>
            jpaController = new ${jpaControllerClassName}(utx, emf);
<#else>
            jpaController = new ${jpaControllerClassName}(Persistence.createEntityManagerFactory(<#if persistenceUnitName??>"${persistenceUnitName}"</#if>));
</#if>
        }
        return jpaController;
    }
</#if>

    public ${entityClassName} prepareCreate() {
        selected = new ${entityClassName}();
        initializeEmbeddableKey();
        return selected;
    }

    public boolean create() {
        boolean ret = persist(PersistAction.CREATE, ResourceBundle.getBundle("${bundle}").getString("${entityClassName}Created"));
        return ret;
    }

    public boolean update() {
        boolean ret = persist(PersistAction.UPDATE, ResourceBundle.getBundle("${bundle}").getString("${entityClassName}Updated"));
        return ret;
    }

    public boolean destroyRows() {
        if (selectedRows == null || selectedRows.length == 0) {
            return false;
        }
       try {
            for (${entityClassName} selectedRow : selectedRows) {
<#if ejbClassName??>
                    getFacade().remove(selectedRow);
<#elseif jpaControllerClassName??>
                    getJpaController().destroy(selectedRow.getId());
 </#if>
            }
            if (!JsfUtil.isValidationFailed()) {
                JsfUtil.addSuccessMessage(selectedRows.length + " " +
                             ResourceBundle.getBundle("${bundle}").getString("${entityClassName}Deleted"));
                selectedRows = null;
                selected = null; // Remove selection
                needLoadItems = true;
                return true;
            } else {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("${bundle}").getString("PersistenceErrorOccured"));
                return false;
            }
        } catch (Exception e) {
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("${bundle}").getString("PersistenceErrorOccured"));
                return false;
        }
    }

    public boolean destroy() {
        boolean ret = persist(PersistAction.DELETE, SessionTools.getSessionMessage("DeletedSuccessfully"));
        if ( ret ) {
            selected = null;  // Remove selection
        }
        return ret;
    }

    public List<${entityClassName}> getItems() {
        if (needLoadItems) {
<#if ejbClassName??>
            items = getFacade().findAll();
<#elseif jpaControllerClassName??>
            items = getJpaController().find${entityClassName}Entities();
</#if>
            needLoadItems = false;
        }
        return items;
    }

    public void refreshRows() {
        needLoadItems = true;
    }

    protected boolean persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
<#if ejbClassName??>
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
<#elseif jpaControllerClassName??>
                if (persistAction == PersistAction.UPDATE) {
                    getJpaController().edit(selected);
                } else if (persistAction == PersistAction.CREATE) {
                    getJpaController().create(selected);
                } else {
                    getJpaController().destroy(selected.getId());
                }
</#if>
                JsfUtil.addSuccessMessage(successMessage);
                needLoadItems = true;
                return true;
            } catch (Exception e) {
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("${bundle}").getString("PersistenceErrorOccured"));
                return false;
            }
        } else {
            return false;
        }
    }

<#if ejbClassName?? && cdiEnabled?? && cdiEnabled>
    public ${entityClassName} get${entityClassName}(${keyType} id) {
        return getFacade().find(id);
    }
</#if>

    public List<${entityClassName}> getItemsAvailableSelectMany() {
<#if ejbClassName??>
        return getFacade().findAll();
<#elseif jpaControllerClassName??>
        return getJpaController().find${entityClassName}Entities();
</#if>
    }

    public List<${entityClassName}> getItemsAvailableSelectOne() {
<#if ejbClassName??>
        return getFacade().findAll();
<#elseif jpaControllerClassName??>
        return getJpaController().find${entityClassName}Entities();
</#if>
    }

    @FacesConverter(forClass = $

{entityClassName}.class

)
    public static class $ {

    controllerClassName
}
Converter implements Converter {
<#if keyEmbedded>

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";
</#if>

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ${controllerClassName} controller = (${controllerClassName})facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "${managedBeanName}");
<#if ejbClassName??>
<#if cdiEnabled?? && cdiEnabled>
            return controller.get${entityClassName}(getKey(value));
<#else>
            return controller.getFacade().find(getKey(value));
</#if>
<#elseif jpaControllerClassName??>
            return controller.getJpaController().find${entityClassName}(getKey(value));
</#if>
        }

        ${keyType} getKey(String value) {
            ${keyType} key;
${keyBody}
            return key;
        }

        String getStringKey(${keyType} value) {
            StringBuilder sb = new StringBuilder();
${keyStringBody}
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ${entityClassName}) {
                ${entityClassName} o = (${entityClassName}) object;
                return getStringKey(o.${keyGetter}());


} else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ${entityClassName}.class
.getName()});
                return null;
            }
        }

    }

}
