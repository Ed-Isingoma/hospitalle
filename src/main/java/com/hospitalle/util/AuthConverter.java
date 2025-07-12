package com.hospitalle.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import com.hospitalle.models.Auth;
import com.hospitalle.services.impl.AuthServiceImpl;

@FacesConverter(value="authConverter", managed=true)
public class AuthConverter implements Converter<Auth> {

    private final AuthServiceImpl authServiceImpl = new AuthServiceImpl();

    @Override
    public Auth getAsObject(FacesContext ctx, UIComponent comp, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            Long id = Long.valueOf(value);
            return authServiceImpl.findById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent comp, Auth auth) {
        if (auth == null || auth.getId() == null) {
            return "";
        }
        return auth.getId().toString();
    }
}
