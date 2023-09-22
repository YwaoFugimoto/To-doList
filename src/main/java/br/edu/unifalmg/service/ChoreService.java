package br.edu.unifalmg.service;
import br.edu.unifalmg.domain.Chore;
import br.edu.unifalmg.exception.DuplicatedChoreException;
import br.edu.unifalmg.exception.InvalidDeadLineException;
import br.edu.unifalmg.exception.InvalidDescriptionException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChoreService {

    private List<Chore> chores;
    public ChoreService(){
        chores = new ArrayList<>();
    }

    public Chore addChore(String description, LocalDate deadLine){
        if ( description == null || description.isEmpty()){
            throw new InvalidDescriptionException("The description cannot be null or empty");
        }
        if(deadLine == null || deadLine.isBefore(LocalDate.now())){
            throw new InvalidDeadLineException("The deadline cannot be null or before the current date");
        }

        for (Chore chore : chores){
            if(chore.getDescription().equals(description) && chore.getDeadLine().equals(deadLine)){
                throw new DuplicatedChoreException("The given chore already exists.");
            }
        }

//        Chore chore = new Chore();
//        chore.setDescription(description);
//        chore.setDeadLine(deadLine);
//        chore.setIsCompleted(Boolean.FALSE);
        Chore chore = Chore.builder()
                .deadLine(deadLine)
                .isCompleted(Boolean.FALSE)
                .description(description)
                .build();
        chores.add(chore);

        return chore;
    }

}
