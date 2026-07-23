package com.dhaneesh.seatsure.theatre.service;

import com.dhaneesh.seatsure.exceptions.ResourceAlreadyExistsException;
import com.dhaneesh.seatsure.exceptions.ResourceNotFoundException;
import com.dhaneesh.seatsure.theatre.dto.CreateTheatreRequest;
import com.dhaneesh.seatsure.theatre.dto.TheatreResponse;
import com.dhaneesh.seatsure.theatre.entity.Theatre;
import com.dhaneesh.seatsure.theatre.mapper.TheatreMapper;
import com.dhaneesh.seatsure.theatre.repository.TheatreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatreService {

    private final TheatreRepository theatreRepository;
    private final TheatreMapper theatreMapper;

    public TheatreService(TheatreRepository theatreRepository,
                          TheatreMapper theatreMapper) {
        this.theatreRepository = theatreRepository;
        this.theatreMapper = theatreMapper;
    }

    public TheatreResponse createTheatre(CreateTheatreRequest request) {

        theatreRepository.findByNameAndCity(
                request.getName(),
                request.getCity()
        ).ifPresent(theatre -> {
            throw new ResourceAlreadyExistsException(
                    "Theatre already exists"
            );
        });

        Theatre theatre = theatreMapper.toEntity(request);

        Theatre savedTheatre = theatreRepository.save(theatre);

        return theatreMapper.toResponse(savedTheatre);
    }

    public TheatreResponse getTheatreById(Long id) {

        Theatre theatre = getActiveTheatre(id);

        return theatreMapper.toResponse(theatre);
    }

    public List<TheatreResponse> getAllTheatres() {

        return theatreRepository.findByActiveTrue()
                .stream()
                .map(theatreMapper::toResponse)
                .toList();
    }

    public void deleteTheatre(Long id) {

        Theatre theatre = getActiveTheatre(id);

        theatre.setActive(false);

        theatreRepository.save(theatre);
    }

    private Theatre getActiveTheatre(Long id) {

        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Theatre not found"
                        ));

        if (!theatre.getActive()) {
            throw new ResourceNotFoundException(
                    "Theatre not found"
            );
        }

        return theatre;
    }
}