package com.fasttracklogistics.service;

import com.fasttracklogistics.dao.ShipmentDao;
import com.fasttracklogistics.models.Shipment;
import java.util.List;

public class ShipmentService {
    private ShipmentDao dao = new ShipmentDao();

    public List<Shipment> getAll() {
        return dao.getAllShipments();
    }

    public void add(Shipment s) {
        dao.addShipment(s);
    }

    public void update(Shipment s) {
        dao.updateShipment(s);
    }

    public void delete(String id) {
        dao.deleteShipment(id);
    }
}