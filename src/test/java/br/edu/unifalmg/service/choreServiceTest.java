package br.edu.unifalmg.service;

import br.edu.unifalmg.domain.Chore;
import br.edu.unifalmg.exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class choreServiceTest {

    @Test
    @DisplayName("#addChore")
    void addChoreWhenTheDescriptionIsInvalidThrowAnException() {
        ChoreService service = new ChoreService();
        assertAll(
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore(null, null)),
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore("", null)),
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore(null, LocalDate.now().plusDays(1))),
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore("", LocalDate.now().plusDays(1))),
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore(null, LocalDate.now().minusDays(1))),
                () -> assertThrows(InvalidDescriptionException.class,
                        () -> service.addChore("", LocalDate.now().minusDays(1)))
        );
    }


    @Test
    @DisplayName("#addChore > When the deadline is invalid > Throw an exception")
    void addChoreWhenTheDeadLineIsInvalidThrowAnException() {
        //LocalDate.of(2023,2,31);
        ChoreService service = new ChoreService();
        assertAll(
                () ->assertThrows(InvalidDeadLineException.class,
                        ()-> service.addChore("Description", null)),
                () ->assertThrows(InvalidDeadLineException.class,
                        ()-> service.addChore("Description", LocalDate.now().minusDays(1)))
        );

    }

    @Test
    @DisplayName("#addChore > When adding a chore > When the chore already exists > Throw an exception")
    void addChoreWhenAddingAChoreWhenTheChoreAlreadyExistsThrowAnException(){
        ChoreService service = new ChoreService();
        service.addChore("Description", LocalDate.now());
        assertThrows(DuplicatedChoreException.class,
                ()-> service.addChore("Description", LocalDate.now()));
    }

    @Test
    @DisplayName("#deleteChore > When the list is empty > ")
    void deleteChoreWhenTheListIsEmptyThrowAnException(){
        ChoreService service = new ChoreService();
        assertThrows(EmptyChoreListException.class, () -> {
            service.deleteChore("Anything", LocalDate.now());
        });
    }

    @Test
    @DisplayName("#deleteChore > When the list in not empty > When the chore does not exists")
    void deleteChoreWhenTheListIsNotEmptyWhenTheChoreDoesNotExistThrowAnException() {
        ChoreService service = new ChoreService();
        service.addChore("Description", LocalDate.now());
        assertThrows(ChoreNotFoundException.class, () -> {
            service.deleteChore("Chore to be deleted", LocalDate.now().plusDays(1));
        });
    }

    @Test
    @DisplayName("#deleteChore > When the list is not empty > When the chore exists > Delete the chore")
    void deleteChoreWhenTheListIsNotEmptyWhenTheChoreExistsDeleteTheChore(){
        ChoreService service = new ChoreService();
        service.addChore("Chore #01", LocalDate.now().plusDays(1));
        assertEquals(1, service.getChores().size());
        service.deleteChore("Chore #01", LocalDate.now().plusDays(1));
        assertEquals(0, service.getChores().size());
    }

    @Test
    @DisplayName("#toggleChore > When the deadline is valid > Toggle the Chore")
    void toggleChoreWhenTheDeadlineIsValidToggleTheChore(){
        ChoreService service = new ChoreService();
        service.addChore("Chore #01", LocalDate.now());
        Assertions.assertFalse(service.getChores().get(0).getIsCompleted());
        assertDoesNotThrow(()-> service.toggleChore("Chore #01", LocalDate.now()));

        Assertions.assertTrue(service.getChores().get(0).getIsCompleted());
    }

    @Test
    @DisplayName("#printChores > When the list is empty > throw exception")
    void printChoresWhenTheListIsEmptyThrowAnException(){
        ChoreService service = new ChoreService();
        assertThrows(EmptyChoreListException.class, ()-> service.printChores());
    }

    @Test
    @DisplayName("#printChores > When the list is not empty > Print the descriptions")
    void printChoresWhenTheListIsNotEmptyPrintTheDescriptions(){
        ChoreService service = new ChoreService();
        service.addChore("Chore 1",LocalDate.now().plusDays(5));
        service.addChore("Chore 2",LocalDate.now().plusDays(3));
        service.addChore("Chore 3",LocalDate.now().plusDays(2));
        assertEquals("Description: Chore 1 / Deadline: 2023-10-18 / Status: Incompleted\n" +
                        "Description: Chore 2 / Deadline: 2023-10-16 / Status: Incompleted\n" +
                        "Description: Chore 3 / Deadline: 2023-10-15 / Status: Incompleted\n",
                service.printChores());
    }
}