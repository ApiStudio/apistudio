package lk.egreen.msa.studio.sample.helloworld.data.dao;

import lk.egreen.msa.studio.datalayer.dao.DAOController;
import lk.egreen.msa.studio.sample.helloworld.data.entity.Order;

import java.io.Serializable;

/**
 * Created by dewmal on 8/4/16.
 */
public interface OrderDAOController extends DAOController<Integer, Order>, Serializable {
}
