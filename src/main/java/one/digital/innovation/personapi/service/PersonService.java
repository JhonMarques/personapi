package one.digital.innovation.personapi.service;

import one.digital.innovation.personapi.dto.MessageResponseDTO;
import one.digital.innovation.personapi.dto.request.PersonDTO;
import one.digital.innovation.personapi.entity.Person;
import one.digital.innovation.personapi.exception.PersonNotFoundException;
import one.digital.innovation.personapi.mapper.PersonMapper;
import one.digital.innovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO) {

        Person personToSave = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(personToSave);
        return createdMetodResponse(savedPerson.getId(), "Created person with ID ");
    }


    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return  allPeople.stream()
                    .map(personMapper::toDTO)
                    .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
         Person person = verifyIfExists(id);

            return personMapper.toDTO(person);
        }

    public void delete(Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        personRepository.deleteById(id);
    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);

        Person personToUpdate = personMapper.toModel(personDTO);

        Person updatePerson = personRepository.save(personToUpdate);
        return createdMetodResponse(updatePerson.getId(), "Update person with ID ");
    }

    private MessageResponseDTO createdMetodResponse(Long id,  String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
}
