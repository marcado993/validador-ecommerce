package epn.edu.ec.service;


import static java.util.stream.Collectors.toList;

import java.util.Comparator;

import org.springframework.stereotype.Service;

import epn.edu.ec.exception.CustomerNotFoundException;
import epn.edu.ec.model.cake.CakeResponse;
import epn.edu.ec.model.cake.CakesResponse;
import epn.edu.ec.model.cake.CreateCakeRequest;
import epn.edu.ec.model.cake.UpdateCakeRequest;
import epn.edu.ec.repository.CakeRepository;
import epn.edu.ec.repository.model.Cake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CakeService {
    private final CakeRepository cakeRepository;

    public CakesResponse getCakes() {
        return new CakesResponse(cakeRepository.findAll().stream()
                .map(this::cakeResponse)
                .sorted(Comparator.comparing(CakeResponse::getTitle))
                .collect(toList()));
    }

    public CakeResponse getCakeById(long cakeId) {
        return cakeResponse(findExistingCake(cakeId));
    }

    public CakeResponse createCake(CreateCakeRequest createCakeRequest) {
        Cake cake = cakeRepository.save(Cake.builder()
                .title(createCakeRequest.getTitle())
                .description(createCakeRequest.getDescription())
                .build());
        return cakeResponse(cake);
    }

    public CakeResponse updateCake(long cakeId, UpdateCakeRequest updateCakeRequest) {
        Cake existingCake = findExistingCake(cakeId);

        Cake updateCake = cakeRepository.save(
                existingCake.toBuilder()
                        .title(updateCakeRequest.getTitle())
                        .description(updateCakeRequest.getDescription())
                        .build()
        );

        return cakeResponse(updateCake);
    }

    public void deleteCake(long cakeId) {
        cakeRepository.delete(findExistingCake(cakeId));
    }

    private Cake findExistingCake(long cakeId) {
        return cakeRepository.findById(cakeId).orElseThrow(() -> {
            log.error("cake with id not found {}", cakeId);
            throw new CustomerNotFoundException();
        });
    }

    private CakeResponse cakeResponse(Cake cake) {
        return new CakeResponse(cake.getId(), cake.getTitle(), cake.getDescription());
    }
}
