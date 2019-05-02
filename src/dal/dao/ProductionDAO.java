package dal.dao;

import dal.DummyData;
import dal.dto.ProductionDTO;

import java.io.IOException;

public class ProductionDAO implements I_ProductionDAO {

    DummyData data = new DummyData();

    public ProductionDAO() throws IOException, ClassNotFoundException {
    }

    @Override
    public ProductionDTO getBatch(int batchId) throws I_ProductionDAO.DALException {
        return null;
    }

    @Override
    public void createBatch(ProductionDTO batch) throws I_ProductionDAO.DALException {
        data.createBatch(batch);
    }

    @Override
    public void updateBatch(ProductionDTO batch) throws I_ProductionDAO.DALException {

    }

    @Override
    public void deleteBatch(ProductionDTO batch) throws I_ProductionDAO.DALException {

    }
}
