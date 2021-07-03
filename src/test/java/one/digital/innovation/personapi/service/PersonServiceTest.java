package one.digital.innovation.personapi.service;

import one.digital.innovation.personapi.dto.MessageResponseDTO;

import one.digital.innovation.personapi.dto.request.PersonDTO;
import one.digital.innovation.personapi.entity.Person;
import one.digital.innovation.personapi.repository.PersonRepository;
import one.digital.innovation.personapi.utils.PersonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static one.digital.innovation.personapi.utils.PersonUtils.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void testGivenPersonDTOThenReturnSavedMessage() {
        PersonDTO personDTP = createFakeDTO();
        Person expectedSavedPerson = createFakeEntity();

        when(personRepository.save(expectedSavedPerson)).thenReturn(expectedSavedPerson);

        MessageResponseDTO expectedSuccessMessage = createExpectedMessageResponse(expectedSavedPerson.getId());
        MessageResponseDTO succesMessage = personService.createPerson(PersonDTO.builder().build());
    }
    private MessageResponseDTO createExpectedMessageResponse(Long id) {
        return MessageResponseDTO
                .builder()
                .message("Created person with ID " + id)
                .build();
    }

}
