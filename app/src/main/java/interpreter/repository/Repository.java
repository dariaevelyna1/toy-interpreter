package interpreter.repository;

import interpreter.model.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    List<PrgState> programStates = new ArrayList<PrgState>();
    public Repository(PrgState state) {
        programStates.add(state);
    }

    @Override
    public PrgState getCrtPrg() {
        return programStates.get(0);
    }
}
