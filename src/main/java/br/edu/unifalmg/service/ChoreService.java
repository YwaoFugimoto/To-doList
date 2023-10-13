package br.edu.unifalmg.service;
import br.edu.unifalmg.domain.Chore;
import br.edu.unifalmg.exception.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ChoreService {

    private List<Chore> chores;

    List<Chore> getChores() {
        return chores;
    }

    public ChoreService() {
        chores = new ArrayList<>();
    }

    public Chore addChore(String description, LocalDate deadLine) {
        if (description == null || description.isEmpty()) {
            throw new InvalidDescriptionException("The description cannot be null or empty");
        }
        if (deadLine == null || deadLine.isBefore(LocalDate.now())) {
            throw new InvalidDeadLineException("The deadline cannot be null or before the current date");
        }

        for (Chore chore : chores) {
            if (chore.getDescription().equals(description) && chore.getDeadLine().equals(deadLine)) {
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

    /**
     * Method to delete a given chore
     *
     * @param description The description of the chore
     * @param deadline    The deadline of the chora
     */
    public void deleteChore(String description, LocalDate deadline) {
        if (this.chores.isEmpty()) {
            throw new EmptyChoreListException("Unable to remove a chore from an empty list");
        }
        boolean isChoreExist = this.chores.stream().anyMatch((chore -> chore.getDescription().equals(description)
                && chore.getDeadLine().isEqual(deadline)));
        if (!isChoreExist) {
            throw new ChoreNotFoundException("The given chore does not exist.");
        }
        this.chores = this.chores.stream().filter(chore -> !chore.getDescription().equals(description)
                && !chore.getDeadLine().isEqual(deadline)).collect(Collectors.toList());
    }
    //private Predicate<List<Chore>> isChoreListEmpty = List::isEmpty;

    public void toggleChore(String description, LocalDate deadLine) {

    }

    public String printChores() {
        if (isChoreListEmpty.test(this.chores)) {
            throw new EmptyChoreListException("Unable to print a chore from an empty list");
        }
        StringBuilder print = new StringBuilder();
        for (Chore chore : chores) {
            if (chore.getIsCompleted()) {
                print.append("Description: ").append(chore.getDescription()).append(" / Deadline: ").append(chore.getDeadline()).append(" / Status: Completed\n");
            } else {
                print.append("Description: ").append(chore.getDescription()).append(" / Deadline: ").append(chore.getDeadline()).append(" / Status: Incompleted\n");
            }
        }
        return print.toString();
    }

    private final Predicate<List<Chore>> isChoreListEmpty = choreList -> choreList.isEmpty();

}
