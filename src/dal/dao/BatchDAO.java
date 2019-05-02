package dal.dao;

import dal.DummyData;
import dal.dto.BatchDTO;

import java.io.IOException;

public class BatchDAO implements IBatchDAO {

    DummyData data = new DummyData();

    public BatchDAO() throws IOException, ClassNotFoundException {
    }

    @Override
    public BatchDTO getBatch(int batchId) throws IBatchDAO.DALException {
        return null;
    }

    @Override
    public void createBatch(BatchDTO batch) throws IBatchDAO.DALException {
        data.createBatch(batch);
    }

    @Override
    public void updateBatch(BatchDTO batch) throws IBatchDAO.DALException {

    }

    @Override
    public void deleteBatch(BatchDTO batch) throws IBatchDAO.DALException {

    }
}
