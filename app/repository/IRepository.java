package app.repository;

import app.model.program_state.ProgramState;

import java.util.List;

public interface IRepository {
    void logProgramState(ProgramState state) throws RepositoryException;
    List <ProgramState> getProgramStates();
    void setProgramStates(List <ProgramState> newStates);
}
