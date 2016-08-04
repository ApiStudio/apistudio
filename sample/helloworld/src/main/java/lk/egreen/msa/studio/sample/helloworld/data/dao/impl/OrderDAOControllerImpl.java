package lk.egreen.msa.studio.sample.helloworld.data.dao.impl;

import lk.egreen.msa.studio.datalayer.dao.impl.AbstractDAOController;
import lk.egreen.msa.studio.sample.helloworld.data.dao.OrderDAOController;
import lk.egreen.msa.studio.sample.helloworld.data.entity.Order;

import javax.inject.Singleton;

/**
 * Created by dewmal on 8/4/16.
 */

@Singleton
public class OrderDAOControllerImpl extends AbstractDAOController<Integer, Order> implements OrderDAOController {

    public OrderDAOControllerImpl() {
        super(Integer.class, Order.class);
    }
}
