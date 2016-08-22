package io.egreen.apistudio.monitor.data.dao.impl;

import io.egreen.apistudio.datalayer.dao.impl.AbstractDAOController;
import io.egreen.apistudio.monitor.data.dao.RequestModelDAO;
import io.egreen.apistudio.monitor.data.entity.RequestStaticModel;

/**
 * Created by dewmal on 8/20/16.
 */
public class RequestStaticModelDAOImpl extends AbstractDAOController<String, RequestStaticModel> implements RequestModelDAO {

    public RequestStaticModelDAOImpl() {
        super(String.class, RequestStaticModel.class);
    }
}
